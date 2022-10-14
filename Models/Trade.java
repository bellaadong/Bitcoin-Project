package Models;

import java.util.*;

import javax.swing.*;

import Controllers.CoinDataFetcher;
import Controllers.CryptoCoin;
import Controllers.ICoinDataFetcher;
import Controllers.ICryptoCoin;
import Controllers.ITradingBroker;
import Controllers.ITradingStrategyCreator;
import Controllers.TradingBroker;
import Controllers.ITradingStrategy;
import Controllers.TradingStrategyCreator;
import Viewers.MainUI;

/**
 * @author Colton Undseth
 * @author Bella Dong
 * @author Joey Gendy
 * @author Scott Guo
 * 
 * when perform trade button is pressed, the TradingBrokers are created, the coin data is fetched, and each Broker's trading strategy is executed
 */
public class Trade {	
	//a list of TradingBrokers created from the table of brokers
	private static LinkedList<ITradingBroker> brokers;
	//a set of coins that need to be fetched from CoinGecko
	private static HashSet<String> coinsToFetch;
	
	/**
	 * uses the facade design pattern to execute a trade.
	 * the TradingBrokers are created, a list of coins to fetch is created, the coin data is fetched, and each Broker's trading strategy is executed
	 */
	public static void PerformTrade() {
		//creates a list of TradingBroker objects will all attributes set
		brokers = generateBrokersFromTable();
		
		//if brokers is null, then one of the fields in the table was left blank and the method should not proceed
		if (brokers == null) {
			return;
		}
		
		//a set of coins that need to be fetched is created
		coinsToFetch = getListOfCoinsToFetch();
		
		//using the coinsToFetch, the CoinDataFetcher gets all of the data from CoinGecko and adds it to the CoinDB
		fetchCoinData();
		
		//once the coinDB is updated, all of the broker's strategies are executed
		performAllBrokerStrategies();
	}
	
	/**
	 * generates a set of coin names based on all the broker's coin lists
	 * @return coinsToFetch a set of coin names that need to be fetched
	 */
	private static HashSet<String> getListOfCoinsToFetch(){
		HashSet<String> coinsToFetch = new HashSet<String>();
		
		//loops through all of the brokers and adds their coin names to the set
		//since we used a set, coin names will only be added once even if  more than one broker have the same coin in their list
		for (int i = 0; i < brokers.size(); i++) {
			for (ICryptoCoin coin : brokers.get(i).getCoinList()) {
				coinsToFetch.add(coin.getName());
				
			}
		}
		
		return coinsToFetch;
	}
	
	/**
	 * uses the CoinDataFetcher and fetches all of the coin data for every coin in the CoinsToFetch set and then adds the info to the coin DB
	 */
	private static void fetchCoinData() {
		//initialize the API to connect to CoinGecko
		ICoinDataFetcher fetcher = new CoinDataFetcher();
		
		String todaysDate = CoinDataFetcher.getDate();
		
		// Fetch data for each coin in set
		for (String coinName : coinsToFetch) {
			Double price = fetcher.getPriceForCoin(coinName, todaysDate);
			Double volume = fetcher.getVolumeForCoin(coinName, todaysDate);
			Double mktCap = fetcher.getMarketCapForCoin(coinName, todaysDate);
			CoinDB.getInstance().addCoin(coinName, price, volume, mktCap);
		}
		
	}
	
	/**
	 * loops through the list of brokers and sets the coin attributes in their coin lists and then executes their strategy
	 */
	private static void performAllBrokerStrategies() {
		// for each broker, get coin information from CoinDB and put into that broker's coinList
		for (ITradingBroker broker : brokers) {
			ICryptoCoin[] coinList = broker.getCoinList();
			for (ICryptoCoin coin : coinList) {
				coin.setPrice(CoinDB.getInstance().get(coin.getName(), "price"));
				coin.setVolume(CoinDB.getInstance().get(coin.getName(), "volume"));
				coin.setMarketCap(CoinDB.getInstance().get(coin.getName(), "mktCap"));
			}
			//executes the specific trading strategy for the broker
			broker.executeStrategy();
		}
	}
	
	/**
	 * generates TradingBroker objects from the table that was filled in by the user and then adds it to a Linked List 
	 * @return brokers returns a linked list of TradingBroker objects
	 */
	private static LinkedList<ITradingBroker> generateBrokersFromTable() {
		//get the table of brokers and the main window of the main UI 
		JTable brokerTable = MainUI.getInstance().getBrokerTable();
		//add all the names to a list and if any of the names already exist in the list then show an error window
		// list of names of brokers
		LinkedList<String> brokerNames = new LinkedList<String>();
		// list of brokerInfo
		LinkedList<Hashtable<String, String>> brokerInfoList = new LinkedList<Hashtable<String, String>>();
		
		for (int i = 0; i < brokerTable.getRowCount(); i++) {
			
			// get broker's name
			String name = (String) brokerTable.getValueAt(i, 0);
			//if field is blank, show error message
			if (name == null || name.equals("")) {
				JOptionPane.showMessageDialog(null, "Please fill in the Trader Name in row " + (i+1));
				return null;
			}
			
			//get broker's coin list
			String coinList = (String) brokerTable.getValueAt(i, 1);
			//if field is blank, show error message
			if (coinList == null || coinList.equals("")) {
				JOptionPane.showMessageDialog(null, "Please fill in the Coin List in row " + (i+1));
				return null;
			}
			
			//get broker's trading strategy
			String tradingStrategy = (String) brokerTable.getValueAt(i, 2);
			//if field is blank, show error message
			if (tradingStrategy == null || tradingStrategy.equals("")) {
				JOptionPane.showMessageDialog(null, "Please fill in the Trading Strategy in row " + (i+1));
				return null;
			}
			
			// give error if broker with that name already exists
			if (!brokerNames.contains(name)) {
				brokerNames.add(name);
			}
			else {
				JOptionPane.showMessageDialog(null, "More than one broker cannot have the same name");
				return null;
			}
			
			// create dictionary holding all broker's info
			Hashtable<String, String> brokerInfo = new Hashtable<String, String>();
			brokerInfo.put("name", name);
			brokerInfo.put("coinList", coinList);
			brokerInfo.put("strategy", tradingStrategy);
			
			brokerInfoList.add(brokerInfo);
			
		}
		
		// create strategy creator to be used for each broker
		ITradingStrategyCreator strategyCreator = new TradingStrategyCreator();
		// list of TradingBroker objects
		LinkedList<ITradingBroker> brokers = new LinkedList<ITradingBroker>();
		
		// create TradingBroker object for each broker row in table
		for (int i = 0; i < brokerInfoList.size(); i++) {
			
			Hashtable<String, String> brokerInfo = brokerInfoList.get(i);
			
			// create array of CryptoCoins from coinList
			ICryptoCoin[] coinList = CryptoCoin.createCoinList(brokerInfo.get("coinList")); 
			
			// create strategy object
			ITradingStrategy strategy = strategyCreator.factoryMethod(brokerInfo.get("strategy"));
			// ^ This implements Factory method
			
			// create TradingBroker
			TradingBroker broker = new TradingBroker(brokerInfo.get("name"), coinList, strategy);
			// ^ Didn't feel a need to use factory for this one 
			
			brokers.add(broker);
		}
		return brokers;
	}
	
}
