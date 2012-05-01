package ai;

import model.Action;
import model.ActionFactory;
import model.BaseStation;
import model.Bot;
import model.Direction;
import model.Position;
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
		
		if(station.getPoint().equals(bot.getPosition().point)) {
			return this.baseStationAction();
		}
		else if(!pathFinder.isInitialized() || !this.validMovePosition(w, pathFinder.peek().point)) {
			pathFinder.initializeAStarPath(bot.getPosition(), new Position(station.getPoint(), Direction.NONE));
		}
		
		if(pathFinder.peek() != null && pathFinder.peek().equals(bot.getPosition().point)) {
			if((pathFinder.peek().direction == Direction.NORTH && bot.getPosition().direction == Direction.WEST) ||
					(pathFinder.peek().direction == Direction.EAST && bot.getPosition().direction == Direction.NORTH) ||
					(pathFinder.peek().direction == Direction.SOUTH && bot.getPosition().direction == Direction.EAST) ||
					(pathFinder.peek().direction == Direction.WEST && bot.getPosition().direction == Direction.SOUTH)) {
				return factory.turnClockwiseAction();
			}
			return factory.turnCounterClockwiseAction();
		} else if(this.validMovePosition(w, pathFinder.peek().point)) {
			return factory.moveAction(bot.getPosition().point, pathFinder.next().point);
		}
		
		System.out.println(bot + " is now blocked while returning to base. Waiting for the user to un-block him.");
		return factory.waitAction();
	}

	public abstract Action baseStationAction();
}
