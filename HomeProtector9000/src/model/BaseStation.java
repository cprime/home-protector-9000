package model;

public class BaseStation extends GroundObject {
	private boolean hasExtinguisher;
	
	public boolean hasExtinguisher() {
		return hasExtinguisher;
	}

	public void setHasExtinguisher(boolean hasExtinguisher) {
		this.hasExtinguisher = hasExtinguisher;
	}

	public BaseStation(int x, int y) {
		super(x, y);
	}
}
