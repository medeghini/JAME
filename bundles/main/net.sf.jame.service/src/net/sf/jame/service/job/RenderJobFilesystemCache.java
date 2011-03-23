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
package net.sf.jame.service.job;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.sf.jame.service.DefaultFilesystemCache;
import net.sf.jame.service.io.ChunkedFileInputStream;
import net.sf.jame.service.io.ChunkedFileOutputStream;
import net.sf.jame.service.io.ChunkedRandomAccessFile;

/**
 * @author Andrea Medeghini
 */
public class RenderJobFilesystemCache extends DefaultFilesystemCache {
	private static final int CHUNK_LENGTH = 1024 * 100000;

	/**
	 * @param workdir
	 * @throws IOException
	 */
	public RenderJobFilesystemCache(final File workdir) throws IOException {
		super(workdir);
	}

	/**
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@Override
	public InputStream getInputStream(final int id) throws IOException {
		return new ChunkedFileInputStream(getDirectory(id), "", ".bin", CHUNK_LENGTH);
	}

	/**
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@Override
	public OutputStream getOutputStream(final int id) throws IOException {
		return new ChunkedFileOutputStream(getDirectory(id), "", ".bin", CHUNK_LENGTH);
	}

	/**
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@Override
	public ChunkedRandomAccessFile getRandomAccessFile(final int id) throws IOException {
		return new ChunkedRandomAccessFile(getDirectory(id), "", ".bin", CHUNK_LENGTH);
	}
}
