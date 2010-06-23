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
package net.sf.jame.mandelbrot.extensions.fractal.orbittrap;

import java.util.ArrayList;
import java.util.List;

import net.sf.jame.core.common.DoubleElement;
import net.sf.jame.core.config.ConfigElement;
import net.sf.jame.core.extension.ExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public class DeltaTrapConfig extends AbstractOrbitTrapConfig {
	private static final long serialVersionUID = 1L;
	private static final Double DEFAULT_THRESHOLD = new Double(0.0001);
	private DoubleElement thresholdElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		thresholdElement = new DoubleElement(getDefaultThreshold());
	}

	/**
	 * @see net.sf.jame.core.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		elements.add(thresholdElement);
		return elements;
	}

	/**
	 * @param threshold
	 */
	public void setThreshold(final Double threshold) {
		thresholdElement.setValue(threshold);
	}

	/**
	 * @return the size.
	 */
	public Double getThreshold() {
		return thresholdElement.getValue();
	}

	/**
	 * @return the default threshold.
	 */
	public Double getDefaultThreshold() {
		return DEFAULT_THRESHOLD;
	}

	/**
	 * @return
	 */
	protected DoubleElement getThresholdElement() {
		return thresholdElement;
	}

	/**
	 * @return
	 */
	@Override
	public ExtensionConfig clone() {
		final DeltaTrapConfig config = new DeltaTrapConfig();
		config.setThreshold(getThreshold());
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
		if (obj == null) {
			return false;
		}
		final DeltaTrapConfig other = (DeltaTrapConfig) obj;
		if (thresholdElement == null) {
			if (other.thresholdElement != null) {
				return false;
			}
		}
		else if (!thresholdElement.equals(other.thresholdElement)) {
			return false;
		}
		return true;
	}
}
