/*
 * $Id:$
 *
 */
package net.sf.jame.contextfree.extensions.pathAdjustment;

import java.util.ArrayList;
import java.util.List;

import net.sf.jame.contextfree.CFDGBuilder;
import net.sf.jame.contextfree.cfdg.pathAdjustment.extension.PathAdjustmentExtensionConfig;
import net.sf.jame.core.common.BooleanElement;
import net.sf.jame.core.common.FloatElement;
import net.sf.jame.core.config.ConfigElement;

/**
 * @author Andrea Medeghini
 */
public class CurrentHuePathAdjustmentConfig extends PathAdjustmentExtensionConfig {
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
		final CurrentHuePathAdjustmentConfig other = (CurrentHuePathAdjustmentConfig) obj;
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
	public CurrentHuePathAdjustmentConfig clone() {
		final CurrentHuePathAdjustmentConfig config = new CurrentHuePathAdjustmentConfig();
		config.setValue(getValue());
		config.setTarget(isTarget());
		return config;
	}

	@Override
	public void toCFDG(CFDGBuilder builder) {
		if (valueElement.getValue() != null) {
			builder.append("h ");
			builder.append(valueElement.getValue() * 360);
		}
		if (targetElement.getValue() != null) {
			builder.append(targetElement.getValue() ? "|" : "");
		}
	}
}
