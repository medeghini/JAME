/*
 * $Id:$
 *
 */
package net.sf.jame.contextfree.extensions.pathReplacement;

import java.util.List;

import net.sf.jame.core.common.FloatElement;
import net.sf.jame.core.common.FloatElementXMLImporter;
import net.sf.jame.core.extension.ExtensionException;
import net.sf.jame.core.xml.XMLImportException;
import net.sf.jame.core.xml.XMLImporter;
import net.sf.jame.core.xml.extension.ExtensionConfigXMLImporterExtensionRuntime;

import org.w3c.dom.Element;

/**
 * @author Andrea Medeghini
 */
public class QuadToPathReplacementConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see net.sf.jame.core.xml.extension.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<QuadToPathReplacementConfig> createXMLImporter() {
		return new QuadToPathReplacementConfigXMLImporter();
	}

	private class QuadToPathReplacementConfigXMLImporter extends XMLImporter<QuadToPathReplacementConfig> {
		protected QuadToPathReplacementConfig createExtensionConfig() {
			return new QuadToPathReplacementConfig();
		}

		protected String getConfigElementClassId() {
			return "QuadToPathReplacementConfig";
		}
		
		/**
		 * @see net.sf.jame.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
		 */
		@Override
		public QuadToPathReplacementConfig importFromElement(final Element element) throws XMLImportException {
			checkClassId(element, this.getConfigElementClassId());
			final QuadToPathReplacementConfig extensionConfig = this.createExtensionConfig();
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
		protected void importProperties(final QuadToPathReplacementConfig extensionConfig, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
			importX(extensionConfig, propertyElements.get(0));
			importY(extensionConfig, propertyElements.get(1));
			importX1(extensionConfig, propertyElements.get(2));
			importY1(extensionConfig, propertyElements.get(3));
		}
	
		private void importX(final QuadToPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> xElements = this.getElements(element, FloatElement.CLASS_ID);
			if (xElements.size() == 1) {
				extensionConfig.setX(new FloatElementXMLImporter().importFromElement(xElements.get(0)).getValue());
			}
		}
		private void importY(final QuadToPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> yElements = this.getElements(element, FloatElement.CLASS_ID);
			if (yElements.size() == 1) {
				extensionConfig.setY(new FloatElementXMLImporter().importFromElement(yElements.get(0)).getValue());
			}
		}
		private void importX1(final QuadToPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> x1Elements = this.getElements(element, FloatElement.CLASS_ID);
			if (x1Elements.size() == 1) {
				extensionConfig.setX1(new FloatElementXMLImporter().importFromElement(x1Elements.get(0)).getValue());
			}
		}
		private void importY1(final QuadToPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> y1Elements = this.getElements(element, FloatElement.CLASS_ID);
			if (y1Elements.size() == 1) {
				extensionConfig.setY1(new FloatElementXMLImporter().importFromElement(y1Elements.get(0)).getValue());
			}
		}
	}
}
