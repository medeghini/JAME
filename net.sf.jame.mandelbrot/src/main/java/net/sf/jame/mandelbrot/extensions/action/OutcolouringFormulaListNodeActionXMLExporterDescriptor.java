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
package net.sf.jame.mandelbrot.extensions.action;

import net.sf.jame.core.actionXMLExporter.extension.ActionXMLExporterExtensionDescriptor;
import net.sf.jame.mandelbrot.extensions.action.OutcolouringFormulaListNodeActionXMLExporterRuntime;

/**
 * @author Andrea Medeghini
 */
public class OutcolouringFormulaListNodeActionXMLExporterDescriptor extends ActionXMLExporterExtensionDescriptor {
	/**
	 * Returns the extensionId.
	 * 
	 * @return the extensionId.
	 */
	public String getExtensionId() {
		return "node.class.OutcolouringFormulaElementList";
	}

	/**
	 * Returns the extensionName.
	 * 
	 * @return the extensionName.
	 */
	public String getExtensionName() {
		return "OutcolouringFormulaElementList";
	}

	/**
	 * Returns the extensionRuntimeClass.
	 * 
	 * @return the extensionRuntimeClass.
	 */
	public OutcolouringFormulaListNodeActionXMLExporterRuntime getExtensionRuntime() {
		return new OutcolouringFormulaListNodeActionXMLExporterRuntime();
	}
}
