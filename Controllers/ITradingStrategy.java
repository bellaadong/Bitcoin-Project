package Controllers;

public interface ITradingStrategy { 
	
	
	//method signature for the executeStrategy() methods that will be implemented by each of the Strategies that use the abstract method
	
	//uses the strategy design pattern because when the broker's TradingStrategy is created and set to a broker using the factory method,
	//the TradingBroker acts like a context class that allows you to set the strategy. Each strategy subclass implements the executeStrategy method 
	//so all of the calls to each of the specific strategies will be the same
	public void executeStrategy(ITradingBroker broker);
	
	public String toString();
}
