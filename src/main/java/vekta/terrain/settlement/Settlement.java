package vekta.terrain.settlement;

import vekta.Player;
import vekta.menu.Menu;
import vekta.terrain.Terrain;

import java.util.ArrayList;
import java.util.List;

public abstract class Settlement implements SettlementPart {
	private final List<SettlementPart> parts = new ArrayList<>();

	public List<SettlementPart> getParts() {
		return parts;
	}

	public void add(SettlementPart part) {
		parts.add(part);
	}

	public abstract String createOverview();

	@Override
	public final void setupTerrain(Terrain terrain) {
		onTerrain(terrain);
		for(SettlementPart part : getParts()) {
			part.setupTerrain(terrain);
		}
	}

	public void onTerrain(Terrain terrain) {
	}

	@Override
	public final void setupLandingMenu(Player player, Menu menu) {
		onLandingMenu(player, menu);
		for(SettlementPart part : getParts()) {
			part.setupLandingMenu(player, menu);
		}
	}

	public void onLandingMenu(Player player, Menu menu) {
	}
}