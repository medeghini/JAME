/*
 * $Id:$
 *
 */
package net.sf.jame.contextfree.extensions.shapeReplacement;

import net.sf.jame.contextfree.cfdg.shapeAdjustment.ShapeAdjustmentConfigElement;
import net.sf.jame.contextfree.cfdg.shapeAdjustment.ShapeAdjustmentRuntimeElement;
import net.sf.jame.contextfree.cfdg.shapeReplacement.ShapeReplacementConfigElement;
import net.sf.jame.contextfree.cfdg.shapeReplacement.ShapeReplacementRuntimeElement;
import net.sf.jame.contextfree.cfdg.shapeReplacement.extension.ShapeReplacementExtensionRuntime;
import net.sf.jame.contextfree.renderer.ContextFreeBounds;
import net.sf.jame.contextfree.renderer.ContextFreeContext;
import net.sf.jame.contextfree.renderer.ContextFreeState;
import net.sf.jame.core.config.ListConfigElement;
import net.sf.jame.core.config.ListRuntimeElement;
import net.sf.jame.core.config.ValueChangeEvent;
import net.sf.jame.core.config.ValueChangeListener;
import net.sf.jame.core.config.ValueConfigElement;

/**
 * @author Andrea Medeghini
 */
public class MultiShapeReplacementRuntime<T extends MultiShapeReplacementConfig> extends ShapeReplacementExtensionRuntime<T> {
	private Integer times;
	private TimesListener timesListener;
	private ListRuntimeElement<ShapeReplacementRuntimeElement> shapeReplacementListElement;
	private ShapeReplacementListElementListener shapeReplacementListElementListener;
	private ListRuntimeElement<ShapeAdjustmentRuntimeElement> shapeAdjustmentListElement;
	private ShapeAdjustmentListElementListener shapeAdjustmentListElementListener;

	/**
	 * @see net.sf.jame.core.extension.ConfigurableExtensionRuntime#configReloaded()
	 */
	@Override
	public void configReloaded() {
		setTimes(getConfig().getTimes());
		timesListener = new TimesListener();
		getConfig().getTimesElement().addChangeListener(timesListener);
		shapeReplacementListElement = new ListRuntimeElement<ShapeReplacementRuntimeElement>();
		for (int i = 0; i < getConfig().getShapeReplacementConfigElementCount(); i++) {
			shapeReplacementListElement.appendElement(new ShapeReplacementRuntimeElement(getConfig().getShapeReplacementConfigElement(i)));
		}
		shapeReplacementListElementListener = new ShapeReplacementListElementListener();
		getConfig().getShapeReplacementListElement().addChangeListener(shapeReplacementListElementListener);
		shapeAdjustmentListElement = new ListRuntimeElement<ShapeAdjustmentRuntimeElement>();
		for (int i = 0; i < getConfig().getShapeAdjustmentConfigElementCount(); i++) {
			shapeAdjustmentListElement.appendElement(new ShapeAdjustmentRuntimeElement(getConfig().getShapeAdjustmentConfigElement(i)));
		}
		shapeAdjustmentListElementListener = new ShapeAdjustmentListElementListener();
		getConfig().getShapeAdjustmentListElement().addChangeListener(shapeAdjustmentListElementListener);
	}

	@Override
	public void dispose() {
		if ((getConfig() != null) && (timesListener != null)) {
			getConfig().getTimesElement().removeChangeListener(timesListener);
		}
		timesListener = null;
		if ((getConfig() != null) && (shapeReplacementListElementListener != null)) {
			getConfig().getShapeReplacementListElement().removeChangeListener(shapeReplacementListElementListener);
		}
		shapeReplacementListElementListener = null;
		if ((getConfig() != null) && (shapeAdjustmentListElementListener != null)) {
			getConfig().getShapeAdjustmentListElement().removeChangeListener(shapeAdjustmentListElementListener);
		}
		shapeAdjustmentListElementListener = null;
		super.dispose();
	}
	
	/**
	 * @return the times.
	 */
	public Integer getTimes() {
		return times;
	}

	private void setTimes(final Integer times) {
		this.times = times;
	}
	
	private class TimesListener implements ValueChangeListener {
		/**
		 * @see net.sf.jame.core.config.ValueChangeListener#valueChanged(net.sf.jame.core.config.ValueChangeEvent)
		 */
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setTimes((Integer) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	/**
	 * Returns a shapeReplacement element.
	 * 
	 * @param index the shapeReplacement index.
	 * @return the shapeReplacement.
	 */
	public ShapeReplacementRuntimeElement getShapeReplacementElement(final int index) {
		return shapeReplacementListElement.getElement(index);
	}

	/**
	 * Returns a shapeReplacement element index.
	 * 
	 * @param shapeReplacementElement the shapeReplacement element.
	 * @return the index.
	 */
	public int indexOfShapeReplacementElement(final ShapeReplacementRuntimeElement shapeReplacementElement) {
		return shapeReplacementListElement.indexOfElement(shapeReplacementElement);
	}

	/**
	 * Returns the number of shapeReplacement elements.
	 * 
	 * @return the number of shapeReplacement elements.
	 */
	public int getShapeReplacementElementCount() {
		return shapeReplacementListElement.getElementCount();
	}

	private void setShapeReplacementElement(final int index, ShapeReplacementRuntimeElement element) {
		shapeReplacementListElement.setElement(index, element);
	}

	private void appendShapeReplacementElement(final ShapeReplacementRuntimeElement shapeReplacementElement) {
		shapeReplacementListElement.appendElement(shapeReplacementElement);
	}

	private void insertShapeReplacementElementAfter(final int index, final ShapeReplacementRuntimeElement shapeReplacementElement) {
		shapeReplacementListElement.insertElementAfter(index, shapeReplacementElement);
	}

	private void insertShapeReplacementElementBefore(final int index, final ShapeReplacementRuntimeElement shapeReplacementElement) {
		shapeReplacementListElement.insertElementBefore(index, shapeReplacementElement);
	}

	private void removeShapeReplacementElement(final int index) {
		shapeReplacementListElement.removeElement(index);
	}

	private void moveUpShapeReplacementElement(final int index) {
		shapeReplacementListElement.moveElementUp(index);
	}

	private void moveDownShapeReplacementElement(final int index) {
		shapeReplacementListElement.moveElementDown(index);
	}
	
	private class ShapeReplacementListElementListener implements ValueChangeListener {
		/**
		 * @see net.sf.jame.core.config.ValueChangeListener#valueChanged(net.sf.jame.core.config.ValueChangeEvent)
		 */
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ListConfigElement.ELEMENT_ADDED: {
					appendShapeReplacementElement(new ShapeReplacementRuntimeElement ((ShapeReplacementConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_INSERTED_AFTER: {
					insertShapeReplacementElementAfter(((Integer) e.getParams()[1]).intValue(), new ShapeReplacementRuntimeElement ((ShapeReplacementConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_INSERTED_BEFORE: {
					insertShapeReplacementElementBefore(((Integer) e.getParams()[1]).intValue(), new ShapeReplacementRuntimeElement ((ShapeReplacementConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_REMOVED: {
					removeShapeReplacementElement(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_MOVED_UP: {
					moveUpShapeReplacementElement(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_MOVED_DOWN: {
					moveDownShapeReplacementElement(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_CHANGED: {
					setShapeReplacementElement(((Integer) e.getParams()[1]).intValue(), new ShapeReplacementRuntimeElement ((ShapeReplacementConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	/**
	 * Returns a shapeAdjustment element.
	 * 
	 * @param index the shapeAdjustment index.
	 * @return the shapeAdjustment.
	 */
	public ShapeAdjustmentRuntimeElement getShapeAdjustmentElement(final int index) {
		return shapeAdjustmentListElement.getElement(index);
	}

	/**
	 * Returns a shapeAdjustment element index.
	 * 
	 * @param shapeAdjustmentElement the shapeAdjustment element.
	 * @return the index.
	 */
	public int indexOfShapeAdjustmentElement(final ShapeAdjustmentRuntimeElement shapeAdjustmentElement) {
		return shapeAdjustmentListElement.indexOfElement(shapeAdjustmentElement);
	}

	/**
	 * Returns the number of shapeAdjustment elements.
	 * 
	 * @return the number of shapeAdjustment elements.
	 */
	public int getShapeAdjustmentElementCount() {
		return shapeAdjustmentListElement.getElementCount();
	}

	private void setShapeAdjustmentElement(final int index, ShapeAdjustmentRuntimeElement element) {
		shapeAdjustmentListElement.setElement(index, element);
	}

	private void appendShapeAdjustmentElement(final ShapeAdjustmentRuntimeElement shapeAdjustmentElement) {
		shapeAdjustmentListElement.appendElement(shapeAdjustmentElement);
	}

	private void insertShapeAdjustmentElementAfter(final int index, final ShapeAdjustmentRuntimeElement shapeAdjustmentElement) {
		shapeAdjustmentListElement.insertElementAfter(index, shapeAdjustmentElement);
	}

	private void insertShapeAdjustmentElementBefore(final int index, final ShapeAdjustmentRuntimeElement shapeAdjustmentElement) {
		shapeAdjustmentListElement.insertElementBefore(index, shapeAdjustmentElement);
	}

	private void removeShapeAdjustmentElement(final int index) {
		shapeAdjustmentListElement.removeElement(index);
	}

	private void moveUpShapeAdjustmentElement(final int index) {
		shapeAdjustmentListElement.moveElementUp(index);
	}

	private void moveDownShapeAdjustmentElement(final int index) {
		shapeAdjustmentListElement.moveElementDown(index);
	}
	
	private class ShapeAdjustmentListElementListener implements ValueChangeListener {
		/**
		 * @see net.sf.jame.core.config.ValueChangeListener#valueChanged(net.sf.jame.core.config.ValueChangeEvent)
		 */
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ListConfigElement.ELEMENT_ADDED: {
					appendShapeAdjustmentElement(new ShapeAdjustmentRuntimeElement ((ShapeAdjustmentConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_INSERTED_AFTER: {
					insertShapeAdjustmentElementAfter(((Integer) e.getParams()[1]).intValue(), new ShapeAdjustmentRuntimeElement ((ShapeAdjustmentConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_INSERTED_BEFORE: {
					insertShapeAdjustmentElementBefore(((Integer) e.getParams()[1]).intValue(), new ShapeAdjustmentRuntimeElement ((ShapeAdjustmentConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_REMOVED: {
					removeShapeAdjustmentElement(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_MOVED_UP: {
					moveUpShapeAdjustmentElement(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_MOVED_DOWN: {
					moveDownShapeAdjustmentElement(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_CHANGED: {
					setShapeAdjustmentElement(((Integer) e.getParams()[1]).intValue(), new ShapeAdjustmentRuntimeElement ((ShapeAdjustmentConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	
	public void createShapes(ContextFreeContext context, ContextFreeState state, ContextFreeBounds globalBounds, ContextFreeBounds shapeBounds) {
		for (int i = 0; i < shapeReplacementListElement.getElementCount(); i++) {
			ShapeReplacementRuntimeElement shapeReplacementRuntime = shapeReplacementListElement.getElement(i);
			ContextFreeState newState = state.clone(); 
			shapeReplacementRuntime.createShapes(context, newState, globalBounds, shapeBounds);
		}
		for (int t = 1; t < times; t++) {
			for (int i = 0; i < shapeAdjustmentListElement.getElementCount(); i++) {
				ShapeAdjustmentRuntimeElement shapeAdjustmentRuntime = shapeAdjustmentListElement.getElement(i);
				shapeAdjustmentRuntime.updateState(state);
			}
			for (int i = 0; i < shapeReplacementListElement.getElementCount(); i++) {
				ShapeReplacementRuntimeElement shapeReplacementRuntime = shapeReplacementListElement.getElement(i);
				ContextFreeState newState = state.clone(); 
				shapeReplacementRuntime.createShapes(context, newState, globalBounds, shapeBounds);
			}
		}
	}
}
