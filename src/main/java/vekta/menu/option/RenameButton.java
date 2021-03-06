package vekta.menu.option;

import vekta.Renameable;
import vekta.context.TextInputContext;
import vekta.menu.Menu;

import static vekta.Vekta.setContext;

public class RenameButton extends ButtonOption {
	private final Renameable renameable;

	public RenameButton(Renameable renameable) {
		this.renameable = renameable;
	}

	@Override
	public String getName() {
		return "Rename";
	}

	@Override
	public void onSelect(Menu menu) {
		TextInputContext context = new TextInputContext(menu, "Rename:", renameable.getName(), renameable::setName);
		setContext(context);
	}
}
