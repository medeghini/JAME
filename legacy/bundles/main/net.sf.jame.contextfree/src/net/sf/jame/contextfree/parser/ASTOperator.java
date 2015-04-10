package net.sf.jame.contextfree.parser;

import java.util.ArrayList;
import java.util.List;


class ASTOperator extends ASTExpression {
	private char operator;
	private ASTExpression left;
	private ASTExpression right;

	public ASTOperator(char operator, ASTExpression leftExpression) {
		super(leftExpression != null ? leftExpression.isConstant() : true, leftExpression != null ? leftExpression.getType() : ExpType.NoType);
		if (leftExpression == null) {
			throw new RuntimeException("Missing expression");
		}
		this.operator = operator;
		this.left = leftExpression;
		this.right = null;
	}

	public ASTOperator(char operator, ASTExpression leftExpression,	ASTExpression rightExpression) {
		super((leftExpression != null ? leftExpression.isConstant() : true)	&& (rightExpression != null ? rightExpression.isConstant() : true), (leftExpression != null ? leftExpression.getType() : (rightExpression != null ? rightExpression.getType() : ExpType.NoType)));
		if (leftExpression == null) {
			throw new RuntimeException("Missing expression");
		}
		this.operator = operator;
		this.left = leftExpression;
		this.right = rightExpression;
	}

	public char getOperator() {
		return operator;
	}

	@Override
	public ASTExpression current() {
		return left;
	}

	@Override
	public ASTExpression next() {
		return right;
	}

	public static ASTExpression makeCanonical(List<ASTExpression> temp) {
		// Receive a vector of modification terms and return an ASTexpression 
		// with those terms rearranged into TRSSF canonical order. 
		// Duplicate terms are left in the input vector.
		List<ASTExpression> dropped = new ArrayList<ASTExpression>();

		try {
			ASTModification[] var = new ASTModification[1];
			ASTModification[] result = new ASTModification[1];

			ASTModTerm[] x = new ASTModTerm[1];
			ASTModTerm[] y = new ASTModTerm[1];
			ASTModTerm[] z = new ASTModTerm[1];
			ASTModTerm[] rot = new ASTModTerm[1];
			ASTModTerm[] skew = new ASTModTerm[1];
			ASTModTerm[] size = new ASTModTerm[1];
			ASTModTerm[] zsize = new ASTModTerm[1];
			ASTModTerm[] flip = new ASTModTerm[1];
			ASTModTerm[] transform = new ASTModTerm[1];

			for (ASTExpression exp : temp) {
				if (exp.type != ExpType.ModificationType) {
					throw new RuntimeException("Non-adjustment in shape adjustment context");
				}

				ASTModTerm mod = (ASTModTerm) exp;

				int argcount = 0;
				if (mod.getArguments() != null && mod.getArguments().type == ExpType.NumericType) {
					argcount = mod.getArguments().evaluate(null, 0, 0, null);
				}

				switch (mod.getModType()) {
				case x:
					setmod(x, mod, dropped);
					if (argcount > 1) {
						y[0] = null;
					}
					break;
				case y:
					setmod(y, mod, dropped);
					break;
				case z:
					setmod(z, mod, dropped);
					break;
				case modification:
				case transform:
					setmod(transform, mod, dropped);
					break;
				case rot:
					setmod(rot, mod, dropped);
					break;
				case size:
					setmod(size, mod, dropped);
					break;
				case zsize:
					setmod(zsize, mod, dropped);
					break;
				case skew:
					setmod(skew, mod, dropped);
					break;
				case flip:
					setmod(flip, mod, dropped);
					break;
				default:
					addmod(var, mod);
					break;
				}
			}

			temp.clear();
			temp.addAll(dropped);

			// If x and y are provided then merge them into a single (x,y) modification
			if (x[0] != null && y[0] != null && x[0].getArguments().evaluate(null, 0, 0, null) == 1 && y[0].getArguments().evaluate(null, 0, 0, null) == 1) {
				x[0].setArguments(new ASTCons(x[0].getArguments(), y[0].getArguments()));
				y[0].setArguments(null);
				y[0] = null;
			}

			addmod(result, x[0]);
			addmod(result, y[0]);
			addmod(result, z[0]);
			addmod(result, rot[0]);
			addmod(result, size[0]);
			addmod(result, zsize[0]);
			addmod(result, skew[0]);
			addmod(result, flip[0]);
			addmod(result, transform[0]);
			addmod(result, var[0]);

			return result[0];
		} catch (RuntimeException e) {
			temp.clear();
			dropped.clear();
			throw e;
		}
	}

	private static void addmod(ASTExpression[] var, ASTExpression mod) {
		if (mod == null)
			return;
		if (var[0] != null) {
			var[0] = new ASTOperator('+', var[0], mod);
		} else {
			var[0] = mod;
		}
	}

	private static void setmod(ASTModTerm[] mod, ASTModTerm newmod, List<ASTExpression> dropped) {
		if (mod[0] != null)
			dropped.add(mod[0]);
		mod[0] = newmod;
	}

	@Override
	public int evaluate(double[] result, int offset, int length, RTI rti) {
		double[] l = new double[] { 0.0 };
		double[] r = new double[] { 0.0 };

		if (result != null && length < 1)
			return -1;

		if (type == ExpType.FlagType && operator == '+') {
			if (left == null || left.evaluate(result != null ? l : null, 0, 1, rti) != 1)
				return -1;
			if (right == null || right.evaluate(result != null ? r : null, 0, 1, rti) != 1)
				return -1;
			int f = (int) l[0] | (int) r[0];
			if (result != null)
				result[0] = f;
			return 1;
		}

		if (type != ExpType.NumericType) {
			throw new RuntimeException("Non-numeric expression in a numeric context");
		}

		if (left.evaluate(result != null ? l : null, 0, 1, rti) != 1) {
			throw new RuntimeException("illegal operand");
		}

		int rightnum = right != null ? right.evaluate(result != null ? r : null, 0, 1, rti) : 0;

		if (rightnum == 0 && (operator == 'N' || operator == 'P' || operator == '!')) {
			if (result != null) {
				switch (operator) {
				case 'P':
					result[0] = +l[0];
					break;
				case 'N':
					result[0] = -l[0];
					break;
				case '!':
					result[0] = l[0] == 0.0 ? 1.0 : 0.0;
					break;
				default:
					return -1;
				}
			}
			return 1;
		}

		if (rightnum != 1) {
			throw new RuntimeException("illegal operand");
		}

		if (result != null) {
			switch (operator) {
			case '+':
				result[0] = l[0] + r[0];
				break;
			case '-':
				result[0] = l[0] - r[0];
				break;
			case '*':
				result[0] = l[0] * r[0];
				break;
			case '/':
				result[0] = l[0] / r[0];
				break;
			case '<':
				result[0] = (l[0] < r[0]) ? 1.0 : 0.0;
				break;
			case 'L':
				result[0] = (l[0] <= r[0]) ? 1.0 : 0.0;
				break;
			case '>':
				result[0] = (l[0] > r[0]) ? 1.0 : 0.0;
				break;
			case 'G':
				result[0] = (l[0] >= r[0]) ? 1.0 : 0.0;
				break;
			case '=':
				result[0] = (l[0] == r[0]) ? 1.0 : 0.0;
				break;
			case 'n':
				result[0] = (l[0] != r[0]) ? 1.0 : 0.0;
				break;
			case '&':
				result[0] = (l[0] != 0 && r[0] != 0) ? 1.0 : 0.0;
				break;
			case '|':
				result[0] = (l[0] != 0 || r[0] != 0) ? 1.0 : 0.0;
				break;
			case 'X':
				result[0] = (l[0] != 0 && r[0] == 0 || l[0] == 0 && r[0] != 0) ? 1.0 : 0.0;
				break;
			case '^':
				result[0] = Math.pow(l[0], r[0]);
				break;
			default:
				return -1;
			}
		} else {
			if ("+-*/^".indexOf(operator) == -1)
				return -1;
		}

		return 1;
	}

	@Override
	public int flatten(List<ASTExpression> dest) {
		if (type != ExpType.ModificationType) {
			dest.add(this);
			return 1;
		}

		int leftnum = 0;
		if (left != null)
			leftnum = left.flatten(dest);

		int rightnum = 0;
		if (right != null)
			rightnum = right.flatten(dest);

		left = null;
		right = null;

		return leftnum + rightnum;
	}

	@Override
	public void entropy(StringBuilder e) {
		if (left != null)
			left.entropy(e);
		if (right != null)
			right.entropy(e);

		switch (operator) {
			case '*':
				e.append("\u002E\u0032\u00D9\u002C\u0041\u00FE");
				break;
			case '/':
				e.append("\u006B\u0015\u0023\u0041\u009E\u00EB");
				break;
			case '+':
				e.append("\u00D7\u00B1\u00B0\u0039\u0033\u00C8");
				break;
			case '-':
				e.append("\u005D\u00E7\u00F0\u0094\u00C4\u0013");
				break;
			case '^':
				e.append("\u0002\u003C\u0068\u0036\u00C5\u00A0");
				break;
			case 'N':
				e.append("\u0055\u0089\u0051\u0046\u00DB\u0084");
				break;
			case 'P':
				e.append("\u008E\u00AC\u0029\u004B\u000E\u00DC");
				break;
			case '!':
				e.append("\u0019\u003A\u003E\u0053\u0014\u00EA");
				break;
			case '<':
				e.append("\u00BE\u00DB\u00C4\u00A6\u004E\u00AD");
				break;
			case '>':
				e.append("\u00C7\u00D9\u0057\u0032\u00D6\u0087");
				break;
			case 'L':
				e.append("\u00E3\u0056\u007E\u0044\u0057\u0080");
				break;
			case 'G':
				e.append("\u00B1\u002D\u002A\u00CC\u002C\u0040");
				break;
			case '=':
				e.append("\u0078\u0048\u00C2\u0095\u00A9\u00E2");
				break;
			case 'n':
				e.append("\u0036\u00CC\u0001\u003B\u002F\u00AD");
				break;
			case '&':
				e.append("\u0028\u009B\u00FB\u007F\u00DB\u009C");
				break;
			case '|':
				e.append("\u002E\u0040\u001B\u0044\u0015\u007C");
				break;
			case 'X':
				e.append("\u00A7\u002B\u0092\u00FA\u00FC\u00F9");
				break;
			default:
				e.append("\u0060\u002F\u0010\u00AD\u0010\u00FF");
				break;
		}
	}

	@Override
	public ASTExpression simplify() {
		if (left != null)
			left = left.simplify();
		if (right != null)
			right = right.simplify();

		if (isConstant && (type == ExpType.NumericType || type == ExpType.FlagType)) {
			double[] result = new double[1];
			if (evaluate(result, 0, 1, null) != 1) {
				return null;
			}

			ASTReal r = new ASTReal(result[0]);
			r.type = type;
			return r;
		}

		return this;
	}
}
