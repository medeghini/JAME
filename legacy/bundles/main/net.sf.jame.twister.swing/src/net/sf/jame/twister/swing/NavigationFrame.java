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
package net.sf.jame.twister.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Point;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import net.sf.jame.core.common.ConfigurableExtensionReferenceElement;
import net.sf.jame.core.common.ExtensionReferenceElement;
import net.sf.jame.core.config.ValueChangeEvent;
import net.sf.jame.core.config.ValueChangeListener;
import net.sf.jame.core.extension.Extension;
import net.sf.jame.core.extension.ExtensionException;
import net.sf.jame.core.swing.ViewContext;
import net.sf.jame.core.tree.NodeSession;
import net.sf.jame.core.util.RenderContext;
import net.sf.jame.twister.swing.view.NavigatorViewRuntime;
import net.sf.jame.twister.swing.view.extension.ViewExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public class NavigationFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final String CONFIG_FRAME_TITLE = "configFrame.title";
	private static final String CONFIG_FRAME_WIDTH = "configFrame.width";
	private static final String CONFIG_FRAME_MIN_HEIGHT = "configFrame.minHeight";
	private static final String CONFIG_FRAME_MAX_HEIGHT = "configFrame.maxHeight";
	private static final String CONFIG_FRAME_ICON = "configFrame.icon";
	private final NavigationPanel navigationPanel;
	private final NavigationModel model;
	private ViewPanel viewPanel;
	private final ConfigurableExtensionReferenceElement<?> configElement;
	private final ValueChangeListener elementListener;
	private final ViewContext viewContext;
	private final String viewName;

	/**
	 * @param configElement
	 * @param renderContext
	 * @param session
	 * @param viewName
	 * @throws HeadlessException
	 */
	public NavigationFrame(final ConfigurableExtensionReferenceElement<?> configElement, final RenderContext renderContext, final NodeSession session, final String viewName) throws HeadlessException {
		this.configElement = configElement;
		this.viewName = viewName;
		setResizable(false);
		final DefaultNavigationContainer container = new DefaultNavigationContainer();
		model = new NavigationModel(container);
		container.setOpaque(false);
		container.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
		navigationPanel = new NavigationPanel(model);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(navigationPanel, BorderLayout.NORTH);
		getContentPane().add(container, BorderLayout.CENTER);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setTitle(TwisterSwingResources.getInstance().getString(NavigationFrame.CONFIG_FRAME_TITLE) + " - " + viewName);
		viewContext = new DefaultViewContext(model);
		final URL resource = NavigationFrame.class.getClassLoader().getResource(TwisterSwingResources.getInstance().getString(NavigationFrame.CONFIG_FRAME_ICON));
		if (resource != null) {
			setIconImage(getToolkit().createImage(resource));
		}
		elementListener = new ValueChangeListener() {
			public void valueChanged(final ValueChangeEvent e) {
				switch (e.getEventType()) {
					case ExtensionReferenceElement.EXTENSION_REFERENCE_CHANGED: {
						if (viewPanel != null) {
							viewContext.removeComponent(viewPanel);
							viewPanel.dispose();
							viewPanel = null;
						}
						updateViewPanel(configElement, viewContext, renderContext, session);
						if (viewPanel != null) {
							viewContext.setComponent(viewPanel);
						}
						break;
					}
					default: {
						break;
					}
				}
			}
		};
		updateViewPanel(configElement, viewContext, renderContext, session);
		configElement.addChangeListener(elementListener);
		if (viewPanel != null) {
			viewPanel.setVisible(false);
			model.setComponent(viewPanel);
		}
		final Point p = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		p.x -= getWidth() / 2;
		p.y -= getHeight() / 2;
		setLocation(p);
	}

	private void updateViewPanel(final ConfigurableExtensionReferenceElement<?> configElement, final ViewContext viewContext, final RenderContext renderContext, final NodeSession session) {
		if (configElement.getReference() != null) {
			try {
				final Extension<ViewExtensionRuntime> extension = TwisterSwingRegistry.getInstance().getViewExtension(configElement.getReference().getExtensionId());
				viewPanel = extension.createExtensionRuntime().createView(configElement.getReference().getExtensionConfig(), viewContext, renderContext, session);
			}
			catch (final ExtensionException x) {
				viewPanel = new NavigatorViewRuntime().createView(configElement.getReference().getExtensionConfig(), viewContext, renderContext, session);
			}
		}
		else {
			viewPanel = new EmptyViewPanel();
		}
		viewPanel.setName(viewName);
	}

	/**
	 * @see java.awt.Window#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		configElement.removeChangeListener(elementListener);
		if (viewPanel != null) {
			viewPanel.dispose();
			viewPanel = null;
		}
	}

	public void setup() {
		if (viewPanel != null) {
			viewPanel.setVisible(true);
		}
	}

	private class DefaultNavigationContainer extends JScrollPane implements NavigationContainer {
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		public DefaultNavigationContainer() {
			setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		}

		/**
		 * @see net.sf.jame.twister.swing.NavigationContainer#loadComponent(java.awt.Component, int)
		 */
		public void loadComponent(final Component c, final int amount) {
			final int defaultWidth = Integer.parseInt(TwisterSwingResources.getInstance().getString(NavigationFrame.CONFIG_FRAME_WIDTH));
			final int defaultMinHeight = Integer.parseInt(TwisterSwingResources.getInstance().getString(NavigationFrame.CONFIG_FRAME_MIN_HEIGHT));
			final int defaultMaxHeight = Integer.parseInt(TwisterSwingResources.getInstance().getString(NavigationFrame.CONFIG_FRAME_MAX_HEIGHT));
			final int width = Integer.getInteger(NavigationFrame.CONFIG_FRAME_WIDTH, defaultWidth);
			final int minHeight = Integer.getInteger(NavigationFrame.CONFIG_FRAME_MIN_HEIGHT, defaultMinHeight);
			final int maxHeight = Integer.getInteger(NavigationFrame.CONFIG_FRAME_MAX_HEIGHT, defaultMaxHeight);
			final Dimension newSize = c.getPreferredSize();
			final Dimension size = new Dimension(width, 40 + Math.min(Math.max(navigationPanel.getHeight() + newSize.height + getInsets().top + getInsets().bottom + NavigationFrame.this.getInsets().top + NavigationFrame.this.getInsets().bottom + amount, minHeight), maxHeight));
			NavigationFrame.this.setSize(size);
			if (c != null) {
				getViewport().setView(c);
			}
		}
	}

	private class DefaultViewContext implements ViewContext {
		private final NavigationModel model;

		/**
		 * @param model
		 */
		public DefaultViewContext(final NavigationModel model) {
			this.model = model;
		}

		/**
		 * @see net.sf.jame.core.swing.ViewContext#setComponent(java.awt.Component)
		 */
		public void setComponent(final Component c) {
			model.setComponent(c);
		}

		/**
		 * @see net.sf.jame.core.swing.ViewContext#removeComponent(java.awt.Component)
		 */
		public void removeComponent(final Component c) {
			model.removeComponent(c);
		}

		/**
		 * @see net.sf.jame.core.swing.ViewContext#resize()
		 */
		public void resize() {
			model.resize(0);
		}

		/**
		 * @see net.sf.jame.core.swing.ViewContext#resize(int)
		 */
		public void resize(final int amount) {
			model.resize(amount + 20);
		}

		/**
		 * @see net.sf.jame.core.swing.ViewContext#restoreComponent(java.awt.Component)
		 */
		public void restoreComponent(final Component c) {
			model.restoreComponent(c);
		}
	}

	private class EmptyViewPanel extends ViewPanel {
		private static final long serialVersionUID = 1L;

		/**
		 * @see net.sf.jame.twister.swing.ViewPanel#dispose()
		 */
		@Override
		public void dispose() {
		}
	}
}
