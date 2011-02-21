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
package net.sf.jame.service.clip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.sf.jame.core.util.ZIPFilenameFilter;
import net.sf.jame.service.DefaultFilesystemCache;
import net.sf.jame.service.FilesystemCache;
import net.sf.jame.service.io.ChunkedRandomAccessFile;

/**
 * @author Andrea Medeghini
 */
public class RenderClipFilesystemCache implements FilesystemCache {
	protected final File workdir;

	/**
	 * @param workdir
	 * @throws IOException
	 */
	public RenderClipFilesystemCache(final File workdir) throws IOException {
		this.workdir = workdir;
		workdir.mkdirs();
	}

	/**
	 * @return
	 */
	public File[] list() {
		return workdir.listFiles(createFilenameFilter());
	}

	/**
	 * @see net.sf.jame.service.FilesystemCache#create(int)
	 */
	public void create(final int id) throws IOException {
		OutputStream os = null;
		try {
			os = new FileOutputStream(getFile(id));
		}
		finally {
			if (os != null) {
				try {
					os.close();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @see net.sf.jame.service.FilesystemCache#delete(int)
	 */
	public void delete(final int id) throws IOException {
		final File file = getFile(id);
		if (file.exists()) {
			file.delete();
			if (file.exists()) {
				throw new IOException("Can't delete the file: " + file.getName());
			}
		}
	}

	/**
	 * @see net.sf.jame.service.FilesystemCache#deleteAll()
	 */
	public void deleteAll() {
		final File[] files = workdir.listFiles();
		if (files != null) {
			for (final File file : files) {
				if (file.isDirectory()) {
					DefaultFilesystemCache.deleteDir(file);
				}
				else if (file.isFile()) {
					file.delete();
				}
			}
		}
	}

	/**
	 * @see net.sf.jame.service.FilesystemCache#exists(int)
	 */
	public boolean exists(final int id) {
		final File dir = getFile(id);
		return dir.exists();
	}

	/**
	 * @param id
	 * @return
	 */
	protected File getFile(final int id) {
		return new File(workdir, String.valueOf(id) + ".zip");
	}

	/**
	 * @return
	 */
	protected FilenameFilter createFilenameFilter() {
		return new ZIPFilenameFilter();
	}

	/**
	 * @see net.sf.jame.service.FilesystemCache#getInputStream(int)
	 */
	public InputStream getInputStream(final int id) throws IOException {
		return new FileInputStream(getFile(id));
	}

	/**
	 * @see net.sf.jame.service.FilesystemCache#getOutputStream(int)
	 */
	public OutputStream getOutputStream(final int id) throws IOException {
		return new FileOutputStream(getFile(id));
	}

	/**
	 * @see net.sf.jame.service.FilesystemCache#getRandomAccessFile(int)
	 */
	public ChunkedRandomAccessFile getRandomAccessFile(final int id) throws IOException {
		throw new UnsupportedOperationException();
	}
}
