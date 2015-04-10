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
package net.sf.jame.queue.extensions.encoder;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.freeimage4java.FIBITMAP;
import net.sf.freeimage4java.FREE_IMAGE_FORMAT;
import net.sf.freeimage4java.FreeImage4Java;
import net.sf.freeimage4java.RGBQUAD;
import net.sf.jame.queue.encoder.EncoderContext;
import net.sf.jame.queue.encoder.EncoderException;
import net.sf.jame.queue.encoder.EncoderHook;
import net.sf.jame.queue.encoder.extension.AbstractEncoderExtensionRuntime;
import net.sf.jame.queue.encoder.extension.EncoderExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public abstract class FreeImageEncoderRuntime<T extends EncoderExtensionConfig> extends AbstractEncoderExtensionRuntime<T> {
	private static final Logger logger = Logger.getLogger(FreeImageEncoderRuntime.class.getName());
	private EncoderHook hook;
	static {
		FreeImage4Java.FreeImage_Initialise(FreeImage4Java.TRUE);
	}

	/**
	 * @see net.sf.jame.queue.encoder.extension.EncoderExtensionRuntime#setInterruptHook(net.sf.jame.queue.encoder.EncoderHook)
	 */
	@Override
	public void setInterruptHook(final EncoderHook hook) {
		this.hook = hook;
	}

	/**
	 * @see net.sf.jame.queue.encoder.extension.EncoderExtensionRuntime#encode(net.sf.jame.queue.encoder.EncoderContext, java.io.File)
	 */
	@Override
	public void encode(final EncoderContext context, final File path) throws EncoderException {
		fireStateChanged(0);
		if (isImageSupported()) {
			RGBQUAD value = null;
			FIBITMAP dib = null;
			try {
				if (FreeImageEncoderRuntime.logger.isLoggable(Level.FINE)) {
					FreeImageEncoderRuntime.logger.fine("Starting encoding...");
				}
				long time = System.currentTimeMillis();
				int channels = isAlphaSupported() ? 4 : 3;
				value = new RGBQUAD();
				dib = FreeImage4Java.FreeImage_Allocate(context.getImageWidth(), context.getImageHeight(), channels * 8, 0x00FF0000, 0x0000FF00, 0x000000FF);
				final byte[] data = context.getTileAsByteArray(0, 0, 0, context.getImageWidth(), context.getImageHeight(), channels);
				for (int y = 0; y < context.getImageHeight(); y++) {
					int j = (context.getImageHeight() - y - 1) * context.getImageWidth();
					for (int x = 0; x < context.getImageWidth(); x++) {
						int i = (j + x) * channels;
						value.setRgbRed(data[i + 0]);
						value.setRgbGreen(data[i + 1]);
						value.setRgbBlue(data[i + 2]);
						if (isAlphaSupported()) {
							value.setRgbReserved(data[i + 3]);
						}
						else {
							value.setRgbReserved((short) 255);
						}
						FreeImage4Java.FreeImage_SetPixelColor(dib, x, y, value);
						if (hook.isInterrupted()) {
							break;
						}
					}
				}
				if (!hook.isInterrupted()) {
					FreeImage4Java.FreeImage_Save(getFormat(), dib, path.getAbsolutePath(), 0);
					time = System.currentTimeMillis() - time;
					if (FreeImageEncoderRuntime.logger.isLoggable(Level.INFO)) {
						FreeImageEncoderRuntime.logger.info("Profile exported: elapsed time " + String.format("%3.2f", time / 1000.0d) + "s");
					}
					fireStateChanged(100);
				}
			}
			catch (final Exception e) {
				throw new EncoderException(e);
			}
			finally {
				if (dib != null) {
					FreeImage4Java.FreeImage_Unload(dib);
					dib.delete();
				}
				if (value != null) {
					value.delete();
				}
			}
		}
		else {
			throw new EncoderException("Can't encode the image");
		}
	}

	protected abstract FREE_IMAGE_FORMAT getFormat();
}
