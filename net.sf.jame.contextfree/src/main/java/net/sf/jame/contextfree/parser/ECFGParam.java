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
package net.sf.jame.contextfree.parser;

enum ECFGParam {
	AllowOverlap(0), Alpha(1), Background(2), BorderDynamic(3), BorderFixed(4), Color(5), 
	ColorDepth(6), Frame(7), FrameTime(8), Impure(9), MaxNatural(10), MaxShapes(11), 
	MinimumSize(12), Size(13), StartShape(14), Symmetry(15), Tile(16), Time(17);
	
	private int ordinal;

	private ECFGParam(int ordinal) {
		this.ordinal = ordinal;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public String getName() {
		return "CF::" + name().toUpperCase();
	}

	public static ECFGParam paramByOrdinal(int ordinal) {
		switch (ordinal) {
			case 0:
				return AllowOverlap; 
			case 1:
				return Alpha; 
			case 2:
				return Background; 
			case 3:
				return BorderDynamic; 
			case 4:
				return BorderFixed; 
			case 5:
				return Color; 
			case 6:
				return ColorDepth; 
			case 7:
				return Frame; 
			case 8:
				return FrameTime; 
			case 9:
				return Impure; 
			case 10:
				return MaxNatural; 
			case 11:
				return MaxShapes; 
			case 12:
				return MinimumSize; 
			case 13:
				return StartShape; 
			case 14:
				return Symmetry; 
			case 15:
				return Tile; 
			case 16:
				return Time; 
		}
		return null;
	}

	public static String nameByOrdinal(int ordinal) {
		ECFGParam param = paramByOrdinal(ordinal);
		return "CF::" + param.name().toUpperCase();
	}
}
