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
package net.sf.jame.twister.image;

import java.util.List;

import net.sf.jame.core.common.ConfigurableExtensionReferenceElement;
import net.sf.jame.core.common.ConfigurableExtensionReferenceElementXMLImporter;
import net.sf.jame.core.extension.ExtensionException;
import net.sf.jame.core.xml.XMLImportException;
import net.sf.jame.core.xml.XMLImporter;
import net.sf.jame.twister.TwisterRegistry;
import net.sf.jame.twister.image.extension.ImageExtensionConfig;

import org.w3c.dom.Element;

/**
 * @author Andrea Medeghini
 */
public class ImageConfigElementXMLImporter extends XMLImporter<ImageConfigElement> {
	/**
	 * @see net.sf.jame.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
	 */
	@Override
	public ImageConfigElement importFromElement(final Element element) throws XMLImportException {
		checkClassId(element, ImageConfigElement.CLASS_ID);
		final ImageConfigElement configElement = new ImageConfigElement();
		final List<Element> propertyElements = getProperties(element);
		if (propertyElements.size() == 1) {
			try {
				importProperties(configElement, propertyElements);
			}
			catch (final ExtensionException e) {
				throw new XMLImportException(e);
			}
		}
		return configElement;
	}

	/**
	 * @param configElement
	 * @param propertyElements
	 * @throws ExtensionException
	 * @throws XMLImportException
	 */
	protected void importProperties(final ImageConfigElement configElement, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
		importExtension(configElement, propertyElements.get(0));
	}

	private void importExtension(final ImageConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> extensionElements = this.getElements(element, ConfigurableExtensionReferenceElement.CLASS_ID);
		if (extensionElements.size() == 1) {
			configElement.setReference(new ConfigurableExtensionReferenceElementXMLImporter<ImageExtensionConfig>(TwisterRegistry.getInstance().getImageRegistry()).importFromElement(extensionElements.get(0)).getReference());
		}
	}
}
