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
 * SL configurable extension.
 * 
 * @author Andrea Medeghini
 * @param <T> the extension runtime type.
 * @param <V> the extension configuration type.
 */
public class SLConfigurableExtension<T extends ConfigurableExtensionRuntime<? extends V>, V extends ExtensionConfig> extends SLExtension<T> implements ConfigurableExtension<T, V> {
	/**
	 * the name of the extension configuration class property.
	 */
	public static final String EXTENSION_CONFIG_CLASS_PROPERTY_NAME = "configClass";

	/**
	 * Constructs a new extension from a configuration element.
	 * 
	 * @param cfgElement the configuration element.
	 * @throws ExtensionException if the extension can't be created.
	 */
	protected SLConfigurableExtension(final ConfigurableExtensionDescriptor<T, V> extensionDescriptor) throws ExtensionException {
		super(extensionDescriptor);
	}

	/**
	 * @see net.sf.jame.core.extension.ConfigurableExtension#createDefaultExtensionConfig()
	 */
	@SuppressWarnings("unchecked")
	public final V createDefaultExtensionConfig() throws ExtensionException {
		return ((ConfigurableExtensionDescriptor<T, V>) getExtensionDescriptor()).getExtensionConfig();
	}

	/**
	 * @see net.sf.jame.core.extension.ConfigurableExtension#createConfigurableExtensionReference(net.sf.jame.core.extension.ExtensionConfig)
	 */
	public ConfigurableExtensionReference<V> createConfigurableExtensionReference(final V extensionConfig) {
		return new ConfigurableExtensionReference<V>(getExtensionId(), getExtensionName(), extensionConfig);
	}

	/**
	 * @see net.sf.jame.core.extension.ConfigurableExtension#createConfigurableExtensionReference()
	 */
	public ConfigurableExtensionReference<V> createConfigurableExtensionReference() throws ExtensionException {
		return new ConfigurableExtensionReference<V>(getExtensionId(), getExtensionName(), this.createDefaultExtensionConfig());
	}
}
