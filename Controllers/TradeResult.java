package Controllers;

/**
 * @author Colton Undseth
 * @author Bella Dong
 * @author Joey Gendy
 * @author Scott Guo
 * TradeResult objects are represent a completed trade and contain all of the attributes necessary to be shown in the table and in the histogram
 */
public class TradeResult implements ITradeResult {
	//all attributes of a TradeResult object
	private ITradingBroker broker;
	private ITradingStrategy strategy;
	private String coin;
	private String buyOrSell;
	private Double quantity;
	private Double price;
	private String date;
	
	/**
	 * creates a TradeResult object and sets all of its attributes
	 * @param broker the TradingBroker who submitted the trade
	 * @param strategy the strategy used to complete the trade
	 * @param coin the name of the coin in the action
	 * @param buyOrSell the action completed in the trade
	 * @param quantity the amount of crypto coins bought/sold
	 * @param price the price each coin was bought/sold at
	 * @param date the "mm-dd-yyy" string representation of the date
	 */
	public TradeResult(ITradingBroker broker, ITradingStrategy strategy, String coin, String buyOrSell, Double quantity, Double price, String date) {
		//creates a TradeReult object and sets all of its attributes
		this.broker = broker;
		this.strategy = strategy;
		this.coin = coin;
		this.buyOrSell = buyOrSell;
		this.quantity = quantity;
		this.price = price;
		this.date = date;
	}
	
	//getters are provided but no setters because once a TradeResult is created using the constructor, its values should not be changed
	
	/**
	 * @return broker returns the broker involved in the trade
	 */
	@Override
	public ITradingBroker getBroker() {
		return broker;
	}
	
	/**
	 * @return strategy returns the trading strategy used in the trade
	 */
	@Override
	public ITradingStrategy getStrategy() {
		return strategy;
	}
	
	/**
	 * @return coin returns the name of the coin in the transaction
	 */
	@Override
	public String getCoin() {
		return coin;
	}
	
	/**
	 * @return buyOrSell returns the action used in the transaction
	 */
	@Override
	public String getBuyOrSell() {
		return buyOrSell;
	}
	
	/**
	 * @return quantity returns the quantity of coins bought/sold
	 */
	@Override
	public Double getQuantity() {
		return quantity;
	}
	
	/**
	 * @return price returns the price the coins were bought/sold at
	 */
	@Override
	public Double getPrice() {
		return price;
	}
	
	/**
	 * @return returns the "mm-dd-yyyy" string of todays date
	 */
	@Override
	public String getDate() {
		return date;
	}
}
