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
package net.sf.jame.core.media;

public class TintEffect extends Effect {
	private int frames;
	private int frame;
	private final int color;
	private int a = 0;
	private int r = 0;
	private int g = 0;
	private int b = 0;
	private final float value1;
	private final float value2;
	private float value;
	private float delta_value = 0.0f;

	public TintEffect(final String name, final int ARGB, float value1, float value2) {
		super(name);
		value1 = (value1 < 0.0f) ? 0.0f : ((value1 > 1.0f) ? 1.0f : value1);
		value2 = (value2 < 0.0f) ? 0.0f : ((value2 > 1.0f) ? 1.0f : value2);
		this.value1 = value1;
		this.value2 = value2;
		a = (ARGB >> 24) & 0xFF;
		r = (ARGB >> 16) & 0xFF;
		g = (ARGB >> 8) & 0xFF;
		b = ARGB & 0xFF;
		color = ARGB;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return new TintEffect(name + "_copy", color, value1, value2);
	}

	@Override
	final void init(final int frames) {
		delta_value = (value2 - value1) / (frames - 1);
		value = value1;
		this.frames = frames;
	}

	@Override
	final void reset() {
		frame = 0;
		value = value1;
	}

	@Override
	final void setFrame(final int frame) {
		this.frame = frame % frames;
		this.setValue(value1 + (delta_value * this.frame));
	}

	@Override
	final void nextFrame() {
		frame = (frame + 1) % frames;
		this.setValue(value + delta_value);
	}

	@Override
	final void prevFrame() {
		frame = (frame - 1) % frames;
		this.setValue(value - delta_value);
	}

	final void setValue(float value) {
		value = (value < 0.0f) ? 0.0f : ((value > 1.0f) ? 1.0f : value);
		this.value = value;
		this.setValue(1.0f - value, (a * value) / 256f, 1.0f - value, (r * value) / 256f, 1.0f - value, (g * value) / 256f, 1.0f - value, (b * value) / 256f);
	}
}
