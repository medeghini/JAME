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
package net.sf.jame.test.core;

import net.sf.jame.core.common.ConfigurableExtensionReferenceElement;
import net.sf.jame.core.common.ConfigurableExtensionReferenceElementXMLExporter;
import net.sf.jame.core.common.ConfigurableExtensionReferenceElementXMLImporter;
import net.sf.jame.core.config.ConfigContext;
import net.sf.jame.core.config.DefaultConfigContext;
import net.sf.jame.core.extension.ConfigurableExtensionReference;
import net.sf.jame.core.extension.ExtensionConfig;
import net.sf.jame.core.tree.*;
import net.sf.jame.core.xml.XML;
import net.sf.jame.core.xml.XMLNodeBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * @author Andrea Medeghini
 */
public abstract class AbstractConfigurableExtensionConfigElementTest<V extends ExtensionConfig> extends AbstractTest {
	private ConfigContext context;
	private ConfigurableExtensionReferenceElement<V> configElement;

	@Before
	public void setup() {
		context = new DefaultConfigContext();
		configElement = createConfigElement(getFirstReference());
		configElement.setContext(context);
	}

	protected abstract ConfigurableExtensionReference<V> getFirstReference();

	protected abstract ConfigurableExtensionReference<V> getSecondReference();

	protected abstract ConfigurableExtensionReferenceElement<V> createConfigElement(ConfigurableExtensionReference<V> defaultValue);

	protected abstract Node createElementNode();

	protected abstract ConfigurableExtensionReferenceElementXMLImporter<V> createXMLImporter();

	protected abstract ConfigurableExtensionReferenceElementXMLExporter<V> createXMLExporter();

	protected ConfigurableExtensionReferenceElement<V> getConfigElement() {
		return configElement;
	}

	protected ConfigContext getContext() {
		return context;
	}

	protected void testSetReference() {
		Assert.assertEquals(getFirstReference(), configElement.getReference());
		configElement.setReference(getSecondReference());
		Assert.assertEquals(getSecondReference(), configElement.getReference());
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
		Assert.assertEquals(getFirstReference(), node.getNodeValue().getValue());
		configElement.setReference(getSecondReference());
		Assert.assertEquals(getSecondReference(), node.getNodeValue().getValue());
		node.getNodeEditor().setNodeValue(node.getNodeEditor().createNodeValue(getFirstReference()));
		Assert.assertEquals(getSecondReference(), configElement.getReference());
		node.accept();
		Assert.assertEquals(getFirstReference(), configElement.getReference());
		final List<NodeAction> actions = session.getActions();
		Assert.assertEquals(2, actions.size());
		NodeAction action = actions.get(0);
		Assert.assertEquals(new NodePath(new Integer[] { 1, 0 }), action.getActionTarget());
		Assert.assertEquals(getSecondReference(), action.getActionParams()[0]);
		Assert.assertEquals(getFirstReference(), action.getActionParams()[1]);
		action = actions.get(1);
		Assert.assertEquals(new NodePath(new Integer[] { 1, 0 }), action.getActionTarget());
		Assert.assertEquals(getFirstReference(), action.getActionParams()[0]);
		Assert.assertEquals(getSecondReference(), action.getActionParams()[1]);
	}

	protected void testClone() {
		configElement.setReference(getSecondReference());
		final ConfigurableExtensionReferenceElement<V> clonedConfigElement = configElement.clone();
		Assert.assertNotSame(clonedConfigElement, configElement);
		Assert.assertEquals(clonedConfigElement, configElement);
	}

	@SuppressWarnings("unchecked")
	protected void testSerialization() {
		configElement.setReference(getSecondReference());
		try {
			final ByteArrayOutputStream os = new ByteArrayOutputStream();
			final ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(configElement);
			oos.close();
			final ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
			final ObjectInputStream ois = new ObjectInputStream(is);
			final ConfigurableExtensionReferenceElement<V> deserializedConfigElement = (ConfigurableExtensionReferenceElement<V>) ois.readObject();
			ois.close();
			Assert.assertNotSame(deserializedConfigElement, configElement);
			Assert.assertEquals(deserializedConfigElement, configElement);
		}
		catch (final Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected void testXML() {
		final ConfigurableExtensionReferenceElementXMLExporter<V> exporter = createXMLExporter();
		final ConfigurableExtensionReferenceElementXMLImporter<V> importer = createXMLImporter();
		configElement.setReference(getSecondReference());
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
			final ConfigurableExtensionReferenceElement<V> importedConfigElement = importer.importFromElement(importedDoc.getDocumentElement());
			Assert.assertNotSame(importedConfigElement, configElement);
			Assert.assertEquals(importedConfigElement, configElement);
		}
		catch (final Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}
