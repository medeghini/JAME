/*
 * JAME 6.2
 * http://jame.sourceforge.net
 *
 * Copyright 2001, 2015 Andrea Medeghini
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
package net.sf.jame.core.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import junit.framework.Assert;
import net.sf.jame.core.config.ConfigContext;
import net.sf.jame.core.config.DefaultConfigContext;
import net.sf.jame.core.config.ValueConfigElement;
import net.sf.jame.core.config.ValueConfigElementXMLExporter;
import net.sf.jame.core.config.ValueConfigElementXMLImporter;
import net.sf.jame.core.tree.DefaultNode;
import net.sf.jame.core.tree.DefaultNodeSession;
import net.sf.jame.core.tree.Node;
import net.sf.jame.core.tree.NodeAction;
import net.sf.jame.core.tree.NodeEditor;
import net.sf.jame.core.tree.NodePath;
import net.sf.jame.core.tree.NodeSession;
import net.sf.jame.core.tree.RootNode;
import net.sf.jame.core.tree.Tree;
import net.sf.jame.core.xml.XML;
import net.sf.jame.core.xml.XMLNodeBuilder;

import org.junit.Before;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Andrea Medeghini
 */
public abstract class AbsractValueElementTest<V extends Serializable, T extends ValueConfigElement<V>> extends AbstractTest {
	private ConfigContext context;
	private T configElement;

	@Before
	public void setup() {
		context = new DefaultConfigContext();
		configElement = createConfigElement(getFirstValue());
		configElement.setContext(context);
	}

	protected abstract V getFirstValue();

	protected abstract V getSecondValue();

	protected abstract T createConfigElement(V defaultValue);

	protected abstract Node createElementNode();

	protected abstract ValueConfigElementXMLImporter<V, T> createXMLImporter();

	protected abstract ValueConfigElementXMLExporter<V, T> createXMLExporter();

	protected T getConfigElement() {
		return configElement;
	}

	protected ConfigContext getContext() {
		return context;
	}

	protected void testSetValue() {
		Assert.assertEquals(getFirstValue(), configElement.getValue());
		configElement.setValue(getSecondValue());
		Assert.assertEquals(getSecondValue(), configElement.getValue());
	}

	protected void testNode() {
		final Tree tree = new Tree(new RootNode("test", "test"));
		final NodeSession session = new DefaultNodeSession("test");
		final ConfigContext context = configElement.getContext();
		tree.getRootNode().setSession(session);
		tree.getRootNode().setContext(context);
		DefaultNode parentNode = new DefaultNode("element") {
			@Override
			protected NodeEditor createNodeEditor() {
				return null;
			}
		};
		tree.getRootNode().appendChildNode(parentNode);
		parentNode = new DefaultNode("element") {
			@Override
			protected NodeEditor createNodeEditor() {
				return null;
			}
		};
		tree.getRootNode().appendChildNode(parentNode);
		final Node node = createElementNode();
		parentNode.appendChildNode(node);
		Assert.assertEquals(getFirstValue(), node.getNodeValue().getValue());
		configElement.setValue(getSecondValue());
		Assert.assertEquals(getSecondValue(), node.getNodeValue().getValue());
		node.getNodeEditor().setNodeValue(node.getNodeEditor().createNodeValue(getFirstValue()));
		Assert.assertEquals(getSecondValue(), configElement.getValue());
		node.accept();
		Assert.assertEquals(getFirstValue(), configElement.getValue());
		final List<NodeAction> actions = session.getActions();
		Assert.assertEquals(2, actions.size());
		NodeAction action = actions.get(0);
		Assert.assertEquals(new NodePath(new Integer[] { 1, 0 }), action.getActionTarget());
		Assert.assertEquals(getSecondValue(), action.getActionParams()[0]);
		Assert.assertEquals(getFirstValue(), action.getActionParams()[1]);
		action = actions.get(1);
		Assert.assertEquals(new NodePath(new Integer[] { 1, 0 }), action.getActionTarget());
		Assert.assertEquals(getFirstValue(), action.getActionParams()[0]);
		Assert.assertEquals(getSecondValue(), action.getActionParams()[1]);
	}

	@SuppressWarnings("unchecked")
	protected void testClone() {
		configElement.setValue(getSecondValue());
		final T clonedConfigElement = (T) configElement.clone();
		Assert.assertNotSame(clonedConfigElement, configElement);
		Assert.assertEquals(clonedConfigElement, configElement);
	}

	@SuppressWarnings("unchecked")
	protected void testSerialization() {
		configElement.setValue(getSecondValue());
		try {
			final ByteArrayOutputStream os = new ByteArrayOutputStream();
			final ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(configElement);
			oos.close();
			final ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
			final ObjectInputStream ois = new ObjectInputStream(is);
			final T deserializedConfigElement = (T) ois.readObject();
			ois.close();
			Assert.assertNotSame(deserializedConfigElement, configElement);
			Assert.assertEquals(deserializedConfigElement, configElement);
		}
		catch (final Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected void testXML() {
		final ValueConfigElementXMLExporter<V, T> exporter = createXMLExporter();
		final ValueConfigElementXMLImporter<V, T> importer = createXMLImporter();
		configElement.setValue(getSecondValue());
		try {
			final Document exportedDoc = XML.createDocument();
			final XMLNodeBuilder builder = XML.createDefaultXMLNodeBuilder(exportedDoc);
			final Element element = exporter.exportToElement(configElement, builder);
			exportedDoc.appendChild(element);
			final ByteArrayOutputStream os = new ByteArrayOutputStream();
			XML.saveDocument(os, "test.xml", exportedDoc);
			os.close();
			final ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
			final Document importedDoc = XML.loadDocument(is, "test.xml");
			is.close();
			final T importedConfigElement = importer.importFromElement(importedDoc.getDocumentElement());
			Assert.assertNotSame(importedConfigElement, configElement);
			Assert.assertEquals(importedConfigElement, configElement);
		}
		catch (final Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected void testActions() {
	}
}
