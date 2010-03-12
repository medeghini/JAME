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
package net.sf.jame.core.scripting;

import net.sf.jame.core.CoreRegistry;
import net.sf.jame.core.extension.ExtensionException;
import net.sf.jame.core.extension.ExtensionNotFoundException;
import net.sf.jame.core.scripting.extension.CreatorExtensionRuntime;
import net.sf.jame.core.tree.Node;

/**
 * @author Andrea Medeghini
 */
public class DefaultJSNode implements JSNode {
	private final Node node;

	/**
	 * @param node
	 */
	public DefaultJSNode(final Node node) {
		this.node = node;
		if (node == null) {
			throw new IllegalArgumentException("node is null");
		}
	}

	private CreatorExtensionRuntime getCreator(final String elementClassId) throws JSException {
		try {
			return CoreRegistry.getInstance().getCreatorExtension(elementClassId).createExtensionRuntime();
		}
		catch (ExtensionNotFoundException e) {
			throw new JSException(e);
		}
		catch (ExtensionException e) {
			throw new JSException(e);
		}
	}

	/**
	 * @see net.sf.jame.core.scripting.JSNode#getChildNode(int)
	 */
	public JSNode getChildNode(final int index) {
		Node childNode = node.getChildNode(index);
		if (childNode != null) {
			JSNode jsNode = (JSNode) childNode.getObject("scripting.jsNode");
			if (jsNode == null) {
				jsNode = new DefaultJSNode(childNode);
				childNode.putObject("scripting.jsNode", jsNode);
			}
			return jsNode;
		}
		else {
			return null;
		}
	}

	/**
	 * @see net.sf.jame.core.scripting.JSNode#getChildNodeCount()
	 */
	public int getChildNodeCount() {
		return node.getChildNodeCount();
	}

	/**
	 * @see net.sf.jame.core.scripting.JSNode#getIndex()
	 */
	public int getIndex() {
		return node.getParentNode().indexOf(node);
	}

	/**
	 * @see net.sf.jame.core.scripting.JSNode#getClassId()
	 */
	public String getClassId() {
		return node.getNodeClass();
	}

	/**
	 * @see net.sf.jame.core.scripting.JSNode#getId()
	 */
	public String getId() {
		return node.getNodeId();
	}

	/**
	 * @see net.sf.jame.core.scripting.JSNode#getPath()
	 */
	public String getPath() {
		return node.getNodePath().toString();
	}

	/**
	 * @see net.sf.jame.core.scripting.JSNode#getLabel()
	 */
	public String getLabel() {
		return node.getNodeLabel();
	}

	/**
	 * @see net.sf.jame.core.scripting.JSNode#getValue()
	 */
	public JSNodeValue getValue() {
		return new JSNodeValue(node.getNodeValue());
	}

	/**
	 * @see net.sf.jame.core.scripting.JSNode#getPreviousValue()
	 */
	public JSNodeValue getPreviousValue() {
		return new JSNodeValue(node.getPreviousNodeValue());
	}

	/**
	 * @see net.sf.jame.core.scripting.JSNode#hasValue()
	 */
	public boolean hasValue() {
		return node.getNodeValue() != null;
	}

	/**
	 * @see net.sf.jame.core.scripting.JSNode#isAttribute()
	 */
	public boolean isAttribute() {
		return node.isAttribute();
	}

	/**
	 * @see net.sf.jame.core.scripting.JSNode#isEditable()
	 */
	public boolean isEditable() {
		return node.isEditable();
	}

	/**
	 * @see net.sf.jame.core.scripting.JSNode#isMutable()
	 */
	public boolean isMutable() {
		return node.isMutable();
	}

	Class<?> getNodeValueType() throws JSException {
		if (node.getNodeEditor() == null) {
			throw new JSException("Editor not defined");
		}
		return node.getNodeEditor().getNodeValueType();
	}

	/**
	 * @see net.sf.jame.core.scripting.JSNode#createValueByArgs(java.lang.Object[])
	 */
	public JSNodeValue createValueByArgs(final Object... args) throws JSException {
		if (node.getNodeEditor() == null) {
			throw new JSException("Editor not defined");
		}
		return new JSNodeValue(node.getNodeEditor().createNodeValue(getCreator(getClassId()).create(args)));
	}

	/**
	 * @see net.sf.jame.core.scripting.JSNode#appendChildNode(net.sf.jame.core.tree.NodeValue)
	 */
	public void appendChildNode(final JSNodeValue value) throws JSException {
		if (node.getNodeEditor() == null) {
			throw new JSException("Editor not defined");
		}
		node.getNodeEditor().appendChildNode(value.getValue());
	}

	/**
	 * @see net.sf.jame.core.scripting.JSNode#insertChildNodeAfter(int, net.sf.jame.core.tree.NodeValue)
	 */
	public void insertChildNodeAfter(final int index, final JSNodeValue value) throws JSException {
		if (node.getNodeEditor() == null) {
			throw new JSException("Editor not defined");
		}
		node.getNodeEditor().insertChildNodeAfter(index, value.getValue());
	}

	/**
	 * @see net.sf.jame.core.scripting.JSNode#insertChildNodeAt(java.lang.Integer, net.sf.jame.core.tree.NodeValue)
	 */
	public void insertChildNodeAt(final Integer index, final JSNodeValue value) throws JSException {
		if (node.getNodeEditor() == null) {
			throw new JSException("Editor not defined");
		}
		node.getNodeEditor().insertChildNodeAt(index, value.getValue());
	}

	/**
	 * @see net.sf.jame.core.scripting.JSNode#insertChildNodeBefore(int, net.sf.jame.core.tree.NodeValue)
	 */
	public void insertChildNodeBefore(final int index, final JSNodeValue value) throws JSException {
		if (node.getNodeEditor() == null) {
			throw new JSException("Editor not defined");
		}
		node.getNodeEditor().insertChildNodeBefore(index, value.getValue());
	}

	/**
	 * @see net.sf.jame.core.scripting.JSNode#moveDownChildNode(int)
	 */
	public void moveDownChildNode(final int index) throws JSException {
		if (node.getNodeEditor() == null) {
			throw new JSException("Editor not defined");
		}
		node.getNodeEditor().moveDownChildNode(index);
	}

	/**
	 * @see net.sf.jame.core.scripting.JSNode#moveUpChildNode(int)
	 */
	public void moveUpChildNode(final int index) throws JSException {
		if (node.getNodeEditor() == null) {
			throw new JSException("Editor not defined");
		}
		node.getNodeEditor().moveUpChildNode(index);
	}

	/**
	 * @see net.sf.jame.core.scripting.JSNode#removeAllChildNodes()
	 */
	public void removeAllChildNodes() throws JSException {
		if (node.getNodeEditor() == null) {
			throw new JSException("Editor not defined");
		}
		node.getNodeEditor().removeAllChildNodes();
	}

	/**
	 * @see net.sf.jame.core.scripting.JSNode#removeChildNode(int)
	 */
	public void removeChildNode(final int index) throws JSException {
		if (node.getNodeEditor() == null) {
			throw new JSException("Editor not defined");
		}
		node.getNodeEditor().removeChildNode(index);
	}

	/**
	 * @see net.sf.jame.core.scripting.JSNode#setValue(net.sf.jame.core.tree.NodeValue)
	 */
	public void setValue(final JSNodeValue value) throws JSException {
		if (node.getNodeEditor() == null) {
			throw new JSException("Editor not defined");
		}
		node.getNodeEditor().setNodeValue(value.getValue());
	}

	/**
	 * @see net.sf.jame.core.scripting.JSNode#setValueByArgs(java.lang.Object[])
	 */
	public void setValueByArgs(final Object... args) throws JSException {
		if (node.getNodeEditor() == null) {
			throw new JSException("Editor not defined");
		}
		node.getNodeEditor().setNodeValue(node.getNodeEditor().createNodeValue(getCreator(getClassId()).create(args)));
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return node.toString();
	}

	/**
	 * @see net.sf.jame.core.scripting.JSNode#getParentNode()
	 */
	public JSNode getParentNode() {
		Node tmpNode = node.getParentNode();
		if (tmpNode != null) {
			JSNode jsNode = (JSNode) tmpNode.getObject("scripting.jsNode");
			if (jsNode == null) {
				jsNode = new DefaultJSNode(tmpNode);
				tmpNode.putObject("scripting.jsNode", jsNode);
			}
			return jsNode;
		}
		return null;
	}

	/**
	 * @see net.sf.jame.core.scripting.JSNode#dump()
	 */
	public String dump() {
		return node.dump();
	}

	/**
	 * @see net.sf.jame.core.scripting.JSNode#getNodeByPath(java.lang.String)
	 */
	public JSNode getNodeByPath(final String path) {
		Node tmpNode = node.getNodeByPath(path);
		if (tmpNode != null) {
			JSNode jsNode = (JSNode) tmpNode.getObject("scripting.jsNode");
			if (jsNode == null) {
				jsNode = new DefaultJSNode(tmpNode);
				tmpNode.putObject("scripting.jsNode", jsNode);
			}
			return jsNode;
		}
		return null;
	}
}
