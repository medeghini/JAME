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
package net.sf.jame.core.tree;

/**
 * @author Andrea Medeghini
 */
public abstract class AttributeNode extends DefaultNode {
	/**
	 * Constructs a new attribute node.
	 * 
	 * @param nodeId the nodeId.
	 */
	public AttributeNode(final String nodeId) {
		super(nodeId);
	}

	/**
	 * @see net.sf.jame.core.tree.Node#isAttribute()
	 */
	@Override
	public final boolean isAttribute() {
		return true;
	}

	/**
	 * @see net.sf.jame.core.tree.Node#isEditable()
	 */
	@Override
	public boolean isEditable() {
		return false;
	}

	/**
	 * @see net.sf.jame.core.tree.Node#addLabel(java.lang.StringBuilder)
	 */
	@Override
	protected void addLabel(final StringBuilder builder) {
		super.addLabel(builder);
		if (getNodeLabel() != null) {
			builder.append(" = ");
			builder.append(getValueAsString());
		}
	}
}
