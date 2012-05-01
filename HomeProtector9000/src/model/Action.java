package model;

import java.awt.Point;

public class Action {
	private ActionType actionType;
	private Bot actor;
	private Model target;
	private Point actorPoint;
	private Point targetPoint;
	
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
	public Point getTargetPoint() {
		return targetPoint;
	}
	public void setTargetPoint(Point targetPoint) {
		this.targetPoint = targetPoint;
	}
	public ActionType getActionType() {
		return actionType;
	}
	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}
	
	public Point getActorPoint() {
		return actorPoint;
	}
	public void setActorPoint(Point actorPoint) {
		this.actorPoint = actorPoint;
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
				return new String("<Suck " + this.target + " at " + this.targetPoint + ">");
			case BLOW:
				return new String("<Blow on " + this.target + " at " + this.targetPoint + ">");
			case CHARGE:
				return new String("<Charge from " + this.target + " at " + this.targetPoint + ">");
			case PICKUP:
				return new String("<Pickup from " + this.target + " at " + this.targetPoint + ">");
			case REPLACE:
				return new String("<Replace to " + this.target + " at " + this.targetPoint + ">");
			case MOVE:
				return new String("<Move " + actorPoint + " to " + this.targetPoint + ">");
		}
		return null;
	}
}
