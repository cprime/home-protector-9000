package ai;

import model.Action;
import model.Bot;
import model.ProtectorBot;
import model.World;

public class PickupExtinguisherAiState extends ReturnToBaseStationAiState {

	public PickupExtinguisherAiState(Bot bot, World world) {
		super(bot, world);
	}

	@Override
	public Action baseStationAction() {
		if(((ProtectorBot)this.getBot()).hasExtinguisher()) {
			return this.getFactory().waitAction();
		} else {
			return this.getFactory().pickupAction(this.getWorld().getStation());
		}
	}

	public String prettyName() {
		return "Seeking Extinguisher";
	}
}
