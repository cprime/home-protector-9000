package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ActionsPanel extends JPanel implements ActionListener {
	private JButton toggleActivityButton;
	private JButton addDirtButton;
	private JButton addFireButton;
	private JButton addObstructionButton;
	private JButton removeObstructionButton;
	private JButton removeDirtButton;
	private JButton removeFireButton;
	private JButton drainEnergyButton;
	private JButton fillEnergyButton;
	private JButton toggleRandomActionsButton;
	
	private boolean randomActionsOn = false;
	private boolean activityOn = false;
	
	public ActionsPanelDelegate delegate;
	
	public ActionsPanelDelegate getDelegate() {
		return this.delegate;
	}
	public void setDelegate(ActionsPanelDelegate delegate) {
		this.delegate = delegate;
	}
	
	public ActionsPanel() {
		super();
		
		toggleActivityButton = new JButton("            Start            ");
		toggleActivityButton.addActionListener(this);
		this.add(toggleActivityButton);
		
		addDirtButton = new JButton("Add Dirt");
		addDirtButton.addActionListener(this);
		this.add(addDirtButton);

		removeDirtButton = new JButton("Remove Dirt");
		removeDirtButton.addActionListener(this);
		this.add(removeDirtButton);
		
		addFireButton = new JButton("Add Fire");
		addFireButton.addActionListener(this);
		this.add(addFireButton);
		
		removeFireButton = new JButton("Remove Fire");
		removeFireButton.addActionListener(this);
		this.add(removeFireButton);
		
		addObstructionButton = new JButton("Add Obstruction");
		addObstructionButton.addActionListener(this);
		this.add(addObstructionButton);
		
		removeObstructionButton = new JButton("Remove Obstructions");
		removeObstructionButton.addActionListener(this);
		this.add(removeObstructionButton);
		
		drainEnergyButton = new JButton("Drain Energy");
		drainEnergyButton.addActionListener(this);
		this.add(drainEnergyButton);
		
		fillEnergyButton = new JButton("Fill Energy");
		fillEnergyButton.addActionListener(this);
		this.add(fillEnergyButton);
		
		toggleRandomActionsButton = new JButton("Enable Random Actions");
		toggleRandomActionsButton.addActionListener(this);
		this.add(toggleRandomActionsButton);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == addDirtButton) {
			delegate.addDirt();
		} else if(source == addFireButton) {
			delegate.addFire();
		} else if(source == removeDirtButton) {
			delegate.removeAllDirt();
		} else if(source == removeFireButton) {
			delegate.removeAllFire();
		} else if(source == drainEnergyButton) {
			delegate.depleteEnergy();
		} else if(source == fillEnergyButton) {
			delegate.fillEnergey();
		} else if(source == addObstructionButton) {
			delegate.addObstruction();
		} else if(source == removeObstructionButton) {
			delegate.removeAllObstructions();
		} else if(source == toggleRandomActionsButton) {
			if(!randomActionsOn) {
				randomActionsOn = true;
				toggleRandomActionsButton.setText("Disable Random Actions");
				delegate.enableRandomActions();
			} else {
				randomActionsOn = false;
				toggleRandomActionsButton.setText("Enable Random Actions");
				delegate.disableRandomActions();
			}
		} else if(source == toggleActivityButton) {
			if(!activityOn) {
				activityOn = true;
				toggleActivityButton.setText("Pause");
				delegate.start();
			} else {
				activityOn = false;
				toggleActivityButton.setText("Resume");
				delegate.stop();
			}
		}
	}
}
