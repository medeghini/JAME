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
package net.sf.jame.core.swing.extension;

import java.util.Collections;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ListDataListener;

import net.sf.jame.core.extension.Extension;
import net.sf.jame.core.extension.ExtensionComparator;
import net.sf.jame.core.extension.ExtensionNotFoundException;
import net.sf.jame.core.extension.ExtensionRegistry;
import net.sf.jame.core.extension.NullExtension;

/**
 * A model for extensions lists.
 * 
 * @author Andrea Medeghini
 */
public class ExtensionComboBoxModel implements ComboBoxModel {
	private static final long serialVersionUID = 1L;
	private final DefaultComboBoxModel model = new DefaultComboBoxModel();
	private final ExtensionRegistry<?> registry;
	private final boolean isNullExtensionAllowed;

	/**
	 * Constructs a new model.
	 * 
	 * @param registry the extension registry.
	 * @param isNullExtensionAllowed true if null extension is allowed
	 */
	public ExtensionComboBoxModel(final ExtensionRegistry<?> registry, final boolean isNullExtensionAllowed) {
		this(registry, null, isNullExtensionAllowed);
	}

	/**
	 * Constructs a new model.
	 * 
	 * @param registry the extension registry.
	 * @param filter the extension filter
	 * @param isNullExtensionAllowed true if null extension is allowed
	 */
	public ExtensionComboBoxModel(final ExtensionRegistry<?> registry, final ExtensionFilter filter, final boolean isNullExtensionAllowed) {
		if (registry == null) {
			throw new NullPointerException("registry == null");
		}
		this.isNullExtensionAllowed = isNullExtensionAllowed;
		reload(registry, filter);
		this.registry = registry;
	}

	/**
	 * @param registry
	 * @param filter
	 */
	@SuppressWarnings("unchecked")
	public void reload(final ExtensionRegistry registry, final ExtensionFilter filter) {
		try {
			model.removeAllElements();
			final List<Extension<?>> extensions = registry.getExtensionList();
			Collections.sort(extensions, new ExtensionComparator());
			if (isNullExtensionAllowed) {
				model.addElement(NullExtension.getInstance());
			}
			for (final Extension extension : extensions) {
				if ((filter == null) || filter.accept(extension)) {
					model.addElement(extension);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param extensionId
	 */
	public void setSelectedItemByExtensionId(final String extensionId) {
		try {
			setSelectedItem(registry.getExtension(extensionId));
		}
		catch (final ExtensionNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see javax.swing.ComboBoxModel#setSelectedItem(java.lang.Object)
	 */
	public void setSelectedItem(final Object item) {
		model.setSelectedItem(item);
	}

	/**
	 * @see javax.swing.ComboBoxModel#getSelectedItem()
	 */
	public Object getSelectedItem() {
		return model.getSelectedItem();
	}

	/**
	 * @see javax.swing.ListModel#getSize()
	 */
	public int getSize() {
		return model.getSize();
	}

	/**
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	public Object getElementAt(final int index) {
		return model.getElementAt(index);
	}

	/**
	 * @see javax.swing.ListModel#addListDataListener(javax.swing.event.ListDataListener)
	 */
	public void addListDataListener(final ListDataListener listener) {
		model.addListDataListener(listener);
	}

	/**
	 * @see javax.swing.ListModel#removeListDataListener(javax.swing.event.ListDataListener)
	 */
	public void removeListDataListener(final ListDataListener listener) {
		model.removeListDataListener(listener);
	}

	/**
	 * @return
	 */
	public ExtensionRegistry<?> getRegistry() {
		return registry;
	}
}
