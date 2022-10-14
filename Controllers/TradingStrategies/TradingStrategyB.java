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
 * if price of BTC is less than 59000 then sell 2 BTC
 */
public class TradingStrategyB implements ITradingStrategy {
	private ITradeResult result;
	
	/**
	 * @param broker the TradingBroker who called the strategy
	 * ensures all of the coins required to execute the strategy are in the broker's coin list and then generates the appropriate TradeResult object and adds it to the TradeDB
	 */
	@Override
	public void executeStrategy(ITradingBroker broker) {
		
		String date = CoinDataFetcher.getDate();
		
		ICryptoCoin[] coinList = broker.getCoinList();
		ICryptoCoin bitcoin = null;
		
		//loop through broker's coin list and initialize bitcoin if it exists in the coin list
		for (int i = 0; i < coinList.length; i++) {
			if (coinList[i].getName().equals("bitcoin")) {
				bitcoin = coinList[i];
			}
		}
		
		//if bitcoin is null then it is not in the coin list and the strategy will not work
		if(bitcoin == null) {
			//generate a failed IITradeResult object
			result = new TradeResult(broker, broker.getStrategy(), null, "Fail", null, null, date); 
			//add the ITradeResult to TradeDB
			TradeDB.getInstance().addTrade(result);
			//display error message
			JOptionPane.showMessageDialog(null, "BTC must be in the coin list to execute Strategy B");
			return;
		}
		//all coins required for strategy are in the coin list
		else {
			//if condition for strategy is met
			if (bitcoin.getPrice() < 59000) {
				//create appropriate trade result object
				result = new TradeResult(broker, broker.getStrategy(), "BTC", "Sell", 2.00, bitcoin.getPrice(), date); 
				//add ITradeResult to the TradeDB
				TradeDB.getInstance().addTrade(result);
				return;
			}
			
		}
	}
	
	/**
	 * @return String a string representation of the strategy's name
	 */
	public String toString() {
		return "Strategy B";
	}
}
