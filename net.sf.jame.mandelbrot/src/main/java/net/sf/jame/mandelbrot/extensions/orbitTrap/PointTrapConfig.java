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
package net.sf.jame.mandelbrot.extensions.orbitTrap;

import java.util.ArrayList;
import java.util.List;

import net.sf.jame.core.common.DoubleElement;
import net.sf.jame.core.config.ConfigElement;
import net.sf.jame.core.extension.ExtensionConfig;
import net.sf.jame.mandelbrot.common.CriteriaElement;

/**
 * @author Andrea Medeghini
 */
public class PointTrapConfig extends AbstractOrbitTrapConfig {
	private static final long serialVersionUID = 1L;
	private static final Double DEFAULT_SIZE = new Double(4);
	private static final Integer DEFAULT_CRITERIA = new Integer(0);
	private CriteriaElement criteriaElement;
	private DoubleElement sizeElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		criteriaElement = new CriteriaElement(getDefaultCriteria());
		sizeElement = new DoubleElement(getDefaultCenter());
	}

	/**
	 * @see net.sf.jame.core.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		elements.add(criteriaElement);
		elements.add(sizeElement);
		return elements;
	}

	/**
	 * @param size
	 */
	public void setSize(final Double size) {
		sizeElement.setValue(size);
	}

	/**
	 * @return the size.
	 */
	public Double getSize() {
		return sizeElement.getValue();
	}

	/**
	 * @return the default size.
	 */
	public Double getDefaultCenter() {
		return PointTrapConfig.DEFAULT_SIZE;
	}

	/**
	 * @return
	 */
	protected DoubleElement getSizeElement() {
		return sizeElement;
	}

	/**
	 * @param criteria
	 */
	public void setCriteria(final Integer criteria) {
		criteriaElement.setValue(criteria);
	}

	/**
	 * @return the criteria.
	 */
	public Integer getCriteria() {
		return criteriaElement.getValue();
	}

	/**
	 * @return the default criteria.
	 */
	public Integer getDefaultCriteria() {
		return DEFAULT_CRITERIA;
	}

	/**
	 * @return
	 */
	protected CriteriaElement getCriteriaElement() {
		return criteriaElement;
	}

	/**
	 * @return
	 */
	@Override
	public ExtensionConfig clone() {
		final PointTrapConfig config = new PointTrapConfig();
		config.setCriteria(getCriteria());
		config.setSize(getSize());
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
		final PointTrapConfig other = (PointTrapConfig) obj;
		if (sizeElement == null) {
			if (other.sizeElement != null) {
				return false;
			}
		}
		else if (!sizeElement.equals(other.sizeElement)) {
			return false;
		}
		if (criteriaElement == null) {
			if (other.criteriaElement != null) {
				return false;
			}
		}
		else if (!criteriaElement.equals(other.criteriaElement)) {
			return false;
		}
		return true;
	}
}
