package vekta.terrain.building;

import vekta.item.Item;
import vekta.item.OreItem;
import vekta.menu.Menu;
import vekta.menu.handle.MenuHandle;
import vekta.menu.option.BackOption;
import vekta.menu.option.BasicOption;
import vekta.menu.option.OreRefineOption;
import vekta.terrain.LandingSite;
import vekta.terrain.settlement.SettlementPart;

import static vekta.Vekta.setContext;
import static vekta.Vekta.v;

public class RefineryBuilding implements SettlementPart {
	public RefineryBuilding() {
	}

	@Override
	public String getName() {
		return getTypeString();
	}

	@Override
	public String getTypeString() {
		return "Refinery";
	}

	@Override
	public void setup(LandingSite site) {
		site.getTerrain().addFeature("Industrial");
		
		if(v.chance(.25F)) {
			site.getTerrain().addFeature("Smog");
		}
	}

	@Override
	public void setupSettlementMenu(Menu menu) {
		menu.add(new BasicOption("Refinery", m -> {
			Menu sub = new Menu(m.getPlayer(), new MenuHandle(new BackOption(m)));
			for(Item item : m.getPlayer().getInventory()) {
				if(item instanceof OreItem && ((OreItem)item).getRefined() != null) {
					sub.add(new OreRefineOption((OreItem)item, m.getPlayer().getInventory()));
				}
			}
			sub.addDefault();
			setContext(sub);
		}));
	}
}
