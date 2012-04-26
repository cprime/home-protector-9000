package model;

import java.awt.Point;
import java.util.ArrayList;

import ai.AiState;

public interface Bot {
	public ArrayList<Action> validActions(World w);
	public ArrayList<Action> validActions(World w, Point fromPosition);
	public int getX();
	public void setX(int x);
	public int getY();
	public void setY(int y);
	public Direction getDirection();
	public void setDirection(Direction d);
	public Point getPosition();
	public void moveTo(int x, int y);
	public void moveBy(int dx, int dy);
	public void setAiState(AiState state);
	public AiState getAiState();
}
