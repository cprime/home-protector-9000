package model;

import java.awt.Point;

public class Position {
	public Direction direction;
	public Point point;
	
	public Position(Point p, Direction d) {
		this.point = p;
		this.direction = d;
	}
	
}
