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
package net.sf.jame.mandelbrot.fractal;

import net.sf.jame.core.config.ValueChangeEvent;
import net.sf.jame.core.config.ValueChangeListener;
import net.sf.jame.core.config.ValueConfigElement;
import net.sf.jame.core.tree.Node;
import net.sf.jame.core.tree.NodeAction;
import net.sf.jame.core.tree.NodeEditor;
import net.sf.jame.core.tree.NodeValue;
import net.sf.jame.core.util.AbstractConfigElementListNode;
import net.sf.jame.core.util.AbstractConfigElementNode;
import net.sf.jame.core.util.ConfigElementListNodeValue;
import net.sf.jame.core.util.DefaultNodeEditor;
import net.sf.jame.mandelbrot.MandelbrotResources;
import net.sf.jame.mandelbrot.incolouringFormula.IncolouringFormulaConfigElement;
import net.sf.jame.mandelbrot.incolouringFormula.IncolouringFormulaConfigElementNode;
import net.sf.jame.mandelbrot.orbitTrap.OrbitTrapConfigElement;
import net.sf.jame.mandelbrot.orbitTrap.OrbitTrapConfigElementNode;
import net.sf.jame.mandelbrot.orbitTrap.OrbitTrapConfigElementNodeValue;
import net.sf.jame.mandelbrot.outcolouringFormula.OutcolouringFormulaConfigElement;
import net.sf.jame.mandelbrot.outcolouringFormula.OutcolouringFormulaConfigElementNode;
import net.sf.jame.mandelbrot.processingFormula.ProcessingFormulaConfigElement;
import net.sf.jame.mandelbrot.processingFormula.ProcessingFormulaConfigElementNode;
import net.sf.jame.mandelbrot.processingFormula.ProcessingFormulaConfigElementNodeValue;
import net.sf.jame.mandelbrot.renderingFormula.RenderingFormulaConfigElement;
import net.sf.jame.mandelbrot.renderingFormula.RenderingFormulaConfigElementNode;
import net.sf.jame.mandelbrot.renderingFormula.RenderingFormulaConfigElementNodeValue;
import net.sf.jame.mandelbrot.transformingFormula.TransformingFormulaConfigElement;
import net.sf.jame.mandelbrot.transformingFormula.TransformingFormulaConfigElementNode;
import net.sf.jame.mandelbrot.transformingFormula.TransformingFormulaConfigElementNodeValue;

/**
 * @author Andrea Medeghini
 */
public class MandelbrotFractalConfigElementNode extends AbstractConfigElementNode<MandelbrotFractalConfigElement> {
	public static final String NODE_ID = MandelbrotFractalConfigElement.CLASS_ID;
	public static final String NODE_CLASS = "node.class.MandelbrotFractalElement";
	private static final String NODE_LABEL = MandelbrotResources.getInstance().getString("node.label.MandelbrotFractalElement");
	private final MandelbrotFractalConfigElement fractalElement;

	/**
	 * Constructs a new fractal node.
	 * 
	 * @param fractalElement the fractalElement.
	 */
	public MandelbrotFractalConfigElementNode(final MandelbrotFractalConfigElement fractalElement) {
		super(MandelbrotFractalConfigElementNode.NODE_ID);
		setNodeClass(MandelbrotFractalConfigElementNode.NODE_CLASS);
		setNodeLabel(MandelbrotFractalConfigElementNode.NODE_LABEL);
		if (fractalElement == null) {
			throw new IllegalArgumentException("fractalElement is null");
		}
		this.fractalElement = fractalElement;
		createChildNodes(fractalElement);
	}

	private void createChildNodes(final MandelbrotFractalConfigElement fractalElement) {
		createRenderingFormulaNode(fractalElement);
		createTransformingFormulaNode(fractalElement);
		createIncolouringFormulaNodes(fractalElement);
		createOutcolouringFormulaNodes(fractalElement);
		createProcessingFormulaNode(fractalElement);
		createOrbitTrapNode(fractalElement);
	}

	private void createRenderingFormulaNode(final MandelbrotFractalConfigElement fractalElement) {
		final RenderingFormulaConfigElement renderingFormulaElement = fractalElement.getRenderingFormulaConfigElement();
		final RenderingFormulaElementNode renderingFormulaNode = new RenderingFormulaElementNode(renderingFormulaElement);
		appendChildNode(renderingFormulaNode);
	}

	private void createTransformingFormulaNode(final MandelbrotFractalConfigElement fractalElement) {
		final TransformingFormulaConfigElement transformingFormulaElement = fractalElement.getTransformingFormulaConfigElement();
		final TransformingFormulaElementNode renderingFormulaNode = new TransformingFormulaElementNode(transformingFormulaElement);
		appendChildNode(renderingFormulaNode);
	}

	private void createProcessingFormulaNode(final MandelbrotFractalConfigElement fractalElement) {
		final ProcessingFormulaConfigElement processingFormulaElement = fractalElement.getProcessingFormulaConfigElement();
		final ProcessingFormulaElementNode renderingFormulaNode = new ProcessingFormulaElementNode(processingFormulaElement);
		appendChildNode(renderingFormulaNode);
	}

	private void createOrbitTrapNode(final MandelbrotFractalConfigElement fractalElement) {
		final OrbitTrapConfigElement orbitTrapElement = fractalElement.getOrbitTrapConfigElement();
		final OrbitTrapElementNode renderingFormulaNode = new OrbitTrapElementNode(orbitTrapElement);
		appendChildNode(renderingFormulaNode);
	}

	private void createIncolouringFormulaNodes(final MandelbrotFractalConfigElement fractalElement) {
		appendChildNode(new IncolouringFormulaListNode(fractalElement));
	}

	private void createOutcolouringFormulaNodes(final MandelbrotFractalConfigElement fractalElement) {
		appendChildNode(new OutcolouringFormulaListNode(fractalElement));
	}

	/**
	 * @see net.sf.jame.core.tree.Node#isEditable()
	 */
	@Override
	public boolean isEditable() {
		return true;
	}

	/**
	 * @return
	 */
	@Override
	public MandelbrotFractalConfigElement getConfigElement() {
		return fractalElement;
	}

	/**
	 * @see net.sf.jame.core.tree.DefaultNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return new DefaultNodeEditor<MandelbrotFractalConfigElement>(this, MandelbrotFractalConfigElementNodeValue.class);
	}

	private class IncolouringFormulaListNode extends AbstractConfigElementListNode<IncolouringFormulaConfigElement> {
		private final String NODE_LABEL = MandelbrotResources.getInstance().getString("node.label.IncolouringFormulaElementList");
		public static final String NODE_CLASS = "node.class.IncolouringFormulaElementList";

		/**
		 * @param fractalElement
		 */
		public IncolouringFormulaListNode(final MandelbrotFractalConfigElement fractalElement) {
			super(MandelbrotFractalConfigElementNode.this.getNodeId() + ".incolouringFormulas", fractalElement.getIncolouringFormulaListElement());
			setNodeLabel(NODE_LABEL);
			setNodeClass(IncolouringFormulaListNode.NODE_CLASS);
		}

		/**
		 * @see net.sf.jame.core.util.AbstractConfigElementListNode#createChildNode(net.sf.jame.core.config.ConfigElement)
		 */
		@Override
		protected AbstractConfigElementNode<IncolouringFormulaConfigElement> createChildNode(final IncolouringFormulaConfigElement value) {
			return new IncolouringFormulaConfigElementNode(value);
		}

		/**
		 * @see net.sf.jame.core.util.AbstractConfigElementListNode#getChildValueType()
		 */
		@Override
		public Class<?> getChildValueType() {
			return IncolouringFormulaConfigElementNodeValue.class;
		}

		/**
		 * @see net.sf.jame.core.util.AbstractConfigElementListNode#createNodeValue(Object)
		 */
		@Override
		public NodeValue<IncolouringFormulaConfigElement> createNodeValue(final Object value) {
			// return new IncolouringFormulaConfigElementNodeValue((IncolouringFormulaConfigElement) value != null ? ((IncolouringFormulaConfigElement) value).clone() : null);
			return new IncolouringFormulaConfigElementNodeValue((IncolouringFormulaConfigElement) value);
		}

		private class IncolouringFormulaConfigElementNodeValue extends ConfigElementListNodeValue<IncolouringFormulaConfigElement> {
			private static final long serialVersionUID = 1L;

			/**
			 * @param value
			 */
			public IncolouringFormulaConfigElementNodeValue(final IncolouringFormulaConfigElement value) {
				super(value);
			}
		}
	}

	private class OutcolouringFormulaListNode extends AbstractConfigElementListNode<OutcolouringFormulaConfigElement> {
		private final String NODE_LABEL = MandelbrotResources.getInstance().getString("node.label.OutcolouringFormulaElementList");
		public static final String NODE_CLASS = "node.class.OutcolouringFormulaElementList";

		/**
		 * @param fractalElement
		 */
		public OutcolouringFormulaListNode(final MandelbrotFractalConfigElement fractalElement) {
			super(MandelbrotFractalConfigElementNode.this.getNodeId() + ".outcolouringFormulas", fractalElement.getOutcolouringFormulaListElement());
			setNodeLabel(NODE_LABEL);
			setNodeClass(OutcolouringFormulaListNode.NODE_CLASS);
		}

		/**
		 * @see net.sf.jame.core.util.AbstractConfigElementListNode#createChildNode(net.sf.jame.core.config.ConfigElement)
		 */
		@Override
		protected AbstractConfigElementNode<OutcolouringFormulaConfigElement> createChildNode(final OutcolouringFormulaConfigElement value) {
			return new OutcolouringFormulaConfigElementNode(value);
		}

		/**
		 * @see net.sf.jame.core.util.AbstractConfigElementListNode#getChildValueType()
		 */
		@Override
		public Class<?> getChildValueType() {
			return OutcolouringFormulaConfigElementNodeValue.class;
		}

		/**
		 * @see net.sf.jame.core.util.AbstractConfigElementListNode#createNodeValue(Object)
		 */
		@Override
		public NodeValue<OutcolouringFormulaConfigElement> createNodeValue(final Object value) {
			// return new OutcolouringFormulaConfigElementNodeValue((OutcolouringFormulaConfigElement) value != null ? ((OutcolouringFormulaConfigElement) value).clone() : null);
			return new OutcolouringFormulaConfigElementNodeValue((OutcolouringFormulaConfigElement) value);
		}

		private class OutcolouringFormulaConfigElementNodeValue extends ConfigElementListNodeValue<OutcolouringFormulaConfigElement> {
			private static final long serialVersionUID = 1L;

			/**
			 * @param value
			 */
			public OutcolouringFormulaConfigElementNodeValue(final OutcolouringFormulaConfigElement value) {
				super(value);
			}
		}
	}

	private class RenderingFormulaElementNode extends RenderingFormulaConfigElementNode {
		private final ConfigListener listener;

		/**
		 * @param frameElement
		 */
		public RenderingFormulaElementNode(final RenderingFormulaConfigElement formulaElement) {
			super(formulaElement);
			listener = new ConfigListener();
		}

		/**
		 * @see net.sf.jame.core.tree.Node#isEditable()
		 */
		@Override
		public boolean isEditable() {
			return true;
		}

		/**
		 * @see net.sf.jame.core.tree.Node#dispose()
		 */
		@Override
		public void dispose() {
			if (fractalElement.getRenderingFormulaSingleElement() != null) {
				fractalElement.getRenderingFormulaSingleElement().removeChangeListener(listener);
			}
			super.dispose();
		}

		/**
		 * @see net.sf.jame.core.tree.Node#nodeAdded()
		 */
		@Override
		protected void nodeAdded() {
			setNodeValue(new RenderingFormulaConfigElementNodeValue(getConfigElement()));
			fractalElement.getRenderingFormulaSingleElement().addChangeListener(listener);
		}

		/**
		 * @see net.sf.jame.core.tree.Node#nodeRemoved()
		 */
		@Override
		protected void nodeRemoved() {
			fractalElement.getRenderingFormulaSingleElement().removeChangeListener(listener);
		}

		/**
		 * @see net.sf.jame.core.tree.DefaultNode#createNodeEditor()
		 */
		@Override
		protected NodeEditor createNodeEditor() {
			return new RenderingFormulaNodeEditor(this);
		}

		protected class RenderingFormulaNodeEditor extends NodeEditor {
			/**
			 * @param node
			 */
			public RenderingFormulaNodeEditor(final Node node) {
				super(node);
			}

			/**
			 * @see net.sf.jame.core.tree.NodeEditor#doSetValue(java.lang.NodeValue)
			 */
			@Override
			protected void doSetValue(final NodeValue<?> value) {
				fractalElement.getRenderingFormulaSingleElement().removeChangeListener(listener);
				fractalElement.setRenderingFormulaConfigElement(((RenderingFormulaConfigElementNodeValue) value).getValue());
				fractalElement.getRenderingFormulaSingleElement().addChangeListener(listener);
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
				return RenderingFormulaConfigElementNodeValue.class;
			}

			/**
			 * @see net.sf.jame.core.tree.NodeEditor#createNodeValue(Object)
			 */
			@Override
			public NodeValue<?> createNodeValue(final Object value) {
				// return new RenderingFormulaConfigElementNodeValue((RenderingFormulaConfigElement) value != null ? ((RenderingFormulaConfigElement) value).clone() : null);
				return new RenderingFormulaConfigElementNodeValue((RenderingFormulaConfigElement) value);
			}
		}

		protected class ConfigListener implements ValueChangeListener {
			public void valueChanged(final ValueChangeEvent e) {
				cancel();
				switch (e.getEventType()) {
					case ValueConfigElement.VALUE_CHANGED: {
						setNodeValue(new RenderingFormulaConfigElementNodeValue((RenderingFormulaConfigElement) e.getParams()[0]));
						getSession().appendAction(new NodeAction(getNodeClass(), NodeAction.ACTION_SET_VALUE, e.getTimestamp(), getNodePath(), e.getParams()[0] != null ? ((RenderingFormulaConfigElement) e.getParams()[0]).clone() : null, e.getParams()[1] != null ? ((RenderingFormulaConfigElement) e.getParams()[1]).clone() : null));
						break;
					}
					default: {
						break;
					}
				}
			}
		}
	}

	private class TransformingFormulaElementNode extends TransformingFormulaConfigElementNode {
		private final ConfigListener listener;

		/**
		 * @param frameElement
		 */
		public TransformingFormulaElementNode(final TransformingFormulaConfigElement formulaElement) {
			super(formulaElement);
			listener = new ConfigListener();
		}

		/**
		 * @see net.sf.jame.core.tree.Node#isEditable()
		 */
		@Override
		public boolean isEditable() {
			return true;
		}

		/**
		 * @see net.sf.jame.core.tree.Node#dispose()
		 */
		@Override
		public void dispose() {
			if (fractalElement.getTransformingFormulaSingleElement() != null) {
				fractalElement.getTransformingFormulaSingleElement().removeChangeListener(listener);
			}
			super.dispose();
		}

		/**
		 * @see net.sf.jame.core.tree.Node#nodeAdded()
		 */
		@Override
		protected void nodeAdded() {
			setNodeValue(new TransformingFormulaConfigElementNodeValue(getConfigElement()));
			fractalElement.getTransformingFormulaSingleElement().addChangeListener(listener);
		}

		/**
		 * @see net.sf.jame.core.tree.Node#nodeRemoved()
		 */
		@Override
		protected void nodeRemoved() {
			fractalElement.getTransformingFormulaSingleElement().removeChangeListener(listener);
		}

		/**
		 * @see net.sf.jame.core.tree.DefaultNode#createNodeEditor()
		 */
		@Override
		protected NodeEditor createNodeEditor() {
			return new TransformingFormulaNodeEditor(this);
		}

		protected class TransformingFormulaNodeEditor extends NodeEditor {
			/**
			 * @param node
			 */
			public TransformingFormulaNodeEditor(final Node node) {
				super(node);
			}

			/**
			 * @see net.sf.jame.core.tree.NodeEditor#doSetValue(java.lang.NodeValue)
			 */
			@Override
			protected void doSetValue(final NodeValue<?> value) {
				fractalElement.getTransformingFormulaSingleElement().removeChangeListener(listener);
				fractalElement.setTransformingFormulaConfigElement(((TransformingFormulaConfigElementNodeValue) value).getValue());
				fractalElement.getTransformingFormulaSingleElement().addChangeListener(listener);
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
				return TransformingFormulaConfigElementNodeValue.class;
			}

			/**
			 * @see net.sf.jame.core.tree.NodeEditor#createNodeValue(Object)
			 */
			@Override
			public NodeValue<?> createNodeValue(final Object value) {
				// return new TransformingFormulaConfigElementNodeValue((TransformingFormulaConfigElement) value != null ? ((TransformingFormulaConfigElement) value).clone() : null);
				return new TransformingFormulaConfigElementNodeValue((TransformingFormulaConfigElement) value);
			}
		}

		protected class ConfigListener implements ValueChangeListener {
			public void valueChanged(final ValueChangeEvent e) {
				cancel();
				switch (e.getEventType()) {
					case ValueConfigElement.VALUE_CHANGED: {
						setNodeValue(new TransformingFormulaConfigElementNodeValue((TransformingFormulaConfigElement) e.getParams()[0]));
						getSession().appendAction(new NodeAction(getNodeClass(), NodeAction.ACTION_SET_VALUE, e.getTimestamp(), getNodePath(), e.getParams()[0] != null ? ((TransformingFormulaConfigElement) e.getParams()[0]).clone() : null, e.getParams()[1] != null ? ((TransformingFormulaConfigElement) e.getParams()[1]).clone() : null));
						break;
					}
					default: {
						break;
					}
				}
			}
		}
	}

	private class ProcessingFormulaElementNode extends ProcessingFormulaConfigElementNode {
		private final ConfigListener listener;

		/**
		 * @param frameElement
		 */
		public ProcessingFormulaElementNode(final ProcessingFormulaConfigElement formulaElement) {
			super(formulaElement);
			listener = new ConfigListener();
		}

		/**
		 * @see net.sf.jame.core.tree.Node#isEditable()
		 */
		@Override
		public boolean isEditable() {
			return true;
		}

		/**
		 * @see net.sf.jame.core.tree.Node#dispose()
		 */
		@Override
		public void dispose() {
			if (fractalElement.getProcessingFormulaSingleElement() != null) {
				fractalElement.getProcessingFormulaSingleElement().removeChangeListener(listener);
			}
			super.dispose();
		}

		/**
		 * @see net.sf.jame.core.tree.Node#nodeAdded()
		 */
		@Override
		protected void nodeAdded() {
			setNodeValue(new ProcessingFormulaConfigElementNodeValue(getConfigElement()));
			fractalElement.getProcessingFormulaSingleElement().addChangeListener(listener);
		}

		/**
		 * @see net.sf.jame.core.tree.Node#nodeRemoved()
		 */
		@Override
		protected void nodeRemoved() {
			fractalElement.getProcessingFormulaSingleElement().removeChangeListener(listener);
		}

		/**
		 * @see net.sf.jame.core.tree.DefaultNode#createNodeEditor()
		 */
		@Override
		protected NodeEditor createNodeEditor() {
			return new ProcessingFormulaNodeEditor(this);
		}

		protected class ProcessingFormulaNodeEditor extends NodeEditor {
			/**
			 * @param node
			 */
			public ProcessingFormulaNodeEditor(final Node node) {
				super(node);
			}

			/**
			 * @see net.sf.jame.core.tree.NodeEditor#doSetValue(java.lang.NodeValue)
			 */
			@Override
			protected void doSetValue(final NodeValue<?> value) {
				fractalElement.getProcessingFormulaSingleElement().removeChangeListener(listener);
				fractalElement.setProcessingFormulaConfigElement(((ProcessingFormulaConfigElementNodeValue) value).getValue());
				fractalElement.getProcessingFormulaSingleElement().addChangeListener(listener);
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
				return ProcessingFormulaConfigElementNodeValue.class;
			}

			/**
			 * @see net.sf.jame.core.tree.NodeEditor#createNodeValue(Object)
			 */
			@Override
			public NodeValue<?> createNodeValue(final Object value) {
				// return new ProcessingFormulaConfigElementNodeValue((ProcessingFormulaConfigElement) value != null ? ((ProcessingFormulaConfigElement) value).clone() : null);
				return new ProcessingFormulaConfigElementNodeValue((ProcessingFormulaConfigElement) value);
			}
		}

		protected class ConfigListener implements ValueChangeListener {
			public void valueChanged(final ValueChangeEvent e) {
				cancel();
				switch (e.getEventType()) {
					case ValueConfigElement.VALUE_CHANGED: {
						setNodeValue(new ProcessingFormulaConfigElementNodeValue((ProcessingFormulaConfigElement) e.getParams()[0]));
						getSession().appendAction(new NodeAction(getNodeClass(), NodeAction.ACTION_SET_VALUE, e.getTimestamp(), getNodePath(), e.getParams()[0] != null ? ((ProcessingFormulaConfigElement) e.getParams()[0]).clone() : null, e.getParams()[1] != null ? ((ProcessingFormulaConfigElement) e.getParams()[1]).clone() : null));
						break;
					}
					default: {
						break;
					}
				}
			}
		}
	}

	private class OrbitTrapElementNode extends OrbitTrapConfigElementNode {
		private final ConfigListener listener;

		/**
		 * @param frameElement
		 */
		public OrbitTrapElementNode(final OrbitTrapConfigElement orbitTrapElement) {
			super(orbitTrapElement);
			listener = new ConfigListener();
		}

		/**
		 * @see net.sf.jame.core.tree.Node#isEditable()
		 */
		@Override
		public boolean isEditable() {
			return true;
		}

		/**
		 * @see net.sf.jame.core.tree.Node#dispose()
		 */
		@Override
		public void dispose() {
			if (fractalElement.getOrbitTrapSingleElement() != null) {
				fractalElement.getOrbitTrapSingleElement().removeChangeListener(listener);
			}
			super.dispose();
		}

		/**
		 * @see net.sf.jame.core.tree.Node#nodeAdded()
		 */
		@Override
		protected void nodeAdded() {
			setNodeValue(new OrbitTrapConfigElementNodeValue(getConfigElement()));
			fractalElement.getOrbitTrapSingleElement().addChangeListener(listener);
		}

		/**
		 * @see net.sf.jame.core.tree.Node#nodeRemoved()
		 */
		@Override
		protected void nodeRemoved() {
			fractalElement.getOrbitTrapSingleElement().removeChangeListener(listener);
		}

		/**
		 * @see net.sf.jame.core.tree.DefaultNode#createNodeEditor()
		 */
		@Override
		protected NodeEditor createNodeEditor() {
			return new OrbitTrapNodeEditor(this);
		}

		protected class OrbitTrapNodeEditor extends NodeEditor {
			/**
			 * @param node
			 */
			public OrbitTrapNodeEditor(final Node node) {
				super(node);
			}

			/**
			 * @see net.sf.jame.core.tree.NodeEditor#doSetValue(java.lang.NodeValue)
			 */
			@Override
			protected void doSetValue(final NodeValue<?> value) {
				fractalElement.getOrbitTrapSingleElement().removeChangeListener(listener);
				fractalElement.setOrbitTrapConfigElement(((OrbitTrapConfigElementNodeValue) value).getValue());
				fractalElement.getOrbitTrapSingleElement().addChangeListener(listener);
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
				return OrbitTrapConfigElementNodeValue.class;
			}

			/**
			 * @see net.sf.jame.core.tree.NodeEditor#createNodeValue(Object)
			 */
			@Override
			public NodeValue<?> createNodeValue(final Object value) {
				// return new OrbitTrapConfigElementNodeValue((OrbitTrapConfigElement) value != null ? ((OrbitTrapConfigElement) value).clone() : null);
				return new OrbitTrapConfigElementNodeValue((OrbitTrapConfigElement) value);
			}
		}

		protected class ConfigListener implements ValueChangeListener {
			public void valueChanged(final ValueChangeEvent e) {
				cancel();
				switch (e.getEventType()) {
					case ValueConfigElement.VALUE_CHANGED: {
						setNodeValue(new OrbitTrapConfigElementNodeValue((OrbitTrapConfigElement) e.getParams()[0]));
						getSession().appendAction(new NodeAction(getNodeClass(), NodeAction.ACTION_SET_VALUE, e.getTimestamp(), getNodePath(), e.getParams()[0] != null ? ((OrbitTrapConfigElement) e.getParams()[0]).clone() : null, e.getParams()[1] != null ? ((OrbitTrapConfigElement) e.getParams()[1]).clone() : null));
						break;
					}
					default: {
						break;
					}
				}
			}
		}
	}
}
