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
public class MoveToPathReplacementConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see net.sf.jame.core.xml.extension.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<MoveToPathReplacementConfig> createXMLImporter() {
		return new MoveToPathReplacementConfigXMLImporter();
	}

	private class MoveToPathReplacementConfigXMLImporter extends XMLImporter<MoveToPathReplacementConfig> {
		protected MoveToPathReplacementConfig createExtensionConfig() {
			return new MoveToPathReplacementConfig();
		}

		protected String getConfigElementClassId() {
			return "MoveToPathReplacementConfig";
		}
		
		/**
		 * @see net.sf.jame.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
		 */
		@Override
		public MoveToPathReplacementConfig importFromElement(final Element element) throws XMLImportException {
			checkClassId(element, this.getConfigElementClassId());
			final MoveToPathReplacementConfig extensionConfig = this.createExtensionConfig();
			final List<Element> propertyElements = getProperties(element);
			if (propertyElements.size() == 2) {
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
		protected void importProperties(final MoveToPathReplacementConfig extensionConfig, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
			importX(extensionConfig, propertyElements.get(0));
			importY(extensionConfig, propertyElements.get(1));
		}
	
		private void importX(final MoveToPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> xElements = this.getElements(element, FloatElement.CLASS_ID);
			if (xElements.size() == 1) {
				extensionConfig.setX(new FloatElementXMLImporter().importFromElement(xElements.get(0)).getValue());
			}
		}
		private void importY(final MoveToPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> yElements = this.getElements(element, FloatElement.CLASS_ID);
			if (yElements.size() == 1) {
				extensionConfig.setY(new FloatElementXMLImporter().importFromElement(yElements.get(0)).getValue());
			}
		}
	}
}
