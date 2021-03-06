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
package net.sf.jame.twister.swing.inputAdapter;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import net.sf.jame.core.extension.ExtensionConfig;
import net.sf.jame.core.util.RenderContext;
import net.sf.jame.twister.swing.inputAdapter.extension.InputAdapterExtensionRuntime;
import net.sf.jame.twister.util.AdapterContext;
import net.sf.jame.twister.util.DefaultAdapterContext;

public class DefaultInputAdapterRuntime extends InputAdapterExtensionRuntime {
	private AdapterContext adapterContext;
	private RenderContext renderContext;

	/**
	 * @see net.sf.jame.twister.swing.inputAdapter.extension.InputAdapterExtensionRuntime#init(net.sf.jame.core.util.RenderContext, net.sf.jame.core.extension.ExtensionConfig)
	 */
	@Override
	public void init(final RenderContext renderContext, final ExtensionConfig config) {
		adapterContext = new DefaultAdapterContext(config);
		this.renderContext = renderContext;
	}

	/**
	 * @see net.sf.jame.twister.swing.inputAdapter.extension.InputAdapterExtensionRuntime#getRenderContext()
	 */
	@Override
	public RenderContext getRenderContext() {
		return renderContext;
	}

	/**
	 * @see net.sf.jame.twister.swing.inputAdapter.extension.InputAdapterExtensionRuntime#getAdapterContext()
	 */
	@Override
	public AdapterContext getAdapterContext() {
		return adapterContext;
	}

	/**
	 * @see net.sf.jame.twister.swing.inputAdapter.extension.InputAdapterExtensionRuntime#processKeyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void processKeyPressed(final KeyEvent e) {
	}

	/**
	 * @see net.sf.jame.twister.swing.inputAdapter.extension.InputAdapterExtensionRuntime#processKeyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void processKeyReleased(final KeyEvent e) {
	}

	/**
	 * @see net.sf.jame.twister.swing.inputAdapter.extension.InputAdapterExtensionRuntime#processKeyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void processKeyTyped(final KeyEvent e) {
	}

	/**
	 * @see net.sf.jame.twister.swing.inputAdapter.extension.InputAdapterExtensionRuntime#processMouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void processMouseClicked(final MouseEvent e) {
	}

	/**
	 * @see net.sf.jame.twister.swing.inputAdapter.extension.InputAdapterExtensionRuntime#processMouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void processMouseDragged(final MouseEvent e) {
	}

	/**
	 * @see net.sf.jame.twister.swing.inputAdapter.extension.InputAdapterExtensionRuntime#processMouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void processMouseEntered(final MouseEvent e) {
	}

	/**
	 * @see net.sf.jame.twister.swing.inputAdapter.extension.InputAdapterExtensionRuntime#processMouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void processMouseExited(final MouseEvent e) {
	}

	/**
	 * @see net.sf.jame.twister.swing.inputAdapter.extension.InputAdapterExtensionRuntime#processMouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void processMouseMoved(final MouseEvent e) {
	}

	/**
	 * @see net.sf.jame.twister.swing.inputAdapter.extension.InputAdapterExtensionRuntime#processMousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void processMousePressed(final MouseEvent e) {
	}

	/**
	 * @see net.sf.jame.twister.swing.inputAdapter.extension.InputAdapterExtensionRuntime#processMouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void processMouseReleased(final MouseEvent e) {
	}

	/**
	 * @see net.sf.jame.twister.swing.inputAdapter.extension.InputAdapterExtensionRuntime#refresh()
	 */
	@Override
	public void refresh() {
	}
}
