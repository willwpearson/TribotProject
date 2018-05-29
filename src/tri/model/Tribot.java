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
		
		appScanner = new Scanner(removeUnnecessary(arbitrageFile));
		//While loop for the scanner to gather the data.
		
		while(appScanner.hasNext())
		{
			//currencyPair, last
			String currencyPair = "";
			double last = 0.0;
			
			currencyPair = appScanner.next();
			last = appScanner.nextDouble();
			
			arbVals.put(currencyPair, last);
		}
		
		//System.out.println(arbVals.toString());
		
		appScanner.close();
	}
	
	public void initializeMapToMap()
	{
		int counter = 0;
		Object [] keyValues = arbVals.keySet().toArray();
		
		while(counter < keyValues.length)
		{
			arbPairs.put(counter, (String) keyValues[counter]);
			counter++;
		}
		
//		System.out.println(arbPairs.toString());
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
	        	//System.out.println(inputLine);
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
			String arbPair = "";
			
			arbPair = arbPairs.get(i);
			
			arbTrios.add(arbPair);
		}
		
//		System.out.println(arbTrios.toString());
		
		List<String> tempList = new ArrayList<String>(arbTrios);
		arbTrios.clear();
		
		//Naming Convention: First # is the pair, second # is the half of the pair; first or second.
		
		//First Value
		for(int i = 0; i < tempList.size(); i++)
		{
			String temp1 = tempList.get(i);
			String temp1_1 = tempList.get(i).substring(0, 3);
			String temp1_2 = tempList.get(i).substring(4);
			//Second Value
			for(int j = i; j < tempList.size(); j++)
			{
				String temp2 = tempList.get(j);
				String temp2_1 = tempList.get(j).substring(0, 3);
				String temp2_2 = tempList.get(j).substring(4);
				
				if(!temp1.equals(temp2))
				{
					if(temp1_2.equals(temp2_1) || temp1_2.equals(temp2_2))
					{
						//Third Value
						for(int k = j; k < tempList.size(); k++)
						{
							String temp3 = tempList.get(k);
							String temp3_1 = tempList.get(k).substring(0, 3);
							String temp3_2 = tempList.get(k).substring(4);
							String trioToAdd = "";
							
							if(!temp2.equals(temp3))
							{
								if(temp1_2.equals(temp2_1))
								{
									if(temp3.contains(temp1_1) && temp3.contains(temp2_2))
									{
										if(temp3_1.equals(temp2_2) || temp3_2.equals(temp1_1))
										{
											trioToAdd = temp1_1 + "_" + temp1_2 + " " + temp2_1 + "_" + temp2_2 + " " + temp3_2 + "_" + temp3_1;
											arbVals.put(temp3_2 + "_" + temp3_1, 1/arbVals.get(temp3));
										}
										else
										{
											trioToAdd = temp1_1 + "_" + temp1_2 + " " + temp2_1 + "_" + temp2_2 + " " + temp3;
										}
										arbTrios.add(trioToAdd);
									}
								}
								else if(temp1_2.equals(temp2_2))
								{
									if(temp3.contains(temp1_1) && temp3.contains(temp2_1))
									{
										if(temp3_1.equals(temp2_1))
										{
											trioToAdd = temp1_1 + "_" + temp1_2 + " " + temp2_2 + "_" + temp2_1 + " " + temp3_2 + "_" + temp3_1;
											arbVals.put(temp3_2 + "_" + temp3_1, 1/arbVals.get(temp3));
										}
										else
										{
											trioToAdd = temp1_1 + "_" + temp1_2 + " " + temp2_2 + "_" + temp2_1 + " " + temp3;
										}
										arbVals.put(temp2_2 + "_" + temp2_1, 1/arbVals.get(temp2));
										arbTrios.add(trioToAdd);
									}
								}
							}
						}
					}
				}
			}
		}
		
		//Special case for BTCD removal.
		for(int i = 0; i < arbTrios.size(); i++)
		{
			Scanner specialScan = new Scanner(arbTrios.get(i));
			if(!specialScan.next().contains("BTCD"))
			{
				if(!specialScan.next().contains("BTCD"))
				{
					if(specialScan.next().contains("BTCD"))
					{
						arbTrios.remove(i);
					}
				}
			}
		}
		
		System.out.println(arbTrios.toString());
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
//			System.out.println(firstPair + "");
			Double secondPair = arbVals.get(trioScanner.next());
//			System.out.println(secondPair + "");
			Double thirdPair = arbVals.get(trioScanner.next());
//			System.out.println(thirdPair + "");
			
			Double arbGap = calculateGap(firstPair, secondPair, thirdPair);
			
			finalArbTrio.add("\n\n" + (i + 1) + ") " + arbTrios.get(i) + " : " + arbGap);
			
			trioScanner.close();
		}
		
		return finalArbTrio;
	}
	
	public Double calculateGap(Double first, Double second, Double third)
	{
		Double gapFinal = 0.0;
		
		gapFinal = (((1 / first) * 0.9984) * ((1 / second) * 0.9984) * ((third) * 0.9984)) - 1;
		
		return gapFinal;
	}
	
	private String removeUnnecessary(String currentString)
	{
		String punctuation = ",'?!:;\"(){}^[]<>";
		
		String scrubbedString = "";
		for(int i = 0; i < currentString.length(); i++)
		{
			if(currentString.charAt(i) == ':' || currentString.charAt(i) == ',')
			{
				scrubbedString += " ";
			}
			else if(punctuation.indexOf(currentString.charAt(i)) == -1)
			{
				scrubbedString += currentString.charAt(i);
			}
		}
		//System.out.println(scrubbedString);
		
		String finalScrub = "";
		Scanner stringScan = new Scanner(scrubbedString);
		int pairCount = 0;
		while(stringScan.hasNext())
		{
			if(pairCount % 19 == 0)
			{
				finalScrub += stringScan.next() + " ";
			}
			if(stringScan.next().equals("last"))
			{
				finalScrub += stringScan.nextDouble() + " ";
			}
			pairCount++;
		}
		stringScan.close();
		
		//System.out.println(finalScrub);
		
		return finalScrub;
	}
}
