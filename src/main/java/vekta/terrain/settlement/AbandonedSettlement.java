package vekta.terrain.settlement;

import vekta.Resources;
import vekta.faction.Faction;
import vekta.item.Inventory;
import vekta.menu.Menu;
import vekta.menu.option.LootMenuButton;
import vekta.object.planet.TerrestrialPlanet;
import vekta.player.Player;
import vekta.spawner.ItemGenerator;

public class AbandonedSettlement extends Settlement {
	private final Inventory inventory = new Inventory();

	public AbandonedSettlement(TerrestrialPlanet planet, Faction faction, String prevName) {
		super(planet, faction, prevName + " Ruins", Resources.generateString("overview_ruins"));

		ItemGenerator.addLoot(getInventory(), 2);
	}

	@Override
	public String getGenericName() {
		return "Ruins";
	}

	@Override
	public float getValueScale() {
		return 2;
	}

	public Inventory getInventory() {
		return inventory;
	}

	@Override
	public boolean hasSecurity(Player player) {
		return false;
	}

	@Override
	public void onSettlementMenu(Menu menu) {
		if(getInventory().itemCount() > 0) {
			menu.add(new LootMenuButton("Scavenge", getInventory()));
		}
	}
}
