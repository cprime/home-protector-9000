package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class StageBasedLoop {
	private final int stepTime = 250;
	
	private StageBasedLoopDelegate delegate;
	private Timer stage1Timer;
	private Timer stage2Timer;
	private Timer stage3Timer;

	public StageBasedLoopDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(StageBasedLoopDelegate delegate) {
		this.delegate = delegate;
	}

	public StageBasedLoop(StageBasedLoopDelegate delegate) {
		super();
		
		this.setDelegate(delegate);
		
		stage1Timer = new Timer(stepTime, new Stage1ActionListener());
		stage1Timer.setRepeats(false);
		
		stage2Timer = new Timer(stepTime, new Stage2ActionListener());
		stage2Timer.setRepeats(false);
		
		stage3Timer = new Timer(stepTime, new Stage3ActionListener());
		stage3Timer.setRepeats(false);
	}
	
	public void run() {
		if(delegate.keepLooping())
			stage1Timer.start();
	}
	public void stop() {
		stage1Timer.stop();
		stage2Timer.stop();
		stage3Timer.stop();
	}
	public void restart() {
		this.stop();
		this.run();
	}
	
	private class Stage1ActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			stage1Timer.stop();
			
			System.out.println("Run Stage 1");
			delegate.stage1();
			
			if(delegate.keepLooping())
				stage2Timer.start();
		}
		
	}
	private class Stage2ActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			stage2Timer.stop();
			
			System.out.println("Run Stage 2");
			delegate.stage2();
			
			if(delegate.keepLooping())
				stage3Timer.start();
		}
		
	}
	private class Stage3ActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			stage3Timer.stop();
			
			System.out.println("Run Stage 3");
			delegate.stage3();
			
			if(delegate.keepLooping())
				stage1Timer.start();
		}
		
	}
}
