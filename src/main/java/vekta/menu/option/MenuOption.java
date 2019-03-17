package vekta.menu.option;

import vekta.menu.Menu;

import java.io.Serializable;

import static vekta.Vekta.UI_COLOR;

public interface MenuOption extends Serializable {
	String getName();

	default int getColor() {
		return UI_COLOR;
	}

	default int getBorderColor() {
		return UI_COLOR;
	}

	default boolean isEnabled() {
		return true;
	}

	default void onUpdate(Menu menu) {
	}

	void onSelect(Menu menu);
}