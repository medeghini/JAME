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
package net.sf.jame.queue.encoder;

import java.io.IOException;

import net.sf.jame.queue.io.ChunkedRandomAccessFile;

/**
 * @author Andrea Medeghini
 */
public class RAFEncoderContext implements EncoderContext {
	private final ChunkedRandomAccessFile raf;
	private final int imageWidth;
	private final int imageHeight;
	private final int frameRate;
	private final int frameCount;

	public RAFEncoderContext(final ChunkedRandomAccessFile raf, final int imageWidth, final int imageHeight, final int frameRate, final int frameCount) throws IOException {
		this.raf = raf;
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
		this.frameRate = frameRate;
		this.frameCount = frameCount;
	}

	/**
	 * @see net.sf.jame.queue.encoder.EncoderContext#getTileAsByteArray(int, int, int, int, int, int)
	 */
	public byte[] getTileAsByteArray(final int n, final int x, final int y, final int w, final int h, final int s) throws IOException {
		final byte[] data = new byte[w * h * s];
		final byte[] row = new byte[w * 4];
		long pos = (n * getImageWidth() * getImageHeight() + y * getImageWidth() + x) * 4;
		int t = 0;
		for (int k = 0; k < h; k++) {
			raf.seek(pos);
			raf.readFully(row);
			for (int j = 0, i = 0; i < row.length; j += s, i += 4) {
				if (s == 3) {
					data[t + j + 0] = row[i + 0];
					data[t + j + 1] = row[i + 1];
					data[t + j + 2] = row[i + 2];
				}
				else if (s == 4) {
					data[t + j + 0] = row[i + 0];
					data[t + j + 1] = row[i + 1];
					data[t + j + 2] = row[i + 2];
					data[t + j + 3] = row[i + 3];
				}
			}
			t += w * s;
			pos += getImageWidth() * 4;
		}
		return data;
	}

	/**
	 * @see net.sf.jame.queue.encoder.EncoderContext#getTileAsByteArray(int, int, int, int, int, int)
	 */
	public int[] getTileAsIntArray(final int n, final int x, final int y, final int w, final int h, final int s) throws IOException {
		final int[] data = new int[w * h * s];
		final byte[] row = new byte[w * 4];
		long pos = (n * getImageWidth() * getImageHeight() + y * getImageWidth() + x) * 4;
		int t = 0;
		for (int k = 0; k < h; k++) {
			raf.seek(pos);
			raf.readFully(row);
			for (int j = 0, i = 0; i < row.length; j += s, i += 4) {
				if (s == 3) {
					data[t + j + 0] = row[i + 0];
					data[t + j + 1] = row[i + 1];
					data[t + j + 2] = row[i + 2];
				}
				else if (s == 4) {
					data[t + j + 0] = row[i + 0];
					data[t + j + 1] = row[i + 1];
					data[t + j + 2] = row[i + 2];
					data[t + j + 3] = row[i + 3];
				}
			}
			t += w * s;
			pos += getImageWidth() * 4;
		}
		return data;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public int getFrameRate() {
		return frameRate;
	}

	public int getFrameCount() {
		return frameCount;
	}
}
