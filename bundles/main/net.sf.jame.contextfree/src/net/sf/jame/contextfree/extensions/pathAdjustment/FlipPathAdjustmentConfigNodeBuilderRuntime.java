/*
 * $Id:$
 *
 */
package net.sf.jame.contextfree.extensions.pathAdjustment;

import net.sf.jame.contextfree.ContextFreeResources;
import net.sf.jame.core.common.FloatElementNode;
import net.sf.jame.core.extension.ExtensionConfig;
import net.sf.jame.core.tree.Node;
import net.sf.jame.core.tree.NodeBuilder;
import net.sf.jame.core.tree.extension.NodeBuilderExtensionRuntime;
import net.sf.jame.core.util.AbstractExtensionConfigNodeBuilder;

/**
 * @author Andrea Medeghini
 */
public class FlipPathAdjustmentConfigNodeBuilderRuntime extends NodeBuilderExtensionRuntime {
	/**
	 * @see net.sf.jame.core.tree.extension.NodeBuilderExtensionRuntime#createNodeBuilder(net.sf.jame.core.extension.ExtensionConfig)
	 */
	@Override
	public NodeBuilder createNodeBuilder(final ExtensionConfig config) {
		return new ConfigNodeBuilder((FlipPathAdjustmentConfig) config);
	}

	private class ConfigNodeBuilder extends AbstractExtensionConfigNodeBuilder<FlipPathAdjustmentConfig> {
		/**
		 * @param config
		 */
		public ConfigNodeBuilder(final FlipPathAdjustmentConfig config) {
			super(config);
		}

		/**
		 * @see net.sf.jame.core.util.AbstractExtensionConfigNodeBuilder#createNodes(net.sf.jame.core.tree.Node)
		 */
		@Override
		public void createNodes(final Node parentNode) {
			parentNode.appendChildNode(new FlipXElementNode(getConfig()));
			parentNode.appendChildNode(new FlipYElementNode(getConfig()));
		}

		private class FlipXElementNode extends FloatElementNode {
			/**
			 * @param config
			 */
			public FlipXElementNode(final FlipPathAdjustmentConfig config) {
				super(config.getExtensionId() + ".flipX", config.getFlipXElement());
				setNodeLabel(ContextFreeResources.getInstance().getString("node.label.FlipXElement"));
			}
		}
		private class FlipYElementNode extends FloatElementNode {
			/**
			 * @param config
			 */
			public FlipYElementNode(final FlipPathAdjustmentConfig config) {
				super(config.getExtensionId() + ".flipY", config.getFlipYElement());
				setNodeLabel(ContextFreeResources.getInstance().getString("node.label.FlipYElement"));
			}
		}
	}
}