package vekta.menu.option;

import vekta.item.WeaponItem;
import vekta.menu.Menu;
import vekta.person.Person;
import vekta.spawner.dialog.SecurityDialogSpawner;

public class MurderButton extends ButtonOption {
	private final Person person;
	private final WeaponItem weapon;

	public MurderButton(Person person, WeaponItem weapon) {
		this.person = person;
		this.weapon = weapon;
	}

	public Person getPerson() {
		return person;
	}

	public WeaponItem getWeapon() {
		return weapon;
	}

	@Override
	public String getName() {
		return getWeapon().getAction();
	}

	@Override
	public void onSelect(Menu menu) {
		MenuOption leave = menu.getDefault();
		MenuOption confront = new DialogButton("Confront Security",
				SecurityDialogSpawner.randomSecurityDialog(getPerson().getFaction(), leave));

		getPerson().die();

		menu.clear();
		menu.setDefault(confront);
		menu.add(new QuicktimeButton(2, "Leave", leave::onSelect));
	}
}
