package model;

public enum Direction {
	UNKNOWN,
	NONE,
	NORTH,
	EAST,
	SOUTH,
	WEST,
	;
	
	public Direction clockwiseDirection() {
		switch(this) {
		case NORTH:
			return EAST;
		case EAST:
			return SOUTH;
		case SOUTH:
			return WEST;
		case WEST:
			return NORTH;
		}
		return this;
	}
	
	public Direction counterClockwiseDirection() {
		switch(this) {
		case NORTH:
			return WEST;
		case EAST:
			return NORTH;
		case SOUTH:
			return EAST;
		case WEST:
			return SOUTH;
		}
		return this;
	}
	
	public String toString() {
		switch(this) {
		case NORTH:
			return "NORTH";
		case EAST:
			return "EAST";
		case SOUTH:
			return "SOUTH";
		case WEST:
			return "WEST";
		case NONE:
			return "NONE";
		case UNKNOWN:
			return "UNKNOWN";
		}
		return "UNKNOWN";
	}
}
