package vekta.terrain;

import vekta.menu.Menu;

import static vekta.Vekta.v;

public class AsteroidTerrain extends Terrain {
	public AsteroidTerrain() {
		if(v.chance(.25F)) {
			addFeature("Mineable");
		}
	}

	@Override
	public String getOverview() {
		return "You carefully touch down on the asteroid.";
	}

	@Override
	public void setupLandingMenu(LandingSite site, Menu menu) {

	}
}
