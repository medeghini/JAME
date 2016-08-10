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
package net.sf.jame.core.media;

import java.awt.*;

public final class EngineColorPaint extends EnginePaint {
	private Color paint;
	private final Color copy;
	private final int ARGB;
	private final int transparency;

	public EngineColorPaint(final Color color) {
		paint = color;
		copy = color;
		ARGB = color.getRGB();
		transparency = color.getTransparency();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return new EngineColorPaint(copy);
	}

	@Override
	public void applyEffect(final Effect effect) {
		paint = new Color(effect.filter(ARGB, transparency), true);
	}

	@Override
	public Paint getPaint() {
		return paint;
	}
}
