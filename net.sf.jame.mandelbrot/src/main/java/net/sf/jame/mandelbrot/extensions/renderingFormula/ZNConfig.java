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

import java.util.List;

import net.sf.jame.core.config.ConfigElement;
import net.sf.jame.core.math.Complex;
import net.sf.jame.core.util.DoubleVector2D;
import net.sf.jame.mandelbrot.common.ExponentElement;

/**
 * @author Andrea Medeghini
 */
public class ZNConfig extends AbstractRenderingFormulaConfig {
	private static final long serialVersionUID = 1L;
	private ExponentElement exponentElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		super.createConfigElements();
		exponentElement = new ExponentElement(getDefaultExponent());
		exponentElement.setMaximum(getExponentMax());
		exponentElement.setMinimum(getExponentMin());
		exponentElement.setStep(getExponentStep());
	}

	/**
	 * @see net.sf.jame.core.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = super.getConfigElements();
		elements.add(exponentElement);
		return elements;
	}

	/**
	 * @see net.sf.jame.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionConfig#getDefaultThreshold()
	 */
	@Override
	public Double getDefaultThreshold() {
		return 40.0;
	}

	/**
	 * @see net.sf.jame.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionConfig#getDefaultIterations()
	 */
	@Override
	public Integer getDefaultIterations() {
		return 200;
	}

	/**
	 * @see net.sf.jame.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionConfig#getDefaultCenter()
	 */
	@Override
	public DoubleVector2D getDefaultCenter() {
		return new DoubleVector2D(0, 0);
	}

	/**
	 * @see net.sf.jame.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionConfig#getDefaultScale()
	 */
	@Override
	public DoubleVector2D getDefaultScale() {
		return new DoubleVector2D(6.0, 6.0);
	}

	/**
	 * @see net.sf.jame.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionConfig#getDefaultConstant()
	 */
	@Override
	public Complex getDefaultConstant() {
		return new Complex(0.0, 0.0);
	}

	/**
	 * @see net.sf.jame.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionConfig#getThresholdMin()
	 */
	@Override
	public Double getThresholdMin() {
		return 0.0;
	}

	/**
	 * @see net.sf.jame.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionConfig#getThresholdMax()
	 */
	@Override
	public Double getThresholdMax() {
		return 1000.0;
	}

	/**
	 * @see net.sf.jame.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionConfig#getThresholdStep()
	 */
	@Override
	public Double getThresholdStep() {
		return 0.1;
	}

	/**
	 * @see net.sf.jame.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionConfig#getIterationsMin()
	 */
	@Override
	public Integer getIterationsMin() {
		return 1;
	}

	/**
	 * @see net.sf.jame.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionConfig#getIterationsMax()
	 */
	@Override
	public Integer getIterationsMax() {
		return 10000;
	}

	/**
	 * @see net.sf.jame.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionConfig#getIterationsStep()
	 */
	@Override
	public Integer getIterationsStep() {
		return 1;
	}

	/**
	 * @return
	 */
	public Integer getDefaultExponent() {
		return 8;
	}

	/**
	 * @return
	 */
	public Integer getExponent() {
		return exponentElement.getValue();
	}

	/**
	 * @param exponent
	 */
	public void setExponent(final Integer exponent) {
		exponentElement.setValue(exponent);
	}

	/**
	 * @return
	 */
	public Integer getExponentMin() {
		return 2;
	}

	/**
	 * @return
	 */
	public Integer getExponentMax() {
		return 50;
	}

	/**
	 * @return
	 */
	public Integer getExponentStep() {
		return 1;
	}

	/**
	 * @param buffer
	 * @return
	 */
	@Override
	protected StringBuilder dump(final StringBuilder buffer) {
		super.dump(buffer);
		buffer.append(", exponent = ");
		buffer.append(getExponent());
		return buffer;
	}

	/**
	 * @return
	 */
	public ExponentElement getExponentElement() {
		return exponentElement;
	}

	/**
	 * @return
	 */
	@Override
	public ZNConfig clone() {
		final ZNConfig config = new ZNConfig();
		config.setCenter(getCenter());
		config.setScale(getScale());
		config.setIterations(getIterations());
		config.setThreshold(getThreshold());
		config.setExponent(getExponent());
		return config;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		final ZNConfig other = (ZNConfig) obj;
		if (exponentElement == null) {
			if (other.exponentElement != null) {
				return false;
			}
		}
		else if (!exponentElement.equals(other.exponentElement)) {
			return false;
		}
		return true;
	}
}
