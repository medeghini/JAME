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
package net.sf.jame.mandelbrot.extensions.paletteRenderer;

import net.sf.jame.core.extensionConfigXMLExporter.extension.ExtensionConfigXMLExporterExtensionRuntime;
import net.sf.jame.core.xml.XMLExportException;
import net.sf.jame.core.xml.XMLExporter;
import net.sf.jame.core.xml.XMLNodeBuilder;
import net.sf.jame.mandelbrot.common.RenderedPaletteElementXMLExporter;

import org.w3c.dom.Element;

/**
 * @author Andrea Medeghini
 */
public class DefaultRendererConfigXMLExporterRuntime extends ExtensionConfigXMLExporterExtensionRuntime {
	/**
	 * @see net.sf.jame.core.extensionConfigXMLExporter.extension.ExtensionConfigXMLExporterExtensionRuntime#createXMLExporter()
	 */
	@Override
	public XMLExporter<DefaultRendererConfig> createXMLExporter() {
		return new DefaultRendererConfigXMLExporter();
	}

	private class DefaultRendererConfigXMLExporter extends AbstractPaletteRendererConfigXMLExporter<DefaultRendererConfig> {
		/**
		 * @see net.sf.jame.mandelbrot.extensions.paletteRenderer.AbstractPaletteRendererConfigXMLExporter#getConfigElementClassId()
		 */
		@Override
		protected String getConfigElementClassId() {
			return "DefaultRendererConfig";
		}

		/**
		 * @see net.sf.jame.mandelbrot.extensions.paletteRenderer.AbstractPaletteRendererConfigXMLExporter#exportProperties(net.sf.jame.mandelbrot.paletteRenderer.extension.PaletteRendererExtensionConfig, org.w3c.dom.Element, net.sf.jame.core.xml.XMLNodeBuilder)
		 */
		@Override
		protected void exportProperties(final DefaultRendererConfig config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			exportRenderedPalette(config, createProperty(builder, element, "renderedPalette"), builder);
		}

		/**
		 * @param config
		 * @param element
		 * @param builder
		 * @throws XMLExportException
		 */
		protected void exportRenderedPalette(final DefaultRendererConfig config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new RenderedPaletteElementXMLExporter().exportToElement(config.getRenderedPaletteElement(), builder));
		}
	}
}
