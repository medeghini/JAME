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
package net.sf.jame.twister.image;

import net.sf.jame.core.common.ConfigurableExtensionReferenceElementNode;
import net.sf.jame.core.extension.ConfigurableExtensionReference;
import net.sf.jame.core.tree.Node;
import net.sf.jame.core.tree.NodeEditor;
import net.sf.jame.core.tree.NodeValue;
import net.sf.jame.core.util.AbstractConfigElementNode;
import net.sf.jame.twister.TwisterResources;
import net.sf.jame.twister.image.extension.ImageExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public class ImageConfigElementNode extends AbstractConfigElementNode<ImageConfigElement> {
	public static final String NODE_ID = ImageConfigElement.CLASS_ID;
	public static final String NODE_CLASS = "node.class.ImageElement";
	private static final String NODE_LABEL = TwisterResources.getInstance().getString("node.label.ImageElement");
	private final ImageConfigElement imageElement;

	/**
	 * Constructs a new image node.
	 * 
	 * @param layerElement the image element.
	 */
	public ImageConfigElementNode(final ImageConfigElement imageElement) {
		super(ImageConfigElementNode.NODE_ID);
		if (imageElement == null) {
			throw new IllegalArgumentException("imageElement is null");
		}
		this.imageElement = imageElement;
		setNodeLabel(ImageConfigElementNode.NODE_LABEL);
		setNodeClass(ImageConfigElementNode.NODE_CLASS);
		setNodeValue(new ImageConfigElementNodeValue(imageElement));
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object o) {
		if (o instanceof ImageConfigElementNode) {
			return (imageElement == ((ImageConfigElementNode) o).imageElement);
		}
		return false;
	}

	/**
	 * @see net.sf.jame.core.util.AbstractConfigElementNode#getConfigElement()
	 */
	@Override
	public ImageConfigElement getConfigElement() {
		return imageElement;
	}

	// private ExtensionReference getReference() {
	// if ((getNodeValue() != null) && (getNodeValue().getValue() != null)) {
	// return ((ImageConfigElementNodeValue) getNodeValue()).getValue().getReference();
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
	protected void createChildNodes(final ImageConfigElementNodeValue value) {
		removeAllChildNodes();
		appendChildNode(new ImageReferenceNode(ImageConfigElementNode.NODE_ID + ".extension", value.getValue()));
	}

	/**
	 * @see net.sf.jame.core.tree.Node#updateNode()
	 */
	@Override
	protected void updateChildNodes() {
		createChildNodes((ImageConfigElementNodeValue) getNodeValue());
	}

	/**
	 * @see net.sf.jame.core.tree.DefaultNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return new ImageNodeEditor(this);
	}

	private static class ImageReferenceNode extends ConfigurableExtensionReferenceElementNode<ImageExtensionConfig> {
		public static final String NODE_CLASS = "node.class.ImageReference";

		/**
		 * @param nodeId
		 * @param imageElement
		 */
		public ImageReferenceNode(final String nodeId, final ImageConfigElement imageElement) {
			super(nodeId, imageElement.getExtensionElement());
			setNodeClass(ImageReferenceNode.NODE_CLASS);
		}

		/**
		 * @see net.sf.jame.core.common.ConfigurableExtensionReferenceElementNode#createNodeValue(net.sf.jame.core.extension.ConfigurableExtensionReference)
		 */
		@Override
		protected NodeValue<?> createNodeValue(final ConfigurableExtensionReference<ImageExtensionConfig> value) {
			// return new ImageExtensionReferenceNodeValue(value != null ? value.clone() : null);
			return new ImageExtensionReferenceNodeValue(value);
		}
	}

	private static class ImageNodeEditor extends NodeEditor {
		/**
		 * @param node
		 */
		public ImageNodeEditor(final Node node) {
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
			// return new ImageConfigElementNodeValue((ImageConfigElement) value != null ? ((ImageConfigElement) value).clone() : null);
			return new ImageConfigElementNodeValue((ImageConfigElement) value);
		}

		/**
		 * @see net.sf.jame.core.tree.NodeEditor#getNodeValueType()
		 */
		@Override
		public Class<?> getNodeValueType() {
			return ImageConfigElementNodeValue.class;
		}
	}
}
