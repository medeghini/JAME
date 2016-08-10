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
package net.sf.jame.core.swing.media;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;

import net.sf.jame.core.media.EngineException;
import net.sf.jame.core.media.Movie;
import net.sf.jame.core.swing.media.renderer.MovieRenderer;
import net.sf.jame.core.swing.media.renderer.RenderingCanvas;

/**
 * @author Andrea Medeghini
 */
public final class MovieFrame extends Frame {
	private static final long serialVersionUID = 1L;
	private GraphicsEnvironment environment;
	private GraphicsDevice device;
	private MovieRenderer renderer;
	private Color color = Color.white;
	private boolean undecorated = false;
	private boolean resizable = false;
	private boolean debug = false;
	private boolean loop = false;

	/**
	 * @param movie
	 * @param title
	 */
	public MovieFrame(final Movie movie, final String title) {
		try {
			environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
			if (GraphicsEnvironment.isHeadless()) {
				throw new EngineException("Graphics environment is headless!");
			}
			device = environment.getDefaultScreenDevice();
			if (device.getType() != GraphicsDevice.TYPE_RASTER_SCREEN) {
				throw new EngineException("Graphics device not found!");
			}
			loadProperties();
			setTitle(title);
			setBackground(color);
			setForeground(color);
			setIgnoreRepaint(false);
			setResizable(resizable);
			setUndecorated(undecorated);
			setCursor(new Cursor(Cursor.HAND_CURSOR));
			DefaultMovieContext movieContext = new DefaultMovieContext();
			MovieRenderer renderer = new MovieRenderer(movieContext, movie);
			RenderingCanvas canvas = new RenderingCanvas(renderer);
			movieContext.setColor(getBackground());
			movieContext.setDebug(debug);
			movieContext.setLoop(loop);
			movieContext.setCanvas(canvas);
			setLayout(new BorderLayout());
			add(canvas, BorderLayout.CENTER);
			pack();
			addWindowListener(new DefaultWindowAdapter());
			canvas.addKeyListener(new DefaultKeyAdapter());
			canvas.requestFocus();
			final Dimension size = getSize();
			final Point center = environment.getCenterPoint();
			setLocation(center.x - (size.width / 2), center.y - (size.height / 2));
			setVisible(true);
			renderer = canvas.getRenderer();
			movie.setSize(canvas.getSize());
			movie.setCenter(new Point2D.Double(-canvas.getSize().getWidth() / 2, -canvas.getSize().getHeight() / 2));
			renderer.init();
		}
		catch (final EngineException e) {
			e.printStackTrace();
		}
	}

	private void loadProperties() {
		if (System.getProperty("undecorated", "false").toLowerCase().equals("true")) {
			undecorated = true;
		}
		if (System.getProperty("resizable", "false").toLowerCase().equals("true")) {
			resizable = true;
		}
		if (System.getProperty("debug", "false").toLowerCase().equals("true")) {
			debug = true;
		}
		if (System.getProperty("loop", "false").toLowerCase().equals("true")) {
			loop = true;
		}
		try {
			color = new Color(Integer.parseInt(System.getProperty("color", "#FFFFFF").substring(1), 16));
		}
		catch (final NumberFormatException e) {
		}
	}

	private class DefaultKeyAdapter extends KeyAdapter {
		public DefaultKeyAdapter() {
		}

		@Override
		public void keyPressed(final KeyEvent e) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_ESCAPE: {
					renderer.dispose();
					break;
				}
				default:
					break;
			}
		}
	}

	private class DefaultWindowAdapter extends WindowAdapter {
		public DefaultWindowAdapter() {
		}

		@Override
		public void windowClosing(final WindowEvent e) {
			renderer.dispose();
		}
	}
}
