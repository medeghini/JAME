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
package net.sf.jame.twister.swing;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import net.sf.jame.core.Application;
import net.sf.jame.core.ApplicationContext;
import net.sf.jame.core.launcher.LauncherContextListener;
import net.sf.jame.core.launcher.LauncherThreadFactory;
import net.sf.jame.core.swing.launcher.Launcher;


/**
 * Application implementation.
 * 
 * @author Andrea Medeghini
 */
public class JAME implements Application {
	private static final List<JFrame> frames = new ArrayList<JFrame>(1);
	private final Launcher<DefaultTwisterContext> launcher = new Launcher<DefaultTwisterContext>(new DefaultTwisterContext(), new DefaultLauncherThreadFactory());
	private LauncherContextListener listener;

	/**
	 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
	 */
	public Object start(final ApplicationContext context) throws Exception {
		boolean restart = false;
		Integer exit = Application.EXIT_OK; 
		try {
			launcher.init();
			launcher.start();
			if (listener != null) {
				listener.started();
			}
			context.applicationRunning();
			restart = launcher.dispatch();
			if (listener != null) {
				listener.stopped();
			}
			launcher.dispose();
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
		if (restart) {
			exit = Application.EXIT_RESTART;
		}
		return exit;
	}

	/**
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	public void stop() {
		launcher.stop();
	}

	private class DefaultTwisterContext implements TwisterContext {
		/**
		 * @see net.sf.jame.SwingContext.swing.TwisterContext#addFrame(javax.swing.JFrame)
		 */
		public void addFrame(final JFrame frame) {
			JAME.frames.add(frame);
		}

		/**
		 * @see net.sf.jame.SwingContext.swing.TwisterContext#removeFrame(javax.swing.JFrame)
		 */
		public void removeFrame(final JFrame frame) {
			JAME.frames.remove(frame);
		}

		/**
		 * @see net.sf.jame.SwingContext.swing.TwisterContext#getFrameCount()
		 */
		public int getFrameCount() {
			return JAME.frames.size();
		}

		/**
		 * @see net.sf.jame.launcher.LauncherContext#exit()
		 */
		public void exit() {
			launcher.stop();
		}

		/**
		 * @see net.sf.jame.launcher.LauncherContext#restart()
		 */
		public void restart() {
			launcher.restart();
		}

		/**
		 * @see net.sf.jame.launcher.LauncherContext#setContextListener(net.sf.jame.launcher.LauncherContextListener)
		 */
		public void setContextListener(final LauncherContextListener listener) {
			JAME.this.listener = listener;
		}
	}

	private class DefaultLauncherThreadFactory implements LauncherThreadFactory<DefaultTwisterContext> {
		/**
		 * @see net.sf.jame.launcher.LauncherThreadFactory#createThread(net.sf.jame.launcher.LauncherContext)
		 */
		public Thread createThread(final DefaultTwisterContext context) {
			return new TwisterLauncherThread(context);
		}
	}
}
