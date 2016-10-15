/*
 * JAME 6.2.1
 * http://jame.sourceforge.net
 *
 * Copyright 2001, 2016 Andrea Medeghini
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
package net.sf.jame.mandelbrot.extensions.outcolouringFormula;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.jame.core.config.ConfigElement;
import net.sf.jame.core.extension.ConfigurableExtensionReference;
import net.sf.jame.mandelbrot.MandelbrotRegistry;
import net.sf.jame.mandelbrot.colorRenderer.ColorRendererConfigElement;
import net.sf.jame.mandelbrot.colorRenderer.extension.ColorRendererExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public class UniversalTrueColorConfig extends AbstractOutcolouringFormulaConfig {
	private static final String DEFAULT_COLOR_RENDERER_EXTENSION_ID = "twister.mandelbrot.color.renderer.sin";
	private static final String ALPHA_COLOR_RENDERER_EXTENSION_ID = "twister.mandelbrot.color.renderer.constant";
	private static final long serialVersionUID = 1L;
	private ColorRendererConfigElement[] colorRendererElements;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		colorRendererElements = new ColorRendererConfigElement[4];
		colorRendererElements[0] = new ColorRendererConfigElement();
		colorRendererElements[1] = new ColorRendererConfigElement();
		colorRendererElements[2] = new ColorRendererConfigElement();
		colorRendererElements[3] = new ColorRendererConfigElement();
	}

	/**
	 * 
	 */
	@Override
	protected void initConfigElements() {
		try {
			colorRendererElements[0].setReference(MandelbrotRegistry.getInstance().getColorRendererExtension(UniversalTrueColorConfig.DEFAULT_COLOR_RENDERER_EXTENSION_ID).createConfigurableExtensionReference());
		}
		catch (final Exception e) {
			throw new Error(e);
		}
		try {
			colorRendererElements[1].setReference(MandelbrotRegistry.getInstance().getColorRendererExtension(UniversalTrueColorConfig.DEFAULT_COLOR_RENDERER_EXTENSION_ID).createConfigurableExtensionReference());
		}
		catch (final Exception e) {
			throw new Error(e);
		}
		try {
			colorRendererElements[2].setReference(MandelbrotRegistry.getInstance().getColorRendererExtension(UniversalTrueColorConfig.DEFAULT_COLOR_RENDERER_EXTENSION_ID).createConfigurableExtensionReference());
		}
		catch (final Exception e) {
			throw new Error(e);
		}
		try {
			colorRendererElements[3].setReference(MandelbrotRegistry.getInstance().getColorRendererExtension(UniversalTrueColorConfig.ALPHA_COLOR_RENDERER_EXTENSION_ID).createConfigurableExtensionReference());
		}
		catch (final Exception e) {
			throw new Error(e);
		}
	}

	/**
	 * @see net.sf.jame.core.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		elements.add(colorRendererElements[0]);
		elements.add(colorRendererElements[1]);
		elements.add(colorRendererElements[2]);
		elements.add(colorRendererElements[3]);
		return elements;
	}

	/**
	 * @param index
	 * @return the colorRenderer
	 */
	public ConfigurableExtensionReference<ColorRendererExtensionConfig> getColorRenderer(final int index) {
		return colorRendererElements[index].getReference();
	}

	/**
	 * @param index
	 * @param colorRenderer the colorRenderer to set
	 */
	public void setColorRenderer(final int index, final ConfigurableExtensionReference<ColorRendererExtensionConfig> reference) {
		colorRendererElements[index].setReference(reference);
	}

	/**
	 * Returns the colorRenderers.
	 * 
	 * @return the colorRenderers.
	 */
	public ColorRendererConfigElement[] getColorRendererElements() {
		return colorRendererElements;
	}

	/**
	 * @param index
	 * @return
	 */
	public ColorRendererConfigElement getColorRendererElement(final int index) {
		return colorRendererElements[index];
	}

	/**
	 * @return
	 */
	@Override
	public UniversalTrueColorConfig clone() {
		final UniversalTrueColorConfig config = new UniversalTrueColorConfig();
		config.setColorRenderer(0, getColorRenderer(0).clone());
		config.setColorRenderer(1, getColorRenderer(1).clone());
		config.setColorRenderer(2, getColorRenderer(2).clone());
		config.setColorRenderer(3, getColorRenderer(3).clone());
		return config;
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
		final UniversalTrueColorConfig other = (UniversalTrueColorConfig) obj;
		if (!Arrays.equals(colorRendererElements, other.colorRendererElements)) {
			return false;
		}
		return true;
	}
}
