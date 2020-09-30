package vekta.menu.option;

import vekta.module.ModuleUpgrader;
import vekta.menu.Menu;
import vekta.module.Module;

import static vekta.Vekta.v;

public class InstallModuleButton implements ButtonOption {
	private final ModuleUpgrader upgrader;
	private final Module module;
	private final ModuleStatus status;

	public InstallModuleButton(ModuleUpgrader upgrader, Module module) {
		this.upgrader = upgrader;
		this.module = module;

		this.status = getModuleStatus(upgrader.getRelevantModule(module));
	}

	public Module getModule() {
		return module;
	}

	public ModuleStatus getModuleStatus(Module module) {
		if(module == null || getModule().isBetter(module)) {
			return ModuleStatus.BETTER;
		}
		else if(module.isBetter(getModule())) {
			return ModuleStatus.WORSE;
		}
		else {
			return ModuleStatus.DIFFERENT;
		}
	}

	@Override
	public String getName() {
		return module.getName() + " " + status.tag;
	}

	@Override
	public int getColor() {
		return status.color;
	}

	@Override
	public String getSelectVerb() {
		return "install";
	}

	@Override
	public void onSelect(Menu menu) {
		upgrader.installModule(module);
	}

	private enum ModuleStatus {
		BETTER("[^]", v.color(255, 255, 0)),
		DIFFERENT("[*]", v.color(200)),
		WORSE("[-]", v.color(100));

		private final String tag;
		private final int color;

		ModuleStatus(String tag, int color) {
			this.tag = tag;
			this.color = color;
		}
	}
}
