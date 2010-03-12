/*
 * $Id:$
 *
 */
package ${element.configElementPackageName};

<#if element.simpleElement>
<#list imports as import>
import ${import};
</#list>

public class ${element.configElementClassName}NodeValue extends NodeValue<${element.valueClassName}> {
	private static final long serialVersionUID = 1L;

	/**
	 * @param value
	 */
	public ${element.configElementClassName}NodeValue(final ${element.valueClassName} value) {
		super(value);
	}

	/**
	 * @see net.sf.jame.core.tree.NodeValue#getValueClone()
	 */
	@Override
	public ${element.valueClassName} getValueClone() {
		return getValue();
	}

	/**
	 * @see net.sf.jame.core.tree.NodeValue#clone()
	 */
	@Override
	public ${element.configElementClassName}NodeValue clone() {
		return new ${element.configElementClassName}NodeValue(getValueClone());
	}
}
<#elseif element.complexElement>
<#list imports as import>
import ${import};
</#list>

public class ${element.configElementClassName}NodeValue extends NodeValue<${element.configElementClassName}> {
	private static final long serialVersionUID = 1L;

	/**
	 * @param value
	 */
	public ${element.configElementClassName}NodeValue(final ${element.configElementClassName} value) {
		super(value);
	}

	/**
	 * @see net.sf.jame.core.tree.NodeValue#getValueClone()
	 */
	@Override
	public ${element.configElementClassName} getValueClone() {
		if (getValue() != null) {
			return getValue().clone();
		}
		return null;
	}

	/**
	 * @see net.sf.jame.core.tree.NodeValue#clone()
	 */
	@Override
	public ${element.configElementClassName}NodeValue clone() {
		return new ${element.configElementClassName}NodeValue(getValueClone());
	}
}
</#if>