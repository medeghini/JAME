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
package net.sf.jame.twister.extensions.frame.layer.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Map;

import net.sf.jame.core.util.Tile;
import net.sf.jame.core.util.IntegerVector2D;
import net.sf.jame.twister.frame.layer.image.extension.ImageExtensionRuntime;
import net.sf.jame.twister.renderer.TwisterRenderer;
import net.sf.jame.twister.renderer.TwisterRenderingHints;

/**
 * @author Andrea Medeghini
 */
public class BorderRuntime extends ImageExtensionRuntime<BorderConfig> {
	private boolean isOverlay;
	private Tile tile;
	private Color color;

	public BorderRuntime() {
	}

	/**
	 * @see net.sf.jame.twister.frame.layer.image.extension.ImageExtensionRuntime#setTile(net.sf.jame.core.util.Tile)
	 */
	@Override
	public void setTile(final Tile tile) {
		this.tile = tile;
	}

	/**
	 * @see net.sf.jame.twister.frame.layer.image.extension.ImageExtensionRuntime#startRenderer()
	 */
	@Override
	public void startRenderer() {
	}

	/**
	 * @see net.sf.jame.twister.frame.layer.image.extension.ImageExtensionRuntime#abortRenderer()
	 */
	@Override
	public void abortRenderer() {
	}

	/**
	 * @see net.sf.jame.twister.frame.layer.image.extension.ImageExtensionRuntime#joinRenderer()
	 */
	@Override
	public void joinRenderer() {
	}

	/**
	 * @see net.sf.jame.twister.frame.layer.image.extension.ImageExtensionRuntime#getRenderingStatus()
	 */
	@Override
	public int getRenderingStatus() {
		return TwisterRenderer.STATUS_TERMINATED;
	}

	/**
	 * @see net.sf.jame.twister.frame.layer.image.extension.ImageExtensionRuntime#drawImage(java.awt.Graphics2D)
	 */
	@Override
	public void drawImage(final Graphics2D g2d) {
		if (!isOverlay && (tile != null)) {
			int w = getImageSize().getX();
			int h = getImageSize().getY();
			int tx = tile.getTileBorder().getX() - tile.getTileOffset().getX();
			int ty = tile.getTileBorder().getY() - tile.getTileOffset().getY();
			int d = w - tile.getTileBorder().getX() * 2;
			int sw = (int) Math.rint((getConfig().getSize().doubleValue() * d) / 100d);
			int sh = (int) Math.rint((getConfig().getSize().doubleValue() * d) / 100d);
			g2d.setColor(color);
			g2d.fillRect(tx, ty, w, sh);
			g2d.fillRect(tx, h - sh + ty, w, sh);
			g2d.fillRect(tx, ty, sw, h);
			g2d.fillRect(w - sw + tx, ty, sw, h);
		}
	}

	/**
	 * @see net.sf.jame.twister.frame.layer.image.extension.ImageExtensionRuntime#drawImage(java.awt.Graphics2D, int, int)
	 */
	@Override
	public void drawImage(final Graphics2D g2d, final int x, final int y) {
		if (!isOverlay && (tile != null)) {
			int w = getImageSize().getX();
			int h = getImageSize().getY();
			int tx = tile.getTileBorder().getX() - tile.getTileOffset().getX() + x;
			int ty = tile.getTileBorder().getY() - tile.getTileOffset().getY() + y;
			int d = w - tile.getTileBorder().getX() * 2;
			int sw = (int) Math.rint((getConfig().getSize().doubleValue() * d) / 100d);
			int sh = (int) Math.rint((getConfig().getSize().doubleValue() * d) / 100d);
			g2d.setColor(color);
			g2d.fillRect(tx, ty, w, sh);
			g2d.fillRect(tx, h - sh + ty, w, sh);
			g2d.fillRect(tx, ty, sw, h);
			g2d.fillRect(w - sw + tx, ty, sw, h);
		}
	}

	/**
	 * @see net.sf.jame.twister.frame.layer.image.extension.ImageExtensionRuntime#drawImage(java.awt.Graphics2D, int, int, int, int)
	 */
	@Override
	public void drawImage(final Graphics2D g2d, final int x, final int y, final int w, final int h) {
		if (!isOverlay && (tile != null)) {
			int sw = (int) Math.rint((getConfig().getSize().doubleValue() * w) / 100d);
			int sh = (int) Math.rint((getConfig().getSize().doubleValue() * w) / 100d);
			g2d.setColor(color);
			g2d.fillRect(x, y, w, sh);
			g2d.fillRect(x, h - sh + y, w, sh);
			g2d.fillRect(x, y, sw, h);
			g2d.fillRect(w - sw + x, y, sw, h);
		}
	}

	/**
	 * @see net.sf.jame.twister.frame.layer.image.extension.ImageExtensionRuntime#getImageSize()
	 */
	@Override
	public IntegerVector2D getImageSize() {
		return tile.getImageSize();
	}

	/**
	 * @see net.sf.jame.twister.frame.layer.image.extension.ImageExtensionRuntime#setRenderingHints(java.util.Map)
	 */
	@Override
	public void setRenderingHints(final Map<Object, Object> hints) {
		if (hints.get(TwisterRenderingHints.KEY_TYPE) == TwisterRenderingHints.TYPE_OVERLAY) {
			isOverlay = true;
		}
		else {
			isOverlay = false;
		}
	}

	/**
	 * @see net.sf.jame.twister.frame.layer.image.extension.ImageExtensionRuntime#prepareImage(boolean)
	 */
	@Override
	public void prepareImage(final boolean isDynamicRequired) {
		color = new Color(getConfig().getColor().getARGB(), true);
	}

	/**
	 * @see net.sf.jame.twister.frame.layer.image.extension.ImageExtensionRuntime#isDynamic()
	 */
	@Override
	public boolean isDynamic() {
		return false;
	}
}
