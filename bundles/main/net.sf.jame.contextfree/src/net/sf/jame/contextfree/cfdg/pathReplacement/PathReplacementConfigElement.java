/*
 * $Id:$
 *
 */
package net.sf.jame.contextfree.cfdg.pathReplacement;

import net.sf.jame.contextfree.CFDGBuilder;
import net.sf.jame.contextfree.cfdg.pathReplacement.extension.PathReplacementExtensionConfig;
import net.sf.jame.core.common.ConfigurableExtensionReferenceElement;
import net.sf.jame.core.config.AbstractConfigElement;
import net.sf.jame.core.config.ConfigContext;
import net.sf.jame.core.config.ConfigElement;
import net.sf.jame.core.extension.ConfigurableExtensionReference;

/**
 * @author Andrea Medeghini
 */
public class PathReplacementConfigElement extends AbstractConfigElement {
	private static final long serialVersionUID = 1L;
	public static final String CLASS_ID = "PathReplacement";
	private final ConfigurableExtensionReferenceElement<PathReplacementExtensionConfig> extensionElement = new ConfigurableExtensionReferenceElement<PathReplacementExtensionConfig>();

	/**
	 * Constructs a new element.
	 */
	public PathReplacementConfigElement() {
		super(PathReplacementConfigElement.CLASS_ID);
	}

	/**
	 * @see net.sf.jame.core.config.AbstractConfigElement#setContext(net.sf.jame.core.config.ConfigContext)
	 */
	@Override
	public void setContext(final ConfigContext context) {
		super.setContext(context);
		extensionElement.setContext(context);
	}

	/**
	 * @return
	 */
	@Override
	public PathReplacementConfigElement clone() {
		final PathReplacementConfigElement element = new PathReplacementConfigElement();
		if (getExtensionReference() != null) {
			element.setExtensionReference(getExtensionReference().clone());
		}
		return element;
	}
	
	/**
	 *
	 */
	public void copyFrom(ConfigElement source) {
		PathReplacementConfigElement pathReplacementElement = (PathReplacementConfigElement) source;
		if (pathReplacementElement.getExtensionReference() != null) {
			setExtensionReference(pathReplacementElement.getExtensionReference().clone());
		}
	}

	/**
	 * @return
	 */
	public ConfigurableExtensionReferenceElement<PathReplacementExtensionConfig> getExtensionElement() {
		return extensionElement;
	}
	
	/**
	 * @return
	 */
	public ConfigurableExtensionReference<PathReplacementExtensionConfig> getExtensionReference() {
		return extensionElement.getReference();
	}

	/**
	 * @param reference
	 */
	public void setExtensionReference(final ConfigurableExtensionReference<PathReplacementExtensionConfig> reference) {
		extensionElement.setReference(reference);
	}
		
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		final PathReplacementConfigElement other = (PathReplacementConfigElement) obj;
		if (extensionElement == null) {
			if (other.extensionElement != null) {
				return false;
			}
		}
		else if (!extensionElement.equals(other.extensionElement)) {
			return false;
		}
		return true;
	}

	/**
	 * @see net.sf.jame.core.config.AbstractConfigElement#dispose()
	 */
	@Override
	public void dispose() {
		extensionElement.dispose();
		super.dispose();
	}

	public void toCFDG(CFDGBuilder builder) {
		if (extensionElement.getReference() != null) {
			if (extensionElement.getReference().getExtensionConfig() != null) {
				extensionElement.getReference().getExtensionConfig().toCFDG(builder);
			}
		}
	}
}
