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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import net.sf.jame.service.DataTableException;
import net.sf.jame.service.Session;
import net.sf.jame.service.SessionException;
import net.sf.jame.service.io.ChunkedRandomAccessFile;

/**
 * @author Andrea Medeghini
 */
public class RenderClipService {
	private final RenderClipFilesystemCache fileCache;
	private final RenderClipDataTable dataTable;

	/**
	 * @param workdir
	 * @throws IOException
	 */
	public RenderClipService(final File workdir) throws IOException {
		fileCache = new RenderClipFilesystemCache(new File(workdir, "clip"));
		dataTable = new RenderClipDataTable();
	}

	/**
	 * @param session
	 * @param clip
	 * @throws SessionException
	 * @throws DataTableException
	 */
	public void create(final Session session, final RenderClipDataRow clip) throws SessionException, DataTableException {
		dataTable.create(session, clip);
	}

	/**
	 * @param session
	 * @param clip
	 * @throws SessionException
	 * @throws DataTableException
	 */
	public void delete(final Session session, final RenderClipDataRow clip) throws SessionException, DataTableException {
		dataTable.delete(session, clip);
		try {
			fileCache.delete(clip.getClipId());
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param session
	 * @param clip
	 * @throws SessionException
	 * @throws DataTableException
	 */
	public void save(final Session session, final RenderClipDataRow clip) throws SessionException, DataTableException {
		dataTable.save(session, clip);
	}

	/**
	 * @param session
	 * @param id
	 * @return
	 * @throws SessionException
	 * @throws DataTableException
	 */
	public boolean validate(final Session session, final int id) throws SessionException, DataTableException {
		final RenderClipDataRow clip = dataTable.load(session, id);
		return fileCache.exists(clip.getClipId());
	}

	/**
	 * @param session
	 * @return
	 * @throws SessionException
	 * @throws DataTableException
	 */
	public List<RenderClipDataRow> loadAll(final Session session) throws SessionException, DataTableException {
		return dataTable.loadAll(session);
	}

	/**
	 * @param session
	 * @throws SessionException
	 * @throws DataTableException
	 */
	public void deleteAll(final Session session) throws SessionException, DataTableException {
		final List<RenderClipDataRow> clips = loadAll(session);
		for (final RenderClipDataRow clip : clips) {
			delete(session, clip);
		}
	}

	/**
	 * @param session
	 * @throws SessionException
	 */
	public void cleanup(final Session session) throws SessionException {
	}

	/**
	 * @param clipId
	 * @return
	 * @throws IOException
	 */
	public InputStream getInputStream(final int clipId) throws IOException {
		return fileCache.getInputStream(clipId);
	}

	/**
	 * @param clipId
	 * @return
	 * @throws IOException
	 */
	public OutputStream getOutputStream(final int clipId) throws IOException {
		return fileCache.getOutputStream(clipId);
	}

	/**
	 * @param clipId
	 * @return
	 * @throws IOException
	 */
	public ChunkedRandomAccessFile getRandomAccessFile(final int clipId) throws IOException {
		return fileCache.getRandomAccessFile(clipId);
	}

	/**
	 * @param session
	 * @param clipId
	 * @return
	 * @throws SessionException
	 * @throws DataTableException
	 */
	public RenderClipDataRow getClip(final Session session, final int clipId) throws SessionException, DataTableException {
		return dataTable.load(session, clipId);
	}

	/**
	 * @param session
	 * @param clip
	 * @throws SessionException
	 * @throws DataTableException
	 */
	public void saveStatus(final Session session, final RenderClipDataRow clip) throws SessionException, DataTableException {
		dataTable.saveStatus(session, clip);
	}

	/**
	 * @param session
	 * @param clip
	 * @throws SessionException
	 * @throws DataTableException
	 * @throws IOException
	 */
	public void clean(final Session session, final RenderClipDataRow clip) throws IOException {
		fileCache.delete(clip.getClipId());
	}

	/**
	 * @param session
	 * @throws DataTableException
	 * @throws SessionException
	 */
	public void init(final Session session) throws DataTableException, SessionException {
		dataTable.init(session);
		cleanup(session);
	}
}
