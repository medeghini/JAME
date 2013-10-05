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

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

public class OutputFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final String OUTPUT_FRAME_TITLE = "outputFrame.title";
	private static final String OUTPUT_FRAME_WIDTH = "outputFrame.width";
	private static final String OUTPUT_FRAME_HEIGHT = "outputFrame.height";
	private static final String OUTPUT_FRAME_ICON = "outputFrame.icon";
	private JTextArea textarea = new JTextArea();

	public OutputFrame() {
		final int defaultWidth = Integer.parseInt(TwisterSwingResources.getInstance().getString(OutputFrame.OUTPUT_FRAME_WIDTH));
		final int defaultHeight = Integer.parseInt(TwisterSwingResources.getInstance().getString(OutputFrame.OUTPUT_FRAME_HEIGHT));
		final int width = Integer.getInteger(OutputFrame.OUTPUT_FRAME_WIDTH, defaultWidth);
		final int height = Integer.getInteger(OutputFrame.OUTPUT_FRAME_HEIGHT, defaultHeight);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle(TwisterSwingResources.getInstance().getString(OutputFrame.OUTPUT_FRAME_TITLE));
		final URL resource = TwisterFrame.class.getClassLoader().getResource(TwisterSwingResources.getInstance().getString(OutputFrame.OUTPUT_FRAME_ICON));
		if (resource != null) {
			setIconImage(getToolkit().createImage(resource));
		}
		this.setSize(new Dimension(width, height));
		getContentPane().add(new JScrollPane(textarea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		final Point p = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		p.x += getWidth() / 3;
		p.y -= getHeight() / 2;
		this.setLocation(p);
	}

	public void clear() {
		textarea.setText("");
	}

	public void append(String s) {
		textarea.append(s);
		textarea.append("\n");
	}
}
