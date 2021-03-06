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
package net.sf.jame.core.extension.sl;

import net.sf.jame.core.extension.*;

/**
 * SL extension.
 * 
 * @author Andrea Medeghini
 * @param <T> the extension runtime type.
 */
public class SLExtension<T extends ExtensionRuntime> implements Extension<T> {
	/**
	 * the name of the extensionId property.
	 */
	public static final String EXTENSION_ID_PROPERTY_NAME = "id";
	/**
	 * the name of the extensionName property.
	 */
	public static final String EXTENSION_NAME_PROPERTY_NAME = "name";
	/**
	 * the name of the extension runtime class property.
	 */
	public static final String EXTENSION_RUNTIME_CLASS_PROPERTY_NAME = "runtimeClass";
	private ExtensionDescriptor<T> extensionDescriptor;
	private final ExtensionReference reference;

	/**
	 * Constructs a new extension from a configuration element.
	 * 
	 * @param extensionDescriptor the extension descriptor.
	 * @throws ExtensionException if the extension can't be created.
	 */
	protected SLExtension(final ExtensionDescriptor<T> extensionDescriptor) throws ExtensionException {
		this.extensionDescriptor = extensionDescriptor;
		this.reference = new ExtensionReference(extensionDescriptor.getExtensionId(), extensionDescriptor.getExtensionName());
	}

	/**
	 * @see net.sf.jame.core.extension.Extension#createExtensionRuntime()
	 */
	public final T createExtensionRuntime() throws ExtensionException {
		return this.extensionDescriptor.getExtensionRuntime();
	}

	/**
	 * @see net.sf.jame.core.extension.Extension#getExtensionReference()
	 */
	public ExtensionReference getExtensionReference() {
		return this.reference;
	}

	/**
	 * @see net.sf.jame.core.extension.Extension#getExtensionId()
	 */
	public String getExtensionId() {
		return this.reference.getExtensionId();
	}

	/**
	 * @see net.sf.jame.core.extension.Extension#getExtensionName()
	 */
	public String getExtensionName() {
		return this.reference.getExtensionName();
	}

	/**
	 * Returns the extension descriptor class.
	 * @return the extension descriptor class.
	 */
	protected ExtensionDescriptor<T> getExtensionDescriptor() {
		return extensionDescriptor;
	}
}
