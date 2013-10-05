/*
 * JAME 6.1 
 * http://jame.sourceforge.net
 *
 * Copyright 2001, 2010 Andrea Medeghini
 * http://andreamedeghini.users.sourceforge.net
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
package net.sf.jame.core.extension;

/**
 * An extension reference is a value object which identifies an extension and contains its configuration.
 * 
 * @author Andrea Medeghini
 * @param <T> the extension configuration type.
 */
public class ConfigurableExtensionReference<T extends ExtensionConfig> extends ExtensionReference {
	private static final long serialVersionUID = 1L;
	private T extensionConfig;

	/**
	 * Constructs a new extension reference.
	 * 
	 * @param extensionId the extensionId.
	 * @param extensionName the extensionName.
	 * @param extensionConfig the extension configuration.
	 */
	public ConfigurableExtensionReference(final String extensionId, final String extensionName, final T extensionConfig) {
		super(extensionId, extensionName);
		if (extensionConfig == null) {
			throw new IllegalArgumentException("extensionConfig == null");
		}
		this.extensionConfig = extensionConfig;
		this.extensionConfig.setExtensionReference(this);
	}

	/**
	 * Constructs a new extension reference from a given reference.
	 * 
	 * @param reference the reference.
	 * @throws CloneNotSupportedException if the configuration can't be cloned.
	 */
	public ConfigurableExtensionReference(final ConfigurableExtensionReference<T> reference) throws CloneNotSupportedException {
		this(reference.getExtensionId(), reference.getExtensionName(), reference.getExtensionConfigAsCopy());
	}

	/**
	 * Returns the extension configuration.
	 * 
	 * @return the extension configuration.
	 */
	public T getExtensionConfig() {
		return this.extensionConfig;
	}

	/**
	 * Returns a copy of the extension configuration.
	 * 
	 * @return a copy of the extension configuration.
	 * @throws CloneNotSupportedException if the configuration can't be cloned.
	 */
	@SuppressWarnings("unchecked")
	public T getExtensionConfigAsCopy() throws CloneNotSupportedException {
		return (T) this.extensionConfig.clone();
	}

	/**
	 * @see net.sf.jame.core.extension.ExtensionReference#dump(java.lang.StringBuilder)
	 */
	@Override
	protected StringBuilder dump(final StringBuilder builder) {
		super.dump(builder);
		builder.append(", config = (");
		builder.append(this.extensionConfig);
		builder.append(")");
		return builder;
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ConfigurableExtensionReference<T> clone() {
		return new ConfigurableExtensionReference(getExtensionId(), getExtensionName(), getExtensionConfig().clone());
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(final Object obj) {
		return super.equals(obj) && (((extensionConfig == null) && (((ConfigurableExtensionReference<T>) obj).extensionConfig == null)) || ((extensionConfig != null) && extensionConfig.equals(((ConfigurableExtensionReference<T>) obj).extensionConfig)));
	}
}
