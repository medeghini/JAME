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
package net.sf.jame.queue.spool.job;

import java.awt.RenderingHints;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadFactory;

import net.sf.jame.core.util.Colors;
import net.sf.jame.core.util.DefaultThreadFactory;
import net.sf.jame.core.util.Files;
import net.sf.jame.core.util.Tile;
import net.sf.jame.core.util.IntegerVector2D;
import net.sf.jame.core.util.Surface;
import net.sf.jame.core.util.Worker;
import net.sf.jame.queue.io.ChunkedRandomAccessFile;
import net.sf.jame.queue.spool.DefaultJobData;
import net.sf.jame.queue.spool.DistributedJobInterface;
import net.sf.jame.queue.spool.JobData;
import net.sf.jame.queue.spool.JobListener;
import net.sf.jame.twister.TwisterClip;
import net.sf.jame.twister.TwisterClipController;
import net.sf.jame.twister.TwisterConfig;
import net.sf.jame.twister.TwisterRuntime;
import net.sf.jame.twister.renderer.DefaultTwisterRenderer;
import net.sf.jame.twister.renderer.OverlayTwisterRenderer;
import net.sf.jame.twister.renderer.TwisterRenderer;
import net.sf.jame.twister.renderer.TwisterRenderingHints;

/**
 * @author Andrea Medeghini
 */
public class DistributedJob implements DistributedJobInterface {
	private static final ThreadFactory factory = new DefaultThreadFactory("DistributedJob Task", true, Thread.MIN_PRIORITY);
	private static final int CHUNK_LENGTH = 1024 * 10000;
	private final JobListener listener;
	private final String jobId;
	private volatile long lastUpdate;
	private volatile boolean started;
	private volatile boolean aborted;
	private volatile boolean terminated;
	private volatile JobData jobDataRow;
	private volatile TwisterClip clip;
	private volatile byte[] jobData;
	private volatile Thread thread;
	private int firstFrameNumber;
	private final Worker worker;
	private File tmpPath;

	/**
	 * @param tmpDir
	 * @param worker
	 * @param jobId
	 * @param listener
	 */
	public DistributedJob(final File tmpDir, final Worker worker, final String jobId, final JobListener listener) {
		lastUpdate = System.currentTimeMillis();
		this.listener = listener;
		this.worker = worker;
		this.jobId = jobId;
		tmpPath = new File(tmpDir, jobId);
		tmpPath.mkdirs();
	}

	/**
	 * @see net.sf.jame.queue.spool.JobInterface#getJobId()
	 */
	public String getJobId() {
		return jobId;
	}

	/**
	 * @see net.sf.jame.queue.spool.JobInterface#getFrameNumber()
	 */
	public synchronized int getFrameNumber() {
		if (jobDataRow == null) {
			throw new IllegalStateException();
		}
		return jobDataRow.getFrameNumber();
	}

	private void setFrameNumber(final int frameNumber) {
		if (jobDataRow == null) {
			throw new IllegalStateException();
		}
		jobDataRow.setFrameNumber(frameNumber);
		lastUpdate = System.currentTimeMillis();
	}

	/**
	 * @see net.sf.jame.queue.spool.DistributedJobInterface#setFirstFrameNumber(int)
	 */
	public synchronized void setFirstFrameNumber(final int frameNumber) {
		if (jobDataRow == null) {
			throw new IllegalStateException();
		}
		if (thread != null) {
			throw new IllegalStateException();
		}
		firstFrameNumber = frameNumber;
		jobDataRow.setFrameNumber(frameNumber);
	}

	/**
	 * @see net.sf.jame.queue.spool.DistributedJobInterface#getFirstFrameNumber()
	 */
	public synchronized int getFirstFrameNumber() {
		return firstFrameNumber;
	}

	/**
	 * @see net.sf.jame.queue.spool.DistributedJobInterface#getClip()
	 */
	public synchronized TwisterClip getClip() {
		return clip;
	}

	/**
	 * @see net.sf.jame.queue.spool.DistributedJobInterface#getJobDataRow()
	 */
	public synchronized JobData getJobDataRow() {
		return jobDataRow;
	}

	/**
	 * @see net.sf.jame.queue.spool.DistributedJobInterface#getJobData()
	 */
	public synchronized byte[] getJobData() {
		return jobData;
	}

	/**
	 * @see net.sf.jame.queue.spool.DistributedJobInterface#setClip(net.sf.jame.twister.TwisterClip)
	 */
	public synchronized void setClip(final TwisterClip clip) {
		if (thread != null) {
			throw new IllegalStateException();
		}
		this.clip = clip;
	}

	/**
	 * @see net.sf.jame.queue.spool.DistributedJobInterface#setJobDataRow(JobData)
	 */
	public synchronized void setJobDataRow(final JobData jobDataRow) {
		if (thread != null) {
			throw new IllegalStateException();
		}
		if (jobDataRow == null) {
			throw new IllegalArgumentException();
		}
		this.jobDataRow = jobDataRow;
	}

	/**
	 * @see net.sf.jame.queue.spool.DistributedJobInterface#setJobData(byte[])
	 */
	public synchronized void setJobData(final byte[] jobData) {
		if (thread != null) {
			throw new IllegalStateException();
		}
		this.jobData = jobData;
	}

	/**
	 * @see net.sf.jame.queue.spool.DistributedJobInterface#getRAF()
	 */
	public synchronized ChunkedRandomAccessFile getRAF() throws IOException {
		return new ChunkedRandomAccessFile(tmpPath, "", ".bin", CHUNK_LENGTH);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public synchronized String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("id = ");
		builder.append(jobId);
		builder.append(", frameNumber = ");
		builder.append(jobDataRow != null ? jobDataRow.getFrameNumber() : "N/A");
		return builder.toString();
	}

	/**
	 * @see net.sf.jame.queue.spool.JobInterface#getLastUpdate()
	 */
	public synchronized long getLastUpdate() {
		return lastUpdate;
	}

	/**
	 * @see net.sf.jame.queue.spool.JobInterface#reset()
	 */
	public void reset() {
		Thread tmpThread = thread;
		if (tmpThread != null) {
			tmpThread.interrupt();
			try {
				tmpThread.join();
			}
			catch (final InterruptedException e) {
			}
		}
		synchronized (this) {
			started = false;
			aborted = false;
			terminated = false;
			lastUpdate = System.currentTimeMillis();
		}
	}

	/**
	 * @see net.sf.jame.queue.spool.JobInterface#start()
	 */
	public void start() {
		synchronized (this) {
			if (jobDataRow == null) {
				throw new IllegalStateException();
			}
			if (thread == null) {
				lastUpdate = System.currentTimeMillis();
				started = true;
				aborted = false;
				terminated = false;
				worker.addTask(new StartedTask(new DefaultJobData(jobDataRow)));
				thread = factory.newThread(new RenderTask());
				thread.start();
			}
		}
	}

	/**
	 * @see net.sf.jame.queue.spool.JobInterface#stop()
	 */
	public void stop() {
		Thread tmpThread = thread;
		if (tmpThread != null) {
			tmpThread.interrupt();
			try {
				tmpThread.join();
			}
			catch (final InterruptedException e) {
			}
		}
		synchronized (this) {
			if (jobDataRow == null) {
				throw new IllegalStateException();
			}
			started = false;
			thread = null;
			worker.addTask(new StoppedTask(new DefaultJobData(jobDataRow)));
		}
	}

	/**
	 * @see net.sf.jame.queue.spool.JobInterface#abort()
	 */
	public synchronized void abort() {
		aborted = true;
	}

	/**
	 * @see net.sf.jame.queue.spool.JobInterface#dispose()
	 */
	public synchronized void dispose() {
		if (tmpPath != null) {
			Files.deleteFiles(tmpPath);
			tmpPath.delete();
			tmpPath = null;
		}
		if (jobDataRow != null) {
			worker.addTask(new DisposedTask(new DefaultJobData(jobDataRow)));
		}
	}

	/**
	 * @see net.sf.jame.queue.spool.JobInterface#isStarted()
	 */
	public synchronized boolean isStarted() {
		return started;
	}

	/**
	 * @see net.sf.jame.queue.spool.JobInterface#isAborted()
	 */
	public synchronized boolean isAborted() {
		return aborted;
	}

	/**
	 * @see net.sf.jame.queue.spool.JobInterface#isTerminated()
	 */
	public synchronized boolean isTerminated() {
		return terminated;
	}

	private class RenderTask implements Runnable {
		/**
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			Surface surface = null;
			TwisterRuntime runtime = null;
			TwisterRenderer renderer = null;
			TwisterRuntime overlayRuntime = null;
			TwisterRenderer overlayRenderer = null;
			ChunkedRandomAccessFile jobRaf = null;
			try {
				if (clip.getSequenceCount() > 0) {
					final int frameCount = (jobDataRow.getStopTime() - jobDataRow.getStartTime()) * jobDataRow.getFrameRate();
					int frameTimeInMillis = 0;
					final int tx = jobDataRow.getTileOffsetX();
					final int ty = jobDataRow.getTileOffsetY();
					final int tw = jobDataRow.getTileWidth();
					final int th = jobDataRow.getTileHeight();
					final int iw = jobDataRow.getImageWidth();
					final int ih = jobDataRow.getImageHeight();
					final int bw = jobDataRow.getBorderWidth();
					final int bh = jobDataRow.getBorderHeight();
					final int sw = tw + 2 * bw;
					final int sh = th + 2 * bh;
					if ((jobDataRow.getFrameRate() == 0) || (frameCount == 0)) {
						TwisterConfig config = clip.getSequence(0).getInitialConfig();
						if (config == null) {
							config = clip.getSequence(0).getFinalConfig();
						}
						if (config != null) {
							surface = new Surface(sw, sh);
							surface.getGraphics2D().setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
							surface.getGraphics2D().setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
							surface.getGraphics2D().setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
							surface.getGraphics2D().setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
							runtime = new TwisterRuntime(config);
							renderer = new DefaultTwisterRenderer(runtime);
							final Map<Object, Object> hints = new HashMap<Object, Object>();
							hints.put(TwisterRenderingHints.KEY_MEMORY, TwisterRenderingHints.MEMORY_LOW);
							renderer.setRenderingHints(hints);
							renderer.setTile(new Tile(new IntegerVector2D(iw, ih), new IntegerVector2D(tw, th), new IntegerVector2D(tx, ty), new IntegerVector2D(bw, bh)));
							overlayRuntime = new TwisterRuntime(config);
							overlayRenderer = new OverlayTwisterRenderer(overlayRuntime);
							final Map<Object, Object> overlayHints = new HashMap<Object, Object>();
							overlayHints.put(TwisterRenderingHints.KEY_MEMORY, TwisterRenderingHints.MEMORY_LOW);
							overlayHints.put(TwisterRenderingHints.KEY_TYPE, TwisterRenderingHints.TYPE_OVERLAY);
							overlayRenderer.setRenderingHints(overlayHints);
							overlayRenderer.setTile(new Tile(new IntegerVector2D(iw, ih), new IntegerVector2D(tw, th), new IntegerVector2D(tx, ty), new IntegerVector2D(0, 0)));
							renderer.prepareImage(false);
							overlayRenderer.prepareImage(false);
							renderer.render();
							overlayRenderer.render();
							renderer.drawSurface(surface.getGraphics2D());
							overlayRenderer.drawSurface(surface.getGraphics2D());
							jobRaf = getRAF();
							final byte[] row = new byte[sw * 4];
							final int[] data = ((DataBufferInt) surface.getImage().getRaster().getDataBuffer()).getData();
							for (int j = 0, k = 0; k < sh; k++) {
								for (int i = 0; i < row.length; i += 4) {
									row[i + 0] = (byte) ((data[j] & 0x00FF0000) >> 16);
									row[i + 1] = (byte) ((data[j] & 0x0000FF00) >> 8);
									row[i + 2] = (byte) ((data[j] & 0x000000FF) >> 0);
									row[i + 3] = (byte) ((data[j] & 0xFF000000) >> 24);
									j += 1;
								}
								jobRaf.write(row);
								Thread.yield();
							}
							setFrameNumber(0);
							worker.addTask(new StatusChangedTask(new DefaultJobData(jobDataRow)));
						}
					}
					else if (jobDataRow.getFrameNumber() < frameCount) {
						surface = new Surface(sw, sh);
						surface.getGraphics2D().setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
						surface.getGraphics2D().setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
						surface.getGraphics2D().setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						surface.getGraphics2D().setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
						final TwisterClipController controller = new TwisterClipController(clip);
						controller.init();
						final TwisterConfig config = controller.getConfig();
						runtime = new TwisterRuntime(config);
						renderer = new DefaultTwisterRenderer(runtime);
						final Map<Object, Object> hints = new HashMap<Object, Object>();
						hints.put(TwisterRenderingHints.KEY_MEMORY, TwisterRenderingHints.MEMORY_LOW);
						renderer.setRenderingHints(hints);
						renderer.setTile(new Tile(new IntegerVector2D(iw, ih), new IntegerVector2D(tw, th), new IntegerVector2D(tx, ty), new IntegerVector2D(bw, bh)));
						jobRaf = getRAF();
						final byte[] row = new byte[sw * 4];
						final int[] data = ((DataBufferInt) surface.getImage().getRaster().getDataBuffer()).getData();
						int startFrameNumber = 0;
						if ((jobDataRow.getFrameNumber() > 0) && (jobData != null)) {
							for (int j = 0, k = 0; k < sh; k++) {
								System.arraycopy(jobData, k * sw * 4, row, 0, row.length);
								for (int i = 0; i < row.length; i += 4) {
									final int argb = Colors.color(row[i + 3], row[i + 0], row[i + 1], row[i + 2]);
									data[j] = argb;
									j += 1;
								}
							}
							startFrameNumber = jobDataRow.getFrameNumber() + 1;
							frameTimeInMillis = jobDataRow.getStartTime() * 1000 + (jobDataRow.getFrameNumber() * 1000) / jobDataRow.getFrameRate();
							controller.redoAction(frameTimeInMillis, false);
							renderer.prepareImage(false);
							renderer.render();
							renderer.loadSurface(surface);
						}
						for (int frameNumber = startFrameNumber; frameNumber < frameCount; frameNumber++) {
							frameTimeInMillis = jobDataRow.getStartTime() * 1000 + (frameNumber * 1000) / jobDataRow.getFrameRate();
							controller.redoAction(frameTimeInMillis, false);
							renderer.prepareImage(false);
							renderer.render();
							renderer.drawSurface(surface.getGraphics2D());
							for (int j = 0, k = 0; k < sh; k++) {
								for (int i = 0; i < row.length; i += 4) {
									row[i + 0] = (byte) ((data[j] & 0x00FF0000) >> 16);
									row[i + 1] = (byte) ((data[j] & 0x0000FF00) >> 8);
									row[i + 2] = (byte) ((data[j] & 0x000000FF) >> 0);
									row[i + 3] = (byte) ((data[j] & 0xFF000000) >> 24);
									j += 1;
								}
								jobRaf.write(row);
								Thread.yield();
							}
							setFrameNumber(frameNumber);
							worker.addTask(new StatusChangedTask(new DefaultJobData(jobDataRow)));
							if ((((frameNumber + 1) % MAX_FRAMES) == 0) && ((frameCount - frameNumber - 1) > (MAX_FRAMES / 2))) {
								break;
							}
							if (aborted) {
								break;
							}
						}
					}
				}
			}
			catch (final InterruptedException e) {
				aborted = true;
			}
			catch (final Throwable e) {
				aborted = true;
				e.printStackTrace();
			}
			finally {
				if (overlayRenderer != null) {
					overlayRenderer.dispose();
					overlayRenderer = null;
				}
				if (overlayRuntime != null) {
					overlayRuntime.dispose();
					overlayRuntime = null;
				}
				if (renderer != null) {
					renderer.dispose();
					renderer = null;
				}
				if (runtime != null) {
					runtime.dispose();
					runtime = null;
				}
				if (surface != null) {
					surface.dispose();
					surface = null;
				}
				if (jobRaf != null) {
					try {
						jobRaf.close();
					}
					catch (final IOException e) {
					}
				}
			}
			synchronized (DistributedJob.this) {
				if (!aborted) {
					terminated = true;
				}
				if (terminated) {
					worker.addTask(new TerminatedTask(new DefaultJobData(jobDataRow)));
				}
			}
		}
	}

	private class StatusChangedTask implements Runnable {
		private final JobData jobData;

		/**
		 * @param jobData
		 */
		protected StatusChangedTask(final JobData jobData) {
			this.jobData = jobData;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			listener.updated(jobId, jobData);
		}
	}

	private class StartedTask implements Runnable {
		private final JobData jobData;

		/**
		 * @param jobData
		 */
		protected StartedTask(final JobData jobData) {
			this.jobData = jobData;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			listener.started(jobId, jobData);
		}
	}

	private class StoppedTask implements Runnable {
		private final JobData jobData;

		/**
		 * @param jobData
		 */
		protected StoppedTask(final JobData jobData) {
			this.jobData = jobData;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			listener.stopped(jobId, jobData);
		}
	}

	private class TerminatedTask implements Runnable {
		private final JobData jobData;

		/**
		 * @param jobData
		 */
		protected TerminatedTask(final JobData jobData) {
			this.jobData = jobData;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			listener.terminated(jobId, jobData);
		}
	}

	private class DisposedTask implements Runnable {
		private final JobData jobData;

		/**
		 * @param jobData
		 */
		protected DisposedTask(final JobData jobData) {
			this.jobData = jobData;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			listener.disposed(jobId, jobData);
		}
	}
}
