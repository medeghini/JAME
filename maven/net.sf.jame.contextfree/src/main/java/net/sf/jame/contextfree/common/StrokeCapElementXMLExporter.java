/*
 * $Id:$
 *
 */
package net.sf.jame.contextfree.common;

import net.sf.jame.core.config.ValueConfigElementXMLExporter;

/**
 * @author Andrea Medeghini
 */
public class StrokeCapElementXMLExporter extends ValueConfigElementXMLExporter<String, StrokeCapElement> {
	/**
	 * @see net.sf.jame.core.config.ValueConfigElementXMLExporter#formatValue(java.io.Serializable)
	 */
	@Override
	protected String formatValue(final String value) {
		return value;
	}
}