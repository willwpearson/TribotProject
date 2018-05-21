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
	private HashMap<String, Integer> sortedArbVals;
	
	private Scanner appScanner;
	private URL valueSite;
	private BufferedReader inputReader;
	private String arbitrageFile;
	private String fileName;
	
	public Tribot()
	{	
		rawInputVals = new ArrayList<String>();
		sortedArbVals = new HashMap<String, Integer>();
	}
	
	public HashMap<String, Integer> readToListFromDoc()
	{
		arbitrageFile = IOController.loadFromFile(appController, fileName);
		
		//currencyPair, last, lowestAsk, highestBid, percentChange, baseVolume, quoteVolume, isFrozen, 24hrHigh, 24hrLow
		
		
		return sortedArbVals;
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
