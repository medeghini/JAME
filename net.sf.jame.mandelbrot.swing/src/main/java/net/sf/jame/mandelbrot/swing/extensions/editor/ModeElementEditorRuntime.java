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
package net.sf.jame.mandelbrot.swing.extensions.editor;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.sf.jame.core.common.IntegerElementNodeValue;
import net.sf.jame.core.swing.NodeEditorComponent;
import net.sf.jame.core.swing.editor.extension.EditorExtensionRuntime;
import net.sf.jame.core.swing.util.GUIFactory;
import net.sf.jame.core.tree.NodeEditor;
import net.sf.jame.mandelbrot.swing.extensions.MandelbrotSwingExtensionResources;

/**
 * @author Andrea Medeghini
 */
public class ModeElementEditorRuntime extends EditorExtensionRuntime {
	/**
	 * @see net.sf.jame.core.swing.editor.extension.EditorExtensionRuntime#createEditor(net.sf.jame.core.tree.NodeEditor)
	 */
	@Override
	public NodeEditorComponent createEditor(final NodeEditor nodeEditor) {
		return new EditorComponent(nodeEditor);
	}

	private class EditorComponent extends JPanel implements NodeEditorComponent {
		private static final long serialVersionUID = 1L;
		private final NodeEditor nodeEditor;
		private final JComboBox modeComboBox;

		/**
		 * @param nodeEditor
		 */
		public EditorComponent(final NodeEditor nodeEditor) {
			this.nodeEditor = nodeEditor;
			setLayout(new FlowLayout(FlowLayout.CENTER));
			modeComboBox = GUIFactory.createComboBox(new ModeComboBoxModel(), MandelbrotSwingExtensionResources.getInstance().getString("tooltip.fractalMode"));
			modeComboBox.setRenderer(new ModeListCellRenderer());
			modeComboBox.setSelectedIndex((((IntegerElementNodeValue) nodeEditor.getNodeValue()).getValue().intValue()));
			modeComboBox.addActionListener(new NodeEditorActionListener(nodeEditor));
			this.add(GUIFactory.createLabel(nodeEditor.getNodeLabel(), SwingConstants.CENTER));
			this.add(modeComboBox);
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
			if (nodeEditor.getNodeValue() != null) {
				modeComboBox.setSelectedIndex((((IntegerElementNodeValue) nodeEditor.getNodeValue()).getValue().intValue()));
			}
		}

		/**
		 * @see net.sf.jame.core.swing.NodeEditorComponent#dispose()
		 */
		public void dispose() {
		}
	}

	private class NodeEditorActionListener implements ActionListener {
		private final NodeEditor nodeEditor;

		/**
		 * @param nodeEditor
		 */
		public NodeEditorActionListener(final NodeEditor nodeEditor) {
			this.nodeEditor = nodeEditor;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent e) {
			final Integer mode = (Integer) ((Object[]) ((JComboBox) e.getSource()).getSelectedItem())[1];
			if (!mode.equals(nodeEditor.getNodeValue().getValue())) {
				nodeEditor.setNodeValue(new IntegerElementNodeValue(mode));
			}
		}
	}

	private class ModeComboBoxModel extends DefaultComboBoxModel {
		private static final long serialVersionUID = 1L;

		public ModeComboBoxModel() {
			addElement(new Object[] { "Mandelbrot set", new Integer(0) });
			addElement(new Object[] { "Julia/Fatou set", new Integer(1) });
		}
	}

	private class ModeListCellRenderer extends DefaultListCellRenderer {
		private static final long serialVersionUID = 1L;

		/**
		 * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
		 */
		@Override
		public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
			return super.getListCellRendererComponent(list, ((Object[]) value)[0], index, isSelected, cellHasFocus);
		}
	}
}
