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
package net.sf.jame.mandelbrot.extensions.renderingFormula;

import java.util.ArrayList;
import java.util.List;

import net.sf.jame.core.math.Complex;
import net.sf.jame.mandelbrot.renderer.RenderedPoint;

/**
 * @author Andrea Medeghini
 */
public class MAGNETISM1Runtime extends AbstractRenderingFormulaRuntime<MAGNETISM1Config> {
	private static final int GUARD_VALUE = 1000;
	private final Complex ta = new Complex(0, 0);
	private final Complex tb = new Complex(0, 0);
	private final Complex tc = new Complex(0, 0);
	private final Complex z = new Complex(0, 0);
	private final Complex x = new Complex(0, 0);
	private final Complex w = new Complex(0, 0);
	private final Complex a = new Complex(1d);
	private final Complex b = new Complex(2d);

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
		x.r = cp.xr;
		x.i = cp.xi;
		w.r = cp.wr;
		w.i = cp.wi;
		cp.time = 0;
		for (int k = 1; k <= iterations; k++) {
			Complex.mul(ta, x, x);
			Complex.sub(tb, w, a);
			Complex.add(ta, ta, tb);
			Complex.mul(tb, x, 2d);
			Complex.sub(tc, w, b);
			Complex.add(tb, tb, tc);
			if (Complex.mod(tb) < 0.0000000000000000000001) {
				tb.r = 0.0000000000000000000001;
				tb.i = 0.0;
			}
			Complex.div(tc, ta, tb);
			Complex.mul(z, tc, tc);
			cp.zr = z.r;
			cp.zi = z.i;
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
				if (Complex.mod(z) > threshold) {
					cp.time = k;
					break;
				}
			}
			Complex.sub(tc, z, a);
			if (Complex.mod(tc) < 0.0000001d) {
				cp.time = k;
				break;
			}
			x.r = cp.zr;
			x.i = cp.zi;
			if ((x.r > GUARD_VALUE) || (x.i > GUARD_VALUE)) {
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
		final Complex ta = new Complex(0, 0);
		final Complex tb = new Complex(0, 0);
		final Complex tc = new Complex(0, 0);
		final Complex z = new Complex(0, 0);
		final Complex x = new Complex(cp.xr, cp.xi);
		final Complex w = new Complex(cp.wr, cp.wi);
		cp.time = 0;
		for (int k = 1; k <= iterations; k++) {
			Complex.mul(ta, x, x);
			Complex.sub(tb, w, a);
			Complex.add(ta, ta, tb);
			Complex.mul(tb, x, 2d);
			Complex.sub(tc, w, b);
			Complex.add(tb, tb, tc);
			if (Complex.mod(tb) < 0.0000000000000000000001) {
				tb.r = 0.0000000000000000000001;
				tb.i = 0.0;
			}
			Complex.div(tc, ta, tb);
			Complex.mul(z, tc, tc);
			orbit.add(new Complex(z.r, z.i));
			if (orbitTrapRuntime != null) {
				if (orbitTrapRuntime.processPoint(cp)) {
					cp.time = k;
					break;
				}
			}
			else {
				if (Complex.mod(z) > threshold) {
					cp.time = k;
					break;
				}
			}
			Complex.sub(tc, z, a);
			if (Complex.mod(tc) < 0.0000001d) {
				cp.time = k;
				break;
			}
			x.r = z.r;
			x.i = z.i;
			if ((x.r > GUARD_VALUE) || (x.i > GUARD_VALUE)) {
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
		return cp.time;// TODO not suported
	}
}
