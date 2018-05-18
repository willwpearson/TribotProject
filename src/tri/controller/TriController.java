package tri.controller;

import tri.view.*;
import tri.model.Tribot;

public class TriController 
{
	private Tribot tribot;
	private TriFrame appFrame;
	
	public TriController()
	{
		tribot = new Tribot();
		appFrame = new TriFrame(this);
	}
	
	public void start()
	{
		
	}
}
