
public class Customer {

	private int partySize;
	private double entryTime;
	private double departureTime;
	private double drinkTab;
	private double foodTab;
	private double grossTotal;
	private double taxedTotal;
	private double tipAmount;
	private double tipPercentage;
	
	public Customer(int partySize, double entryTime, double departureTime, double drinkTab, double foodTab, double tipAmount)
	{
		this.partySize = partySize;
		this.entryTime = (Math.round(entryTime * 1.06625*100))/100.0;
		this.departureTime = (Math.round(departureTime * 1.06625*100))/100.0;
		this.drinkTab = drinkTab;
		this.foodTab = foodTab;
		this.tipAmount = tipAmount;
		grossTotal = (Math.round((drinkTab + foodTab) * 1.06625*100))/100.0;	//sums drinkTab and foodTab for grossTotal
		taxedTotal = (Math.round(grossTotal * 1.06625*100))/100.0;	//adds 6.625% sales tax for New Jersey
	}

	// Calculate tip percentage
	public String calcTipPercentage() {
		tipPercentage = tipAmount / taxedTotal * 100;
		tipPercentage = (Math.round(tipPercentage*100))/100.0;
		return tipPercentage + "%";
	}
	
	// Get the party size
	public int getPartySize() {
		return partySize;
	}
	
	// Get the entry time
	public double getEntryTime() {
		return entryTime;
	}
	
	// Get the departure time
	public double getDepartureTime() {
		return departureTime;
	}
	
	// Get the drink time
	public double getDrinkTab() {
		return drinkTab;
	}
	
	// Get the food tab
	public double getFoodTab() {
		return foodTab;
	}
	
	// Get the gross total
	public double getGrossTotal() {
		return grossTotal;
	}
	
	// Get the 
	public double getTaxedTotal() {
		return taxedTotal;
	}
	
	// Get the 
	public double getTipAmount() {
		return tipAmount;
	}
	
	public String toString()
	{
		return "Party Size: " + partySize + "\t Entry Time: " + entryTime + "\tDeparture Time: " + departureTime + "\t\tFood Tab: " + foodTab + "\t  Drink Tab: "
						+ drinkTab + "\tGross Total: " + grossTotal + "\tTaxed Total: " + taxedTotal + "\t Tip Amount: " + tipAmount + "\tTip %: " + calcTipPercentage();
	}
	
}