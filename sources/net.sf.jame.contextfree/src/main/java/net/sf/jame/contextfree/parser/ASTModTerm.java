package net.sf.jame.contextfree.parser;

import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

class ASTModTerm extends ASTExpression {
	private int argCount;
	private EModType modType;
	private ASTExpression args;

	public ASTModTerm(EModType modType, ASTExpression args) {
		super(true, false, EExpType.ModType);

		this.modType = modType;
		this.args = args;
		this.argCount = 0;

		if (args.type == EExpType.RuleType)
			throw new RuntimeException("Illegal expression in shape adjustment");

		if (args.type == EExpType.ModType) {
			if (modType != EModType.transform)
				throw new RuntimeException(
						"Cannot accept a transform expression here");
			modType = EModType.modification;
		}
	}

	public ASTModTerm(EModType param, String entropy) {
		// TODO Auto-generated constructor stub
	}

	public ASTModTerm(EModType param) {
		// TODO Auto-generated constructor stub
	}

	public EModType getModType() {
		return modType;
	}

	public void setModType(EModType modType) {
		this.modType = modType;
	}
	
	public ASTExpression getArguments() {
		return args;
	}

	public void setArguments(ASTExpression arguments) {
		this.args = arguments;
	}
	
	public int getArgumentsCount() {
		return argCount;
	}

	public void setArgumentsCount(int argCount) {
		this.argCount = argCount;
	}
	
	@Override
	public void entropy(StringBuilder e) {
		if (args != null) {
			args.entropy(e);
		}
		e.append(modType.getEntropy());
	}

	@Override
	public int evaluate(double[] result, int length, RTI rti) {
		error("Improper evaluation of an adjustment expression");
		return -1;
	}

	@Override
	public void evaluate(Modification[] result, boolean shapeDest, RTI rti) {
		double[] modArgs = new double[6];
		int argcount = 0;

		if (args != null) {
			if (modType != EModType.modification && args.type == EExpType.NumericType) {
				argcount = args.evaluate(modArgs, 6, rti);
			} else if (modType == EModType.modification && args.type != EExpType.ModType) {
				error("Adjustments require numeric arguments");
				return;
			}
		}

		if (argcount != argCount) {
			error("Error evaluating arguments");
			return;
		}
		
		double[] args = new double[6];
		for (int i = 0; i < argcount; ++i) {
			args[i] = Math.max(-1.0, Math.min(1.0, modArgs[i]));
		}
		
		double[] color = result[0].color().values();
		double[] target = result[0].colorTarget().values();
		int colorComp = 0;
		boolean hue = true;
		long mask = EAssignmentType.HueMask.ordinal();

		switch (modType) {
		case x: {
			if (argcount == 1) {
				modArgs[1] = 0.0;
			}
			AffineTransform t2d = AffineTransform.getTranslateInstance(modArgs[0], modArgs[1]);
			result[0].getTransform().preConcatenate(t2d);
			break;
		}
		case y: {
			AffineTransform t2d = AffineTransform.getTranslateInstance(0.0, modArgs[0]);
			result[0].getTransform().preConcatenate(t2d);
			break;
		}
		case z: {
			AffineTransform1D t1d = AffineTransform1D.getTranslateInstance(modArgs[0]);
			result[0].getTransformZ().preConcatenate(t1d);
			break;
		}
		case xyz: {
			AffineTransform t2d = AffineTransform.getTranslateInstance(modArgs[0], modArgs[1]);
			AffineTransform1D t1d = AffineTransform1D.getTranslateInstance(modArgs[0]);
			result[0].getTransform().preConcatenate(t2d);
			result[0].getTransformZ().preConcatenate(t1d);
			break;
		}
		case time: {
			AffineTransformTime tTime = AffineTransformTime.getTranslateInstance(modArgs[0], modArgs[1]);
			result[0].getTransformTime().preConcatenate(tTime);
			break;
		}
		case timescale: {
			AffineTransformTime tTime = AffineTransformTime.getScaleInstance(modArgs[0]);
			result[0].getTransformTime().preConcatenate(tTime);
			break;
		}
		case transform: {
			switch (argcount) {
				case 2:
				case 1:
					{
						if (argcount == 1) {
							modArgs[1] = 0.0;
						}
						AffineTransform t2d = AffineTransform.getTranslateInstance(modArgs[0], modArgs[1]);
						result[0].getTransform().preConcatenate(t2d);
					}
					break;
	
				case 4:
					{
						AffineTransform t2d = new AffineTransform();
						double dx = modArgs[2] - modArgs[0];
						double dy = modArgs[3] - modArgs[1];
						double s = Math.hypot(dx, dy);
						t2d.rotate(Math.atan2(dx, dy));
						t2d.scale(s, s);
						t2d.translate(modArgs[0], modArgs[1]);
						result[0].getTransform().preConcatenate(t2d);
					}
					break;
					
				case 6:
					{
						try {
							AffineTransform t2d = new AffineTransform(modArgs[2] - modArgs[0], modArgs[3] - modArgs[1], modArgs[4] - modArgs[0], modArgs[5] - modArgs[1], modArgs[0], modArgs[1]);
							AffineTransform par = new AffineTransform();
							par.shear(1, 0);
							par.invert();
							par.concatenate(t2d);
							result[0].getTransform().preConcatenate(par);
						} catch (NoninvertibleTransformException e) {
							error(e.getMessage());
						}
					}
					break;
					
				default:
					break;
			}
			break;
		}
		case size: {
			if (argcount == 1) {
				modArgs[1] =  modArgs[0];
			}
			AffineTransform t2d = AffineTransform.getScaleInstance(modArgs[0], modArgs[1]);
			result[0].getTransform().preConcatenate(t2d);
			break;
		}
		case sizexyz: {
			AffineTransform t2d = AffineTransform.getScaleInstance(modArgs[0], modArgs[1]);
			AffineTransform1D t1d = AffineTransform1D.getScaleInstance(modArgs[0]);
			result[0].getTransform().preConcatenate(t2d);
			result[0].getTransformZ().preConcatenate(t1d);
			break;
		}
		case zsize: {
			AffineTransform1D t1d = AffineTransform1D.getScaleInstance(modArgs[0]);
			result[0].getTransformZ().preConcatenate(t1d);
			break;
		}
		case rot: {
			AffineTransform t2d = AffineTransform.getRotateInstance(modArgs[0] * Math.PI / 180.0);
			result[0].getTransform().preConcatenate(t2d);
			break;
		}
		case skew: {
			AffineTransform t2d = AffineTransform.getShearInstance(modArgs[0] * Math.PI / 180.0, modArgs[1] * Math.PI / 180.0);
			result[0].getTransform().preConcatenate(t2d);
			break;
		}
		case flip: {
			double a = modArgs[0] * Math.PI / 180.0;
			double ux = Math.cos(a);
			double uy = Math.cos(a);
			AffineTransform t2d = new AffineTransform(2.0 * ux * ux - 1.0, 2.0 * ux * uy, 2.0 * ux * uy, 2.0 * uy * uy - 1.0, 0.0, 0.0);
			result[0].getTransform().preConcatenate(t2d);
			break;
		}
		case alpha: 
		case bright: 
		case sat: 
			{
				colorComp += modType.ordinal() - EModType.hue.ordinal();
				mask <<= 2 * modType.ordinal() - EModType.hue.ordinal();
				hue = false;
			}
		case hue: 
			{
				 if (argcount == 1) {
					 if ((result[0].colorAssignment() & mask) != 0 || (!hue && color[colorComp] != 0.0)) {
						 if (rti == null) throw new DeferUntilRuntimeException();
						 if (!shapeDest) {
							 rti.colorConflict();
						 }
					 }
					 if (shapeDest) {
						 color[colorComp] = hue ? HSBColor.adjustHue(color[colorComp], modArgs[0]) : HSBColor.adjust(color[colorComp], modArgs[0]);
					 } else {
						 color[colorComp] = hue ? color[colorComp] + modArgs[0] : args[0];
					 }
				 } else {
					 if ((result[0].colorAssignment() & mask) != 0 || (color[colorComp] != 0.0) || (!hue && target[colorComp] != 0.0)) {
						 if (rti == null) throw new DeferUntilRuntimeException();
						 if (!shapeDest) {
							 rti.colorConflict();
						 }
					 }
					 if (shapeDest) {
						 color[colorComp] = hue ? HSBColor.adjustHue(color[colorComp], args[0], EAssignmentType.HueTarget, modArgs[1]) : HSBColor.adjust(color[colorComp], args[0], EAssignmentType.ColorTarget, args[1]);
					 } else {
						 color[colorComp] = args[0];
						 target[colorComp] = hue ? modArgs[1] : args[1];
						 result[0].setColorAssignment(result[0].colorAssignment() | EAssignmentType.HSBA2Value.ordinal() & mask);
					 }
				 }
			}
			break;
		case alphaTarg: 
		case brightTarg: 
		case satTarg: 
			{
				colorComp += modType.ordinal() - EModType.hueTarg.ordinal();
				mask <<= 2 * modType.ordinal() - EModType.hueTarg.ordinal();
				hue = false;
			}
		case hueTarg: 
			{
				 if ((result[0].colorAssignment() & mask) != 0 || (color[colorComp] != 0.0)) {
					 if (rti == null) throw new DeferUntilRuntimeException();
					 if (!shapeDest) {
						 rti.colorConflict();
					 }
				 }
				 if (shapeDest) {
					 color[colorComp] = hue ? HSBColor.adjustHue(color[colorComp], args[0], EAssignmentType.HueTarget, target[colorComp]) : HSBColor.adjust(color[colorComp], args[0], EAssignmentType.ColorTarget, target[colorComp]);
				 } else {
					 color[colorComp] = args[0];
					 result[0].setColorAssignment(result[0].colorAssignment() | EAssignmentType.HSBATarget.ordinal() & mask);
				 }
			}
			break;
		case targAlpha: 
		case targBright: 
		case targSat: 
			{
				colorComp += modType.ordinal() - EModType.targHue.ordinal();
				mask <<= 2 * modType.ordinal() - EModType.targHue.ordinal();
				if (target[colorComp] != 0.0) {
					 if (rti == null) throw new DeferUntilRuntimeException();
					 if (!shapeDest) {
						 rti.colorConflict();
					 }
				}
				 if (shapeDest) {
					 target[colorComp] = HSBColor.adjust(target[colorComp], args[0]);
				 } else {
					 target[colorComp] = args[0];
				 }
			}
			break;
		case targHue: 
			{
				 target[0] += modArgs[0];
			}
			break;
		case param: {
			error("Cannot provide a parameter in this context");
			break;
		}
		case stroke: {
			error("Cannot provide a stroke width in this context");
			break;
		}
		case modification: {
			if (rti == null) {
				if (args != null && getArguments() instanceof ASTModification) {
					ASTModification mod = (ASTModification)getArguments();
					if ((mod.getModClass().ordinal() & (EModClass.HueClass.ordinal() | EModClass.HueTargetClass.ordinal() | EModClass.BrightClass.ordinal() | EModClass.BrightTargetClass.ordinal() | EModClass.SatClass.ordinal() | EModClass.SatTargetClass.ordinal() | EModClass.AlphaClass.ordinal() | EModClass.AlphaTargetClass.ordinal())) != 0) {
						throw new DeferUntilRuntimeException();
					}
				}
			}
			getArguments().evaluate(result, shapeDest, rti);
			break;
		}
		default:
			break;
		}
	}

	@Override
	public ASTExpression simplify() {
		if (args != null) {
			args = args.simplify();
		}
		return this;
	}

	@Override
	public ASTExpression compile(ECompilePhase ph) {
		if (args != null) {
			args = args.compile(ph);
		}
		if (args == null) {
			if (modType == EModType.param) {
				error("Illegal expression in shape adjustment");
				return null;
			}
		}
		
		switch (ph) {
			case TypeCheck:
				{
					isConstant = args.isConstant();
					locality = args.getLocality();
					switch (args.getType()) {
						case NumericType:
							{
								argCount = args.evaluate(null, 0);
								int minCount = 1;
								int maxCount = 1;
								
								if (argCount == 3 && modType == EModType.x) {
									modType = EModType.xyz;
								}
								if (argCount == 3 && modType == EModType.size) {
									modType = EModType.sizexyz;
								}
								
								switch (modType) {
									case x:
									case size:
									case hue:
									case sat:
									case bright:
									case alpha:
										maxCount = 2;
										break;
	
									case y:
									case z:
									case timescale:
									case zsize:
									case rot:
									case flip:
									case hueTarg:
									case satTarg:
									case brightTarg:
									case alphaTarg:
									case targHue:
									case targSat:
									case targBright:
									case targAlpha:
									case stroke:
										break;
										
									case xyz:
									case sizexyz:
										minCount = maxCount = 3;
										break;
										
									case time:
									case skew:
										minCount = maxCount = 2;
										break;
										
									case transform:
										maxCount = 6;
										if (argCount != 1 && argCount != 2 && argCount != 4 && argCount != 6) {
											error("transform adjustment takes 1, 2, 4, or 6 parameters");
										}
										break;
										
									case param:
										minCount = maxCount = 0;
										break;
										
									case modification:
										break;
										
									default:
										break;
								}
								
		                        if (argCount < minCount) {
		                        	error("Not enough adjustment parameters");
		                        }
		                        if (argCount > maxCount) {
		                        	error("Too many adjustment parameters");
		                        }
							}
							break;
	
						case ModType: 
							{
								if (modType != EModType.transform) {
									error("Cannot accept a transform expression here");
								} else {
									modType = EModType.modification;
								}
							}
							break;
							
						default:
							error("Illegal expression in shape adjustment");
							break;
					}
				}
				break;
	
			case Simplify:
				break;
	
			default:
				break;
			}
		return null;
	}
}