package model;

import java.util.Random;

public class Obstruction extends Model {
	int type;
	
	public void setType(int type) {
		this.type = type;
	}
	public int getType() {
		return this.type;
	}
	
	public Obstruction(int x, int y) {
		super(x, y);
		
		Random generator = new Random();
		type = Math.abs(generator.nextInt()) % 2;
	}
}
