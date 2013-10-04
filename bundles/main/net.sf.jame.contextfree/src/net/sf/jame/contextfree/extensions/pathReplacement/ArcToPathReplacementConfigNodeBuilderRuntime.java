/*
 * $Id:$
 *
 */
package net.sf.jame.contextfree.extensions.pathReplacement;

import net.sf.jame.contextfree.extensions.ContextFreeExtensionResources;
import net.sf.jame.core.common.BooleanElementNode;
import net.sf.jame.core.common.FloatElementNode;
import net.sf.jame.core.extension.ExtensionConfig;
import net.sf.jame.core.nodeBuilder.extension.NodeBuilderExtensionRuntime;
import net.sf.jame.core.tree.Node;
import net.sf.jame.core.tree.NodeBuilder;
import net.sf.jame.core.util.AbstractExtensionConfigNodeBuilder;

/**
 * @author Andrea Medeghini
 */
public class ArcToPathReplacementConfigNodeBuilderRuntime extends NodeBuilderExtensionRuntime {
	/**
	 * @see net.sf.jame.core.nodeBuilder.extension.NodeBuilderExtensionRuntime#createNodeBuilder(net.sf.jame.core.extension.ExtensionConfig)
	 */
	@Override
	public NodeBuilder createNodeBuilder(final ExtensionConfig config) {
		return new ConfigNodeBuilder((ArcToPathReplacementConfig) config);
	}

	private class ConfigNodeBuilder extends AbstractExtensionConfigNodeBuilder<ArcToPathReplacementConfig> {
		/**
		 * @param config
		 */
		public ConfigNodeBuilder(final ArcToPathReplacementConfig config) {
			super(config);
		}

		/**
		 * @see net.sf.jame.core.util.AbstractExtensionConfigNodeBuilder#createNodes(net.sf.jame.core.tree.Node)
		 */
		@Override
		public void createNodes(final Node parentNode) {
			parentNode.appendChildNode(new XElementNode(getConfig()));
			parentNode.appendChildNode(new YElementNode(getConfig()));
			parentNode.appendChildNode(new RxElementNode(getConfig()));
			parentNode.appendChildNode(new RyElementNode(getConfig()));
			parentNode.appendChildNode(new RElementNode(getConfig()));
			parentNode.appendChildNode(new SweepElementNode(getConfig()));
			parentNode.appendChildNode(new LargeElementNode(getConfig()));
		}

		private class XElementNode extends FloatElementNode {
			/**
			 * @param config
			 */
			public XElementNode(final ArcToPathReplacementConfig config) {
				super(config.getExtensionId() + ".x", config.getXElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.XElement"));
			}
		}
		private class YElementNode extends FloatElementNode {
			/**
			 * @param config
			 */
			public YElementNode(final ArcToPathReplacementConfig config) {
				super(config.getExtensionId() + ".y", config.getYElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.YElement"));
			}
		}
		private class RxElementNode extends FloatElementNode {
			/**
			 * @param config
			 */
			public RxElementNode(final ArcToPathReplacementConfig config) {
				super(config.getExtensionId() + ".rx", config.getRxElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.RxElement"));
			}
		}
		private class RyElementNode extends FloatElementNode {
			/**
			 * @param config
			 */
			public RyElementNode(final ArcToPathReplacementConfig config) {
				super(config.getExtensionId() + ".ry", config.getRyElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.RyElement"));
			}
		}
		private class RElementNode extends FloatElementNode {
			/**
			 * @param config
			 */
			public RElementNode(final ArcToPathReplacementConfig config) {
				super(config.getExtensionId() + ".r", config.getRElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.RElement"));
			}
		}
		private class SweepElementNode extends BooleanElementNode {
			/**
			 * @param config
			 */
			public SweepElementNode(final ArcToPathReplacementConfig config) {
				super(config.getExtensionId() + ".sweep", config.getSweepElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.SweepElement"));
			}
		}
		private class LargeElementNode extends BooleanElementNode {
			/**
			 * @param config
			 */
			public LargeElementNode(final ArcToPathReplacementConfig config) {
				super(config.getExtensionId() + ".large", config.getLargeElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.LargeElement"));
			}
		}
	}
}
