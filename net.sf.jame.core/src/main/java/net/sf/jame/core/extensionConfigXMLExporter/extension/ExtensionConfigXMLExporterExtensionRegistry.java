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
package net.sf.jame.core.extensionConfigXMLExporter.extension;

import net.sf.jame.core.extension.sl.SLExtensionBuilder;
import net.sf.jame.core.extension.sl.SLExtensionRegistry;

/**
 * @author Andrea Medeghini
 */
public class ExtensionConfigXMLExporterExtensionRegistry extends SLExtensionRegistry<ExtensionConfigXMLExporterExtensionRuntime> {
	/**
	 * the extension point name.
	 */
	public static final String EXTENSION_POINT_NAME = "net.sf.jame.core.extensions";
	/**
	 * the configuration element name.
	 */
	public static final String CONFIGURATION_ELEMENT_NAME = "ExtensionConfigXMLExporter";
	/**
	 * the extension descriptor class.
	 */
	public static final Class<? extends ExtensionConfigXMLExporterExtensionDescriptor> EXTENSION_DESCRIPTOR_CLASS = ExtensionConfigXMLExporterExtensionDescriptor.class;

	/**
	 * Constructs a new registry.
	 */
	public ExtensionConfigXMLExporterExtensionRegistry() {
		super(ExtensionConfigXMLExporterExtensionRegistry.EXTENSION_DESCRIPTOR_CLASS, ExtensionConfigXMLExporterExtensionRegistry.EXTENSION_POINT_NAME, new SLExtensionBuilder<ExtensionConfigXMLExporterExtensionRuntime>(ExtensionConfigXMLExporterExtensionRegistry.CONFIGURATION_ELEMENT_NAME));
	}
}
