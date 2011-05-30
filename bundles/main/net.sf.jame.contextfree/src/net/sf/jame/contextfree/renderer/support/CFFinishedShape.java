package net.sf.jame.contextfree.renderer.support;

import java.awt.Graphics2D;


public class CFFinishedShape implements Cloneable, Comparable<CFFinishedShape> {
	private CFPath path;
	private CFPathAttribute attribute;
	private double cumulativeArea;

	public CFFinishedShape(CFPath path, CFPathAttribute attribute) {
		this(path, attribute, 0);
	}

	public CFFinishedShape(CFPath path, CFPathAttribute attribute, double cumulativeArea) {
		this.path = path;
		this.attribute = attribute;
		this.cumulativeArea = cumulativeArea;
	}

	public double getCumulativeArea() {
		return cumulativeArea;
	}
	
	@Override
	public CFFinishedShape clone() {
		return new CFFinishedShape(path.clone(), attribute.clone(), cumulativeArea);
	}

	public int compareTo(CFFinishedShape s) {
		if (attribute.getModification().getZ() == s.attribute.getModification().getZ()) {
			return (cumulativeArea - s.cumulativeArea) < 0 ? -1 : 1;
		}
		return (attribute.getModification().getZ() - s.attribute.getModification().getZ()) < 0 ? -1 : 1;
	}

	@Override
	public String toString() {
		return "CFFinishedShape [path=" + path + ", attribute=" + attribute + ", cumulativeArea=" + cumulativeArea + "]";
	}

	public void render(Graphics2D g2d) {
		path.render(g2d, attribute);
	}

	public double area() {
		return attribute.getModification().getTransform().getDeterminant();
	}
}
