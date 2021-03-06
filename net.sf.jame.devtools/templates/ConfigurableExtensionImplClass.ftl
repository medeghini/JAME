package ${packageName};

import ${extensionPackage}.${extensionType};
import ${runtimePackage}.${runtimeType};
import ${configPackage}.${configType};

/**
 * @author Andrea Medeghini
 */
public class ${className} extends ${extensionType} {
	/**
	 * Returns the extensionId.
	 * 
	 * @return the extensionId.
	 */
	public String getExtensionId() {
		return "${extensionId}";
	}

	/**
	 * Returns the extensionName.
	 * 
	 * @return the extensionName.
	 */
	public String getExtensionName() {
		return "${extensionName}";
	}

	/**
	 * Returns the extensionRuntimeClass.
	 * 
	 * @return the extensionRuntimeClass.
	 */
	public ${runtimeType} getExtensionRuntime() {
		return new ${runtimeType}();
	}

	/**
	 * Returns the extensionConfigClass.
	 * 
	 * @return the extensionConfigClass.
	 */
	public ${configType} getExtensionConfig() {
		return new ${configType}();
	}
}
