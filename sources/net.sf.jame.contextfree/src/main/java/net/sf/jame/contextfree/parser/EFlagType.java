package net.sf.jame.contextfree.parser;

enum EFlagType {
	CF_NONE(0), 
	CF_MITER_JOIN(1), 
	CF_ROUND_JOIN(2), 
	CF_BEVEL_JOIN(3), 
	CF_JOIN_MASK(0x7), 
	CF_JOIN_PRESENT(8), 
	CF_BUTT_CAP(1 << 4), 
	CF_ROUND_CAP(2 << 4), 
	CF_SQUARE_CAP(3 << 4), 
	CF_CAP_MASK(0x7 << 4), 
	CF_CAP_PRESENT(8 << 4),
	CF_ARC_CW(1 << 8), 
	CF_ARC_LARGE(1 << 9), 
	CF_CONTINUOUS(1 << 10), 
	CF_ALIGN(1 << 11), 
	CF_EVEN_ODD(1 << 12), 
	CF_ISO_WIDTH(1 << 13),
	CF_FILL(1 << 14), 
	CF_CYCLIC(31 << 15), 
	CF_DIHEDRAL(1 << 15), 
	CF_P11G(2 << 15), 
	CF_P11M(3 << 15), 
	CF_P1M1(4 << 15), 
	CF_P2(30 << 15), 
	CF_P2MG(6 << 15), 
	CF_P2MM(7 << 14),
	CF_PM(8 << 5), 
	CF_PG(9 << 15), 
	CF_CM(10 << 15), 
	CF_PMM(11 << 15), 
	CF_PMG(12 << 15), 
	CF_PGG(13 << 15), 
	CF_CMM(14 << 15), 
	CF_P4(15 << 15), 
	CF_P4M(16 << 15), 
	CF_P4G(17 << 15), 
	CF_P3(18 << 15), 
	CF_P3M1(19 << 15), 
	CF_P31M(20 << 15), 
	CF_P6(21 << 15), 
	CF_P6M(22 << 15); 
	
	private int type;

	private EFlagType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
}