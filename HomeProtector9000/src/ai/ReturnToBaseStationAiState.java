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
		Position botPosition = bot.getPosition();
		World w = this.getWorld();
		BaseStation station = w.getStation();
		
		Position nextPos = pathFinder.next();
		if(station.getPoint().equals(botPosition.point)) {
			return this.baseStationAction();
		}
		else if(nextPos == null || !this.validMovePosition(w, nextPos.point)) {
			pathFinder.initializeAStarPath(botPosition, new Position(station.getPoint(), Direction.NONE));
			nextPos = pathFinder.next();
		}
		
		if(nextPos != null) {
			if(nextPos.point.equals(botPosition.point)) {
				if(this.directionIsClockwiseToDirection(botPosition.direction, nextPos.direction)) {
					return factory.turnClockwiseAction();
				}
				return factory.turnCounterClockwiseAction();
			} else if(this.validMovePosition(w, nextPos.point)) {
				return factory.moveAction(botPosition.point, nextPos.point);
			}
		}
		
		System.out.println(bot + " is now blocked while returning to base. Waiting for the user to un-block him.");
		return factory.waitAction();
	}

	public abstract Action baseStationAction();
}
