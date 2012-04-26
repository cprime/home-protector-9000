package ai;

import model.Action;
import model.Bot;
import model.ProtectorBot;
import model.World;

public class ReplaceExtinguisherAiState extends ReturnToBaseStationAiState {

	public ReplaceExtinguisherAiState(Bot bot, World world) {
		super(bot, world);
	}

	@Override
	public Action baseStationAction() {
		if(((ProtectorBot)this.getBot()).hasExtinguisher()) {
			return this.getFactory().replaceAction(this.getWorld().getStation());
		} else {
			return this.getFactory().waitAction();
		}
	}
	

	public String prettyName() {
		return "Replace Extinguisher";
	}
}
