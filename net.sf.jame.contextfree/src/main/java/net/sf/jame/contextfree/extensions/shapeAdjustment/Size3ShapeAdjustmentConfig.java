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
import net.sf.jame.core.common.FloatElement;
import net.sf.jame.core.config.ConfigElement;

/**
 * @author Andrea Medeghini
 */
public class Size3ShapeAdjustmentConfig extends ShapeAdjustmentExtensionConfig {
	private static final long serialVersionUID = 1L;
	private FloatElement scaleXElement;
	private FloatElement scaleYElement;
	private FloatElement scaleZElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		scaleXElement = new FloatElement(0f);
		scaleYElement = new FloatElement(0f);
		scaleZElement = new FloatElement(0f);
	}

	/**
	 * @see net.sf.jame.core.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		elements.add(scaleXElement);
		elements.add(scaleYElement);
		elements.add(scaleZElement);
		return elements;
	}

	/**
	 * @return
	 */
	public FloatElement getScaleXElement() {
		return scaleXElement;
	}
	
	/**
	 * @return
	 */
	public Float getScaleX() {
		return scaleXElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setScaleX(final Float value) {
		scaleXElement.setValue(value);
	}
	/**
	 * @return
	 */
	public FloatElement getScaleYElement() {
		return scaleYElement;
	}
	
	/**
	 * @return
	 */
	public Float getScaleY() {
		return scaleYElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setScaleY(final Float value) {
		scaleYElement.setValue(value);
	}
	/**
	 * @return
	 */
	public FloatElement getScaleZElement() {
		return scaleZElement;
	}
	
	/**
	 * @return
	 */
	public Float getScaleZ() {
		return scaleZElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setScaleZ(final Float value) {
		scaleZElement.setValue(value);
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
		final Size3ShapeAdjustmentConfig other = (Size3ShapeAdjustmentConfig) obj;
		if (scaleXElement == null) {
			if (other.scaleXElement != null) {
				return false;
			}
		}
		else if (!scaleXElement.equals(other.scaleXElement)) {
			return false;
		}
		if (scaleYElement == null) {
			if (other.scaleYElement != null) {
				return false;
			}
		}
		else if (!scaleYElement.equals(other.scaleYElement)) {
			return false;
		}
		if (scaleZElement == null) {
			if (other.scaleZElement != null) {
				return false;
			}
		}
		else if (!scaleZElement.equals(other.scaleZElement)) {
			return false;
		}
		return true;
	}

	/**
	 * @return
	 */
	@Override
	public Size3ShapeAdjustmentConfig clone() {
		final Size3ShapeAdjustmentConfig config = new Size3ShapeAdjustmentConfig();
		config.setScaleX(getScaleX());
		config.setScaleY(getScaleY());
		config.setScaleZ(getScaleZ());
		return config;
	}

	@Override
	public void toCFDG(CFDGBuilder builder) {
		if (scaleXElement.getValue() != null && scaleYElement.getValue() != null && scaleZElement.getValue() != null) {
			builder.append("s ");
			builder.append(scaleXElement.getValue());
			builder.append(" ");
			builder.append(scaleYElement.getValue());
			builder.append(" ");
			builder.append(scaleZElement.getValue());
		}
	}
}

