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
package net.sf.jame.contextfree.extensions.shapeAdjustment;

import java.util.ArrayList;
import java.util.List;

import net.sf.jame.contextfree.CFDGBuilder;
import net.sf.jame.contextfree.shapeAdjustment.extension.ShapeAdjustmentExtensionConfig;
import net.sf.jame.core.common.BooleanElement;
import net.sf.jame.core.common.FloatElement;
import net.sf.jame.core.config.ConfigElement;

/**
 * @author Andrea Medeghini
 */
public class CurrentAlphaShapeAdjustmentConfig extends ShapeAdjustmentExtensionConfig {
	private static final long serialVersionUID = 1L;
	private FloatElement valueElement;
	private BooleanElement targetElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		valueElement = new FloatElement(0f);
		targetElement = new BooleanElement(false);
	}

	/**
	 * @see net.sf.jame.core.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		elements.add(valueElement);
		elements.add(targetElement);
		return elements;
	}

	/**
	 * @return
	 */
	public FloatElement getValueElement() {
		return valueElement;
	}
	
	/**
	 * @return
	 */
	public Float getValue() {
		return valueElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setValue(final Float value) {
		valueElement.setValue(value);
	}
	/**
	 * @return
	 */
	public BooleanElement getTargetElement() {
		return targetElement;
	}
	
	/**
	 * @return
	 */
	public Boolean isTarget() {
		return targetElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setTarget(final Boolean value) {
		targetElement.setValue(value);
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
		final CurrentAlphaShapeAdjustmentConfig other = (CurrentAlphaShapeAdjustmentConfig) obj;
		if (valueElement == null) {
			if (other.valueElement != null) {
				return false;
			}
		}
		else if (!valueElement.equals(other.valueElement)) {
			return false;
		}
		if (targetElement == null) {
			if (other.targetElement != null) {
				return false;
			}
		}
		else if (!targetElement.equals(other.targetElement)) {
			return false;
		}
		return true;
	}

	/**
	 * @return
	 */
	@Override
	public CurrentAlphaShapeAdjustmentConfig clone() {
		final CurrentAlphaShapeAdjustmentConfig config = new CurrentAlphaShapeAdjustmentConfig();
		config.setValue(getValue());
		config.setTarget(isTarget());
		return config;
	}

	@Override
	public void toCFDG(CFDGBuilder builder) {
		if (valueElement.getValue() != null) {
			builder.append("a ");
			builder.append(valueElement.getValue());
		}
		if (targetElement.getValue() != null) {
			builder.append(targetElement.getValue() ? "|" : "");
		}
	}
}
