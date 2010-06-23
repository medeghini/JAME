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
package net.sf.jame.twister.frame.layer;

import net.sf.jame.core.tree.Node;
import net.sf.jame.core.tree.NodeEditor;
import net.sf.jame.core.tree.NodeValue;
import net.sf.jame.core.util.AbstractConfigElementListNode;
import net.sf.jame.core.util.AbstractConfigElementNode;
import net.sf.jame.core.util.ConfigElementListNodeValue;
import net.sf.jame.twister.TwisterResources;

/**
 * @author Andrea Medeghini
 */
public class GroupLayerConfigElementNode extends LayerConfigElementNode<GroupLayerConfigElement> {
	public static final String NODE_ID = GroupLayerConfigElement.CLASS_ID;
	public static final String NODE_CLASS = "node.class.GroupLayerElement";
	private static final String NODE_LABEL = TwisterResources.getInstance().getString("node.label.GroupLayerElement");

	/**
	 * Constructs a new layer node.
	 * 
	 * @param layerElement the layer element.
	 */
	public GroupLayerConfigElementNode(final GroupLayerConfigElement layerElement) {
		super(GroupLayerConfigElementNode.NODE_ID, layerElement);
		setNodeClass(GroupLayerConfigElementNode.NODE_CLASS);
		setNodeLabel(GroupLayerConfigElementNode.NODE_LABEL);
		setNodeValue(new GroupLayerConfigElementNodeValue(layerElement));
	}

	/**
	 * 
	 */
	@Override
	protected void createChildNodes() {
		createLayerNodes(getConfigElement());
		super.createChildNodes();
	}

	/**
	 * @param layerElement
	 */
	protected void createLayerNodes(final GroupLayerConfigElement layerElement) {
		appendChildNode(new LayerListNode(layerElement));
	}

	/**
	 * @see net.sf.jame.core.tree.DefaultNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return new GroupLayerNodeEditor(this);
	}

	/**
	 * @see net.sf.jame.core.tree.Node#addLabel(java.lang.StringBuilder)
	 */
	@Override
	protected void addLabel(final StringBuilder builder) {
		builder.append(GroupLayerConfigElementNode.NODE_LABEL);
	}

	private class GroupLayerNodeEditor extends NodeEditor {
		/**
		 * @param node
		 */
		public GroupLayerNodeEditor(final Node node) {
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
		 * @see net.sf.jame.core.tree.NodeEditor#getNodeValueType()
		 */
		@Override
		public Class<?> getNodeValueType() {
			return GroupLayerConfigElementNodeValue.class;
		}

		/**
		 * @see net.sf.jame.core.tree.NodeEditor#createNodeValue(Object)
		 */
		@Override
		public NodeValue<?> createNodeValue(final Object value) {
			// return new GroupLayerConfigElementNodeValue((GroupLayerConfigElement) value != null ? ((GroupLayerConfigElement) value).clone() : null);
			return new GroupLayerConfigElementNodeValue((GroupLayerConfigElement) value);
		}
	}

	private class LayerListNode extends AbstractConfigElementListNode<ImageLayerConfigElement> {
		private final String NODE_LABEL = TwisterResources.getInstance().getString("node.label.ImageLayerElementList");
		public static final String NODE_CLASS = "node.class.ImageLayerElementList";

		/**
		 * @param frameElement
		 */
		public LayerListNode(final GroupLayerConfigElement frameElement) {
			super(GroupLayerConfigElementNode.this.getNodeId() + ".layers", frameElement.getLayerListElement());
			setNodeLabel(NODE_LABEL);
			setNodeClass(LayerListNode.NODE_CLASS);
		}

		/**
		 * @see net.sf.jame.core.util.AbstractConfigElementListNode#createChildNode(net.sf.jame.core.config.ConfigElement)
		 */
		@Override
		protected AbstractConfigElementNode<ImageLayerConfigElement> createChildNode(final ImageLayerConfigElement value) {
			return new ImageLayerConfigElementNode(value);
		}

		/**
		 * @see net.sf.jame.core.util.AbstractConfigElementListNode#getChildValueType()
		 */
		@Override
		public Class<?> getChildValueType() {
			return ImageLayerConfigElementNodeValue.class;
		}

		/**
		 * @see net.sf.jame.core.util.AbstractConfigElementListNode#createNodeValue(Object)
		 */
		@Override
		public NodeValue<ImageLayerConfigElement> createNodeValue(final Object value) {
			// return new ImageLayerConfigElementNodeValue((ImageLayerConfigElement) value != null ? ((ImageLayerConfigElement) value).clone() : null);
			return new ImageLayerConfigElementNodeValue((ImageLayerConfigElement) value);
		}

		private class ImageLayerConfigElementNodeValue extends ConfigElementListNodeValue<ImageLayerConfigElement> {
			private static final long serialVersionUID = 1L;

			/**
			 * @param value
			 */
			public ImageLayerConfigElementNodeValue(final ImageLayerConfigElement value) {
				super(value);
			}
		}
	}
}
