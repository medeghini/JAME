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
package net.sf.jame.twister.extensions.frame.filter;

import net.sf.jame.core.common.ColorElement;
import net.sf.jame.core.config.ConfigElement;
import net.sf.jame.core.config.ValueConfigElement;
import net.sf.jame.core.util.Color32bit;
import net.sf.jame.twister.frame.filter.extension.FrameFilterExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public class BlackConfig extends FrameFilterExtensionConfig {
	private static final long serialVersionUID = 1L;
	private ReadonlyColorElement colorElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		colorElement = new ReadonlyColorElement(Color32bit.BLACK);
	}

	/**
	 * @return
	 */
	public ValueConfigElement<Color32bit> getColorElement() {
		return colorElement;
	}

	/**
	 * @return
	 */
	@Override
	public BlackConfig clone() {
		final BlackConfig config = new BlackConfig();
		return config;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		final BlackConfig other = (BlackConfig) obj;
		if (colorElement == null) {
			if (other.colorElement != null) {
				return false;
			}
		}
		else if (!colorElement.equals(other.colorElement)) {
			return false;
		}
		return true;
	}

	private class ReadonlyColorElement extends ColorElement {
		private static final long serialVersionUID = 1L;

		/**
		 * @param defaultValue
		 */
		public ReadonlyColorElement(final Color32bit defaultValue) {
			super(defaultValue);
		}

		/**
		 * @see net.sf.jame.core.config.ValueConfigElement#setValue(java.io.Serializable)
		 */
		@Override
		public void setValue(final Color32bit value) {
		}

		/**
		 * @see net.sf.jame.core.common.ColorElement#copyFrom(net.sf.jame.core.config.ConfigElement)
		 */
		@Override
		public void copyFrom(ConfigElement source) {
		}
	}
}
