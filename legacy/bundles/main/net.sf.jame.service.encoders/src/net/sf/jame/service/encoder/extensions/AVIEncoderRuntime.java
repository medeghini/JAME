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
package net.sf.jame.service.encoder.extensions;


/**
 * @author Andrea Medeghini
 */
public class AVIEncoderRuntime extends FFmpegEncoderRuntime<AVIEncoderConfig> {
	/**
	 * @see net.sf.jame.service.extensions.encoder.JAIEncoderRuntime#getFormatName()
	 */
	@Override
	protected String getFormatName() {
		return "avi";
	}

	/**
	 * @see net.sf.jame.service.encoder.extension.EncoderExtensionRuntime#getImageSuffix()
	 */
	@Override
	public String getImageSuffix() {
		return null;
	}

	/**
	 * @see net.sf.jame.service.encoder.extension.EncoderExtensionRuntime#getMovieSuffix()
	 */
	@Override
	public String getMovieSuffix() {
		return ".avi";
	}

	/**
	 * @see net.sf.jame.service.encoder.extension.EncoderExtensionRuntime#isAlphaSupported()
	 */
	@Override
	public boolean isAlphaSupported() {
		return false;
	}

	/**
	 * @see net.sf.jame.service.encoder.extension.EncoderExtensionRuntime#isImageSupported()
	 */
	@Override
	public boolean isImageSupported() {
		return false;
	}

	/**
	 * @see net.sf.jame.service.encoder.extension.EncoderExtensionRuntime#isMovieSupported()
	 */
	@Override
	public boolean isMovieSupported() {
		return true;
	}
}