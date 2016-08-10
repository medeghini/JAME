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
