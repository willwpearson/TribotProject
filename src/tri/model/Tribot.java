package tri.model;

//Import as needed.
import tri.controller.*;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import java.net.*;
import java.io.*;

public class Tribot 
{
	private TriController appController;
	
	private List<String> rawInputVals;
	private List<String> sortedArbPairs;
	private HashMap<String, Double> arbVals;
	
	private Scanner appScanner;
	private URL valueSite;
	private BufferedReader inputReader;
	private String arbitrageFile;
	private String fileName;
	
	public Tribot()
	{	
		rawInputVals = new ArrayList<String>();
		sortedArbPairs = new ArrayList<String>();
		arbVals = new HashMap<String, Double>();
	}
	
	public HashMap<String, Double> readToListFromDoc()
	{
		arbitrageFile = IOController.loadFromFile(appController, fileName);
		
		appScanner = new Scanner(arbitrageFile);
		//While loop for the scanner to gather the data.
		while(appScanner.hasNext())
		{
			//currencyPair, last
			String currencyPair = "";
			double last = 0.0;
			//Checker for doubles because we only need the first
			boolean firstDouble = true;
			
			if(!appScanner.hasNextDouble())
			{
				currencyPair = appScanner.next();
			}
			if(appScanner.hasNextDouble() && firstDouble == true)
			{
				firstDouble = false;
				last = appScanner.nextDouble();
			}
			
			arbVals.put(currencyPair, last);
		}
		
		return arbVals;
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
			
	        IOController.saveToFile(appController, rawInputVals.toString(), "/Users/optimii/Documents/Tribot Aribtrage Outputs");
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
