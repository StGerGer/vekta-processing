package vekta.person;

import vekta.menu.Menu;
import vekta.menu.handle.DialogMenuHandle;
import vekta.menu.option.CustomButton;
import vekta.menu.option.DialogButton;
import vekta.menu.option.MenuOption;
import vekta.player.Player;
import vekta.spawner.DialogGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static vekta.Vekta.*;

public class Dialog implements Serializable {
	private final String type;
	private final Person person;
	private String message;
	private int color;

	private final List<String> responses = new ArrayList<>();
	private final List<MenuOption> options = new ArrayList<>();
	private Dialog next;

	private boolean visited;

	public Dialog(String type, Person person, String message) {
		this(type, person, message, v.color(255));
	}

	public Dialog(String type, Person person, String message, int color) {
		this.type = type;
		this.person = person;
		this.message = message;
		this.color = color;

		DialogGenerator.initDialog(this);
	}

	public String getType() {
		return type;
	}

	public Person getPerson() {
		return person;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public boolean isVisited() {
		return visited;
	}

	public List<String> getResponses() {
		return responses;
	}

	public void addResponse(String response) {
		responses.add(response);
	}

	public List<MenuOption> getOptions() {
		return options;
	}

	public Dialog getNext() {
		return next;
	}

	public boolean hasNext() {
		return next != null && (!next.isVisited() || next.hasNext());
	}

	public Dialog then(String next) {
		return then(getPerson().createDialog(next));
	}

	public Dialog then(String next, float chance) {
		return v.chance(chance) ? then(next) : this;
	}

	public Dialog then(Dialog dialog) {
		if(next == null) {
			next = dialog;
		}
		else {
			next.then(dialog);
		}
		return this;
	}

	public void parseResponse(String text) {
		if(text.startsWith(":")) {
			String[] args = text.split(" ", 2);
			String type = args[0].substring(1).trim();
			String response = args[1].trim();
			boolean aside = type.startsWith("aside"); // Special behavior for `dialog_aside_...`
			if(type.startsWith("^")) {
				// Mark to use aside behavior
				type = type.substring(1).trim();
				aside = true;
			}

			Dialog next = getPerson().createDialog(type);
			add(new DialogButton(response, next));
			if(aside) {
				next.then(this);
			}
		}
		else {
			addResponse(text);
		}
	}

	public void add(MenuOption option) {
		if(!options.contains(option)) {
			options.add(option);
		}
	}

	public void add(String response, Dialog dialog) {
		add(new DialogButton(response, dialog));
	}

	public void openMenu(Player player, MenuOption def) {
		if(visited && next != null) {
			getNext().openMenu(player, def);
			return;
		}
		visited = true;

		if(getPerson().getPersonality() != null) {
			getPerson().getPersonality().prepareDialog(this);
		}

		Menu menu = new Menu(player, def, new DialogMenuHandle(this));
		DialogGenerator.setupDialogMenu(menu, this);

		for(MenuOption option : getOptions()) {
			menu.add(option);
		}

		if(hasNext()) {
			// Add custom responses leading to next dialog
			List<String> responses = !getResponses().isEmpty() ? getResponses() : Collections.singletonList("Next");
			for(String response : responses) {
				menu.add(new DialogButton(response, getNext()));
			}
		}
		else {
			// Add custom responses for exiting dialog
			for(String response : getResponses()) {
				menu.add(new CustomButton(response, menu.getDefault()::onSelect));
			}
		}

		// Randomize and ensure at least one option is available
		if(menu.size() > 0) {
			Collections.shuffle(menu.getOptions());
		}
		else {
			menu.add(new CustomButton("Back", menu.getDefault()::onSelect));
			//			menu.addDefault();
		}

		setContext(menu);
		applyContext();
	}
}
