/*
 * $Id:$
 *
 */
package net.sf.jame.contextfree.extensions.pathAdjustment;

import java.lang.Float;
import net.sf.jame.core.config.ValueChangeEvent;
import net.sf.jame.core.config.ValueChangeListener;
import net.sf.jame.core.config.ValueConfigElement;
import net.sf.jame.contextfree.cfdg.pathAdjustment.extension.PathAdjustmentExtensionRuntime;
import net.sf.jame.contextfree.renderer.ContextFreeState;

/**
 * @author Andrea Medeghini
 */
public class FlipPathAdjustmentRuntime extends PathAdjustmentExtensionRuntime<FlipPathAdjustmentConfig> {
	private Float angle;
	private AngleListener angleListener;
	private float delta;

	/**
	 * @see net.sf.jame.core.extension.ConfigurableExtensionRuntime#configReloaded()
	 */
	@Override
	public void configReloaded() {
		setAngle(getConfig().getAngle());
		angleListener = new AngleListener();
		getConfig().getAngleElement().addChangeListener(angleListener);
	}

	@Override
	public void dispose() {
		if ((getConfig() != null) && (angleListener != null)) {
			getConfig().getAngleElement().removeChangeListener(angleListener);
		}
		angleListener = null;
		super.dispose();
	}
	
	/**
	 * @return the angle.
	 */
	public Float getAngle() {
		return angle;
	}

	private void setAngle(final Float angle) {
		this.angle = angle;
	}
	
	private class AngleListener implements ValueChangeListener {
		/**
		 * @see net.sf.jame.core.config.ValueChangeListener#valueChanged(net.sf.jame.core.config.ValueChangeEvent)
		 */
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}

	@Override
	public void configureState(ContextFreeState state, int times) {
		// TODO Auto-generated method stub
		if (times == 0) {
			state.setFlip(angle);
			return;
		}
		delta = state.getFlip() - angle;
	}

	@Override
	public void updateState(ContextFreeState state, int time) {
		// TODO Auto-generated method stub
		state.setFlip(delta * time);
	}
}
