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
package net.sf.jame.contextfree.extensions.pathReplacement;

import net.sf.jame.contextfree.pathReplacement.extension.PathReplacementExtensionDescriptor;
import net.sf.jame.contextfree.extensions.pathReplacement.StrokePathReplacementRuntime;
import net.sf.jame.contextfree.extensions.pathReplacement.StrokePathReplacementConfig;

/**
 * @author Andrea Medeghini
 */
public class StrokePathReplacementDescriptor extends PathReplacementExtensionDescriptor {
	/**
	 * Returns the extensionId.
	 * 
	 * @return the extensionId.
	 */
	public String getExtensionId() {
		return "contextfree.path.replacement.command.stroke";
	}

	/**
	 * Returns the extensionName.
	 * 
	 * @return the extensionName.
	 */
	public String getExtensionName() {
		return "Stroke Replacement";
	}

	/**
	 * Returns the extensionRuntimeClass.
	 * 
	 * @return the extensionRuntimeClass.
	 */
	public StrokePathReplacementRuntime getExtensionRuntime() {
		return new StrokePathReplacementRuntime();
	}

	/**
	 * Returns the extensionConfigClass.
	 * 
	 * @return the extensionConfigClass.
	 */
	public StrokePathReplacementConfig getExtensionConfig() {
		return new StrokePathReplacementConfig();
	}
}
