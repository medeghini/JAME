/*
 * $Id:$
 *
 */
package net.sf.jame.contextfree.extensions.shapeAdjustment;

import java.lang.Float;
import java.util.ArrayList;
import java.util.List;
import net.sf.jame.core.common.FloatElement;
import net.sf.jame.core.config.ConfigElement;
import net.sf.jame.contextfree.cfdg.shapeAdjustment.extension.ShapeAdjustmentExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public class RotateShapeAdjustmentConfig extends ShapeAdjustmentExtensionConfig {
	private static final long serialVersionUID = 1L;
	private FloatElement angleElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		angleElement = new FloatElement(0f);
	}

	/**
	 * @see net.sf.jame.core.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		elements.add(angleElement);
		return elements;
	}

	/**
	 * @return
	 */
	public FloatElement getAngleElement() {
		return angleElement;
	}
	
	/**
	 * @return
	 */
	public Float getAngle() {
		return angleElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setAngle(final Float value) {
		angleElement.setValue(value);
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
		final RotateShapeAdjustmentConfig other = (RotateShapeAdjustmentConfig) obj;
		if (angleElement == null) {
			if (other.angleElement != null) {
				return false;
			}
		}
		else if (!angleElement.equals(other.angleElement)) {
			return false;
		}
		return true;
	}

	/**
	 * @return
	 */
	@Override
	public RotateShapeAdjustmentConfig clone() {
		final RotateShapeAdjustmentConfig config = new RotateShapeAdjustmentConfig();
		config.setAngle(getAngle());
		return config;
	}

	@Override
	public void toCFDG(StringBuilder builder) {
		if (angleElement.getValue() != null) {
			builder.append("r ");
			builder.append(angleElement.getValue());
		}
	}
}

