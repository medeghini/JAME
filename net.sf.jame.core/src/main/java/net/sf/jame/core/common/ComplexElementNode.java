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
package net.sf.jame.core.common;

import net.sf.jame.core.config.ValueChangeEvent;
import net.sf.jame.core.config.ValueChangeListener;
import net.sf.jame.core.config.ValueConfigElement;
import net.sf.jame.core.tree.*;
import net.sf.jame.core.util.DoubleVector2D;

/**
 * @author Andrea Medeghini
 */
public class ComplexElementNode extends AttributeNode {
	public static final String NODE_CLASS = "node.class.ComplexElement";
	private final ConfigElementListener listener;
	private final ValueConfigElement<DoubleVector2D> configElement;

	/**
	 * @param nodeId
	 */
	public ComplexElementNode(final String nodeId, final ValueConfigElement<DoubleVector2D> configElement) {
		super(nodeId);
		setNodeClass(ComplexElementNode.NODE_CLASS);
		this.configElement = configElement;
		listener = new ConfigElementListener();
		setNodeValue(new ComplexElementNodeValue(configElement.getValue()));
	}

	/**
	 * @see net.sf.jame.core.tree.Node#dispose()
	 */
	@Override
	public void dispose() {
		if (configElement != null) {
			configElement.removeChangeListener(listener);
		}
		super.dispose();
	}

	/**
	 * @see net.sf.jame.core.tree.Node#setSession(net.sf.jame.core.tree.NodeSession)
	 */
	@Override
	public void setSession(final NodeSession session) {
		if (session != null) {
			configElement.addChangeListener(listener);
		}
		else {
			configElement.removeChangeListener(listener);
		}
		super.setSession(session);
	}

	/**
	 * @see net.sf.jame.core.tree.Node#nodeAdded()
	 */
	@Override
	protected void nodeAdded() {
		setNodeValue(new ComplexElementNodeValue(configElement.getValue()));
	}

	/**
	 * @see net.sf.jame.core.tree.Node#nodeRemoved()
	 */
	@Override
	protected void nodeRemoved() {
	}

	/**
	 * @see net.sf.jame.core.tree.Node#isEditable()
	 */
	@Override
	public boolean isEditable() {
		return true;
	}

	/**
	 * @see net.sf.jame.core.tree.DefaultNode#getValueAsString()
	 */
	@Override
	public String getValueAsString() {
		if (getNodeValue() != null) {
			return ((ComplexElementNodeValue) getNodeValue()).getValue().toString();
		}
		else {
			return "";
		}
	}

	/**
	 * @see net.sf.jame.core.common.ComplexElementNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return new ComplexNodeEditor(this);
	}

	protected class ComplexNodeEditor extends NodeEditor {
		/**
		 * @param node
		 */
		public ComplexNodeEditor(final AttributeNode node) {
			super(node);
		}

		/**
		 * @see net.sf.jame.core.tree.NodeEditor#doSetValue(java.lang.NodeValue)
		 */
		@Override
		protected void doSetValue(final NodeValue<?> value) {
			configElement.removeChangeListener(listener);
			configElement.setValue(((ComplexElementNodeValue) value).getValue());
			configElement.addChangeListener(listener);
		}

		/**
		 * @see net.sf.jame.core.tree.NodeEditor#createChildNode(net.sf.jame.core.tree.NodeValue)
		 */
		@Override
		protected Node createChildNode(final NodeValue<?> value) {
			return null;
		}

		/**
		 * @see net.sf.jame.core.tree.NodeEditor#getNodeValueType()
		 */
		@Override
		public Class<?> getNodeValueType() {
			return ComplexElementNodeValue.class;
		}

		/**
		 * @see net.sf.jame.core.tree.NodeEditor#createNodeValue(Object)
		 */
		@Override
		public NodeValue<?> createNodeValue(final Object value) {
			return new ComplexElementNodeValue((DoubleVector2D) value);
		}
	}

	protected class ConfigElementListener implements ValueChangeListener {
		public void valueChanged(final ValueChangeEvent e) {
			cancel();
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setNodeValue(new ComplexElementNodeValue((DoubleVector2D) e.getParams()[0]));
					getSession().appendAction(new NodeAction(getNodeClass(), NodeAction.ACTION_SET_VALUE, e.getTimestamp(), getNodePath(), e.getParams()[0], e.getParams()[1]));
					break;
				}
				default: {
					break;
				}
			}
		}
	}
}
