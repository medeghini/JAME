/*
 * $Id:$
 *
 */
package net.sf.jame.contextfree.cfdg.pathReplacement.extension;

import net.sf.jame.contextfree.renderer.ContextFreeContext;
import net.sf.jame.core.extension.ConfigurableExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public abstract class PathReplacementExtensionRuntime<T extends PathReplacementExtensionConfig> extends ConfigurableExtensionRuntime<T> {
	/**
	 * @param context
	 */
	public abstract void process(ContextFreeContext context);
}
