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
package net.sf.jame.mandelbrot.outcolouringFormula;

import net.sf.jame.core.common.ExtensionReferenceElement;
import net.sf.jame.core.config.RuntimeElement;
import net.sf.jame.core.config.ValueChangeEvent;
import net.sf.jame.core.config.ValueChangeListener;
import net.sf.jame.core.config.ValueConfigElement;
import net.sf.jame.core.extension.ConfigurableExtensionReference;
import net.sf.jame.core.extension.ExtensionException;
import net.sf.jame.core.extension.ExtensionNotFoundException;
import net.sf.jame.mandelbrot.MandelbrotRegistry;
import net.sf.jame.mandelbrot.outcolouringFormula.extension.OutcolouringFormulaExtensionConfig;
import net.sf.jame.mandelbrot.outcolouringFormula.extension.OutcolouringFormulaExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public class OutcolouringFormulaRuntimeElement extends RuntimeElement {
	private OutcolouringFormulaExtensionRuntime<?> formulaRuntime;
	private OutcolouringFormulaConfigElement formulaElement;
	private ExtensionListener extensionListener;
	private IterationsListener iterationsListener;
	private OpacityListener opacityListener;
	private EnabledListener enabledListener;
	private AutoIterationsListener autoIterationsListener;
	private int iterations;
	private int opacity;
	private boolean enabled;
	private boolean autoIterations;

	/**
	 * Constructs a new formula element.
	 * 
	 * @param formulaElement
	 */
	public OutcolouringFormulaRuntimeElement(final OutcolouringFormulaConfigElement formulaElement) {
		if (formulaElement == null) {
			throw new IllegalArgumentException("formulaElement is null");
		}
		this.formulaElement = formulaElement;
		setOpacity((int) Math.rint(formulaElement.getOpacity().intValue() * 255d / 100d));
		setIterations(formulaElement.getIterations());
		setEnabled(formulaElement.isEnabled());
		setAutoIterations(formulaElement.getAutoIterations());
		createRuntime(formulaElement.getReference());
		iterationsListener = new IterationsListener(this);
		formulaElement.getIterationsElement().addChangeListener(iterationsListener);
		opacityListener = new OpacityListener(this);
		formulaElement.getOpacityElement().addChangeListener(opacityListener);
		enabledListener = new EnabledListener(this);
		formulaElement.getEnabledElement().addChangeListener(enabledListener);
		autoIterationsListener = new AutoIterationsListener(this);
		formulaElement.getAutoIterationsElement().addChangeListener(autoIterationsListener);
		extensionListener = new ExtensionListener();
		formulaElement.getExtensionElement().addChangeListener(extensionListener);
	}

	/**
	 * @see net.sf.jame.core.config.RuntimeElement#dispose()
	 */
	@Override
	public void dispose() {
		if ((formulaElement != null) && (iterationsListener != null)) {
			formulaElement.getIterationsElement().removeChangeListener(iterationsListener);
		}
		if ((formulaElement != null) && (opacityListener != null)) {
			formulaElement.getOpacityElement().removeChangeListener(opacityListener);
		}
		if ((formulaElement != null) && (enabledListener != null)) {
			formulaElement.getEnabledElement().removeChangeListener(enabledListener);
		}
		if ((formulaElement != null) && (autoIterationsListener != null)) {
			formulaElement.getAutoIterationsElement().removeChangeListener(autoIterationsListener);
		}
		if ((formulaElement != null) && (extensionListener != null)) {
			formulaElement.getExtensionElement().removeChangeListener(extensionListener);
		}
		iterationsListener = null;
		opacityListener = null;
		enabledListener = null;
		autoIterationsListener = null;
		extensionListener = null;
		if (formulaRuntime != null) {
			formulaRuntime.dispose();
			formulaRuntime = null;
		}
		formulaElement = null;
		super.dispose();
	}

	@SuppressWarnings("unchecked")
	private void createRuntime(final ConfigurableExtensionReference<OutcolouringFormulaExtensionConfig> reference) {
		try {
			if (reference != null) {
				final OutcolouringFormulaExtensionRuntime formulaRuntime = MandelbrotRegistry.getInstance().getOutcolouringFormulaExtension(reference.getExtensionId()).createExtensionRuntime();
				final OutcolouringFormulaExtensionConfig formulaConfig = reference.getExtensionConfig();
				formulaRuntime.setConfig(formulaConfig);
				setFormulaRuntime(formulaRuntime);
			}
			else {
				setFormulaRuntime(null);
			}
		}
		catch (final ExtensionNotFoundException e) {
			e.printStackTrace();
		}
		catch (final ExtensionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the formulaRuntime
	 */
	public OutcolouringFormulaExtensionRuntime<?> getFormulaRuntime() {
		return formulaRuntime;
	}

	private void setFormulaRuntime(final OutcolouringFormulaExtensionRuntime<?> formulaRuntime) {
		if (this.formulaRuntime != null) {
			this.formulaRuntime.dispose();
		}
		this.formulaRuntime = formulaRuntime;
	}

	/**
	 * @return the opacity.
	 */
	public int getOpacity() {
		return opacity;
	}

	private void setOpacity(final int opacity) {
		this.opacity = opacity;
	}

	/**
	 * @return the iterations.
	 */
	public int getIterations() {
		return iterations;
	}

	private void setIterations(final int iterations) {
		this.iterations = iterations;
	}

	/**
	 * @return the enabled.
	 */
	public boolean isEnabled() {
		return enabled;
	}

	private void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return
	 */
	public boolean isAutoIterations() {
		return autoIterations;
	}

	private void setAutoIterations(final boolean autoIterations) {
		this.autoIterations = autoIterations;
	}

	/**
	 * @see net.sf.jame.core.config.RuntimeElement#isChanged()
	 */
	@Override
	public boolean isChanged() {
		final boolean formulaChanged = (formulaRuntime != null) && formulaRuntime.isChanged();
		return super.isChanged() || formulaChanged;
	}

	private class ExtensionListener implements ValueChangeListener {
		/**
		 * @see net.sf.jame.core.config.ValueChangeListener#valueChanged(net.sf.jame.core.config.ValueChangeEvent)
		 */
		@SuppressWarnings("unchecked")
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ExtensionReferenceElement.EXTENSION_REFERENCE_CHANGED: {
					createRuntime((ConfigurableExtensionReference<OutcolouringFormulaExtensionConfig>) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}

	private class OpacityListener implements ValueChangeListener {
		private final OutcolouringFormulaRuntimeElement formula;

		/**
		 * @param formula the formula.
		 */
		public OpacityListener(final OutcolouringFormulaRuntimeElement formula) {
			this.formula = formula;
		}

		/**
		 * @see net.sf.jame.core.config.ValueChangeListener#valueChanged(net.sf.jame.core.config.ValueChangeEvent)
		 */
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					formula.setOpacity((int) Math.rint((Integer) e.getParams()[0] * 255d / 100d));
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}

	private class IterationsListener implements ValueChangeListener {
		private final OutcolouringFormulaRuntimeElement formula;

		/**
		 * @param formula the formula.
		 */
		public IterationsListener(final OutcolouringFormulaRuntimeElement formula) {
			this.formula = formula;
		}

		/**
		 * @see net.sf.jame.core.config.ValueChangeListener#valueChanged(net.sf.jame.core.config.ValueChangeEvent)
		 */
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					formula.setIterations((Integer) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}

	private class EnabledListener implements ValueChangeListener {
		private final OutcolouringFormulaRuntimeElement formula;

		/**
		 * @param formula the formula.
		 */
		public EnabledListener(final OutcolouringFormulaRuntimeElement formula) {
			this.formula = formula;
		}

		/**
		 * @see net.sf.jame.core.config.ValueChangeListener#valueChanged(net.sf.jame.core.config.ValueChangeEvent)
		 */
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					formula.setEnabled((Boolean) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}

	private class AutoIterationsListener implements ValueChangeListener {
		private final OutcolouringFormulaRuntimeElement formula;

		/**
		 * @param formula the formula.
		 */
		public AutoIterationsListener(final OutcolouringFormulaRuntimeElement formula) {
			this.formula = formula;
		}

		/**
		 * @see net.sf.jame.core.config.ValueChangeListener#valueChanged(net.sf.jame.core.config.ValueChangeEvent)
		 */
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					formula.setAutoIterations((Boolean) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
}
