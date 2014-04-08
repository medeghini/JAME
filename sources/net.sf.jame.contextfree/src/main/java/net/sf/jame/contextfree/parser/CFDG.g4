grammar CFDG;

options
{
} 

@lexer::header { 
}

@parser::header {
	import java.util.Map;
}

@members {
	Builder driver = new Builder();
}

choose
		:
		CFDG2 cfdg2 
		|
		CFDG3 cfdg3
		;
		 
cfdg2
        :
        r=statement_v2 cfdg2 {
	        if ($r.result != null) {
	          	driver.pushRep($r.result, true);
	        }
        }
        | 
        r=statement_v2 {
	        if ($r.result != null) {
	          	driver.pushRep($r.result, true);
	        }
        }
        ;
        
cfdg3
        :
        r=statement_v3 cfdg3 {
	        if ($r.result != null) {
	          	driver.pushRep($r.result, true);
	        }
        }
        |
        r=statement_v3 {
	        if ($r.result != null) {
	          	driver.pushRep($r.result, true);
	        }
        }
        ; 
        
statement_v2 returns [ASTReplacement result]
        : 
        initialization_v2
        | directive_v2  
        | inclusion_v2 { 
        	$result = null;
        }
        | eof {
        	$result = null;        	
        }
        | rule_v2
        | path_v2
        | v3clues { 
        	if (driver.getMaybeVersion().equals("CFDG2")) {
        		driver.error("Illegal mixture of old and new elements");	        		
        	} else {
        		driver.setMaybeVersion("CFDG3");
        	}
        	$result = null;
        }
        ;
        
statement_v3 returns [ASTReplacement result]
        : 
        initialization_v3
        | import_v3 { 
        	$result = null;
        }
        | eof {
        	$result = null;        	
        }
        | rule_v3
        | path
        | r=shape { 
        	$result = null;
        }
        | shape_singleton
        | d=global_definition { 
        	$result = $d.result;
        }
        | v2stuff {
        	driver.error("Illegal mixture of old and new elements");	
        	$result = null;
        }
        ;
        
v3clues
		:
        USER_STRING BECOMES
        | MODTYPE BECOMES
        | PARAM BECOMES
        | USER_STRING '('
        | USER_STRING USER_STRING '('
        | IMPORT
        | SHAPE
        | PATH USER_STRING '('
        | STARTSHAPE USER_STRING '('
        | STARTSHAPE USER_STRING '['
        | STARTSHAPE USER_ARRAYNAME '['
        ;
        
v2stuff
		:
        BACKGROUND modification_v2
        | TILE modification_v2
        | MODTYPE modification_v2
        | INCLUDE fileString
        | rule_header_v2
        ;

inclusion_v2 
        : 
        INCLUDE f=USER_QSTRING {
        	driver.setShape(null);
        	driver.includeFile($f.getText());
        }
        |
        INCLUDE f=USER_FILENAME {
        	driver.setShape(null);
        	driver.includeFile($f.getText());
        }
        ;

import_v3
        : 
        IMPORT n=fileNameSpace f=fileString {
            driver.setShape(null);
            driver.includeFile($f.result);
            if ($n.result != null) {
                driver.pushNameSpace($n.result);
            }
        }
        ;

eof 
		:
		EOF {
			if (driver.endInclude()) {
			}
		}
		;
		
fileString returns [String result]
		:
        f=USER_FILENAME {
        	$result = $f.getText();
        } 
        | 
        f=USER_QSTRING {
        	$result = $f.getText();
        } 
       	;
		
fileNameSpace returns [String result]
		:
        '@' r=USER_STRING { 
        	$result = $r.getText();
        }
        | { 
        	$result = null;
        }
        ;

initialization_v3 returns [ASTDefine result]
        : 
        STARTSHAPE s=USER_STRING p=parameter_spec m=modification {
        	String name = $s.getText();
        	ASTExpression p = $p.result;
        	ASTModification mod = $m.result;
        	driver.setShape(null);
        	ASTDefine cfg = driver.makeDefinition(ECFGParam.StartShape.getName(), false);
        	if (cfg != null) {
        		cfg.setExp(driver.makeRuleSpec(name, p, mod, true));
        	}
        	$result = cfg;
        }
        |
        STARTSHAPE s=USER_ARRAYNAME m=modification {
        	String name = $s.getText();
        	ASTModification mod = $m.result;
        	driver.setShape(null);
        	ASTDefine cfg = driver.makeDefinition(ECFGParam.StartShape.getName(), false);
        	if (cfg != null) {
        		cfg.setExp(driver.makeRuleSpec(name, null, mod, true));
        	}
        	$result = cfg;
        }
        |
        STARTSHAPE s=USER_STRING p=parameter_spec {
        	String name = $s.getText();
        	ASTExpression p = $p.result;
        	driver.setShape(null);
        	ASTDefine cfg = driver.makeDefinition(ECFGParam.StartShape.getName(), false);
        	if (cfg != null) {
        		cfg.setExp(driver.makeRuleSpec(name, p, null, true));
        	}
        	$result = cfg;
        }
        ;

initialization_v2 returns [ASTDefine result]
        : 
        STARTSHAPE s=USER_STRING {
        	String name = $s.getText();
        	driver.setShape(null);
        	ASTDefine cfg = driver.makeDefinition(ECFGParam.StartShape.getName(), false);
        	if (cfg != null) {
        		cfg.setExp(driver.makeRuleSpec(name, null, null, true));
        	}
        	$result = cfg;
        }
        ;

directive_v2 returns [ASTDefine result]
		:
        s=directive_string m=modification_v2 {
        	ASTModification mod = $m.result; 
            ASTDefine cfg = driver.makeDefinition($s.result, false);
            if (cfg != null) {
                cfg.setExp(mod);
            }
            driver.setMaybeVersion("CFDG2");
        	$result = cfg;
        }
        ;

directive_string returns [String result]
		:
        BACKGROUND { 
        	$result = ECFGParam.Background.getName();
        }
        |
        TILE { 
        	$result = ECFGParam.Tile.getName();
        }
        |
        t=MODTYPE {
        	if (EModType.size.name().equals($t.getText())) {
                $result = ECFGParam.Size.getName();
        	} else if (EModType.time.name().equals($t.getText())) {
                $result = ECFGParam.Time.getName();
        	} else {
                $result = ECFGParam.Size.getName();
                driver.error("Syntax error");
        	} 
        }
        ;
        
shape
        : 
        SHAPE s=USER_STRING parameter_list {
        	String name = $s.getText(); 
			driver.setShape(name);
        }
        ;

shape_singleton_header returns [ASTRule result]
        : 
        shape '{' {
        	driver.setInPathContainer(false);
        	$result = new ASTRule(-1);
        	driver.addRule($result);
        	driver.pushRepContainer($result.getRuleBody());
        }
        ; 

shape_singleton returns [ASTRule result]
        :
        s=shape_singleton_header buncha_elements '}' {
        	$result = $s.result;
        	driver.popRepContainer($result);
        }
        ; 

rule_header_v2 returns [ASTRule result]
        : 
        RULE s=USER_STRING {
        	String name = $s.getText();
        	driver.setShape(null);
        	$result = new ASTRule(driver.stringToShape(name, false));
        	driver.addRule($result);
        	driver.pushRepContainer($result.getRuleBody());
        }
        |
        RULE s=USER_STRING w=USER_RATIONAL {
        	String name = $s.getText();
        	String weight = $w.getText();
        	driver.setShape(null);
        	$result = new ASTRule(driver.stringToShape(name, false), Float.parseFloat(weight), weight.indexOf("\u0025") != -1);
        	driver.addRule($result);
        	driver.pushRepContainer($result.getRuleBody());
        }
        ;

rule_v2 returns [ASTRule result]
        : 
        h=rule_header_v2 '{' buncha_replacements_v2 '}' {
            driver.setMaybeVersion("CFDG2");
        	$result = $h.result;
        	driver.popRepContainer($h.result);
        }
        ;

rule_header returns [ASTRule result]
        : 
        RULE {
        	driver.setInPathContainer(false);
        	$result = new ASTRule(-1);
        	driver.addRule($result);
        	driver.pushRepContainer($result.getRuleBody());
        }
        |
        RULE w=USER_RATIONAL {
        	driver.setInPathContainer(false);
        	String weight = $w.getText();
        	$result = new ASTRule(-1, Float.parseFloat(weight), weight.indexOf("\u0025") != -1);
        	driver.addRule($result);
        	driver.pushRepContainer($result.getRuleBody());
        }
        ;

path_header returns [ASTRule result]
        : 
        PATH s=USER_STRING parameter_list {
        	String name = $s.getText();
        	driver.setShape(null);
        	driver.setInPathContainer(true);
        	$result = new ASTRule(-1);
        	$result.setPath(true);
        	driver.addRule($result);
        	driver.pushRepContainer($result.getRuleBody());
        }
        ;

rule_v3 returns [ASTRule result]
        : 
        h=rule_header '{' buncha_elements '}' {
        	$result = $h.result;
        	driver.popRepContainer($result);
        	driver.setInPathContainer(false);
        }
        ;

path returns [ASTRule result]
        : 
        h=path_header '{' buncha_elements '}' {
        	$result = $h.result;
        	driver.popRepContainer($result);
        	driver.setInPathContainer(false);
        	driver.setShape(null);
        }
        ;
        
path_header_v2 returns [ASTRule result]
        : 
        PATH s=USER_STRING {
        	String name = $s.getText();
        	driver.setShape(null);
        	$result = new ASTRule(driver.stringToShape(name, false));
        	$result.setPath(true);
        	driver.addRule($result);
        	driver.pushRepContainer($result.getRuleBody());
        	driver.setInPathContainer(true);
        }
        ;

path_v2 returns [ASTRule result]
		:
        r=path_header_v2 '{' buncha_pathOps_v2 '}' {
            $result = $r.result;
            driver.popRepContainer($result);
        }
        ;

parameter
       : 
       t=USER_STRING v=USER_STRING {
			String type = $t.getText();
			String var = $v.getText();
			driver.nextParameterDecl(type, var);
        }
        |
        SHAPE v=USER_STRING {
			String var = $v.getText();
			driver.nextParameterDecl("shape", var);
        }
        |
        v=USER_STRING MODTYPE {
        	driver.error("Reserved keyword: adjustment");
        }
        |
        SHAPE MODTYPE {
        	driver.error("Reserved keyword: adjustment");
        }
        |
        v=USER_STRING {
			String var = $v.getText();
			driver.nextParameterDecl("number", var);
        }
        |
        MODTYPE {
        	driver.error("Reserved keyword: adjustment");
        }
        ;

buncha_parameters 
        : 
        buncha_parameters ',' parameter 
        | 
        parameter
        ;

parameter_list
        : 
        '(' buncha_parameters ')' {
        }
        |
        ;

function_parameter_list
		:
        '(' buncha_parameters ')'
        | 
        '(' ')'
        ;

parameter_spec returns [ASTExpression result]
        : 
        '(' a=arglist ')' { 
        	$result = $a.result;
        }
        |
        '(' BECOMES ')' { 
        	$result = new ASTExpression(false, false, EExpType.ReuseType);
        }
        | '(' ')' { 
        	$result = null; 
        }
        | {
        	$result = null;
        }
        ;

buncha_elements 
        : 
        buncha_elements r=element {
        	driver.pushRep($r.result, false);
        }
        | 
        ;

buncha_pathOps_v2 
        : 
        buncha_pathOps_v2 r=pathOp_v2 {
        	driver.pushRep($r.result, false);
        }
        | 
        ;

pathOp_simple_v2 returns [ASTReplacement result]
        : 
        o=USER_PATHOP '{' a=buncha_adjustments '}' {
        	String pop = $o.getText();
        	ASTModification mod = $a.result;
            driver.setMaybeVersion("CFDG2");
        	$result = new ASTPathOp(pop, mod);
        }
        |
        c=shapeName m=modification_v2 {
        	String cmd = $c.result;
        	ASTModification mod = $m.result;
            driver.setMaybeVersion("CFDG2");
        	$result = new ASTPathCommand(cmd, mod, null);
        }
        ;

element_simple returns [ASTReplacement result]
        : 
        o=USER_PATHOP '(' e=exp2 ')' {
        	String pop = $o.getText();
        	ASTExpression exp = $e.result;
        	$result = new ASTPathOp(pop, exp);
        }
        |
        o=USER_PATHOP '(' ')' {
        	String operator = $o.getText();
        	$result = new ASTPathOp(operator, null);
        }
        |
        s=shapeName p=parameter_spec m=modification {
        	String cmd = $s.result;
        	ASTExpression p = $p.result;
        	ASTModification mod = $m.result;
        	$result = driver.makeElement(cmd, mod, p, false);
        }
        |
        IF '(' e=exp2 ')' m=modification {
        	ASTExpression args = $e.result;
        	ASTModification mod = $m.result;
        	args = driver.makeFunction("if", args, false);
        	$result = driver.makeElement("if", mod, args, false);
        }
        |
        h=letHeader b=letBody m=modification {
        	ASTRepContainer vars = $h.result;
        	ASTExpression exp = $b.result;
        	ASTModification mod = $m.result;
        	exp = driver.makeLet(vars, exp);
        	$result = driver.makeElement("let", mod, exp, false);
        }
        |
        PATH n=USER_STRING p=parameter_spec m=modification {
        	String cmd = $n.getText();
        	ASTExpression p = $p.result;
        	ASTModification mod = $m.result;
        	$result = driver.makeElement(cmd, mod, p, true);
        }
        ;

one_or_more_elements
        : 
        '{' buncha_elements '}' { }
        |
        r=element_simple {
        	driver.pushRep($r.result, false);
        }
        ;

one_or_more_pathOp_v2
        : 
        '{' buncha_pathOps_v2 '}' { }
        |
        r=pathOp_simple_v2 {
        	driver.pushRep($r.result, false);
        }
        ;

caseBody
        : 
        caseBody_element caseBody 
        |
        ;

caseBody_element
        : 
        h=caseHeader one_or_more_elements {
        	driver.popRepContainer(driver.getSwitchStack().lastElement());
        }
        ;

element returns [ASTReplacement result]
        : 
        r=element_simple { 
        	$result = $r.result; 
        }
        |
        definition { 
        	$result = null;
        }
        |
        rl=element_loop { 
        	$result = $rl.result; 
        	driver.popRepContainer($result);
        	if ($result.getRepType().getType() == 0) {
	        	$result = null; 
        	}
        }
        |
        rl=element_loop FINALLY {
        	driver.popRepContainer($rl.result);
        	driver.pushRepContainer(((ASTLoop) $rl.result).getFinallyBody());
        } one_or_more_elements {
        	driver.popRepContainer($result);
        	$result = $rl.result; 
        	if ($result.getRepType().getType() == 0) {
	        	$result = null; 
        	}
        }
        |
        ri=ifHeader one_or_more_elements {
        	$result = $ri.result; 
        	driver.popRepContainer($result);
        	if ($result.getRepType().getType() == 0) {
	        	$result = null; 
        	}
        }
        |
        rei=ifElseHeader one_or_more_elements {
        	$result = $rei.result; 
        	driver.popRepContainer($result);
        	if ($result.getRepType().getType() == 0) {
	        	$result = null; 
        	}
        }
        |
        rt=transHeader one_or_more_elements {
        	$result = $rt.result; 
        	driver.popRepContainer($result);
        	if ($result.getRepType().getType() == 0) {
	        	$result = null; 
        	}
        }
        |
        rs=switchHeader '{' caseBody '}' {
        	$result = $rs.result; 
			$rs.result.unify();
        	driver.getSwitchStack().pop();
        }
        |
	    element_v2clue {
            driver.error("Illegal mixture of old and new elements");
            $result = null;
        }
        ;

element_v2clue
		:
        USER_RATIONAL '*'
        | USER_STRING '{'
        | USER_PATHOP '{'
        ;
        
pathOp_v2 returns [ASTReplacement result]
        : 
        rp=pathOp_simple_v2 { 
        	$result = $rp.result;
        }
        |
        rl=loopHeader_v2 one_or_more_pathOp_v2 { 
        	$result = $rl.result;
			driver.popRepContainer($result);
			if ($result.getRepType().getType() == 0) {
				$result = null;			
			}
        }
        | pathOp_v3clues {
            if (driver.getMaybeVersion().equals("CFDG2")) {
                driver.error("Illegal mixture of old and new elements");
            } else {
                driver.setMaybeVersion("CFDG3");
            }
            $result = null;
        }
        ;

pathOp_v3clues
		:
        USER_PATHOP '('
        | USER_STRING '('
        | PATH
        | LOOP
        | USER_STRING BECOMES
        | MODTYPE BECOMES
        | IF
        | MODTYPE
        | SWITCH
        ;

element_loop returns [ASTLoop result]
        : 
        h=loopHeader m=modification one_or_more_elements {
        	$result = $h.result;
        	$result.setLoopHolder($m.result);
        }
        ;

buncha_replacements_v2 
        : 
        r=replacement_v2 buncha_replacements_v2 {
        	driver.pushRep($r.result, false);
        }
        |
        ;

one_or_more_replacements_v2
        : 
        '{' buncha_replacements_v2 '}' { 
        }
        |
        r=replacement_simple_v2 {
        	driver.pushRep($r.result, false);
        }
        ;

replacement_simple_v2 returns [ASTReplacement result]
        : 
        s=shapeName m=modification_v2 {
        	String name = $s.result;
        	ASTModification mod = $m.result;
        	ASTRuleSpecifier r = driver.makeRuleSpec(name, null);
        	$result = new ASTReplacement(r, mod);
        }
        ;

replacement_v2 returns [ASTReplacement result]
        : 
        r=replacement_simple_v2 { 
        	$result = $r.result;
        }
        |
        rl=loopHeader_v2 one_or_more_replacements_v2 {
        	$result = $rl.result;
			driver.popRepContainer($result);
			if ($result.getRepType().getType() == 0) {
	        	$result = null;			
			}
        }
        ;

loopHeader_v2 returns [ASTLoop result]
        : 
        r=USER_RATIONAL '*' { 
        	driver.incSwitchStack();
        } m=modification_v2 {
        	ASTExpression count = new ASTReal(Float.parseFloat($r.getText()));
        	ASTModification mod = $m.result;
        	driver.decSwitchStack();
            driver.setMaybeVersion("CFDG2");
            String dummyvar = "~~inaccessiblevar~~";
        	$result = new ASTLoop(driver.stringToShape(dummyvar, false), dummyvar, count, mod);
        	driver.pushRepContainer($result.getLoopBody());
        }
        ;

loopHeader returns [ASTLoop result]
        : 
        LOOP v=USER_STRING BECOMES i=exp2 {
        	String var = $v.getText();
        	ASTExpression index = $i.result;
        	$result = new ASTLoop(driver.stringToShape(var, false), var, index, null);
        	driver.pushRepContainer($result.getLoopBody());
        }
        |
        LOOP MODTYPE BECOMES c=exp2 {
        	ASTExpression index = $c.result;
            String dummyvar = "~~inaccessiblevar~~";
        	$result = new ASTLoop(driver.stringToShape(dummyvar, false), dummyvar, index, null);
        	driver.pushRepContainer($result.getLoopBody());
        }
        |
        LOOP c=exp2 {
        	ASTExpression count = $c.result;
            String dummyvar = "~~inaccessiblevar~~";
        	$result = new ASTLoop(driver.stringToShape(dummyvar, false), dummyvar, count, null);
        	driver.pushRepContainer($result.getLoopBody());
        }
        ;

ifHeader returns [ASTIf result]
        : 
        IF '(' e=exp2 ')' {
        	ASTExpression cond = $e.result;
        	$result = new ASTIf(cond);
        	driver.pushRepContainer($result.getThenBody());
        }
        ;

ifElseHeader returns [ASTIf result]
        : 
        h=ifHeader one_or_more_elements ELSE {
        	$result = $h.result;
        	driver.popRepContainer($result);
        	driver.pushRepContainer($result.getElseBody());
        }
        ;

transHeader returns [ASTTransform result]
        : 
        t=MODTYPE e=exp2 {
        	ASTExpression exp = $e.result;
        	if (!$t.getText().equals(EModType.transform.name())) {
        		driver.error("Syntax error");
        	} 
        	$result = new ASTTransform(exp);
        	driver.pushRepContainer($result.getBody());
        }
        |
        CLONE e=exp2 {
        	ASTExpression exp = $e.result;
        	$result = new ASTTransform(exp);
        	driver.pushRepContainer($result.getBody());
        	$result.setClone(true);
        }
        ;

switchHeader returns [ASTSwitch result]
        : 
        SWITCH '(' e=exp2 ')' {
        	ASTExpression caseVal = $e.result;
            $result = new ASTSwitch(caseVal);
            driver.getSwitchStack().push($result);
        }
        ;

caseHeader returns [Integer result]
        :  
        CASE e=exp2 ':' {
        	ASTExpression valExp = $e.result;
            double[] val = new double[] { 0.0 };
            try {
                if (valExp.evaluate(val, 1) != 1) {
                    driver.error("Case expression is not a single, numeric expression");
                } else {
                	int intval = (int) Math.floor(val[0]);
                	Map<Integer, ASTRepContainer> caseMap = driver.getSwitchStack().peek().getCaseStatements();
                	if (caseMap.get(intval) != null) {
                		driver.error("Case value already in use");
                		driver.pushRepContainer(caseMap.get(intval));
                	} else {
                		ASTRepContainer caseBody = new ASTRepContainer();
                		driver.pushRepContainer(caseBody);
                		caseMap.put(intval, caseBody);
                	}
                }
            }
            catch (DeferUntilRuntimeException e) {
                driver.error("Case expression is not constant");
            }
            $result = 0;
        }
        |
        ELSE ':' {
            if (!driver.getSwitchStack().peek().getElseBody().getBody().isEmpty()) {
                driver.error("There can only be one 'else:' clause");
            } else {
                driver.pushRepContainer(driver.getSwitchStack().peek().getElseBody());
            }
            $result = 0;
        }
        ;

modification_v2 returns [ASTModification result]
        : 
        '{' m=buncha_adjustments '}' {
        	$result = driver.makeModification($m.result, true);
        }
        |
        '[' m=buncha_adjustments ']' {
        	$result = driver.makeModification($m.result, false);
        }
        ;

modification returns [ASTModification result]
        : 
        '[' m=buncha_adjustments ']' {
        	$result = driver.makeModification($m.result, true);
        }
        |
        '[' '[' m=buncha_adjustments ']' ']' {
        	$result = driver.makeModification($m.result, false);
        }
        ;

buncha_adjustments returns [ASTModification result]
        : 
        a2=buncha_adjustments a1=adjustment {
        	driver.makeModTerm($a2.result, $a1.result);
        	$result = $a2.result;
        }
        | {
			$result = new ASTModification();
        }
        ;

adjustment returns [ASTModTerm result]
        : 
        t=MODTYPE el=explist {
        	$result = new ASTModTerm(EModType.modTypeByName($t.getText()), $el.result);
        }
        |
        t=MODTYPE e=exp '|' {
        	EModType type = EModType.modTypeByName($t.getText());
        	if (type.ordinal() < EModType.hue.ordinal() || type.ordinal() > EModType.alpha.ordinal()) {
        		driver.error("The target operator can only be applied to color adjustments");
        		$result = null;
        	} else {
	        	$result = new ASTModTerm(EModType.modTypeByOrdinal(type.ordinal() + 4), $e.result);
        	}
        }
        |
        PARAM p=USER_STRING {
        	$result = new ASTModTerm(EModType.param, $p.getText());
        }
        |
        PARAM p=USER_QSTRING {
        	$result = new ASTModTerm(EModType.param, $p.getText());
        }
        ;
        
letHeader returns [ASTRepContainer result]
		:
        LET {
            $result = new ASTRepContainer();
            driver.pushRepContainer($result);
        }
        ;
        
letBody returns [ASTExpression result]
		:
        '(' letVariables ';' e=exp2 ')' {
            $result = $e.result;
        }
        ;
        
letVariables
		:
        letVariables ';' letVariable
        |
        letVariable
        ;
        
letVariable returns [ASTDefine result]
		:
        r=definition {
            driver.pushRep($r.result, false);
        }
        ;
        
explist returns [ASTExpression result]
        : 
        e2=explist e1=exp {
        	$result = $e2.result.append($e1.result);
        }
        | 
        e=exp { 
        	$result = $e.result;
        }
        ;

arglist returns [ASTExpression result]
        : 
        e2=explist e1=exp3 {
        	$result = $e2.result.append($e1.result);
        }
        | 
        e=exp3 { 
        	$result = new ASTCons(new ASTParen($e.result));
        }
        ;

exp returns [ASTExpression result]
        : 
        (
        n=USER_RATIONAL { 
			$result = new ASTReal(Float.parseFloat($n.getText())); 
        }
        |
        CF_INFINITY { 
			$result = new ASTReal(Float.MAX_VALUE); 
        }
        |
        '(' x=exp2 ')' { 
			$result = new ASTParen($x.result); 
        }
        | 
        f=expfunc { 
			$result = $f.result; 
        }
        |
        '-' e=exp { 
			$result = new ASTOperator('N', $e.result); 
        }
        |
        '+' e=exp { 
			$result = new ASTOperator('P', $e.result); 
        }
        )
        (
        RANGE r=exp {
        	ASTExpression pair = $result.append($r.result);
        	$result = new ASTFunction("rand", pair, driver.getSeed());
        }
        |
        PLUSMINUS r=exp {
        	ASTExpression pair = $result.append($r.result);
        	$result = new ASTFunction("rand+/-", pair, driver.getSeed());
        }
        )?
        ;

exp2 returns [ASTExpression result]	
        : 
        (
        n=USER_RATIONAL { 
        	$result = new ASTReal(Float.parseFloat($n.getText())); 
        }
        |
        CF_INFINITY { 
			$result = new ASTReal(Float.MAX_VALUE); 
        }
        |
        f=exp2func { 
        	$result = $f.result; 
        }
        | 
        '-' e=exp2 { 
			$result = new ASTOperator('N', $e.result); 
        }
        |
        '+' e=exp2 { 
			$result = new ASTOperator('P', $e.result); 
        }
        |
        NOT e=exp2 { 
			$result = new ASTOperator('!', $e.result); 
        }
        |
        '(' e=exp2 ')' { 
			$result = new ASTParen($e.result); 
        }
        |
        m=modification {
        	$result = $m.result;
        }
        )
        (
        ',' r=exp2 {
        	$result = new ASTCons($result, $r.result);
        }
        |
        '+' r=exp2 {
        	$result = new ASTOperator('+', $result, $r.result);
        }
        |
        '-' r=exp2 {
        	$result = new ASTOperator('-', $result, $r.result);
        }
        |
        '_' r=exp2 {
        	$result = new ASTOperator('_', $result, $r.result);
        }
        |
        '*' r=exp2 {
        	$result = new ASTOperator('*', $result, $r.result);
        }
        |
        '/' r=exp2 {
        	$result = new ASTOperator('/', $result, $r.result);
        }
        |
        '^' r=exp2 {
        	$result = new ASTOperator('^', $result, $r.result);
        }
        |
        LT r=exp2 {
        	$result = new ASTOperator('<', $result, $r.result);
        }
        |
        GT r=exp2 {
        	$result = new ASTOperator('>', $result, $r.result);
        }
        |
        LE r=exp2 {
        	$result = new ASTOperator('L', $result, $r.result);
        }
        |
        GE r=exp2 {
        	$result = new ASTOperator('G', $result, $r.result);
        }
        |
        EQ r=exp2 {
        	$result = new ASTOperator('=', $result, $r.result);
        }
        |
        NEQ r=exp2 {
        	$result = new ASTOperator('n', $result, $r.result);
        }
        |
        AND r=exp2 {
        	$result = new ASTOperator('&', $result, $r.result);
        }
        |
        OR r=exp2 {
        	$result = new ASTOperator('|', $result, $r.result);
        }
        |
        XOR r=exp2 {
        	$result = new ASTOperator('X', $result, $r.result);
        }
        |
        RANGE r=exp2 {
        	ASTExpression pair = $result.append($r.result);
        	$result = new ASTFunction("rand", pair, driver.getSeed());
        }
        |
        PLUSMINUS r=exp2 {
        	ASTExpression pair = $result.append($r.result);
        	$result = new ASTFunction("rand+/-", pair, driver.getSeed());
        }
        )?
        ;

exp3 returns [ASTExpression result]	
        : 
        (
        n=USER_RATIONAL { 
        	$result = new ASTReal(Float.parseFloat($n.getText())); 
        }
        |
        CF_INFINITY { 
			$result = new ASTReal(Float.MAX_VALUE); 
        }
        |
        f=exp2func { 
        	$result = $f.result;
        }
        | 
        '-' e=exp3 { 
			$result = new ASTOperator('N', $e.result); 
        }
        |
        '+' e=exp3 { 
			$result = new ASTOperator('P', $e.result); 
        }
        |
        NOT e=exp3 { 
			$result = new ASTOperator('!', $e.result); 
        }
        |
        '(' x=exp2 ')' { 
			$result = new ASTParen($x.result); 
        }
        |
        m=modification {
        	$result = $m.result;
        }
        )
        (
        '+' r=exp3 {
        	$result = new ASTOperator('+', $result, $r.result);
        }
        |
        '-' r=exp3 {
        	$result = new ASTOperator('-', $result, $r.result);
        }
        |
        '_' r=exp3 {
        	$result = new ASTOperator('_', $result, $r.result);
        }
        |
        '*' r=exp3 {
        	$result = new ASTOperator('*', $result, $r.result);
        }
        |
        '/' r=exp3 {
        	$result = new ASTOperator('/', $result, $r.result);
        }
        |
        '^' r=exp3 {
        	$result = new ASTOperator('^', $result, $r.result);
        }
        |
        LT r=exp3 {
        	$result = new ASTOperator('<', $result, $r.result);
        }
        |
        GT r=exp3 {
        	$result = new ASTOperator('>', $result, $r.result);
        }
        |
        LE r=exp3 {
        	$result = new ASTOperator('L', $result, $r.result);
        }
        |
        GE r=exp3 {
        	$result = new ASTOperator('G', $result, $r.result);
        }
        |
        EQ r=exp3 {
        	$result = new ASTOperator('=', $result, $r.result);
        }
        |
        NEQ r=exp3 {
        	$result = new ASTOperator('n', $result, $r.result);
        }
        |
        AND r=exp3 {
        	$result = new ASTOperator('&', $result, $r.result);
        }
        |
        OR r=exp3 {
        	$result = new ASTOperator('|', $result, $r.result);
        }
        |
        XOR r=exp3 {
        	$result = new ASTOperator('X', $result, $r.result);
        }
        |
        RANGE r=exp3 {
        	ASTExpression pair = $result.append($r.result);
        	$result = new ASTFunction("rand", pair, driver.getSeed());
        }
        |
        PLUSMINUS r=exp3 {
        	ASTExpression pair = $result.append($r.result);
        	$result = new ASTFunction("rand+/-", pair, driver.getSeed());
        }
        )?
        ;

expfunc returns [ASTExpression result]
        : 
        f=USER_STRING '(' ')' { 
        	String func = $f.getText();
        	$result = driver.makeFunction(func, null);
        }
        | 
        f=USER_STRING '(' a=arglist ')' { 
        	String func = $f.getText();
        	ASTExpression args = $a.result;
        	$result = driver.makeFunction(func, args);
        }
        |
        f=USER_ARRAYNAME '(' e=exp2 ')' { 
        	String func = $f.getText();
        	ASTExpression args = $e.result;
        	$result = driver.makeArray(func, args);
        }
        |
        IF '(' e=exp2 ')' { 
        	ASTExpression args = $e.result;
        	$result = driver.makeFunction("if", args);
        }
        |
        h=letHeader b=letBody {
        	driver.popRepContainer(null);
        	$result = driver.makeLet($h.result, $b.result);
        }
        |
        v=USER_STRING { 
        	String var = $v.getText();
        	$result = driver.makeVariable(var);
        }
        ;

exp2func returns [ASTExpression result]
        : 
        f=USER_STRING '(' ')' { 
        	String func = $f.getText();
        	$result = driver.makeFunction(func, null);
        }
        | 
        f=USER_STRING '(' a=arglist ')' { 
        	String func = $f.getText();
        	ASTExpression args = $a.result;
        	$result = driver.makeFunction(func, args);
        }
        |
        f=USER_ARRAYNAME '(' e=exp2 ')' { 
        	String func = $f.getText();
        	ASTExpression args = $e.result;
        	$result = driver.makeArray(func, args);
        }
        |
        IF '(' e=exp2 ')' { 
        	ASTExpression args = $e.result;
        	$result = driver.makeFunction("if", args);
        }
        |
        f=USER_STRING '(' BECOMES ')' { 
        	String func = $f.getText();
        	ASTExpression args = new ASTExpression(false, false, EExpType.ReuseType);
        	$result = driver.makeArray(func, args);
        }
        |
        h=letHeader b=letBody {
        	driver.popRepContainer(null);
        	$result = driver.makeLet($h.result, $b.result);
        }
        |
        v=USER_STRING { 
        	String var = $v.getText();
        	$result = driver.makeVariable(var);
        }
        ;
        
shapeName returns [String result]
		:
        r=USER_STRING { 
        	$result = $r.getText();
        }
        |
        r=USER_ARRAYNAME { 
        	$result = $r.getText();
        }
        ;
        
global_definition returns [ASTDefine result]
		:
        r=global_definition_header e=exp2 {
            ASTDefine var = $r.result;
            ASTExpression exp = $e.result;
            if (var != null) {
                switch (var.getDefineType()) {
                    case StackDefine:
                        if (exp instanceof ASTModification) {
                        	ASTModification mod = (ASTModification)exp;
                            var.getChildChange().grab(mod); // emptied ASTmod gets deleted
                        } else {
                            var.setExp(exp);
                        }
                        break;
                    case LetDefine:
                        assert(false);
                        break;
                    case FunctionDefine:
                        driver.popRepContainer(null);
                        driver.getParamDecls().getParameters().clear();
                        driver.getParamDecls().setStackCount(0);
                        // fall through
                    default:
                        var.setExp(exp);
                        break;
                }
                $result = var;
            } else {
                $result = null;
            }
        }
        ;

function_definition_header returns [ASTDefine result]
		:
        SHAPE f=USER_STRING p=function_parameter_list BECOMES {
        	String name = $f.getText();
            $result = driver.makeDefinition(name, true);
            if ($result != null) {
                $result.setExpType(EExpType.RuleType);
                $result.setTupleSize(1);
            }
        }
        |
        f=USER_STRING p=function_parameter_list BECOMES {
        	String name = $f.getText();
            $result = driver.makeDefinition(name, true);
            if ($result != null) {
                $result.setExpType(EExpType.NumericType);
                $result.setTupleSize(1);
            }
        }
        |
        t=USER_STRING f=USER_STRING p=function_parameter_list BECOMES {
        	String name = $f.getText();
        	String type = $f.getText();
            $result = driver.makeDefinition(name, true);
            if ($result != null) {
            	int[] tupleSize = new int[1];
            	boolean[] isNatural = new boolean[1];
                $result.setExpType(driver.decodeType(type, tupleSize, isNatural));
                $result.setTupleSize(tupleSize[0]);
                $result.setIsNatural(isNatural[0]); 
            }
        }
        |
        SHAPE MODTYPE p=function_parameter_list BECOMES {
            driver.error("Reserved keyword: adjustment");
            $result = null;
        }
        |
        MODTYPE p=function_parameter_list BECOMES {
            driver.error("Reserved keyword: adjustment");
            $result = null;
        }
        |
        t=USER_STRING MODTYPE p=function_parameter_list BECOMES {
            driver.error("Reserved keyword: adjustment");
            $result = null;
        }
        ;

global_definition_header returns [ASTDefine result]
		:
        fd=function_definition_header {
            if ($fd.result != null) {
                assert($fd.result.getDefineType() == EDefineType.FunctionDefine);
                driver.pushRepContainer(driver.getParamDecls());
            } else {
                // An error occurred
                driver.getParamDecls().getParameters().clear();
                driver.getParamDecls().setStackCount(0);
            }
            $result = $fd.result;
        }
        |
       	d=definition_header {
            $result = $d.result;
        }
        ;

definition_header returns [ASTDefine result]
		:
        n=USER_STRING BECOMES {
        	String name = $n.getText();
            $result = driver.makeDefinition(name, false);
        }
        | MODTYPE BECOMES {
            driver.error("Reserved keyword: adjustment");
            $result = null;
        }
        ;

definition returns [ASTDefine result]
        :
        d=definition_header e=exp2 { 
        	ASTDefine var = $d.result;
        	ASTExpression exp = $e.result;
        	if (var != null) {
        		if (exp instanceof ASTModification) {
        			ASTModification mod = (ASTModification)exp;
        			mod.getModData().setSeed(0); 
        			var.getChildChange().grab(mod);
        		} else {
        			var.setExp(exp);
        		}
        		$result = var;
        	} else {
        		$result = null;        		
        	}
        }
        ;
	
CFDG2
	: 
	'CFDG2' 
	;
	
CFDG3
	: 
	'CFDG3' 
	;
	
USER_RATIONAL
	: 
	('0'..'9')+ '.' ('0'..'9')* '%'? | '.' ('0'..'9')+ '%'? | '0'..'9'+ '%'?
	;

STARTSHAPE
	: 
	'startshape' 
	;

BACKGROUND
	: 
	'background' 
	;

INCLUDE
	: 
	'include' 
	;

IMPORT
	: 
	'import' 
	;

TILE
	: 
	'tile' 
	;

RULE
	: 
	'rule' 
	;

PATH
	: 
	'path' 
	;

SHAPE
	: 
	'shape' 
	;

LOOP
	: 
	'loop' 
	;

FINALLY
	: 
	'finally' 
	;

IF
	: 
	'if' 
	;

ELSE
	: 
	'else' 
	;

SWITCH
	: 
	'switch' 
	;

CASE
	: 
	'case' 
	;

RANGE
	: 
	'..' 
	;

PLUSMINUS
	: 
	'+/-' | '\u00b1'
	;

TIME
	: 
	'time' 
	;

TIMESCALE
	: 
	'timescale' 
	;

X
	: 
	'x' 
	;

Y
	: 
	'y' 
	;

Z
	: 
	'z' 
	;
	
ROTATE
	: 
	'rotate' | 'r' 
	;

SIZE
	: 
	'size' | 's' 
	;
	
SKEW
	: 
	'skew' 
	;

FLIP
	: 
	'flip' | 'f' 
	;

HUE
	: 
	'hue' | 'h' 
	;

SATURATION
	: 
	'saturation' | 'sat'
	;

BRIGHTNESS
	: 
	'brightness' | 'b' 
	;

ALPHA
	: 
	'alpha' | 'a' 
	;

TARGETHUE
	: 
	'|hue' | '|h' 
	;

TARGETSATURATION
	: 
	'|saturation' | '|sat' 
	;

TARGETBRIGHTNESS
	: 
	'|brightness' | '|b' 
	;

TARGETALPHA
	: 
	'|alpha' | '|a' 
	;

X1
	: 
	'x1' 
	;

X2
	: 
	'x2' 
	;

Y1
	: 
	'y1' 
	;

Y2
	: 
	'y2' 
	;

RX
	: 
	'rx' 
	;

RY
	: 
	'ry' 
	;

WIDTH
	: 
	'width' 
	;

TRANSFORM
	: 
	'transform' | 'trans' 
	;

PARAM
	: 
	'param' | 'p' 
	;
	
BECOMES
	: 
	'=' 
	;

LT
	: 
	'<' 
	;

GT
	: 
	'>' 
	;

LE
	: 
	'<=' | '\u2264'
	;

GE
	: 
	'>=' | '\u2265' 
	;

EQ
	: 
	'==' 
	;

NEQ
	: 
	'<>' | '\u2276'
	;

NOT
	: 
	'!' 
	;

AND
	: 
	'&&' 
	;

OR
	: 
	'||' 
	;

XOR
	: 
	'^^' 
	;

CF_INFINITY
	: 
	'CF_INFINITY' 
	;
	
USER_PATHOP
	: 
	'MOVETO'
	| 
	'LINETO'
	| 
	'ARCTO'
	| 
	'CURVETO'
	| 
	'MOVEREL'
	| 
	'LINEREL'
	| 
	'ARCREL'
	| 
	'CURVEREL'
	| 
	'CLOSEPOLY' 
	;

CLONE 
	:
	'CLONE'
	;

LET 
	:
	'LET'
	;
	
MODTYPE
	:
	TIME | TIMESCALE | X | Y | Z | ROTATE | SIZE | SKEW | FLIP | HUE | SATURATION | BRIGHTNESS | ALPHA | TARGETHUE | TARGETSATURATION | TARGETBRIGHTNESS | TARGETALPHA | X1 | X2 | Y1 | Y2 | RX | RY | WIDTH
	;
		
USER_STRING 
	: 
	('a'..'z'|'A'..'Z'|'_'|'\u0200'..'\u0301'|'\u0303'..'\u0377') (('a'..'z'|'A'..'Z'|'0'..'9'|'_'|'\u0200'..'\u0301'|'\u0303'..'\u0377')|('\u0302'('\u0200'..'\u0260'|'\u0262'..'\u0377')))* 
	;

USER_QSTRING	
	:	
	'"' USER_STRING '"' 
	;
	
USER_FILENAME 
	: 
	('a'..'z'|'A'..'Z'|'\u0200'..'\u0377') ('a'..'z'|'A'..'Z'|'0'..'9'|'_'|'\u0200'..'\u0377'|'.')* '.cfdg' 
	;

USER_ARRAYNAME 
	: 
	('a'..'z'|'A'..'Z'|'_'|'\u0200'..'\u0301'|'\u0303'..'\u0377') (('a'..'z'|'A'..'Z'|'0'..'9'|'_'|'\u0200'..'\u0301'|'\u0303'..'\u0377')|('\u0302'('\u0200'..'\u0260'|'\u0262'..'\u0377')))* 
	;

COMMENT
	: 
	('//' ~('\n'|'\r')* '\r'? '\n' {} | '/*' (.)*? '*/' {}) -> skip 
	;

WHITESPACE  
	: 
	( ' ' | '\t' | '\r' | '\n' ) -> skip 
	;
	