package Controllers;

public interface ITradingStrategyCreator {

	//use the factory design pattern to create the appropriate strategy
	ITradingStrategy factoryMethod(String strategyString);

}