package Controllers.TradingStrategies;

import Controllers.ITradingStrategy;
import Models.TradeDB;
import java.text.DecimalFormat;
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
 * if price of XRP is greater than 1 and mkt cap of bitcoin is more than 2.5 buy $100,000 worth of bitcoin
 */
public class TradingStrategyC implements ITradingStrategy {
	private ITradeResult result;
	
	/**
	 * @param broker the TradingBroker who called the strategy
	 * ensures all of the coins required to execute the strategy are in the broker's coin list and then generates the appropriate TradeResult object and adds it to the TradeDB
	 */
	@Override
	public void executeStrategy(ITradingBroker broker) {
		
		String date = CoinDataFetcher.getDate();
		
		ICryptoCoin[] coinList = broker.getCoinList();
		ICryptoCoin ripple = null, bitcoin = null;
		
		//loop through the broker's coin list
		for (int i = 0; i < coinList.length; i++) {
			//if ripple is in the coin list, then initialize ripple
			if (coinList[i].getName().equals("ripple")) {
				ripple = coinList[i];
			}
			//if bitcoin is in the coin list, initialize bitcoin
			else if (coinList[i].getName().equals("bitcoin")) {
				bitcoin = coinList[i];
			}
		}
		
		//if either bitcoin or ripple is null, then one of them was not in the coin list and the strategy cannot be executed
		if(bitcoin == null || ripple == null) {
			//create a failed TradeResult object
			result = new TradeResult(broker, new TradingStrategyC(), null, "Fail", 0.00, 0.00, date); 
			//add TradeResult to TradeDB
			TradeDB.getInstance().addTrade(result);
			//show error message
			JOptionPane.showMessageDialog(null, "BTC and XRP must be in the coin list to execute Strategy C");
			return;
		}
		//all coins required for strategy are in the coin list
		else {
			//if conditions for the strategy are met
			if (ripple.getPrice() > 1 && bitcoin.getMarketCap() > 2.5) {
				//figure out how many bitcoins can be purchased with $100,000
				Double numBitcoins = 100000/bitcoin.getPrice();
				//limit the number of decimal places to 2 using DecimalFormat
				DecimalFormat df = new DecimalFormat("#.##");
				numBitcoins = Double.valueOf(df.format(numBitcoins));
				
				//create appropriate TradeResult object
				result = new TradeResult(broker, new TradingStrategyC(), "BTC", "Buy", numBitcoins, bitcoin.getPrice(), date); 
				//add TradeResult to TradeDB
				TradeDB.getInstance().addTrade(result);
				return;
			}
			
		}
	}
	
	/**
	 * @return String a string representation of the strategy's name
	 */
	public String toString() {
		return "Strategy C";
	}
}
