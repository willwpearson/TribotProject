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
	private List<String> finalArbTrio;
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
		finalArbTrio = new ArrayList<String>();
		arbVals = new HashMap<String, Double>();
		arbPairs = new HashMap<Integer, String>();
		
		time = Calendar.getInstance();
	}
	
	public void initializeMapFromFile()
	{
		arbitrageFile = IOController.loadFromFile(appController, fileName);
		
		appScanner = new Scanner(arbitrageFile);
		//While loop for the scanner to gather the data.
		
		//Add a condition to remove all extraneous text, unneeded words and punctuation.
		
		boolean scanNext = false;
		
		while(scanNext == false)
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
			
			if(!appScanner.hasNext() || !appScanner.hasNextDouble())
			{
				scanNext = true;
			}
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
			
			arbPair = arbPairs.get(i);
			
			arbTrios.add(arbPair);
		}
		
		List<String> tempList = new ArrayList<String>(arbTrios);
		arbTrios.clear();
		
		//Naming Convention: First # is the pair, second # is the half of the pair; first or second.
		
		//First Value
		for(int i = 0; i < tempList.size(); i++)
		{
			String temp1_2 = tempList.get(i).substring(4);
			//Second Value
			for(int j = 1; j < tempList.size(); j++)
			{
				String temp2_1 = tempList.get(j).substring(0, 3);
				
				if(temp1_2.equals(temp2_1))
				{
					String temp2_2 = tempList.get(j).substring(4);
					
					//Third Value
					for(int k = 2; k < tempList.size(); k++)
					{
						String temp3_1 = tempList.get(i).substring(0, 3);
						
						if(temp2_2.equals(temp3_1))
						{
							String temp1_1 = tempList.get(i).substring(0, 3);
							String temp3_2 = tempList.get(k).substring(4);
							
							if(temp1_1.equals(temp3_2))
							{
								String trioToAdd = temp1_1 + "_" + temp1_2 + " " + temp2_1 + "_" + temp2_2 + " " + temp3_1 + "_" + temp3_2;
								
								arbTrios.add(trioToAdd);
							}
						}
					}
				}
			}
		}
		
		return arbTrios;
	}
	
	public List<String> calculateArbitrage()
	{
		List<String> arbTrios = calculateArbitrageTrios();
		
		for(int i = 0; i < arbTrios.size(); i++)
		{
			Scanner trioScanner = new Scanner(arbTrios.get(i));
			
			//Retrieve values for each part of the trio.
			Double firstPair = arbVals.get(trioScanner.next());
			Double secondPair = arbVals.get(trioScanner.next());
			Double thirdPair = arbVals.get(trioScanner.next());
			
			Double arbGap = calculateGap(firstPair, secondPair, thirdPair);
			
			finalArbTrio.add(arbTrios.get(i) + ", " + arbGap);
			
			trioScanner.close();
		}
		
		return finalArbTrio;
	}
	
	public Double calculateGap(Double first, Double second, Double third)
	{
		Double gapFinal = 0.0;
		
		gapFinal = (((1 / first) * 0.9975) * ((1 / second) * 0.9975) * ((1/ third) * 0.9975)) - 1;
		
		return gapFinal;
	}
}
