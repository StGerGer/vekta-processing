package vekta.module;

import vekta.KeyBinding;
import vekta.mission.Mission;
import vekta.object.CargoCrate;
import vekta.object.SpaceObject;
import vekta.object.Targeter;
import vekta.object.planet.Asteroid;
import vekta.object.planet.TerrestrialPlanet;
import vekta.object.ship.Ship;

public class TargetingModule extends ShipModule implements Targeter {

	private TargetingMode mode;
	private SpaceObject target;

	public TargetingMode getMode() {
		return mode;
	}

	public void setMode(TargetingMode mode) {
		this.mode = mode;
		setTarget(null);

		// Immediately update ship's targeting instruments
		getShip().updateTargets();
	}

	@Override
	public SpaceObject getSpaceObject() {
		return getShip();
	}

	@Override
	public SpaceObject getTarget() {
		return target;
	}

	@Override
	public void setTarget(SpaceObject target) {
		this.target = target;
	}

	@Override
	public boolean isValidTarget(SpaceObject obj) {
		if(mode == null) {
			return obj == getTarget(); // Don't update targeter
		}
		switch(mode) {
		case PLANET:
			return obj instanceof TerrestrialPlanet && !(obj instanceof Asteroid);
		case ASTEROID:
			return obj instanceof Asteroid;
		case SHIP:
			return obj instanceof Ship || obj instanceof CargoCrate;
		default:
			return false;
		}
	}

	@Override
	public String getName() {
		return "Targeting Computer";
	}

	@Override
	public ModuleType getType() {
		return ModuleType.TARGET_COMPUTER;
	}

	@Override
	public boolean isBetter(Module other) {
		return false;
	}

	@Override
	public Module getVariant() {
		return new TargetingModule();
	}

	@Override
	public void onInstall() {
		setMode(null);
	}

	@Override
	public void onKeyPress(KeyBinding key) {
		switch(key) {
		case SHIP_TARGET_CLEAR:
			setMode(null);
			break;
		case SHIP_TARGET_PLANET:
			setMode(TargetingModule.TargetingMode.PLANET);
			break;
		case SHIP_TARGET_ASTEROID:
			setMode(TargetingModule.TargetingMode.ASTEROID);
			break;
		case SHIP_TARGET_SHIP:
			setMode(TargetingModule.TargetingMode.SHIP);
			break;
		case SHIP_TARGET_OBJECTIVE:
			setMode(null);
			if(getShip().hasController()) {
				Mission mission = getShip().getController().getCurrentMission();
				if(mission != null) {
					setTarget(mission.getCurrentObjective().getSpaceObject());
				}
			}
			break;
		}
	}

	public enum TargetingMode {
		PLANET,
		ASTEROID,
		SHIP,
	}
}
