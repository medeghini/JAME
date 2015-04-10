/*
 * JAME 6.1 
 * http://jame.sourceforge.net
 *
 * Copyright 2001, 2010 Andrea Medeghini
 * http://andreamedeghini.users.sourceforge.net
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
package net.sf.jame.queue.network.spool;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Logger;

import net.sf.jame.core.util.DefaultThreadFactory;
import net.sf.jame.queue.network.EventMessage;
import net.sf.jame.queue.network.RequestMessage;
import net.sf.jame.queue.network.ResponseMessage;
import net.sf.jame.queue.network.ServiceException;
import net.sf.jame.queue.network.ServiceMessage;
import net.sf.jame.queue.network.ServiceProcessor;
import net.sf.jame.queue.network.ServiceSession;
import net.sf.jame.queue.network.SessionHandler;
import net.sf.jame.queue.spool.DistributedJobInterface;
import net.sf.jame.queue.spool.DistributedJobService;
import net.sf.jame.queue.spool.JobData;
import net.sf.jame.queue.spool.JobEvent;
import net.sf.jame.queue.spool.JobListener;
import net.sf.jame.queue.spool.JobStatus;
import net.sf.jame.queue.spool.job.DistributedJobDecoder;

/**
 * @author Andrea Medeghini
 */
public class DistributedServiceProcessor implements ServiceProcessor {
	private static final Logger logger = Logger.getLogger(DistributedServiceProcessor.class.getName());
	private static final ThreadFactory factory = new DefaultThreadFactory("Thread", true, Thread.MIN_PRIORITY);
	private final List<ExecutorTask> tasks = new LinkedList<ExecutorTask>();
	private final Object monitor = new Object();
	private final DistributedJobService<? extends DistributedJobInterface> jobService;
	private Thread thread;
	private boolean running;
	private boolean dirty;

	/**
	 * @param jobService
	 * @param maxJobCount
	 */
	public DistributedServiceProcessor(final DistributedJobService<? extends DistributedJobInterface> jobService, final int maxJobCount) {
		this.jobService = jobService;
	}

	/**
	 * @see net.sf.jame.queue.network.ServiceProcessor#start()
	 */
	public void start() {
		jobService.start();
		if (thread == null) {
			thread = factory.newThread(new ExecutorHandler());
			thread.setName(jobService.getName() + " Executor Thread");
			running = true;
			thread.start();
		}
	}

	/**
	 * @see net.sf.jame.queue.network.ServiceProcessor#stop()
	 */
	public void stop() {
		jobService.stop();
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
	 * @see net.sf.jame.queue.network.ServiceProcessor#createSessionHandler()
	 */
	public SessionHandler createSessionHandler() {
		return new SpoolSessionHandler();
	}

	private class ExecutorHandler implements Runnable {
		/**
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			final long pollingTime = 60 * 1000L;
			final List<ExecutorTask> tasksToRun = new LinkedList<ExecutorTask>();
			try {
				while (running) {
					try {
						synchronized (tasks) {
							tasksToRun.addAll(tasks);
							tasks.clear();
						}
						for (final ExecutorTask task : tasksToRun) {
							task.run();
							Thread.yield();
						}
						tasksToRun.clear();
					}
					catch (final Exception e) {
						e.printStackTrace();
					}
					synchronized (monitor) {
						if (!dirty) {
							monitor.wait(pollingTime);
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

	private class ExecutorTask implements Runnable {
		private final ServiceSession session;
		private final ServiceMessage message;

		/**
		 * @param session
		 * @param message
		 */
		public ExecutorTask(final ServiceSession session, final ServiceMessage message) {
			this.session = session;
			this.message = message;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			try {
				logger.fine("Ready to send message " + message + " on session " + session.getSessionId());
				if (!session.isExpired()) {
					session.sendMessage(message);
				}
				else {
					logger.warning("Failed to send the message " + message + " on session " + session.getSessionId());
					logger.warning("Invalidate session " + session.getSessionId());
					session.invalidate();
				}
			}
			catch (final Exception e) {
				logger.warning("Failed to send the message " + message + " on session " + session.getSessionId());
				logger.warning("Invalidate session " + session.getSessionId());
				session.invalidate();
				e.printStackTrace();
			}
		}
	}

	private class SpoolSessionHandler implements SessionHandler {
		private ServiceSession session;

		/**
		 * @see net.sf.jame.queue.network.SessionHandler#dispose()
		 */
		public void dispose() {
			if (session != null) {
				session.dispose();
				session = null;
			}
		}

		/**
		 * @see net.sf.jame.queue.network.SessionHandler#setSession(net.sf.jame.queue.network.ServiceSession)
		 */
		public void setSession(final ServiceSession session) {
			if (session == null) {
				throw new IllegalArgumentException("session == null");
			}
			this.session = session;
		}

		/**
		 * @see net.sf.jame.queue.network.SessionHandler#getSession()
		 */
		public ServiceSession getSession() {
			return session;
		}

		/**
		 * @see net.sf.jame.queue.network.SessionHandler#isExpired()
		 */
		public boolean isExpired() {
			return (session == null) || session.isExpired();
		}

		/**
		 * @see net.sf.jame.queue.network.SessionHandler#onMessage(net.sf.jame.queue.network.ServiceMessage)
		 */
		public void onMessage(final ServiceMessage message) throws ServiceException {
			if (isExpired()) {
				return;
			}
			try {
				switch (message.getMessageType()) {
					case ServiceMessage.MESSAGE_TYPE_REQUEST: {
						final RequestMessage request = (RequestMessage) message;
						switch (request.getRequestType()) {
							case RequestMessage.TYPE_HELLO: {
								processHelloRequest(request);
								break;
							}
							case RequestMessage.TYPE_PUT: {
								processPutRequest(request);
								break;
							}
							case RequestMessage.TYPE_GET: {
								processGetRequest(request);
								break;
							}
							case RequestMessage.TYPE_ABORT: {
								processAbortRequest(request);
								break;
							}
							case RequestMessage.TYPE_DELETE: {
								processDeleteRequest(request);
								break;
							}
							default: {
								break;
							}
						}
						break;
					}
					case ServiceMessage.MESSAGE_TYPE_RESPONSE: {
						break;
					}
					case ServiceMessage.MESSAGE_TYPE_EVENT: {
						break;
					}
					default: {
						break;
					}
				}
			}
			catch (final Exception e) {
				e.printStackTrace();
			}
			synchronized (monitor) {
				dirty = true;
				monitor.notify();
			}
		}

		private void processDeleteRequest(final RequestMessage request) throws Exception {
			final String jobId = (String) request.getUserData();
			jobService.deleteJob(jobId);
			synchronized (tasks) {
				final ResponseMessage response = createDeleteResponse(request, jobId);
				tasks.add(new ExecutorTask(session, response));
			}
		}

		private void processAbortRequest(final RequestMessage request) throws Exception {
			final String jobId = (String) request.getUserData();
			jobService.abortJob(jobId);
			jobService.stopJob(jobId);
			synchronized (tasks) {
				final ResponseMessage response = createAbortResponse(request, jobId);
				tasks.add(new ExecutorTask(session, response));
			}
		}

		private void processGetRequest(final RequestMessage request) throws Exception {
			final String jobId = (String) ((Object[]) request.getUserData())[0];
			final int frameNumber = (Integer) ((Object[]) request.getUserData())[1];
			byte[] frameData = jobService.getJobFrame(jobId, frameNumber);
			synchronized (tasks) {
				final ResponseMessage response = createGetResponse(request, jobId, frameNumber, frameData);
				tasks.add(new ExecutorTask(session, response));
			}
		}

		private void processPutRequest(final RequestMessage request) throws Exception {
			final String jobId = (String) ((Object[]) request.getUserData())[0];
			final int frameNumber = (Integer) ((Object[]) request.getUserData())[1];
			final byte[] data = (byte[]) ((Object[]) request.getUserData())[2];
			final DistributedJobDecoder decoder = new DistributedJobDecoder(data);
			jobService.setJobData(jobId, decoder.getJobData(), frameNumber);
			jobService.setJobFrame(jobId, decoder.getClip(), decoder.getFrameData());
			jobService.runJob(jobId);
			synchronized (tasks) {
				final ResponseMessage response = createPutResponse(request, jobId);
				tasks.add(new ExecutorTask(session, response));
			}
		}

		private void processHelloRequest(final RequestMessage request) throws Exception {
			final String jobId = jobService.createJob(new SpoolJobListener(session));
			int jobCount = jobService.getJobCount();
			if (jobId != null) {
				synchronized (tasks) {
					final ResponseMessage response = createHelloResponse(request, jobCount, jobId);
					tasks.add(new ExecutorTask(session, response));
				}
			}
			else {
				synchronized (tasks) {
					final ResponseMessage response = createHelloResponse(request, jobCount, null);
					tasks.add(new ExecutorTask(session, response));
				}
			}
		}

		private ResponseMessage createHelloResponse(final RequestMessage request, final Integer jobCount, final String jobId) throws Exception {
			final ResponseMessage message = new ResponseMessage();
			message.setRequestId(request.getRequestId());
			message.setResponseType(RequestMessage.TYPE_HELLO);
			if (jobId != null) {
				message.setUserData(new Object[] { jobCount, jobId });
				message.setReturnCode(0);
			}
			else {
				message.setUserData(new Object[] { jobCount, null });
				message.setReturnCode(1);
			}
			return message;
		}

		private ResponseMessage createPutResponse(final RequestMessage request, final String jobId) throws Exception {
			final ResponseMessage message = new ResponseMessage();
			message.setRequestId(request.getRequestId());
			message.setResponseType(RequestMessage.TYPE_PUT);
			if (jobId != null) {
				message.setUserData(jobId);
				message.setReturnCode(0);
			}
			else {
				message.setReturnCode(1);
			}
			return message;
		}

		private ResponseMessage createGetResponse(final RequestMessage request, final String jobId, final int frameNumber, final byte[] frameData) throws Exception {
			final ResponseMessage message = new ResponseMessage();
			message.setRequestId(request.getRequestId());
			message.setResponseType(RequestMessage.TYPE_GET);
			if ((jobId != null) && (frameData != null)) {
				message.setUserData(new Object[] { jobId, frameNumber, frameData });
				message.setReturnCode(0);
			}
			else {
				message.setReturnCode(1);
			}
			return message;
		}

		private ResponseMessage createAbortResponse(final RequestMessage request, final String jobId) throws Exception {
			final ResponseMessage message = new ResponseMessage();
			message.setRequestId(request.getRequestId());
			message.setResponseType(RequestMessage.TYPE_ABORT);
			if (jobId != null) {
				message.setUserData(jobId);
				message.setReturnCode(0);
			}
			else {
				message.setReturnCode(1);
			}
			return message;
		}

		private ResponseMessage createDeleteResponse(final RequestMessage request, final String jobId) throws Exception {
			final ResponseMessage message = new ResponseMessage();
			message.setRequestId(request.getRequestId());
			message.setResponseType(RequestMessage.TYPE_DELETE);
			if (jobId != null) {
				message.setUserData(jobId);
				message.setReturnCode(0);
			}
			else {
				message.setReturnCode(1);
			}
			return message;
		}
	}

	private class SpoolJobListener implements JobListener {
		private final ServiceSession session;

		/**
		 * @param session
		 */
		public SpoolJobListener(final ServiceSession session) {
			this.session = session;
		}

		/**
		 * @see net.sf.jame.queue.spool.JobListener#stateChanged(String, JobData)
		 */
		public void updated(final String jobId, final JobData job) {
			if (session.isExpired()) {
				return;
			}
			processUpdated(jobId, job);
		}

		/**
		 * @see net.sf.jame.queue.spool.JobListener#started(String, JobData)
		 */
		public void started(final String jobId, final JobData job) {
			if (session.isExpired()) {
				return;
			}
			processStarted(jobId, job);
		}

		/**
		 * @see net.sf.jame.queue.spool.JobListener#stopped(String, JobData)
		 */
		public void stopped(final String jobId, final JobData job) {
			if (session.isExpired()) {
				return;
			}
			processStopped(jobId, job);
		}

		/**
		 * @see net.sf.jame.queue.spool.JobListener#terminated(String, JobData)
		 */
		public void terminated(final String jobId, final JobData job) {
			if (session.isExpired()) {
				return;
			}
			processTerminated(jobId, job);
		}

		/**
		 * @see net.sf.jame.queue.spool.JobListener#disposed(String, JobData)
		 */
		public void disposed(final String jobId, final JobData job) {
			if (session.isExpired()) {
				return;
			}
			processDisposed(jobId, job);
		}

		private void processUpdated(final String jobId, final JobData job) {
			int jobCount = jobService.getJobCount();
			synchronized (tasks) {
				try {
					final EventMessage message = createFrameMessage(jobId, job, jobCount);
					tasks.add(new ExecutorTask(session, message));
				}
				catch (final Exception e) {
					e.printStackTrace();
				}
			}
			synchronized (monitor) {
				dirty = true;
				monitor.notify();
			}
		}

		private void processStarted(final String jobId, final JobData job) {
			int jobCount = jobService.getJobCount();
			synchronized (tasks) {
				try {
					final EventMessage message = createStartMessage(jobId, job, jobCount);
					tasks.add(new ExecutorTask(session, message));
				}
				catch (final Exception e) {
					e.printStackTrace();
				}
			}
			synchronized (monitor) {
				dirty = true;
				monitor.notify();
			}
		}

		private void processStopped(final String jobId, final JobData job) {
			int jobCount = jobService.getJobCount();
			synchronized (tasks) {
				try {
					final EventMessage message = createStopMessage(jobId, job, jobCount);
					tasks.add(new ExecutorTask(session, message));
				}
				catch (final Exception e) {
					e.printStackTrace();
				}
			}
			synchronized (monitor) {
				dirty = true;
				monitor.notify();
			}
		}

		private void processTerminated(final String jobId, final JobData job) {
			int jobCount = jobService.getJobCount();
			synchronized (tasks) {
				try {
					final EventMessage message = createDoneMessage(jobId, job, jobCount);
					tasks.add(new ExecutorTask(session, message));
				}
				catch (final Exception e) {
					e.printStackTrace();
				}
			}
			synchronized (monitor) {
				dirty = true;
				monitor.notify();
			}
		}

		private void processDisposed(final String jobId, final JobData job) {
			synchronized (monitor) {
				dirty = true;
				monitor.notify();
			}
		}

		private EventMessage createFrameMessage(final String jobId, final JobData job, final int jobCount) throws Exception {
			final EventMessage message = new EventMessage();
			message.setUserData(new JobEvent(JobEvent.EVENT_TYPE_FRAME, new JobStatus(jobId, job.getFrameNumber(), jobCount)));
			return message;
		}

		private EventMessage createStartMessage(final String jobId, final JobData job, final int jobCount) throws Exception {
			final EventMessage message = new EventMessage();
			message.setUserData(new JobEvent(JobEvent.EVENT_TYPE_BEGIN, new JobStatus(jobId, job.getFrameNumber(), jobCount)));
			return message;
		}

		private EventMessage createStopMessage(final String jobId, final JobData job, final int jobCount) throws Exception {
			final EventMessage message = new EventMessage();
			message.setUserData(new JobEvent(JobEvent.EVENT_TYPE_END, new JobStatus(jobId, job.getFrameNumber(), jobCount)));
			return message;
		}

		private EventMessage createDoneMessage(final String jobId, final JobData job, final int jobCount) throws Exception {
			final EventMessage message = new EventMessage();
			message.setUserData(new JobEvent(JobEvent.EVENT_TYPE_DONE, new JobStatus(jobId, job.getFrameNumber(), jobCount)));
			return message;
		}
	}
}
