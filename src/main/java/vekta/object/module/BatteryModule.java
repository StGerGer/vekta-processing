package vekta.object.module;

import static java.lang.Math.round;

public class BatteryModule extends ShipModule {
	private final int capacity;

	private float charge;

	public BatteryModule(int capacity) {
		this.capacity = capacity;
	}

	public int getCapacity() {
		return capacity;
	}

	public float getCharge() {
		return getShip() != null ? getShip().getEnergy() : charge;
	}

	public float getRatio() {
		return getCharge() / getCapacity();
	}

	@Override
	public String getName() {
		return "Battery v" + getCapacity() + " (" + round(getRatio() * 100) + "%)";
	}

	@Override
	public ModuleType getType() {
		return ModuleType.BATTERY;
	}

	@Override
	public boolean isBetter(Module other) {
		return other instanceof BatteryModule && getCapacity() > ((BatteryModule)other).getCapacity();
	}

	@Override
	public Module getVariant() {
		BatteryModule battery = new BatteryModule(chooseInclusive(1, 20) * 10);
		battery.charge = choose(0, battery.getCapacity());
		return battery;
	}

	@Override
	public void onInstall() {
		getShip().setEnergy(getCharge());
		getShip().setMaxEnergy(getCapacity());
	}

	@Override
	public void onUninstall() {
		charge = getShip().getEnergy();
		getShip().setMaxEnergy(0);
	}
}
