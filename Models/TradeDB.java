package Models;
import Controllers.ITradeResult;
import Controllers.TradeResult;

import java.util.LinkedList;

/**
 * @author Colton Undseth
 * @author Bella Dong
 * @author Joey Gendy
 * @author Scott Guo
 * 
 * A database of TradeResult objects implemented using a Linked List
 */
public class TradeDB implements ITradeDB {
	private static ITradeDB instance = null;
	private LinkedList<ITradeResult> tradeList;
	
	/**
	 * Initializes the linked list of trades
	 */
	private TradeDB() {
		//the tradeDB is a linked list of TradeResult objects
		tradeList = new LinkedList<ITradeResult>();
	}
	
	/**
	 * @return instance returns a static instance of the TradeDB
	 */
	public static ITradeDB getInstance() {
		//use singleton design pattern for trade DB
		if (instance == null) {
			instance = new TradeDB();
		}
		
		return instance;
	}
	
	/**
	 * @param result adds a TradeResult object to the linked list
	 * performs appropriate operations depending on which button was pressed
	 */
	@Override
	public void addTrade(ITradeResult result) {
		//adds a trade to the database
		tradeList.add(result);
	}
	
	/**
	 * @return tradeList returns the linked list of trades
	 */
	@Override
	public LinkedList<ITradeResult> getTradeList(){
		return tradeList;
	}
}
