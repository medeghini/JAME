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
package net.sf.jame.mandelbrot.extensions.processingFormula.distance;

import net.sf.jame.mandelbrot.processingFormula.extension.ProcessingFormulaExtensionRuntime;
import net.sf.jame.mandelbrot.renderer.RenderedPoint;

/**
 * @author Andrea Medeghini
 */
public class MinimumFormulaRuntime extends ProcessingFormulaExtensionRuntime {
	private double value;
	private double dr;
	private double di;
	private double m;

	/**
	 * @see net.sf.jame.mandelbrot.processingFormula.extension.ProcessingFormulaExtensionRuntime#prepareForProcessing()
	 */
	@Override
	public void prepareForProcessing() {
		value = Double.MAX_VALUE;
		dr = 0;
		di = 0;
	}

	/**
	 * @see net.sf.jame.mandelbrot.processingFormula.extension.ProcessingFormulaExtensionRuntime#processPoint(net.sf.jame.mandelbrot.renderer.RenderedPoint)
	 */
	@Override
	public void processPoint(final RenderedPoint cp) {
		m = cp.dr * cp.dr + cp.di * cp.di;
		if (m < value) {
			value = m;
			dr = cp.dr;
			di = cp.di;
		}
	}

	/**
	 * @see net.sf.jame.mandelbrot.processingFormula.extension.ProcessingFormulaExtensionRuntime#renderPoint(net.sf.jame.mandelbrot.renderer.RenderedPoint)
	 */
	@Override
	public void renderPoint(final RenderedPoint cp) {
		cp.tr = dr;
		cp.ti = di;
	}
}
