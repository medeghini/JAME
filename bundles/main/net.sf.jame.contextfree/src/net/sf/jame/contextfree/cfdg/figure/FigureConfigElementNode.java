/*
 * $Id:$
 *
 */
package net.sf.jame.contextfree.cfdg.figure;

import net.sf.jame.contextfree.ContextFreeResources;
import net.sf.jame.contextfree.cfdg.figure.extension.FigureExtensionConfig;
import net.sf.jame.core.common.ConfigurableExtensionReferenceElementNode;
import net.sf.jame.core.extension.ConfigurableExtensionReference;
import net.sf.jame.core.tree.Node;
import net.sf.jame.core.tree.NodeEditor;
import net.sf.jame.core.tree.NodeValue;
import net.sf.jame.core.util.AbstractConfigElementNode;

/**
 * @author Andrea Medeghini
 */
public class FigureConfigElementNode extends AbstractConfigElementNode<FigureConfigElement> {
	private static final String NODE_ID = FigureConfigElement.CLASS_ID;
	private static final String NODE_CLASS = "node.class.FigureElement";
	private static final String NODE_LABEL = ContextFreeResources.getInstance().getString("node.label.FigureElement");
	private final FigureConfigElement figure;

	/**
	 * Constructs a new effect node.
	 * 
	 * @param figure the figure element.
	 */
	public FigureConfigElementNode(final FigureConfigElement figure) {
		super(FigureConfigElementNode.NODE_ID);
		if (figure == null) {
			throw new IllegalArgumentException("figure is null");
		}
		this.figure = figure;
		setNodeLabel(FigureConfigElementNode.NODE_LABEL);
		setNodeClass(FigureConfigElementNode.NODE_CLASS);
		setNodeValue(new FigureConfigElementNodeValue(figure));
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object o) {
		if (o instanceof FigureConfigElementNode) {
			return (figure == ((FigureConfigElementNode) o).figure);
		}
		return false;
	}

	/**
	 * @see net.sf.jame.core.util.AbstractConfigElementNode#getConfigElement()
	 */
	@Override
	public FigureConfigElement getConfigElement() {
		return figure;
	}

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
	 * @see net.sf.jame.core.tree.Node#updateNode()
	 */
	@Override
	protected void updateChildNodes() {
		createChildNodes((FigureConfigElementNodeValue) getNodeValue());
	}

	/**
	 * @see net.sf.jame.core.tree.DefaultNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return new FigureNodeEditor(this);
	}

	/**
	 * @param value
	 */
	protected void createChildNodes(final FigureConfigElementNodeValue value) {
		removeAllChildNodes();
		appendChildNode(new ExtensionElementNode(FigureConfigElementNode.NODE_ID + ".extension", value.getValue()));
	}

	private static class FigureNodeEditor extends NodeEditor {
		/**
		 * @param node
		 */
		public FigureNodeEditor(final Node node) {
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
			return new FigureConfigElementNodeValue((FigureConfigElement) value);
		}

		/**
		 * @see net.sf.jame.core.tree.NodeEditor#getNodeValueType()
		 */
		@Override
		public Class<?> getNodeValueType() {
			return FigureConfigElementNodeValue.class;
		}
	}

	private static class ExtensionElementNode extends ConfigurableExtensionReferenceElementNode<FigureExtensionConfig> {
		public static final String NODE_CLASS = "node.class.FigureReference";

		/**
		 * @param nodeId
		 * @param figure
		 */
		public ExtensionElementNode(final String nodeId, final FigureConfigElement figure) {
			super(nodeId, figure.getExtensionElement());
			setNodeClass(ExtensionElementNode.NODE_CLASS);
		}

		/**
		 * @see net.sf.jame.core.common.ConfigurableExtensionReferenceElementNode#createNodeValue(net.sf.jame.core.extension.ConfigurableExtensionReference)
		 */
		@Override
		protected NodeValue<?> createNodeValue(final ConfigurableExtensionReference<FigureExtensionConfig> value) {
			return new FigureExtensionReferenceNodeValue(value);
		}
	}
}
