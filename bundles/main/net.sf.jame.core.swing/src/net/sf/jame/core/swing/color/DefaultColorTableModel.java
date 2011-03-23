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
package net.sf.jame.core.swing.color;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Andrea Medeghini
 */
public class DefaultColorTableModel implements ColorTableModel {
	private ArrayList<ColorChangeListener> listeners = new ArrayList<ColorChangeListener>();
	private BufferedImage image;
	private int x = 0;
	private int y = 0;

	/**
	 * @param width
	 * @param height
	 * @param horizontal
	 */
	public DefaultColorTableModel(int width, int height, final boolean horizontal) {
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		if (horizontal) {
			width = image.getWidth();
			height = image.getHeight();
		}
		else {
			width = image.getHeight();
			height = image.getWidth();
		}
		final int tw = width;
		final int th = height / 2;
		final int th2 = height - 1;
		final float dh = 1.0f / tw;
		final float ds = 1.0f / th;
		float h = 0;
		float s = 0;
		for (int y = 0; y < th; y++) {
			h = 0;
			for (int x = 0; x < tw; x++) {
				if (horizontal) {
					image.setRGB(x, y, Color.HSBtoRGB(h, s, 1.0f));
				}
				else {
					image.setRGB(y, x, Color.HSBtoRGB(h, s, 1.0f));
				}
				h += dh;
			}
			s += ds;
		}
		h = 0;
		s = 0;
		for (int y = 0; y < th; y++) {
			h = 0;
			for (int x = 0; x < tw; x++) {
				if (horizontal) {
					image.setRGB(x, th2 - y, Color.HSBtoRGB(h, 1.0f, s));
				}
				else {
					image.setRGB(th2 - y, x, Color.HSBtoRGB(h, 1.0f, s));
				}
				h += dh;
			}
			s += ds;
		}
	}

	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable {
		image.flush();
		image = null;
		listeners.clear();
		listeners = null;
		super.finalize();
	}

	/**
	 * @see net.sf.jame.core.swing.color.ColorTableModel#addColorChangeListener(net.sf.jame.core.swing.color.ColorChangeListener)
	 */
	public void addColorChangeListener(final ColorChangeListener listener) {
		listeners.add(listener);
	}

	/**
	 * @see net.sf.jame.core.swing.color.ColorTableModel#removeColorChangeListener(net.sf.jame.core.swing.color.ColorChangeListener)
	 */
	public void removeColorChangeListener(final ColorChangeListener listener) {
		listeners.remove(listener);
	}

	/**
	 * @param e
	 */
	protected void fireColorChangeEvent(final ColorChangeEvent e) {
		for (final ColorChangeListener listener : listeners) {
			listener.colorChanged(e);
		}
	}

	/**
	 * @see net.sf.jame.core.swing.color.ColorTableModel#setColor(int, int, boolean)
	 */
	public void setColor(final int x, final int y, final boolean isAdjusting) {
		if ((x >= 0) && (x < image.getWidth())) {
			if ((y >= 0) && (y < image.getHeight())) {
				this.x = x;
				this.y = y;
				fireColorChangeEvent(new ColorChangeEvent(this, isAdjusting));
			}
		}
	}

	/**
	 * @see net.sf.jame.core.swing.color.ColorTableModel#getColor()
	 */
	public Color getColor() {
		return new Color(image.getRGB(x, y));
	}

	/**
	 * @see net.sf.jame.core.swing.color.ColorTableModel#getImage()
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * @see net.sf.jame.core.swing.color.ColorTableModel#getImageSize()
	 */
	public Dimension getImageSize() {
		return new Dimension(image.getWidth(), image.getHeight());
	}

	/**
	 * @see net.sf.jame.core.swing.color.ColorTableModel#getImageWidth()
	 */
	public int getImageWidth() {
		return image.getWidth();
	}

	/**
	 * @see net.sf.jame.core.swing.color.ColorTableModel#getImageHeight()
	 */
	public int getImageHeight() {
		return image.getHeight();
	}

	/**
	 * @see net.sf.jame.core.swing.color.ColorTableModel#getX()
	 */
	public int getX() {
		return x;
	}

	/**
	 * @see net.sf.jame.core.swing.color.ColorTableModel#getY()
	 */
	public int getY() {
		return y;
	}
}
