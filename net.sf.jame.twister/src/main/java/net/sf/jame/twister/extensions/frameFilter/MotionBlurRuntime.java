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
package net.sf.jame.twister.extensions.frameFilter;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;

import net.sf.jame.core.util.Surface;
import net.sf.jame.core.util.Tile;
import net.sf.jame.twister.frameFilter.extension.FrameFilterExtensionRuntime;
import net.sf.jame.twister.util.Padding;

/**
 * @author Andrea Medeghini
 */
public class MotionBlurRuntime extends FrameFilterExtensionRuntime<MotionBlurConfig> {
	private final Padding padding = new Padding(0, 0, 0, 0);
	private int alpha;

	/**
	 * @see net.sf.jame.twister.frameFilter.extension.FrameFilterExtensionRuntime#getPadding()
	 */
	@Override
	public Padding getPadding() {
		return padding;
	}

	/**
	 * @see net.sf.jame.twister.frameFilter.extension.FrameFilterExtensionRuntime#renderImage(net.sf.jame.core.util.Surface, net.sf.jame.core.util.Surface, net.sf.jame.core.util.Surface)
	 */
	@Override
	public void renderImage(final Surface src, final Surface dst, final Surface prev) {
		final Graphics2D g2d = dst.getGraphics2D();
		g2d.setComposite(AlphaComposite.SrcOver);
		g2d.drawImage(src.getImage(), 0, 0, null);
		if (alpha > 0) {
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha / 100f));
			g2d.drawImage(prev.getImage(), 0, 0, null);
		}
		// g2d.dispose();
	}

	/**
	 * @see net.sf.jame.twister.layerFilter.extension.LayerFilterExtensionRuntime#setTile(net.sf.jame.core.util.Tile)
	 */
	@Override
	public void setTile(final Tile tile) {
	}

	/**
	 * @see net.sf.jame.twister.frameFilter.extension.FrameFilterExtensionRuntime#prepareFilter(boolean)
	 */
	@Override
	public void prepareFilter(final boolean isDynamic) {
		if (isDynamic) {
			alpha = getConfig().getOpacity();
		}
		else {
			if (alpha > 0) {
				alpha -= 1;
				fireChanged();
			}
		}
	}
}
