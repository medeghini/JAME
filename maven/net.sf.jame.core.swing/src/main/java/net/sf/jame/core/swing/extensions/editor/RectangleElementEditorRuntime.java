/*
 * JAME 6.1 
 * http://jame.sourceforge.net
 *
 * Copyright 2001, 2010 Andrea Medeghini
 * http://andreamedeghini.users.sourceforge.net
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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.sf.jame.core.common.RectangleElementNodeValue;
import net.sf.jame.core.swing.NodeEditorComponent;
import net.sf.jame.core.swing.editor.extension.EditorExtensionRuntime;
import net.sf.jame.core.swing.extensions.CoreSwingExtensionResources;
import net.sf.jame.core.swing.util.GUIFactory;
import net.sf.jame.core.tree.NodeEditor;
import net.sf.jame.core.util.Rectangle;

/**
 * @author Andrea Medeghini
 */
public class RectangleElementEditorRuntime extends EditorExtensionRuntime {
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
		private final JTextField[] textFields = new JTextField[4];

		/**
		 * @param nodeEditor
		 */
		public EditorComponent(final NodeEditor nodeEditor) {
			this.nodeEditor = nodeEditor;
			setLayout(new GridLayout(5, 1, 4, 4));
			final Rectangle rectangle = ((RectangleElementNodeValue) nodeEditor.getNodeValue()).getValue();
			final JLabel rectangleLabel = GUIFactory.createLabel(nodeEditor.getNodeLabel(), SwingConstants.CENTER);
			textFields[0] = GUIFactory.createTextField(String.valueOf(rectangle.getX()), CoreSwingExtensionResources.getInstance().getString("tooltip.rectangleLeft"));
			textFields[0].addActionListener(new FieldListener(nodeEditor, textFields));
			textFields[0].addFocusListener(new FieldListener(nodeEditor, textFields));
			textFields[0].setColumns(6);
			textFields[0].setCaretPosition(0);
			textFields[1] = GUIFactory.createTextField(String.valueOf(rectangle.getY()), CoreSwingExtensionResources.getInstance().getString("tooltip.rectangleTop"));
			textFields[1].addActionListener(new FieldListener(nodeEditor, textFields));
			textFields[1].addFocusListener(new FieldListener(nodeEditor, textFields));
			textFields[1].setColumns(6);
			textFields[1].setCaretPosition(0);
			textFields[2] = GUIFactory.createTextField(String.valueOf(rectangle.getW()), CoreSwingExtensionResources.getInstance().getString("tooltip.rectangleWidth"));
			textFields[2].addActionListener(new FieldListener(nodeEditor, textFields));
			textFields[2].addFocusListener(new FieldListener(nodeEditor, textFields));
			textFields[2].setColumns(6);
			textFields[2].setCaretPosition(0);
			textFields[3] = GUIFactory.createTextField(String.valueOf(rectangle.getH()), CoreSwingExtensionResources.getInstance().getString("tooltip.rectangleHeight"));
			textFields[3].addActionListener(new FieldListener(nodeEditor, textFields));
			textFields[3].addFocusListener(new FieldListener(nodeEditor, textFields));
			textFields[3].setColumns(6);
			textFields[3].setCaretPosition(0);
			this.add(rectangleLabel);
			this.add(createTextFieldPanel("L", textFields[0]));
			this.add(createTextFieldPanel("T", textFields[1]));
			this.add(createTextFieldPanel("W", textFields[2]));
			this.add(createTextFieldPanel("H", textFields[3]));
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
			final Rectangle rectangle = ((RectangleElementNodeValue) nodeEditor.getNodeValue()).getValue();
			textFields[0].setText(String.valueOf(rectangle.getX()));
			textFields[0].setCaretPosition(0);
			textFields[1].setText(String.valueOf(rectangle.getY()));
			textFields[1].setCaretPosition(0);
			textFields[2].setText(String.valueOf(rectangle.getW()));
			textFields[2].setCaretPosition(0);
			textFields[3].setText(String.valueOf(rectangle.getH()));
			textFields[3].setCaretPosition(0);
		}

		/**
		 * @see net.sf.jame.core.swing.NodeEditorComponent#dispose()
		 */
		public void dispose() {
		}

		/**
		 * @param label
		 * @param textField
		 * @return
		 */
		protected JPanel createTextFieldPanel(final String text, final JTextField textField) {
			final JPanel panel = new JPanel(new BorderLayout(4, 4));
			final JLabel label = GUIFactory.createLabel(text, SwingConstants.CENTER);
			label.setPreferredSize(new Dimension(20, 20));
			panel.add(label, BorderLayout.WEST);
			panel.add(textField, BorderLayout.CENTER);
			return panel;
		}

		private class FieldListener implements ActionListener, FocusListener {
			private final NodeEditor nodeEditor;
			private final JTextField[] textFields;

			public FieldListener(final NodeEditor nodeEditor, final JTextField[] textFields) {
				this.nodeEditor = nodeEditor;
				this.textFields = textFields;
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(final ActionEvent e) {
				final Rectangle rectangle = ((RectangleElementNodeValue) nodeEditor.getNodeValue()).getValue();
				double x = rectangle.getX();
				double y = rectangle.getY();
				double w = rectangle.getW();
				double h = rectangle.getH();
				try {
					final String text = textFields[0].getText();
					x = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					textFields[0].setText(String.valueOf(rectangle.getX()));
					textFields[0].setCaretPosition(0);
				}
				try {
					final String text = textFields[1].getText();
					y = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					textFields[1].setText(String.valueOf(rectangle.getY()));
					textFields[1].setCaretPosition(0);
				}
				try {
					final String text = textFields[2].getText();
					w = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					textFields[2].setText(String.valueOf(rectangle.getW()));
					textFields[2].setCaretPosition(0);
				}
				try {
					final String text = textFields[3].getText();
					h = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					textFields[3].setText(String.valueOf(rectangle.getH()));
					textFields[3].setCaretPosition(0);
				}
				final Rectangle value = new Rectangle(x, y, w, h);
				if (!nodeEditor.getNodeValue().getValue().equals(value)) {
					nodeEditor.setNodeValue(new RectangleElementNodeValue(value));
				}
			}

			/**
			 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
			 */
			public void focusGained(final FocusEvent e) {
			}

			/**
			 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
			 */
			public void focusLost(final FocusEvent e) {
				final Rectangle rectangle = ((RectangleElementNodeValue) nodeEditor.getNodeValue()).getValue();
				double x = rectangle.getX();
				double y = rectangle.getY();
				double w = rectangle.getW();
				double h = rectangle.getH();
				try {
					final String text = textFields[0].getText();
					x = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					textFields[0].setText(String.valueOf(rectangle.getX()));
					textFields[0].setCaretPosition(0);
				}
				try {
					final String text = textFields[1].getText();
					y = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					textFields[1].setText(String.valueOf(rectangle.getY()));
					textFields[1].setCaretPosition(0);
				}
				try {
					final String text = textFields[2].getText();
					w = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					textFields[2].setText(String.valueOf(rectangle.getW()));
					textFields[2].setCaretPosition(0);
				}
				try {
					final String text = textFields[3].getText();
					h = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					textFields[3].setText(String.valueOf(rectangle.getH()));
					textFields[3].setCaretPosition(0);
				}
				final Rectangle value = new Rectangle(x, y, w, h);
				if (!nodeEditor.getNodeValue().getValue().equals(value)) {
					nodeEditor.setNodeValue(new RectangleElementNodeValue(value));
				}
			}
		}
	}
}
