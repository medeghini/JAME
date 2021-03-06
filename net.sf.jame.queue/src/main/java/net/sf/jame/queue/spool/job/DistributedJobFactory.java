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
package net.sf.jame.queue.spool.job;

import java.io.File;

import net.sf.jame.core.util.Files;
import net.sf.jame.core.util.Worker;
import net.sf.jame.queue.spool.JobFactory;
import net.sf.jame.queue.spool.JobListener;

/**
 * @author Andrea Medeghini
 */
public class DistributedJobFactory implements JobFactory<DistributedJob> {
	private final File tmpDir;
	private final Worker worker;

	/**
	 * @param tmpDir
	 */
	public DistributedJobFactory(final File tmpDir, final Worker worker) {
		this.worker = worker;
		this.tmpDir = tmpDir;
		tmpDir.mkdirs();
		Files.deleteFiles(tmpDir);
	}

	/**
	 * @see net.sf.jame.queue.spool.JobFactory#createJob(java.lang.String, net.sf.jame.queue.spool.JobListener)
	 */
	public DistributedJob createJob(final String jobId, final JobListener listener) {
		return new DistributedJob(tmpDir, worker, jobId, listener);
	}
}
