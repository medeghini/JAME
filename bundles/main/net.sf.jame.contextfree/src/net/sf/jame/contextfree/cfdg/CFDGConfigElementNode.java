/*
 * $Id:$
 *
 */
package net.sf.jame.contextfree.cfdg;

import net.sf.jame.contextfree.ContextFreeResources;
import net.sf.jame.contextfree.cfdg.figure.FigureConfigElement;
import net.sf.jame.contextfree.cfdg.figure.FigureConfigElementNode;
import net.sf.jame.core.common.BooleanElementNode;
import net.sf.jame.core.common.ColorElementNode;
import net.sf.jame.core.common.FloatElementNode;
import net.sf.jame.core.common.StringElementNode;
import net.sf.jame.core.tree.Node;
import net.sf.jame.core.tree.NodeEditor;
import net.sf.jame.core.tree.NodeValue;
import net.sf.jame.core.util.AbstractConfigElementListNode;
import net.sf.jame.core.util.AbstractConfigElementNode;
import net.sf.jame.core.util.ConfigElementListNodeValue;

/**
 * @author Andrea Medeghini
 */
public class CFDGConfigElementNode extends AbstractConfigElementNode<CFDGConfigElement> {
	private static final String NODE_ID = CFDGConfigElement.CLASS_ID;
	private static final String NODE_CLASS = "node.class.CFDGElement";
	private static final String NODE_LABEL = ContextFreeResources.getInstance().getString("node.label.CFDGElement");
	private final CFDGConfigElement cfdg;

	/**
	 * Constructs a new effect node.
	 * 
	 * @param cfdg the cfdg element.
	 */
	public CFDGConfigElementNode(final CFDGConfigElement cfdg) {
		super(CFDGConfigElementNode.NODE_ID);
		if (cfdg == null) {
			throw new IllegalArgumentException("cfdg is null");
		}
		this.cfdg = cfdg;
		setNodeLabel(CFDGConfigElementNode.NODE_LABEL);
		setNodeClass(CFDGConfigElementNode.NODE_CLASS);
		setNodeValue(new CFDGConfigElementNodeValue(cfdg));
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object o) {
		if (o instanceof CFDGConfigElementNode) {
			return (cfdg == ((CFDGConfigElementNode) o).cfdg);
		}
		return false;
	}

	/**
	 * @see net.sf.jame.core.util.AbstractConfigElementNode#getConfigElement()
	 */
	@Override
	public CFDGConfigElement getConfigElement() {
		return cfdg;
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
		createChildNodes((CFDGConfigElementNodeValue) getNodeValue());
	}

	/**
	 * @see net.sf.jame.core.tree.DefaultNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return new CfdgNodeEditor(this);
	}

	/**
	 * @param value
	 */
	protected void createChildNodes(final CFDGConfigElementNodeValue value) {
		removeAllChildNodes();
		appendChildNode(new BaseDirElementNode(CFDGConfigElementNode.NODE_ID + ".baseDir", value.getValue()));
		appendChildNode(new VariationElementNode(CFDGConfigElementNode.NODE_ID + ".variation", value.getValue()));
		appendChildNode(new StartshapeElementNode(CFDGConfigElementNode.NODE_ID + ".startshape", value.getValue()));
		appendChildNode(new BackgroundElementNode(CFDGConfigElementNode.NODE_ID + ".background", value.getValue()));
		appendChildNode(new UseSizeElementNode(CFDGConfigElementNode.NODE_ID + ".useSize", value.getValue()));
		appendChildNode(new XElementNode(CFDGConfigElementNode.NODE_ID + ".x", value.getValue()));
		appendChildNode(new YElementNode(CFDGConfigElementNode.NODE_ID + ".y", value.getValue()));
		appendChildNode(new WidthElementNode(CFDGConfigElementNode.NODE_ID + ".width", value.getValue()));
		appendChildNode(new HeightElementNode(CFDGConfigElementNode.NODE_ID + ".height", value.getValue()));
		appendChildNode(new UseTileElementNode(CFDGConfigElementNode.NODE_ID + ".useTile", value.getValue()));
		appendChildNode(new TileWidthElementNode(CFDGConfigElementNode.NODE_ID + ".tileWidth", value.getValue()));
		appendChildNode(new TileHeightElementNode(CFDGConfigElementNode.NODE_ID + ".tileHeight", value.getValue()));
		appendChildNode(new FigureListElementNode(CFDGConfigElementNode.NODE_ID + ".figureList", value.getValue()));
	}

	private static class CfdgNodeEditor extends NodeEditor {
		/**
		 * @param node
		 */
		public CfdgNodeEditor(final Node node) {
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
			return new CFDGConfigElementNodeValue((CFDGConfigElement) value);
		}

		/**
		 * @see net.sf.jame.core.tree.NodeEditor#getNodeValueType()
		 */
		@Override
		public Class<?> getNodeValueType() {
			return CFDGConfigElementNodeValue.class;
		}
	}

	private static class BaseDirElementNode extends StringElementNode {
		private static final String NODE_LABEL = ContextFreeResources.getInstance().getString("node.label.BaseDirElement");

		/**
		 * @param nodeId
		 * @param cfdg
		 */
		public BaseDirElementNode(final String nodeId, final CFDGConfigElement cfdg) {
			super(nodeId, cfdg.getBaseDirElement());
			setNodeLabel(BaseDirElementNode.NODE_LABEL);
		}
	}
	private static class VariationElementNode extends StringElementNode {
		private static final String NODE_LABEL = ContextFreeResources.getInstance().getString("node.label.VariationElement");

		/**
		 * @param nodeId
		 * @param cfdg
		 */
		public VariationElementNode(final String nodeId, final CFDGConfigElement cfdg) {
			super(nodeId, cfdg.getVariationElement());
			setNodeLabel(VariationElementNode.NODE_LABEL);
		}
	}
	private static class StartshapeElementNode extends StringElementNode {
		private static final String NODE_LABEL = ContextFreeResources.getInstance().getString("node.label.StartshapeElement");

		/**
		 * @param nodeId
		 * @param cfdg
		 */
		public StartshapeElementNode(final String nodeId, final CFDGConfigElement cfdg) {
			super(nodeId, cfdg.getStartshapeElement());
			setNodeLabel(StartshapeElementNode.NODE_LABEL);
		}
	}
	private static class BackgroundElementNode extends ColorElementNode {
		private static final String NODE_LABEL = ContextFreeResources.getInstance().getString("node.label.BackgroundElement");

		/**
		 * @param nodeId
		 * @param cfdg
		 */
		public BackgroundElementNode(final String nodeId, final CFDGConfigElement cfdg) {
			super(nodeId, cfdg.getBackgroundElement());
			setNodeLabel(BackgroundElementNode.NODE_LABEL);
		}
	}
	private static class UseSizeElementNode extends BooleanElementNode {
		private static final String NODE_LABEL = ContextFreeResources.getInstance().getString("node.label.UseSizeElement");

		/**
		 * @param nodeId
		 * @param cfdg
		 */
		public UseSizeElementNode(final String nodeId, final CFDGConfigElement cfdg) {
			super(nodeId, cfdg.getUseSizeElement());
			setNodeLabel(UseSizeElementNode.NODE_LABEL);
		}
	}
	private static class XElementNode extends FloatElementNode {
		private static final String NODE_LABEL = ContextFreeResources.getInstance().getString("node.label.XElement");

		/**
		 * @param nodeId
		 * @param cfdg
		 */
		public XElementNode(final String nodeId, final CFDGConfigElement cfdg) {
			super(nodeId, cfdg.getXElement());
			setNodeLabel(XElementNode.NODE_LABEL);
		}
	}
	private static class YElementNode extends FloatElementNode {
		private static final String NODE_LABEL = ContextFreeResources.getInstance().getString("node.label.YElement");

		/**
		 * @param nodeId
		 * @param cfdg
		 */
		public YElementNode(final String nodeId, final CFDGConfigElement cfdg) {
			super(nodeId, cfdg.getYElement());
			setNodeLabel(YElementNode.NODE_LABEL);
		}
	}
	private static class WidthElementNode extends FloatElementNode {
		private static final String NODE_LABEL = ContextFreeResources.getInstance().getString("node.label.WidthElement");

		/**
		 * @param nodeId
		 * @param cfdg
		 */
		public WidthElementNode(final String nodeId, final CFDGConfigElement cfdg) {
			super(nodeId, cfdg.getWidthElement());
			setNodeLabel(WidthElementNode.NODE_LABEL);
		}
	}
	private static class HeightElementNode extends FloatElementNode {
		private static final String NODE_LABEL = ContextFreeResources.getInstance().getString("node.label.HeightElement");

		/**
		 * @param nodeId
		 * @param cfdg
		 */
		public HeightElementNode(final String nodeId, final CFDGConfigElement cfdg) {
			super(nodeId, cfdg.getHeightElement());
			setNodeLabel(HeightElementNode.NODE_LABEL);
		}
	}
	private static class UseTileElementNode extends BooleanElementNode {
		private static final String NODE_LABEL = ContextFreeResources.getInstance().getString("node.label.UseTileElement");

		/**
		 * @param nodeId
		 * @param cfdg
		 */
		public UseTileElementNode(final String nodeId, final CFDGConfigElement cfdg) {
			super(nodeId, cfdg.getUseTileElement());
			setNodeLabel(UseTileElementNode.NODE_LABEL);
		}
	}
	private static class TileWidthElementNode extends FloatElementNode {
		private static final String NODE_LABEL = ContextFreeResources.getInstance().getString("node.label.TileWidthElement");

		/**
		 * @param nodeId
		 * @param cfdg
		 */
		public TileWidthElementNode(final String nodeId, final CFDGConfigElement cfdg) {
			super(nodeId, cfdg.getTileWidthElement());
			setNodeLabel(TileWidthElementNode.NODE_LABEL);
		}
	}
	private static class TileHeightElementNode extends FloatElementNode {
		private static final String NODE_LABEL = ContextFreeResources.getInstance().getString("node.label.TileHeightElement");

		/**
		 * @param nodeId
		 * @param cfdg
		 */
		public TileHeightElementNode(final String nodeId, final CFDGConfigElement cfdg) {
			super(nodeId, cfdg.getTileHeightElement());
			setNodeLabel(TileHeightElementNode.NODE_LABEL);
		}
	}
	private static class FigureListElementNode extends AbstractConfigElementListNode<FigureConfigElement> {
		public static final String NODE_CLASS = "node.class.FigureListElement";

		/**
		 * @param nodeId
		 * @param cfdg
		 */
		public FigureListElementNode(final String nodeId, final CFDGConfigElement cfdg) {
			super(nodeId, cfdg.getFigureListElement());
			setNodeClass(FigureListElementNode.NODE_CLASS);
			setNodeLabel(ContextFreeResources.getInstance().getString("node.label.FigureListElement"));
		}

		/**
		 * @see net.sf.jame.core.util.AbstractConfigElementListNode#createChildNode(net.sf.jame.core.config.ConfigElement)
		 */
		@Override
		protected AbstractConfigElementNode<FigureConfigElement> createChildNode(final FigureConfigElement value) {
			return new FigureConfigElementNode(value);
		}

		/**
		 * @see net.sf.jame.core.util.AbstractConfigElementListNode#getChildValueType()
		 */
		@Override
		public Class<?> getChildValueType() {
			return FigureConfigElementNodeValue.class;
		}

		/**
		 * @see net.sf.jame.core.util.AbstractConfigElementListNode#createNodeValue(Object)
		 */
		@Override
		public NodeValue<FigureConfigElement> createNodeValue(final Object value) {
			return new FigureConfigElementNodeValue((FigureConfigElement) value);
		}

		private class FigureConfigElementNodeValue extends ConfigElementListNodeValue<FigureConfigElement> {
			private static final long serialVersionUID = 1L;

			/**
			 * @param value
			 */
			public FigureConfigElementNodeValue(final FigureConfigElement value) {
				super(value);
			}
		}
	}
}
