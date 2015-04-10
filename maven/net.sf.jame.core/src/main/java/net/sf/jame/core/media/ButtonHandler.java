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
package net.sf.jame.core.media;

public abstract class ButtonHandler extends Handler {
	private Controller engine;
	private Movie parent;
	private Layer layer;
	private Sequence sequence;

	final void build(final Controller engine, final Movie parent, final Layer layer, final Sequence sequence) {
		this.engine = engine;
		this.parent = parent;
		this.layer = layer;
		this.sequence = sequence;
	}

	protected void init() {
	}

	protected void kill() {
	}

	protected void reset() {
	}

	protected Layer getLayer() {
		return layer;
	}

	protected Sequence getSequence() {
		return sequence;
	}

	public final Movie getParent() {
		return parent;
	}

	public final Controller getController() {
		return engine;
	}

	final void process(final EngineButtonEvent e) {
		switch (e.event) {
			case EngineButtonEvent.RELEASED: {
				released(e);
				break;
			}
			case EngineButtonEvent.PRESSED: {
				pressed(e);
				break;
			}
			case EngineButtonEvent.ENTERED: {
				entered(e);
				break;
			}
			case EngineButtonEvent.EXITED: {
				exited(e);
				break;
			}
			default:
				break;
		}
	}

	protected abstract void released(EngineButtonEvent e);

	protected abstract void pressed(EngineButtonEvent e);

	protected abstract void entered(EngineButtonEvent e);

	protected abstract void exited(EngineButtonEvent e);

	@Override
	public abstract Object clone() throws CloneNotSupportedException;
}
