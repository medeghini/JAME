package net.sf.jame.contextfree.parser;

enum EPathOp { 
	UNKNOWN, MOVETO, MOVEREL, LINETO, LINEREL, ARCTO, ARCREL, CURVETO, CURVEREL, CLOSEPOLY;

	public static EPathOp pathOpTypeByName(String name) {
		for (EPathOp type : EPathOp.values()) {
			if (type.name().equals(name)) {
				return type;
			}
		}
		return UNKNOWN;
	}
}
