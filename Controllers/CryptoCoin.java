package Controllers;

import java.util.HashMap;

/**
 * @author Colton Undseth
 * @author Bella Dong
 * @author Joey Gendy
 * @author Scott Guo
 * 
 * represents a CryptoCoin with the attributes name, price, volume, and market cap
 */
public class CryptoCoin implements ICryptoCoin {
	//all attributes of a coin
	private String name;
	private Double price = 0.00;
	private Double volume = 0.00;
	private Double mktCap = 0.00;
	
	//stores pairs of coin ticker names and their coinGecko ids
	private static HashMap<String, String> tickerIDPairs;
	
	/**
	 * constructor sets the name of the coin
	 * @param name the name of the coin
	 */
	public CryptoCoin(String name) {
		this.name = name;
	}
	
	/**
	 * creates an instance of the HashMap that associates common CryptoCurrency ticker names and their id name
	 * @return tickerIDPairs the static instance of the tickerIDPairs HashMap 
	 */
	private static HashMap<String, String> createInstance(){
		//singleton design pattern for the tickerIDPAirs
		if (tickerIDPairs == null) {
			//create hash map
			tickerIDPairs = new HashMap<String, String>();
			
			//add common coin ticker names and their pairs to the hash map
			tickerIDPairs.put("BTC", "bitcoin");
			tickerIDPairs.put("ETH", "ethereum");
			tickerIDPairs.put("XRP", "ripple");
			tickerIDPairs.put("BCH", "bitcoin-cash");
			tickerIDPairs.put("ADA", "cardano");
			tickerIDPairs.put("LTC", "litecoin");
			tickerIDPairs.put("XEM", "nem");
			tickerIDPairs.put("XLM", "stellar");
			tickerIDPairs.put("EOS", "eos");
			tickerIDPairs.put("DOGE", "doegcoin");
		}
		return tickerIDPairs;
	}
	
	// getters and setters for all  coin attributes
	
	/**
	 * @return price returns the price of the coin
	 */
	@Override
	public Double getPrice() {
		return price;
	}
	
	/**
	 * @param price the price of a coin to set
	 */
	@Override
	public void setPrice(Double price) {
		this.price = price;
	}
	
	/**
	 * @return name returns the name of a coin
	 */
	@Override
	public String getName() {
		return name;
	}
	
	/**
	 * converts the name entered from a ticker name to its ID name
	 * if the name entered is already the ID name, then this name will be set
	 * @param name the name to set for the new coin
	 */
	@Override
	public void setName(String name) {
		//if the name has an ID pair in the tickerToIDPairs hash map, then convert the name to the id before setting name
		createInstance();
		name = tickerToID(name);
		this.name = name;
	}
	/**
	 * @return volume returns the volume that a coin has traded in a day
	 */
	@Override
	public Double getVolume() {
		return volume;
	}
	
	/**
	 * @param volume the volume that a coin has traded in a day
	 */
	@Override
	public void setVolume(Double volume) {
		this.volume = volume;
	}
	
	/**
	 * @return mktCap returns the market cap of a coin
	 */
	@Override
	public Double getMarketCap() {
		return mktCap;
	}
	
	/**
	 * @param mktCap the market cap to be set for a coin
	 */
	@Override
	public void setMarketCap(Double mktCap) {
		this.mktCap = mktCap;
	}
	
	/**
	 * @param other another crypto coin to be compared with
	 * @return boolean true if the coins have the same name, false otherwise
	 */
	@Override
	public boolean equals(ICryptoCoin other) {
		// 2 coins are equal if their names are the same
		return (this.getName().equals(other.getName()));
	}
	
	/**
	 * 
	 * @param ticker the ticker name of a coin (ex. BTC = bitcoin)
	 * @return the ID name of the coin that is associated with the ticker name or the ticker name if not pair is stored in the tickerIDPairs HashMap
	 */
	private static String tickerToID(String ticker) {
		//converts the ticker name entered into the equivalent ID name to be recognized on coinGecko API
		//creates an instance of the ticker name to ID pairs
		createInstance();
		String name = tickerIDPairs.get(ticker);
		
		//if their was an ID paired with the ticker name, then return it
		if (name != null) {
			return name;
		}
		//if no ID was paired with the ticker name, return the ticker name
		return ticker;
	}
	
	/**
	 * 
	 * @param coinListString the coin list in string form separated by commas that is entered by the user in the broker table
	 * @return an array of coin ID names that is split on commas and has had whitespace removed 
	 */
	public static ICryptoCoin[] createCoinList(String coinListString) {
		// take string, split by commas
		String[] splitCoinList = coinListString.split(",");
		// create CryptoCoin array that is length of splitCoinList
		int numCoins = splitCoinList.length;
		ICryptoCoin[] coinList = new ICryptoCoin[numCoins];
		// create CryptoCoin object for each coin. Only name for now, price will be checked when performing trade.
		for (int i = 0; i < splitCoinList.length; i++) {
			// removes whitespace from coin name
			String tickerName = splitCoinList[i].trim();
			// converts the coin name from ticker name to its ID
			String coinName = tickerToID(tickerName.toUpperCase());
			// create the coin
			ICryptoCoin coin = new CryptoCoin(coinName.toLowerCase());
			// add to array
			coinList[i] = coin;
		}
		// return array
		return coinList;
	}
	

	
}
