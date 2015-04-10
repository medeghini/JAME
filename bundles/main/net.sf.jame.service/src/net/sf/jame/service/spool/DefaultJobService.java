/*
 * JAME 6.2
 * http://jame.sourceforge.net
 *
 * Copyright 2001, 2015 Andrea Medeghini
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
package net.sf.jame.service.spool;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadFactory;

import net.sf.jame.core.util.DefaultThreadFactory;
import net.sf.jame.core.util.Worker;

import org.apache.log4j.Logger;

/**
 * @author Andrea Medeghini
 */
public class DefaultJobService<T extends JobInterface> implements JobService<T> {
	private static final long CLEANUP_POLLINGTIME = 60 * 1000L;
	private static final long DISPATCHER_POLLINGTIME = 30 * 1000L;
	private static final int DEFAULT_MAX_STARTED_JOBS = 5;
	private static final long JOB_TIMEOUT = 60 * 1000L;
	private static final long JOB_LIFETIME = 120 * 1000L;
	private static final Logger logger = Logger.getLogger(DefaultJobService.class);
	private static final ThreadFactory factory = new DefaultThreadFactory("Thread", true, Thread.MIN_PRIORITY);
	private final List<JobServiceListener> listeners = new LinkedList<JobServiceListener>();
	protected final HashMap<String, ScheduledJob> spooledJobs = new HashMap<String, ScheduledJob>();
	protected final HashMap<String, ScheduledJob> scheduledJobs = new HashMap<String, ScheduledJob>();
	protected final HashMap<String, ScheduledJob> startedJobs = new HashMap<String, ScheduledJob>();
	protected final HashMap<String, ScheduledJob> terminatedJobs = new HashMap<String, ScheduledJob>();
	private final JobFactory<T> jobFactory;
	private final Object dispatchMonitor = new Object();
	private final Object serviceMonitor = new Object();
	private final String serviceName;
	private final int maxJobCount;
	private final Worker worker;
	private Thread thread;
	private boolean running;
	private boolean dispatchDirty;
	private boolean serviceDirty;
	private volatile int jobCount;
	private final int serviceId;

	/**
	 * @param serviceName
	 * @param jobFactory
	 * @param worker
	 */
	public DefaultJobService(final int serviceId, final String serviceName, final JobFactory<T> jobFactory, final Worker worker) {
		this.serviceId = serviceId;
		this.serviceName = serviceName;
		this.jobFactory = jobFactory;
		this.worker = worker;
		maxJobCount = Integer.getInteger("jame.maxJobCount", DEFAULT_MAX_STARTED_JOBS);
	}

	/**
	 * @see net.sf.jame.service.spool.JobService#getName()
	 */
	public String getName() {
		return serviceName;
	}

	/**
	 * @see net.sf.jame.service.spool.JobService#start()
	 */
	public void start() {
		if (thread == null) {
			thread = factory.newThread(new ServiceHandler());
			thread.setName(serviceName + " JobService Thread");
			running = true;
			thread.start();
		}
	}

	/**
	 * @see net.sf.jame.service.spool.JobService#stop()
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
		synchronized (spooledJobs) {
			synchronized (scheduledJobs) {
				synchronized (startedJobs) {
					synchronized (terminatedJobs) {
						spooledJobs.clear();
						scheduledJobs.clear();
						startedJobs.clear();
						terminatedJobs.clear();
					}
				}
			}
		}
	}

	/**
	 * @see net.sf.jame.service.spool.JobService#getJobCount()
	 */
	public int getJobCount() {
		synchronized (spooledJobs) {
			return spooledJobs.size();
		}
	}

	private boolean isBusy() {
		return (scheduledJobs.size() != 0) || (startedJobs.size() != 0) || (terminatedJobs.size() != 0);
	}

	/**
	 * @see net.sf.jame.service.spool.JobService#deleteJob(java.lang.String)
	 */
	public void deleteJob(final String jobId) {
		if (jobId == null) {
			throw new NullPointerException("jobId == null");
		}
		synchronized (spooledJobs) {
			synchronized (scheduledJobs) {
				synchronized (startedJobs) {
					synchronized (terminatedJobs) {
						final ScheduledJob scheduledJob = spooledJobs.remove(jobId);
						scheduledJobs.remove(jobId);
						terminatedJobs.remove(jobId);
						if (scheduledJob != null) {
							worker.addTask(new DeleteTask(scheduledJob));
						}
						fireStateChanged(isBusy() ? JobServiceListener.STATUS_BUSY : JobServiceListener.STATUS_IDLE, serviceName + " running");
					}
				}
			}
		}
	}

	/**
	 * @see net.sf.jame.service.spool.JobService#stopJob(java.lang.String)
	 */
	public void stopJob(final String jobId) {
		if (jobId == null) {
			throw new NullPointerException("jobId == null");
		}
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
	 * @see net.sf.jame.service.spool.JobService#abortJob(java.lang.String)
	 */
	public void abortJob(final String jobId) {
		if (jobId == null) {
			throw new NullPointerException("jobId == null");
		}
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
	 * @see net.sf.jame.service.spool.JobService#createJob(net.sf.jame.service.spool.JobListener)
	 */
	public String createJob(final JobListener listener) {
		if (listener == null) {
			throw new NullPointerException("listener == null");
		}
		synchronized (spooledJobs) {
			final T job = jobFactory.createJob(JobIDFactory.newJobId(), new ServiceJobListener(listener));
			spooledJobs.put(job.getJobId(), new ScheduledJob(job));
			return job.getJobId();
		}
	}

	/**
	 * @see net.sf.jame.service.spool.JobService#setJobData(java.lang.String, JobData, int)
	 */
	public void setJobData(final String jobId, final JobData jobData, final int frameNumber) {
		if (jobId == null) {
			throw new NullPointerException("jobId == null");
		}
		if (jobData == null) {
			throw new NullPointerException("jobData == null");
		}
		synchronized (spooledJobs) {
			ScheduledJob job = spooledJobs.get(jobId);
			if (job != null) {
				job.getJob().setJobDataRow(jobData);
				job.getJob().setFirstFrameNumber(frameNumber);
			}
		}
	}

	/**
	 * @see net.sf.jame.service.spool.JobService#runJob(java.lang.String)
	 */
	public void runJob(final String jobId) {
		if (jobId == null) {
			throw new NullPointerException("jobId == null");
		}
		synchronized (spooledJobs) {
			synchronized (scheduledJobs) {
				synchronized (startedJobs) {
					synchronized (terminatedJobs) {
						if (terminatedJobs.get(jobId) == null) {
							if (startedJobs.get(jobId) == null) {
								ScheduledJob scheduledJob = scheduledJobs.get(jobId);
								if (scheduledJob != null) {
									worker.addTask(new ResetTask(scheduledJob));
								}
								else {
									scheduledJob = spooledJobs.get(jobId);
									if (scheduledJob != null) {
										scheduledJobs.put(jobId, scheduledJob);
										worker.addTask(new ResetTask(scheduledJob));
									}
								}
							}
						}
						fireStateChanged(isBusy() ? JobServiceListener.STATUS_BUSY : JobServiceListener.STATUS_IDLE, serviceName + " running");
					}
				}
			}
		}
		synchronized (dispatchMonitor) {
			dispatchDirty = true;
			dispatchMonitor.notify();
		}
	}

	private class ServiceHandler implements Runnable {
		/**
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			boolean interrupted = false;
			fireStateChanged(JobServiceListener.STATUS_BORN, serviceName + " started");
			final Thread dispatcherThread = factory.newThread(new DispatcherHandler());
			dispatcherThread.setName(serviceName + " Dispatcher Thread");
			try {
				dispatcherThread.start();
				Iterator<ScheduledJob> jobIterator = null;
				while (running) {
					synchronized (spooledJobs) {
						synchronized (scheduledJobs) {
							synchronized (startedJobs) {
								jobIterator = startedJobs.values().iterator();
								while (jobIterator.hasNext()) {
									final ScheduledJob scheduledJob = jobIterator.next();
									if (scheduledJob.isTerminated()) {
										jobIterator.remove();
										terminatedJobs.put(scheduledJob.getJob().getJobId(), scheduledJob);
										worker.addTask(new StopTask(scheduledJob));
									}
									else if ((System.currentTimeMillis() - scheduledJob.getJob().getLastUpdate()) > JOB_TIMEOUT) {
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
								}
								synchronized (terminatedJobs) {
									jobIterator = terminatedJobs.values().iterator();
									while (jobIterator.hasNext()) {
										final ScheduledJob scheduledJob = jobIterator.next();
										if ((System.currentTimeMillis() - scheduledJob.getJob().getLastUpdate()) > JOB_LIFETIME) {
											jobIterator.remove();
											spooledJobs.remove(scheduledJob.getJob().getJobId());
											worker.addTask(new DeleteTask(scheduledJob));
										}
									}
									fireStateChanged(isBusy() ? JobServiceListener.STATUS_BUSY : JobServiceListener.STATUS_IDLE, serviceName + " running");
								}
							}
						}
					}
					synchronized (serviceMonitor) {
						if (!serviceDirty) {
							serviceMonitor.wait(CLEANUP_POLLINGTIME);
						}
						// else {
						// Thread.yield();
						// }
						serviceDirty = false;
					}
				}
			}
			catch (final InterruptedException e) {
				interrupted = true;
			}
			finally {
				dispatcherThread.interrupt();
				try {
					dispatcherThread.join();
				}
				catch (InterruptedException e) {
					interrupted = true;
				}
				fireStateChanged(JobServiceListener.STATUS_DEAD, serviceName + " stopped");
			}
			if (interrupted) {
				Thread.currentThread().interrupt();
			}
		}
	}

	private class DispatcherHandler implements Runnable {
		private final List<ScheduledJob> jobs = new LinkedList<ScheduledJob>();

		/**
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			try {
				while (running) {
					try {
						fetchJobs(jobs);
						dispatchJobs(jobs);
						jobs.clear();
					}
					catch (final Exception e) {
						e.printStackTrace();
					}
					synchronized (dispatchMonitor) {
						if (!dispatchDirty) {
							dispatchMonitor.wait(DISPATCHER_POLLINGTIME);
						}
						// else {
						// Thread.yield();
						// }
						dispatchDirty = false;
					}
				}
			}
			catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		private void fetchJobs(final List<ScheduledJob> jobs) {
			synchronized (scheduledJobs) {
				synchronized (startedJobs) {
					for (ScheduledJob scheduledJob : scheduledJobs.values()) {
						if (!scheduledJob.isAborted() && !startedJobs.containsKey(scheduledJob.getJob().getJobId()) && (startedJobs.size() < maxJobCount)) {
							startedJobs.put(scheduledJob.getJob().getJobId(), scheduledJob);
							jobs.add(scheduledJob);
						}
					}
				}
			}
		}

		private void dispatchJobs(final List<ScheduledJob> jobs) {
			for (ScheduledJob scheduledJob : jobs) {
				worker.addTask(new StartTask(scheduledJob));
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

	private class ServiceJobListener implements JobListener {
		private final JobListener listener;

		/**
		 * @param listener
		 */
		protected ServiceJobListener(final JobListener listener) {
			this.listener = listener;
		}

		/**
		 * @see net.sf.jame.service.spool.JobListener#updated(java.lang.String, net.sf.jame.service.spool.JobData)
		 */
		public void updated(final String jobId, final JobData job) {
			listener.updated(jobId, new DefaultJobData(job));
		}

		/**
		 * @see net.sf.jame.service.spool.JobListener#started(java.lang.String, net.sf.jame.service.spool.JobData)
		 */
		public void started(final String jobId, final JobData job) {
			listener.started(jobId, new DefaultJobData(job));
		}

		/**
		 * @see net.sf.jame.service.spool.JobListener#stopped(java.lang.String, net.sf.jame.service.spool.JobData)
		 */
		public void stopped(final String jobId, final JobData job) {
			listener.stopped(jobId, new DefaultJobData(job));
			synchronized (dispatchMonitor) {
				dispatchDirty = true;
				dispatchMonitor.notify();
			}
		}

		/**
		 * @see net.sf.jame.service.spool.JobListener#terminated(java.lang.String, net.sf.jame.service.spool.JobData)
		 */
		public void terminated(final String jobId, final JobData job) {
			listener.terminated(jobId, new DefaultJobData(job));
			synchronized (serviceMonitor) {
				serviceDirty = true;
				serviceMonitor.notify();
			}
		}

		/**
		 * @see net.sf.jame.service.spool.JobListener#disposed(java.lang.String, net.sf.jame.service.spool.JobData)
		 */
		public void disposed(final String jobId, final JobData job) {
			listener.disposed(jobId, new DefaultJobData(job));
		}
	}

	protected class ScheduledJob {
		private final T job;

		/**
		 * @param job
		 */
		public ScheduledJob(final T job) {
			this.job = job;
		}

		/**
		 * @return
		 */
		public boolean isTerminated() {
			return job.isTerminated();
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
		public boolean isStarted() {
			return job.isStarted();
		}

		/**
		 * 
		 */
		public void stop() {
			job.stop();
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
		public void abort() {
			job.abort();
		}

		/**
		 * 
		 */
		public void reset() {
			job.reset();
		}

		/**
		 * @return the job
		 */
		public T getJob() {
			return job;
		}

		/**
		 * 
		 */
		public void dispose() {
			job.dispose();
		}
	}

	/**
	 * @see net.sf.jame.service.spool.JobService#addServiceListener(net.sf.jame.service.spool.JobServiceListener)
	 */
	public void addServiceListener(final JobServiceListener listener) {
		listeners.add(listener);
	}

	/**
	 * @see net.sf.jame.service.spool.JobService#removeServiceListener(net.sf.jame.service.spool.JobServiceListener)
	 */
	public void removeServiceListener(final JobServiceListener listener) {
		listeners.remove(listener);
	}

	/**
	 * @param message
	 */
	protected void fireStateChanged(final int status, final String message) {
		for (JobServiceListener listener : listeners) {
			listener.stateChanged(serviceId, status, message);
		}
	}
}
