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
package net.sf.jame.mandelbrot.swing.extensions.inputAdapter;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import net.sf.jame.core.extension.ConfigurableExtensionReference;
import net.sf.jame.core.util.DoubleVector2D;
import net.sf.jame.core.util.DoubleVector4D;
import net.sf.jame.core.util.IntegerVector2D;
import net.sf.jame.core.util.IntegerVector4D;
import net.sf.jame.core.util.Rectangle;
import net.sf.jame.mandelbrot.MandelbrotConfig;
import net.sf.jame.mandelbrot.MandelbrotInputHandler;
import net.sf.jame.mandelbrot.MandelbrotInputManager;
import net.sf.jame.mandelbrot.MandelbrotZoom;
import net.sf.jame.mandelbrot.MandelbrotZoomHandler;
import net.sf.jame.mandelbrot.extensions.image.MandelbrotImageConfig;
import net.sf.jame.mandelbrot.fractal.MandelbrotFractalConfigElement;
import net.sf.jame.mandelbrot.renderingFormula.RenderingFormulaConfigElement;
import net.sf.jame.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionConfig;
import net.sf.jame.twister.common.SpeedElement;
import net.sf.jame.twister.common.ViewElement;
import net.sf.jame.twister.swing.inputAdapter.DefaultInputAdapterRuntime;
import net.sf.jame.twister.util.Speed;
import net.sf.jame.twister.util.View;

/**
 * @author Andrea Medeghini
 */
public class MandelbrotInputAdapterRuntime extends DefaultInputAdapterRuntime {
	private final MandelbrotInputManager inputManager = new MandelbrotInputManager(new MandelbrotZoomHandlerImpl());

	/**
	 * 
	 */
	public MandelbrotInputAdapterRuntime() {
		inputManager.setInputHandlerEnabled(true);
		inputManager.startInputHandler();
	}

	/**
	 * @see net.sf.jame.twister.swing.inputAdapter.DefaultInputAdapterRuntime#refresh()
	 */
	@Override
	public void refresh() {
		inputManager.runInputHandler();
	}

	/**
	 * @see net.sf.jame.twister.swing.inputAdapter.DefaultInputAdapterRuntime#processKeyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void processKeyPressed(final KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_J: {
				final MandelbrotImageConfig config = (MandelbrotImageConfig) getAdapterContext().getConfig();
				if ((config != null) && (config.getMandelbrotConfig() != null)) {
					if (config.getMandelbrotConfig().getImageMode() != MandelbrotImageConfig.IMAGE_MODE_JULIA) {
						config.getContext().updateTimestamp();
						config.getMandelbrotConfig().setImageMode(MandelbrotImageConfig.IMAGE_MODE_JULIA);
						final ViewElement viewElement = getViewElement();
						if (viewElement != null) {
							View view = viewElement.getValue();
							getAdapterContext().setAttribute("lastMandelbrotView", view);
							view = (View) getAdapterContext().getAttribute("lastJuliaView");
							if (view == null) {
								final IntegerVector4D status = new IntegerVector4D(0, 0, 2, 0);
								final DoubleVector4D position = new DoubleVector4D(0, 0, 1, 0);
								final DoubleVector4D rotation = new DoubleVector4D(0, 0, 0, 0);
								view = new View(status, position, rotation);
							}
							// getRenderContext().stopRenderers();
							viewElement.setValue(view);
							// getRenderContext().startRenderers();
						}
					}
					else {
						final ViewElement viewElement = getViewElement();
						if (viewElement != null) {
							final IntegerVector4D status = new IntegerVector4D(0, 0, 2, 0);
							final DoubleVector4D position = new DoubleVector4D(0, 0, 1, 0);
							final DoubleVector4D rotation = new DoubleVector4D(0, 0, 0, 0);
							final View view = new View(status, position, rotation);
							// getRenderContext().stopRenderers();
							config.getContext().updateTimestamp();
							viewElement.setValue(view);
							// getRenderContext().startRenderers();
						}
					}
				}
				break;
			}
			case KeyEvent.VK_M: {
				final MandelbrotImageConfig config = (MandelbrotImageConfig) getAdapterContext().getConfig();
				if ((config != null) && (config.getMandelbrotConfig() != null)) {
					if (config.getMandelbrotConfig().getImageMode() != MandelbrotImageConfig.IMAGE_MODE_MANDELBROT) {
						config.getContext().updateTimestamp();
						config.getMandelbrotConfig().setImageMode(MandelbrotImageConfig.IMAGE_MODE_MANDELBROT);
						final ViewElement viewElement = getViewElement();
						if (viewElement != null) {
							View view = viewElement.getValue();
							getAdapterContext().setAttribute("lastJuliaView", view);
							view = (View) getAdapterContext().getAttribute("lastMandelbrotView");
							if (view == null) {
								final IntegerVector4D status = new IntegerVector4D(0, 0, 2, 0);
								final DoubleVector4D position = new DoubleVector4D(0, 0, 1, 0);
								final DoubleVector4D rotation = new DoubleVector4D(0, 0, 0, 0);
								view = new View(status, position, rotation);
							}
							// getRenderContext().stopRenderers();
							viewElement.setValue(view);
							// getRenderContext().startRenderers();
						}
					}
					else {
						final ViewElement viewElement = getViewElement();
						if (viewElement != null) {
							final IntegerVector4D status = new IntegerVector4D(0, 0, 2, 0);
							final DoubleVector4D position = new DoubleVector4D(0, 0, 1, 0);
							final DoubleVector4D rotation = new DoubleVector4D(0, 0, 0, 0);
							final View view = new View(status, position, rotation);
							// getRenderContext().stopRenderers();
							config.getContext().updateTimestamp();
							viewElement.setValue(view);
							// getRenderContext().startRenderers();
						}
					}
				}
				break;
			}
			case KeyEvent.VK_Z: {
				final MandelbrotImageConfig config = (MandelbrotImageConfig) getAdapterContext().getConfig();
				if (config.getMandelbrotConfig() != null) {
					if (config.getMandelbrotConfig().getInputMode() != MandelbrotImageConfig.INPUT_MODE_ZOOM) {
						final MandelbrotInputHandler mandelbrotHandler = inputManager.getInputHandler();
						mandelbrotHandler.setZoomDirection(MandelbrotInputHandler.DIRECTION_UNDEFINED);
						mandelbrotHandler.setShiftDirection(MandelbrotInputHandler.DIRECTION_UNDEFINED);
						mandelbrotHandler.setRotationDirection(MandelbrotInputHandler.DIRECTION_UNDEFINED);
						mandelbrotHandler.setZoomEnabled(false);
						mandelbrotHandler.setShiftEnabled(false);
						mandelbrotHandler.setRotationEnabled(false);
						config.getContext().updateTimestamp();
						config.getMandelbrotConfig().getInputModeElement().setValue(new Integer(MandelbrotImageConfig.INPUT_MODE_ZOOM));
					}
				}
				break;
			}
			case KeyEvent.VK_S: {
				final MandelbrotImageConfig config = (MandelbrotImageConfig) getAdapterContext().getConfig();
				if (config.getMandelbrotConfig() != null) {
					if (config.getMandelbrotConfig().getInputMode() != MandelbrotImageConfig.INPUT_MODE_SELECT) {
						final MandelbrotInputHandler mandelbrotHandler = inputManager.getInputHandler();
						mandelbrotHandler.setZoomDirection(MandelbrotInputHandler.DIRECTION_UNDEFINED);
						mandelbrotHandler.setShiftDirection(MandelbrotInputHandler.DIRECTION_UNDEFINED);
						mandelbrotHandler.setRotationDirection(MandelbrotInputHandler.DIRECTION_UNDEFINED);
						mandelbrotHandler.setZoomEnabled(false);
						mandelbrotHandler.setShiftEnabled(false);
						mandelbrotHandler.setRotationEnabled(false);
						config.getContext().updateTimestamp();
						config.getMandelbrotConfig().getInputModeElement().setValue(new Integer(MandelbrotImageConfig.INPUT_MODE_SELECT));
					}
				}
				break;
			}
			case KeyEvent.VK_R: {
				final MandelbrotImageConfig config = (MandelbrotImageConfig) getAdapterContext().getConfig();
				if (config.getMandelbrotConfig() != null) {
					config.getContext().updateTimestamp();
					config.getMandelbrotConfig().setPreviewArea(config.getMandelbrotConfig().getPreviewAreaElement().getDefaultValue());
				}
				break;
			}
			case KeyEvent.VK_P: {
				final MandelbrotImageConfig config = (MandelbrotImageConfig) getAdapterContext().getConfig();
				if (config.getMandelbrotConfig() != null) {
					config.getContext().updateTimestamp();
					config.getMandelbrotConfig().setShowPreview(!config.getMandelbrotConfig().getShowPreview());
				}
				break;
			}
			case KeyEvent.VK_T: {
				final MandelbrotImageConfig config = (MandelbrotImageConfig) getAdapterContext().getConfig();
				if (config.getMandelbrotConfig() != null) {
					config.getContext().updateTimestamp();
					config.getMandelbrotConfig().setShowOrbitTrap(!config.getMandelbrotConfig().getShowOrbitTrap());
				}
				break;
			}
			case KeyEvent.VK_O: {
				final MandelbrotImageConfig config = (MandelbrotImageConfig) getAdapterContext().getConfig();
				if (config.getMandelbrotConfig() != null) {
					config.getContext().updateTimestamp();
					config.getMandelbrotConfig().setShowOrbit(!config.getMandelbrotConfig().getShowOrbit());
				}
				break;
			}
			case KeyEvent.VK_LEFT: {
				final MandelbrotImageConfig config = (MandelbrotImageConfig) getAdapterContext().getConfig();
				if ((config != null) && (config.getMandelbrotConfig() != null)) {
					final MandelbrotInputHandler mandelbrotHandler = inputManager.getInputHandler();
					mandelbrotHandler.setRotationDirection(MandelbrotInputHandler.DIRECTION_FORWARD);
					mandelbrotHandler.setRotationEnabled(true);
				}
				break;
			}
			case KeyEvent.VK_RIGHT: {
				final MandelbrotImageConfig config = (MandelbrotImageConfig) getAdapterContext().getConfig();
				if ((config != null) && (config.getMandelbrotConfig() != null)) {
					final MandelbrotInputHandler mandelbrotHandler = inputManager.getInputHandler();
					mandelbrotHandler.setRotationDirection(MandelbrotInputHandler.DIRECTION_BACKWARD);
					mandelbrotHandler.setRotationEnabled(true);
				}
				break;
			}
			case KeyEvent.VK_UP: {
				final MandelbrotImageConfig config = (MandelbrotImageConfig) getAdapterContext().getConfig();
				if ((config != null) && (config.getMandelbrotConfig() != null)) {
					final MandelbrotInputHandler mandelbrotHandler = inputManager.getInputHandler();
					mandelbrotHandler.setZoomDirection(MandelbrotInputHandler.DIRECTION_FORWARD);
					mandelbrotHandler.setZoomEnabled(true);
				}
				break;
			}
			case KeyEvent.VK_DOWN: {
				final MandelbrotImageConfig config = (MandelbrotImageConfig) getAdapterContext().getConfig();
				if ((config != null) && (config.getMandelbrotConfig() != null)) {
					final MandelbrotInputHandler mandelbrotHandler = inputManager.getInputHandler();
					mandelbrotHandler.setZoomDirection(MandelbrotInputHandler.DIRECTION_BACKWARD);
					mandelbrotHandler.setZoomEnabled(true);
				}
				break;
			}
			case KeyEvent.VK_PAGE_UP: {
				final MandelbrotImageConfig config = (MandelbrotImageConfig) getAdapterContext().getConfig();
				if ((config != null) && (config.getMandelbrotConfig() != null)) {
					final MandelbrotInputHandler mandelbrotHandler = inputManager.getInputHandler();
					mandelbrotHandler.setShiftDirection(MandelbrotInputHandler.DIRECTION_FORWARD);
					mandelbrotHandler.setShiftEnabled(true);
				}
				break;
			}
			case KeyEvent.VK_PAGE_DOWN: {
				final MandelbrotImageConfig config = (MandelbrotImageConfig) getAdapterContext().getConfig();
				if ((config != null) && (config.getMandelbrotConfig() != null)) {
					final MandelbrotInputHandler mandelbrotHandler = inputManager.getInputHandler();
					mandelbrotHandler.setShiftDirection(MandelbrotInputHandler.DIRECTION_BACKWARD);
					mandelbrotHandler.setShiftEnabled(true);
				}
				break;
			}
			default: {
				break;
			}
		}
	}

	/**
	 * @see net.sf.jame.twister.swing.inputAdapter.DefaultInputAdapterRuntime#processKeyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void processKeyReleased(final KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT: {
				final MandelbrotImageConfig config = (MandelbrotImageConfig) getAdapterContext().getConfig();
				if ((config != null) && (config.getMandelbrotConfig() != null)) {
					final MandelbrotInputHandler mandelbrotHandler = inputManager.getInputHandler();
					mandelbrotHandler.setRotationEnabled(false);
					mandelbrotHandler.setRotationDirection(MandelbrotInputHandler.DIRECTION_UNDEFINED);
				}
				break;
			}
			case KeyEvent.VK_RIGHT: {
				final MandelbrotImageConfig config = (MandelbrotImageConfig) getAdapterContext().getConfig();
				if ((config != null) && (config.getMandelbrotConfig() != null)) {
					final MandelbrotInputHandler mandelbrotHandler = inputManager.getInputHandler();
					mandelbrotHandler.setRotationEnabled(false);
					mandelbrotHandler.setRotationDirection(MandelbrotInputHandler.DIRECTION_UNDEFINED);
				}
				break;
			}
			case KeyEvent.VK_UP: {
				final MandelbrotImageConfig config = (MandelbrotImageConfig) getAdapterContext().getConfig();
				if ((config != null) && (config.getMandelbrotConfig() != null)) {
					final MandelbrotInputHandler mandelbrotHandler = inputManager.getInputHandler();
					mandelbrotHandler.setZoomEnabled(false);
					mandelbrotHandler.setZoomDirection(MandelbrotInputHandler.DIRECTION_UNDEFINED);
				}
				break;
			}
			case KeyEvent.VK_DOWN: {
				final MandelbrotImageConfig config = (MandelbrotImageConfig) getAdapterContext().getConfig();
				if ((config != null) && (config.getMandelbrotConfig() != null)) {
					final MandelbrotInputHandler mandelbrotHandler = inputManager.getInputHandler();
					mandelbrotHandler.setZoomEnabled(false);
					mandelbrotHandler.setZoomDirection(MandelbrotInputHandler.DIRECTION_UNDEFINED);
				}
				break;
			}
			case KeyEvent.VK_PAGE_UP: {
				final MandelbrotImageConfig config = (MandelbrotImageConfig) getAdapterContext().getConfig();
				if ((config != null) && (config.getMandelbrotConfig() != null)) {
					final MandelbrotInputHandler mandelbrotHandler = inputManager.getInputHandler();
					mandelbrotHandler.setShiftEnabled(false);
					mandelbrotHandler.setShiftDirection(MandelbrotInputHandler.DIRECTION_UNDEFINED);
				}
				break;
			}
			case KeyEvent.VK_PAGE_DOWN: {
				final MandelbrotImageConfig config = (MandelbrotImageConfig) getAdapterContext().getConfig();
				if ((config != null) && (config.getMandelbrotConfig() != null)) {
					final MandelbrotInputHandler mandelbrotHandler = inputManager.getInputHandler();
					mandelbrotHandler.setShiftEnabled(false);
					mandelbrotHandler.setShiftDirection(MandelbrotInputHandler.DIRECTION_UNDEFINED);
				}
				break;
			}
			default: {
				break;
			}
		}
	}

	/**
	 * @see net.sf.jame.twister.swing.inputAdapter.DefaultInputAdapterRuntime#processKeyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void processKeyTyped(final KeyEvent e) {
	}

	/**
	 * @see net.sf.jame.twister.swing.inputAdapter.DefaultInputAdapterRuntime#processMouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void processMouseClicked(final MouseEvent e) {
		final MandelbrotImageConfig config = (MandelbrotImageConfig) getAdapterContext().getConfig();
		if (config.getMandelbrotConfig() != null) {
			if (config.getMandelbrotConfig().getShowPreview()) {
				final Rectangle previewArea = config.getMandelbrotConfig().getPreviewArea();
				final double x = (double) e.getX() / (double) getSizeX();
				final double y = (double) e.getY() / (double) getSizeY();
				if ((x > previewArea.getX()) && (x < previewArea.getX() + previewArea.getW())) {
					if ((y > previewArea.getY()) && (y < previewArea.getY() + previewArea.getH())) {
						if (config.getMandelbrotConfig().getImageMode() == MandelbrotImageConfig.IMAGE_MODE_JULIA) {
							config.getContext().updateTimestamp();
							config.getMandelbrotConfig().setImageMode(MandelbrotImageConfig.IMAGE_MODE_MANDELBROT);
							final ViewElement viewElement = getViewElement();
							if (viewElement != null) {
								View view = viewElement.getValue();
								getAdapterContext().setAttribute("lastJuliaView", view);
								view = (View) getAdapterContext().getAttribute("lastMandelbrotView");
								if (view == null) {
									final IntegerVector4D status = new IntegerVector4D(0, 0, 0, 0);
									final DoubleVector4D position = new DoubleVector4D(0, 0, 1, 0);
									final DoubleVector4D rotation = new DoubleVector4D(0, 0, 0, 0);
									view = new View(status, position, rotation);
								}
								// getRenderContext().stopRenderers();
								viewElement.setValue(view);
								// getRenderContext().startRenderers();
							}
							return;
						}
						if (config.getMandelbrotConfig().getImageMode() == MandelbrotImageConfig.IMAGE_MODE_MANDELBROT) {
							config.getContext().updateTimestamp();
							config.getMandelbrotConfig().setImageMode(MandelbrotImageConfig.IMAGE_MODE_JULIA);
							final ViewElement viewElement = getViewElement();
							if (viewElement != null) {
								View view = viewElement.getValue();
								getAdapterContext().setAttribute("lastMandelbrotView", view);
								view = (View) getAdapterContext().getAttribute("lastJuliaView");
								if (view == null) {
									final IntegerVector4D status = new IntegerVector4D(0, 0, 0, 0);
									final DoubleVector4D position = new DoubleVector4D(0, 0, 1, 0);
									final DoubleVector4D rotation = new DoubleVector4D(0, 0, 0, 0);
									view = new View(status, position, rotation);
								}
								// getRenderContext().stopRenderers();
								viewElement.setValue(view);
								// getRenderContext().startRenderers();
							}
							return;
						}
					}
				}
			}
			if ((config.getMandelbrotConfig().getInputMode() == MandelbrotImageConfig.INPUT_MODE_SELECT) && !e.isControlDown()) {
				final MandelbrotInputHandler mandelbrotHandler = inputManager.getInputHandler();
				mandelbrotHandler.setLastMousePosition(e.getX(), e.getY());
				final ViewElement viewElement = getViewElement();
				if ((config.getMandelbrotConfig() != null) && (viewElement != null)) {
					View view = viewElement.getValue();
					double x = view.getPosition().getX();
					double y = view.getPosition().getY();
					final double z = view.getPosition().getZ();
					final double a = view.getRotation().getZ();
					final RenderingFormulaExtensionConfig formulaConfig = getRenderingFormulaExtensionConfig();
					if ((formulaConfig != null) && config.getMandelbrotConfig().isMandelbrotMode()) {
						final DoubleVector2D center = formulaConfig.getCenter();
						final DoubleVector2D scale = formulaConfig.getScale();
						x = x + center.getX() + (z * scale.getX()) * ((Math.cos(a) * mandelbrotHandler.getNormalizedLastMousePositionX()) + (Math.sin(a) * mandelbrotHandler.getNormalizedLastMousePositionY()));
						y = y + center.getY() + (z * scale.getY()) * ((Math.cos(a) * mandelbrotHandler.getNormalizedLastMousePositionY()) - (Math.sin(a) * mandelbrotHandler.getNormalizedLastMousePositionX()));
						getAdapterContext().setAttribute("lastMandelbrotView", view);
						final IntegerVector4D status = new IntegerVector4D(0, 0, 0, 0);
						final DoubleVector4D position = new DoubleVector4D(0, 0, 1, 0);
						final DoubleVector4D rotation = new DoubleVector4D(0, 0, 0, 0);
						view = new View(status, position, rotation);
						// getRenderContext().stopRenderers();
						config.getContext().updateTimestamp();
						config.getMandelbrotConfig().setConstant(new DoubleVector2D(x, y));
						config.getMandelbrotConfig().setImageMode(1);
						getAdapterContext().setAttribute("lastJuliaView", view);
						viewElement.setValue(view);
						config.getMandelbrotConfig().setInputMode(new Integer(0));
						// getRenderContext().startRenderers();
					}
				}
			}
			if ((config.getMandelbrotConfig().getInputMode() == MandelbrotImageConfig.INPUT_MODE_SELECT) && e.isShiftDown()) {
				final MandelbrotInputHandler mandelbrotHandler = inputManager.getInputHandler();
				mandelbrotHandler.setLastMousePosition(e.getX(), e.getY());
				final ViewElement viewElement = getViewElement();
				if ((config.getMandelbrotConfig() != null) && (viewElement != null)) {
					View view = viewElement.getValue();
					double x = view.getPosition().getX();
					double y = view.getPosition().getY();
					final double z = view.getPosition().getZ();
					final double a = view.getRotation().getZ();
					final RenderingFormulaExtensionConfig formulaConfig = getRenderingFormulaExtensionConfig();
					if (formulaConfig != null) {
						final DoubleVector2D center = formulaConfig.getCenter();
						final DoubleVector2D scale = formulaConfig.getScale();
						x = x + center.getX() + (z * scale.getX()) * ((Math.cos(a) * mandelbrotHandler.getNormalizedLastMousePositionX()) + (Math.sin(a) * mandelbrotHandler.getNormalizedLastMousePositionY()));
						y = y + center.getY() + (z * scale.getY()) * ((Math.cos(a) * mandelbrotHandler.getNormalizedLastMousePositionY()) - (Math.sin(a) * mandelbrotHandler.getNormalizedLastMousePositionX()));
						// getRenderContext().stopRenderers();
						config.getContext().updateTimestamp();
						config.getMandelbrotConfig().getMandelbrotFractal().getOrbitTrapConfigElement().setCenter(new DoubleVector2D(x, -y));
						// getRenderContext().refresh();
						// getRenderContext().startRenderers();
					}
				}
			}
		}
	}

	/**
	 * @see net.sf.jame.twister.swing.inputAdapter.DefaultInputAdapterRuntime#processMouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void processMouseDragged(final MouseEvent e) {
		final MandelbrotImageConfig config = (MandelbrotImageConfig) getAdapterContext().getConfig();
		if (config.getMandelbrotConfig() != null) {
			final IntegerVector2D startMousePosition = (IntegerVector2D) getAdapterContext().getAttribute("startMousePosition");
			final Rectangle oldArea = (Rectangle) getAdapterContext().getAttribute("oldArea");
			if ((startMousePosition != null) && (oldArea != null)) {
				final double dx = (e.getX() - startMousePosition.getX()) / (double) getSizeX();
				final double dy = (e.getY() - startMousePosition.getY()) / (double) getSizeY();
				final Rectangle newArea = new Rectangle(oldArea.getX() + dx, oldArea.getY() + dy, oldArea.getW(), oldArea.getH());
				config.getContext().updateTimestamp();
				config.getMandelbrotConfig().setPreviewArea(newArea);
				return;
			}
			if (config.getMandelbrotConfig().getInputMode() == MandelbrotImageConfig.INPUT_MODE_ZOOM) {
				final MandelbrotInputHandler mandelbrotHandler = inputManager.getInputHandler();
				mandelbrotHandler.setLastMousePosition(e.getX(), e.getY());
			}
			if ((config.getMandelbrotConfig().getInputMode() == MandelbrotImageConfig.INPUT_MODE_SELECT) && e.isShiftDown()) {
				final MandelbrotInputHandler mandelbrotHandler = inputManager.getInputHandler();
				mandelbrotHandler.setLastMousePosition(e.getX(), e.getY());
				final ViewElement viewElement = getViewElement();
				if ((config.getMandelbrotConfig() != null) && (viewElement != null)) {
					View view = viewElement.getValue();
					double x = view.getPosition().getX();
					double y = view.getPosition().getY();
					final double z = view.getPosition().getZ();
					final double a = view.getRotation().getZ();
					final RenderingFormulaExtensionConfig formulaConfig = getRenderingFormulaExtensionConfig();
					if (formulaConfig != null) {
						final DoubleVector2D center = formulaConfig.getCenter();
						final DoubleVector2D scale = formulaConfig.getScale();
						x = x + center.getX() + (z * scale.getX()) * ((Math.cos(a) * mandelbrotHandler.getNormalizedLastMousePositionX()) + (Math.sin(a) * mandelbrotHandler.getNormalizedLastMousePositionY()));
						y = y + center.getY() + (z * scale.getY()) * ((Math.cos(a) * mandelbrotHandler.getNormalizedLastMousePositionY()) - (Math.sin(a) * mandelbrotHandler.getNormalizedLastMousePositionX()));
						// getRenderContext().stopRenderers();
						config.getContext().updateTimestamp();
						config.getMandelbrotConfig().getMandelbrotFractal().getOrbitTrapConfigElement().setCenter(new DoubleVector2D(x, -y));
						// getRenderContext().refresh();
						// getRenderContext().startRenderers();
					}
				}
			}
		}
	}

	/**
	 * @see net.sf.jame.twister.swing.inputAdapter.DefaultInputAdapterRuntime#processMouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void processMouseEntered(final MouseEvent e) {
	}

	/**
	 * @see net.sf.jame.twister.swing.inputAdapter.DefaultInputAdapterRuntime#processMouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void processMouseExited(final MouseEvent e) {
	}

	/**
	 * @see net.sf.jame.twister.swing.inputAdapter.DefaultInputAdapterRuntime#processMouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void processMouseMoved(final MouseEvent e) {
		final MandelbrotImageConfig config = (MandelbrotImageConfig) getAdapterContext().getConfig();
		if (config.getMandelbrotConfig() != null) {
			if (config.getMandelbrotConfig().getInputMode() == MandelbrotImageConfig.INPUT_MODE_ZOOM) {
				final MandelbrotInputHandler mandelbrotHandler = inputManager.getInputHandler();
				mandelbrotHandler.setLastMousePosition(e.getX(), e.getY());
			}
			else {
				final MandelbrotInputHandler mandelbrotHandler = inputManager.getInputHandler();
				mandelbrotHandler.setLastMousePosition(e.getX(), e.getY());
				final ViewElement viewElement = getViewElement();
				if (viewElement != null) {
					final View view = viewElement.getValue();
					double x = view.getPosition().getX();
					double y = view.getPosition().getY();
					final double z = view.getPosition().getZ();
					final double a = view.getRotation().getZ();
					final RenderingFormulaExtensionConfig formulaConfig = getRenderingFormulaExtensionConfig();
					if ((formulaConfig != null) && config.getMandelbrotConfig().isMandelbrotMode()) {
						final DoubleVector2D center = formulaConfig.getCenter();
						final DoubleVector2D scale = formulaConfig.getScale();
						x = x + center.getX() + (z * scale.getX()) * ((Math.cos(a) * mandelbrotHandler.getNormalizedLastMousePositionX()) + (Math.sin(a) * mandelbrotHandler.getNormalizedLastMousePositionY()));
						y = y + center.getY() + (z * scale.getY()) * ((Math.cos(a) * mandelbrotHandler.getNormalizedLastMousePositionY()) - (Math.sin(a) * mandelbrotHandler.getNormalizedLastMousePositionX()));
						config.getContext().updateTimestamp();
						config.getMandelbrotConfig().setConstant(new DoubleVector2D(x, y));
					}
				}
			}
		}
	}

	/**
	 * @see net.sf.jame.twister.swing.inputAdapter.DefaultInputAdapterRuntime#processMousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void processMousePressed(final MouseEvent e) {
		final MandelbrotImageConfig config = (MandelbrotImageConfig) getAdapterContext().getConfig();
		if (config.getMandelbrotConfig() != null) {
			if (config.getMandelbrotConfig().getShowPreview()) {
				final Rectangle previewArea = config.getMandelbrotConfig().getPreviewArea();
				final double x = (double) e.getX() / (double) getSizeX();
				final double y = (double) e.getY() / (double) getSizeY();
				if ((x > previewArea.getX()) && (x < previewArea.getX() + previewArea.getW())) {
					if ((y > previewArea.getY()) && (y < previewArea.getY() + previewArea.getH())) {
						getAdapterContext().setAttribute("startMousePosition", new IntegerVector2D(e.getX(), e.getY()));
						getAdapterContext().setAttribute("oldArea", previewArea);
						return;
					}
				}
			}
			if (config.getMandelbrotConfig().getInputMode() == MandelbrotImageConfig.INPUT_MODE_ZOOM) {
				final MandelbrotInputHandler mandelbrotHandler = inputManager.getInputHandler();
				mandelbrotHandler.setLastMousePosition(e.getX(), e.getY());
				mandelbrotHandler.setStartMousePosition(e.getX(), e.getY());
				if (e.isControlDown()) {
					mandelbrotHandler.setRotationEnabled(false);
					mandelbrotHandler.setShiftEnabled(false);
					mandelbrotHandler.setZoomEnabled(false);
					switch (e.getButton()) {
						case (MouseEvent.BUTTON1): {
							mandelbrotHandler.setShiftDirection(MandelbrotInputHandler.DIRECTION_FORWARD);
							break;
						}
						case (MouseEvent.BUTTON2): {
							mandelbrotHandler.setShiftDirection(MandelbrotInputHandler.DIRECTION_UNDEFINED);
							break;
						}
						case (MouseEvent.BUTTON3): {
							mandelbrotHandler.setShiftDirection(MandelbrotInputHandler.DIRECTION_BACKWARD);
							break;
						}
					}
					mandelbrotHandler.setShiftEnabled(true);
				}
				else if (e.isShiftDown()) {
					mandelbrotHandler.setRotationEnabled(false);
					mandelbrotHandler.setShiftEnabled(false);
					mandelbrotHandler.setZoomEnabled(false);
					switch (e.getButton()) {
						case (MouseEvent.BUTTON1): {
							mandelbrotHandler.setRotationDirection(MandelbrotInputHandler.DIRECTION_FORWARD);
							break;
						}
						case (MouseEvent.BUTTON2): {
							mandelbrotHandler.setRotationDirection(MandelbrotInputHandler.DIRECTION_UNDEFINED);
							break;
						}
						case (MouseEvent.BUTTON3): {
							mandelbrotHandler.setRotationDirection(MandelbrotInputHandler.DIRECTION_BACKWARD);
							break;
						}
					}
					mandelbrotHandler.setRotationEnabled(true);
				}
				else {
					mandelbrotHandler.setRotationEnabled(false);
					mandelbrotHandler.setShiftEnabled(false);
					mandelbrotHandler.setZoomEnabled(false);
					switch (e.getButton()) {
						case (MouseEvent.BUTTON1): {
							mandelbrotHandler.setZoomDirection(MandelbrotInputHandler.DIRECTION_FORWARD);
							break;
						}
						case (MouseEvent.BUTTON2): {
							mandelbrotHandler.setZoomDirection(MandelbrotInputHandler.DIRECTION_UNDEFINED);
							break;
						}
						case (MouseEvent.BUTTON3): {
							mandelbrotHandler.setZoomDirection(MandelbrotInputHandler.DIRECTION_BACKWARD);
							break;
						}
					}
					mandelbrotHandler.setZoomEnabled(true);
				}
			}
		}
	}

	/**
	 * @see net.sf.jame.twister.swing.inputAdapter.DefaultInputAdapterRuntime#processMouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void processMouseReleased(final MouseEvent e) {
		final MandelbrotImageConfig config = (MandelbrotImageConfig) getAdapterContext().getConfig();
		if (config.getMandelbrotConfig() != null) {
			final IntegerVector2D startMousePosition = (IntegerVector2D) getAdapterContext().getAttribute("startMousePosition");
			final Rectangle oldArea = (Rectangle) getAdapterContext().getAttribute("oldArea");
			if ((startMousePosition != null) && (oldArea != null)) {
				final double dx = (e.getX() - startMousePosition.getX()) / (double) getSizeX();
				final double dy = (e.getY() - startMousePosition.getY()) / (double) getSizeY();
				final Rectangle newArea = new Rectangle(oldArea.getX() + dx, oldArea.getY() + dy, oldArea.getW(), oldArea.getH());
				config.getContext().updateTimestamp();
				config.getMandelbrotConfig().setPreviewArea(newArea);
				getAdapterContext().removeAttribute("startMousePosition");
				getAdapterContext().removeAttribute("oldArea");
				return;
			}
			if (config.getMandelbrotConfig().getInputMode() == MandelbrotImageConfig.INPUT_MODE_ZOOM) {
				final MandelbrotInputHandler mandelbrotHandler = inputManager.getInputHandler();
				mandelbrotHandler.setLastMousePosition(e.getX(), e.getY());
				mandelbrotHandler.setStartMousePosition(e.getX(), e.getY());
				mandelbrotHandler.setZoomDirection(MandelbrotInputHandler.DIRECTION_UNDEFINED);
				mandelbrotHandler.setShiftDirection(MandelbrotInputHandler.DIRECTION_UNDEFINED);
				mandelbrotHandler.setRotationDirection(MandelbrotInputHandler.DIRECTION_UNDEFINED);
				mandelbrotHandler.setZoomEnabled(false);
				mandelbrotHandler.setShiftEnabled(false);
				mandelbrotHandler.setRotationEnabled(false);
			}
		}
	}

	private RenderingFormulaExtensionConfig getRenderingFormulaExtensionConfig() {
		final MandelbrotImageConfig imageConfig = (MandelbrotImageConfig) getAdapterContext().getConfig();
		if (imageConfig != null) {
			final MandelbrotConfig mandelbrotConfig = imageConfig.getMandelbrotConfig();
			if (mandelbrotConfig != null) {
				final MandelbrotFractalConfigElement fractalElement = mandelbrotConfig.getMandelbrotFractal();
				if (fractalElement != null) {
					final RenderingFormulaConfigElement formulaElement = fractalElement.getRenderingFormulaConfigElement();
					if (formulaElement != null) {
						final ConfigurableExtensionReference<RenderingFormulaExtensionConfig> reference = formulaElement.getReference();
						if (reference != null) {
							return reference.getExtensionConfig();
						}
					}
				}
			}
		}
		return null;
	}

	private ViewElement getViewElement() {
		final MandelbrotImageConfig imageConfig = (MandelbrotImageConfig) getAdapterContext().getConfig();
		if (imageConfig != null) {
			final MandelbrotConfig mandelbrotConfig = imageConfig.getMandelbrotConfig();
			if (mandelbrotConfig != null) {
				return mandelbrotConfig.getViewElement();
			}
		}
		return null;
	}

	private SpeedElement getSpeedElement() {
		final MandelbrotImageConfig imageConfig = (MandelbrotImageConfig) getAdapterContext().getConfig();
		if (imageConfig != null) {
			final MandelbrotConfig mandelbrotConfig = imageConfig.getMandelbrotConfig();
			if (mandelbrotConfig != null) {
				return mandelbrotConfig.getSpeedElement();
			}
		}
		return null;
	}

	private int getSizeX() {
		return getRenderContext().getImageSize().getX();
	}

	private int getSizeY() {
		return getRenderContext().getImageSize().getY();
	}

	private class MandelbrotZoomHandlerImpl implements MandelbrotZoomHandler {
		/**
		 * @see net.sf.jame.mandelbrot.MandelbrotZoomHandler#doZoom(net.sf.jame.mandelbrot.MandelbrotZoom)
		 */
		public void doZoom(final MandelbrotZoom zoom) {
			double zoomSpeed = 1.0;
			double shiftSpeed = 1.0;
			double rotationSpeed = Math.PI / 180.0;
			final ViewElement viewElement = getViewElement();
			final SpeedElement speedElement = getSpeedElement();
			Speed speed = speedElement.getValue();
			zoomSpeed = zoomSpeed + Math.abs((speed.getPosition().getZ() / 1000));
			shiftSpeed = Math.abs(speed.getPosition().getW());
			rotationSpeed = Math.abs((((Math.PI / 180.0) * speed.getRotation().getZ()) / 100));
			if (viewElement != null) {
				View view = viewElement.getValue();
				double x = view.getPosition().getX();
				double y = view.getPosition().getY();
				double z = view.getPosition().getZ();
				double a = view.getRotation().getZ();
				double s = view.getRotation().getW();
				if (zoom.rotationEnabled) {
					if (zoom.rotationDirection != 0) {
						a = a + ((zoom.rotationDirection > 0) ? (+rotationSpeed) : (-rotationSpeed));
					}
				}
				if (zoom.shiftEnabled) {
					if (zoom.shiftDirection != 0) {
						s = s + ((zoom.shiftDirection > 0) ? (+shiftSpeed) : (-shiftSpeed));
					}
				}
				if (s < 0) {
					s = 0;
				}
				if (zoom.zoomEnabled) {
					if (zoom.zoomDirection != 0) {
						final double zs = (zoom.zoomDirection > 0) ? zoomSpeed : (1.0 / zoomSpeed);
						final RenderingFormulaExtensionConfig formulaConfig = getRenderingFormulaExtensionConfig();
						if (formulaConfig != null) {
							final DoubleVector2D scale = formulaConfig.getScale();
							x = x - (zs - 1) * (z * scale.getX()) * ((Math.cos(a) * zoom.lastX) + (Math.sin(a) * zoom.lastY));
							y = y - (zs - 1) * (z * scale.getY()) * ((Math.cos(a) * zoom.lastY) - (Math.sin(a) * zoom.lastX));
							z = z * zs;
							final IntegerVector4D status = new IntegerVector4D(0, 0, zoom.zoomEnabled || zoom.rotationEnabled ? 1 : 0, zoom.shiftEnabled ? 1 : 0);
							final DoubleVector4D position = new DoubleVector4D(x, y, z, 0);
							final DoubleVector4D rotation = new DoubleVector4D(0, 0, a, s);
							view = new View(status, position, rotation);
						}
					}
					else {
						final RenderingFormulaExtensionConfig formulaConfig = getRenderingFormulaExtensionConfig();
						if (formulaConfig != null) {
							final DoubleVector2D scale = formulaConfig.getScale();
							x = x - (z * scale.getX()) * ((Math.cos(a) * (zoom.lastX - zoom.startX)) + (Math.sin(a) * (zoom.lastY - zoom.startY)));
							y = y - (z * scale.getY()) * ((Math.cos(a) * (zoom.lastY - zoom.startY)) - (Math.sin(a) * (zoom.lastX - zoom.startX)));
							final IntegerVector4D status = new IntegerVector4D(0, 0, zoom.zoomEnabled || zoom.rotationEnabled ? 1 : 0, zoom.shiftEnabled ? 1 : 0);
							final DoubleVector4D position = new DoubleVector4D(x, y, z, 0);
							final DoubleVector4D rotation = new DoubleVector4D(0, 0, a, s);
							view = new View(status, position, rotation);
						}
					}
				}
				else {
					final IntegerVector4D status = new IntegerVector4D(0, 0, zoom.zoomEnabled || zoom.rotationEnabled ? 1 : 0, zoom.shiftEnabled ? 1 : 0);
					final DoubleVector4D position = new DoubleVector4D(x, y, z, 0);
					final DoubleVector4D rotation = new DoubleVector4D(0, 0, a, s);
					view = new View(status, position, rotation);
				}
				getAdapterContext().getConfig().getContext().updateTimestamp();
				viewElement.setValue(view);
			}
		}

		/**
		 * @see net.sf.jame.mandelbrot.MandelbrotZoomHandler#getWidth()
		 */
		public int getWidth() {
			return getSizeX();
		}

		/**
		 * @see net.sf.jame.mandelbrot.MandelbrotZoomHandler#getHeight()
		 */
		public int getHeight() {
			return getSizeY();
		}
	}
}
