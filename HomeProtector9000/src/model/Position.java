package model;

import java.awt.Point;

public class Position {
	public Direction direction;
	public Point point;
	
	public Position(Point p, Direction d) {
		this.point = p;
		this.direction = d;
	}
	
	
	public boolean equals(Position p) {
		return this.point.equals(p.point) && this.direction == p.direction;
	}
	
	public String toString() {
		return new String("<" + direction + ": " + point + ">");
	}
}
