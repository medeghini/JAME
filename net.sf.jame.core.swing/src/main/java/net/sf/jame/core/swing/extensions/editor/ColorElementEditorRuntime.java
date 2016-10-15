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
package net.sf.jame.core.swing.extensions.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.sf.jame.core.common.ColorElementNodeValue;
import net.sf.jame.core.swing.NodeEditorComponent;
import net.sf.jame.core.swing.color.ColorChooser;
import net.sf.jame.core.swing.color.ColorField;
import net.sf.jame.core.swing.editor.extension.EditorExtensionRuntime;
import net.sf.jame.core.swing.extensions.CoreSwingExtensionResources;
import net.sf.jame.core.swing.util.GUIFactory;
import net.sf.jame.core.tree.NodeEditor;
import net.sf.jame.core.util.Color32bit;

/**
 * @author Andrea Medeghini
 */
public class ColorElementEditorRuntime extends EditorExtensionRuntime {
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
		private final ColorField field;

		/**
		 * @param nodeEditor
		 */
		public EditorComponent(final NodeEditor nodeEditor) {
			this.nodeEditor = nodeEditor;
			setLayout(new FlowLayout(FlowLayout.CENTER));
			field = new ColorField(new Color(((ColorElementNodeValue) nodeEditor.getNodeValue()).getValue().getARGB(), true));
			field.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			final Dimension size = new Dimension(50, 50);
			field.setMinimumSize(size);
			field.setMaximumSize(size);
			field.setPreferredSize(size);
			if (nodeEditor.isNodeEditable()) {
				field.addMouseListener(new FieldMouseListener(field, nodeEditor));
			}
			add(GUIFactory.createLabel(nodeEditor.getNodeLabel(), SwingConstants.CENTER));
			this.add(field);
			if (nodeEditor.isNodeEditable()) {
				final JButton button = GUIFactory.createButton(new EditActon(field, nodeEditor), CoreSwingExtensionResources.getInstance().getString("tooltip.editColor"));
				this.add(button);
			}
		}

		private class FieldMouseListener extends MouseAdapter {
			private final ColorField field;
			private final NodeEditor nodeEditor;

			/**
			 * @param field
			 * @param nodeEditor
			 */
			public FieldMouseListener(final ColorField field, final NodeEditor nodeEditor) {
				this.field = field;
				this.nodeEditor = nodeEditor;
			}

			@Override
			public void mouseClicked(final MouseEvent e) {
				final Color color = ColorChooser.showColorChooser(field, nodeEditor.getNodeLabel(), field.getColor());
				if (color != null) {
					field.setColor(color);
					nodeEditor.setNodeValue(new ColorElementNodeValue(new Color32bit(color.getRGB())));
				}
			}
		}

		private class EditActon extends AbstractAction {
			private static final long serialVersionUID = 1L;
			private final ColorField field;
			private final NodeEditor nodeEditor;

			/**
			 * @param field
			 * @param nodeEditor
			 */
			public EditActon(final ColorField field, final NodeEditor nodeEditor) {
				super(CoreSwingExtensionResources.getInstance().getString("action.edit"));
				this.field = field;
				this.nodeEditor = nodeEditor;
			}

			public void actionPerformed(final ActionEvent e) {
				final Color color = ColorChooser.showColorChooser(field, nodeEditor.getNodeLabel(), field.getColor());
				if (color != null) {
					field.setColor(color);
					nodeEditor.setNodeValue(new ColorElementNodeValue(new Color32bit(color.getRGB())));
				}
			}
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
				field.getModel().setColor(new Color(((ColorElementNodeValue) nodeEditor.getNodeValue()).getValue().getARGB(), true), false);
			}
		}

		/**
		 * @see net.sf.jame.core.swing.NodeEditorComponent#dispose()
		 */
		public void dispose() {
		}
	}
}
