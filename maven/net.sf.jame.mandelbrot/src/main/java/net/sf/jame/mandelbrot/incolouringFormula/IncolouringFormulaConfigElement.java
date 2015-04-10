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
package net.sf.jame.mandelbrot.incolouringFormula;

import net.sf.jame.core.common.BooleanElement;
import net.sf.jame.core.common.ConfigurableExtensionReferenceElement;
import net.sf.jame.core.common.StringElement;
import net.sf.jame.core.config.AbstractConfigElement;
import net.sf.jame.core.config.ConfigContext;
import net.sf.jame.core.config.ConfigElement;
import net.sf.jame.core.extension.ConfigurableExtensionReference;
import net.sf.jame.mandelbrot.common.IterationsElement;
import net.sf.jame.mandelbrot.incolouringFormula.extension.IncolouringFormulaExtensionConfig;
import net.sf.jame.twister.common.PercentageElement;

/**
 * @author Andrea Medeghini
 */
public class IncolouringFormulaConfigElement extends AbstractConfigElement {
	private static final long serialVersionUID = 1L;
	public static final String CLASS_ID = "IncolouringFormula";
	private final ConfigurableExtensionReferenceElement<IncolouringFormulaExtensionConfig> extensionElement = new ConfigurableExtensionReferenceElement<IncolouringFormulaExtensionConfig>();
	private final BooleanElement lockedElement = new BooleanElement(false);
	private final BooleanElement enabledElement = new BooleanElement(true);
	private final PercentageElement opacityElement = new PercentageElement(100);
	private final BooleanElement autoIterationsElement = new BooleanElement(true);
	private final IterationsElement iterationsElement = new IterationsElement(200);
	private final StringElement labelElement = new StringElement("New Incolouring Formula");

	/**
	 * Constructs a new element.
	 */
	public IncolouringFormulaConfigElement() {
		super(IncolouringFormulaConfigElement.CLASS_ID);
	}

	/**
	 * @return
	 */
	@Override
	public IncolouringFormulaConfigElement clone() {
		final IncolouringFormulaConfigElement element = new IncolouringFormulaConfigElement();
		element.setLabel(getLabel());
		element.setOpacity(getOpacity());
		element.setIterations(getIterations());
		element.setAutoIterations(getAutoIterations());
		element.setLocked(isLocked());
		element.setEnabled(isEnabled());
		if (getReference() != null) {
			element.setReference(getReference().clone());
		}
		return element;
	}

	/**
	 * @see net.sf.jame.core.config.ConfigElement#copyFrom(net.sf.jame.core.config.ConfigElement)
	 */
	public void copyFrom(ConfigElement source) {
		IncolouringFormulaConfigElement element = (IncolouringFormulaConfigElement) source;
		setLabel(element.getLabel());
		setOpacity(element.getOpacity());
		setIterations(element.getIterations());
		setAutoIterations(element.getAutoIterations());
		setLocked(element.isLocked());
		setEnabled(element.isEnabled());
		if (element.getReference() != null) {
			setReference(element.getReference().clone());
		}
	}

	/**
	 * @see net.sf.jame.core.common.ConfigurableExtensionReferenceElement#setContext(net.sf.jame.core.config.ConfigContext)
	 */
	@Override
	public void setContext(final ConfigContext context) {
		super.setContext(context);
		autoIterationsElement.setContext(getContext());
		iterationsElement.setContext(getContext());
		opacityElement.setContext(getContext());
		lockedElement.setContext(context);
		enabledElement.setContext(context);
		extensionElement.setContext(context);
		labelElement.setContext(context);
	}

	/**
	 * @param label
	 */
	public void setLabel(final String label) {
		labelElement.setValue(label);
	}

	/**
	 * @return
	 */
	public String getLabel() {
		return labelElement.getValue();
	}

	/**
	 * @return
	 */
	public ConfigurableExtensionReference<IncolouringFormulaExtensionConfig> getReference() {
		return extensionElement.getReference();
	}

	/**
	 * @param reference
	 */
	public void setReference(final ConfigurableExtensionReference<IncolouringFormulaExtensionConfig> reference) {
		extensionElement.setReference(reference);
	}

	/**
	 * @return
	 */
	public ConfigurableExtensionReferenceElement<IncolouringFormulaExtensionConfig> getExtensionElement() {
		return extensionElement;
	}

	/**
	 * @return the opacity
	 */
	public Integer getOpacity() {
		return opacityElement.getValue();
	}

	/**
	 * @param opacity the opacity to set
	 */
	public void setOpacity(final Integer opacity) {
		opacityElement.setValue(opacity);
	}

	/**
	 * @return
	 */
	public PercentageElement getOpacityElement() {
		return opacityElement;
	}

	/**
	 * @return
	 */
	public Integer getIterations() {
		return iterationsElement.getValue();
	}

	/**
	 * @param iterations
	 */
	public void setIterations(final Integer iterations) {
		iterationsElement.setValue(iterations);
	}

	/**
	 * @return
	 */
	public IterationsElement getIterationsElement() {
		return iterationsElement;
	}

	/**
	 * @return
	 */
	public boolean getAutoIterations() {
		return autoIterationsElement.getValue();
	}

	/**
	 * @return
	 */
	public void setAutoIterations(final boolean autoIterations) {
		autoIterationsElement.setValue(autoIterations);
	}

	/**
	 * @return the autoIterationsElement
	 */
	public BooleanElement getAutoIterationsElement() {
		return autoIterationsElement;
	}

	/**
	 * @return
	 */
	public boolean isLocked() {
		return lockedElement.getValue();
	}

	/**
	 * @param locked
	 */
	public void setLocked(final boolean locked) {
		lockedElement.setValue(locked);
	}

	/**
	 * @return
	 */
	public BooleanElement getLockedElement() {
		return lockedElement;
	}

	/**
	 * @return
	 */
	public boolean isEnabled() {
		return enabledElement.getValue();
	}

	/**
	 * @param enabled
	 */
	public void setEnabled(final boolean enabled) {
		enabledElement.setValue(enabled);
	}

	/**
	 * @return
	 */
	public BooleanElement getEnabledElement() {
		return enabledElement;
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
		final IncolouringFormulaConfigElement other = (IncolouringFormulaConfigElement) obj;
		if (autoIterationsElement == null) {
			if (other.autoIterationsElement != null) {
				return false;
			}
		}
		else if (!autoIterationsElement.equals(other.autoIterationsElement)) {
			return false;
		}
		if (enabledElement == null) {
			if (other.enabledElement != null) {
				return false;
			}
		}
		else if (!enabledElement.equals(other.enabledElement)) {
			return false;
		}
		if (extensionElement == null) {
			if (other.extensionElement != null) {
				return false;
			}
		}
		else if (!extensionElement.equals(other.extensionElement)) {
			return false;
		}
		if (iterationsElement == null) {
			if (other.iterationsElement != null) {
				return false;
			}
		}
		else if (!iterationsElement.equals(other.iterationsElement)) {
			return false;
		}
		if (lockedElement == null) {
			if (other.lockedElement != null) {
				return false;
			}
		}
		else if (!lockedElement.equals(other.lockedElement)) {
			return false;
		}
		if (opacityElement == null) {
			if (other.opacityElement != null) {
				return false;
			}
		}
		else if (!opacityElement.equals(other.opacityElement)) {
			return false;
		}
		return true;
	}

	/**
	 * @see net.sf.jame.core.config.AbstractConfigElement#dispose()
	 */
	@Override
	public void dispose() {
		extensionElement.dispose();
		lockedElement.dispose();
		enabledElement.dispose();
		opacityElement.dispose();
		autoIterationsElement.dispose();
		iterationsElement.dispose();
		labelElement.dispose();
		super.dispose();
	}

	/**
	 * @return
	 */
	public StringElement getLabelElement() {
		return labelElement;
	}
}
