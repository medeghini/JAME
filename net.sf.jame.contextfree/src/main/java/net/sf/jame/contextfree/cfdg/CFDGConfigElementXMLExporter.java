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
package net.sf.jame.contextfree.cfdg;

import net.sf.jame.contextfree.figure.FigureConfigElementXMLExporter;
import net.sf.jame.core.common.BooleanElementXMLExporter;
import net.sf.jame.core.common.ColorElementXMLExporter;
import net.sf.jame.core.common.FloatElementXMLExporter;
import net.sf.jame.core.common.StringElementXMLExporter;
import net.sf.jame.core.extension.ExtensionException;
import net.sf.jame.core.xml.XMLExportException;
import net.sf.jame.core.xml.XMLExporter;
import net.sf.jame.core.xml.XMLNodeBuilder;

import org.w3c.dom.Element;

/**
 * @author Andrea Medeghini
 */
public class CFDGConfigElementXMLExporter extends XMLExporter<CFDGConfigElement> {
	/**
	 * @see net.sf.jame.core.xml.XMLExporter#exportToElement(java.lang.Object, net.sf.jame.core.xml.XMLNodeBuilder)
	 */
	@Override
	public Element exportToElement(final CFDGConfigElement configElement, final XMLNodeBuilder builder) throws XMLExportException {
		final Element element = this.createElement(builder, CFDGConfigElement.CLASS_ID);
		try {
			exportProperties(configElement, element, builder);
		}
		catch (final ExtensionException e) {
			throw new XMLExportException(e);
		}
		return element;
	}

	/**
	 * @see net.sf.jame.core.common.ConfigurableExtensionReferenceElementXMLExporter#exportProperties(net.sf.jame.twister.util.ConfigurableExtensionConfigElement, org.w3c.dom.Element, net.sf.jame.core.xml.XMLNodeBuilder, java.lang.String)
	 */
	protected void exportProperties(final CFDGConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws ExtensionException, XMLExportException {
		exportBaseDir(configElement, createProperty(builder, element, "baseDir"), builder);
		exportVariation(configElement, createProperty(builder, element, "variation"), builder);
		exportStartshape(configElement, createProperty(builder, element, "startshape"), builder);
		exportBackground(configElement, createProperty(builder, element, "background"), builder);
		exportUseSize(configElement, createProperty(builder, element, "useSize"), builder);
		exportX(configElement, createProperty(builder, element, "x"), builder);
		exportY(configElement, createProperty(builder, element, "y"), builder);
		exportWidth(configElement, createProperty(builder, element, "width"), builder);
		exportHeight(configElement, createProperty(builder, element, "height"), builder);
		exportUseTile(configElement, createProperty(builder, element, "useTile"), builder);
		exportTileWidth(configElement, createProperty(builder, element, "tileWidth"), builder);
		exportTileHeight(configElement, createProperty(builder, element, "tileHeight"), builder);
		exportFigureListElement(configElement, createProperty(builder, element, "figureList"), builder);
	}

	private void exportBaseDir(final CFDGConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new StringElementXMLExporter().exportToElement(configElement.getBaseDirElement(), builder));
	}
	private void exportVariation(final CFDGConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new StringElementXMLExporter().exportToElement(configElement.getVariationElement(), builder));
	}
	private void exportStartshape(final CFDGConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new StringElementXMLExporter().exportToElement(configElement.getStartshapeElement(), builder));
	}
	private void exportBackground(final CFDGConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new ColorElementXMLExporter().exportToElement(configElement.getBackgroundElement(), builder));
	}
	private void exportUseSize(final CFDGConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new BooleanElementXMLExporter().exportToElement(configElement.getUseSizeElement(), builder));
	}
	private void exportX(final CFDGConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new FloatElementXMLExporter().exportToElement(configElement.getXElement(), builder));
	}
	private void exportY(final CFDGConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new FloatElementXMLExporter().exportToElement(configElement.getYElement(), builder));
	}
	private void exportWidth(final CFDGConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new FloatElementXMLExporter().exportToElement(configElement.getWidthElement(), builder));
	}
	private void exportHeight(final CFDGConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new FloatElementXMLExporter().exportToElement(configElement.getHeightElement(), builder));
	}
	private void exportUseTile(final CFDGConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new BooleanElementXMLExporter().exportToElement(configElement.getUseTileElement(), builder));
	}
	private void exportTileWidth(final CFDGConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new FloatElementXMLExporter().exportToElement(configElement.getTileWidthElement(), builder));
	}
	private void exportTileHeight(final CFDGConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new FloatElementXMLExporter().exportToElement(configElement.getTileHeightElement(), builder));
	}
	private void exportFigureListElement(final CFDGConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		final FigureConfigElementXMLExporter figureExporter = new FigureConfigElementXMLExporter();
		for (int i = 0; i < configElement.getFigureConfigElementCount(); i++) {
			element.appendChild(figureExporter.exportToElement(configElement.getFigureConfigElement(i), builder));
		}
	}
}
