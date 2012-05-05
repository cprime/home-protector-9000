package ai;

import java.awt.Point;

import model.Action;
import model.ActionFactory;
import model.Bot;
import model.Fire;
import model.Model;
import model.Position;
import model.World;

public class FiremanAiState extends AiState implements SimpleSearchDelegate {
	private SimpleSearch simpleSearch;
	private Position goal;
	
	public FiremanAiState(Bot bot, World world) {
		super(bot, world);
		
		goal = null;
		simpleSearch = new SimpleSearch(this.getWorld(), this);
	}
	
	private Action setupNewGoal() {
		Bot bot = this.getBot();
		Position start = bot.getPosition();
		AStarSearch pathFinder = this.getPathFinder();
		ActionFactory factory = this.getFactory();
		
		//assume current position is not goal (based on action())
		System.out.println("Starting Position: " + start);
		this.goal = this.simpleSearch.findPositionClosestToGoal(start);
		if(this.goal != null) {
			System.out.println("Goal: " + this.goal);
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
		} else if(!this.getWorld().containsFire()) {
			System.out.println("No more fire to extinguish. I should probably do something else...");
			return factory.waitAction();
		}
		
		System.out.println(this.getBot() + " is now blocked from fire while fighting fire. Waiting for the user to un-block him.");
		return factory.waitAction();
	}

	@Override
	public Action action() {
		AStarSearch pathFinder = this.getPathFinder();
		ActionFactory factory = this.getFactory();
		Bot bot = this.getBot();
		Position botPosition = bot.getPosition();
		World w = this.getWorld();
		
		if(this.isPositionGoal(botPosition)) {
			Point forwardPoint = this.forwardPointFromPosition(botPosition);
			if(forwardPoint != null) {
				Model model = this.getWorld().objectAtPosition(forwardPoint);
				if(model instanceof Fire)  {
					this.goal = null;
					pathFinder.reset();
					return factory.blowAction(model);
				}
			}
		} else if(this.goal != null) {
			if(botPosition.point.equals(this.goal.point)) {
				Point forwardPoint = this.forwardPointFromPosition(botPosition);
				if(forwardPoint != null) {
					Model model = this.getWorld().objectAtPosition(forwardPoint);
					if(model instanceof Fire)  {
						this.goal = null;
						pathFinder.reset();
						return factory.blowAction(model);
					}
				}
			} else {
				Position nextPos = pathFinder.next();
				if(nextPos != null) {
					if(nextPos.point.equals(botPosition.point)) {
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
		return "Fireman";
	}
	
	private Point forwardPointFromPosition(Position p) {
		Point forward = null;
		switch(p.direction) {
		case NORTH:
			forward = new Point(p.point.x, p.point.y - 1);
			break;
		case SOUTH:
			forward = new Point(p.point.x, p.point.y + 1);
			break;
		case EAST:
			forward = new Point(p.point.x + 1, p.point.y);
			break;
		case WEST:
			forward = new Point(p.point.x - 1, p.point.y);
			break;
			default:
				break;
		}
	
		if(forward.x < this.getWorld().getWidth() && forward.y < this.getWorld().getHeight() && forward.x >= 0 && forward.y >= 0) {
			return forward;
		}
		
		return null;
	}

	//SimpleSearchDelegate method
	@Override
	public boolean isPositionGoal(Position p) {
		Point forward = this.forwardPointFromPosition(p);
	
		if(forward != null) {
			Model model = this.getWorld().objectAtPosition(forward);
			if(model instanceof Fire) return true;
		}
		
		return false;
	}
}
