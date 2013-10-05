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
package net.sf.jame.twister.swing.encoder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import net.sf.jame.core.extension.ConfigurableExtension;
import net.sf.jame.core.extension.Extension;
import net.sf.jame.core.extension.ExtensionException;
import net.sf.jame.core.extension.ExtensionNotFoundException;
import net.sf.jame.core.swing.extension.ExtensionComboBoxModel;
import net.sf.jame.core.swing.extension.ExtensionFilter;
import net.sf.jame.core.swing.extension.ExtensionListCellRenderer;
import net.sf.jame.core.swing.util.GUIFactory;
import net.sf.jame.core.swing.util.GUIUtil;
import net.sf.jame.core.util.ProgressListener;
import net.sf.jame.queue.LibraryService;
import net.sf.jame.queue.RenderService;
import net.sf.jame.queue.RenderService.ServiceCallback;
import net.sf.jame.queue.RenderServiceRegistry;
import net.sf.jame.queue.encoder.EncoderHook;
import net.sf.jame.queue.encoder.extension.EncoderExtensionConfig;
import net.sf.jame.queue.encoder.extension.EncoderExtensionRuntime;
import net.sf.jame.queue.profile.RenderProfileDataRow;
import net.sf.jame.twister.swing.TwisterSwingResources;

/**
 * @author Andrea Medeghini
 */
public class EncoderDialog extends JDialog {
	private static final Logger logger = Logger.getLogger(EncoderDialog.class.getName());
	private static final long serialVersionUID = 1L;
	private static final String STRING_FRAME_TITLE = "encoderFrame.title";
	// private static final String STRING_FRAME_WIDTH = "encoderFrame.width";
	// private static final String STRING_FRAME_HEIGHT = "encoderFrame.height";
	// private static final String STRING_FRAME_ICON = "encoderFrame.icon";
	private final JProgressBar progressBar = new JProgressBar(0, 100);
	private final JFileChooser profileChooser = new JFileChooser(System.getProperty("user.home"));
	private final JButton profileExportButton = GUIFactory.createButton(new ExportAction(), TwisterSwingResources.getInstance().getString("tooltip.export"));
	private final JButton chooseButton = GUIFactory.createButton(new ChooseAction(), TwisterSwingResources.getInstance().getString("tooltip.choose"));
	private final JButton cancelButton = GUIFactory.createButton(new CancelAction(), TwisterSwingResources.getInstance().getString("tooltip.cancel"));
	private final JButton closeButton = GUIFactory.createButton(new CloseAction(), TwisterSwingResources.getInstance().getString("tooltip.close"));
	private final JTextField pathTextField = GUIFactory.createTextField((String) null, TwisterSwingResources.getInstance().getString("tooltip.path"));
	private final ExtensionComboBoxModel model;
	private RenderProfileDataRow profile;
	private boolean isInterrupted;
	private final RenderService service;
	private final JComboBox combobox;

	/**
	 * @param frame
	 * @param service
	 * @param profile
	 */
	public EncoderDialog(final JFrame frame, final RenderService service) {
		super(frame);
		this.service = service;
		setResizable(true);
		setUndecorated(false);
		// setCursor(new Cursor(Cursor.HAND_CURSOR));
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setTitle(TwisterSwingResources.getInstance().getString(EncoderDialog.STRING_FRAME_TITLE));
		// final Rectangle bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		// final int width = Math.min(Integer.parseInt(ServiceResources.getInstance().getString(EncoderDialog.STRING_FRAME_WIDTH)), bounds.width);
		// final int height = Math.min(Integer.parseInt(ServiceResources.getInstance().getString(EncoderDialog.STRING_FRAME_HEIGHT)), bounds.height);
		final JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		final JPanel encoderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		final JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		final Dimension textFieldSize = new Dimension(300, 24);
		pathTextField.setPreferredSize(textFieldSize);
		pathTextField.setMinimumSize(textFieldSize);
		pathTextField.setMaximumSize(textFieldSize);
		encoderPanel.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), TwisterSwingResources.getInstance().getString("label.encoder")));
		filePanel.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), TwisterSwingResources.getInstance().getString("label.path")));
		model = new ExtensionComboBoxModel(RenderServiceRegistry.getInstance().getEncoderRegistry(), false);
		combobox = GUIFactory.createComboBox(model, TwisterSwingResources.getInstance().getString("tooltip.encoder"));
		combobox.setRenderer(new ExtensionListCellRenderer());
		encoderPanel.add(combobox);
		buttonsPanel.add(profileExportButton);
		buttonsPanel.add(closeButton);
		filePanel.add(pathTextField);
		filePanel.add(chooseButton);
		final Box panel = Box.createVerticalBox();
		profileChooser.setMultiSelectionEnabled(false);
		panel.setBorder(new EmptyBorder(8, 8, 8, 8));
		panel.add(encoderPanel);
		panel.add(Box.createVerticalStrut(8));
		panel.add(filePanel);
		panel.add(Box.createVerticalStrut(8));
		panel.add(buttonsPanel);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(panel);
		pack();
	}

	/**
	 * @param profile
	 */
	@SuppressWarnings("unchecked")
	public void setProfile(final RenderProfileDataRow profile) {
		this.profile = profile;
		model.reload(RenderServiceRegistry.getInstance().getEncoderRegistry(), new ExtensionFilter() {
			/**
			 * @see net.sf.jame.core.swing.extension.ExtensionFilter#accept(net.sf.jame.core.extension.Extension)
			 */
			public boolean accept(final Extension<?> extension) {
				try {
					if ((profile.getTotalFrames() > 0) && ((EncoderExtensionRuntime<?>) extension.createExtensionRuntime()).isMovieSupported()) {
						return true;
					}
					if ((profile.getTotalFrames() == 0) && ((EncoderExtensionRuntime<?>) extension.createExtensionRuntime()).isImageSupported()) {
						return true;
					}
				}
				catch (final ExtensionException e) {
					e.printStackTrace();
				}
				return false;
			}
		});
		if ((pathTextField.getText() != null) && (pathTextField.getText().trim().length() > 0)) {
			try {
				final Extension<EncoderExtensionRuntime> extension = (Extension<EncoderExtensionRuntime>) model.getRegistry().getExtension("service.encoder" + pathTextField.getText().substring(pathTextField.getText().indexOf(".")));
				model.setSelectedItemByExtensionId(extension.getExtensionId());
			}
			catch (final ExtensionNotFoundException e) {
				if (profile.getTotalFrames() == 0) {
					model.setSelectedItemByExtensionId("service.encoder.tiff");
				}
				else {
					model.setSelectedItemByExtensionId("service.encoder.mpeg4");
				}
			}
		}
		else {
			if (profile.getTotalFrames() == 0) {
				model.setSelectedItemByExtensionId("service.encoder.tiff");
			}
			else {
				model.setSelectedItemByExtensionId("service.encoder.mpeg4");
			}
		}
	}

	private class ExportAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		public ExportAction() {
			super(TwisterSwingResources.getInstance().getString("action.export"));
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent e) {
			if ((pathTextField.getText() != null) && (pathTextField.getText().trim().length() == 0)) {
				JOptionPane.showMessageDialog(EncoderDialog.this, TwisterSwingResources.getInstance().getString("message.missingFile"), TwisterSwingResources.getInstance().getString("label.exportProfile"), JOptionPane.WARNING_MESSAGE);
			}
			else {
				final File file = new File(pathTextField.getText());
				if (file.exists()) {
					if (JOptionPane.showConfirmDialog(EncoderDialog.this, TwisterSwingResources.getInstance().getString("message.confirmOverwrite"), TwisterSwingResources.getInstance().getString("label.exportProfile"), JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION) {
						final ProgressDialog dialog = new ProgressDialog();
						GUIUtil.centerWindow(dialog, getLocationOnScreen(), EncoderDialog.this.getBounds());
						dialog.setVisible(true);
					}
				}
				else {
					final ProgressDialog dialog = new ProgressDialog();
					GUIUtil.centerWindow(dialog, getLocationOnScreen(), EncoderDialog.this.getBounds());
					dialog.setVisible(true);
				}
			}
		}
	}

	private class ChooseAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		public ChooseAction() {
			super(TwisterSwingResources.getInstance().getString("action.choose"));
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@SuppressWarnings("unchecked")
		public void actionPerformed(final ActionEvent e) {
			profileChooser.setDialogTitle(TwisterSwingResources.getInstance().getString("label.exportProfile"));
			final int returnVal = profileChooser.showSaveDialog(new JFrame());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				pathTextField.setText(profileChooser.getSelectedFile().getAbsoluteFile().getPath());
				try {
					final EncoderExtensionRuntime encoder = ((ConfigurableExtension<EncoderExtensionRuntime<?>, EncoderExtensionConfig>) combobox.getSelectedItem()).createExtensionRuntime();
					if ((pathTextField.getText() != null) && (encoder.isMovieSupported() && !pathTextField.getText().toLowerCase().endsWith(encoder.getMovieSuffix().toLowerCase()))) {
						pathTextField.setText(pathTextField.getText() + encoder.getMovieSuffix().toLowerCase());
					}
					else if ((pathTextField.getText() != null) && (encoder.isImageSupported() && !pathTextField.getText().toLowerCase().endsWith(encoder.getImageSuffix().toLowerCase()))) {
						pathTextField.setText(pathTextField.getText() + encoder.getImageSuffix().toLowerCase());
					}
				}
				catch (final Exception x) {
					x.printStackTrace();
				}
			}
		}
	}

	private class CancelAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		public CancelAction() {
			super(TwisterSwingResources.getInstance().getString("action.cancel"));
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent e) {
			isInterrupted = true;
		}
	}

	private class CloseAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		public CloseAction() {
			super(TwisterSwingResources.getInstance().getString("action.close"));
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent e) {
			setVisible(false);
		}
	}

	public class ProgressDialog extends JDialog {
		private static final long serialVersionUID = 1L;

		public ProgressDialog() {
			super(new JFrame());
			progressBar.setValue(0);
			setModal(true);
			setUndecorated(true);
			setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			final JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			buttonsPanel.add(cancelButton);
			final JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.setBorder(new CompoundBorder(new LineBorder(Color.LIGHT_GRAY), new TitledBorder(new EmptyBorder(40, 40, 40, 40), TwisterSwingResources.getInstance().getString("message.export"))));
			panel.add(progressBar, BorderLayout.CENTER);
			panel.add(buttonsPanel, BorderLayout.SOUTH);
			getContentPane().setLayout(new BorderLayout());
			getContentPane().add(panel);
			pack();
			addWindowListener(new Dialoglistener());
		}

		private class Dialoglistener extends WindowAdapter {
			/**
			 * @see java.awt.event.WindowAdapter#windowOpened(java.awt.event.WindowEvent)
			 */
			@SuppressWarnings("unchecked")
			@Override
			public void windowOpened(final WindowEvent e) {
				try {
					final EncoderExtensionRuntime encoder = ((ConfigurableExtension<EncoderExtensionRuntime<?>, EncoderExtensionConfig>) combobox.getSelectedItem()).createExtensionRuntime();
					isInterrupted = false;
					encoder.setInterruptHook(new EncoderHook() {
						/**
						 * @see net.sf.jame.service.encoder.EncoderHook#isInterrupted()
						 */
						public boolean isInterrupted() {
							return isInterrupted;
						}
					});
					final EncoderProgressListener listener = new EncoderProgressListener();
					if (encoder != null) {
						service.execute(new ServiceCallback<Object>() {
							public void executed(final Object value) {
								listener.done();
							}

							public void failed(final Throwable throwable) {
								listener.failed(throwable);
							}

							public Object execute(final LibraryService service) throws Exception {
								service.exportProfile(profile, encoder, listener, new File(pathTextField.getText()));
								return null;
							}
						});
					}
					else {
						setVisible(false);
						dispose();
					}
				}
				catch (final ExtensionException x) {
					JOptionPane.showMessageDialog(EncoderDialog.this, TwisterSwingResources.getInstance().getString("message.exportFailed"), TwisterSwingResources.getInstance().getString("label.exportProfile"), JOptionPane.ERROR_MESSAGE);
					EncoderDialog.logger.log(Level.WARNING, "Can't export the profile", x);
					x.printStackTrace();
				}
			}

			private class EncoderProgressListener implements ProgressListener {
				public void done() {
					GUIUtil.executeTask(new Runnable() {
						public void run() {
							setVisible(false);
							dispose();
						}
					}, true);
				}

				public void failed(final Throwable e) {
					GUIUtil.executeTask(new Runnable() {
						public void run() {
							JOptionPane.showMessageDialog(EncoderDialog.this, TwisterSwingResources.getInstance().getString("message.exportFailed"), TwisterSwingResources.getInstance().getString("label.exportProfile"), JOptionPane.ERROR_MESSAGE);
							EncoderDialog.logger.log(Level.WARNING, "Can't export the profile", e);
							setVisible(false);
							dispose();
						}
					}, true);
				}

				public void stateChanged(final String message, final int percentage) {
					GUIUtil.executeTask(new Runnable() {
						public void run() {
							progressBar.setValue(percentage);
						}
					}, true);
				}

				public void stateChanged(final String message) {
				}
			}
		}
	}
}
