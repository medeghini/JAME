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
package net.sf.jame.mandelbrot.extensions.outcolouringFormula.processed;

import net.sf.jame.mandelbrot.extensions.outcolouringFormula.AbstractOutcolouringFormulaRuntime;
import net.sf.jame.mandelbrot.renderer.RenderedPoint;
import net.sf.jame.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public class BinaryRuntime extends AbstractOutcolouringFormulaRuntime<BinaryConfig> {
	private final int[] colorTable = new int[2];

	/**
	 * @see net.sf.jame.mandelbrot.extensions.outcolouringFormula.AbstractOutcolouringFormulaRuntime#prepareForRendering(int)
	 */
	@Override
	public void prepareForRendering(final RenderingFormulaExtensionRuntime<?> formulaRuntime, final int maxColors) {
		final BinaryConfig config = getConfig();
		colorTable[0] = config.getColors()[0].getARGB();
		colorTable[1] = config.getColors()[1].getARGB();
	}

	/**
	 * @see net.sf.jame.mandelbrot.outcolouringFormula.extension.OutcolouringFormulaExtensionRuntime#renderColor(net.sf.jame.mandelbrot.renderer.RenderedPoint)
	 */
	@Override
	public int renderColor(final RenderedPoint cp) {
		return colorTable[renderIndex(cp)];
	}

	/**
	 * @see net.sf.jame.mandelbrot.outcolouringFormula.extension.OutcolouringFormulaExtensionRuntime#renderColor(net.sf.jame.mandelbrot.renderer.RenderedPoint, int)
	 */
	@Override
	public int renderColor(final RenderedPoint cp, final int shift) {
		return this.renderColor(cp);
	}

	/**
	 * @param cp
	 * @return the index.
	 */
	protected int renderIndex(final RenderedPoint cp) {
		final double phase = Math.atan2(cp.ti, cp.tr);
		return (phase > 0) ? 0 : 1;
	}
}
