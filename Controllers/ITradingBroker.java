package Controllers;

public interface ITradingBroker {

	// provide all getters and setters for the attributes of a TradingBroker
	String getName();

	void setName(String newName);

	ICryptoCoin[] getCoinList();

	void setCoinList(ICryptoCoin[] newList);

	void setStrategy(ITradingStrategy newStrategy);

	ITradingStrategy getStrategy();

	//this implements the strategy design pattern because TradingBrokers behave like a context class that allows you to set the strategy and call it
	//the specific executeStrategy() method that is called depends on the TradingStrategy that the broker has set
	void executeStrategy();

}