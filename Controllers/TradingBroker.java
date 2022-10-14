package Controllers;


/**
 * @author Colton Undseth
 * @author Bella Dong
 * @author Joey Gendy
 * @author Scott Guo
 * 
 * Represents a TradingBroker will all of their attributes (name, coin list, and strategy)
 * acts like the Context class in the strategy design pattern because it allows you to set the broker's strategy and then call their specific strategy
 */
public class TradingBroker implements ITradingBroker {
	//attributes of a TradingBroker
	private String name;
	private ICryptoCoin[] coinList;
	private ITradingStrategy strategy;
	
	/**
	 * creates a TradingBroker who has unknown attributes so they are set to null
	 */
	public TradingBroker() {
		//overload the constructor for a trading broker
		name = null;
		coinList = null;
		strategy = null;
	}
	
	/**
	 * creates a TradingBroker with known attributes
	 * @param name the name of the broker
	 * @param coinList the list of CryptoCoin the broker is interested in
	 * @param strategy the particular TradingStrategy the broker is using to trade
	 */
	public TradingBroker(String name, ICryptoCoin[] coinList, ITradingStrategy strategy) {
		//overload the constructor for a trading broker
		this.name = name;
		this.coinList = coinList;
		this.strategy = strategy;
	}
	
	/**
	 * @return String returns the name of the broker
	 */
	@Override
	public String getName() {
		return name;
	}
	
	/**
	 * @param newName sets the name to a newName
	 */
	@Override
	public void setName(String newName) {
		name = newName;
	}
	
	/**
	 * @return ICryptoCoin[] returns the broker's list of CryptoCoins 
	 */
	@Override
	public ICryptoCoin[] getCoinList() {
		return coinList;
	}
	
	/**
	 * @param newList a new list of crypto coins to set
	 */
	@Override
	public void setCoinList(ICryptoCoin[] newList) {
		coinList = newList;
	}
	
	/**
	 * @param newStrategy sets the broker's strategy as the new strategy
	 */
	@Override
	public void setStrategy(ITradingStrategy newStrategy) {
		strategy = newStrategy;
	}
	
	/**
	 * @return TradingStrategy returns the broker's strategy
	 */
	@Override
	public ITradingStrategy getStrategy() {
		return strategy;
	}
	
	/**
	 * this implements the strategy design pattern because TradingBrokers behave like a context class that allows you to set the strategy and call it.
	 * the specific executeStrategy() method that is called depends on the TradingStrategy that the broker has set
	 */
	@Override
	public void executeStrategy() {
		//this implements the strategy design pattern because TradingBrokers behave like a context class that allows you to set the strategy and call it
		//the specific executeStrategy() method that is called depends on the TradingStrategy that the broker has set
		strategy.executeStrategy(this);
	}
}
