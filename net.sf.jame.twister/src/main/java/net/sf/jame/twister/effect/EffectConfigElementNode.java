/*
 * JAME 6.2
 * http://jame.sourceforge.net
 *
 * Copyright 2001, 2015 Andrea Medeghini
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
package net.sf.jame.twister.effect;

import net.sf.jame.core.common.BooleanElementNode;
import net.sf.jame.core.common.ConfigurableExtensionReferenceElementNode;
import net.sf.jame.core.extension.ConfigurableExtensionReference;
import net.sf.jame.core.tree.Node;
import net.sf.jame.core.tree.NodeEditor;
import net.sf.jame.core.tree.NodeValue;
import net.sf.jame.core.util.AbstractConfigElementNode;
import net.sf.jame.twister.TwisterResources;
import net.sf.jame.twister.effect.extension.EffectExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public class EffectConfigElementNode extends AbstractConfigElementNode<EffectConfigElement> {
	public static final String NODE_ID = EffectConfigElement.CLASS_ID;
	public static final String NODE_CLASS = "node.class.EffectElement";
	private static final String NODE_LABEL = TwisterResources.getInstance().getString("node.label.EffectElement");
	private final EffectConfigElement effectElement;

	/**
	 * Constructs a new effect node.
	 * 
	 * @param effectElement the effect element.
	 */
	public EffectConfigElementNode(final EffectConfigElement effectElement) {
		super(EffectConfigElementNode.NODE_ID);
		if (effectElement == null) {
			throw new IllegalArgumentException("effectElement is null");
		}
		this.effectElement = effectElement;
		setNodeLabel(EffectConfigElementNode.NODE_LABEL);
		setNodeClass(EffectConfigElementNode.NODE_CLASS);
		setNodeValue(new EffectConfigElementNodeValue(effectElement));
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object o) {
		if (o instanceof EffectConfigElementNode) {
			return (effectElement == ((EffectConfigElementNode) o).effectElement);
		}
		return false;
	}

	/**
	 * @see net.sf.jame.core.util.AbstractConfigElementNode#getConfigElement()
	 */
	@Override
	public EffectConfigElement getConfigElement() {
		return effectElement;
	}

	// private ExtensionReference getReference() {
	// if ((getNodeValue() != null) && (getNodeValue().getValue() != null)) {
	// return ((EffectConfigElementNodeValue) getNodeValue()).getValue().getReference();
	// }
	// return null;
	// }
	/**
	 * @see net.sf.jame.core.tree.Node#addDescription(java.lang.StringBuilder)
	 */
	@Override
	protected void addDescription(final StringBuilder builder) {
		if (getChildNodeCount() > 0) {
			builder.append(getChildNode(0).getLabel());
		}
		else {
			super.addDescription(builder);
		}
	}

	/**
	 * @param value
	 */
	protected void createChildNodes(final EffectConfigElementNodeValue value) {
		removeAllChildNodes();
		appendChildNode(new EffectReferenceNode(EffectConfigElementNode.NODE_ID + ".extension", value.getValue()));
		appendChildNode(new EnabledElementNode(EffectConfigElementNode.NODE_ID + ".enabled", value.getValue()));
		appendChildNode(new LockedElementNode(EffectConfigElementNode.NODE_ID + ".locked", value.getValue()));
	}

	/**
	 * @see net.sf.jame.core.tree.Node#updateNode()
	 */
	@Override
	protected void updateChildNodes() {
		createChildNodes((EffectConfigElementNodeValue) getNodeValue());
	}

	/**
	 * @see net.sf.jame.core.tree.DefaultNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return new EffectNodeEditor(this);
	}

	private static class EffectReferenceNode extends ConfigurableExtensionReferenceElementNode<EffectExtensionConfig> {
		public static final String NODE_CLASS = "node.class.EffectReference";

		/**
		 * @param nodeId
		 * @param effectElement
		 */
		public EffectReferenceNode(final String nodeId, final EffectConfigElement effectElement) {
			super(nodeId, effectElement.getExtensionElement());
			setNodeClass(EffectReferenceNode.NODE_CLASS);
		}

		/**
		 * @see net.sf.jame.core.common.ConfigurableExtensionReferenceElementNode#createNodeValue(net.sf.jame.core.extension.ConfigurableExtensionReference)
		 */
		@Override
		protected NodeValue<?> createNodeValue(final ConfigurableExtensionReference<EffectExtensionConfig> value) {
			// return new EffectExtensionReferenceNodeValue(value != null ? value.clone() : null);
			return new EffectExtensionReferenceNodeValue(value);
		}
	}

	private static class LockedElementNode extends BooleanElementNode {
		private static final String NODE_LABEL = TwisterResources.getInstance().getString("node.label.LockedElement");

		/**
		 * @param nodeId
		 * @param effectElement
		 */
		public LockedElementNode(final String nodeId, final EffectConfigElement effectElement) {
			super(nodeId, effectElement.getLockedElement());
			setNodeLabel(LockedElementNode.NODE_LABEL);
		}
	}

	private static class EnabledElementNode extends BooleanElementNode {
		private static final String NODE_LABEL = TwisterResources.getInstance().getString("node.label.EnabledElement");

		/**
		 * @param nodeId
		 * @param effectElement
		 */
		public EnabledElementNode(final String nodeId, final EffectConfigElement effectElement) {
			super(nodeId, effectElement.getEnabledElement());
			setNodeLabel(EnabledElementNode.NODE_LABEL);
		}
	}

	private static class EffectNodeEditor extends NodeEditor {
		/**
		 * @param node
		 */
		public EffectNodeEditor(final Node node) {
			super(node);
		}

		/**
		 * @see net.sf.jame.core.tree.NodeEditor#createChildNode(net.sf.jame.core.tree.NodeValue)
		 */
		@Override
		protected Node createChildNode(final NodeValue<?> value) {
			return null;
		}

		/**
		 * @see net.sf.jame.core.tree.NodeEditor#createNodeValue(Object)
		 */
		@Override
		public NodeValue<?> createNodeValue(final Object value) {
			// return new EffectConfigElementNodeValue((EffectConfigElement) value != null ? ((EffectConfigElement) value).clone() : null);
			return new EffectConfigElementNodeValue((EffectConfigElement) value);
		}

		/**
		 * @see net.sf.jame.core.tree.NodeEditor#getNodeValueType()
		 */
		@Override
		public Class<?> getNodeValueType() {
			return EffectConfigElementNodeValue.class;
		}
	}
}
