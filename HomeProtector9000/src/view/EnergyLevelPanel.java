package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import model.ProtectorBot;

@SuppressWarnings("serial")
public class EnergyLevelPanel extends JPanel {
	private ProtectorBot bot;

	public ProtectorBot getBot() {
		return bot;
	}

	public void setBot(ProtectorBot bot) {
		this.bot = bot;
	}
	
	public EnergyLevelPanel(ProtectorBot bot) {
		super();
		this.setBot(bot);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g.setColor(Color.WHITE);
		g.fillRect(1, 1, this.getWidth() - 2, this.getHeight() - 2);
		
		if(bot.getPowerLevel() > 0) {
			if(bot.getPowerLevel() > 60) {
				g.setColor(Color.GREEN);
			} else if(bot.getPowerLevel() > 30) {
				g.setColor(Color.YELLOW);
			} else if(bot.getPowerLevel() > 15) {
				g.setColor(Color.ORANGE);
			} else {
				g.setColor(Color.RED);
			}
			int scaledWidth = (int)((this.getWidth() - 2) * (bot.getPowerLevel() / (float)bot.getPowerLimit()));
			g.fillRect(1, 1, scaledWidth, this.getHeight() - 2);
		}
		
		g.setColor(Color.BLACK);
		if(bot.isLowPowered()) {
			g.drawString("Low Power", 17, this.getHeight() - 15);
		} else if(bot.isFullyPowered()) {
			g.drawString("Full Power", 17, this.getHeight() - 15);
		}
	}
}
