package controller;

public interface StageBasedLoopDelegate {
	public void stage1();
	public void stage2();
	public void stage3();
	public boolean keepLooping();
}
