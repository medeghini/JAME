/*
 * $Id:$
 *
 */
package net.sf.jame.contextfree.extensions.figure;

import net.sf.jame.contextfree.cfdg.figure.extension.FigureExtensionRuntime;
import net.sf.jame.contextfree.renderer.ContextFreeBounds;
import net.sf.jame.contextfree.renderer.ContextFreeContext;
import net.sf.jame.contextfree.renderer.ContextFreePath;
import net.sf.jame.contextfree.renderer.ContextFreeShape;
import net.sf.jame.contextfree.renderer.ContextFreeState;
import net.sf.jame.contextfree.renderer.support.SolidPathShape;

/**
 * @author Andrea Medeghini
 */
public class CircleFigureRuntime extends FigureExtensionRuntime<CircleFigureConfig> implements ContextFreePath {
	@Override
	public void register(ContextFreeContext contextFreeContext) {
		contextFreeContext.registerPath(this);
	}

	public String getName() {
		return "CIRCLE";
	}

	public ContextFreeShape createShape(ContextFreeContext context, ContextFreeState state, ContextFreeBounds globalBounds, ContextFreeBounds shapeBounds) {
		float[] p = new float[] { -0.5f, -0.5f, +0.5f, +0.5f };
		float[] q = new float[p.length];
		state.transform(p, q);
		for (int i = 0; i < q.length; i += 2) {
			float x = q[i + 0];
			float y = q[i + 1];
			shapeBounds.addPoint(x, y);
			globalBounds.addPoint(x, y);
		}
		state.circle();
		return new SolidPathShape(state, "even-odd");
	}
}
