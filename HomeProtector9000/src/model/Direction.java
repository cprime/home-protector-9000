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
}
