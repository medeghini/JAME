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
package net.sf.jame.core.extensions.enumerator;

import net.sf.jame.core.CoreRegistry;
import net.sf.jame.core.enumerator.extension.EnumeratorExtensionRuntime;
import net.sf.jame.core.extension.Extension;
import net.sf.jame.core.extension.ExtensionNotFoundException;
import net.sf.jame.core.scripting.JSException;
import net.sf.jame.core.scripting.JSExtension;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Andrea Medeghini
 */
public class EnumeratorEnumeratorRuntime extends EnumeratorExtensionRuntime {
	/**
	 * @see net.sf.jame.core.enumerator.extension.EnumeratorExtensionRuntime#listExtensions()
	 */
	@Override
	public List<String> listExtensions() throws JSException {
		List<Extension<EnumeratorExtensionRuntime>> extensions = CoreRegistry.getInstance().getEnumeratorRegistry().getExtensionList();
		List<String> references = new LinkedList<String>();
		for (Extension<EnumeratorExtensionRuntime> extension : extensions) {
			references.add(extension.getExtensionId());
		}
		return references;
	}

	/**
	 * @see net.sf.jame.core.enumerator.extension.EnumeratorExtensionRuntime#getExtension(java.lang.String)
	 */
	@Override
	public JSExtension getExtension(final String extensionId) throws JSException {
		try {
			return new JSExtension(CoreRegistry.getInstance().getEnumeratorExtension(extensionId));
		}
		catch (ExtensionNotFoundException e) {
			throw new JSException(e);
		}
	}
}
