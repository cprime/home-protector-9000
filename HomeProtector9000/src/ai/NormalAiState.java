package ai;

import java.awt.Point;

import model.Action;
import model.ActionFactory;
import model.Bot;
import model.Direction;
import model.Dirt;
import model.Model;
import model.Position;
import model.World;

public class NormalAiState extends AiState implements SimpleSearchDelegate {
	private SimpleSearch simpleSearch;
	private Position goal;
	
	public NormalAiState(Bot bot, World world) {
		super(bot, world);
		
		goal = null;
		simpleSearch = new SimpleSearch(this.getWorld(), this);
	}
	
	public Action setupNewGoal() {
		Bot bot = this.getBot();
		Position start = bot.getPosition();
		AStarSearch pathFinder = this.getPathFinder();
		ActionFactory factory = this.getFactory();
		
		System.out.println("Starting Position: " + start);
		this.goal = this.simpleSearch.findPositionClosestToGoal(start);
		System.out.println("Goal: " + this.goal);
		if(this.goal != null) {
			pathFinder.initializeAStarPath(start, this.goal);
			System.out.println("Path: " + pathFinder.getPath());
			Position nextPos = pathFinder.next();
			if(nextPos != null) {
				if(nextPos != null && nextPos.point.equals(start.point)) {
					if(this.directionIsClockwiseToDirection(bot.getPosition().direction, nextPos.direction)) {
						return factory.turnClockwiseAction();
					}
					return factory.turnCounterClockwiseAction();
				} else if(this.validMovePosition(this.getWorld(), nextPos.point)) {
					return factory.moveAction(start.point, nextPos.point);
				}
			}
		} else if(!this.getWorld().containsDirt()) {
			System.out.println("No more dirt to vacuum.");
			return factory.waitAction();
		}
		
		System.out.println(this.getBot() + " is now blocked from dirt while performing normal business. Waiting for the user to un-block him.");
		return factory.waitAction();
		
	}

	@Override
	public Action action() {
		AStarSearch pathFinder = this.getPathFinder();
		ActionFactory factory = this.getFactory();
		Bot bot = this.getBot();
		Position botPosition = bot.getPosition();
		World w = this.getWorld();
		Model m = w.objectAtPosition(botPosition.point);
		
		if((this.goal == null || !botPosition.point.equals(this.goal.point)) && m != bot && m instanceof Dirt) {
			return factory.suckAction(m);
		} else if(this.goal != null) {
			if(bot.getPosition().point.equals(this.goal.point)) {
				if(m != null && m instanceof Dirt) {
					this.goal = null;
					pathFinder.reset();
					return factory.suckAction(m);
				}
			} else {
				Position nextPos = pathFinder.next();
				if(nextPos != null) {
					if(nextPos != null && nextPos.point.equals(botPosition.point)) {
						if(this.directionIsClockwiseToDirection(bot.getPosition().direction, nextPos.direction)) {
							return factory.turnClockwiseAction();
						}
						return factory.turnCounterClockwiseAction();
					} else if(this.validMovePosition(w, nextPos.point)) {
						return factory.moveAction(botPosition.point, nextPos.point);
					}
				}
			}
		}
		return setupNewGoal();
	}
	

	public String prettyName() {
		return "Normal Vacuuming";
	}

	//SimpleSearchDelegate methods
	@Override
	public boolean isPositionGoal(Position p) {
		Model m = this.getWorld().objectAtPosition(p.point);
		if(m instanceof Dirt) return true;
		return false;
	}
	
}
