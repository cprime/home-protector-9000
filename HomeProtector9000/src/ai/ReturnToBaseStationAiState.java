package ai;

import model.Action;
import model.ActionFactory;
import model.BaseStation;
import model.Bot;
import model.World;

public abstract class ReturnToBaseStationAiState extends AiState {

	public ReturnToBaseStationAiState(Bot bot, World world) {
		super(bot, world);
	}

	@Override
	public final Action action() {
		AStarSearch pathFinder = this.getPathFinder();
		ActionFactory factory = this.getFactory();
		Bot bot = this.getBot();
		World w = this.getWorld();
		BaseStation station = w.getStation();
		
		if(station.getPosition().equals(bot.getPosition())) {
			return this.baseStationAction();
		}
		else if(!pathFinder.isInitialized() || !this.validMovePosition(w, pathFinder.peek())) {
			pathFinder.initializeAStarPath(bot.getPosition(), station.getPosition());
		}
		
		if(this.validMovePosition(w, pathFinder.peek())) {
			return factory.moveAction(bot.getPosition(), pathFinder.next());
		}
		
		System.out.println(bot + " is now blocked while returning to base. Waiting for the user to un-block him.");
		return factory.waitAction();
	}

	public abstract Action baseStationAction();
}
