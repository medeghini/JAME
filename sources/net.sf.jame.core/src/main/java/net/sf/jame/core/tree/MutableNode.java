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
package net.sf.jame.core.tree;

import net.sf.jame.core.CoreResources;

/**
 * @author Andrea Medeghini
 */
public abstract class MutableNode extends DefaultNode {
	/**
	 * Constructs a new attribute node.
	 * 
	 * @param nodeId the nodeId.
	 */
	public MutableNode(final String nodeId) {
		super(nodeId);
	}

	/**
	 * @see net.sf.jame.core.tree.Node#isEditable()
	 */
	@Override
	public boolean isEditable() {
		return false;
	}

	/**
	 * @see net.sf.jame.core.tree.Node#isMutable()
	 */
	@Override
	public final boolean isMutable() {
		return true;
	}

	/**
	 * @return the child type.
	 */
	public abstract Class<?> getChildValueType();

	/**
	 * @see net.sf.jame.core.tree.DefaultNode#getValueAsString()
	 */
	@Override
	public String getValueAsString() {
		return isEditable() ? super.getValueAsString() : getChildNodeCount() + " " + CoreResources.getInstance().getString("label.elements");
	}
}
