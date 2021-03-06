package vekta.terrain.settlement.building;

import vekta.item.Inventory;
import vekta.menu.Menu;
import vekta.menu.option.LootMenuButton;
import vekta.spawner.ItemGenerator;
import vekta.spawner.item.JunkItemSpawner;
import vekta.terrain.settlement.SettlementPart;

import java.util.Set;

public class JunkyardBuilding implements SettlementPart {
	private final Inventory inv = new Inventory();

	public JunkyardBuilding() {
		ItemGenerator.addLoot(inv, 2, new JunkItemSpawner());
	}

	public Inventory getInventory() {
		return inv;
	}

	@Override
	public String getName() {
		return getGenericName();
	}

	@Override
	public String getGenericName() {
		return "Junkyard";
	}

	@Override
	public BuildingType getType() {
		return BuildingType.INDUSTRIAL;
	}

	@Override
	public void onSurveyTags(Set<String> tags) {
		tags.add("Industrial");
	}

	@Override
	public void setupMenu(Menu menu) {
		if(getInventory().itemCount() > 0) {
			menu.add(new LootMenuButton("Junkyard", getInventory()));
		}
	}
}
