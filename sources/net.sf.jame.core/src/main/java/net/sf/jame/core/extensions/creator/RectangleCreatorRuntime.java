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
package net.sf.jame.core.extensions.creator;

import net.sf.jame.core.creator.extension.CreatorExtensionRuntime;
import net.sf.jame.core.scripting.JSException;
import net.sf.jame.core.util.Rectangle;

/**
 * @author Andrea Medeghini
 */
public class RectangleCreatorRuntime extends CreatorExtensionRuntime {
	/**
	 * @see net.sf.jame.core.creator.extension.CreatorExtensionRuntime#create(java.lang.Object[])
	 */
	@Override
	public Object create(final Object... args) throws JSException {
		try {
			return new Rectangle((Double) args[0], (Double) args[1], (Double) args[2], (Double) args[3]);
		}
		catch (Exception e) {
			throw new JSException("Rectangle creator requires four arguments: #1 of type Number,  #2 of type Number,  #3 of type Number,  #4 of type Number", e);
		}
	}
}
