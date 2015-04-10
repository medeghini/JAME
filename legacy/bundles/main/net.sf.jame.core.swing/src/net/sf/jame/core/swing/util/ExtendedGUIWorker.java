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
package net.sf.jame.core.swing.util;

import java.util.LinkedList;
import java.util.List;

import net.sf.jame.core.util.ProgressListener;

/**
 * @author Andrea Medeghini
 */
public abstract class ExtendedGUIWorker extends DefaultGUIWorker implements ProgressListener {
	/**
	 * @see it.trend.lit.studio.workbench.ProgressListener#failed(java.lang.Exception)
	 */
	public void failed(final Throwable e) {
		for (ProgressListener progressListener : progressListenerList) {
			progressListener.failed(e);
		}
	}

	/**
	 * @see it.trend.lit.studio.workbench.ProgressListener#stateChanged(java.lang.String, float)
	 */
	public void stateChanged(final String message, final int percentage) {
		List<ProgressListener> tmpProgressListenerList = new LinkedList<ProgressListener>(progressListenerList);
		for (ProgressListener progressListener : tmpProgressListenerList) {
			progressListener.stateChanged(message, percentage);
		}
	}

	/**
	 * @see it.trend.lit.studio.workbench.ProgressListener#stateChanged(java.lang.String)
	 */
	public void stateChanged(final String message) {
		List<ProgressListener> tmpProgressListenerList = new LinkedList<ProgressListener>(progressListenerList);
		for (ProgressListener progressListener : tmpProgressListenerList) {
			progressListener.stateChanged(message);
		}
	}
}
