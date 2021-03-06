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

import java.util.List;

import net.sf.jame.core.common.FloatElement;
import net.sf.jame.core.common.FloatElementXMLImporter;
import net.sf.jame.core.extension.ExtensionException;
import net.sf.jame.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRuntime;
import net.sf.jame.core.xml.XMLImportException;
import net.sf.jame.core.xml.XMLImporter;

import org.w3c.dom.Element;

/**
 * @author Andrea Medeghini
 */
public class SmoothCurveToPathReplacementConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see net.sf.jame.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<SmoothCurveToPathReplacementConfig> createXMLImporter() {
		return new SmoothCurveToPathReplacementConfigXMLImporter();
	}

	private class SmoothCurveToPathReplacementConfigXMLImporter extends XMLImporter<SmoothCurveToPathReplacementConfig> {
		protected SmoothCurveToPathReplacementConfig createExtensionConfig() {
			return new SmoothCurveToPathReplacementConfig();
		}

		protected String getConfigElementClassId() {
			return "SmoothCurveToPathReplacementConfig";
		}
		
		/**
		 * @see net.sf.jame.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
		 */
		@Override
		public SmoothCurveToPathReplacementConfig importFromElement(final Element element) throws XMLImportException {
			checkClassId(element, this.getConfigElementClassId());
			final SmoothCurveToPathReplacementConfig extensionConfig = this.createExtensionConfig();
			final List<Element> propertyElements = getProperties(element);
			if (propertyElements.size() == 4) {
				try {
					importProperties(extensionConfig, propertyElements);
				}
				catch (final ExtensionException e) {
					throw new XMLImportException(e);
				}
			}
			return extensionConfig;
		}
	
		/**
		 * @param extensionConfig
		 * @param propertyElements
		 * @throws ExtensionException
		 * @throws XMLImportException
		 */
		protected void importProperties(final SmoothCurveToPathReplacementConfig extensionConfig, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
			importX(extensionConfig, propertyElements.get(0));
			importY(extensionConfig, propertyElements.get(1));
			importX2(extensionConfig, propertyElements.get(2));
			importY2(extensionConfig, propertyElements.get(3));
		}
	
		private void importX(final SmoothCurveToPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> xElements = this.getElements(element, FloatElement.CLASS_ID);
			if (xElements.size() == 1) {
				extensionConfig.setX(new FloatElementXMLImporter().importFromElement(xElements.get(0)).getValue());
			}
		}
		private void importY(final SmoothCurveToPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> yElements = this.getElements(element, FloatElement.CLASS_ID);
			if (yElements.size() == 1) {
				extensionConfig.setY(new FloatElementXMLImporter().importFromElement(yElements.get(0)).getValue());
			}
		}
		private void importX2(final SmoothCurveToPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> x2Elements = this.getElements(element, FloatElement.CLASS_ID);
			if (x2Elements.size() == 1) {
				extensionConfig.setX2(new FloatElementXMLImporter().importFromElement(x2Elements.get(0)).getValue());
			}
		}
		private void importY2(final SmoothCurveToPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> y2Elements = this.getElements(element, FloatElement.CLASS_ID);
			if (y2Elements.size() == 1) {
				extensionConfig.setY2(new FloatElementXMLImporter().importFromElement(y2Elements.get(0)).getValue());
			}
		}
	}
}
