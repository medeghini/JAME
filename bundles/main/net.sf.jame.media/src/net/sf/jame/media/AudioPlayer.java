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
package net.sf.jame.media;

import java.util.LinkedList;

import javax.sound.sampled.SourceDataLine;

public final class AudioPlayer extends Thread {
	private byte[] data;
	private final LinkedList<byte[]> queue;
	private final SourceDataLine line;
	private boolean running;

	public AudioPlayer(final String name, final SourceDataLine line) {
		super(name);
		setDaemon(true);
		this.line = line;
		queue = new LinkedList<byte[]>();
		setPriority(Thread.MAX_PRIORITY - 1);
		running = true;
		start();
	}

	@Override
	public void run() {
		// System.out.println("player started");
		try {
			while (running) {
				synchronized (queue) {
					if (queue.size() == 0) {
						queue.wait();
					}
					data = queue.removeFirst();
				}
				line.write(data, 0, data.length);
			}
		}
		catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		// System.out.println("player stopped");
	}

	public void play(final byte[] data) {
		synchronized (queue) {
			// System.out.println("data enqueued");
			queue.addLast(data);
			queue.notify();
		}
	}

	public void clear() {
		synchronized (queue) {
			queue.clear();
		}
	}

	public void kill() {
		synchronized (this) {
			running = false;
			interrupt();
			try {
				join();
			}
			catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
}
