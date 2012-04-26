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
		action.setTargetPosition(target.getPosition());
		
		return action;
	}
	
	public Action blowAction(Model target) {
		Action action = new Action();
		action.setActionType(ActionType.BLOW);
		action.setActor(this.bot);
		action.setTarget(target);
		action.setTargetPosition(target.getPosition());
		
		return action;
	}
	
	public Action chargeAction(Model target) {
		Action action = new Action();
		action.setActionType(ActionType.CHARGE);
		action.setActor(this.bot);
		action.setTargetPosition(target.getPosition());
		
		return action;
	}
	
	public Action pickupAction(Model target) {
		Action action = new Action();
		action.setActionType(ActionType.PICKUP);
		action.setActor(this.bot);
		action.setTarget(target);
		action.setTargetPosition(target.getPosition());
		
		return action;
	}
	
	public Action replaceAction(Model target) {
		Action action = new Action();
		action.setActionType(ActionType.REPLACE);
		action.setActor(this.bot);
		action.setTarget(target);
		action.setTargetPosition(target.getPosition());
		
		return action;
	}
	
	public Action moveAction(Point from, Point to) {
		Action action = new Action();
		action.setActionType(ActionType.MOVE);
		action.setActor(this.bot);
		action.setActorPosition(from);
		action.setTargetPosition(to);

		return action;
	}
	
	public Action waitAction() {
		Action action = new Action();
		action.setActionType(ActionType.WAIT);
		action.setActor(this.bot);
		
		return action;
	}
}
