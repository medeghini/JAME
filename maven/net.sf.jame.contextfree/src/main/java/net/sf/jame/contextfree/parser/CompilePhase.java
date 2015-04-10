package net.sf.jame.contextfree.parser;

enum CompilePhase {
	TypeCheck(0), Simplify(1);
	
	private int ordinal;

	private CompilePhase(int ordinal) {
		this.ordinal = ordinal;
	}

	public int getOrdinal() {
		return ordinal;
	}
}