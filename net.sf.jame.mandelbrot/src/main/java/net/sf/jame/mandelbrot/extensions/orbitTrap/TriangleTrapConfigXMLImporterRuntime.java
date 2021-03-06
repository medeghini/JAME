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
package net.sf.jame.mandelbrot.extensions.orbitTrap;

import java.util.List;

import net.sf.jame.core.common.DoubleElement;
import net.sf.jame.core.common.DoubleElementXMLImporter;
import net.sf.jame.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRuntime;
import net.sf.jame.core.xml.XMLImportException;
import net.sf.jame.core.xml.XMLImporter;
import net.sf.jame.mandelbrot.common.CriteriaElement;
import net.sf.jame.mandelbrot.common.CriteriaElementXMLImporter;

import org.w3c.dom.Element;

/**
 * @author Andrea Medeghini
 */
public class TriangleTrapConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see net.sf.jame.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<TriangleTrapConfig> createXMLImporter() {
		return new TriangleTrapConfigXMLImporter();
	}

	private class TriangleTrapConfigXMLImporter extends AbstractOrbitTrapConfigXMLImporter<TriangleTrapConfig> {
		/**
		 * @see net.sf.jame.mandelbrot.extensions.incolouringFormula.AbstractOrbitTrapConfigXMLImporter#createExtensionConfig()
		 */
		@Override
		protected TriangleTrapConfig createExtensionConfig() {
			return new TriangleTrapConfig();
		}

		/**
		 * @see net.sf.jame.mandelbrot.extensions.incolouringFormula.AbstractOrbitTrapConfigXMLImporter#getConfigElementClassId()
		 */
		@Override
		protected String getConfigElementClassId() {
			return "TriangleTrapConfig";
		}

		/**
		 * @see net.sf.jame.mandelbrot.extensions.incolouringFormula.AbstractOrbitTrapConfigXMLImporter#getPropertiesSize()
		 */
		@Override
		protected int getPropertiesSize() {
			return 4;
		}

		/**
		 * @see net.sf.jame.mandelbrot.extensions.incolouringFormula.AbstractOrbitTrapConfigXMLImporter#importProperties(net.sf.jame.mandelbrot.incolouringFormula.extension.OrbitTrapExtensionConfig, java.util.List)
		 */
		@Override
		protected void importProperties(final TriangleTrapConfig config, final List<Element> propertyElements) throws XMLImportException {
			importWidth(config, propertyElements.get(0));
			importHeight(config, propertyElements.get(1));
			importRotation(config, propertyElements.get(2));
			importCriteria(config, propertyElements.get(3));
		}

		/**
		 * @param config
		 * @param element
		 * @throws XMLImportException
		 */
		protected void importWidth(final TriangleTrapConfig config, final Element element) throws XMLImportException {
			final List<Element> elements = this.getElements(element, DoubleElement.CLASS_ID);
			if (elements.size() == 1) {
				config.getWidthElement().setValue(new DoubleElementXMLImporter().importFromElement(elements.get(0)).getValue());
			}
		}

		/**
		 * @param config
		 * @param element
		 * @throws XMLImportException
		 */
		protected void importHeight(final TriangleTrapConfig config, final Element element) throws XMLImportException {
			final List<Element> elements = this.getElements(element, DoubleElement.CLASS_ID);
			if (elements.size() == 1) {
				config.getHeightElement().setValue(new DoubleElementXMLImporter().importFromElement(elements.get(0)).getValue());
			}
		}

		/**
		 * @param config
		 * @param element
		 * @throws XMLImportException
		 */
		protected void importRotation(final TriangleTrapConfig config, final Element element) throws XMLImportException {
			final List<Element> elements = this.getElements(element, DoubleElement.CLASS_ID);
			if (elements.size() == 1) {
				config.getRotationElement().setValue(new DoubleElementXMLImporter().importFromElement(elements.get(0)).getValue());
			}
		}

		/**
		 * @param config
		 * @param element
		 * @throws XMLImportException
		 */
		protected void importCriteria(final TriangleTrapConfig config, final Element element) throws XMLImportException {
			final List<Element> elements = this.getElements(element, CriteriaElement.CLASS_ID);
			if (elements.size() == 1) {
				config.getCriteriaElement().setValue(new CriteriaElementXMLImporter().importFromElement(elements.get(0)).getValue());
			}
		}
	}
}
