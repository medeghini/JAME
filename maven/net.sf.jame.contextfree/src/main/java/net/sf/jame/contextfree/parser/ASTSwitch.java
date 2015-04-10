package net.sf.jame.contextfree.parser;

import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.Token;

class ASTSwitch extends ASTReplacement {
	private Map<Integer, ASTRepContainer> caseStatements = new HashMap<Integer, ASTRepContainer>();
	private ASTRepContainer elseBody = new ASTRepContainer();
	private ASTExpression switchExp;
	
	public ASTSwitch(ASTExpression caseVal, Token location) {
		super(null, ERepElemType.empty, location);
		this.switchExp = caseVal;
	}

	public ASTRepContainer getElseBody() {
		return elseBody;
	}

	public Map<Integer, ASTRepContainer> getCaseStatements() {
		return caseStatements;
	}

	public ASTExpression getSwitchExp() {
		return switchExp;
	}
	
	@Override
	public void compile(ECompilePhase ph) {
		super.compile(ph);
		if (switchExp != null) {
			switchExp.compile(ph);
		}
		for (ASTRepContainer caseVal : caseStatements.values()) {
			caseVal.compile(ph, null, null);
		}
		elseBody.compile(ph, null, null);
		
		switch (ph) {
			case TypeCheck:
				if (switchExp.getType() != EExpType.NumericType || switchExp.evaluate(null, 0) != 1) {
					error("Switch selector must be a numeric scalar");
				}
				break;
	
			case Simplify:
				if (switchExp != null) {
					switchExp.simplify();
				}
				break;
	
			default:
				break;
		}
	}

	@Override
	public void traverse(Shape parent, boolean tr, RTI rti) {
		double[] caveValue = new double[1];
		if (switchExp.evaluate(caveValue, 1) != 1) {
			error("Error evaluating switch selector");
			return;
		}
		
		ASTRepContainer caseVal = caseStatements.get((int)Math.floor(caveValue[0]));
		if (caseVal != null) {
			caseVal.traverse(parent, tr, rti, false);
		} else {
			elseBody.traverse(parent, tr, rti, false);
		}
	}
	
	public void unify() {
		if (elseBody.getPathOp() != getPathOp()) {
			setPathOp(EPathOp.UNKNOWN);
		}
		for (ASTRepContainer caseVal : caseStatements.values()) {
			if (caseVal.getPathOp() != getPathOp()) {
				setPathOp(EPathOp.UNKNOWN);
			}
		}
	}
}
