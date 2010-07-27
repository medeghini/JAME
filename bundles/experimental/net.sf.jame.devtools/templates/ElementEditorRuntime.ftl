/*
 * $Id:$
 *
 */
package ${element.runtimeElementPackageName};

<#list imports as import>
import ${import};
</#list>
<#if element.simpleElement>
/**
 * @author ${author}
 */
public class <#if parentRuntimeClass?exists>${element.elementName?cap_first}EditorRuntime extends ${parentRuntimeClass}<#else>${element.elementName?cap_first}EditorRuntime extends EditorExtensionRuntime</#if> {
	/**
	 * @see net.sf.jame.core.swing.editor.element.EditorExtensionRuntime#createEditor(net.sf.jame.core.tree.NodeEditor)
	 */
	@Override
	public NodeEditorComponent createEditor(final NodeEditor nodeEditor) {
		return new EditorComponent(nodeEditor);
	}

	private class EditorComponent extends JPanel implements NodeEditorComponent {
		private static final long serialVersionUID = 1L;

		/**
		 * @param nodeEditor
		 */
		public EditorComponent(final NodeEditor nodeEditor) {
			setLayout(new FlowLayout(FlowLayout.CENTER));
		}

		/**
		 * @see net.sf.jame.core.swing.NodeEditorComponent#getComponent()
		 */
		public JComponent getComponent() {
			return this;
		}

		/**
		 * @see net.sf.jame.core.swing.NodeEditorComponent#reloadValue()
		 */
		public void reloadValue() {
		}

		/**
		 * @see net.sf.jame.core.swing.NodeEditorComponent#dispose()
		 */
		public void dispose() {
		}
	}
} 
<#elseif element.complexElement>
/**
 * @author ${author}
 */
public class <#if parentRuntimeClass?exists>${element.elementName?cap_first}EditorRuntime extends ${parentRuntimeClass}<#else>${element.elementName?cap_first}EditorRuntime extends EditorExtensionRuntime</#if> {
	/**
	 * @see net.sf.jame.core.swing.editor.element.EditorExtensionRuntime#createEditor(net.sf.jame.core.tree.NodeEditor)
	 */
	@Override
	public NodeEditorComponent createEditor(final NodeEditor nodeEditor) {
		return new EditorComponent(nodeEditor);
	}

	private class EditorComponent extends JPanel implements NodeEditorComponent {
		private static final long serialVersionUID = 1L;

		/**
		 * @param nodeEditor
		 */
		public EditorComponent(final NodeEditor nodeEditor) {
			setLayout(new FlowLayout(FlowLayout.CENTER));
		}

		/**
		 * @see net.sf.jame.core.swing.NodeEditorComponent#getComponent()
		 */
		public JComponent getComponent() {
			return this;
		}

		/**
		 * @see net.sf.jame.core.swing.NodeEditorComponent#reloadValue()
		 */
		public void reloadValue() {
		}

		/**
		 * @see net.sf.jame.core.swing.NodeEditorComponent#dispose()
		 */
		public void dispose() {
		}
	}
}
</#if> 