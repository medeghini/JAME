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
package net.sf.jame.twister.extensions.constructor;

import net.sf.jame.core.constructor.extension.ConstructorExtensionRuntime;
import net.sf.jame.core.scripting.JSException;
import net.sf.jame.core.util.DoubleVector4D;

/**
 * @author Andrea Medeghini
 */
public class DoubleVector4DConstructorRuntime extends ConstructorExtensionRuntime {
	/**
	 * @see net.sf.jame.core.constructor.extension.ConstructorExtensionRuntime#create(java.lang.Object[])
	 */
	@Override
	public Object create(final Object... args) throws JSException {
		try {
			return new DoubleVector4D(toDouble(args[0]), toDouble(args[1]), toDouble(args[2]), toDouble(args[3]));
		}
		catch (Exception e) {
			throw new JSException("DoubleVector4D constructor requires 4 arguments of type Number", e);
		}
	}
}
