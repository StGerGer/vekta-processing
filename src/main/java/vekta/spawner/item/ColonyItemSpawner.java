package vekta.spawner.item;

import vekta.item.CoinItem;
import vekta.item.ColonyItem;
import vekta.item.Item;
import vekta.spawner.ItemGenerator;

public class ColonyItemSpawner implements ItemGenerator.ItemSpawner {
	@Override
	public float getWeight() {
		return .1F; // Rare to find naturally
	}

	@Override
	public boolean isValid(Item item) {
		return item instanceof CoinItem;
	}

	@Override
	public Item create() {
		return randomColonyItem();
	}

	public static Item randomColonyItem() {
		// TODO: add more colony-related items
		return new ColonyItem();
	}
}
