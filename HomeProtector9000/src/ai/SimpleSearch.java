package ai;

import java.awt.Point;
import java.util.ArrayList;

import model.Direction;
import model.Model;
import model.Position;
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
	
	public ArrayList<Position> positionsToExpandAroundPosition(Position p) {
		ArrayList<Position> ret = new ArrayList<Position>();
		
		Point forward = null;
		switch(p.direction) {
		case NORTH:
			forward = new Point(p.point.x, p.point.y - 1);
			ret.add(new Position(p.point, Direction.EAST));
			ret.add(new Position(p.point, Direction.WEST));
			break;
		case SOUTH:
			forward = new Point(p.point.x, p.point.y + 1);
			ret.add(new Position(p.point, Direction.EAST));
			ret.add(new Position(p.point, Direction.WEST));
			break;
		case EAST:
			forward = new Point(p.point.x + 1, p.point.y);
			ret.add(new Position(p.point, Direction.NORTH));
			ret.add(new Position(p.point, Direction.SOUTH));
			break;
		case WEST:
			forward = new Point(p.point.x - 1, p.point.y);
			ret.add(new Position(p.point, Direction.NORTH));
			ret.add(new Position(p.point, Direction.SOUTH));
			break;
			default:
				break;
		}
	
		if(forward.x < this.world.getWidth() && forward.y < this.world.getHeight() && forward.x >= 0 && forward.y >= 0) {
			Model model = world.objectAtPosition(forward);
			if(model == null || model.canStackObject()) {
				ret.add(0, new Position(forward, p.direction));
			}
		}
		
		return ret;
	}

	public Position findPositionClosestToGoal(Position start) {
		ArrayList<Position> openList = new ArrayList<Position>();
		ArrayList<Position> closedList = new ArrayList<Position>();
		
		openList.add(start);
		
		Position current = null;
		while(!openList.isEmpty()) {
			current = openList.get(0);
			
			if(this.delegate.isPositionGoal(current)) break;
			
			//connections
			ArrayList<Position> positionsAroundCurrent = this.positionsToExpandAroundPosition(current);
			for(Position endPosition : positionsAroundCurrent) {
				if(closedList.contains(endPosition)) {
					continue;
				} else if(openList.contains(endPosition)) {
					continue;
				} else {
					openList.add(endPosition);
				}
			}
			
			openList.remove(current);
			closedList.add(current);
		}
		if(this.delegate.isPositionGoal(current)) return current;
		
		return null;
	}
}
