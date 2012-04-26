package ai;

import java.awt.Point;
import java.util.ArrayList;

import model.Model;
import model.World;

public class AStarSearch {
	private final int connectionWeight = 1;
	
	private World world;
	private AStarSearchDelegate delegate;
	private ArrayList<Point> path;
	private boolean initialized;
	
	public World getWorld() {
		return world;
	}
	public void setWorld(World world) {
		this.world = world;
	}
	public ArrayList<Point> getPath() {
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
	
	//search methods
	public void initializeAStarPath(Point start, Point goal) {
		initialized = true;
		path = new ArrayList<Point>();
		
		ArrayList<AStarNode> openList = new ArrayList<AStarNode>();
		ArrayList<AStarNode> closedList = new ArrayList<AStarNode>();
		
		AStarNode startingNode = new AStarNode(null, start, 0, delegate.heuristic(start, goal));
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
			ArrayList<Point> pointsAroundCurrent = this.positionsToExpandAroundPoint(current.position);
			for(Point endPoint : pointsAroundCurrent) {
				int endPointCost = current.costSoFar + connectionWeight;
				
				//if a node with endPoint has been closed or opened previously, at a higher cost, remove node from closed list and reuse them
				AStarNode endNode = null;
				if((endNode = this.previousNodeWithPointInList(closedList, endPoint)) != null) {
					if(endNode.costSoFar <= endPointCost) continue;
					closedList.remove(endNode);
				} else if((endNode = this.previousNodeWithPointInList(openList, endPoint)) != null) {
					if(endNode.costSoFar <= endPointCost) continue;
				} else {
					endNode = new AStarNode(null, endPoint, -1, -1);
				}
				
				//use/reuse endNode
				endNode.costSoFar = endPointCost;
				endNode.parent = current;
				endNode.estimatedTotalCost = endPointCost + delegate.heuristic(endPoint, goal);
				
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
	
	public Point peek() {
		if(!path.isEmpty()) {
			return path.get(0);
		}
		return null;
	}
	
	public Point next() {
		if(!path.isEmpty()) {
			Point ret = path.get(0);
			path.remove(0);
			return ret;
		}
		return null;
	}
	
	//helper functions	
	private AStarNode previousNodeWithPointInList(ArrayList<AStarNode> nodes, Point p) {
		for(AStarNode node : nodes) {
			if(p.equals(node.position)) return node;
		}
		return null;
	}
	
	//helper class
	private class AStarNode {
		public AStarNode parent;
		public Point position;
		public int costSoFar;
		public int estimatedTotalCost;
		
		public AStarNode(AStarNode parent, Point position, int costSoFar, int estimatedTotalCost) {
			this.parent = parent;
			this.position = position;
			this.costSoFar = costSoFar;
			this.estimatedTotalCost = estimatedTotalCost;
		}
	}
}
