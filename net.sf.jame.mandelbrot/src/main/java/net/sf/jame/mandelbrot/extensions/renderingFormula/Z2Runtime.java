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
package net.sf.jame.mandelbrot.extensions.renderingFormula;

import java.util.ArrayList;
import java.util.List;

import net.sf.jame.core.math.Complex;
import net.sf.jame.mandelbrot.orbitTrap.extension.OrbitTrapExtensionRuntime;
import net.sf.jame.mandelbrot.processingFormula.extension.ProcessingFormulaExtensionRuntime;
import net.sf.jame.mandelbrot.renderer.RenderedPoint;

/**
 * @author Andrea Medeghini
 */
public class Z2Runtime extends AbstractRenderingFormulaRuntime<Z2Config> {
	private static final int GUARD_VALUE = 1000;
	private double ta;
	private double tb;
	private double tc;
	private double td;

	/**
	 * @see net.sf.jame.mandelbrot.extensions.renderingFormula.AbstractRenderingFormulaRuntime#prepareForRendering(net.sf.jame.mandelbrot.processingFormula.extension.ProcessingFormulaExtensionRuntime, net.sf.jame.mandelbrot.orbitTrap.extension.OrbitTrapExtensionRuntime)
	 */
	@Override
	public void prepareForRendering(final ProcessingFormulaExtensionRuntime formulaRuntime, final OrbitTrapExtensionRuntime<?> orbitTrapRuntime) {
		super.prepareForRendering(formulaRuntime, orbitTrapRuntime);
		td = threshold * threshold;
	}

	/**
	 * @see net.sf.jame.mandelbrot.extensions.renderingFormula.AbstractRenderingFormulaRuntime#isVerticalSymetryAllowed()
	 */
	@Override
	public boolean isVerticalSymetryAllowed() {
		if (formulaRuntime != null) {
			return false;
		}
		if (orbitTrapRuntime != null) {
			return false;
		}
		return true;
	}

	/**
	 * @see net.sf.jame.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionRuntime#renderPoint(net.sf.jame.mandelbrot.renderer.RenderedPoint)
	 */
	@Override
	public int renderPoint(final RenderedPoint cp) {
		if (formulaRuntime != null) {
			formulaRuntime.prepareForProcessing();
		}
		cp.time = 0;
		cp.zr = cp.xr;
		cp.zi = cp.xi;
		for (int k = 1; k <= iterations; k++) {
			ta = cp.zr * cp.zr;
			tb = cp.zi * cp.zi;
			tc = cp.zi * cp.zr;
			cp.zr = ta - tb + cp.wr;
			cp.zi = (2d * tc) + cp.wi;
			if (formulaRuntime != null) {
				formulaRuntime.processPoint(cp);
			}
			if (orbitTrapRuntime != null) {
				if (orbitTrapRuntime.processPoint(cp)) {
					cp.time = k;
					break;
				}
			}
			else {
				if (((cp.zr * cp.zr) + (cp.zi * cp.zi)) > td) {
					cp.time = k;
					break;
				}
			}
			if ((cp.zr > GUARD_VALUE) || (cp.zi > GUARD_VALUE)) {
				cp.time = k;
				break;
			}
		}
		if (formulaRuntime != null) {
			formulaRuntime.renderPoint(cp);
		}
		else {
			if (orbitTrapRuntime != null) {
				orbitTrapRuntime.renderPoint(cp);
			}
		}
		return cp.time;
	}

	/**
	 * @see net.sf.jame.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionRuntime#renderOrbit(net.sf.jame.mandelbrot.renderer.RenderedPoint)
	 */
	@Override
	public List<Complex> renderOrbit(final RenderedPoint cp) {
		final ArrayList<Complex> orbit = new ArrayList<Complex>();
		double ta = 0.0;
		double tb = 0.0;
		double tc = 0.0;
		double td = 0.0;
		td = threshold * threshold;
		cp.zr = cp.xr;
		cp.zi = cp.xi;
		cp.time = 0;
		for (int k = 1; k <= iterations; k++) {
			ta = cp.zr * cp.zr;
			tb = cp.zi * cp.zi;
			tc = cp.zi * cp.zr;
			cp.zr = ta - tb + cp.wr;
			cp.zi = (2d * tc) + cp.wi;
			orbit.add(new Complex(cp.zr, cp.zi));
			if (orbitTrapRuntime != null) {
				if (orbitTrapRuntime.processPoint(cp)) {
					cp.time = k;
					break;
				}
			}
			else {
				if (((cp.zr * cp.zr) + (cp.zi * cp.zi)) > td) {
					cp.time = k;
					break;
				}
			}
			if ((cp.zr > GUARD_VALUE) || (cp.zi > GUARD_VALUE)) {
				cp.time = k;
				break;
			}
		}
		return orbit;
	}

	/**
	 * @see net.sf.jame.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionRuntime#getNormalizedIterationCount(net.sf.jame.mandelbrot.renderer.RenderedPoint)
	 */
	@Override
	public double getNormalizedIterationCount(final RenderedPoint cp) {
		final double modulus = Math.sqrt((cp.zr * cp.zr) + (cp.zi * cp.zi));
		// return cp.time + 1d - Math.log(Math.log(modulus)) / Math.log(2d);
		return cp.time + (Math.log(Math.log(threshold)) - Math.log(Math.log(modulus))) / Math.log(2d);
	}
}
