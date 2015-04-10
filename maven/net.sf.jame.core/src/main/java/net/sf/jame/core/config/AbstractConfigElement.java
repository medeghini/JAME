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
package net.sf.jame.core.config;

import java.util.LinkedList;
import java.util.List;

/**
 * Abstract implementation of a configuration element.
 * 
 * @author Andrea Medeghini
 */
public abstract class AbstractConfigElement implements ConfigElement {
	private static final long serialVersionUID = 1L;
	private transient List<ValueChangeListener> listenerList;
	private transient ConfigContext context;
	private transient Object userData;
	private String classId;

	/**
	 * Constructs a new element.
	 * 
	 * @param classId the classId.
	 */
	protected AbstractConfigElement(final String classId) {
		this.classId = classId;
	}

	/**
	 * @throws Throwable
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable {
		dispose();
		super.finalize();
	}

	/**
	 * @see net.sf.jame.core.config.ConfigElement#dispose()
	 */
	public void dispose() {
		userData = null;
		classId = null;
		context = null;
		if (listenerList != null) {
			listenerList.clear();
			listenerList = null;
		}
	}

	private List<ValueChangeListener> getListenerList() {
		if (listenerList == null) {
			listenerList = new LinkedList<ValueChangeListener>();
		}
		return listenerList;
	}

	/**
	 * Checks is the context is defined.
	 * 
	 * @return true is the context is defined.
	 */
	protected boolean checkContext() {
		return context != null;
	}

	/**
	 * Returns the configuration context.
	 * 
	 * @return the configuration context.
	 */
	public ConfigContext getContext() {
		if (context == null) {
			throw new IllegalStateException("Context not defined");
		}
		return context;
	}

	/**
	 * Sets the configuration context.
	 * 
	 * @param context the configuration context to set.
	 */
	public void setContext(final ConfigContext context) {
		if ((this.context != null) && (this.context != context)) {
			throw new IllegalStateException("Context already defined");
		}
		this.context = context;
	}

	/**
	 * Returns the classId.
	 * 
	 * @return the classId.
	 */
	public String getClassId() {
		return classId;
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public abstract ConfigElement clone();

	/**
	 * @see net.sf.jame.core.config.ConfigElement#addChangeListener(net.sf.jame.core.config.ValueChangeListener)
	 */
	public void addChangeListener(final ValueChangeListener listener) {
		getListenerList().add(listener);
	}

	/**
	 * @see net.sf.jame.core.config.ConfigElement#removeChangeListener(net.sf.jame.core.config.ValueChangeListener)
	 */
	public void removeChangeListener(final ValueChangeListener listener) {
		getListenerList().remove(listener);
	}

	/**
	 * Fires a new event.
	 * 
	 * @param e the event to fire.
	 */
	protected void fireConfigChanged(final ValueChangeEvent e) {
		final List<ValueChangeListener> listeners = getListenerList();
		for (final ValueChangeListener listener : listeners) {
			listener.valueChanged(e);
		}
	}

	/**
	 * @see net.sf.jame.core.config.ConfigElement#getUserData()
	 */
	public Object getUserData() {
		return userData;
	}

	/**
	 * @see net.sf.jame.core.config.ConfigElement#setUserData(java.lang.Object)
	 */
	public void setUserData(final Object userData) {
		this.userData = userData;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		return classId;
	}
}
