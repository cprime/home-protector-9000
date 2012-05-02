package ai;

import java.awt.Point;
import java.util.ArrayList;

import model.Direction;
import model.Model;
import model.Position;
import model.World;

public class AStarSearch {
	private final int connectionWeight = 1;
	
	private World world;
	private AStarSearchDelegate delegate;
	private ArrayList<Position> path;
	private boolean initialized;
	
	public World getWorld() {
		return world;
	}
	public void setWorld(World world) {
		this.world = world;
	}
	public ArrayList<Position> getPath() {
		return path;
	}

	public boolean isInitialized() {
		return initialized;
	}
	
	public AStarSearchDelegate getDelegate() {
		return delegate;
	}
	public void setDelegate(AStarSearchDelegate delegate) {
		this.delegate = delegate;
	}

	public AStarSearch(World w, AStarSearchDelegate delegate) {
		super();
		
		this.setWorld(w);
		this.setDelegate(delegate);
		this.initialized = false;
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
	
	public boolean positionIsGoal(Position position, Position goal) {
		if(goal.direction == Direction.UNKNOWN || goal.direction == Direction.NONE || goal.direction == position.direction) {
			return position.point.equals(goal.point);
		}
		return false;
	}
	
	//search methods
	public void initializeAStarPath(Position start, Position goal) {
		initialized = true;
		path = new ArrayList<Position>();
		
		ArrayList<AStarNode> openList = new ArrayList<AStarNode>();
		ArrayList<AStarNode> closedList = new ArrayList<AStarNode>();
		
		AStarNode startingNode = new AStarNode(null, start, 0, delegate.heuristic(start.point, goal.point));
		openList.add(startingNode);
		
		AStarNode current = null;
		while(!openList.isEmpty()) {
			//expand node with lowest f() = g() + h()
			current = openList.get(0);
			for(int i = 1; i < openList.size(); i ++) {
				AStarNode testNode = openList.get(i);
				if(testNode.estimatedTotalCost < current.estimatedTotalCost) current = testNode; 
			}
			
			if(current.position.equals(goal)) break;
			
			//connections
			ArrayList<Position> positionsAroundCurrent = this.positionsToExpandAroundPosition(current.position);
			for(Position endPosition : positionsAroundCurrent) {
				int endPositionCost = current.costSoFar + this.connectionWeight(current.position, endPosition);
				
				//if a node with endPoint has been closed or opened previously, at a higher cost, remove node from closed list and reuse them
				AStarNode endNode = null;
				if((endNode = this.previousNodeWithPointInList(closedList, endPosition)) != null) {
					if(endNode.costSoFar <= endPositionCost) continue;
					closedList.remove(endNode);
				} else if((endNode = this.previousNodeWithPointInList(openList, endPosition)) != null) {
					if(endNode.costSoFar <= endPositionCost) continue;
				} else {
					endNode = new AStarNode(null, endPosition, -1, -1);
				}
				
				//use/reuse endNode
				endNode.costSoFar = endPositionCost;
				endNode.parent = current;
				endNode.estimatedTotalCost = endPositionCost + delegate.heuristic(endPosition.point, goal.point);
				
				if(!openList.contains(endNode)) openList.add(endNode);
			}
			
			openList.remove(current);
			closedList.add(current);
		}
		
		if(current.position.equals(goal)) {
			AStarNode checking = current;
			while(checking != startingNode) {
				if(path.isEmpty()) {
					path.add(checking.position);
				} else {
					path.add(0, checking.position);
				}
				checking = checking.parent;
			}
		}
		else {
			System.out.println("No Path Found");
		}
	}
	
	public void reset() {
		initialized = false;
		path = null;
	}
	
	//Pseudo Iterator Methods
	public boolean hasNext() {
		return !path.isEmpty();
	}
	
	public Position peek() {
		if(!path.isEmpty()) {
			return path.get(0);
		}
		return null;
	}
	
	public Position next() {
		if(!path.isEmpty()) {
			Position ret = path.get(0);
			path.remove(0);
			return ret;
		}
		return null;
	}
	
	//helper functions	
	private AStarNode previousNodeWithPointInList(ArrayList<AStarNode> nodes, Position p) {
		for(AStarNode node : nodes) {
			if(p.equals(node.position)) return node;
		}
		return null;
	}
	
	private int connectionWeight(Position start, Position end) {
		int ret = 0;
		if(start.direction != end.direction) ret += 1;
		if(start.point.equals(end.point)) ret += 1;
		return ret;
	}
	
	//helper class
	private class AStarNode {
		public AStarNode parent;
		public Position position;
		public int costSoFar;
		public int estimatedTotalCost;
		
		public AStarNode(AStarNode parent, Position position, int costSoFar, int estimatedTotalCost) {
			this.parent = parent;
			this.position = position;
			this.costSoFar = costSoFar;
			this.estimatedTotalCost = estimatedTotalCost;
		}
	}
}
