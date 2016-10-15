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

import java.io.Serializable;
import java.util.*;

/**
 * @author Andrea Medeghini
 */
public class NodePath implements Serializable {
	private static final long serialVersionUID = 1L;
	private final List<Integer> elementList = new LinkedList<Integer>();

	/**
	 * 
	 */
	public NodePath() {
	}

	/**
	 * @param pathElements
	 */
	public NodePath(final Integer[] pathElements) {
		for (final Integer pathElement : pathElements) {
			elementList.add(pathElement);
		}
	}

	/**
	 * @param pathElementList
	 */
	public NodePath(final List<Integer> pathElementList) {
		elementList.addAll(pathElementList);
	}

	/**
	 * @return
	 */
	public List<Integer> getPathElementList() {
		return Collections.unmodifiableList(elementList);
	}

	/**
	 * @return
	 */
	public Integer[] getPathElements() {
		return elementList.toArray(new Integer[elementList.size()]);
	}

	/**
	 * @param pathElement
	 */
	public void addPathElement(final Integer pathElement) {
		elementList.add(pathElement);
	}

	/**
	 * @see java.util.EventObject#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		final Iterator<Integer> iterator = elementList.iterator();
		while (iterator.hasNext()) {
			final Integer pathElement = iterator.next();
			builder.append(pathElement);
			if (iterator.hasNext()) {
				builder.append(",");
			}
		}
		return builder.toString();
	}

	/**
	 * @return
	 */
	public int getLastPathElement() {
		return elementList.get(elementList.size() - 1);
	}

	/**
	 * @param value
	 * @return
	 */
	public static NodePath valueOf(final String value) {
		final StringTokenizer tkn = new StringTokenizer(value, ",");
		final NodePath path = new NodePath();
		while (tkn.hasMoreTokens()) {
			path.addPathElement(Integer.valueOf(tkn.nextToken()));
		}
		return path;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (elementList.size() != ((NodePath) obj).elementList.size()) {
			return false;
		}
		for (int i = 0; i < elementList.size(); i++) {
			if (!elementList.get(i).equals(((NodePath) obj).elementList.get(i))) {
				return false;
			}
		}
		return true;
	}
}
