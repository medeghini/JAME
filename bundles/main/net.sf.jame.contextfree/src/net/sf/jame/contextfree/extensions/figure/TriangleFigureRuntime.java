/*
 * $Id:$
 *
 */
package net.sf.jame.contextfree.extensions.figure;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;

import net.sf.jame.contextfree.cfdg.figure.extension.FigureExtensionRuntime;
import net.sf.jame.contextfree.renderer.ContextFreeArea;
import net.sf.jame.contextfree.renderer.ContextFreeContext;
import net.sf.jame.contextfree.renderer.ContextFreeLimits;
import net.sf.jame.contextfree.renderer.ContextFreeNode;
import net.sf.jame.contextfree.renderer.ContextFreePath;
import net.sf.jame.contextfree.renderer.ContextFreeState;

/**
 * @author Andrea Medeghini
 */
public class TriangleFigureRuntime<T extends TriangleFigureConfig> extends FigureExtensionRuntime<T> implements ContextFreePath {
	@Override
	public void register(ContextFreeContext contextFreeContext) {
		contextFreeContext.registerPath(this);
	}

	public String getName() {
		return "triangle";
	}

	public ContextFreeNode buildNode(ContextFreeContext context, ContextFreeState state, ContextFreeLimits limits) {
		return new FigureContextFreeNode(context, state, limits);
	}
	
	private class FigureContextFreeNode extends ContextFreeNode {
		private AlphaComposite a; 
		private Color c;
		private float[] p; 
		private float[] q;
		
		public FigureContextFreeNode(ContextFreeContext context, ContextFreeState state, ContextFreeLimits limits) {
			a = AlphaComposite.Src.derive(state.getCurrentAlpha());
			c = Color.getHSBColor(state.getCurrentHue(), state.getCurrentSaturation(), state.getCurrentBrightness());
			p = new float[] { -0.5f, (float) (-0.5 * Math.tan(Math.PI / 6.0)), +0.5f, (float) (-0.5 * Math.tan(Math.PI / 6.0)), 0, (float) (0.5 * Math.tan(Math.PI / 3.0) - 0.5 * Math.tan(Math.PI / 6.0)) };
			q = new float[p.length];
			AffineTransform t = new AffineTransform();
			t.translate(state.getX(), state.getY());
			t.rotate(state.getRotation());
			t.shear(state.getSkewX(), state.getSkewY());
			t.scale((state.getFlipX() < 0 ? -1 : +1) * state.getSizeX(), (state.getFlipY() < 0 ? -1 : +1) * state.getSizeY());
			t.transform(p, 0, q, 0, p.length / 2);
			for (int i = 0; i < q.length; i += 2) {
				limits.addPoint(q[i + 0], q[i + 1]);
			}
		}

		@Override
		public void drawNode(Graphics2D g2d, ContextFreeArea area) {
			g2d.setComposite(a);
			g2d.setColor(c);
			float sx = area.getScaleX();
			float sy = area.getScaleY();
			float tx = area.getX();
			float ty = area.getY();
			float px;
			float py;
			float qx;
			float qy;
			float gx;
			float gy;
			GeneralPath path = new GeneralPath();
			gx = px = tx + q[0] * sx;
			gy = py = ty + q[1] * sy;
			for (int i = 2; i < q.length; i += 2) {
				qx = tx + q[i + 0] * sx;
				qy = ty + q[i + 1] * sy;
				path.append(new Line2D.Float(px, py, qx, qy), true);
				px = qx;
				py = qy;
			}
			path.append(new Line2D.Float(px, py, gx, gy), true);
			g2d.fill(path);	
			g2d.draw(path);	
		}
	}
}