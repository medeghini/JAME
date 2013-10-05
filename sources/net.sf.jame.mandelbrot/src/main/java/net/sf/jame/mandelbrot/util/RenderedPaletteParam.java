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
package net.sf.jame.mandelbrot.util;

import java.io.Serializable;
import java.util.Arrays;

import net.sf.jame.core.extension.ExtensionReference;

/**
 * @author Andrea Medeghini
 */
public class RenderedPaletteParam implements Serializable {
	private static final long serialVersionUID = 1L;
	private final ExtensionReference[] formulas;
	private final int[] colors;
	private final double size;

	/**
	 * @param formulas
	 * @param colors
	 * @param size
	 */
	public RenderedPaletteParam(final ExtensionReference[] formulas, final int[] colors, final double size) {
		this.size = size;
		this.colors = colors;
		this.formulas = formulas;
		if (colors == null) {
			throw new NullPointerException("colors == null");
		}
		if (colors.length != 2) {
			throw new IllegalArgumentException("colors.length != 2");
		}
		if (formulas == null) {
			throw new NullPointerException("formulas == null");
		}
		if (formulas.length != 4) {
			throw new IllegalArgumentException("formulas.length != 4");
		}
		if ((size < 0) || (size > 100)) {
			throw new IllegalArgumentException("size < 0 || size > 100");
		}
	}

	/**
	 * @return
	 */
	public double getSize() {
		return size;
	}

	/**
	 * @return
	 */
	public int getColor(final int index) {
		return colors[index];
	}

	/**
	 * @return
	 */
	public ExtensionReference getFormula(final int index) {
		return formulas[index];
	}

	/**
	 * @param buffer
	 * @return
	 */
	protected StringBuilder dump(final StringBuilder buffer) {
		buffer.append("colors = [");
		buffer.append("#");
		buffer.append(Integer.toHexString(colors[0]));
		buffer.append(", #");
		buffer.append(Integer.toHexString(colors[1]));
		buffer.append("], formulas = [");
		buffer.append(formulas[0].getExtensionId());
		buffer.append(", ");
		buffer.append(formulas[1].getExtensionId());
		buffer.append(", ");
		buffer.append(formulas[2].getExtensionId());
		buffer.append(", ");
		buffer.append(formulas[3].getExtensionId());
		buffer.append("], size = ");
		buffer.append(size);
		return buffer;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		final RenderedPaletteParam other = (RenderedPaletteParam) obj;
		if (!Arrays.equals(colors, other.colors)) {
			return false;
		}
		if (!Arrays.equals(formulas, other.formulas)) {
			return false;
		}
		if (Double.doubleToLongBits(size) != Double.doubleToLongBits(other.size)) {
			return false;
		}
		return true;
	}
}
