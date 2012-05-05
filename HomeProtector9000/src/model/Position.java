package model;

import java.awt.Point;

public class Position {
	public Direction direction;
	public Point point;
	
	public Position(Point p, Direction d) {
		this.point = p;
		this.direction = d;
	}
	
	
	public boolean equals(Object anObject) {
		if(anObject instanceof Position) {
			Position p = (Position)anObject;
			return this.point.equals(p.point) && this.direction == p.direction;
		}
		return false;
	}
	
	public String toString() {
		return new String("<" + direction + ": " + point + ">");
	}
}
