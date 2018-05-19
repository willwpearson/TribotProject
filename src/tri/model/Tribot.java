package tri.model;

//Import as needed.
import tri.controller.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import java.net.*;
import java.io.*;

public class Tribot 
{
	private IOController inputHandler;
	private TriController appController;
	
	private List<String> rawInputVals;
	private List<String> sortedValues;
	private Scanner appScanner;
	private URL valueSite;
	private BufferedReader inputReader;
	private File arbitrageFile;
	
	public Tribot()
	{
		inputHandler = new IOController();
		
		sortedValues = new ArrayList<String>();
		rawInputVals = new ArrayList<String>();
	}
	
	public List<String> readToListFromDoc()
	{
		
		
		return sortedValues;
	}
	
	public void saveToDrive(String url)
	{
		try
		{
			valueSite = new URL(url);
			inputReader = new BufferedReader(new InputStreamReader(valueSite.openStream()));
			
			String inputLine;
	        while ((inputLine = inputReader.readLine()) != null)
	        {
	        	rawInputVals.add(inputLine);
	        	System.out.println(inputLine);
	        }
			
	        inputReader.close();
			
	        inputHandler.saveToFile(appController, rawInputVals.toString(), "/Users/optimii/Documents/Tribot Aribtrage Outputs");
		}
		catch(Exception error)
		{
			appController.handleErrors(error);
		}
	}
	
	public String calculateArbitrage()
	{
		String values = "";
		
		//May need list for this, output the list to the view as a String. Use multiple lists or 2DArray to coordinate matching values.
		
		return values;
	}
}
