package vekta.menu.option;

import vekta.Resources;
import vekta.menu.Menu;
import vekta.menu.handle.MenuHandle;
import vekta.player.Player;
import vekta.terrain.settlement.Settlement;
import vekta.terrain.settlement.building.upgrade.SettlementUpgrade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static vekta.Vekta.setContext;

public class UpgradeMenuButton extends ButtonOption {
	private static final SettlementUpgrade[] UPGRADES = Resources.findSubclassInstances(SettlementUpgrade.class);

	private final Player player;
	private final Settlement settlement;

	public UpgradeMenuButton(Player player, Settlement settlement) {
		this.player = player;
		this.settlement = settlement;
	}

	@Override
	public String getName() {
		return "Upgrade";
	}

	@Override
	public void onSelect(Menu menu) {
		Menu sub = new Menu(menu, new MenuHandle());
		updateMenu(sub);
		sub.addSelectListener(option -> updateMenu(sub));
		setContext(sub);
	}

	private void updateMenu(Menu sub) {
		sub.clear();

		List<SettlementUpgrade> upgrades = new ArrayList<>(Arrays.asList(UPGRADES));
		upgrades.sort(Comparator.comparingInt(u -> u.getCost(sub.getPlayer(), settlement)));

		for(SettlementUpgrade upgrade : upgrades) {
			if(upgrade.isAvailable(player, settlement)) {
				sub.add(new UpgradeButton(player, settlement, upgrade));
			}
		}
		sub.addDefault();
	}
}
