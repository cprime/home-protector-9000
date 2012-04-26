package ai;

import java.awt.Point;

import model.Action;
import model.ActionFactory;
import model.Bot;
import model.Dirt;
import model.Model;
import model.World;

public class NormalAiState extends AiState implements SimpleSearchDelegate {
	private SimpleSearch simpleSearch;
	private Point goal;
	
	public NormalAiState(Bot bot, World world) {
		super(bot, world);
		
		goal = null;
		simpleSearch = new SimpleSearch(this.getWorld(), this);
	}
	
	public Action setupNewGoal() {
		Point start = this.getBot().getPosition();
		AStarSearch pathFinder = this.getPathFinder();
		ActionFactory factory = this.getFactory();
		
		this.goal = this.simpleSearch.findPointClosestToGoal(start);
		if(this.goal != null) {
			pathFinder.initializeAStarPath(start, this.goal);
			return factory.moveAction(start, pathFinder.next());  //assume there is one because there is a goal
		} else if(!this.getWorld().containsDirt()) {
			System.out.println("No more dirt to vacuum.");
			return factory.waitAction();
		} else {
			System.out.println(this.getBot() + " is now blocked from dirt while performing normal business. Waiting for the user to un-block him.");
			return factory.waitAction();
		}
	}

	@Override
	public Action action() {
		AStarSearch pathFinder = this.getPathFinder();
		ActionFactory factory = this.getFactory();
		Bot bot = this.getBot();
		World w = this.getWorld();
		Model m = null;
		
		if((m = w.objectAtPosition(bot.getPosition())) != bot && m instanceof Dirt) {
			return factory.suckAction(m);
		} else if(this.goal != null) {
			if(bot.getPosition().equals(this.goal)) {
				m = w.objectAtPosition(this.goal);
				if(m != null && m instanceof Dirt) {
					this.goal = null;
					pathFinder.reset();
					return factory.suckAction(m);
				}
			} else {
				if(this.validMovePosition(w, pathFinder.peek())) {
					return factory.moveAction(bot.getPosition(), pathFinder.next());
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
	public boolean isPointGoal(Point p) {
		Model m = this.getWorld().objectAtPosition(p);
		if(m instanceof Dirt) return true;
		return false;
	}
	
}
