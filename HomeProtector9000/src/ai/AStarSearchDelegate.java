package ai;

import java.awt.Point;

public interface AStarSearchDelegate {
	public int heuristic(Point p, Point goal);
}
