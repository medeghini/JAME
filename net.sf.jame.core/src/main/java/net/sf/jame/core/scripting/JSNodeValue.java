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
package net.sf.jame.core.scripting;

import net.sf.jame.core.config.ConfigElement;
import net.sf.jame.core.extension.ExtensionReference;
import net.sf.jame.core.tree.NodeValue;

/**
 * @author Andrea Medeghini
 */
public class JSNodeValue {
	private final NodeValue<?> value;

	/**
	 * @param value
	 */
	public JSNodeValue(final NodeValue<?> value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	NodeValue<?> getValue() {
		return value;
	}

	/**
	 * @return
	 */
	public Object toJSObject() {
		if ((value != null) && (value.getValue() != null)) {
			if (value.getValue() instanceof ConfigElement) {
				return ((ConfigElement) value.getValue()).toString();
			}
			if (value.getValue() instanceof ExtensionReference) {
				return ((ExtensionReference) value.getValue()).getExtensionId();
			}
			return value.getValue();
		}
		return null;
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new JSNodeValue((value != null ? value.clone() : null));
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj.getClass() != JSNodeValue.class) {
			return false;
		}
		return value.equals(((JSNodeValue) obj).value);
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return value.hashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return value.toString();
	}
}
