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
package net.sf.jame.core.util;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadFactory;

/**
 * @author Andrea Medeghini
 */
public class Worker {
	private final List<Runnable> tasks = new LinkedList<Runnable>();
	private ThreadFactory factory;
	private volatile Thread thread;
	private volatile boolean running;
	private volatile Runnable runnable;

	/**
	 * @param factory
	 */
	public Worker(final ThreadFactory factory) {
		this.factory = factory;
	}

	/**
	 * @param runnable
	 * @return
	 */
	protected Thread createThread(Runnable runnable) {
		return factory.newThread(runnable);
	}

	/**
	 * 
	 */
	public void stop() {
		running = false;
		if (thread != null) {
			abortTasks();
			try {
				thread.join();
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			thread = null;
		}
	}

	/**
	 * 
	 */
	public void start() {
		if (thread == null) {
			thread = createThread(new WorkerRunnable());
			running = true;
			thread.start();
		}
	}

	/**
	 * 
	 */
	public void waitTasks() {
		synchronized (tasks) {
			while ((runnable != null) || (tasks.size() > 0)) {
				try {
					tasks.wait();
				}
				catch (InterruptedException e) {
				}
			}
		}
	}

	/**
	 * 
	 */
	public void terminateTasks() {
		synchronized (tasks) {
			while ((runnable != null) || (tasks.size() > 0)) {
				if (thread != null) {
					thread.interrupt();
				}
				try {
					tasks.wait(1000);
				}
				catch (InterruptedException e) {
				}
			}
		}
	}

	/**
	 * 
	 */
	public void abortTasks() {
		synchronized (tasks) {
			tasks.clear();
			if (thread != null) {
				thread.interrupt();
			}
		}
	}

	/**
	 * 
	 */
	public void resumeTasks() {
		synchronized (tasks) {
			tasks.notify();
		}
	}

	/**
	 * 
	 */
	public void clearTasks() {
		synchronized (tasks) {
			tasks.clear();
		}
	}

	/**
	 * @param task
	 */
	public void addTask(final Runnable task) {
		synchronized (tasks) {
			tasks.add(task);
			tasks.notify();
		}
	}

	/**
	 * @return
	 */
	public int tasksCount() {
		synchronized (tasks) {
			return tasks.size();
		}
	}

	private class WorkerRunnable implements Runnable {
		/**
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			try {
				while (running) {
					Thread.interrupted();
					synchronized (tasks) {
						tasks.notifyAll();
						try {
							while (!Thread.interrupted() && tasks.size() == 0) {
								tasks.wait();
							}
							if (tasks.size() > 0) {
								runnable = tasks.remove(0);
							}
						}
						catch (final InterruptedException e) {
							Thread.currentThread().interrupt();
						}
					}
					try {
						if (runnable != null) {
							runnable.run();
						}
					}
					catch (Throwable e) {
						e.printStackTrace();
					}
					finally {
						runnable = null;
					}
				}
			}
			finally {
				synchronized (tasks) {
					runnable = null;
					tasks.clear();
					tasks.notifyAll();
				}
			}
		}
	}
}
