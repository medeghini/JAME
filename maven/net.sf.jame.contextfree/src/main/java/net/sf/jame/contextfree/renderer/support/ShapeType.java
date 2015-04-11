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
package net.sf.jame.contextfree.renderer.support;

public class ShapeType {
	private String name;
	private int type;
	private boolean hasRules;
	public static int TYPE_NEW = -1;
	public static int TYPE_PATH = 1;
	public static int TYPE_RULE = 2;
	public static int TYPE_LOOP_START = 3;
	public static int TYPE_LOOP_END = 4;

	public ShapeType(String name) {
		this.name = name;
	}

	public final String getName() {
		return name;
	}

	public final int getType() {
		return type;
	}

	public final boolean hasRules() {
		return hasRules;
	}

	public void setHasRules(boolean hasRules) {
		this.hasRules = hasRules;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "EShapeType [name=" + name + ", type=" + type + ", hasRules=" + hasRules + "]";
	}
}
