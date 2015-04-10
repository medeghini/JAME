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
package net.sf.jame.queue;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.sf.jame.core.util.FolderFilenameFilter;
import net.sf.jame.queue.io.ChunkedRandomAccessFile;

/**
 * @author Andrea Medeghini
 */
public abstract class DefaultFilesystemCache implements FilesystemCache {
	protected final File workdir;

	/**
	 * @param workdir
	 * @throws IOException
	 */
	public DefaultFilesystemCache(final File workdir) throws IOException {
		this.workdir = workdir;
		workdir.mkdirs();
	}

	/**
	 * @return
	 */
	public File getWorkdir() {
		return workdir;
	}

	/**
	 * @param dir
	 */
	public static void deleteDir(final File dir) {
		final File[] files = dir.listFiles();
		if (files != null) {
			for (final File file : files) {
				if (file.isDirectory()) {
					deleteDir(file);
				}
				if (file.isFile()) {
					file.delete();
				}
			}
		}
		dir.delete();
	}

	/**
	 * @see net.sf.jame.queue.FilesystemCache#list()
	 */
	public File[] list() {
		return workdir.listFiles(createFilenameFilter());
	}

	/**
	 * @see net.sf.jame.queue.FilesystemCache#create(int)
	 */
	public void create(final int id) throws IOException {
		final File dir = getDirectory(id);
		if (!dir.exists()) {
			dir.mkdirs();
			// if (!dir.exists()) {
			// throw new IOException("Can't create the directory: " + dir.getName());
			// }
		}
	}

	/**
	 * @see net.sf.jame.queue.FilesystemCache#delete(int)
	 */
	public void delete(final int id) throws IOException {
		final File dir = getDirectory(id);
		if (dir.exists()) {
			deleteDir(dir);
			// if (dir.exists()) {
			// throw new IOException("Can't delete the directory: " + dir.getName());
			// }
		}
	}

	/**
	 * @see net.sf.jame.queue.FilesystemCache#deleteAll()
	 */
	public void deleteAll() {
		final File[] files = workdir.listFiles();
		if (files != null) {
			for (final File file : files) {
				if (file.isDirectory()) {
					deleteDir(file);
				}
				else if (file.isFile()) {
					file.delete();
				}
			}
		}
	}

	/**
	 * @see net.sf.jame.queue.FilesystemCache#exists(int)
	 */
	public boolean exists(final int id) {
		final File dir = getDirectory(id);
		return dir.exists();
	}

	/**
	 * @param id
	 * @return
	 */
	protected File getDirectory(final int id) {
		final File dir = new File(workdir, String.valueOf(id));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}

	/**
	 * @return
	 */
	protected FilenameFilter createFilenameFilter() {
		return new FolderFilenameFilter();
	}

	/**
	 * @see net.sf.jame.queue.FilesystemCache#getInputStream(int)
	 */
	public abstract InputStream getInputStream(final int id) throws IOException;

	/**
	 * @see net.sf.jame.queue.FilesystemCache#getOutputStream(int)
	 */
	public abstract OutputStream getOutputStream(final int id) throws IOException;

	/**
	 * @see net.sf.jame.queue.FilesystemCache#getRandomAccessFile(int)
	 */
	public abstract ChunkedRandomAccessFile getRandomAccessFile(final int id) throws IOException;
}
