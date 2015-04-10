/*
 * $Id:$
 *
 */
package net.sf.jame.contextfree.extensions.shapeAdjustment;

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
public class SkewShapeAdjustmentConfigXMLExporterRuntime extends ExtensionConfigXMLExporterExtensionRuntime {
	/**
	 * @see net.sf.jame.core.extensionConfigXMLExporter.extension.ExtensionConfigXMLExporterExtensionRuntime#createXMLExporter()
	 */
	@Override
	public XMLExporter<SkewShapeAdjustmentConfig> createXMLExporter() {
		return new SkewShapeAdjustmentConfigXMLExporter();
	}

	private class SkewShapeAdjustmentConfigXMLExporter extends XMLExporter<SkewShapeAdjustmentConfig> {
		protected String getConfigElementClassId() {
			return "SkewShapeAdjustmentConfig";
		}
		
		/**
		 * @see net.sf.jame.core.xml.XMLExporter#exportToElement(java.lang.Object, net.sf.jame.core.xml.XMLNodeBuilder)
		 */
		@Override
		public Element exportToElement(final SkewShapeAdjustmentConfig extensionConfig, final XMLNodeBuilder builder) throws XMLExportException {
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
		protected void exportProperties(final SkewShapeAdjustmentConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws ExtensionException, XMLExportException {
			exportShearX(extensionConfig, createProperty(builder, element, "shearX"), builder);
			exportShearY(extensionConfig, createProperty(builder, element, "shearY"), builder);
		}
	
		private void exportShearX(final SkewShapeAdjustmentConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new FloatElementXMLExporter().exportToElement(extensionConfig.getShearXElement(), builder));
		}
		private void exportShearY(final SkewShapeAdjustmentConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new FloatElementXMLExporter().exportToElement(extensionConfig.getShearYElement(), builder));
		}
	}
}
