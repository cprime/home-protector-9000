package model;

import java.awt.Point;
import java.util.ArrayList;

import ai.AiState;

public class ProtectorBot extends Model implements Bot {
	private final int fullyChargedPowerLevel = 100;
	private final int movementCost = 2;
	
	private int powerLevel;
	private boolean hasExtinguisher;
	private AiState aiState;
	private Direction direction;
	
	public Direction getDirection() {
		return this.direction;
	}
	public void setDirection(Direction d) {
		this.direction = d;
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
		setDirection(Direction.SOUTH);
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
	}
	public void moveBy(int dx, int dy) {
		this.setX(this.getX() + dx);
		this.setY(this.getY() + dy);
		
		this.powerLevel -= movementCost * ((Math.abs(dx) + Math.abs(dy)));
	}
}
