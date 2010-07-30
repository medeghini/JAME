/*
 * JAME 6.1 
 * http://jame.sourceforge.net
 *
 * Copyright 2001, 2010 Andrea Medeghini
 * http://andreamedeghini.users.sourceforge.net
 *
 * This file is part of JAME.
 *
 * JAME is an application for creating fractals and other graphics artifacts.
 *
 * JAME is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JAME is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JAME.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package net.sf.jame.contextfree.devtools;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.jame.core.extension.Extension;
import net.sf.jame.core.extension.ExtensionException;
import net.sf.jame.devtools.DevToolsRegistry;
import net.sf.jame.devtools.ProcessorCardinality;
import net.sf.jame.devtools.ProcessorDescriptor;
import net.sf.jame.devtools.ProcessorParameters;
import net.sf.jame.devtools.extension.DescriptorExtensionRuntime;
import net.sf.jame.devtools.extension.ProcessorExtensionRuntime;

import org.junit.Test;

public class ContextFree {
	private ProcessorParameters createCFDGParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("String").createDescriptor("startshape", "\"\"", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("x", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("y", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("width", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("height", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("tileWidth", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("tileHeight", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Color").createDescriptor("background", "new Color32bit(0xFF000000)", ProcessorCardinality.NONE));
		descriptors.add(new ProcessorDescriptor("figure", "Figure", "Figure", "net.sf.jame.contextfree.cfdg.figure", "FigureConfigElement", "net.sf.jame.contextfree.cfdg.figure", "FigureRuntimeElement", null, null, null, null, null, null, null, null, "net.sf.jame.contextfree", "ContextFreeResources", null, null, null, "get", "set", ProcessorCardinality.MANY));
		return new ProcessorParameters(new ProcessorDescriptor("cfdg", "CFDG", "CFDG", "net.sf.jame.contextfree.cfdg", "CFDGConfigElement", "net.sf.jame.contextfree.cfdg", "CFDGRuntimeElement", null, null, null, null, null, null, null, null, "net.sf.jame.contextfree", "ContextFreeResources", null, null, null, "get", "set", ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createFigureParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(new ProcessorDescriptor("extension", "Figure", "Figure", "net.sf.jame.core.common", "ConfigurableExtensionReferenceElement", null, null, "net.sf.jame.contextfree.cfdg.figure.extension", "FigureExtensionConfig", "net.sf.jame.contextfree.cfdg.figure.extension", "FigureExtensionRuntime", null, null, "net.sf.jame.contextfree", "ContextFreeRegistry", null, null, null, null, null, "get", "set", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("figure", "Figure", "Figure", "net.sf.jame.contextfree.cfdg.figure", "FigureConfigElement", "net.sf.jame.contextfree.cfdg.figure", "FigureRuntimeElement", null, null, null, null, null, null, null, null, "net.sf.jame.contextfree", "ContextFreeResources", null, null, null, "get", "set", ProcessorCardinality.NONE), descriptors);
	}
	
	private ProcessorParameters createShapeAdjustmentParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(new ProcessorDescriptor("extension", "ShapeAdjustment", "ShapeAdjustment", "net.sf.jame.core.common", "ConfigurableExtensionReferenceElement", null, null, "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension", "ShapeAdjustmentExtensionConfig", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension", "ShapeAdjustmentExtensionRuntime", null, null, "net.sf.jame.contextfree", "ContextFreeRegistry", null, null, null, null, null, "get", "set", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("shapeAdjustment", "ShapeAdjustment", "ShapeAdjustment", "net.sf.jame.contextfree.cfdg.shapeAdjustment", "ShapeAdjustmentConfigElement", "net.sf.jame.contextfree.cfdg.shapeAdjustment", "ShapeAdjustmentRuntimeElement", null, null, null, null, null, null, null, null, "net.sf.jame.contextfree", "ContextFreeResources", null, null, null, "get", "set", ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createShapeReplacementParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(new ProcessorDescriptor("extension", "ShapeReplacement", "ShapeReplacement", "net.sf.jame.core.common", "ConfigurableExtensionReferenceElement", null, null, "net.sf.jame.contextfree.cfdg.shapeReplacement.extension", "ShapeReplacementExtensionConfig", "net.sf.jame.contextfree.cfdg.shapeReplacement.extension", "ShapeReplacementExtensionRuntime", null, null, "net.sf.jame.contextfree", "ContextFreeRegistry", null, null, null, null, null, "get", "set", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("shapeReplacement", "ShapeReplacement", "ShapeReplacement", "net.sf.jame.contextfree.cfdg.shapeReplacement", "ShapeReplacementConfigElement", "net.sf.jame.contextfree.cfdg.shapeReplacement", "ShapeReplacementRuntimeElement", null, null, null, null, null, null, null, null, "net.sf.jame.contextfree", "ContextFreeResources", null, null, null, "get", "set", ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createPathAdjustmentParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(new ProcessorDescriptor("extension", "PathAdjustment", "PathAdjustment", "net.sf.jame.core.common", "ConfigurableExtensionReferenceElement", null, null, "net.sf.jame.contextfree.cfdg.pathAdjustment.extension", "PathAdjustmentExtensionConfig", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension", "PathAdjustmentExtensionRuntime", null, null, "net.sf.jame.contextfree", "ContextFreeRegistry", null, null, null, null, null, "get", "set", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("pathAdjustment", "PathAdjustment", "PathAdjustment", "net.sf.jame.contextfree.cfdg.pathAdjustment", "PathAdjustmentConfigElement", "net.sf.jame.contextfree.cfdg.pathAdjustment", "PathAdjustmentRuntimeElement", null, null, null, null, null, null, null, null, "net.sf.jame.contextfree", "ContextFreeResources", null, null, null, "get", "set", ProcessorCardinality.NONE), descriptors);
	}
	
	private ProcessorParameters createPathReplacementParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(new ProcessorDescriptor("extension", "PathReplacement", "PathReplacement", "net.sf.jame.core.common", "ConfigurableExtensionReferenceElement", null, null, "net.sf.jame.contextfree.cfdg.pathReplacement.extension", "PathReplacementExtensionConfig", "net.sf.jame.contextfree.cfdg.pathReplacement.extension", "PathReplacementExtensionRuntime", null, null, "net.sf.jame.contextfree", "ContextFreeRegistry", null, null, null, null, null, "get", "set", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("pathReplacement", "PathReplacement", "PathReplacement", "net.sf.jame.contextfree.cfdg.pathReplacement", "PathReplacementConfigElement", "net.sf.jame.contextfree.cfdg.pathReplacement", "PathReplacementRuntimeElement", null, null, null, null, null, null, null, null, "net.sf.jame.contextfree", "ContextFreeResources", null, null, null, "get", "set", ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createFigureExtensionParameters() {
		return new ProcessorParameters(new ProcessorDescriptor("figure", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.cfdg.figure.extension", "FigureExtensionConfig", "net.sf.jame.contextfree.cfdg.figure.extension", "FigureExtensionRuntime", "net.sf.jame.contextfree.cfdg.figure.extension", "FigureExtensionRegistry", "net.sf.jame.contextfree", "ContextFreeRegistry", "net.sf.jame.contextfree", "ContextFreeResources", null, null, null, null, null, ProcessorCardinality.NONE), new LinkedList<ProcessorDescriptor>());
	}

	private ProcessorParameters createPathAdjustmentExtensionParameters() {
		return new ProcessorParameters(new ProcessorDescriptor("pathAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.cfdg.pathAdjustment.extension", "PathAdjustmentExtensionConfig", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension", "PathAdjustmentExtensionRuntime", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension", "PathAdjustmentExtensionRegistry", "net.sf.jame.contextfree", "ContextFreeRegistry", "net.sf.jame.contextfree", "ContextFreeResources", null, null, null, null, null, ProcessorCardinality.NONE), new LinkedList<ProcessorDescriptor>());
	}

	private ProcessorParameters createPathReplacementExtensionParameters() {
		return new ProcessorParameters(new ProcessorDescriptor("pathReplacement", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.cfdg.pathReplacement.extension", "PathReplacementExtensionConfig", "net.sf.jame.contextfree.cfdg.pathReplacement.extension", "PathReplacementExtensionRuntime", "net.sf.jame.contextfree.cfdg.pathReplacement.extension", "PathReplacementExtensionRegistry", "net.sf.jame.contextfree", "ContextFreeRegistry", "net.sf.jame.contextfree", "ContextFreeResources", null, null, null, null, null, ProcessorCardinality.NONE), new LinkedList<ProcessorDescriptor>());
	}
	
	private ProcessorParameters createShapeAdjustmentExtensionParameters() {
		return new ProcessorParameters(new ProcessorDescriptor("shapeAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension", "ShapeAdjustmentExtensionConfig", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension", "ShapeAdjustmentExtensionRuntime", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension", "ShapeAdjustmentExtensionRegistry", "net.sf.jame.contextfree", "ContextFreeRegistry", "net.sf.jame.contextfree", "ContextFreeResources", null, null, null, null, null, ProcessorCardinality.NONE), new LinkedList<ProcessorDescriptor>());
	}
	
	private ProcessorParameters createShapeReplacementExtensionParameters() {
		return new ProcessorParameters(new ProcessorDescriptor("shapeReplacement", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.cfdg.shapeReplacement.extension", "ShapeReplacementExtensionConfig", "net.sf.jame.contextfree.cfdg.shapeReplacement.extension", "ShapeReplacementExtensionRuntime", "net.sf.jame.contextfree.cfdg.shapeReplacement.extension", "ShapeReplacementExtensionRegistry", "net.sf.jame.contextfree", "ContextFreeRegistry", "net.sf.jame.contextfree", "ContextFreeResources", null, null, null, null, null, ProcessorCardinality.NONE), new LinkedList<ProcessorDescriptor>());
	}

	private ProcessorParameters createRuleExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("String").createDescriptor("name", "\"\"", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("probability", "1f", ProcessorCardinality.NONE));
		descriptors.add(new ProcessorDescriptor("shapeReplacement", "ShapeReplacement", "ShapeReplacement", "net.sf.jame.contextfree.cfdg.shapeReplacement", "ShapeReplacementConfigElement", "net.sf.jame.contextfree.cfdg.shapeReplacement", "ShapeReplacementRuntimeElement", null, null, null, null, null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, "get", "set", ProcessorCardinality.MANY));
		return new ProcessorParameters(new ProcessorDescriptor("rule", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.figure", "RuleFigureConfig", "net.sf.jame.contextfree.extensions.figure", "RuleFigureRuntime", null, null, null, null,"net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createPathExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("String").createDescriptor("name", "\"\"", ProcessorCardinality.NONE));
		descriptors.add(new ProcessorDescriptor("pathReplacement", "PathReplacement", "PathReplacement", "net.sf.jame.contextfree.cfdg.pathReplacement", "PathReplacementConfigElement", "net.sf.jame.contextfree.cfdg.pathReplacement", "PathReplacementRuntimeElement", null, null, null, null, null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, "get", "set", ProcessorCardinality.MANY));
		return new ProcessorParameters(new ProcessorDescriptor("path", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.figure", "PathFigureConfig", "net.sf.jame.contextfree.extensions.figure", "PathFigureRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createTriangleExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		return new ProcessorParameters(new ProcessorDescriptor("triangle", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.figure", "TriangleFigureConfig", "net.sf.jame.contextfree.extensions.figure", "TriangleFigureRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createSquareExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		return new ProcessorParameters(new ProcessorDescriptor("square", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.figure", "SquareFigureConfig", "net.sf.jame.contextfree.extensions.figure", "SquareFigureRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createCircleExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		return new ProcessorParameters(new ProcessorDescriptor("circle", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.figure", "CircleFigureConfig", "net.sf.jame.contextfree.extensions.figure", "CircleFigureRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createSingleShapeReplacementExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("String").createDescriptor("shape", "\"\"", ProcessorCardinality.NONE));
		descriptors.add(new ProcessorDescriptor("shapeAdjustment", "ShapeAdjustment", "ShapeAdjustment", "net.sf.jame.contextfree.cfdg.shapeAdjustment", "ShapeAdjustmentConfigElement", "net.sf.jame.contextfree.cfdg.shapeAdjustment", "ShapeAdjustmentRuntimeElement", null, null, null, null, null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, "get", "set", ProcessorCardinality.MANY));
		return new ProcessorParameters(new ProcessorDescriptor("singleShapeReplacement", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.shapeReplacement", "SingleShapeReplacementConfig", "net.sf.jame.contextfree.extensions.shapeReplacement", "SingleShapeReplacementRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createMultiShapeReplacementExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Integer").createDescriptor("times", "1", ProcessorCardinality.NONE));
		descriptors.add(new ProcessorDescriptor("shapeReplacement", "ShapeReplacement", "ShapeReplacement", "net.sf.jame.contextfree.cfdg.shapeReplacement", "ShapeReplacementConfigElement", "net.sf.jame.contextfree.cfdg.shapeReplacement", "ShapeReplacementRuntimeElement", null, null, null, null, null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, "get", "set", ProcessorCardinality.MANY));
		descriptors.add(new ProcessorDescriptor("shapeAdjustment", "ShapeAdjustment", "ShapeAdjustment", "net.sf.jame.contextfree.cfdg.shapeAdjustment", "ShapeAdjustmentConfigElement", "net.sf.jame.contextfree.cfdg.shapeAdjustment", "ShapeAdjustmentRuntimeElement", null, null, null, null, null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, "get", "set", ProcessorCardinality.MANY));
		return new ProcessorParameters(new ProcessorDescriptor("multiShapeReplacement", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.shapeReplacement", "MultiShapeReplacementConfig", "net.sf.jame.contextfree.extensions.shapeReplacement", "MultiShapeReplacementRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createLineToPathReplacementExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("x", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("y", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("linetoPathReplacement", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathReplacement", "LineToPathReplacementConfig", "net.sf.jame.contextfree.extensions.pathReplacement", "LineToPathReplacementRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createArcToPathReplacementExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("x", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("y", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("rx", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("ry", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("r", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Boolean").createDescriptor("sweep", "false", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Boolean").createDescriptor("large", "false", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("arctoPathReplacement", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathReplacement", "ArcToPathReplacementConfig", "net.sf.jame.contextfree.extensions.pathReplacement", "ArcToPathReplacementRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createCurveToPathReplacementExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("x", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("y", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("x1", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("y1", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("x2", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("y2", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("curvetoPathReplacement", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathReplacement", "CurveToPathReplacementConfig", "net.sf.jame.contextfree.extensions.pathReplacement", "CurveToPathReplacementRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createQuadToPathReplacementExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("x", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("y", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("x1", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("y1", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("quadtoPathReplacement", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathReplacement", "QuadToPathReplacementConfig", "net.sf.jame.contextfree.extensions.pathReplacement", "QuadToPathReplacementRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}
	
	private ProcessorParameters createSmoothCurveToPathReplacementExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("x", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("y", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("x2", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("y2", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("smoothcurvetoPathReplacement", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathReplacement", "SmoothCurveToPathReplacementConfig", "net.sf.jame.contextfree.extensions.pathReplacement", "SmoothCurveToPathReplacementRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createSmoothQuadToPathReplacementExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("x", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("y", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("smoothquadtoPathReplacement", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathReplacement", "SmoothQuadToPathReplacementConfig", "net.sf.jame.contextfree.extensions.pathReplacement", "SmoothQuadToPathReplacementRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createMoveToPathReplacementExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("x", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("y", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("movetoPathReplacement", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathReplacement", "MoveToPathReplacementConfig", "net.sf.jame.contextfree.extensions.pathReplacement", "MoveToPathReplacementRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createLineRelPathReplacementExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("x", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("y", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("linerelPathReplacement", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathReplacement", "LineRelPathReplacementConfig", "net.sf.jame.contextfree.extensions.pathReplacement", "LineRelPathReplacementRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createArcRelPathReplacementExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("x", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("y", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("rx", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("ry", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("r", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Boolean").createDescriptor("sweep", "false", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Boolean").createDescriptor("large", "false", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("arcrelPathReplacement", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathReplacement", "ArcRelPathReplacementConfig", "net.sf.jame.contextfree.extensions.pathReplacement", "ArcRelPathReplacementRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createCurveRelPathReplacementExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("x", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("y", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("x1", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("y1", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("x2", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("y2", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("curverelPathReplacement", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathReplacement", "CurveRelPathReplacementConfig", "net.sf.jame.contextfree.extensions.pathReplacement", "CurveRelPathReplacementRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createQuadRelPathReplacementExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("x", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("y", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("x1", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("y1", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("quadrelPathReplacement", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathReplacement", "QuadRelPathReplacementConfig", "net.sf.jame.contextfree.extensions.pathReplacement", "QuadRelPathReplacementRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createSmoothCurveRelPathReplacementExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("x", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("y", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("x2", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("y2", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("smoothcurverelPathReplacement", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathReplacement", "SmoothCurveRelPathReplacementConfig", "net.sf.jame.contextfree.extensions.pathReplacement", "SmoothCurveRelPathReplacementRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createSmoothQuadRelPathReplacementExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("x", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("y", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("smoothquadrelPathReplacement", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathReplacement", "SmoothQuadRelPathReplacementConfig", "net.sf.jame.contextfree.extensions.pathReplacement", "SmoothQuadRelPathReplacementRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createMoveRelPathReplacementExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("x", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("y", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("moverelPathReplacement", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathReplacement", "MoveRelPathReplacementConfig", "net.sf.jame.contextfree.extensions.pathReplacement", "MoveRelPathReplacementRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createClosePolyPathReplacementExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Boolean").createDescriptor("align", "false", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("closepolyPathReplacement", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathReplacement", "ClosePolyPathReplacementConfig", "net.sf.jame.contextfree.extensions.pathReplacement", "ClosePolyPathReplacementRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createFillPathReplacementExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("String").createDescriptor("rule", "\"non-zero\"", ProcessorCardinality.NONE));
		descriptors.add(new ProcessorDescriptor("pathAdjustment", "PathAdjustment", "PathAdjustment", "net.sf.jame.contextfree.cfdg.pathAdjustment", "PathAdjustmentConfigElement", "net.sf.jame.contextfree.cfdg.pathAdjustment", "PathAdjustmentRuntimeElement", null, null, null, null, null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, "get", "set", ProcessorCardinality.MANY));
		return new ProcessorParameters(new ProcessorDescriptor("fillPathReplacement", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathReplacement", "FillPathReplacementConfig", "net.sf.jame.contextfree.extensions.pathReplacement", "FillPathReplacementRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createStrokePathReplacementExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("width", "1f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("String").createDescriptor("cap", "\"butt\"", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("String").createDescriptor("join", "\"miter\"", ProcessorCardinality.NONE));
		descriptors.add(new ProcessorDescriptor("pathAdjustment", "PathAdjustment", "PathAdjustment", "net.sf.jame.contextfree.cfdg.pathAdjustment", "PathAdjustmentConfigElement", "net.sf.jame.contextfree.cfdg.pathAdjustment", "PathAdjustmentRuntimeElement", null, null, null, null, null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, "get", "set", ProcessorCardinality.MANY));
		return new ProcessorParameters(new ProcessorDescriptor("strokePathReplacement", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathReplacement", "StrokePathReplacementConfig", "net.sf.jame.contextfree.extensions.pathReplacement", "StrokePathReplacementRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createFlushPathReplacementExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(new ProcessorDescriptor("pathAdjustment", "PathAdjustment", "PathAdjustment", "net.sf.jame.contextfree.cfdg.pathAdjustment", "PathAdjustmentConfigElement", "net.sf.jame.contextfree.cfdg.pathAdjustment", "PathAdjustmentRuntimeElement", null, null, null, null, null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, "get", "set", ProcessorCardinality.MANY));
		return new ProcessorParameters(new ProcessorDescriptor("flushPathReplacement", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathReplacement", "FlushPathReplacementConfig", "net.sf.jame.contextfree.extensions.pathReplacement", "FlushPathReplacementRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

//	private ProcessorParameters createSinglePathReplacementExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
//		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
//		descriptors.add(descriptorExtensionMap.get("String").createDescriptor("path", "\"\"", ProcessorCardinality.NONE));
//		descriptors.add(new ProcessorDescriptor("pathAdjustment", "PathAdjustment", "PathAdjustment", "net.sf.jame.contextfree.cfdg.pathAdjustment", "PathAdjustmentConfigElement", "net.sf.jame.contextfree.cfdg.pathAdjustment", "PathAdjustmentRuntimeElement", null, null, null, null, null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, "get", "set", ProcessorCardinality.MANY));
//		return new ProcessorParameters(new ProcessorDescriptor("singlePathReplacement", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathReplacement", "SinglePathReplacementConfig", "net.sf.jame.contextfree.extensions.pathReplacement", "SinglePathReplacementRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
//	}

	private ProcessorParameters createMultiPathReplacementExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Integer").createDescriptor("times", "1", ProcessorCardinality.NONE));
		descriptors.add(new ProcessorDescriptor("pathReplacement", "PathReplacement", "PathReplacement", "net.sf.jame.contextfree.cfdg.pathReplacement", "PathReplacementConfigElement", "net.sf.jame.contextfree.cfdg.pathReplacement", "PathReplacementRuntimeElement", null, null, null, null, null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, "get", "set", ProcessorCardinality.MANY));
		descriptors.add(new ProcessorDescriptor("pathAdjustment", "PathAdjustment", "PathAdjustment", "net.sf.jame.contextfree.cfdg.pathAdjustment", "PathAdjustmentConfigElement", "net.sf.jame.contextfree.cfdg.pathAdjustment", "PathAdjustmentRuntimeElement", null, null, null, null, null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, "get", "set", ProcessorCardinality.MANY));
		return new ProcessorParameters(new ProcessorDescriptor("multiPathReplacement", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathReplacement", "MultiPathReplacementConfig", "net.sf.jame.contextfree.extensions.pathReplacement", "MultiPathReplacementRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createTargetHuePathAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("value", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("targetHuePathAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathAdjustment", "TargetHuePathAdjustmentConfig", "net.sf.jame.contextfree.extensions.pathAdjustment", "TargetHuePathAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createTargetBrightnessPathAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("value", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("targetBrightnessPathAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathAdjustment", "TargetBrightnessPathAdjustmentConfig", "net.sf.jame.contextfree.extensions.pathAdjustment", "TargetBrightnessPathAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createTargetSaturationPathAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("value", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("targetSaturationPathAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathAdjustment", "TargetSaturationPathAdjustmentConfig", "net.sf.jame.contextfree.extensions.pathAdjustment", "TargetSaturationPathAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createTargetAlphaPathAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("value", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("targetAlphaPathAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathAdjustment", "TargetAlphaPathAdjustmentConfig", "net.sf.jame.contextfree.extensions.pathAdjustment", "TargetAlphaPathAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createCurrentHuePathAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("value", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Boolean").createDescriptor("target", "false", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("currentHuePathAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathAdjustment", "CurrentHuePathAdjustmentConfig", "net.sf.jame.contextfree.extensions.pathAdjustment", "CurrentHuePathAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createCurrentBrightnessPathAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("value", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Boolean").createDescriptor("target", "false", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("currentBrightnessPathAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathAdjustment", "CurrentBrightnessPathAdjustmentConfig", "net.sf.jame.contextfree.extensions.pathAdjustment", "CurrentBrightnessPathAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createCurrentSaturationPathAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("value", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Boolean").createDescriptor("target", "false", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("currentSaturationPathAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathAdjustment", "CurrentSaturationPathAdjustmentConfig", "net.sf.jame.contextfree.extensions.pathAdjustment", "CurrentSaturationPathAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createCurrentAlphaPathAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("value", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Boolean").createDescriptor("target", "false", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("currentAlphaPathAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathAdjustment", "CurrentAlphaPathAdjustmentConfig", "net.sf.jame.contextfree.extensions.pathAdjustment", "CurrentAlphaPathAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createXPathAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("value", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("xPathAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathAdjustment", "XPathAdjustmentConfig", "net.sf.jame.contextfree.extensions.pathAdjustment", "XPathAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createYPathAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("value", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("yPathAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathAdjustment", "YPathAdjustmentConfig", "net.sf.jame.contextfree.extensions.pathAdjustment", "YPathAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createRotatePathAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("angle", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("rotatePathAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathAdjustment", "RotatePathAdjustmentConfig", "net.sf.jame.contextfree.extensions.pathAdjustment", "RotatePathAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createSkewPathAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("shearX", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("shearY", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("skewPathAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathAdjustment", "SkewPathAdjustmentConfig", "net.sf.jame.contextfree.extensions.pathAdjustment", "SkewPathAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createFlipPathAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("angle", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("flipPathAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathAdjustment", "FlipPathAdjustmentConfig", "net.sf.jame.contextfree.extensions.pathAdjustment", "FlipPathAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createSizePathAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("scale", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("sizePathAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathAdjustment", "SizePathAdjustmentConfig", "net.sf.jame.contextfree.extensions.pathAdjustment", "SizePathAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createSize2PathAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("scaleX", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("scaleY", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("size2PathAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.pathAdjustment", "Size2PathAdjustmentConfig", "net.sf.jame.contextfree.extensions.pathAdjustment", "Size2PathAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createTargetHueShapeAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("value", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("targetHueShapeAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.shapeAdjustment", "TargetHueShapeAdjustmentConfig", "net.sf.jame.contextfree.extensions.shapeAdjustment", "TargetHueShapeAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}
	
	private ProcessorParameters createTargetBrightnessShapeAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("value", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("targetBrightnessShapeAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.shapeAdjustment", "TargetBrightnessShapeAdjustmentConfig", "net.sf.jame.contextfree.extensions.shapeAdjustment", "TargetBrightnessShapeAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createTargetSaturationShapeAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("value", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("targetSaturationShapeAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.shapeAdjustment", "TargetSaturationShapeAdjustmentConfig", "net.sf.jame.contextfree.extensions.shapeAdjustment", "TargetSaturationShapeAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createTargetAlphaShapeAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("value", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("targetAlphaShapeAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.shapeAdjustment", "TargetAlphaShapeAdjustmentConfig", "net.sf.jame.contextfree.extensions.shapeAdjustment", "TargetAlphaShapeAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createCurrentHueShapeAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("value", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Boolean").createDescriptor("target", "false", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("currentHueShapeAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.shapeAdjustment", "CurrentHueShapeAdjustmentConfig", "net.sf.jame.contextfree.extensions.shapeAdjustment", "CurrentHueShapeAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createCurrentBrightnessShapeAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("value", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Boolean").createDescriptor("target", "false", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("currentBrightnessShapeAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.shapeAdjustment", "CurrentBrightnessShapeAdjustmentConfig", "net.sf.jame.contextfree.extensions.shapeAdjustment", "CurrentBrightnessShapeAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createCurrentSaturationShapeAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("value", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Boolean").createDescriptor("target", "false", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("currentSaturationShapeAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.shapeAdjustment", "CurrentSaturationShapeAdjustmentConfig", "net.sf.jame.contextfree.extensions.shapeAdjustment", "CurrentSaturationShapeAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createCurrentAlphaShapeAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("value", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Boolean").createDescriptor("target", "false", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("currentAlphaShapeAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.shapeAdjustment", "CurrentAlphaShapeAdjustmentConfig", "net.sf.jame.contextfree.extensions.shapeAdjustment", "CurrentAlphaShapeAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createXShapeAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("value", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("xShapeAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.shapeAdjustment", "XShapeAdjustmentConfig", "net.sf.jame.contextfree.extensions.shapeAdjustment", "XShapeAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createYShapeAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("value", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("yShapeAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.shapeAdjustment", "YShapeAdjustmentConfig", "net.sf.jame.contextfree.extensions.shapeAdjustment", "YShapeAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createZShapeAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("value", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("zShapeAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.shapeAdjustment", "ZShapeAdjustmentConfig", "net.sf.jame.contextfree.extensions.shapeAdjustment", "ZShapeAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createRotateShapeAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("angle", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("rotateShapeAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.shapeAdjustment", "RotateShapeAdjustmentConfig", "net.sf.jame.contextfree.extensions.shapeAdjustment", "RotateShapeAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createSkewShapeAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("shearX", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("shearY", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("skewShapeAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.shapeAdjustment", "SkewShapeAdjustmentConfig", "net.sf.jame.contextfree.extensions.shapeAdjustment", "SkewShapeAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createFlipShapeAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("angle", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("flipShapeAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.shapeAdjustment", "FlipShapeAdjustmentConfig", "net.sf.jame.contextfree.extensions.shapeAdjustment", "FlipShapeAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createSizeShapeAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("scale", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("sizeShapeAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.shapeAdjustment", "SizeShapeAdjustmentConfig", "net.sf.jame.contextfree.extensions.shapeAdjustment", "SizeShapeAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createSize2ShapeAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("scaleX", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("scaleY", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("size2ShapeAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.shapeAdjustment", "Size2ShapeAdjustmentConfig", "net.sf.jame.contextfree.extensions.shapeAdjustment", "Size2ShapeAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}

	private ProcessorParameters createSize3ShapeAdjustmentExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("scaleX", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("scaleY", "0f", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Float").createDescriptor("scaleZ", "0f", ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("size3ShapeAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.extensions.shapeAdjustment", "Size3ShapeAdjustmentConfig", "net.sf.jame.contextfree.extensions.shapeAdjustment", "Size3ShapeAdjustmentRuntime", null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}
	
	private ProcessorParameters createRegistryParameters() {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(new ProcessorDescriptor("figure", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.cfdg.figure.extension", "FigureExtensionConfig", "net.sf.jame.contextfree.cfdg.figure.extension", "FigureExtensionRuntime", "net.sf.jame.contextfree.cfdg.figure.extension", "FigureExtensionRegistry", null, null, "net.sf.jame.contextfree", "ContextFreeResources", null, null, null, null, null, ProcessorCardinality.NONE));
		descriptors.add(new ProcessorDescriptor("pathAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.cfdg.pathAdjustment.extension", "PathAdjustmentExtensionConfig", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension", "PathAdjustmentExtensionRuntime", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension", "PathAdjustmentExtensionRegistry", null, null, "net.sf.jame.contextfree", "ContextFreeResources", null, null, null, null, null, ProcessorCardinality.NONE));
		descriptors.add(new ProcessorDescriptor("pathReplacement", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.cfdg.pathReplacement.extension", "PathReplacementExtensionConfig", "net.sf.jame.contextfree.cfdg.pathReplacement.extension", "PathReplacementExtensionRuntime", "net.sf.jame.contextfree.cfdg.pathReplacement.extension", "PathReplacementExtensionRegistry", null, null, "net.sf.jame.contextfree", "ContextFreeResources", null, null, null, null, null, ProcessorCardinality.NONE));
		descriptors.add(new ProcessorDescriptor("shapeAdjustment", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension", "ShapeAdjustmentExtensionConfig", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension", "ShapeAdjustmentExtensionRuntime", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension", "ShapeAdjustmentExtensionRegistry", null, null, "net.sf.jame.contextfree", "ContextFreeResources", null, null, null, null, null, ProcessorCardinality.NONE));
		descriptors.add(new ProcessorDescriptor("shapeReplacement", "Extension", null, null, null, null, null, "net.sf.jame.contextfree.cfdg.shapeReplacement.extension", "ShapeReplacementExtensionConfig", "net.sf.jame.contextfree.cfdg.shapeReplacement.extension", "ShapeReplacementExtensionRuntime", "net.sf.jame.contextfree.cfdg.shapeReplacement.extension", "ShapeReplacementExtensionRegistry", null, null, "net.sf.jame.contextfree", "ContextFreeResources", null, null, null, null, null, ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("registry", "Registry", null, null, null, null, null, null, null, null, null, null, null, "net.sf.jame.contextfree", "ContextFreeRegistry", null, null, null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}
	
	private ProcessorParameters createResourcesParameters() {
		return new ProcessorParameters(new ProcessorDescriptor("resources", "Resources", null, null, null, null, null, null, null, null, null, null, null, null, null, "net.sf.jame.contextfree", "ContextFreeResources", null, null, null, null, null, ProcessorCardinality.NONE), new LinkedList<ProcessorDescriptor>());
	}
	
	private ProcessorParameters createExtensionResourcesParameters() {
		return new ProcessorParameters(new ProcessorDescriptor("resources", "Resources", null, null, null, null, null, null, null, null, null, null, null, null, null, "net.sf.jame.contextfree.extensions", "ContextFreeExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), new LinkedList<ProcessorDescriptor>());
	}

	private ProcessorParameters createSwingExtensionResourcesParameters() {
		return new ProcessorParameters(new ProcessorDescriptor("resources", "Resources", null, null, null, null, null, null, null, null, null, null, null, null, null, "net.sf.jame.contextfree.swing.extensions", "ContextFreeSwingExtensionResources", null, null, null, null, null, ProcessorCardinality.NONE), new LinkedList<ProcessorDescriptor>());
	}

	private void populateProcessorExtensionMap(Map<String, ProcessorExtensionRuntime> map) {
		List<Extension<ProcessorExtensionRuntime>> extensions = DevToolsRegistry.getInstance().getProcessorRegistry().getExtensionList();
		for (Extension<ProcessorExtensionRuntime> extension : extensions) {
			try {
				ProcessorExtensionRuntime runtime = extension.createExtensionRuntime();
				map.put(runtime.getName(), runtime);
			}
			catch (ExtensionException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void populateDescriptorExtensionMap(Map<String, DescriptorExtensionRuntime> map) {
		List<Extension<DescriptorExtensionRuntime>> extensions = DevToolsRegistry.getInstance().getDescriptorRegistry().getExtensionList();
		for (Extension<DescriptorExtensionRuntime> extension : extensions) {
			try {
				DescriptorExtensionRuntime runtime = extension.createExtensionRuntime();
				map.put(runtime.getClassId(), runtime);
			}
			catch (ExtensionException e) {
				e.printStackTrace();
			}
		}
	}

	private Map<String, String> createVariables() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("author", "Andrea Medeghini");
		map.put("extensionPointId", "net.sf.jame.contextfree.extensions");
		return map;
	}

	@Test
	public void generateCFDG() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("generateElementEditor", "yes");
			variables.put("editorPackageName", "net.sf.jame.contextfree.swing.extensions.editor");
			variables.put("generateElementNodeActionXMLExporter", "yes");
			variables.put("generateElementNodeActionXMLImporter", "yes");
			variables.put("nodeActionXMLExporterPackageName", "net.sf.jame.contextfree.extensions.action");
			variables.put("nodeActionXMLImporterPackageName", "net.sf.jame.contextfree.extensions.action");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createCFDGParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateFigure() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("generateElementEditor", "yes");
			variables.put("editorPackageName", "net.sf.jame.contextfree.swing.extensions.editor");
			variables.put("generateElementNodeActionXMLExporter", "yes");
			variables.put("generateElementNodeActionXMLImporter", "yes");
			variables.put("generateElementListNodeActionXMLExporter", "yes");
			variables.put("generateElementListNodeActionXMLImporter", "yes");
			variables.put("nodeActionXMLExporterPackageName", "net.sf.jame.contextfree.extensions.action");
			variables.put("nodeActionXMLImporterPackageName", "net.sf.jame.contextfree.extensions.action");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createFigureParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateFigureExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("generateAbstractClass", "yes");
			variables.put("generateReferenceEditor", "yes");
			variables.put("generateReferenceElementEditor", "yes");
			variables.put("generateReferenceElementListEditor", "yes");
			variables.put("configElementPackageName", "net.sf.jame.contextfree.cfdg.figure");
			variables.put("configElementClassName", "FigureConfigElement");
			variables.put("resourcesPackageName", "net.sf.jame.contextfree.swing.extensions");
			variables.put("resourcesClassName", "ContextFreeSwingExtensionResources");
			variables.put("editorPackageName", "net.sf.jame.contextfree.swing.extensions.editor");
			variables.put("generateReferenceNodeActionXMLExporter", "yes");
			variables.put("generateReferenceNodeActionXMLImporter", "yes");
			variables.put("nodeActionXMLExporterPackageName", "net.sf.jame.contextfree.extensions.action");
			variables.put("nodeActionXMLImporterPackageName", "net.sf.jame.contextfree.extensions.action");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createFigureExtensionParameters();
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generatePathExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.figure.extension");
			variables.put("parentConfigClass", "FigureExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.figure.extension");
			variables.put("parentRuntimeClass", "FigureExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createPathExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateRuleExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.figure.extension");
			variables.put("parentConfigClass", "FigureExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.figure.extension");
			variables.put("parentRuntimeClass", "FigureExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createRuleExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateTriangleExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.figure.extension");
			variables.put("parentConfigClass", "FigureExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.figure.extension");
			variables.put("parentRuntimeClass", "FigureExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createTriangleExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateSquareExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.figure.extension");
			variables.put("parentConfigClass", "FigureExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.figure.extension");
			variables.put("parentRuntimeClass", "FigureExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createSquareExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateCircleExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.figure.extension");
			variables.put("parentConfigClass", "FigureExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.figure.extension");
			variables.put("parentRuntimeClass", "FigureExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createCircleExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generatePathAdjustment() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("generateElementEditor", "yes");
			variables.put("editorPackageName", "net.sf.jame.contextfree.swing.extensions.editor");
			variables.put("generateElementNodeActionXMLExporter", "yes");
			variables.put("generateElementNodeActionXMLImporter", "yes");
			variables.put("generateElementListNodeActionXMLExporter", "yes");
			variables.put("generateElementListNodeActionXMLImporter", "yes");
			variables.put("nodeActionXMLExporterPackageName", "net.sf.jame.contextfree.extensions.action");
			variables.put("nodeActionXMLImporterPackageName", "net.sf.jame.contextfree.extensions.action");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createPathAdjustmentParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generatePathAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("generateAbstractClass", "yes");
			variables.put("generateReferenceEditor", "yes");
			variables.put("generateReferenceElementEditor", "yes");
			variables.put("generateReferenceElementListEditor", "yes");
			variables.put("configElementPackageName", "net.sf.jame.contextfree.cfdg.pathAdjustment");
			variables.put("configElementClassName", "PathAdjustmentConfigElement");
			variables.put("resourcesPackageName", "net.sf.jame.contextfree.swing.extensions");
			variables.put("resourcesClassName", "ContextFreeSwingExtensionResources");
			variables.put("editorPackageName", "net.sf.jame.contextfree.swing.extensions.editor");
			variables.put("generateReferenceNodeActionXMLExporter", "yes");
			variables.put("generateReferenceNodeActionXMLImporter", "yes");
			variables.put("nodeActionXMLExporterPackageName", "net.sf.jame.contextfree.extensions.action");
			variables.put("nodeActionXMLImporterPackageName", "net.sf.jame.contextfree.extensions.action");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createPathAdjustmentExtensionParameters();
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateTargetHuePathAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension");
			variables.put("parentConfigClass", "PathAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension");
			variables.put("parentRuntimeClass", "PathAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createTargetHuePathAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateTargetBrightnessPathAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension");
			variables.put("parentConfigClass", "PathAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension");
			variables.put("parentRuntimeClass", "PathAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createTargetBrightnessPathAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateTargetSaturationPathAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension");
			variables.put("parentConfigClass", "PathAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension");
			variables.put("parentRuntimeClass", "PathAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createTargetSaturationPathAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateTargetAlphaPathAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension");
			variables.put("parentConfigClass", "PathAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension");
			variables.put("parentRuntimeClass", "PathAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createTargetAlphaPathAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateCurrentHuePathAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension");
			variables.put("parentConfigClass", "PathAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension");
			variables.put("parentRuntimeClass", "PathAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createCurrentHuePathAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateCurrentBrightnessPathAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension");
			variables.put("parentConfigClass", "PathAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension");
			variables.put("parentRuntimeClass", "PathAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createCurrentBrightnessPathAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateCurrentSaturationPathAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension");
			variables.put("parentConfigClass", "PathAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension");
			variables.put("parentRuntimeClass", "PathAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createCurrentSaturationPathAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateCurrentAlphaPathAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension");
			variables.put("parentConfigClass", "PathAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension");
			variables.put("parentRuntimeClass", "PathAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createCurrentAlphaPathAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateXPathAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension");
			variables.put("parentConfigClass", "PathAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension");
			variables.put("parentRuntimeClass", "PathAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createXPathAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateYPathAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension");
			variables.put("parentConfigClass", "PathAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension");
			variables.put("parentRuntimeClass", "PathAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createYPathAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateSizePathAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension");
			variables.put("parentConfigClass", "PathAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension");
			variables.put("parentRuntimeClass", "PathAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createSizePathAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateSize2PathAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension");
			variables.put("parentConfigClass", "PathAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension");
			variables.put("parentRuntimeClass", "PathAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createSize2PathAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateFlipPathAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension");
			variables.put("parentConfigClass", "PathAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension");
			variables.put("parentRuntimeClass", "PathAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createFlipPathAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateSkewPathAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension");
			variables.put("parentConfigClass", "PathAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension");
			variables.put("parentRuntimeClass", "PathAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createSkewPathAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateRotatePathAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension");
			variables.put("parentConfigClass", "PathAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathAdjustment.extension");
			variables.put("parentRuntimeClass", "PathAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createRotatePathAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generatePathReplacement() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("generateElementEditor", "yes");
			variables.put("editorPackageName", "net.sf.jame.contextfree.swing.extensions.editor");
			variables.put("generateElementNodeActionXMLExporter", "yes");
			variables.put("generateElementNodeActionXMLImporter", "yes");
			variables.put("generateElementListNodeActionXMLExporter", "yes");
			variables.put("generateElementListNodeActionXMLImporter", "yes");
			variables.put("nodeActionXMLExporterPackageName", "net.sf.jame.contextfree.extensions.action");
			variables.put("nodeActionXMLImporterPackageName", "net.sf.jame.contextfree.extensions.action");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createPathReplacementParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generatePathReplacementExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("generateAbstractClass", "yes");
			variables.put("generateReferenceEditor", "yes");
			variables.put("generateReferenceElementEditor", "yes");
			variables.put("generateReferenceElementListEditor", "yes");
			variables.put("configElementPackageName", "net.sf.jame.contextfree.cfdg.pathReplacement");
			variables.put("configElementClassName", "PathReplacementConfigElement");
			variables.put("resourcesPackageName", "net.sf.jame.contextfree.swing.extensions");
			variables.put("resourcesClassName", "ContextFreeSwingExtensionResources");
			variables.put("editorPackageName", "net.sf.jame.contextfree.swing.extensions.editor");
			variables.put("generateReferenceNodeActionXMLExporter", "yes");
			variables.put("generateReferenceNodeActionXMLImporter", "yes");
			variables.put("nodeActionXMLExporterPackageName", "net.sf.jame.contextfree.extensions.action");
			variables.put("nodeActionXMLImporterPackageName", "net.sf.jame.contextfree.extensions.action");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createPathReplacementExtensionParameters();
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

//	@Test
//	public void generateSinglePathReplacementExtension() {
//		try {
//			File path = new File("build");
//			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
//			populateProcessorExtensionMap(processorExtensionMap);
//			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
//			Map<String, String> variables = createVariables();
//			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
//			variables.put("parentConfigClass", "PathReplacementExtensionConfig");
//			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
//			variables.put("parentRuntimeClass", "PathReplacementExtensionRuntime");
//			populateDescriptorExtensionMap(descriptorExtensionMap);
//			ProcessorParameters parameters = createSinglePathReplacementExtensionParameters(descriptorExtensionMap);
//			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
//				processorRuntime.process(path, parameters, variables);
//			}
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	@Test
	public void generateMultiPathReplacementExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentConfigClass", "PathReplacementExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentRuntimeClass", "PathReplacementExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createMultiPathReplacementExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateLineToPathReplacementExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentConfigClass", "PathReplacementExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentRuntimeClass", "PathReplacementExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createLineToPathReplacementExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateArcToPathReplacementExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentConfigClass", "PathReplacementExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentRuntimeClass", "PathReplacementExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createArcToPathReplacementExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateCurveToPathReplacementExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentConfigClass", "PathReplacementExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentRuntimeClass", "PathReplacementExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createCurveToPathReplacementExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateQuadToPathReplacementExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentConfigClass", "PathReplacementExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentRuntimeClass", "PathReplacementExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createQuadToPathReplacementExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateSmoothCurveToPathReplacementExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentConfigClass", "PathReplacementExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentRuntimeClass", "PathReplacementExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createSmoothCurveToPathReplacementExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateSmoothQuadToPathReplacementExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentConfigClass", "PathReplacementExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentRuntimeClass", "PathReplacementExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createSmoothQuadToPathReplacementExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateMoveToPathReplacementExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentConfigClass", "PathReplacementExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentRuntimeClass", "PathReplacementExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createMoveToPathReplacementExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateLineRelPathReplacementExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentConfigClass", "PathReplacementExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentRuntimeClass", "PathReplacementExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createLineRelPathReplacementExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateArcRelPathReplacementExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentConfigClass", "PathReplacementExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentRuntimeClass", "PathReplacementExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createArcRelPathReplacementExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateCurveRelPathReplacementExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentConfigClass", "PathReplacementExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentRuntimeClass", "PathReplacementExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createCurveRelPathReplacementExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateQuadRelPathReplacementExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentConfigClass", "PathReplacementExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentRuntimeClass", "PathReplacementExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createQuadRelPathReplacementExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateSmoothCurveRelPathReplacementExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentConfigClass", "PathReplacementExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentRuntimeClass", "PathReplacementExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createSmoothCurveRelPathReplacementExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateSmoothQuadRelPathReplacementExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentConfigClass", "PathReplacementExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentRuntimeClass", "PathReplacementExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createSmoothQuadRelPathReplacementExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateMoveRelPathReplacementExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentConfigClass", "PathReplacementExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentRuntimeClass", "PathReplacementExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createMoveRelPathReplacementExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateClosePolyPathReplacementExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentConfigClass", "PathReplacementExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentRuntimeClass", "PathReplacementExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createClosePolyPathReplacementExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateFillPathReplacementExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentConfigClass", "PathReplacementExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentRuntimeClass", "PathReplacementExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createFillPathReplacementExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateStrokePathReplacementExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentConfigClass", "PathReplacementExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentRuntimeClass", "PathReplacementExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createStrokePathReplacementExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateFlushPathReplacementExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentConfigClass", "PathReplacementExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.pathReplacement.extension");
			variables.put("parentRuntimeClass", "PathReplacementExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createFlushPathReplacementExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateShapeAdjustment() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("generateElementEditor", "yes");
			variables.put("editorPackageName", "net.sf.jame.contextfree.swing.extensions.editor");
			variables.put("generateElementNodeActionXMLExporter", "yes");
			variables.put("generateElementNodeActionXMLImporter", "yes");
			variables.put("generateElementListNodeActionXMLExporter", "yes");
			variables.put("generateElementListNodeActionXMLImporter", "yes");
			variables.put("nodeActionXMLExporterPackageName", "net.sf.jame.contextfree.extensions.action");
			variables.put("nodeActionXMLImporterPackageName", "net.sf.jame.contextfree.extensions.action");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createShapeAdjustmentParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateShapeAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("generateAbstractClass", "yes");
			variables.put("generateReferenceEditor", "yes");
			variables.put("generateReferenceElementEditor", "yes");
			variables.put("generateReferenceElementListEditor", "yes");
			variables.put("configElementPackageName", "net.sf.jame.contextfree.cfdg.shapeAdjustment");
			variables.put("configElementClassName", "ShapeAdjustmentConfigElement");
			variables.put("resourcesPackageName", "net.sf.jame.contextfree.swing.extensions");
			variables.put("resourcesClassName", "ContextFreeSwingExtensionResources");
			variables.put("editorPackageName", "net.sf.jame.contextfree.swing.extensions.editor");
			variables.put("generateReferenceNodeActionXMLExporter", "yes");
			variables.put("generateReferenceNodeActionXMLImporter", "yes");
			variables.put("nodeActionXMLExporterPackageName", "net.sf.jame.contextfree.extensions.action");
			variables.put("nodeActionXMLImporterPackageName", "net.sf.jame.contextfree.extensions.action");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createShapeAdjustmentExtensionParameters();
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateTargetHueShapeAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentConfigClass", "ShapeAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentRuntimeClass", "ShapeAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createTargetHueShapeAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateTargetBrightnessShapeAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentConfigClass", "ShapeAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentRuntimeClass", "ShapeAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createTargetBrightnessShapeAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateTargetSaturationShapeAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentConfigClass", "ShapeAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentRuntimeClass", "ShapeAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createTargetSaturationShapeAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateTargetAlphaShapeAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentConfigClass", "ShapeAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentRuntimeClass", "ShapeAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createTargetAlphaShapeAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateCurrentHueShapeAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentConfigClass", "ShapeAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentRuntimeClass", "ShapeAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createCurrentHueShapeAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateCurrentBrightnessShapeAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentConfigClass", "ShapeAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentRuntimeClass", "ShapeAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createCurrentBrightnessShapeAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateCurrentSaturationShapeAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentConfigClass", "ShapeAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentRuntimeClass", "ShapeAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createCurrentSaturationShapeAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateCurrentAlphaShapeAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentConfigClass", "ShapeAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentRuntimeClass", "ShapeAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createCurrentAlphaShapeAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateXShapeAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentConfigClass", "ShapeAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentRuntimeClass", "ShapeAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createXShapeAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateYShapeAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentConfigClass", "ShapeAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentRuntimeClass", "ShapeAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createYShapeAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateZShapeAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentConfigClass", "ShapeAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentRuntimeClass", "ShapeAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createZShapeAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateSizeShapeAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentConfigClass", "ShapeAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentRuntimeClass", "ShapeAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createSizeShapeAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateSize2ShapeAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentConfigClass", "ShapeAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentRuntimeClass", "ShapeAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createSize2ShapeAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateSize3ShapeAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentConfigClass", "ShapeAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentRuntimeClass", "ShapeAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createSize3ShapeAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateFlipShapeAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentConfigClass", "ShapeAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentRuntimeClass", "ShapeAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createFlipShapeAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateSkewShapeAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentConfigClass", "ShapeAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentRuntimeClass", "ShapeAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createSkewShapeAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateRotateShapeAdjustmentExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentConfigClass", "ShapeAdjustmentExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.shapeAdjustment.extension");
			variables.put("parentRuntimeClass", "ShapeAdjustmentExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createRotateShapeAdjustmentExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateShapeReplacement() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("generateElementEditor", "yes");
			variables.put("editorPackageName", "net.sf.jame.contextfree.swing.extensions.editor");
			variables.put("generateElementNodeActionXMLExporter", "yes");
			variables.put("generateElementNodeActionXMLImporter", "yes");
			variables.put("generateElementListNodeActionXMLExporter", "yes");
			variables.put("generateElementListNodeActionXMLImporter", "yes");
			variables.put("nodeActionXMLExporterPackageName", "net.sf.jame.contextfree.extensions.action");
			variables.put("nodeActionXMLImporterPackageName", "net.sf.jame.contextfree.extensions.action");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createShapeReplacementParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateShapeReplacementExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("generateAbstractClass", "yes");
			variables.put("generateReferenceEditor", "yes");
			variables.put("generateReferenceElementEditor", "yes");
			variables.put("generateReferenceElementListEditor", "yes");
			variables.put("configElementPackageName", "net.sf.jame.contextfree.cfdg.shapeReplacement");
			variables.put("configElementClassName", "ShapeReplacementConfigElement");
			variables.put("resourcesPackageName", "net.sf.jame.contextfree.swing.extensions");
			variables.put("resourcesClassName", "ContextFreeSwingExtensionResources");
			variables.put("editorPackageName", "net.sf.jame.contextfree.swing.extensions.editor");
			variables.put("generateReferenceNodeActionXMLExporter", "yes");
			variables.put("generateReferenceNodeActionXMLImporter", "yes");
			variables.put("nodeActionXMLExporterPackageName", "net.sf.jame.contextfree.extensions.action");
			variables.put("nodeActionXMLImporterPackageName", "net.sf.jame.contextfree.extensions.action");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createShapeReplacementExtensionParameters();
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateSingleShapeReplacementExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.shapeReplacement.extension");
			variables.put("parentConfigClass", "ShapeReplacementExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.shapeReplacement.extension");
			variables.put("parentRuntimeClass", "ShapeReplacementExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createSingleShapeReplacementExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateMultiShapeReplacementExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			variables.put("parentConfigPackage", "net.sf.jame.contextfree.cfdg.shapeReplacement.extension");
			variables.put("parentConfigClass", "ShapeReplacementExtensionConfig");
			variables.put("parentRuntimePackage", "net.sf.jame.contextfree.cfdg.shapeReplacement.extension");
			variables.put("parentRuntimeClass", "ShapeReplacementExtensionRuntime");
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createMultiShapeReplacementExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateRegistry() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createRegistryParameters();
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateResources() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createResourcesParameters();
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateExtensionResources() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createExtensionResourcesParameters();
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateSwingExtensionResources() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createSwingExtensionResourcesParameters();
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
