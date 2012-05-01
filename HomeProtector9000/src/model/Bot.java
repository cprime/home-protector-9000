package model;

import java.awt.Point;
import java.util.ArrayList;

import ai.AiState;

public interface Bot {
	public ArrayList<Action> validActions(World w);
	public ArrayList<Action> validActions(World w, Point fromPosition);
	public void setPosition(Point p, Direction d);
	public void setPosition(Position p);
	public Position getPosition();
	public void moveTo(int x, int y);
	public void moveBy(int dx, int dy);
	public void setAiState(AiState state);
	public AiState getAiState();
}
