package net.sf.jame.contextfree.parser;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Token;


class ASTExpression {
	protected boolean isConstant;
	protected boolean isNatural;
	protected ELocality locality;
	protected EExpType type;
	protected Token location;

	public ASTExpression(Token location) {
		this(false, false, ELocality.UnknownLocal, EExpType.NoType, location);
	}

	public ASTExpression(boolean isConstant, boolean isNatural, Token location) {
		this(isConstant, isNatural, ELocality.UnknownLocal, EExpType.NoType, location);
	}
	
	public ASTExpression(boolean isConstant, boolean isNatural, EExpType type, Token location) {
		this(isConstant, isNatural, ELocality.UnknownLocal, type, location);
	}

	public ASTExpression(boolean isConstant, boolean isNatural, ELocality locality, Token location) {
		this(isConstant, isNatural, locality, EExpType.NoType, location);
	}

	public ASTExpression(boolean isConstant, boolean isNatural, ELocality locality, EExpType type, Token location) {
		this.isConstant = isConstant;
		this.isNatural = isNatural;
		this.locality = locality;
		this.type = type;
	}

	public boolean isConstant() {
		return isConstant;
	}

	public boolean isNatural() {
		return isNatural;
	}

	public void setIsNatural(boolean isNatural) {
		this.isNatural = isNatural;
	}
	
	public ELocality getLocality() {
		return locality;
	}
	
	public EExpType getType() {
		return type;
	}

	public void setType(EExpType type) {
		this.type = type;
	}
	
	public int evaluate(double[] result, int length) {
		return evaluate(result, length, null);
	}

	public int evaluate(double[] result, int length, RTI rti) {
		return 0;
	}

	public void evaluate(Modification[] result, boolean shapeDest) {
		evaluate(result, shapeDest, null);
	}

	public void evaluate(Modification[] result, boolean shapeDest, RTI rti) {
		throw new RuntimeException("Cannot convert this expression into an adjustment"); 
	}

	public StackRule evalArgs(RTI rti, StackRule parent) {
		throw new RuntimeException("Cannot convert this expression into a shape"); 
	}
	
	public void entropy(StringBuilder e) {
	}
	
	public ASTExpression simplify() {
		return this;
	}
	
	public ASTExpression getChild(int i) {
		if (i > 0) {
			error("Expression list bounds exceeded");
		}
		return this;
	}

	public int size() {
		return 1;
	}

	public ASTExpression append(ASTExpression e) {
		return null;
	}

	public ASTExpression compile(ECompilePhase ph) {
		switch (ph) {
			case TypeCheck: 
				break;
	
			case Simplify: 
				break;

			default:
				break;
		}
		return null;
	}

	// Always returns nullptr except during type check in the following cases:
	// * An ASTvariable bound to a constant returns a copy of the constant
	// * An ASTvariable bound to a rule spec returns an ASTruleSpec that
	//   acts as a stack variable
	// * A shape spec that was parsed as an ASTuserFunc because of grammar
	//   ambiguity will return the correct ASTruleSpec
	//
	// It is safe to ignore the return value if you can guarantee that none
	// of these conditions is possible. Otherwise you must replace the object
	// with the returned object. Using the original object after type check
	// will fail.
	public static ASTExpression append(ASTExpression le, ASTExpression re) {
		return null;
	}
	
	protected final void error(String message) {
		System.out.println(message);
	}

	protected ELocality combineLocality(ELocality locality1, ELocality locality2) {
		return ELocality.localityByOrdinal(locality1.ordinal() | locality2.ordinal());
	}
	
	protected List<ASTExpression> extract(ASTExpression exp) {
		if (exp instanceof ASTCons) {
			return ((ASTCons)exp).getChildren();
		} else {
			List<ASTExpression> ret = new ArrayList<ASTExpression>();
			ret.add(exp);
			return ret;
		}
	}

	public Token getLocation() {
		return location;
	}

	public void setLocation(Token location) {
		this.location = location;
	}
}