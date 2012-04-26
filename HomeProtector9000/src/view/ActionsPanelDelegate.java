package view;

public interface ActionsPanelDelegate {
	public void start();
	public void stop();
	
	public void addObstruction();
	public void removeAllObstructions();
	
	public void addDirt();
	public void removeAllDirt();
	
	public void addFire();
	public void removeAllFire();
	
	public void fillEnergey();
	public void depleteEnergy();
	
	public void enableRandomActions();
	public void disableRandomActions();
}
