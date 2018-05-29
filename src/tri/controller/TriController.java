package tri.controller;

import tri.view.*;
import tri.model.Tribot;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

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
	
	public List<String> passArbitrage()
	{
		return tribot.calculateArbitrage();
	}
	
	public void handleErrors(Exception error)
	{
		System.out.println("There was an error.");
	}
}
