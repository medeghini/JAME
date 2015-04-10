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
package net.sf.jame.twister.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import net.sf.jame.core.DefaultTree;
import net.sf.jame.core.swing.NavigatorFrame;
import net.sf.jame.core.swing.util.GUIFactory;
import net.sf.jame.core.swing.util.GUIUtil;
import net.sf.jame.core.util.IntegerVector2D;
import net.sf.jame.core.util.RenderContext;
import net.sf.jame.core.util.RenderContextListener;
import net.sf.jame.core.xml.XML;
import net.sf.jame.core.xml.XMLNodeBuilder;
import net.sf.jame.twister.TwisterConfig;
import net.sf.jame.twister.TwisterConfigNodeBuilder;
import net.sf.jame.twister.TwisterConfigXMLExporter;
import net.sf.jame.twister.TwisterConfigXMLImporter;
import net.sf.jame.twister.TwisterSessionController;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Andrea Medeghini
 */
public class ConfiguratorFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private final ConfiguratorWindowListener listener = new ConfiguratorWindowListener();
	private final DefaultRenderContrext renderContext = new DefaultRenderContrext();
	private final TwisterSessionController sessionController;
	private final DefaultTree configuratorTree;
	private final TwisterConfig config;
	private final ConfiguratorPanel panel;
	private final TwisterContext context;
	private NavigatorFrame advancedConfigFrame;
	private PlatformFrame platformFrame;

	/**
	 * @param context
	 * @param config
	 */
	public ConfiguratorFrame(TwisterContext context, TwisterConfig config) {
		this.context = context;
		this.config = config;
		configuratorTree = new DefaultTree();
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle(TwisterSwingResources.getInstance().getString("configuratorFrame.title"));
		panel = new ConfiguratorPanel();
		getContentPane().add(panel);
		sessionController = new TwisterSessionController("options", config);
		sessionController.init();
		sessionController.setRenderContext(renderContext);
		final TwisterConfigNodeBuilder nodeBuilder = new TwisterConfigNodeBuilder(config);
		configuratorTree.getRootNode().setContext(config.getContext());
		configuratorTree.getRootNode().setSession(sessionController);
		nodeBuilder.createNodes(configuratorTree.getRootNode());
		addWindowListener(listener);
		pack();
		final Point p = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		p.x -= getWidth() / 2;
		p.y -= getHeight() / 2;
		this.setLocation(p);
	}

	private void openAdvancedConfigWindow(final TwisterConfig config) {
		GUIUtil.executeTask(new Runnable() {
				public void run() {
				if (advancedConfigFrame == null) {
					advancedConfigFrame = new NavigatorFrame(configuratorTree, renderContext, sessionController);
					advancedConfigFrame.addWindowListener(new WindowAdapter() {
													/**
						 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
						 */
						@Override
						public void windowClosing(final WindowEvent e) {
							if (advancedConfigFrame != null) {
								advancedConfigFrame.dispose();
								advancedConfigFrame = null;
							}
							}
												});
				}
				advancedConfigFrame.setVisible(true);
				advancedConfigFrame.toFront();
				}
						}, true);
	}

	private void openPlatformWindow() {
		GUIUtil.executeTask(new Runnable() {
				public void run() {
				if (platformFrame == null) {
					platformFrame = new PlatformFrame();
					platformFrame.addWindowListener(new WindowAdapter() {
													/**
						 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
						 */
						@Override
						public void windowClosing(final WindowEvent e) {
							if (platformFrame != null) {
								platformFrame.dispose();
								platformFrame = null;
							}
							}
												});
				}
				platformFrame.setVisible(true);
				platformFrame.toFront();
				}
						}, true);
	}

	private class ConfiguratorWindowListener extends WindowAdapter {
		/**
		 * @see java.awt.event.WindowAdapter#windowActivated(java.awt.event.WindowEvent)
		 */
		@Override
		public void windowActivated(final WindowEvent e) {
		}

		/**
		 * @see java.awt.event.WindowAdapter#windowOpened(java.awt.event.WindowEvent)
		 */
		@Override
		public void windowOpened(final WindowEvent e) {
			context.addFrame(ConfiguratorFrame.this);
		}

		/**
		 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
		 */
		@Override
		public void windowClosing(final WindowEvent e) {
			dispose();
			if (advancedConfigFrame != null) {
				advancedConfigFrame.dispose();
				advancedConfigFrame = null;
			}
			if (platformFrame != null) {
				platformFrame.dispose();
				platformFrame = null;
			}
			context.removeFrame(ConfiguratorFrame.this);
			if (context.getFrameCount() == 0) {
				context.exit();
			}
		}

		/**
		 * @see java.awt.event.WindowAdapter#windowDeiconified(java.awt.event.WindowEvent)
		 */
		@Override
		public void windowDeiconified(final WindowEvent e) {
		}

		/**
		 * @see java.awt.event.WindowAdapter#windowIconified(java.awt.event.WindowEvent)
		 */
		@Override
		public void windowIconified(final WindowEvent e) {
		}

		/**
		 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
		 */
		@Override
		public void windowClosed(final WindowEvent e) {
		}
	}

	public class ConfiguratorPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private JButton editConfigButton = GUIFactory.createButton(new EditConfigAction(), TwisterSwingResources.getInstance().getString("tooltip.modifyConfig"));
		private JButton loadConfigButton = GUIFactory.createButton(new LoadConfigAction(), TwisterSwingResources.getInstance().getString("tooltip.importConfig"));
		private JButton saveConfigButton = GUIFactory.createButton(new SaveConfigAction(), TwisterSwingResources.getInstance().getString("tooltip.exportConfig"));
		private JButton showPlatformButton = GUIFactory.createButton(new ShowPlatformAction(), TwisterSwingResources.getInstance().getString("tooltip.showPlatform"));
		private JButton startButton = GUIFactory.createButton(new StartAction(), TwisterSwingResources.getInstance().getString("tooltip.start"));
		private final JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));

		/**
		 * 
		 */
		public ConfiguratorPanel() {
			setLayout(new BorderLayout());
			final JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
			panelButtons.add(editConfigButton);
			panelButtons.add(loadConfigButton);
			panelButtons.add(saveConfigButton);
			panelButtons.add(showPlatformButton);
			panelButtons.add(startButton);
			panelButtons.setFocusable(false);
			panelButtons.setOpaque(false);
			setFocusable(false);
			setOpaque(true);
			add(panelButtons);
		}

		protected class EditConfigAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			public EditConfigAction() {
				super(TwisterSwingResources.getInstance().getString("action.modifyConfig"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(final ActionEvent e) {
				openAdvancedConfigWindow(config);
			}
		}

		protected class LoadConfigAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			public LoadConfigAction() {
				super(TwisterSwingResources.getInstance().getString("action.importConfig"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(final ActionEvent e) {
				fileChooser.setDialogTitle(TwisterSwingResources.getInstance().getString("label.importConfig"));
				final int returnVal = fileChooser.showOpenDialog(ConfiguratorPanel.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					final File file = fileChooser.getSelectedFile();
					if (file != null) {
						try {
							final TwisterConfigXMLImporter importer = new TwisterConfigXMLImporter();
							final InputStream is = new FileInputStream(file);
							final Document doc = XML.loadDocument(is, "twister-config.xml");
							is.close();
							final TwisterConfig config = importer.importFromElement(doc.getDocumentElement());
							ConfiguratorFrame.this.config.getEffectSingleElement().setValue(config.getEffectConfigElement().clone());
							ConfiguratorFrame.this.config.getFrameSingleElement().setValue(config.getFrameConfigElement().clone());
							ConfiguratorFrame.this.config.setBackground(config.getBackground());
						}
						catch (Exception x) {
							x.printStackTrace();
						}
					}
				}
			}
		}

		protected class SaveConfigAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			public SaveConfigAction() {
				super(TwisterSwingResources.getInstance().getString("action.exportConfig"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(final ActionEvent e) {
				fileChooser.setDialogTitle(TwisterSwingResources.getInstance().getString("label.exportConfig"));
				final int returnVal = fileChooser.showSaveDialog(ConfiguratorPanel.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					final File file = fileChooser.getSelectedFile();
					if (file.exists()) {
						if (JOptionPane.showConfirmDialog(ConfiguratorPanel.this, TwisterSwingResources.getInstance().getString("message.confirmOverwrite"), TwisterSwingResources.getInstance().getString("label.exportConfig"), JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION) {
							try {
								final TwisterConfigXMLExporter exporter = new TwisterConfigXMLExporter();
								Document doc = XML.createDocument();
								final XMLNodeBuilder builder = XML.createDefaultXMLNodeBuilder(doc);
								final Element element = exporter.exportToElement(config, builder);
								doc.appendChild(element);
								final OutputStream os = new FileOutputStream(file);
								XML.saveDocument(os, "twister-config.xml", doc);
								os.close();
							}
							catch (Exception x) {
								x.printStackTrace();
							}
						}
					}
					else {
						try {
							final TwisterConfigXMLExporter exporter = new TwisterConfigXMLExporter();
							Document doc = XML.createDocument();
							final XMLNodeBuilder builder = XML.createDefaultXMLNodeBuilder(doc);
							final Element element = exporter.exportToElement(config, builder);
							doc.appendChild(element);
							final OutputStream os = new FileOutputStream(file);
							XML.saveDocument(os, "twister-config.xml", doc);
							os.close();
						}
						catch (Exception x) {
							x.printStackTrace();
						}
					}
				}
			}
		}

		protected class StartAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			public StartAction() {
				super(TwisterSwingResources.getInstance().getString("action.start"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(final ActionEvent e) {
				removeWindowListener(listener);
				dispose();
				context.removeFrame(ConfiguratorFrame.this);
				if (advancedConfigFrame != null) {
					advancedConfigFrame.dispose();
					advancedConfigFrame = null;
				}
				if (platformFrame != null) {
					platformFrame.dispose();
					platformFrame = null;
				}
				if (!TwisterHelper.openTwisterFrame(context, config)) {
					context.exit();
				}
			}
		}

		protected class ShowPlatformAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			public ShowPlatformAction() {
				super(TwisterSwingResources.getInstance().getString("action.showPlatform"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(final ActionEvent e) {
				openPlatformWindow();
			}
		}
	}

	private class DefaultRenderContrext implements RenderContext {
		public void acquire() throws InterruptedException {
		}

		public void addRenderContextListener(RenderContextListener listener) {
		}

		public void removeRenderContextListener(RenderContextListener listener) {
		}

		public IntegerVector2D getImageSize() {
			return null;
		}

		public void refresh() {
		}

		public void release() {
		}

		public void startRenderers() {
		}

		public void stopRenderers() {
		}
	}
}
