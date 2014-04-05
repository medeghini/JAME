package net.sf.jame.contextfree.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Stack;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;

public class Builder {
	private static Builder currentBuilder;

	private CFDG cfdg = new CFDG();
	private ASTRand48 seed;
	private Stack<ASTRepContainer> containerStack = new Stack<ASTRepContainer>();
	private ASTRepContainer paramDecls = new ASTRepContainer();
	private Map<String, Integer> flagNames = new HashMap<String, Integer>();
	private List<StackRule> longLivedParams = new ArrayList<StackRule>();
	private Stack<String> fileNames = new Stack<String>();
	private Stack<String> filesToLoad = new Stack<String>();
	private Stack<CharStream> streamsToLoad = new Stack<CharStream>();
	private Stack<Boolean> includeNamespace = new Stack<Boolean>();
	private Stack<ASTSwitch> switchStack = new Stack<ASTSwitch>();
	private String currentNameSpace = "";
	private String currentPath;
	private String maybeVersion;
	private int currentShape;
	private int pathCount;
	private int includeDepth;
	private int localStackDepth;
	private double maxNatual;
	private boolean allowOverlap;
	private boolean inPathContainer;

	static List<String> globals = new ArrayList<String>();
	{ 
		globals.add("CIRCLE");
		globals.add("FILL");
		globals.add("SQUARE");
		globals.add("TRIANGLE");
	}
	
	protected void warning(String message) {
		System.out.println(message);
	}
	
	protected void error(String message) {
		System.err.println(message);
	}
	
	protected boolean isPrimeShape(int nameIndex) {
		return nameIndex < 4;
	}

	public int stringToShape(String name, boolean colonsAllowed) {
		checkName(name, colonsAllowed);
		if (currentNameSpace.length() == 0) {
			return cfdg.encodeShapeName(name);
		}
		int index = Collections.binarySearch(globals, name);
		String n = currentNameSpace + name;
		if (index != -1 && cfdg.tryEncodeShapeName(n) == -1) {
			return cfdg.encodeShapeName(name);
		} else {
			return cfdg.encodeShapeName(n);
		}
	}

	public String shapeToString(int shape) {
		return cfdg.decodeShapeName(shape);
	}

	public void checkName(String name, boolean colonsAllowed) {
		int pos = name.indexOf(":");
		if (pos == -1) {
			return;
		}
		if (!colonsAllowed) {
			error("namespace specification not allowed in this context");
			return;
		}
		if (pos == 0) {
			error("improper namespace specification");
			return;
		}
		for (;;) {
			if (pos == name.length() - 1 || name.charAt(pos + 1) != ':') break;
			int next = name.indexOf(":", pos + 2);
			if (next == -1) return;
			if (next == pos + 2) break;
			pos = next;
		}
		error("improper namespace specification");
	}
	
	public void includeFile(String fileName) {
		try {
			String path = relativeFilePath(currentPath, fileName);
			ANTLRFileStream is = new ANTLRFileStream(path);
			fileNames.push(path);
			currentPath = path;
			filesToLoad.push(currentPath);
			streamsToLoad.push(is);
			includeNamespace.push(Boolean.FALSE);
			pathCount++;
			includeDepth++;
			currentShape = -1;
			setShape(null, false);
			warning("Reading rules file " + path);
		} catch (Exception e) {
			error(e.getMessage());
		}
	}
	
	public boolean endInclude() {
		try {
			boolean endOfInput = includeDepth == 0;
			setShape(null, false);
			includeDepth--;
			if (filesToLoad.isEmpty()) {
				return endOfInput;
			}
			if (includeNamespace.peek()) {
				popNameSpace();
			}
			streamsToLoad.pop();
			filesToLoad.pop();
			includeNamespace.pop();
			currentPath = filesToLoad.isEmpty() ? null : filesToLoad.peek();
			return endOfInput;
		} catch (Exception e) {
			error(e.getMessage());
			return false;
		}
	}
	
	public void setShape(String name) {
		setShape(null, false);
	}
	
	public void setShape(String name, boolean isPath) {
		if (name == null) {
			currentShape = -1;
			return;
		}
		currentShape = stringToShape(name, false);
		ASTDefine def = cfdg.findFunction(currentShape);
		if (def != null) {
			error("There is a function with the same name as this shape: " + def.getLocation());
			return;
		}
		String err = cfdg.setShapeParams(currentShape, paramDecls, paramDecls.getStackCount(), isPath);
		if (err != null) {
			error("cannot set shape params: " + err);
		}
		localStackDepth -= paramDecls.getStackCount();
		paramDecls.getParameters().clear();
		paramDecls.setStackCount(0);
	}
	
	public void addRule(ASTRule rule) {
		boolean isShapeItem = rule.getNameIndex() == -1;
		if (isShapeItem) {
			rule.setNameIndex(currentShape);
		} else {
			currentShape = -1;
		}
		if (rule.getNameIndex() == -1) {
			error("Shape rules/paths must follow a shape declaration");
		}
		EShapeType type = cfdg.getShapeType(rule.getNameIndex());
		if ((rule.isPath() && type == EShapeType.RuleType) || (!rule.isPath() && type == EShapeType.PathType)) {
			error("Cannot mix rules and shapes with the same name");
		}
		boolean matchesShape = cfdg.addRuleShape(rule);
		if (!isShapeItem && matchesShape) {
			error("Rule/path name matches existing shape name");
		}
	}

	public void nextParameterDecl(String type, String name) {
		int nameIndex = stringToShape(name, false);
		checkVariableName(nameIndex, true);
		paramDecls.addParameter(type, nameIndex);
		ASTParameter param = paramDecls.getParameters().get(paramDecls.getParameters().size() - 1);
		param.setStackIndex(localStackDepth);
		paramDecls.setStackCount(paramDecls.getStackCount() + param.getTupleSize());
		localStackDepth += param.getTupleSize();
	}

	public ASTDefine makeDefinition(String name, boolean isFunction) {
		if (name.startsWith("CF::")) {
			if (isFunction) {
				error("Configuration parameters cannot be functions");
				return null;
			}
			if (containerStack.lastElement().isGlobal()) {
				error("Configuration parameters must be at global scope");
				return null;
			}
			ASTDefine def = new ASTDefine(name);
			def.setConfigDepth(includeDepth);
			def.setDefineType(EDefineType.ConfigDefine);
			return def;
		}
		if (EFuncType.getFuncTypeByName(name) != EFuncType.NotAFunction) {
			error("Internal function names are reserved");
			return null;
		}
		int nameIndex = stringToShape(name, false);
		ASTDefine def = cfdg.findFunction(nameIndex);
		if (def != null) {
			error("Definition with same name as user function: " + def.getLocation());
			return null;
		}
		checkVariableName(nameIndex, false);
		def = new ASTDefine(name);
		def.getRuleSpecifier().setShapeType(nameIndex);
		if (isFunction) {
			for (ASTParameter param : paramDecls.getParameters()) {
				param.setLocality(ELocality.PureNonlocal);
			}
			def.getParameters().clear();
			def.getParameters().addAll(paramDecls.getParameters());
			def.setStackCount(paramDecls.getStackCount());
			def.setDefineType(EDefineType.FunctionDefine);
			localStackDepth -= paramDecls.getStackCount();
			paramDecls.setStackCount(0);
			cfdg.declareFunction(nameIndex, def);
		} else {
			containerStack.lastElement().addDefParameter(nameIndex, def);
		}
		return def;
	}
	
	public void makeConfig(ASTDefine cfg) {
		if (cfg.getName().equals("CF::Impure")) {
			double[] v = new double[] { 0.0 };
			if (cfg.getExp() != null || cfg.getExp().isConstant() || cfg.getExp().evaluate(v, 1, null) != 1) {
				error("CF::Impure requires a constant numeric expression");
			} else {
				ASTParameter.Impure = v[0] != 0.0;
			}
		}
		if (cfg.getName().equals("CF::AllowOverlap")) {
			double[] v = new double[] { 0.0 };
			if (cfg.getExp() != null || cfg.getExp().isConstant() || cfg.getExp().evaluate(v, 1, null) != 1) {
				error("CF::AllowOverlap requires a constant numeric expression");
			} else {
				allowOverlap = v[0] != 0.0;
			}
		}
		if (cfg.getName().equals("CF::StartShape") && cfg.getExp() != null && (cfg.getExp() instanceof ASTStartSpecifier)) {
			ASTRuleSpecifier rule = null;
			ASTModification mod = null;
			List<ASTExpression> specAndMod = extract(cfg.getExp());
			switch (specAndMod.size()) {
				case 2:
					if (!(specAndMod.get(1) instanceof ASTModification)) {
						error("CF::StartShape second term must be a modification");
					} else {
						mod = (ASTModification) specAndMod.get(1);
					}
					break;
	
				case 1:
					if (!(specAndMod.get(0) instanceof ASTRuleSpecifier)) {
						error("CF::StartShape must start with a shape specification");
					} else {
						rule = (ASTRuleSpecifier) specAndMod.get(0);
					}
					break;
					
				default:
					error("CF::StartShape expression must have the form shape_spec or shape_spec, modification");
					break;
			}
			if (mod == null) {
				mod = new ASTModification();
			}
			cfg.setExp(new ASTStartSpecifier(rule, mod));
		}
		ASTExpression current = cfg.getExp();
		if (!cfdg.addParameter(cfg.getName(), cfg.getExp(), cfg.getConfigDepth())) {
			error("Unknown configuration parameter");
		}
		if (cfg.getName().equals("CF::MaxNatural")) {
			ASTExpression max = cfdg.hasParameter(ECFGParam.MaxNatural);
			if (max != current) {
				return;
			}
			double[] v = new double[] { -1.0 };
			if (max == null || !max.isConstant() || max.getType() != EExpType.NumericType || max.evaluate(v, 1, null) != 1) {
				error("CF::MaxNatural requires a constant numeric expression");
			} else if (v[0] < 1.0 || v[0] > 9007199254740992.0) {
				error(v[0] < 1.0 ? "CF::MaxNatural must be >= 1" : "CF::MaxNatural must be < 9007199254740992");
			} else {
				maxNatual = v[0];
			}
		}
	}
	
	private List<ASTExpression> extract(ASTExpression exp) {
		if (exp instanceof ASTCons) {
			return ((ASTCons)exp).getChildren();
		} else {
			List<ASTExpression> ret = new ArrayList<ASTExpression>();
			ret.add(exp);
			return ret;
		}
	}

	public ASTExpression makeVariable(String name) {
		Integer flagItem = flagNames.get(name);
		if (flagItem != null) {
			ASTReal flag = new ASTReal(flagItem);
			flag.setType(EExpType.FlagType);
			return flag;
		}
		if (name.startsWith("CF::")) {
			error("Configuration parameter names are reserved");
			return new ASTExpression();
		}
		if (EFuncType.getFuncTypeByName(name) != EFuncType.NotAFunction) {
			error("Internal function names are reserved");
			return new ASTExpression();
		}
		int varNum = stringToShape(name, true);
		boolean isGlobal = false;
		ASTParameter bound = findExpression(varNum, isGlobal);
		if (bound == null) {
			return new ASTRuleSpecifier(varNum, name, null, cfdg.getShapeParams(currentShape));
		}
		return new ASTVariable(varNum, name);
	}

	public ASTExpression makeArray(String name, ASTExpression args) {
		if (name.startsWith("CF::")) {
			error("Configuration parameter names are reserved");
			return args;
		}
		int varNum = stringToShape(name, true);
		boolean isGlobal = false;
		ASTParameter bound = findExpression(varNum, isGlobal);
		if (bound == null) {
			return args;
		}
		return new ASTArray(varNum, args, new String[1]);
	}

	public ASTExpression makeLet(ASTRepContainer vars, ASTExpression exp) {
		int nameIndex = stringToShape("let", false);
		ASTDefine def = new ASTDefine("let");
		def.getRuleSpecifier().setShapeType(nameIndex);
		def.setExp(exp);
		def.setDefineType(EDefineType.LetDefine);
		return new ASTLet(vars, def);
	}

	public ASTRuleSpecifier makeRuleSpec(String name, ASTExpression args) {
			return makeRuleSpec(name, args, null, false);
	}
	
	public ASTRuleSpecifier makeRuleSpec(String name, ASTExpression args, ASTModification mod, boolean makeStart) {
		if (name.equals("if") || name.equals("let") || name.equals("select")) {
			if (name.equals("select")) {
				args = new ASTSelect(args, false);
			}
			if (makeStart) {
				return new ASTStartSpecifier(args, mod);
			} else {
				return new ASTRuleSpecifier(args);
			}
		}
		int nameIndex = stringToShape(name, true);
		boolean isGlobal = false;
		ASTParameter bound = findExpression(nameIndex, isGlobal);
		if (bound != null && args != null && args.getType() == EExpType.ReuseType && !makeStart && isGlobal && nameIndex == currentShape) {
			error("Shape name binds to global variable and current shape, using current shape");
		}
		if (bound != null && bound.isParameter() && bound.getType() == EExpType.RuleType) {
			return new ASTRuleSpecifier(nameIndex, name);
		}
		ASTRuleSpecifier ret = null;
		cfdg.setShapeHasNoParam(nameIndex, args);
		if (makeStart) {
			ret = new ASTStartSpecifier(nameIndex, name, args, mod);
		} else {
			ret = new ASTRuleSpecifier(nameIndex, name, args, cfdg.getShapeParams(currentShape));
		}
		if (ret.getArguments() != null && ret.getArguments().getType() == EExpType.ReuseType) {
			if (makeStart) {
				error("Startshape cannot reuse parameters");
			} else if (nameIndex == currentShape)  {
				ret.setArgSouce(EArgSource.SimpleArgs);
				ret.setTypeSignature(ret.getTypeSignature());
			}
		}
		return ret;
	}

	public void makeModTerm(ASTModification dest, ASTModTerm t) {
		if (t == null) {
			return;
		}
		if (t.getModType() == EModType.time) {
			timeWise();
		}
		if (t.getModType() == EModType.sat || t.getModType() == EModType.satTarg) {
			inColor();
		}
		dest.concat(t);
	}
	
	public ASTReplacement makeElement(String s, ASTModification mods, ASTExpression params, boolean subPath) {
		if (inPathContainer && !subPath && (s.equals("FILL") || s.equals("STROKE"))) {
			return new ASTPathCommand(s, mods, params);
		}
		ASTRuleSpecifier r = makeRuleSpec(s, params, null, false);
		ERepElemType t = ERepElemType.replacement;
		if (inPathContainer) {
			boolean isGlobal = false;
			ASTParameter bound = findExpression(r.getShapeType(), isGlobal);
			if (!subPath) {
				error("Replacements are not allowed in paths");
			} else if (r.getArgSource() == EArgSource.StackArgs || r.getArgSource() == EArgSource.ShapeArgs) {
	            // Parameter subpaths must be all ops, but we must check at runtime
				t = ERepElemType.op;
			} else if (cfdg.getShapeType(r.getShapeType()) == EShapeType.PathType) {
				ASTRule rule = cfdg.findRule(r.getShapeType());
				if (rule != null) {
					t = ERepElemType.typeByOrdinal(rule.getRuleBody().getRepType());
				} else {
					error("Subpath references must be to previously declared paths");
				}
			} else if (bound != null) {
	            // Variable subpaths must be all ops, but we must check at runtime
				t = ERepElemType.op;
			} else if (isPrimeShape(r.getShapeType())) {
				t = ERepElemType.op;
			} else {
				error("Subpath references must be to previously declared paths");
			}
		}
		return new ASTReplacement(r, mods, t); 
	}

	public ASTExpression makeFunction(String name, ASTExpression args) {
		return makeFunction(name, args, false);
	}
	
	public ASTExpression makeFunction(String name, ASTExpression args, boolean consAllowed) {
		int nameIndex = stringToShape(name, true);
		boolean isGlobal = false;
		ASTParameter bound = findExpression(nameIndex, isGlobal);
		if (bound != null) {
			if (!consAllowed) {
				error("Cannot bind expression to variable/parameter");
			}
			return makeVariable(name).append(args); 
		}
		if (name.equals("select") || name.equals("if")) {
			return new ASTSelect(args, name.equals("if"));
		}
		EFuncType t = EFuncType.getFuncTypeByName(name);
		if (t == EFuncType.NotAFunction) {
			return new ASTFunction(name, args, seed);
		}
		if (args != null && args.getType() == EExpType.ReuseType) {
			return makeRuleSpec(name, args, null, false);
		}
		return new ASTUserFunction(name, args, null);
	}
	
	public ASTModification makeModification(ASTModification mod, boolean canonial) {
		mod.setIsConstant(mod.getModExp().isEmpty());
		mod.setCanonical(canonial);
		return mod;
	}
	
	public String getTypeInfo(int nameIndex, ASTDefine[] func, List<ASTParameter>[] p) {
		func[0] = cfdg.findFunction(nameIndex);
		p[0] = cfdg.getShapeParams(nameIndex);
		return cfdg.decodeShapeName(nameIndex);
	}

	public ASTRule getRule(int nameIndex) {
		return cfdg.findRule(nameIndex);
	}

	public void pushRepContainer(ASTRepContainer c) {
		containerStack.push(c);
		processRepContainer(c);
	}
	
	private void processRepContainer(ASTRepContainer c) {
		c.setStackCount(0);
		for (ASTParameter param : c.getParameters()) {
			if (param.isParameter() || param.isLoopIndex()) {
				param.setStackIndex(localStackDepth);
				c.setStackCount(c.getStackCount() + param.getTupleSize());
				localStackDepth += param.getTupleSize();
			} else {
				break;  // the parameters are all in front
			}
		}
	}
	
	public void popRepContainer(ASTReplacement r) {
		ASTRepContainer lastContainer = containerStack.lastElement();
		localStackDepth -= lastContainer.getStackCount();
		if (r != null) {
			r.setRepType(ERepElemType.typeByOrdinal(r.getRepType().ordinal() | lastContainer.getRepType()));
			if (r.getPathOp() == EPathOp.UNKNOWN) {
				r.setPathOp(lastContainer.getPathOp());
			}
		}
		containerStack.pop();
	}

	private boolean badContainer(int containerType) {
		return (containerType & (ERepElemType.op.ordinal() | ERepElemType.replacement.ordinal())) == (ERepElemType.op.ordinal() | ERepElemType.replacement.ordinal());
	}
	
	public void pushRep(ASTReplacement r, boolean global) {
		if (r == null) {
			return;
		}
		ASTRepContainer container = containerStack.lastElement();
		container.getBody().remove(container.getBody().size() - 1);
		container.getBody().add(r);
		if (container.getPathOp() == EPathOp.UNKNOWN) {
			container.setPathOp(r.getPathOp());
		}
		int oldType = container.getRepType();
		container.setRepType(oldType | r.getRepType().ordinal());
		if (badContainer(container.getRepType()) && !badContainer(oldType) && !global) {
			error("Cannot mix path elements and replacements in the same container");
		}
	}
	
	public ASTParameter findExpression(int nameIndex, boolean isGlobal) {
		for (ListIterator<ASTRepContainer> i = containerStack.listIterator(); i.hasPrevious();) {
			ASTRepContainer repCont = i.previous();
			for (ListIterator<ASTParameter> p = repCont.getParameters().listIterator(); i.hasPrevious();) {
				ASTParameter param = p.previous();
				if (param.getNameIndex() == nameIndex) {
					isGlobal = repCont.isGlobal();
					return param;
				}
			}
		}
		return null;
	}
	
	protected void checkVariableName(int nameIndex, boolean param) {
		if (allowOverlap && !param) {
			return;
		}
		ASTRepContainer repCont = param ? paramDecls : containerStack.lastElement();
		for (ListIterator<ASTParameter> i = repCont.getParameters().listIterator(); i.hasPrevious();) {
			ASTParameter p = i.previous();
			if (p.getNameIndex() == nameIndex) {
				error("Scope of name overlaps variable/parameter with same name: + " + p.getLocation());
			}
		}
	}

	protected String relativeFilePath(String base, String rel) {
		int i = base.lastIndexOf("/");
		if (i == -1) {
			return rel;
		}
		return base.substring(0, i) + rel;
	}
	
	protected void popNameSpace() {
		currentNameSpace = currentNameSpace.substring(0, currentNameSpace.length() - 2);
		int end = currentNameSpace.lastIndexOf(":");
		if (end == -1) {
			currentNameSpace = "";
		} else {
			currentNameSpace.substring(0, end + 1);
		}
	}

	protected void pushNameSpace(String n) {
		if (n.equals("CF")) {
			error("CF namespace is reserved");
			return;
		}
		if (n.length() == 0) {
			error("zero-length namespace");
			return;
		}
		checkName(n, false);
		includeNamespace.pop();
		includeNamespace.push(Boolean.TRUE);
		currentNameSpace = currentNameSpace + n + "::";
	}
	
	public void inColor() {
		cfdg.addParameter(EParam.Color);
	}

	public void timeWise() {
		cfdg.addParameter(EParam.Time);
	}

	public void storeParams(StackRule p) {
		p.setRefCount(Integer.MAX_VALUE);
		longLivedParams.add(p);
	}

	public String getMaybeVersion() {
		return maybeVersion;
	}

	public void setMaybeVersion(String maybeVersion) {
		this.maybeVersion = maybeVersion;
	}

	public EExpType decodeType(String typeName, int[] tupleSize, boolean[] isNatural) {
		EExpType type;
		tupleSize[0] = 1;
        isNatural[0] = false;
        
        if (typeName.equals("number")) {
            type = EExpType.NumericType;
        } else if (typeName.equals("natural")) {
            type = EExpType.NumericType;
            isNatural[0] = true;
        } else if (typeName == "adjustment") {
            type = EExpType.ModType;
            tupleSize[0] = 6;
        } else if (typeName == "shape") {
            type = EExpType.RuleType;
        } else if (typeName.startsWith("vector")) {
        	type = EExpType.NumericType;
        	if (typeName.matches("vector[0-9]+")) {
                tupleSize[0] = Integer.parseInt(typeName.substring(6));
                if (tupleSize[0] <= 1 || tupleSize[0] > 99) {
                	error("Illegal vector size (<=1 or >99)");
                }
        	} else {
        		error("Illegal vector type specification");
        	}
        } else {
            type = EExpType.NoType;
            error("Unrecognized type name");
        }
        return type;
	}

	public void setInPathContainer(boolean inPathContainer) {
		this.inPathContainer = inPathContainer;
	}

	public Stack<ASTSwitch> getSwitchStack() {
		return switchStack;
	}

	public void incSwitchStack() {
		localStackDepth--;
	}

	public void decSwitchStack() {
		localStackDepth++;
	}

	public ASTRand48 getSeed() {
		return seed;
	}

	public ASTRepContainer getParamDecls() {
		return paramDecls;
	}

	public static Builder currentBuilder() {
		return currentBuilder;
	}

	public int getLocalStackDepth() {
		return localStackDepth;
	}

	public boolean isInPathContainer() {
		return this.inPathContainer ;
	}
}
