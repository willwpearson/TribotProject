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
	
	public void passToTribot(String url)
	{
		tribot.saveToDrive(url);
	}
	
	public void handleErrors(Exception error)
	{
		System.out.println("There was an error.");
	}
}
