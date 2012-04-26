package ai;

import java.awt.Point;
import java.util.ArrayList;

import model.Model;
import model.World;

public class SimpleSearch {
	private World world;
	private SimpleSearchDelegate delegate;
	
	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
	public SimpleSearchDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(SimpleSearchDelegate delegate) {
		this.delegate = delegate;
	}

	public SimpleSearch(World w, SimpleSearchDelegate delegate) {
		super();
		
		this.setWorld(w);
		this.setDelegate(delegate);
	}
	
	public ArrayList<Point> positionsToExpandAroundPoint(Point p) {
		ArrayList<Point> ret = new ArrayList<Point>();
		
		ArrayList<Point> basic = world.pointsArountPoint(p);
		for(Point pointToAdd : basic) {
			Model model = world.objectAtPosition(pointToAdd);
			if(model == null || model.canStackObject()) {
				ret.add(pointToAdd);
			}
		}
		
		return ret;
	}

	public Point findPointClosestToGoal(Point start) {
		ArrayList<Point> openList = new ArrayList<Point>();
		ArrayList<Point> closedList = new ArrayList<Point>();
		
		openList.add(start);
		
		Point current = null;
		while(!openList.isEmpty()) {
			current = openList.get(0);
			
			if(this.delegate.isPointGoal(current)) break;
			
			//connections
			ArrayList<Point> pointsAroundCurrent = this.positionsToExpandAroundPoint(current);
			for(Point endPoint : pointsAroundCurrent) {
				if(closedList.contains(endPoint)) {
					continue;
				} else if(openList.contains(endPoint)) {
					continue;
				} else {
					openList.add(endPoint);
				}
			}
			
			openList.remove(current);
			closedList.add(current);
		}
		if(this.delegate.isPointGoal(current)) return current;
		
		return null;
	}
}
