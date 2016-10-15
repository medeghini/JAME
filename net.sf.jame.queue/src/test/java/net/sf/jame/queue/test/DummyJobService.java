/*
 * JAME 6.2.1
 * http://jame.sourceforge.net
 *
 * Copyright 2001, 2016 Andrea Medeghini
 *
 * This file is part of JAME.
 *
 * JAME is an application for creating fractals and other graphics artifacts.
 *
 * JAME is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JAME is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JAME.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package net.sf.jame.queue.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;

import net.sf.jame.core.util.Worker;
import net.sf.jame.queue.spool.DistributedJobService;
import net.sf.jame.queue.spool.JobData;
import net.sf.jame.queue.spool.JobFactory;
import net.sf.jame.queue.spool.JobIDFactory;
import net.sf.jame.queue.spool.JobListener;
import net.sf.jame.queue.spool.JobServiceListener;
import net.sf.jame.twister.TwisterClip;

/**
 * @author Andrea Medeghini
 */
public class DummyJobService<T extends DummyJob> implements DistributedJobService<T> {
	private static final Logger logger = Logger.getLogger(DummyJobService.class.getName());
	private final HashMap<String, ScheduledJob> scheduledJobs = new HashMap<String, ScheduledJob>();
	private final HashMap<String, ScheduledJob> startedJobs = new HashMap<String, ScheduledJob>();
	private final HashMap<String, ScheduledJob> terminatedJobs = new HashMap<String, ScheduledJob>();
	private final HashMap<String, ScheduledJob> spooledJobs = new HashMap<String, ScheduledJob>();
	private final JobFactory<T> jobFactory;
	private final Object monitor = new Object();
	private final String serviceName;
	private final int maxJobCount = 5;
	private final Worker worker;
	private Thread thread;
	private boolean running;
	private boolean dirty;
	private volatile int jobCount;

	/**
	 * @param serviceName
	 * @param jobFactory
	 * @param worker
	 */
	public DummyJobService(final String serviceName, final JobFactory<T> jobFactory, final Worker worker) {
		this.worker = worker;
		this.jobFactory = jobFactory;
		this.serviceName = serviceName;
	}

	/**
	 * @see net.sf.jame.queue.spool.JobService#getName()
	 */
	public String getName() {
		return serviceName;
	}

	/**
	 * @see net.sf.jame.queue.spool.JobService#start()
	 */
	public void start() {
		if (thread == null) {
			thread = new Thread(new ServiceHandler(), "DummyJobService Thread");
			thread.setDaemon(true);
			running = true;
			thread.start();
		}
	}

	/**
	 * @see net.sf.jame.queue.spool.JobService#stop()
	 */
	public void stop() {
		if (thread != null) {
			running = false;
			thread.interrupt();
			try {
				thread.join();
			}
			catch (final InterruptedException e) {
			}
			thread = null;
		}
	}

	/**
	 * @see net.sf.jame.queue.spool.JobService#getJobCount()
	 */
	public int getJobCount() {
		synchronized (spooledJobs) {
			return spooledJobs.size();
		}
	}

	/**
	 * @see net.sf.jame.queue.spool.JobService#deleteJob(java.lang.String)
	 */
	public void deleteJob(final String jobId) {
		synchronized (spooledJobs) {
			synchronized (scheduledJobs) {
				synchronized (startedJobs) {
					synchronized (terminatedJobs) {
						scheduledJobs.remove(jobId);
						final ScheduledJob scheduledJob = terminatedJobs.remove(jobId);
						if (scheduledJob != null) {
							worker.addTask(new DeleteTask(scheduledJob));
						}
					}
				}
			}
		}
	}

	/**
	 * @see net.sf.jame.queue.spool.JobService#stopJob(java.lang.String)
	 */
	public void stopJob(final String jobId) {
		synchronized (spooledJobs) {
			synchronized (scheduledJobs) {
				synchronized (startedJobs) {
					scheduledJobs.remove(jobId);
					final ScheduledJob scheduledJob = startedJobs.remove(jobId);
					if (scheduledJob != null) {
						worker.addTask(new StopTask(scheduledJob));
					}
				}
			}
		}
	}

	/**
	 * @see net.sf.jame.queue.spool.JobService#abortJob(java.lang.String)
	 */
	public void abortJob(final String jobId) {
		synchronized (spooledJobs) {
			synchronized (scheduledJobs) {
				synchronized (startedJobs) {
					final ScheduledJob scheduledJob = startedJobs.get(jobId);
					if (scheduledJob != null) {
						worker.addTask(new AbortTask(scheduledJob));
					}
				}
			}
		}
	}

	/**
	 * @see net.sf.jame.queue.spool.JobService#createJob(net.sf.jame.queue.spool.JobListener)
	 */
	public String createJob(final JobListener listener) {
		synchronized (spooledJobs) {
			final T job = jobFactory.createJob(JobIDFactory.newJobId(), listener);
			spooledJobs.put(job.getJobId(), new ScheduledJob(job, System.currentTimeMillis() + 10 * 1000L));
			return job.getJobId();
		}
	}

	/**
	 * @see net.sf.jame.queue.spool.JobService#setJobData(java.lang.String, JobData, int)
	 */
	public void setJobData(final String jobId, final JobData jobData, final int frameNumber) {
		synchronized (spooledJobs) {
			ScheduledJob job = spooledJobs.get(jobId);
			if (job != null) {
				job.getJob().setJobDataRow(jobData);
				job.getJob().setFirstFrameNumber(frameNumber);
			}
		}
	}

	/**
	 * @see net.sf.jame.queue.spool.JobService#runJob(java.lang.String)
	 */
	public void runJob(final String jobId) {
		synchronized (spooledJobs) {
			synchronized (scheduledJobs) {
				synchronized (startedJobs) {
					synchronized (terminatedJobs) {
						if (terminatedJobs.get(jobId) == null) {
							if (startedJobs.get(jobId) == null) {
								final ScheduledJob scheduledJob = scheduledJobs.get(jobId);
								if (scheduledJob != null) {
									worker.addTask(new ResetTask(scheduledJob));
								}
								else {
									final ScheduledJob job = spooledJobs.get(jobId);
									if ((job != null) && !scheduledJobs.containsKey(jobId)) {
										scheduledJobs.put(jobId, job);
									}
								}
							}
						}
					}
				}
			}
		}
		synchronized (monitor) {
			dirty = true;
			monitor.notify();
		}
	}

	private class ServiceHandler implements Runnable {
		/**
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			try {
				Iterator<ScheduledJob> jobIterator = null;
				while (running) {
					synchronized (scheduledJobs) {
						synchronized (startedJobs) {
							while (jobIterator.hasNext()) {
								final ScheduledJob scheduledJob = jobIterator.next();
								if (scheduledJob.getJob().isStarted() && (System.currentTimeMillis() > scheduledJob.time)) {
									worker.addTask(new UpdateTask(scheduledJob));
								}
								else if (scheduledJob.getJob().isStarted() && (System.currentTimeMillis() > scheduledJob.time + 30 * 1000L)) {
									jobIterator.remove();
									worker.addTask(new StopTask(scheduledJob));
								}
								else if (!scheduledJob.isAborted() && ((System.currentTimeMillis() - scheduledJob.getJob().getLastUpdate()) > 60 * 1000L)) {
									jobIterator.remove();
									worker.addTask(new ResetTask(scheduledJob));
								}
							}
							jobIterator = scheduledJobs.values().iterator();
							while (jobIterator.hasNext()) {
								final ScheduledJob scheduledJob = jobIterator.next();
								if (scheduledJob.isTerminated()) {
									jobIterator.remove();
								}
								else if (!scheduledJob.isAborted() && !startedJobs.containsKey(scheduledJob.getJob().getJobId()) && (startedJobs.size() < maxJobCount)) {
									startedJobs.put(scheduledJob.getJob().getJobId(), scheduledJob);
									worker.addTask(new StartTask(scheduledJob));
								}
							}
						}
					}
					synchronized (spooledJobs) {
						synchronized (terminatedJobs) {
							jobIterator = terminatedJobs.values().iterator();
							while (jobIterator.hasNext()) {
								final ScheduledJob scheduledJob = jobIterator.next();
								if ((System.currentTimeMillis() - scheduledJob.getJob().getLastUpdate()) > 120 * 1000L) {
									jobIterator.remove();
									spooledJobs.remove(scheduledJob.getJob().getJobId());
									worker.addTask(new DeleteTask(scheduledJob));
								}
							}
						}
					}
					synchronized (monitor) {
						if (!dirty) {
							monitor.wait(10000);
						}
						dirty = false;
					}
				}
			}
			catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	private class DeleteTask implements Runnable {
		private final ScheduledJob job;

		/**
		 * @param job
		 */
		protected DeleteTask(final ScheduledJob job) {
			this.job = job;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			job.dispose();
			logger.info(serviceName + ": Job deleted " + job.getJob() + " (jobs = " + jobCount + ")");
		}
	}

	private class StartTask implements Runnable {
		private final ScheduledJob job;

		/**
		 * @param job
		 */
		protected StartTask(final ScheduledJob job) {
			this.job = job;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			job.start();
			jobCount += 1;
			logger.info(serviceName + ": Job started " + job.getJob() + " (jobs = " + jobCount + ")");
		}
	}

	private class StopTask implements Runnable {
		private final ScheduledJob job;

		/**
		 * @param job
		 */
		protected StopTask(final ScheduledJob job) {
			this.job = job;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			job.stop();
			if (jobCount > 0) {
				jobCount -= 1;
			}
			logger.info(serviceName + ": Job stopped " + job.getJob() + " (jobs = " + jobCount + ")");
			synchronized (terminatedJobs) {
				terminatedJobs.put(job.getJob().getJobId(), job);
			}
		}
	}

	private class AbortTask implements Runnable {
		private final ScheduledJob job;

		/**
		 * @param job
		 */
		protected AbortTask(final ScheduledJob job) {
			this.job = job;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			job.abort();
			logger.info(serviceName + ": Job aborted " + job.getJob() + " (jobs = " + jobCount + ")");
		}
	}

	private class UpdateTask implements Runnable {
		private final ScheduledJob job;

		/**
		 * @param job
		 */
		protected UpdateTask(final ScheduledJob job) {
			this.job = job;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			job.setFrameNumber(0);
			logger.info(serviceName + ": Job updated " + job.getJob() + " (jobs = " + jobCount + ")");
		}
	}

	private class ResetTask implements Runnable {
		private final ScheduledJob job;

		/**
		 * @param job
		 */
		protected ResetTask(final ScheduledJob job) {
			this.job = job;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			job.reset();
			if (jobCount > 0) {
				jobCount -= 1;
			}
			logger.info(serviceName + ": Job resetted " + job.getJob() + " (jobs = " + jobCount + ")");
		}
	}

	private class ScheduledJob {
		private final T job;
		private long time;

		/**
		 * @param job
		 * @param time
		 */
		public ScheduledJob(final T job, final long time) {
			this.job = job;
			this.time = time;
		}

		/**
		 * @return
		 */
		public boolean isAborted() {
			return job.isAborted();
		}

		/**
		 * @return
		 */
		public boolean isTerminated() {
			return job.isStarted() && (System.currentTimeMillis() > time);
		}

		/**
		 * @param frameNumber
		 */
		public void setFrameNumber(final int frameNumber) {
			job.setFrameNumber(frameNumber);
		}

		/**
		 * 
		 */
		public void start() {
			job.start();
		}

		/**
		 * 
		 */
		public void stop() {
			time = 0;
			job.abort();
			job.stop();
		}

		/**
		 * 
		 */
		public void abort() {
			job.abort();
		}

		/**
		 * 
		 */
		public void reset() {
			time = 0;
			job.abort();
			job.stop();
			job.reset();
		}

		/**
		 * @return the job
		 */
		public T getJob() {
			return job;
		}

		/**
		 * @return the time
		 */
		public long getTime() {
			return time;
		}

		/**
		 * 
		 */
		public void dispose() {
			job.dispose();
		}
	}

	public void addServiceListener(final JobServiceListener listener) {
	}

	public void removeServiceListener(final JobServiceListener listener) {
	}

	public byte[] getJobFrame(final String jobId, final int frameNumber) throws IOException {
		return null;
	}

	public void setJobFrame(final String jobId, final TwisterClip clip, final byte[] data) throws IOException {
	}
}
