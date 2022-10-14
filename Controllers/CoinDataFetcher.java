package Controllers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * 
 * @author Colton Undseth
 * @author Bella Dong
 * @author Joey Gendy
 * @author Scott Guo
 * 
 * contains the operations used to fetch the coin data from CoinGecko API
 */
public class CoinDataFetcher implements ICoinDataFetcher {

	/**
	 * 
	 * @param id the coin's name 
	 * @param date the "mm-dd-yyy" string representtion of todays date
	 * @return jsonObject returns the json object that contains all of the info about a particular coin
	 */
	private JsonObject getDataForCrypto(String id, String date) {

		String urlString = String.format(
				"https://api.coingecko.com/api/v3/coins/%s/history?date=%s", id, date);
		
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
			if (responsecode == 200) {
				String inline = "";
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) {
					inline += sc.nextLine();
				}
				sc.close();
				JsonObject jsonObject = new JsonParser().parse(inline).getAsJsonObject();
				return jsonObject;
			}

		} catch (IOException e) {
			System.out.println("Something went wrong with the API call.");
		}
		return null;
	}
	
	/**
	 * @param id the coin's name 
	 * @param date the "mm-dd-yyy" string representtion of todays date
	 * @return price the price of the coin from the json object
	 */
	@Override
	public double getPriceForCoin(String id, String date) {
		double price = 0.0;
		
		JsonObject jsonObject = getDataForCrypto(id, date);
		if (jsonObject != null) {
			JsonObject marketData = jsonObject.get("market_data").getAsJsonObject();
			JsonObject currentPrice = marketData.get("current_price").getAsJsonObject();
			price = currentPrice.get("cad").getAsDouble();
		}
		
		return price;
	}
	
	/**
	 * @param id the coin's name 
	 * @param date the "mm-dd-yyy" string representtion of todays date
	 * @return marketCap the market cap of the coin from the json object
	 */
	@Override
	public double getMarketCapForCoin(String id, String date) {
		double marketCap = 0.0;
		
		JsonObject jsonObject = getDataForCrypto(id, date);
		if (jsonObject != null) {
			JsonObject marketData = jsonObject.get("market_data").getAsJsonObject();
			JsonObject currentPrice = marketData.get("market_cap").getAsJsonObject();
			marketCap = currentPrice.get("cad").getAsDouble();
		}
		
		return marketCap;
	}
	
	/**
	 * @param id the coin's name 
	 * @param date the "mm-dd-yyy" string representtion of todays date
	 * @return volume the volume of the coin from the json object
	 */
	@Override
	public double getVolumeForCoin(String id, String date) {
		double volume = 0.0;
		
		JsonObject jsonObject = getDataForCrypto(id, date);
		if (jsonObject != null) {
			JsonObject marketData = jsonObject.get("market_data").getAsJsonObject();
			JsonObject currentPrice = marketData.get("total_volume").getAsJsonObject();
			volume = currentPrice.get("cad").getAsDouble();
		}
			
			return volume;
		}
	
    
	/**
	 * 
	 * @return todaysDate returns a string form of todays date in the format: "dd-mm-yyyy"
	 */
	public static String getDate() {
		//creates a string form of todays date in the format: "dd-mm-yyyy"
		Date date = new Date();
		
		//create string for the day of the month
		String day = "" + date.getDate();
		day = day.length() == 1 ? "0" + day : day;
		
		//create string for the month of the year
		String month = "" + (date.getMonth() + 1);
		month = month.length() == 1 ? "0" + month : month;
		
		//create string for the year
		String year = "" + (date.getYear() + 1900);
		
		//create the string for the whole date in the format expected in the URL
		String todaysDate = day + "-" + month + "-" + year;
		
		return todaysDate;
	}
}
