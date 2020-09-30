package vekta.terrain.settlement;

import vekta.faction.Faction;
import vekta.economy.Economy;
import vekta.economy.NoiseModifier;
import vekta.item.Inventory;
import vekta.menu.Menu;
import vekta.spawner.WorldGenerator;
import vekta.terrain.building.CapitalBuilding;
import vekta.terrain.building.ForumBuilding;

import static vekta.Vekta.v;

public class ColonySettlement extends Settlement {
	private final Inventory inventory = new Inventory();

	public ColonySettlement(Faction faction) {
		super(faction, "colony");

		add(new CapitalBuilding("Leader", this));
		add(WorldGenerator.createMarket(1));

		if(v.chance(.75F)) {
			add(new ForumBuilding(this, (int)(v.random(2, 5))));
		}
	}

	public Inventory getInventory() {
		return inventory;
	}

	@Override
	public String getGenericName() {
		return "Colony";
	}

	@Override
	public void setupEconomy(Economy economy) {
		getEconomy().setValue(v.random(1, 2));
		getEconomy().addModifier(new NoiseModifier(.5F));
	}

	@Override
	public void onSettlementMenu(Menu menu) {
	}
}
