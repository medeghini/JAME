package net.sf.jame.contextfree.renderer.support;

import java.util.Comparator;

public class CFRuleSpecifierComparator implements Comparator<CFRuleSpecifier> {
	public int compare(CFRuleSpecifier o1, CFRuleSpecifier o2) {
		return o1.compareTo(o2);
	}
}
