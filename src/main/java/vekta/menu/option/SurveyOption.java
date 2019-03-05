package vekta.menu.option;

import vekta.menu.Menu;
import vekta.menu.handle.SurveyMenuHandle;
import vekta.terrain.LandingSite;

import static vekta.Vekta.setContext;

public class SurveyOption implements MenuOption {
	private final LandingSite site;

	public SurveyOption(LandingSite site) {
		this.site = site;
	}

	public LandingSite getSite() {
		return site;
	}

	@Override
	public String getName() {
		return "Survey";
	}

	@Override
	public void select(Menu menu) {
		Menu sub = new Menu(menu.getPlayer(), new SurveyMenuHandle(new BackOption(menu), getSite()));
		sub.addDefault();
		setContext(sub);
	}
}