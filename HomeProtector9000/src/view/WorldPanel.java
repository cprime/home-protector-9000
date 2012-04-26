package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JPanel;

import model.*;

@SuppressWarnings("serial")
public class WorldPanel extends JPanel {
	private World model;
	private final int tileWidth = 54;
	private final int tileHeight = 54;

	public World getModel() {
		return model;
	}

	public void setModel(World model) {
		this.model = model;

		this.repaint();
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);

		this.repaint();
	}

	public WorldPanel(World world) {
		this.model = world;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if(tileWidth * tileHeight != 0) {
			//Color Background
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, tileWidth * this.model.getWidth(), tileHeight * this.model.getHeight());

			for(int r = 0; r < this.model.getHeight(); r++) {
				for(int c = 0; c < this.model.getWidth(); c++) {
					//Draw Checker Board
					g.setColor(Color.WHITE);
					g.fillRect(c * tileWidth + 1, r * tileHeight + 1, tileWidth - 2, tileHeight - 2);

					Model m = this.model.objectAtPosition(new Point(c, r));
					if(m != null) {
						this.drawModel(g, m);
					}
				}
			}
		}
	}

	private void drawModel(Graphics g, Model m) {
		if(m instanceof ProtectorBot) {
			ProtectorBot b = (ProtectorBot)m;
			
			String mode = "small";
			if(b.hasExtinguisher()) {
				mode = "hat";
			}
			
			String direction = "front";
			if(b.getDirection() == Direction.NORTH) {
				direction = "back";
			} else if(b.getDirection() == Direction.EAST) {
				direction = "right";
			} else if(b.getDirection() == Direction.WEST) {
				direction = "left";
			}
			
			Image img = Toolkit.getDefaultToolkit().getImage(new String("ImagesForProject/icleaner" + mode + direction + ".png"));
			g.drawImage(img, b.getX() * tileWidth + 2, b.getY() * tileHeight + 2, this);
		} else if(m instanceof Fire) {
			Image img = Toolkit.getDefaultToolkit().getImage("ImagesForProject/fire.gif");
			g.drawImage(img, m.getX() * tileWidth + 2, m.getY() * tileHeight + 2, this);
		} else if(m instanceof Dirt) {
			Image img = Toolkit.getDefaultToolkit().getImage("ImagesForProject/dirt.png");
			g.drawImage(img, m.getX() * tileWidth + 2, m.getY() * tileHeight + 2, this);

			GroundObject t = (GroundObject)m;
			if(t.getStackedObject() != null) {
				this.drawModel(g, t.getStackedObject());
			}
		}  else if(m instanceof Obstruction) {
			Obstruction o = (Obstruction)m;
			Image img;
			if(o.getType() == 0) {
				img = Toolkit.getDefaultToolkit().getImage("ImagesForProject/plant.png");
			} else {
				img = Toolkit.getDefaultToolkit().getImage("ImagesForProject/sneakers.png");
			}
			g.drawImage(img, m.getX() * tileWidth + 2, m.getY() * tileHeight + 2, this);
		} else if(m instanceof BaseStation) {
			Image img = Toolkit.getDefaultToolkit().getImage("ImagesForProject/power.png");
			g.drawImage(img, m.getX() * tileWidth + 2, m.getY() * tileHeight + 2, this);

			GroundObject t = (GroundObject)m;
			if(t.getStackedObject() != null) {
				this.drawModel(g, t.getStackedObject());
			}

			BaseStation b = (BaseStation)m;
			if(b.hasExtinguisher()) {
				img = Toolkit.getDefaultToolkit().getImage("ImagesForProject/firehat.png");
				g.drawImage(img, m.getX() * tileWidth, m.getY() * tileHeight, this);
			}
		}
	}

}
