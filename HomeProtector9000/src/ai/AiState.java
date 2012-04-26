package ai;

import java.awt.Point;

import model.Action;
import model.ActionFactory;
import model.Bot;
import model.Model;
import model.World;

public abstract class AiState implements AStarSearchDelegate {
	private Bot bot;
	private World world;
	private AStarSearch pathFinder;
	private ActionFactory factory;
	
	public void setBot(Bot bot) {
		this.bot = bot;
	}

	public Bot getBot() {
		return this.bot;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public World getWorld() {
		return this.world;
	}

	public AStarSearch getPathFinder() {
		return pathFinder;
	}

	public void setPathFinder(AStarSearch pathFinder) {
		this.pathFinder = pathFinder;
	}
	
	public ActionFactory getFactory() {
		return factory;
	}

	public void setFactory(ActionFactory factory) {
		this.factory = factory;
	}
	
	public AiState(Bot bot, World world) {
		super();
		
		this.setBot(bot);
		this.setWorld(world);
		this.setPathFinder(new AStarSearch(world, this));
		this.setFactory(new ActionFactory(bot));
	}
	
	public boolean validMovePosition(World w, Point p) {
		if(p == null) return false;
		
		Model m = w.objectAtPosition(p);
		if(m != null && (!m.canStackObject())) return false;
		
		return true;
	}
	
	public int heuristic(Point p, Point goal) {
		return Math.abs(p.x - goal.x) + Math.abs(p.y - goal.y);
	}

	public abstract Action action();

//	public abstract Action action(World w, Bot bot);
	public abstract String prettyName();
}
