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
package net.sf.jame.twister.extensions.image;

import net.sf.jame.core.common.ColorElementNode;
import net.sf.jame.core.common.DoubleElementNode;
import net.sf.jame.core.extension.ExtensionConfig;
import net.sf.jame.core.nodeBuilder.extension.NodeBuilderExtensionRuntime;
import net.sf.jame.core.tree.Node;
import net.sf.jame.core.tree.NodeBuilder;
import net.sf.jame.core.util.AbstractExtensionConfigNodeBuilder;
import net.sf.jame.twister.extensions.TwisterExtensionResources;

/**
 * @author Andrea Medeghini
 */
public class BorderConfigNodeBuilderRuntime extends NodeBuilderExtensionRuntime {
	/**
	 * @see net.sf.jame.core.nodeBuilder.extension.NodeBuilderExtensionRuntime#createNodeBuilder(net.sf.jame.core.extension.ExtensionConfig)
	 */
	@Override
	public NodeBuilder createNodeBuilder(final ExtensionConfig config) {
		return new ConfigNodeBuilder((BorderConfig) config);
	}

	private class ConfigNodeBuilder extends AbstractExtensionConfigNodeBuilder<BorderConfig> {
		/**
		 * @param config
		 */
		public ConfigNodeBuilder(final BorderConfig config) {
			super(config);
		}

		/**
		 * @see net.sf.jame.core.util.AbstractExtensionConfigNodeBuilder#createNodes(net.sf.jame.core.tree.Node)
		 */
		@Override
		public void createNodes(final Node parentNode) {
			parentNode.appendChildNode(new ColorNode(getConfig()));
			parentNode.appendChildNode(new SizeNode(getConfig()));
		}

		private class ColorNode extends ColorElementNode {
			/**
			 * @param config
			 */
			public ColorNode(final BorderConfig config) {
				super(config.getExtensionId() + ".color", config.getColorElement());
			}
		}

		private class SizeNode extends DoubleElementNode {
			private final String NODE_LABEL = TwisterExtensionResources.getInstance().getString("node.label.SizeElement");

			/**
			 * @param config
			 */
			public SizeNode(final BorderConfig config) {
				super(config.getExtensionId() + ".size", config.getSizeElement());
				setNodeLabel(NODE_LABEL);
			}
		}
	}
}
