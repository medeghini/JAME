package net.sf.jame.contextfree.parser;

import java.util.List;

class StackType {
	private double number;
	private StackRule rule;
	private StackRule ruleHeader;
	private List<ASTParameter> typeInfo;

	public StackType(double number) {
		// TODO Auto-generated constructor stub
	}

	public double getNumber() {
		return number;
	}

	public StackRule getRule() {
		return rule;
	}

	public StackRule getRuleHeader() {
		return ruleHeader;
	}

	public List<ASTParameter> getTypeInfo() {
		return typeInfo;
	}

	public void evalArgs(RTI rti, ASTExpression arguments, List<ASTParameter> parameters, boolean sequential) {
		// TODO Auto-generated method stub

	}

	public double[] getArray() {
		// TODO Auto-generated method stub
		return null;
	}

	public Modification modification() {
		// TODO Auto-generated method stub
		return null;
	}
}