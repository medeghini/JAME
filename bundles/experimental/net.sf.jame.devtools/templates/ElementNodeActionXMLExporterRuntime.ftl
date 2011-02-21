/*
 * $Id:$
 *
 */
package ${nodeActionXMLExporterPackageName};

<#list imports as import>
import ${import};
</#list>
/**
 * @author ${author}
 */
public class ${element.elementName?cap_first}ElementNodeActionXMLExporterRuntime extends ConfigElementNodeActionXMLExporterRuntime<${element.configElementClassName}> {
	/**
	 * @see net.sf.jame.core.util.ConfigElementNodeActionXMLExporterRuntime#createExporter()
	 */
	@Override
	protected ${element.configElementClassName}XMLExporter createExporter() {
		return new ${element.configElementClassName}XMLExporter();
	}
}
 