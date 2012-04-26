package model;

public class GroundObject extends Model {
	private Model stackedObject;

	public Model getStackedObject() {
		return stackedObject;
	}

	public void setStackedObject(Model stackedObject) {
		this.stackedObject = stackedObject;
	}
	
	public boolean canStackObject() {
		return true;
	}
	
	public GroundObject() {
		// TODO Auto-generated constructor stub
	}

	public GroundObject(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
}
