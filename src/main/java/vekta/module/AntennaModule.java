package vekta.module;

import vekta.menu.Menu;
import vekta.menu.handle.ObjectMenuHandle;
import vekta.menu.option.InternetMenuOption;
import vekta.object.SpaceObject;
import vekta.object.planet.TerrestrialPlanet;

import static processing.core.PApplet.sq;
import static vekta.Vekta.AU_DISTANCE;
import static vekta.Vekta.getWorld;

public class AntennaModule extends ShipModule {
	private static final float RANGE_SCALE = AU_DISTANCE;

	private final float range;

	public AntennaModule() {
		this(1);
	}

	public AntennaModule(float range) {
		this.range = range;
	}

	public float getRange() {
		return range;
	}

	@Override
	public String getName() {
		return "Internet Antenna v" + getRange();
	}

	@Override
	public ModuleType getType() {
		return ModuleType.ANTENNA;
	}

	@Override
	public boolean isBetter(Module other) {
		return other instanceof AntennaModule && getRange() > ((AntennaModule)other).getRange();
	}

	@Override
	public Module getVariant() {
		return new AntennaModule(chooseInclusive(.5F, 10, .5F));
	}

	@Override
	public void onMenu(Menu menu) {
		if(menu.getHandle() instanceof ObjectMenuHandle && ((ObjectMenuHandle)menu.getHandle()).getSpaceObject() == getShip()) {
			boolean connected = findRelay() != null;
			menu.add(new InternetMenuOption(connected));
		}
	}

	private SpaceObject findRelay() {
		for(TerrestrialPlanet planet : getWorld().findObjects(TerrestrialPlanet.class)) {
			if(planet.getTerrain().isInhabited() && getShip().relativePosition(planet).magSq() <= sq(getRange() * AU_DISTANCE)) {
				return planet;
			}
		}
		return null;
	}
}