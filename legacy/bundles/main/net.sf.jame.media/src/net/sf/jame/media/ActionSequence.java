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
package net.sf.jame.media;

public final class ActionSequence extends Sequence {
	private final AbstractAction object;
	private int frames = 0;
	private int frame = 0;

	public ActionSequence(final AbstractAction object, final int frames) {
		this.object = object;
		this.frames = frames;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return new ActionSequence((AbstractAction) object.clone(), frames);
	}

	@Override
	public AbstractObject getObject() {
		return object;
	}

	@Override
	public int getFrames() {
		return frames;
	}

	@Override
	public int getFrame() {
		return frame;
	}

	@Override
	public boolean isFirstFrame() {
		return (frame == 0);
	}

	@Override
	public boolean isLastFrame() {
		return (frame == (frames - 1));
	}

	@Override
	void build(final Controller controller, final Movie parent, final Layer layer) {
		if (object != null) {
			object.build(controller, parent, layer, this);
		}
	}

	@Override
	void init() {
		if (object != null) {
			object.init();
		}
	}

	@Override
	void kill() {
		if (object != null) {
			object.kill();
		}
	}

	@Override
	void reset() {
		if (object != null) {
			object.reset();
		}
	}

	@Override
	void setFrame(final int frame) {
		this.frame = frame;
		if (isFirstFrame()) {
			object.execute();
		}
	}

	@Override
	void nextFrame() {
		frame = frame + 1;
		if (isFirstFrame()) {
			object.execute();
		}
	}

	@Override
	void prevFrame() {
		frame = frame - 1;
		if (isFirstFrame()) {
			object.execute();
		}
	}
}
