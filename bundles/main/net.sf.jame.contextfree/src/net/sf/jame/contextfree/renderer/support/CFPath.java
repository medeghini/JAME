package net.sf.jame.contextfree.renderer.support;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Double;

public class CFPath implements Cloneable {
	private ExtendedGeneralPath path;
	private float x1 = 0;
	private float y1 = 0;
	private float x = 0;
	private float y = 0;
	
	public void moveTo(float x, float y) {
		ExtendedGeneralPath path = generalPath();
		this.x = x;
		this.y = y;
		this.x1 = x;
		this.y1 = y;
		path.moveTo(x, y);
	}

	public void lineTo(float x, float y) {
		ExtendedGeneralPath path = generalPath();
		this.x = x;
		this.y = y;
		this.x1 = x;
		this.y1 = y;
		path.lineTo(x, y);
	}

	public void arcTo(float x, float y, float rx, float ry, float angle, boolean largeArcFlag, boolean sweepFlag) {
		ExtendedGeneralPath path = generalPath();
		this.x = x;
		this.y = y;
		this.x1 = x;
		this.y1 = y;
		path.arcTo(rx, ry, angle, largeArcFlag, sweepFlag, x, y);
	}
	
	public void quadTo(float x, float y, float x1, float y1) {
		ExtendedGeneralPath path = generalPath();
		this.x = x;
		this.y = y;
		this.x1 = x1;
		this.y1 = y1;
		path.quadTo(x1, y1, x, y);
	}

	public void quadTo(float x, float y) {
		ExtendedGeneralPath path = generalPath();
		this.x = x;
		this.y = y;
		this.x1 = x + x - x1;
		this.y1 = y + y - y1;
		path.quadTo(x1, y1, x, y);
	}
	
	public void curveTo(float x, float y, float x1, float y1, float x2, float y2) {
		ExtendedGeneralPath path = generalPath();
		this.x = x;
		this.y = y;
		this.x1 = x1;
		this.y1 = y1;
		path.curveTo(x1, y1, x2, y2, x, y);
	}
	
	public void curveTo(float x, float y, float x2, float y2) {
		ExtendedGeneralPath path = generalPath();
		this.x = x;
		this.y = y;
		this.x1 = x + x - x1;
		this.y1 = y + y - y1;
		path.curveTo(x1, y1, x2, y2, x, y);
	}
	
	public void moveRel(float x, float y) {
		ExtendedGeneralPath path = generalPath();
		this.x += x;
		this.y += y;
		x = this.x;
		y = this.y;
		this.x1 = x;
		this.y1 = y;
		path.moveTo(x, y);
	}

	public void lineRel(float x, float y) {
		ExtendedGeneralPath path = generalPath();
		this.x += x;
		this.y += y;
		x = this.x;
		y = this.y;
		this.x1 = x;
		this.y1 = y;
		path.lineTo(x, y);
	}
	
	public void arcRel(float x, float y, float rx, float ry, float angle, boolean largeArcFlag, boolean sweepFlag) {
		ExtendedGeneralPath path = generalPath();
		this.x += x;
		this.y += y;
		x = this.x;
		y = this.y;
		this.x1 = x;
		this.y1 = y;
		path.arcTo(rx, ry, angle, largeArcFlag, sweepFlag, x, y);
	}
	
	public void quadRel(float x, float y, float x1, float y1) {
		ExtendedGeneralPath path = generalPath();
		this.x += x;
		this.y += y;
		x = this.x;
		y = this.y;
		this.x1 = x1;
		this.y1 = y1;
		path.quadTo(x1, y1, x, y);
	}

	public void quadRel(float x, float y) {
		ExtendedGeneralPath path = generalPath();
		this.x += x;
		this.y += y;
		x = this.x;
		y = this.y;
		this.x1 = x + x - x1;
		this.y1 = y + y - y1;
		path.quadTo(x1, y1, x, y);
	}

	public void curveRel(float x, float y, float x1, float y1, float x2, float y2) {
		ExtendedGeneralPath path = generalPath();
		this.x += x;
		this.y += y;
		x = this.x;
		y = this.y;
		this.x1 = x1;
		this.y1 = y1;
		path.curveTo(x1, y1, x2, y2, x, y);
	}
	
	public void curveRel(float x, float y, float x2, float y2) {
		ExtendedGeneralPath path = generalPath();
		this.x += x;
		this.y += y;
		x = this.x;
		y = this.y;
		this.x1 = x + x - x1;
		this.y1 = y + y - y1;
		path.curveTo(x1, y1, x2, y2, x, y);
	}
	
	public void circle() {
		ExtendedGeneralPath path = generalPath();
		path.append(new Ellipse2D.Float(x - 0.5f, y - 0.5f, 1f, 1f), true);
	}

	public void closePath(Boolean align) {
		ExtendedGeneralPath path = generalPath();
		path.closePath();
	}

	public void bounds(CFBounds bounds) {
		ExtendedGeneralPath path = generalPath();
		Rectangle2D pathBounds = path.getBounds2D();
		bounds.setMinX(pathBounds.getMinX());
		bounds.setMinY(pathBounds.getMinY());
		bounds.setMaxX(pathBounds.getMaxX());
		bounds.setMaxY(pathBounds.getMaxY());
	}

	private ExtendedGeneralPath generalPath() {
		if (path == null) {
			path = new ExtendedGeneralPath();
			path.moveTo(x, y);
		}
		return path;
	}

	@Override
	public CFPath clone() {
		CFPath p = new CFPath();
		p.path = (ExtendedGeneralPath) path.clone();
		p.x = x;
		p.y = y;
		p.x1 = x1;
		p.y1 = y1;
		return p;
	}

	public Rectangle2D getBounds(AffineTransform transform, float scale) {
		AffineTransform t = new AffineTransform();
		t.concatenate(transform);
		t.scale(scale, scale);
		Shape shape = path.createTransformedShape(transform);
		return shape.getBounds2D();
	}
	
	public String toString() {
		ExtendedGeneralPath path = generalPath();
		return "CFPath [bounds=" + path.getBounds2D().toString() + "]";
	}

	public void render(Graphics2D g2d, CFPathAttribute attribute, Point2D.Double point) {
		CFColor c = attribute.getModification().getColor();
		Color color = Color.getHSBColor(c.getHue() / 360, c.getSaturation(), c.getBrightness());
		Composite composite = AlphaComposite.Src.derive(c.getAlpha());
		AffineTransform tmpTransform = g2d.getTransform();
		Composite tmpComposite = g2d.getComposite();
		Color tmpColor = g2d.getColor();
		g2d.setComposite(composite);
		g2d.setColor(color);
		if (point != null) {
			g2d.translate(point.getX(), point.getY());
		}
		g2d.transform(attribute.getModification().getTransform());
		if (attribute.getCommand().equals(CFPathCommand.FILL)) {
			path.setWindingRule(attribute.getWindingRule());
			g2d.fill(path);
		} else {
			g2d.draw(path);
		}
		g2d.setTransform(tmpTransform);
		g2d.setComposite(tmpComposite);
		g2d.setColor(tmpColor);
	}
}
