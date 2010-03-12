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
package net.sf.jame.mandelbrot.extensions.fractal.outcolouring;

import net.sf.jame.mandelbrot.fractal.rendering.extension.RenderingFormulaExtensionRuntime;
import net.sf.jame.mandelbrot.renderer.RenderedPoint;

/**
 * @author Andrea Medeghini
 */
public class ContinuousPotentialRuntime extends AbstractOutcolouringPaletteRuntime<ContinuousPotentialConfig> {
	private double ratio;

	/**
	 * @see net.sf.jame.mandelbrot.extensions.fractal.incolouring.AbstractIncolouringPaletteRuntime#prepareForRendering(net.sf.jame.mandelbrot.fractal.rendering.extension.RenderingFormulaExtensionRuntime, int)
	 */
	@Override
	public void prepareForRendering(final RenderingFormulaExtensionRuntime<?> formulaRuntime, final int maxColors) {
		super.prepareForRendering(formulaRuntime, maxColors);
		if (formulaRuntime != null) {
			ratio = ((double) colorTable.length / (double) formulaRuntime.getIterations());
		}
	}

	/**
	 * @see net.sf.jame.mandelbrot.extensions.fractal.outcolouring.AbstractOutcolouringFormulaRuntime#isHorizontalSymetryAllowed()
	 */
	@Override
	public boolean isHorizontalSymetryAllowed() {
		return true;
	}

	/**
	 * @see net.sf.jame.mandelbrot.extensions.fractal.outcolouring.AbstractOutcolouringFormulaRuntime#isVerticalSymetryAllowed()
	 */
	@Override
	public boolean isVerticalSymetryAllowed() {
		return true;
	}

	/**
	 * @see net.sf.jame.mandelbrot.extensions.fractal.outcolouring.AbstractOutcolouringFormulaRuntime#isSolidGuessAllowed()
	 */
	@Override
	public boolean isSolidGuessAllowed() {
		return true;
	}

	/**
	 * @see net.sf.jame.mandelbrot.extensions.fractal.outcolouring.AbstractOutcolouringPaletteRuntime#renderIndex(net.sf.jame.mandelbrot.renderer.RenderedPoint)
	 */
	@Override
	protected int renderIndex(final RenderedPoint cp) {
		if (formulaRuntime != null) {
			return (int) Math.rint(ratio * (formulaRuntime.getNormalizedIterationCount(cp) - 1));
		}
		return 0;
	}
}
