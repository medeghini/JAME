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
package net.sf.jame.contextfree.extensions.pathReplacement;


import net.sf.jame.contextfree.pathAdjustment.PathAdjustmentConfigElement;
import net.sf.jame.contextfree.pathAdjustment.PathAdjustmentRuntimeElement;
import net.sf.jame.contextfree.pathReplacement.extension.PathReplacementExtensionRuntime;
import net.sf.jame.contextfree.renderer.support.CFModification;
import net.sf.jame.contextfree.renderer.support.CFPathAttribute;
import net.sf.jame.contextfree.renderer.support.CFPathCommand;
import net.sf.jame.contextfree.renderer.support.CFRule;
import net.sf.jame.core.config.ListConfigElement;
import net.sf.jame.core.config.ListRuntimeElement;
import net.sf.jame.core.config.ValueChangeEvent;
import net.sf.jame.core.config.ValueChangeListener;
import net.sf.jame.core.config.ValueConfigElement;

/**
 * @author Andrea Medeghini
 */
public class StrokePathReplacementRuntime extends PathReplacementExtensionRuntime<StrokePathReplacementConfig> {
	private Float width;
	private WidthListener widthListener;
	private String cap;
	private CapListener capListener;
	private String join;
	private JoinListener joinListener;
	private CFModification stateChange;
	private ListRuntimeElement<PathAdjustmentRuntimeElement> pathAdjustmentListElement;
	private PathAdjustmentListElementListener pathAdjustmentListElementListener;

	/**
	 * @see net.sf.jame.core.extension.ConfigurableExtensionRuntime#configReloaded()
	 */
	@Override
	public void configReloaded() {
		setWidth(getConfig().getWidth());
		widthListener = new WidthListener();
		getConfig().getWidthElement().addChangeListener(widthListener);
		setCap(getConfig().getCap());
		capListener = new CapListener();
		getConfig().getCapElement().addChangeListener(capListener);
		setJoin(getConfig().getJoin());
		joinListener = new JoinListener();
		getConfig().getJoinElement().addChangeListener(joinListener);
		pathAdjustmentListElement = new ListRuntimeElement<PathAdjustmentRuntimeElement>();
		for (int i = 0; i < getConfig().getPathAdjustmentConfigElementCount(); i++) {
			pathAdjustmentListElement.appendElement(new PathAdjustmentRuntimeElement(getConfig().getPathAdjustmentConfigElement(i)));
		}
		pathAdjustmentListElementListener = new PathAdjustmentListElementListener();
		getConfig().getPathAdjustmentListElement().addChangeListener(pathAdjustmentListElementListener);
	}

	@Override
	public void dispose() {
		if ((getConfig() != null) && (widthListener != null)) {
			getConfig().getWidthElement().removeChangeListener(widthListener);
		}
		widthListener = null;
		if ((getConfig() != null) && (capListener != null)) {
			getConfig().getCapElement().removeChangeListener(capListener);
		}
		capListener = null;
		if ((getConfig() != null) && (joinListener != null)) {
			getConfig().getJoinElement().removeChangeListener(joinListener);
		}
		joinListener = null;
		if ((getConfig() != null) && (pathAdjustmentListElementListener != null)) {
			getConfig().getPathAdjustmentListElement().removeChangeListener(pathAdjustmentListElementListener);
		}
		pathAdjustmentListElementListener = null;
		super.dispose();
	}
	
	/**
	 * @return the width.
	 */
	public Float getWidth() {
		return width;
	}

	private void setWidth(final Float width) {
		this.width = width;
	}
	
	private class WidthListener implements ValueChangeListener {
		/**
		 * @see net.sf.jame.core.config.ValueChangeListener#valueChanged(net.sf.jame.core.config.ValueChangeEvent)
		 */
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setWidth((Float) e.getParams()[0]);
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
	 * @return the cap.
	 */
	public String getCap() {
		return cap;
	}

	private void setCap(final String cap) {
		this.cap = cap;
	}
	
	private class CapListener implements ValueChangeListener {
		/**
		 * @see net.sf.jame.core.config.ValueChangeListener#valueChanged(net.sf.jame.core.config.ValueChangeEvent)
		 */
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setCap((String) e.getParams()[0]);
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
	 * @return the join.
	 */
	public String getJoin() {
		return join;
	}

	private void setJoin(final String join) {
		this.join = join;
	}
	
	private class JoinListener implements ValueChangeListener {
		/**
		 * @see net.sf.jame.core.config.ValueChangeListener#valueChanged(net.sf.jame.core.config.ValueChangeEvent)
		 */
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setJoin((String) e.getParams()[0]);
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
	 * Returns a pathAdjustment element.
	 * 
	 * @param index the pathAdjustment index.
	 * @return the pathAdjustment.
	 */
	public PathAdjustmentRuntimeElement getPathAdjustmentElement(final int index) {
		return pathAdjustmentListElement.getElement(index);
	}

	/**
	 * Returns a pathAdjustment element index.
	 * 
	 * @param pathAdjustmentElement the pathAdjustment element.
	 * @return the index.
	 */
	public int indexOfPathAdjustmentElement(final PathAdjustmentRuntimeElement pathAdjustmentElement) {
		return pathAdjustmentListElement.indexOfElement(pathAdjustmentElement);
	}

	/**
	 * Returns the number of pathAdjustment elements.
	 * 
	 * @return the number of pathAdjustment elements.
	 */
	public int getPathAdjustmentElementCount() {
		return pathAdjustmentListElement.getElementCount();
	}

	private void setPathAdjustmentElement(final int index, PathAdjustmentRuntimeElement element) {
		pathAdjustmentListElement.setElement(index, element);
	}

	private void appendPathAdjustmentElement(final PathAdjustmentRuntimeElement pathAdjustmentElement) {
		pathAdjustmentListElement.appendElement(pathAdjustmentElement);
	}

	private void insertPathAdjustmentElementAfter(final int index, final PathAdjustmentRuntimeElement pathAdjustmentElement) {
		pathAdjustmentListElement.insertElementAfter(index, pathAdjustmentElement);
	}

	private void insertPathAdjustmentElementBefore(final int index, final PathAdjustmentRuntimeElement pathAdjustmentElement) {
		pathAdjustmentListElement.insertElementBefore(index, pathAdjustmentElement);
	}

	private void removePathAdjustmentElement(final int index) {
		pathAdjustmentListElement.removeElement(index);
	}

	private void moveUpPathAdjustmentElement(final int index) {
		pathAdjustmentListElement.moveElementUp(index);
	}

	private void moveDownPathAdjustmentElement(final int index) {
		pathAdjustmentListElement.moveElementDown(index);
	}
	
	private class PathAdjustmentListElementListener implements ValueChangeListener {
		/**
		 * @see net.sf.jame.core.config.ValueChangeListener#valueChanged(net.sf.jame.core.config.ValueChangeEvent)
		 */
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ListConfigElement.ELEMENT_ADDED: {
					appendPathAdjustmentElement(new PathAdjustmentRuntimeElement ((PathAdjustmentConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_INSERTED_AFTER: {
					insertPathAdjustmentElementAfter(((Integer) e.getParams()[1]).intValue(), new PathAdjustmentRuntimeElement ((PathAdjustmentConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_INSERTED_BEFORE: {
					insertPathAdjustmentElementBefore(((Integer) e.getParams()[1]).intValue(), new PathAdjustmentRuntimeElement ((PathAdjustmentConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_REMOVED: {
					removePathAdjustmentElement(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_MOVED_UP: {
					moveUpPathAdjustmentElement(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_MOVED_DOWN: {
					moveDownPathAdjustmentElement(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_CHANGED: {
					setPathAdjustmentElement(((Integer) e.getParams()[1]).intValue(), new PathAdjustmentRuntimeElement ((PathAdjustmentConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}

	public void process(CFRule rule) {
		if (stateChange == null) {
			stateChange = new CFModification();
			for (int i = 0; i < pathAdjustmentListElement.getElementCount(); i++) {
				PathAdjustmentRuntimeElement pathAdjustmentRuntime = pathAdjustmentListElement.getElement(i);
				pathAdjustmentRuntime.apply(stateChange);
			}
		}
		rule.addAttribute(new CFPathAttribute(CFPathCommand.STROKE, stateChange, cap, join, width));
	}
}
