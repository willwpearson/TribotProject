package tri.model;

//Import as needed.
import tri.controller.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import java.util.Calendar;

import java.net.*;
import java.io.*;

public class Tribot 
{
	private TriController appController;
	
	private Calendar time;
	
	private List<String> rawInputVals;
	private List<String> sortedArbPairs;
	private HashMap<String, Double> arbVals;
	private HashMap<Integer, String> arbPairs;
	
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
		arbPairs = new HashMap<Integer, String>();
		
		time = Calendar.getInstance();
	}
	
	public void initializeMapFromFile()
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
	}
	
	public void initializeMapToMap()
	{
		int counter = 0;
		String [] keyValues = (String[]) arbVals.keySet().toArray();
		
		while(counter < keyValues.length)
		{
			arbPairs.put(counter, keyValues[counter]);
		}
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
			
	        IOController.saveToFile(appController, rawInputVals.toString(), "/Users/optimii/General-Programming/TribotProject");
	        fileName = "Saved Stream" + time.get(Calendar.HOUR_OF_DAY) + " - " + time.get(Calendar.MINUTE) + ".txt";
		}
		catch(Exception error)
		{
			appController.handleErrors(error);
		}
	}
	
	public List<String> calculateArbitrageTrios()
	{
		initializeMapFromFile();
		initializeMapToMap();
		
		List<String> arbTrios = new ArrayList<String>();
		
		for(int i = 0; i < arbPairs.size(); i++)
		{
			String arbitrageTrioValue = "";
			String arbPair = "";
			String value = "";
			
			arbPair = arbPairs.get(i);
			value = arbVals.get(arbPair).toString();
			
			arbitrageTrioValue = arbPair + "," + value;
			
			arbTrios.add(arbitrageTrioValue);
		}
		
		return arbTrios;
	}
	
	public List<String> calculateArbitrage()
	{
		
		
		return sortedArbPairs;
	}
}
