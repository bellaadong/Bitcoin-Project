package Controllers.TradingStrategies;

import Controllers.ITradingStrategy;
import Models.TradeDB;
import javax.swing.JOptionPane;

import Controllers.CoinDataFetcher;
import Controllers.ICryptoCoin;
import Controllers.ITradeResult;
import Controllers.ITradingBroker;
import Controllers.TradeResult;

/**
 * @author Colton Undseth
 * @author Bella Dong
 * @author Joey Gendy
 * @author Scott Guo
 * 
 * A specific implementation of ITradingStrategy.
 * implemented using the strategy design pattern
 * if price of ETH is greater than 4200 and mkt cap of ETH is more than 1.5 buy 20 ETH
 */
public class TradingStrategyA implements ITradingStrategy {
	private ITradeResult result;
	
	/**
	 * @param broker the TradingBroker who called the strategy
	 * ensures all of the coins required to execute the strategy are in the broker's coin list and then generates the appropriate TradeResult object and adds it to the TradeDB
	 */
	@Override
	public void executeStrategy(ITradingBroker broker) {
		
		String date = CoinDataFetcher.getDate();
		
		ICryptoCoin[] coinList = broker.getCoinList();
		ICryptoCoin ethereum = null;
		
		//ensure all of the coins required for the strategy are in the broker's coin list
		for (int i = 0; i < coinList.length; i++) {
			if (coinList[i].getName().equals("ethereum")) {
				ethereum = coinList[i];
			}
		}
		
		
		//if ethereum is null, then it was not found in the broker's coin list and the trader does not have all the information needed to use the strategy
		if(ethereum == null) {
			//create a failed TradeResult object and add it to the TradeDB
			result = new TradeResult(broker, broker.getStrategy(), null, "Fail", null, null, date); 
			TradeDB.getInstance().addTrade(result);
			
			//display an error message to the user
			JOptionPane.showMessageDialog(null, "BTC and ETH must be in the coin list to execute Strategy A");
			return;
		}
		//if the broker has all required info for strategy
		else {
			//if conditions of strategy are met
			if (ethereum.getPrice() > 4200 && ethereum.getMarketCap() > 1.5) {
				//create appropriate TradeResut object and add it to DB
				result = new TradeResult(broker, broker.getStrategy(), "ETH", "Buy", 20.00, ethereum.getPrice(), date); 
				TradeDB.getInstance().addTrade(result);
			}
			
		}
	}
	
	/**
	 * @return String a string representation of the strategy's name
	 */
	public String toString() {
		return "Strategy A";
	}
}
