/*
 * $Id:$
 *
 */
package net.sf.jame.contextfree.extensions.figure;

import java.util.List;

import net.sf.jame.contextfree.shapeReplacement.ShapeReplacementConfigElement;
import net.sf.jame.contextfree.shapeReplacement.ShapeReplacementConfigElementXMLImporter;
import net.sf.jame.core.common.FloatElement;
import net.sf.jame.core.common.FloatElementXMLImporter;
import net.sf.jame.core.common.StringElement;
import net.sf.jame.core.common.StringElementXMLImporter;
import net.sf.jame.core.extension.ExtensionException;
import net.sf.jame.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRuntime;
import net.sf.jame.core.xml.XMLImportException;
import net.sf.jame.core.xml.XMLImporter;

import org.w3c.dom.Element;

/**
 * @author Andrea Medeghini
 */
public class RuleFigureConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see net.sf.jame.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<RuleFigureConfig> createXMLImporter() {
		return new RuleFigureConfigXMLImporter();
	}

	private class RuleFigureConfigXMLImporter extends XMLImporter<RuleFigureConfig> {
		protected RuleFigureConfig createExtensionConfig() {
			return new RuleFigureConfig();
		}

		protected String getConfigElementClassId() {
			return "RuleFigureConfig";
		}
		
		/**
		 * @see net.sf.jame.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
		 */
		@Override
		public RuleFigureConfig importFromElement(final Element element) throws XMLImportException {
			checkClassId(element, this.getConfigElementClassId());
			final RuleFigureConfig extensionConfig = this.createExtensionConfig();
			final List<Element> propertyElements = getProperties(element);
			if (propertyElements.size() == 3) {
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
		protected void importProperties(final RuleFigureConfig extensionConfig, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
			importName(extensionConfig, propertyElements.get(0));
			importProbability(extensionConfig, propertyElements.get(1));
			importShapeReplacementListElement(extensionConfig, propertyElements.get(2));
		}
	
		private void importName(final RuleFigureConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> nameElements = this.getElements(element, StringElement.CLASS_ID);
			if (nameElements.size() == 1) {
				extensionConfig.setName(new StringElementXMLImporter().importFromElement(nameElements.get(0)).getValue());
			}
		}
		private void importProbability(final RuleFigureConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> probabilityElements = this.getElements(element, FloatElement.CLASS_ID);
			if (probabilityElements.size() == 1) {
				extensionConfig.setProbability(new FloatElementXMLImporter().importFromElement(probabilityElements.get(0)).getValue());
			}
		}
		private void importShapeReplacementListElement(final RuleFigureConfig extensionConfig, final Element element) throws XMLImportException {
			final ShapeReplacementConfigElementXMLImporter shapeReplacementImporter = new ShapeReplacementConfigElementXMLImporter();
			final List<Element> shapeReplacementElements = this.getElements(element, ShapeReplacementConfigElement.CLASS_ID);
			for (int i = 0; i < shapeReplacementElements.size(); i++) {
				extensionConfig.appendShapeReplacementConfigElement(shapeReplacementImporter.importFromElement(shapeReplacementElements.get(i)));
			}
		}
	}
}
