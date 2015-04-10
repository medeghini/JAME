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
package net.sf.jame.twister.swing.extensions.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.sf.jame.core.swing.NodeEditorComponent;
import net.sf.jame.core.swing.editor.extension.EditorExtensionRuntime;
import net.sf.jame.core.swing.util.GUIFactory;
import net.sf.jame.core.tree.NodeEditor;
import net.sf.jame.core.util.DoubleVector4D;
import net.sf.jame.core.util.IntegerVector4D;
import net.sf.jame.twister.common.ViewElementNodeValue;
import net.sf.jame.twister.swing.extensions.TwisterSwingExtensionResources;
import net.sf.jame.twister.util.View;

/**
 * @author Andrea Medeghini
 */
public class ViewElementEditorRuntime extends EditorExtensionRuntime {
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
		private final VectorPanel[] panels = new VectorPanel[2];

		/**
		 * @param nodeEditor
		 */
		public EditorComponent(final NodeEditor nodeEditor) {
			this.nodeEditor = nodeEditor;
			setLayout(new BorderLayout());
			final JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			final JButton applyButton = GUIFactory.createButton(new ApplyAction(nodeEditor, panels), TwisterSwingExtensionResources.getInstance().getString("tooltip.applyView"));
			final JButton reloadButton = GUIFactory.createButton(new ReloadAction(nodeEditor, panels), TwisterSwingExtensionResources.getInstance().getString("tooltip.reloadView"));
			buttonsPanel.add(applyButton);
			buttonsPanel.add(reloadButton);
			final View view = ((ViewElementNodeValue) nodeEditor.getNodeValue()).getValue();
			final JPanel panel = new JPanel(new GridLayout(1, 2, 4, 4));
			panels[0] = new VectorPanel(TwisterSwingExtensionResources.getInstance().getString("label.position"), view.getPosition());
			panel.add(panels[0]);
			panels[1] = new VectorPanel(TwisterSwingExtensionResources.getInstance().getString("label.rotation"), view.getRotation());
			panel.add(panels[1]);
			this.add(panel, BorderLayout.CENTER);
			this.add(buttonsPanel, BorderLayout.SOUTH);
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
			final View view = ((ViewElementNodeValue) nodeEditor.getNodeValue()).getValue();
			panels[0].textFields[0].setText(String.valueOf(view.getPosition().getX()));
			panels[0].textFields[0].setCaretPosition(0);
			panels[0].textFields[1].setText(String.valueOf(view.getPosition().getY()));
			panels[0].textFields[1].setCaretPosition(0);
			panels[0].textFields[2].setText(String.valueOf(view.getPosition().getZ()));
			panels[0].textFields[2].setCaretPosition(0);
			panels[0].textFields[3].setText(String.valueOf(view.getPosition().getW()));
			panels[0].textFields[3].setCaretPosition(0);
			panels[1].textFields[0].setText(String.valueOf(view.getRotation().getX()));
			panels[1].textFields[0].setCaretPosition(0);
			panels[1].textFields[1].setText(String.valueOf(view.getRotation().getY()));
			panels[1].textFields[1].setCaretPosition(0);
			panels[1].textFields[2].setText(String.valueOf(view.getRotation().getZ()));
			panels[1].textFields[2].setCaretPosition(0);
			panels[1].textFields[3].setText(String.valueOf(view.getRotation().getW()));
			panels[1].textFields[3].setCaretPosition(0);
		}

		/**
		 * @see net.sf.jame.core.swing.NodeEditorComponent#dispose()
		 */
		public void dispose() {
		}

		private class VectorPanel extends JPanel {
			private static final long serialVersionUID = 1L;
			private final VectorField[] textFields = new VectorField[4];

			public VectorPanel(final String label, final DoubleVector4D vector) {
				setLayout(new GridLayout(5, 1, 4, 4));
				textFields[0] = new VectorField();
				textFields[0].setText(String.valueOf(vector.getX()));
				textFields[0].setColumns(10);
				textFields[0].setCaretPosition(0);
				textFields[0].setToolTipText(TwisterSwingExtensionResources.getInstance().getString("tooltip.vectorX"));
				textFields[1] = new VectorField();
				textFields[1].setText(String.valueOf(vector.getY()));
				textFields[1].setColumns(10);
				textFields[1].setCaretPosition(0);
				textFields[1].setToolTipText(TwisterSwingExtensionResources.getInstance().getString("tooltip.vectorY"));
				textFields[2] = new VectorField();
				textFields[2].setText(String.valueOf(vector.getZ()));
				textFields[2].setColumns(10);
				textFields[2].setCaretPosition(0);
				textFields[2].setToolTipText(TwisterSwingExtensionResources.getInstance().getString("tooltip.vectorZ"));
				textFields[3] = new VectorField();
				textFields[3].setText(String.valueOf(vector.getW()));
				textFields[3].setColumns(10);
				textFields[3].setCaretPosition(0);
				textFields[3].setToolTipText(TwisterSwingExtensionResources.getInstance().getString("tooltip.vectorW"));
				this.add(GUIFactory.createLabel(label, SwingConstants.CENTER));
				this.add(createTextFieldPanel("X", textFields[0]));
				this.add(createTextFieldPanel("Y", textFields[1]));
				this.add(createTextFieldPanel("Z", textFields[2]));
				this.add(createTextFieldPanel("W", textFields[3]));
			}
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

		private class VectorField extends JTextField {
			private static final long serialVersionUID = 1L;

			public VectorField() {
				setColumns(6);
			}

			@Override
			public void setText(final String text) {
				super.setText(text);
				setCaretPosition(0);
			}
		}

		private class ApplyAction extends AbstractAction {
			private static final long serialVersionUID = 1L;
			private final VectorPanel[] panels;
			private final NodeEditor nodeEditor;

			public ApplyAction(final NodeEditor nodeEditor, final VectorPanel[] panels) {
				super(TwisterSwingExtensionResources.getInstance().getString("action.applyView"));
				this.panels = panels;
				this.nodeEditor = nodeEditor;
			}

			public void actionPerformed(final ActionEvent e) {
				View view = ((ViewElementNodeValue) nodeEditor.getNodeValue()).getValue();
				double x = view.getPosition().getX();
				double y = view.getPosition().getY();
				double z = view.getPosition().getZ();
				double w = view.getPosition().getW();
				double a = view.getRotation().getX();
				double b = view.getRotation().getY();
				double c = view.getRotation().getZ();
				double d = view.getRotation().getW();
				try {
					final String text = panels[0].textFields[0].getText();
					x = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					panels[0].textFields[0].setText(String.valueOf(view.getPosition().getX()));
				}
				try {
					final String text = panels[0].textFields[1].getText();
					y = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					panels[0].textFields[1].setText(String.valueOf(view.getPosition().getY()));
				}
				try {
					final String text = panels[0].textFields[2].getText();
					z = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					panels[0].textFields[2].setText(String.valueOf(view.getPosition().getZ()));
				}
				try {
					final String text = panels[0].textFields[3].getText();
					w = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					panels[0].textFields[3].setText(String.valueOf(view.getPosition().getW()));
				}
				try {
					final String text = panels[1].textFields[0].getText();
					a = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					panels[1].textFields[0].setText(String.valueOf(view.getRotation().getX()));
				}
				try {
					final String text = panels[1].textFields[1].getText();
					b = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					panels[1].textFields[1].setText(String.valueOf(view.getRotation().getY()));
				}
				try {
					final String text = panels[1].textFields[2].getText();
					c = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					panels[1].textFields[2].setText(String.valueOf(view.getRotation().getZ()));
				}
				try {
					final String text = panels[1].textFields[3].getText();
					d = Double.parseDouble(text);
				}
				catch (final NumberFormatException nfe) {
					panels[1].textFields[3].setText(String.valueOf(view.getRotation().getW()));
				}
				view = new View(new IntegerVector4D(0, 0, 0, 0), new DoubleVector4D(x, y, z, w), new DoubleVector4D(a, b, c, d));
				nodeEditor.setNodeValue(new ViewElementNodeValue(view));
			}
		}

		private class ReloadAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			// private VectorPanel[] panels;
			// private NodeEditor nodeEditor;
			public ReloadAction(final NodeEditor nodeEditor, final VectorPanel[] panels) {
				super(TwisterSwingExtensionResources.getInstance().getString("action.reloadView"));
				// this.panels = panels;
				// this.nodeEditor = nodeEditor;
			}

			public void actionPerformed(final ActionEvent e) {
				reloadValue();
			}
		}
	}
}
