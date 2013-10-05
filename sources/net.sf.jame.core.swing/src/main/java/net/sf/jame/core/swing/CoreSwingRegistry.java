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
package net.sf.jame.core.swing;

import net.sf.jame.core.extension.Extension;
import net.sf.jame.core.extension.ExtensionNotFoundException;
import net.sf.jame.core.extension.ExtensionRegistry;
import net.sf.jame.core.swing.editor.extension.EditorExtensionRegistry;
import net.sf.jame.core.swing.editor.extension.EditorExtensionRuntime;

/**
 * The twister registry.
 * 
 * @author Andrea Medeghini
 */
public class CoreSwingRegistry {
	private ExtensionRegistry<EditorExtensionRuntime> editorRegistry;

	private static class RegistryHolder {
		private static final CoreSwingRegistry instance = new CoreSwingRegistry();
	}

	private CoreSwingRegistry() {
		setEditorRegistry(new EditorExtensionRegistry());
	}

	/**
	 * @return
	 */
	public static CoreSwingRegistry getInstance() {
		return RegistryHolder.instance;
	}

	/**
	 * Returns a editor extension.
	 * 
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public Extension<EditorExtensionRuntime> getEditorExtension(final String extensionId) throws ExtensionNotFoundException {
		return editorRegistry.getExtension(extensionId);
	}

	private void setEditorRegistry(final ExtensionRegistry<EditorExtensionRuntime> editorRegistry) {
		this.editorRegistry = editorRegistry;
	}

	/**
	 * @return the editorRegistry
	 */
	public ExtensionRegistry<EditorExtensionRuntime> getEditorRegistry() {
		return editorRegistry;
	}
}