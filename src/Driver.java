import java.io.BufferedReader;
import java.util.Random;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
public class Driver {

	public static void main(String[] args) throws IOException
	{
		BufferedReader stdin = new BufferedReader (new InputStreamReader(System.in));
		ArrayList<Customer> bar = new ArrayList<Customer>();
		
		System.out.println("Enter how many parties you want to simulate.\n");
		int numberOfParties = Integer.parseInt(stdin.readLine());
		int tracker = 0, barGuest = 0, customerCount = 0;
		double perCustomer, shiftTipPercentage, tipsMade = 0.0, shiftRevenue = 0.0;
		
		for(int i = 0; i < numberOfParties; i++)
		{
			double entryTime = (Math.round(randomEntryTime()*100))/100.0;
			double stayDuration = (Math.round(randomStayDuration()*10))/10.0;
			double drinkTab = (Math.round(randomDrinkTab()*100))/100.0;
			double foodTab = (Math.round(randomFoodTab()*100))/100.0;
			double nextCustomer = (Math.round(nextCustomer()*100))/100.0;
			double tipAmount = (Math.round(randomTip(drinkTab,foodTab)*100))/100.0;
			int partySize = randomPartySize();
			
			// First Customer, bar empty
			if(i == 0)
			{
				barGuest = barGuest + partySize;
				bar.add(new Customer(partySize, entryTime, entryTime + stayDuration,drinkTab,foodTab, tipAmount)); 
			}
			// Bar is full and someone is waiting for seats to open
			else if (barGuest > 20 - partySize )
			{
				barGuest = barGuest - bar.get(tracker).getPartySize();
				tracker++;
				if(barGuest > 20 - partySize)
				{
					barGuest = barGuest - bar.get(tracker).getPartySize();
					tracker++;
				}
				bar.add(new Customer(partySize, bar.get(tracker).getDepartureTime() + nextCustomer, bar.get(i-1).getEntryTime() + nextCustomer + stayDuration, drinkTab,foodTab,tipAmount));
				barGuest = barGuest + partySize;		
			}
			// Not first customer and bar is not full
			else 
			{
				if (entryTime > bar.get(tracker).getDepartureTime())
				{
					barGuest = barGuest - bar.get(tracker).getPartySize();
					tracker++;
				}
				barGuest = barGuest + partySize;
				bar.add(new Customer(partySize, bar.get(i-1).getEntryTime() + nextCustomer, bar.get(i-1).getEntryTime() + nextCustomer + stayDuration, drinkTab,foodTab,tipAmount));
			}			
		}
		// Shift report
		for(int i = 0; i < bar.size(); i++)
		{
			customerCount =  customerCount + bar.get(i).getPartySize();
			shiftRevenue = shiftRevenue + bar.get(i).getTaxedTotal();
			shiftRevenue = (Math.round(shiftRevenue*100))/100.0;
			tipsMade = tipsMade + bar.get(i).getTipAmount();
			tipsMade = (Math.round(tipsMade*100))/100.0;
			System.out.println(bar.get(i).toString());
			// End of shift report
			if(i == (bar.size() - 1))
				{
					shiftTipPercentage = tipsMade / shiftRevenue;
					shiftTipPercentage = ((Math.round(shiftTipPercentage*10000))/100.0 );
					perCustomer = shiftRevenue / customerCount;
					perCustomer = (Math.round(perCustomer*100))/100.0;
					shiftRevenue = (Math.round(shiftRevenue*100))/100.0;
					System.out.println("\nShift Report");
					System.out.println("Revenue: " + shiftRevenue + " Total Customers: " + customerCount + " $/Customer: " + perCustomer + " Tips Made: " + tipsMade + " Tip Percentage: " + shiftTipPercentage + "%");
				}
		}
	}
	
	// generate a random party size
	private static int randomPartySize()
	{
		int min = 1, max = 5; 	
		return new Random().nextInt((max - min)) + min;
	}
	
	// generate a random entry time
	private static double randomEntryTime()
	{
		double min = 0.0, max = .75; 	// decimals represent amount of hours - .01 of an hour is 40 seconds 
		double ran = new Random().nextDouble() * (max - min) + min;
		ran = (Math.round(ran*100))/100.0;
        return ran;
	}
	
	// generate random stay duration
	private static double randomStayDuration()
	{
		double min = 0.5, max = 2.0;	// decimals represent amount of hours - .01 of an hour is 40 seconds 
		double ran = new Random().nextDouble() * (max - min) + min;
		ran = (Math.round(ran*100))/100.0;
        return ran;
	}
	
	// generate time until next customer enters
	private static double nextCustomer()
	{
		double min = 0.0, max =.5;		// decimals represent amount of hours - .01 of an hour is 40 seconds 
		double ran = new Random().nextDouble() * (max - min) + min;
		ran = (Math.round(ran*100))/100.0;
        return ran;
	}
	
	// generate random drink tab
	private static double randomDrinkTab()
	{
		int noDrinks = new Random().nextInt(100);
		// chance of no drink cost
		if(noDrinks % 20 == 0)
		{
			return 0.00;
		}
		double min = 7.00, max = 94.00;	
		double ran = new Random().nextDouble() * (max - min) + min;
		ran = (Math.round(ran*100))/100.0;
        return ran;
	}
	
	private static double randomFoodTab()
	{
		int noFood = new Random().nextInt(10);
		// chance of no food cost
		if(noFood % 10 == 0)
		{
			return 0.00;
		}
		double min = 12.00, max = 54.00;
		double ran = new Random().nextDouble() * (max - min) + min;
		ran = (Math.round(ran*100))/100.0;
        return ran;
	}
	
	// generate random tip percentage
	private static double randomTip(double drinkTab, double foodTab)
	{
		int probOfOutlier = new Random().nextInt(100);
		double ran;
		// chance of outside expected tip percentage
		if(probOfOutlier % 8 == 0)
		{
			// chance of below average tip
			if(probOfOutlier % 3 == 0) 
			{
				double min = 0.00, max =.15;
				ran = new Random().nextDouble() * (max - min) + min;
				ran = (Math.round(ran*100))/100.0;
			}
			// chance of above average tip
			else
			{
				double min = 0.45, max =.90;
				ran = new Random().nextDouble() * (max - min) + min;
				ran = (Math.round(ran*100))/100.0;
			}
		}
		// returns average tip range
		else 
		{
			double min = 0.15, max =.45;
			ran = new Random().nextDouble() * (max - min) + min;
			ran = (Math.round(ran*100))/100.0;
		}
        return (ran * ((drinkTab + foodTab) * 1.06625));
	}
}