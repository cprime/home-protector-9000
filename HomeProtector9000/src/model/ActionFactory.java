package model;

import java.awt.Point;

public class ActionFactory {
	private Bot bot;
	
	public ActionFactory(Bot bot) {
		this.bot = bot;
	}
	
	public Action suckAction(Model target) {
		Action action = new Action();
		action.setActionType(ActionType.SUCK);
		action.setActor(this.bot);
		action.setTarget(target);
		action.setTargetPoint(target.getPoint());
		
		return action;
	}
	
	public Action blowAction(Model target) {
		Action action = new Action();
		action.setActionType(ActionType.BLOW);
		action.setActor(this.bot);
		action.setTarget(target);
		action.setTargetPoint(target.getPoint());
		
		return action;
	}
	
	public Action chargeAction(Model target) {
		Action action = new Action();
		action.setActionType(ActionType.CHARGE);
		action.setActor(this.bot);
		action.setTargetPoint(target.getPoint());
		
		return action;
	}
	
	public Action pickupAction(Model target) {
		Action action = new Action();
		action.setActionType(ActionType.PICKUP);
		action.setActor(this.bot);
		action.setTarget(target);
		action.setTargetPoint(target.getPoint());
		
		return action;
	}
	
	public Action replaceAction(Model target) {
		Action action = new Action();
		action.setActionType(ActionType.REPLACE);
		action.setActor(this.bot);
		action.setTarget(target);
		action.setTargetPoint(target.getPoint());
		
		return action;
	}
	
	public Action moveAction(Point from, Point to) {
		Action action = new Action();
		action.setActionType(ActionType.MOVE);
		action.setActor(this.bot);
		action.setActorPoint(from);
		action.setTargetPoint(to);

		return action;
	}
	
	public Action turnClockwiseAction() {
		Action action = new Action();
		action.setActionType(ActionType.TURNCLOCKWISE);
		action.setActor(this.bot);
		
		return action;
	}
	
	public Action turnCounterClockwiseAction() {
		Action action = new Action();
		action.setActionType(ActionType.TURNCOUNTERCLOCKWISE);
		action.setActor(this.bot);
		
		return action;
	}
	
	public Action waitAction() {
		Action action = new Action();
		action.setActionType(ActionType.WAIT);
		action.setActor(this.bot);
		
		return action;
	}
}
