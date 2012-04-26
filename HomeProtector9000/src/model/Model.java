package model;

import java.awt.Point;

public abstract class Model {
	private int x;
	private int y;
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Point getPosition() {
		return new Point(x, y);
	}

	public Model() {
		this(-1, -1);
	}
	
	public Model(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean canStackObject() {
		return false;
	}
	
	public String toString() {
		return String.format("<%s (%d, %d)>", this.getClass().getSimpleName(), this.x, this.y);
	}
}
