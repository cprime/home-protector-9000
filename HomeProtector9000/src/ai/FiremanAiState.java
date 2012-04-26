package ai;

import java.awt.Point;
import java.util.ArrayList;

import model.Action;
import model.ActionFactory;
import model.Bot;
import model.Fire;
import model.Model;
import model.World;

public class FiremanAiState extends AiState implements SimpleSearchDelegate {
	private SimpleSearch simpleSearch;
	private Point goal;
	
	public FiremanAiState(Bot bot, World world) {
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
		} else if(!this.getWorld().containsFire()) {
			System.out.println("No more fire to extinguish. I should probably do something else...");
			return factory.waitAction();
		} else {
			System.out.println(this.getBot() + " is now blocked from fire while fighting fire. Waiting for the user to un-block him.");
			return factory.waitAction();
		}
	}

	@Override
	public Action action() {
		AStarSearch pathFinder = this.getPathFinder();
		ActionFactory factory = this.getFactory();
		Bot bot = this.getBot();
		World w = this.getWorld();
		
		if(this.isPointGoal(bot.getPosition())) {
			ArrayList<Point> pointsAround = this.getWorld().pointsArountPoint(bot.getPosition());
			for(Point nearPoint : pointsAround) {
				Model model = this.getWorld().objectAtPosition(nearPoint);
				if(model instanceof Fire)  {
					return factory.blowAction(model);
				}
			}
		} else if(this.goal != null) {
			if(bot.getPosition().equals(this.goal)) {
				ArrayList<Point> pointsAround = this.getWorld().pointsArountPoint(bot.getPosition());
				for(Point nearPoint : pointsAround) {
					Model model = this.getWorld().objectAtPosition(nearPoint);
					if(model instanceof Fire)  {
						this.goal = null;
						pathFinder.reset();
						return factory.blowAction(model);
					}
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
		return "Fireman";
	}

	//SimpleSearchDelegate method
	public boolean isPointGoal(Point p) {
		ArrayList<Point> pointsAround = this.getWorld().pointsArountPoint(p);
		for(Point nearPoint : pointsAround) {
			Model model = this.getWorld().objectAtPosition(nearPoint);
			if(model instanceof Fire) return true;
		}
		return false;
	}
}
