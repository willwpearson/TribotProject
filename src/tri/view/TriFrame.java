package tri.view;

import tri.controller.TriController;

import javax.swing.JFrame;

public class TriFrame extends JFrame
{
	private TriController appController;
	private TriPanel appPanel;
	
	public TriFrame(TriController appController)
	{
		super();
		this.appController = appController;
		this.appPanel = new TriPanel(appController);
		
		setupFrame();
	}
	
	public void setupFrame()
	{
		this.setContentPane(appPanel);		
		this.setTitle("Triangular Arbitrage Bot");
		this.setSize(500, 500);
		this.setVisible(true);
	}
	
	public TriController getTriController()
	{
		return appController;
	}
}
