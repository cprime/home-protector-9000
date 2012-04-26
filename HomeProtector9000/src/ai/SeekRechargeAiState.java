package ai;

import model.Action;
import model.Bot;
import model.ProtectorBot;
import model.World;

public class SeekRechargeAiState extends ReturnToBaseStationAiState {

	public SeekRechargeAiState(Bot bot, World world) {
		super(bot, world);
	}

	@Override
	public Action baseStationAction() {
		if(((ProtectorBot)this.getBot()).isFullyPowered()) {
			return this.getFactory().waitAction();
		} else {
			return this.getFactory().chargeAction(this.getWorld().getStation());
		}
	}
	
	public String prettyName() {
		return "Seeking Battery Recharge";
	}
}
