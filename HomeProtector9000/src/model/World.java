package model;

import java.awt.Point;
import java.util.ArrayList;

public class World {
	private Model[][] worldGrid;
	private int width;
	private int height;
	
	private ProtectorBot bot;
	private BaseStation station;

	public BaseStation getStation() {
		return station;
	}

	public void setStation(BaseStation station) {
		this.station = station;
	}

	public ProtectorBot getBot() {
		return bot;
	}

	public void setBot(ProtectorBot bot) {
		this.bot = bot;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Model[][] getWorldGrid() {
		return worldGrid;
	}

	public void setWorldGrid(Model[][] worldGrid) {
		this.worldGrid = worldGrid;
	}
	
	public World() {
		this(1, 1);
	}
	public World(int w, int h) {
		this.width = w;
		this.height = h;
		
		this.worldGrid = new Model[h][w];
		for(int r = 0; r < h; r++) {
			for(int c = 0; c < w; c++) {
				this.worldGrid[r][c] = null;
			}
		}
	}
	
	public Model objectAtPosition(Point p) {
		return this.worldGrid[p.y][p.x];
	}
	
	public boolean addObject(Model m) {
		return this.addObjectAtPosition(m, new Point(m.getX(), m.getY()));
	}
	
	public boolean addObjectAtPosition(Model m, Point p) {
		if(this.objectAtPosition(p) != null) {
			return false;
		}
		
		this.worldGrid[p.y][p.x] = m;
		m.setX(p.x);
		m.setY(p.y);
		
		return true;
	}
	
	public void removeObjectAtPosition(Point p) {
		this.worldGrid[p.y][p.x] = null;
	}
	
	public ArrayList<Point> pointsArountPoint(Point p) {
		ArrayList<Point> ret = new ArrayList<Point>();
		
		if(p.x + 1 < this.getWidth()) ret.add(new Point(p.x + 1, p.y));
		if(p.y + 1 < this.getHeight()) ret.add(new Point(p.x, p.y + 1));
		if(p.x - 1 >= 0) ret.add(new Point(p.x - 1, p.y));
		if(p.y - 1 >= 0) ret.add(new Point(p.x, p.y - 1));
		
		return ret;
	}
	
	public boolean containsDirt() {
		for(int r = 0; r < this.getHeight(); r++) {
			for(int c = 0; c < this.getWidth(); c++) {
				Point point = new Point(c, r);
				Model m = this.objectAtPosition(point);
				if(m != null && m instanceof Dirt) {
					return true;
				}
			}
		}
		return false;
	}
	public boolean containsFire() {
		for(int r = 0; r < this.getHeight(); r++) {
			for(int c = 0; c < this.getWidth(); c++) {
				Point point = new Point(c, r);
				Model m = this.objectAtPosition(point);
				if(m != null && m instanceof Fire) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void executeAction(Action a) {
		System.out.println("Execute Action: " + a);
		
		switch(a.getActionType()) {
		case UNKNOWN:
			//DO NOTHING
			break;
		case WAIT:
			//DO NOTHING
			break;
		case SUCK:
			this.executeSuckAction(a);
			break;
		case BLOW:
			this.executeBlowAction(a);
			break;
		case CHARGE:
			this.executeChargeAction(a);
			break;
		case PICKUP:
			this.executePickupAction(a);
			break;
		case REPLACE:
			this.executeReplaceAction(a);
			break;
		case MOVE:
			this.executeMoveAction(a);
			break;
		case TURNCLOCKWISE:
			this.executeTurnClockwiseAction(a);
			break;
		case TURNCOUNTERCLOCKWISE:
			this.executeTurnCounterClockwiseAction(a);
			break;
		}
	}
	public void executeSuckAction(Action a) {
		Point p = a.getTargetPoint();
		this.worldGrid[p.y][p.x] = (Model)(a.getActor());
	}
	public void executeBlowAction(Action a) {
		Point p = a.getTargetPoint();
		this.worldGrid[p.y][p.x] = null;
	}
	public void executeChargeAction(Action a) {
		Model targetModel = this.objectAtPosition(a.getTargetPoint());
		ProtectorBot bot = (ProtectorBot)a.getActor();
		if(targetModel.getPoint().equals(bot.getPoint())) {
			bot.chargeBattery();
		}
	}
	public void executePickupAction(Action a) {
		ProtectorBot bot = (ProtectorBot)a.getActor();
		BaseStation station = (BaseStation)a.getTarget();
		
		bot.pickupFireExtinguisher();
		station.setHasExtinguisher(false);
	}
	public void executeReplaceAction(Action a) {
		ProtectorBot bot = (ProtectorBot)a.getActor();
		BaseStation station = (BaseStation)a.getTarget();
		
		bot.dropFireExtinguisher();
		station.setHasExtinguisher(true);
	}
	public void executeMoveAction(Action a) {
		Model startingModel = this.objectAtPosition(a.getActorPoint());
		Model targetModel = this.objectAtPosition(a.getTargetPoint());
		ProtectorBot bot = (ProtectorBot)a.getActor();
		
		if(startingModel instanceof GroundObject) {
			((GroundObject)startingModel).setStackedObject(null);
		}
		else {
			this.worldGrid[bot.getY()][bot.getX()] = null;
		}
		
		bot.moveTo((int)a.getTargetPoint().getX(), (int)a.getTargetPoint().getY());
		
		if(targetModel instanceof GroundObject) {
			((GroundObject)targetModel).setStackedObject(bot);
		} else {
			this.worldGrid[bot.getY()][bot.getX()] = bot;
		}
	}
	public void executeTurnClockwiseAction(Action a) {
		this.bot.setPosition(this.bot.getPosition().point, this.bot.getPosition().direction.clockwiseDirection());
	}
	public void executeTurnCounterClockwiseAction(Action a) {
		this.bot.setPosition(this.bot.getPosition().point, this.bot.getPosition().direction.counterClockwiseDirection());
	}
}
