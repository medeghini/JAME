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
package net.sf.jame.contextfree.test;

import java.io.File;

import net.sf.jame.contextfree.ContextFreeConfig;
import net.sf.jame.contextfree.ContextFreeConfigNodeBuilder;
import net.sf.jame.contextfree.parser.ContextFreeParser;
import net.sf.jame.core.tree.RootNode;
import net.sf.jame.core.tree.Tree;

import org.junit.Test;

public class TestContextFreeParser {
	@Test
	public void parse() {
//		String text = "" +
//			"startshape Foo\n" +
//			"include \"stuff.cfdg\"\n" +
//			"background { b -1 a (sin( 4 * tan(2)) * 5) }\n" +
//			"tile { s 3 4 }\n" +
//			"size { s 3 4 x 1 y 2}\n" +
//			"rule Foo 0.1 {\n" +
//			"SQUARE { x ((sin(45 * sin(5) )) + 6 * (6 / tan (6) - 9)) }\n" +
//			"6 * { |a 1 s 1 3 sin(2) } SQUARE [ x tan(6) a 6| |sat 4 ]\n" +
//			"2 * { s 1 } { CIRCLE [ a 6 ]\n TRI [ sat 4 ] }\n" +
//			"}\n" +
//			"";
//		String text = "" +
//			"startshape Foo\n" +
//			"background { b -1 a (sin( 4 * tan(2)) * 5) }\n" +
//			"tile { s 3 4 }\n" +
//			"size { s 3 4 x 1 y 2}\n" +
//			"rule Foo 0.1 {\n" +
//			"SQUARE { x ((sin(45 * sin(5) )) + 6 * (6 / tan (6) - 9)) }\n" +
//			"6 * { |a 1 s 1 3 sin(2) } SQUARE [ x tan(6) a 6| |sat 4 ]\n" +
//			"}\n" +
//			"";
		String text = "" +
			"startshape Foo\n" +
			"background { b -1 }\n" +
			"tile { s 30 30 }\n" +
			"size { s 30 30 }\n" +
			"rule Foo 0.1 {\n" +
			"SQUARE { x 0 }\n" +
			"}\n" +
			"";
		try {
			System.out.println(text);
			ContextFreeParser parser = new ContextFreeParser();
			ContextFreeConfig config = parser.parseConfig(new File(System.getProperty("user.home")), text);
			RootNode rootNode = new RootNode("contextfree");
			ContextFreeConfigNodeBuilder nodeBuilder = new ContextFreeConfigNodeBuilder(config);
			nodeBuilder.createNodes(rootNode);
			Tree tree = new Tree(rootNode);
			System.out.println(tree);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
