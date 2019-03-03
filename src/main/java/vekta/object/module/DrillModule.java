package vekta.object.module;

import vekta.menu.Menu;
import vekta.menu.option.MineOption;
import vekta.terrain.LandingSite;

import static processing.core.PApplet.round;

public class DrillModule extends ShipModule {
	private final float efficiency;

	public DrillModule(float efficiency) {
		this.efficiency = efficiency;
	}

	public float getEfficiency() {
		return efficiency;
	}

	@Override
	public String getName() {
		return "Mining Drill v" + getEfficiency();
	}

	@Override
	public ModuleType getType() {
		return ModuleType.UTILITY;
	}

	@Override
	public boolean isBetter(Module other) {
		return other instanceof DrillModule && getEfficiency() > ((DrillModule)other).getEfficiency();
	}

	@Override
	public Module getVariant() {
		return new DrillModule(chooseInclusive(.5F, 10, .5F));
	}

	@Override
	public void onLandingMenu(LandingSite site, Menu menu) {
		if(site.getTerrain().has("Mineable")) {
			menu.add(new MineOption(site.getTerrain(), getShip().getInventory(), round(getEfficiency() * 5)));
		}
	}
}