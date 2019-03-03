package vekta.object.module;

import vekta.object.ModularShip;
import vekta.object.SpaceObject;

public interface Module extends Cloneable {
	// Define per-second energy consumption rate
	float PER_SECOND = 1 / 60F;
	float PER_MINUTE = PER_SECOND / 60F;

	String getName();

	ModuleType getType();

	boolean isBetter(Module other);

	Module getVariant();

	default void onInstall(ModularShip ship) {
	}

	default void onUninstall(ModularShip ship) {
	}

	default void onUpdate() {
	}

	default void onDepart(SpaceObject s) {
	}

	default void onKeyPress(char key) {
	}

	default void onKeyRelease(char key) {
	}
}
