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
 * A specific implementation of ITradingStrategy
 * implemented using the strategy design pattern
 * if volume of ETH is more than 1 and price of ADA is less than 2 then buy 1000 ADA
 */
public class TradingStrategyD implements ITradingStrategy {
	private ITradeResult result;
	
	/**
	 * @param broker the TradingBroker who called the strategy
	 * ensures all of the coins required to execute the strategy are in the broker's coin list and then generates the appropriate TradeResult object and adds it to the TradeDB
	 */
	@Override
	public void executeStrategy(ITradingBroker broker) {
		
		String date = CoinDataFetcher.getDate();
		
		ICryptoCoin[] coinList = broker.getCoinList();
		ICryptoCoin cardano = null, ethereuum = null;

		//loop through the broker's coin list
		for (int i = 0; i < coinList.length; i++) {
			//if cardano is in the coin list, initialize cardano
			if (coinList[i].getName().equals("cardano")) {
				cardano = coinList[i];
			}
			//if ethereum is in the coin list, initialize ethereum
			else if (coinList[i].getName().equals("ethereum")) {
				ethereuum = coinList[i];
			}
		}
		//if either cardano or ethereum is null, then one of them was not in the coin list and the strategy cannot be executed
		if(cardano == null || ethereuum == null) {
			//create failed TradeResult object
			result = new TradeResult(broker, new TradingStrategyD(), null, "Fail", null,null, date); 
			//add TradeResult to TradeDB
			TradeDB.getInstance().addTrade(result);
			//show error message
			JOptionPane.showMessageDialog(null, "ETH and ADA muct be in the coin list to execute Strategy D");
			return;
		}
		//all coins required for strategy are in the coin list
		else {
			//if conditions for strategy are met
			if (ethereuum.getVolume() > 4.75 && cardano.getPrice() < 2) {
				//create appropriate TradeResult object
				result = new TradeResult(broker, new TradingStrategyD(), "ADA", "Buy", 1000.00, cardano.getPrice(), date); 
				//add object to TradeDB
				TradeDB.getInstance().addTrade(result);
				return;
			}
			
		}
	}
	
	/**
	 * @return String a string representation of the strategy's name
	 */
	public String toString() {
		//toString() method to return the strategy's name
		return "Strategy D";
	}
}
