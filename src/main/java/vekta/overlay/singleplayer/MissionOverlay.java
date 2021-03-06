package vekta.overlay.singleplayer;

import vekta.mission.Mission;
import vekta.mission.objective.Objective;
import vekta.overlay.Overlay;
import vekta.player.Player;

import java.util.List;

import static vekta.Vekta.MISSION_COLOR;
import static vekta.Vekta.v;

public class MissionOverlay implements Overlay {

	private final Player player;
	private boolean show;

	public MissionOverlay(Player player) {
		this.player = player;
		show = true;
	}

	@Override
	public void render() {
		Mission mission = player.getCurrentMission();
		if(mission != null && show) {
			List<Objective> objectives = mission.getObjectives();

			// Draw mission/objective text
			v.fill(MISSION_COLOR);
			v.textSize(24);
			v.text(mission.getName(), 50, 70);
			for(int i = 0; i < objectives.size(); i++) {
				Objective objective = objectives.get(i);
				boolean isCurrent = mission.getCurrentObjective() == objective;
				int color = objective.getStatus().getColor();
				v.fill(isCurrent ? color : v.lerpColor(color, 0, .2F));
				v.textSize(16);
				v.text(objective.getDisplayText(), isCurrent ? 55 : 50, 110 + i * 30);
			}
		}
	}

	public void hide() {
		show = false;
	}

	public void show() {
		show = true;
	}

	public void toggleShow() {
		show = !show;
	}
}
