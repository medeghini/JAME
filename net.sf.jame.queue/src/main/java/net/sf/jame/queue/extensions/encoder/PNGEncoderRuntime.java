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
package net.sf.jame.queue.extensions.encoder;

import com.nextbreakpoint.freeimage4java.FREE_IMAGE_FORMAT;

/**
 * @author Andrea Medeghini
 */
public class PNGEncoderRuntime extends FreeImageEncoderRuntime<PNGEncoderConfig> {
	/**
	 * @see net.sf.jame.queue.extensions.encoder.FreeImageEncoderRuntime#getFormat()
	 */
	@Override
	protected FREE_IMAGE_FORMAT getFormat() {
		return FREE_IMAGE_FORMAT.FIF_PNG;
	}

	/**
	 * @see net.sf.jame.queue.encoder.extension.EncoderExtensionRuntime#getImageSuffix()
	 */
	@Override
	public String getImageSuffix() {
		return ".png";
	}

	/**
	 * @see net.sf.jame.queue.encoder.extension.EncoderExtensionRuntime#getMovieSuffix()
	 */
	@Override
	public String getMovieSuffix() {
		return null;
	}

	/**
	 * @see net.sf.jame.queue.encoder.extension.EncoderExtensionRuntime#isAlphaSupported()
	 */
	@Override
	public boolean isAlphaSupported() {
		return true;
	}

	/**
	 * @see net.sf.jame.queue.encoder.extension.EncoderExtensionRuntime#isImageSupported()
	 */
	@Override
	public boolean isImageSupported() {
		return true;
	}

	/**
	 * @see net.sf.jame.queue.encoder.extension.EncoderExtensionRuntime#isMovieSupported()
	 */
	@Override
	public boolean isMovieSupported() {
		return false;
	}
}
