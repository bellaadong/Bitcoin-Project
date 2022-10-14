package Models;

import java.util.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import Controllers.CryptoCoin;

/**
 * @author Colton Undseth
 * @author Bella Dong
 * @author Joey Gendy
 * @author Scott Guo
 * 
 * A database of JsonObjects that stores all of the CryptoCoin information after the CoinDataFetcher gets it from CoinGecko
 */
public class CoinDB implements ICoinDB {
	//instance of CoinDB for singleton pattern
	private static ICoinDB instance;
	
	//info for each coin will be stored in a JsonObject and all coins will be stored in a linked list
	private LinkedList<JsonObject> coinInfo;
	
	//private constructor initializes linked list of json objects
	private CoinDB() {
		coinInfo = new LinkedList<JsonObject>();
	}
		
	
	/**
	 * @return instance an static instance of the coin DB
	 */
	public static ICoinDB getInstance() {
	//implemented using the singleton pattern to limit the number of times the constructor can be called to 1	
		if (instance == null) {
			instance = new CoinDB();
		}
		
		return instance;
	}
	
	 /**
	  * @param name the name of the coins
	  * @param price the price of the coin
	  * @param volume the volume of the coin
	  * @param mktCap the market cap of the coin
	  * the addCoin method creates the JsonObject and adds all the coin attributes to it and add it to the DB
	  */
	@Override
	public void addCoin(String name, Double price, Double volume, Double mktCap) {
		//adds a coin to the linked list by creating a JsonObject and storing it in the linked list
		JsonObject obj = new JsonObject();
		obj.addProperty("name", name);
		obj.addProperty("price", price);
		obj.addProperty("volume", volume);
		obj.addProperty("mktCap", mktCap);
		
		coinInfo.add(obj);
	}
	
	/**
	 * @param name the name of the coin
	 * @param property the particular attribute that you want to be returned
	 * @return double the attribute that you want returned
	 * searches the linked list of JsonObjects for a coin by name and when found, returns the property of the coin entered. if the coin is not found by name, -1 us returned
	 */
	@Override
	public Double get(String name, String property) {
		//searches the linked list of JsonObjects for a coin by name and when found, returns the property of the coin entered
		//if the coin is not found by name, -1 us returned
		//loop through all coins in the list
		for (JsonObject coin : coinInfo) {
			//if the coin is found by name
			if (coin.get("name").getAsString().equals(name)) {
				//use a switch to return the property entered
				switch (property) {
				case "price":
					return coin.get("price").getAsDouble();
				case "volume":
					return coin.get("volume").getAsDouble();
				case "mktCap":
					return coin.get("mktCap").getAsDouble();
				}
			}
		}
		return -1.0;
	}
}
