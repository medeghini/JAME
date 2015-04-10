/*
 * JAME 6.2
 * http://jame.sourceforge.net
 *
 * Copyright 2001, 2015 Andrea Medeghini
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
package net.sf.jame.core.util;

import java.io.Serializable;
import java.util.List;

import net.sf.jame.core.common.ExtensionReferenceElement;
import net.sf.jame.core.common.ExtensionReferenceElementXMLImporter;
import net.sf.jame.core.extension.ExtensionRegistry;
import net.sf.jame.core.extension.ExtensionRuntime;
import net.sf.jame.core.tree.NodeActionValue;
import net.sf.jame.core.xml.XML;
import net.sf.jame.core.xml.XMLImportException;

import org.w3c.dom.Element;

/**
 * @author Andrea Medeghini
 */
public abstract class AbstractExtensionReferenceElementNodeActionXMLImporterRuntime extends AbstractActionXMLImporterRuntime {
	private final ExtensionRegistry<? extends ExtensionRuntime> registry;

	/**
	 * @param registry
	 */
	public AbstractExtensionReferenceElementNodeActionXMLImporterRuntime(final ExtensionRegistry<? extends ExtensionRuntime> registry) {
		this.registry = registry;
	}

	/**
	 * @see net.sf.jame.core.util.AbstractActionXMLImporterRuntime#importParams(net.sf.jame.core.tree.NodeActionValue, org.w3c.dom.Element)
	 */
	@Override
	protected void importParams(final NodeActionValue action, final Element element) throws XMLImportException {
		final ExtensionReferenceElementXMLImporter importer = new ExtensionReferenceElementXMLImporter(registry);
		final List<Element> elements = XML.getElementsByName(element, "element");
		if (elements.size() == 2) {
			final ExtensionReferenceElement configElement0 = importer.importFromElement(elements.get(0));
			final ExtensionReferenceElement configElement1 = importer.importFromElement(elements.get(1));
			action.setActionParams(new Serializable[] { configElement0.getReference(), configElement1.getReference() });
		}
	}
}
