/*
 * $Id:$
 *
 */
package net.sf.jame.contextfree.extensions.shapeReplacement;

import net.sf.jame.contextfree.ContextFreeResources;
import net.sf.jame.contextfree.cfdg.shapeAdjustment.ShapeAdjustmentConfigElement;
import net.sf.jame.contextfree.cfdg.shapeAdjustment.ShapeAdjustmentConfigElementNode;
import net.sf.jame.core.common.StringElementNode;
import net.sf.jame.core.extension.ExtensionConfig;
import net.sf.jame.core.tree.Node;
import net.sf.jame.core.tree.NodeBuilder;
import net.sf.jame.core.tree.NodeValue;
import net.sf.jame.core.tree.extension.NodeBuilderExtensionRuntime;
import net.sf.jame.core.util.AbstractConfigElementListNode;
import net.sf.jame.core.util.AbstractConfigElementNode;
import net.sf.jame.core.util.AbstractExtensionConfigNodeBuilder;
import net.sf.jame.core.util.ConfigElementListNodeValue;

/**
 * @author Andrea Medeghini
 */
public class SingleShapeReplacementConfigNodeBuilderRuntime extends NodeBuilderExtensionRuntime {
	/**
	 * @see net.sf.jame.core.tree.extension.NodeBuilderExtensionRuntime#createNodeBuilder(net.sf.jame.core.extension.ExtensionConfig)
	 */
	@Override
	public NodeBuilder createNodeBuilder(final ExtensionConfig config) {
		return new ConfigNodeBuilder((SingleShapeReplacementConfig) config);
	}

	private class ConfigNodeBuilder extends AbstractExtensionConfigNodeBuilder<SingleShapeReplacementConfig> {
		/**
		 * @param config
		 */
		public ConfigNodeBuilder(final SingleShapeReplacementConfig config) {
			super(config);
		}

		/**
		 * @see net.sf.jame.core.util.AbstractExtensionConfigNodeBuilder#createNodes(net.sf.jame.core.tree.Node)
		 */
		@Override
		public void createNodes(final Node parentNode) {
			parentNode.appendChildNode(new ShapeElementNode(getConfig()));
			parentNode.appendChildNode(new ShapeAdjustmentListElementNode(getConfig()));
		}

		private class ShapeElementNode extends StringElementNode {
			/**
			 * @param config
			 */
			public ShapeElementNode(final SingleShapeReplacementConfig config) {
				super(config.getExtensionId() + ".shape", config.getShapeElement());
				setNodeLabel(ContextFreeResources.getInstance().getString("node.label.ShapeElement"));
			}
		}
		private class ShapeAdjustmentListElementNode extends AbstractConfigElementListNode<ShapeAdjustmentConfigElement> {
			public static final String NODE_CLASS = "node.class.ShapeAdjustmentListElement";
			
			/**
			 * @param config
			 */
			public ShapeAdjustmentListElementNode(final SingleShapeReplacementConfig config) {
				super(config.getExtensionId() + ".shapeAdjustments", config.getShapeAdjustmentListElement());
				setNodeClass(ShapeAdjustmentListElementNode.NODE_CLASS);
				setNodeLabel(ContextFreeResources.getInstance().getString("node.label.ShapeAdjustmentListElement"));
			}

			/**
			 * @see net.sf.jame.core.util.AbstractConfigElementListNode#createChildNode(net.sf.jame.core.config.ConfigElement)
			 */
			@Override
			protected AbstractConfigElementNode<ShapeAdjustmentConfigElement> createChildNode(final ShapeAdjustmentConfigElement value) {
				return new ShapeAdjustmentConfigElementNode(value);
			}
	
			/**
			 * @see net.sf.jame.core.util.AbstractConfigElementListNode#getChildValueType()
			 */
			@Override
			public Class<?> getChildValueType() {
				return ShapeAdjustmentConfigElementNodeValue.class;
			}
	
			/**
			 * @see net.sf.jame.core.util.AbstractConfigElementListNode#createNodeValue(Object)
			 */
			@Override
			public NodeValue<ShapeAdjustmentConfigElement> createNodeValue(final Object value) {
				return new ShapeAdjustmentConfigElementNodeValue((ShapeAdjustmentConfigElement) value);
			}
	
			private class ShapeAdjustmentConfigElementNodeValue extends ConfigElementListNodeValue<ShapeAdjustmentConfigElement> {
				private static final long serialVersionUID = 1L;
	
				/**
				 * @param value
				 */
				public ShapeAdjustmentConfigElementNodeValue(final ShapeAdjustmentConfigElement value) {
					super(value);
				}
			}
		}
	}
}