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
package net.sf.jame.contextfree.extensions.pathReplacement;

import net.sf.jame.core.common.BooleanElementXMLExporter;
import net.sf.jame.core.common.FloatElementXMLExporter;
import net.sf.jame.core.extension.ExtensionException;
import net.sf.jame.core.extensionConfigXMLExporter.extension.ExtensionConfigXMLExporterExtensionRuntime;
import net.sf.jame.core.xml.XMLExportException;
import net.sf.jame.core.xml.XMLExporter;
import net.sf.jame.core.xml.XMLNodeBuilder;

import org.w3c.dom.Element;

/**
 * @author Andrea Medeghini
 */
public class ArcRelPathReplacementConfigXMLExporterRuntime extends ExtensionConfigXMLExporterExtensionRuntime {
	/**
	 * @see net.sf.jame.core.extensionConfigXMLExporter.extension.ExtensionConfigXMLExporterExtensionRuntime#createXMLExporter()
	 */
	@Override
	public XMLExporter<ArcRelPathReplacementConfig> createXMLExporter() {
		return new ArcRelPathReplacementConfigXMLExporter();
	}

	private class ArcRelPathReplacementConfigXMLExporter extends XMLExporter<ArcRelPathReplacementConfig> {
		protected String getConfigElementClassId() {
			return "ArcRelPathReplacementConfig";
		}
		
		/**
		 * @see net.sf.jame.core.xml.XMLExporter#exportToElement(java.lang.Object, net.sf.jame.core.xml.XMLNodeBuilder)
		 */
		@Override
		public Element exportToElement(final ArcRelPathReplacementConfig extensionConfig, final XMLNodeBuilder builder) throws XMLExportException {
			final Element element = this.createElement(builder, this.getConfigElementClassId());
			try {
				exportProperties(extensionConfig, element, builder);
			}
			catch (final ExtensionException e) {
				throw new XMLExportException(e);
			}
			return element;
		}
	
		/**
		 * @see net.sf.jame.core.common.ConfigurableExtensionReferenceElementXMLExporter#exportProperties(net.sf.jame.twister.util.ConfigurableExtensionConfigElement, org.w3c.dom.Element, net.sf.jame.core.xml.XMLNodeBuilder, java.lang.String)
		 */
		protected void exportProperties(final ArcRelPathReplacementConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws ExtensionException, XMLExportException {
			exportX(extensionConfig, createProperty(builder, element, "x"), builder);
			exportY(extensionConfig, createProperty(builder, element, "y"), builder);
			exportRx(extensionConfig, createProperty(builder, element, "rx"), builder);
			exportRy(extensionConfig, createProperty(builder, element, "ry"), builder);
			exportR(extensionConfig, createProperty(builder, element, "r"), builder);
			exportSweep(extensionConfig, createProperty(builder, element, "sweep"), builder);
			exportLarge(extensionConfig, createProperty(builder, element, "large"), builder);
		}
	
		private void exportX(final ArcRelPathReplacementConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new FloatElementXMLExporter().exportToElement(extensionConfig.getXElement(), builder));
		}
		private void exportY(final ArcRelPathReplacementConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new FloatElementXMLExporter().exportToElement(extensionConfig.getYElement(), builder));
		}
		private void exportRx(final ArcRelPathReplacementConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new FloatElementXMLExporter().exportToElement(extensionConfig.getRxElement(), builder));
		}
		private void exportRy(final ArcRelPathReplacementConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new FloatElementXMLExporter().exportToElement(extensionConfig.getRyElement(), builder));
		}
		private void exportR(final ArcRelPathReplacementConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new FloatElementXMLExporter().exportToElement(extensionConfig.getRElement(), builder));
		}
		private void exportSweep(final ArcRelPathReplacementConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new BooleanElementXMLExporter().exportToElement(extensionConfig.getSweepElement(), builder));
		}
		private void exportLarge(final ArcRelPathReplacementConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new BooleanElementXMLExporter().exportToElement(extensionConfig.getLargeElement(), builder));
		}
	}
}
