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
package net.sf.jame.core.config;

import java.io.Serializable;

import net.sf.jame.core.xml.XML;
import net.sf.jame.core.xml.XMLExportException;
import net.sf.jame.core.xml.XMLExporter;
import net.sf.jame.core.xml.XMLNodeBuilder;

import org.w3c.dom.Element;

/**
 * Abstract value element exporter.
 * 
 * @author Andrea Medeghini
 */
public abstract class ValueConfigElementXMLExporter<V extends Serializable, T extends ValueConfigElement<V>> extends XMLExporter<T> {
	/**
	 * @see net.sf.jame.core.xml.XMLExporter#exportToElement(java.lang.Object, net.sf.jame.core.xml.XMLNodeBuilder)
	 */
	@Override
	public Element exportToElement(final T configElement, final XMLNodeBuilder builder) throws XMLExportException {
		final Element element = this.createElement(builder, configElement.getClassId());
		this.exportProperties(configElement, element, builder);
		return element;
	}

	/**
	 * @param configElement
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	protected void exportProperties(final T configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		this.exportValue(configElement, createProperty(builder, element, "value"), builder);
	}

	/**
	 * @param configElement
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	protected void exportValue(final T configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(XML.createStringElement(builder, "value", this.formatValue(configElement.getValue())));
	}

	/**
	 * Formats the value.
	 * 
	 * @param value the value.
	 * @return the formatted value.
	 */
	protected abstract String formatValue(V value);
}
