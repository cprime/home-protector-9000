package model;

import java.awt.Point;

public class Action {
	private ActionType actionType;
	private Bot actor;
	private Model target;
	private Point actorPosition;
	private Point targetPosition;
	
	public Bot getActor() {
		return actor;
	}
	public void setActor(Bot actor) {
		this.actor = actor;
	}
	public Model getTarget() {
		return target;
	}
	public void setTarget(Model target) {
		this.target = target;
	}
	public Point getTargetPosition() {
		return targetPosition;
	}
	public void setTargetPosition(Point targetPosition) {
		this.targetPosition = targetPosition;
	}
	public ActionType getActionType() {
		return actionType;
	}
	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}
	
	public Point getActorPosition() {
		return actorPosition;
	}
	public void setActorPosition(Point actorPosition) {
		this.actorPosition = actorPosition;
	}
	public Action() {
		super();
	}
	
	public String toString() {
		switch(this.actionType) {
			case UNKNOWN:
				return new String("<Unknown>");
			case WAIT:
				return new String("<Wait>");
			case SUCK:
				return new String("<Suck " + this.target + " at " + this.targetPosition + ">");
			case BLOW:
				return new String("<Blow on " + this.target + " at " + this.targetPosition + ">");
			case CHARGE:
				return new String("<Charge from " + this.target + " at " + this.targetPosition + ">");
			case PICKUP:
				return new String("<Pickup from " + this.target + " at " + this.targetPosition + ">");
			case REPLACE:
				return new String("<Replace to " + this.target + " at " + this.targetPosition + ">");
			case MOVE:
				return new String("<Move " + actorPosition + " to " + this.targetPosition + ">");
		}
		return null;
	}
}
