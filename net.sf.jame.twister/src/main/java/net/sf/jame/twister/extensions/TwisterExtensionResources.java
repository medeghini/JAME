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
package net.sf.jame.twister.extensions;

import java.util.ResourceBundle;

import net.sf.jame.core.util.Resources;

/**
 * @author Andrea Medeghini
 */
public class TwisterExtensionResources extends Resources {
	private static final TwisterExtensionResources instance = new TwisterExtensionResources();

	private TwisterExtensionResources() {
		super(ResourceBundle.getBundle("twister_extension_resources"));
	}

	/**
	 * Returns the instance.
	 * 
	 * @return the instance.
	 */
	public static TwisterExtensionResources getInstance() {
		return TwisterExtensionResources.instance;
	}
}
