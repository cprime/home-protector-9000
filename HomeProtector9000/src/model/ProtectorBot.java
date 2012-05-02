package model;

import java.awt.Point;
import java.util.ArrayList;

import ai.AiState;

public class ProtectorBot extends Model implements Bot {
	private final int fullyChargedPowerLevel = 100;
	private final int movementCost = 4;
	private final int turnCost = 1;
	
	private int powerLevel;
	private boolean hasExtinguisher;
	private AiState aiState;
	private Position position;
	
	public void setPosition(Point p, Direction d) {
		super.setX(p.x);
		super.setY(p.y);
		
		this.setPosition(new Position(p, d));
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public Position getPosition() {
		return position;
	}
	
	public Direction getDirection() {
		return this.position.direction;
	}
	public void setDirection(Direction d) {
		this.position.direction = d;
	}
	
	public void setAiState(AiState state) {
		this.aiState = state;
	}
	public AiState getAiState() {
		return this.aiState;
	}
	
	public int getPowerLevel() {
		return powerLevel;
	}

	private void setPowerLevel(int powerLevel) {
		this.powerLevel = powerLevel;
	}
	public int getPowerLimit() {
		return fullyChargedPowerLevel;
	}
	
	public boolean isFullyPowered() {
		return powerLevel == fullyChargedPowerLevel;
	}
	public boolean isLowPowered() {
		return powerLevel <= 0;
	}

	public boolean hasExtinguisher() {
		return hasExtinguisher;
	}

	private void setHasExtinguisher(boolean hasExtinguisher) {
		this.hasExtinguisher = hasExtinguisher;
	}

	public ProtectorBot(int x, int y) {
		super(x, y);
		
		setPowerLevel(fullyChargedPowerLevel);
		setHasExtinguisher(false);
		setPosition(new Position(new Point(x, y), Direction.SOUTH));
	}

	public ArrayList<Action> validActions(World w) {
		return this.validActions(w, new Point(this.getX(), this.getY()));
	}

	public ArrayList<Action> validActions(World w, Point fromPosition) {
		ArrayList<Action> ret = new ArrayList<Action>();
		
		ActionFactory factory = new ActionFactory(this);
		
		Model objectAtPosition = w.objectAtPosition(fromPosition);
		if(objectAtPosition instanceof Dirt) {
			ret.add(factory.suckAction(objectAtPosition));
		} else if(objectAtPosition instanceof BaseStation) {
			if(this.powerLevel < fullyChargedPowerLevel) {
				ret.add(factory.chargeAction(objectAtPosition));
			}
			if(this.hasExtinguisher) {
				ret.add(factory.replaceAction(objectAtPosition));
			} else {
				ret.add(factory.pickupAction(objectAtPosition));
			}
		}
		
		for(Point p : w.pointsArountPoint(fromPosition)) {
			objectAtPosition = w.objectAtPosition(p);
			if(objectAtPosition == null || objectAtPosition instanceof Dirt) {
				ret.add(factory.moveAction(fromPosition, p));
			} else if(objectAtPosition instanceof Fire) {
				if(this.hasExtinguisher) {
					ret.add(factory.blowAction(objectAtPosition));
				}
			}
		}
		
		return ret;
	}

	public void pickupFireExtinguisher() {
		setHasExtinguisher(true);
	}
	public void dropFireExtinguisher() {
		setHasExtinguisher(false);
	}
	public void drainBattery() {
		setPowerLevel(0);
	}
	public void chargeBattery() {
		setPowerLevel(fullyChargedPowerLevel);
	}
	
	public void moveTo(int x, int y) {
		this.powerLevel -= movementCost * (Math.abs(this.getX() - x) + Math.abs(this.getY() - y));
		
		this.setX(x);
		this.setY(y);
		this.setPosition(new Position(new Point(x, y), this.position.direction));
	}
	public void moveBy(int dx, int dy) {
		this.setX(this.getX() + dx);
		this.setY(this.getY() + dy);
		this.setPosition(new Position(new Point(this.getX(), this.getY()), this.position.direction));
		
		this.powerLevel -= movementCost * ((Math.abs(dx) + Math.abs(dy)));
	}
	public void turnClockwise() {
		this.position.direction = this.position.direction.clockwiseDirection();
		
		this.powerLevel -= turnCost;
	}
	public void turnCounterClockwise() {
		this.position.direction = this.position.direction.counterClockwiseDirection();
		
		this.powerLevel -= turnCost;
	}
}
