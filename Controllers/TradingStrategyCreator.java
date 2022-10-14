package Controllers;

import Controllers.TradingStrategies.TradingStrategyA;
import Controllers.TradingStrategies.TradingStrategyB;
import Controllers.TradingStrategies.TradingStrategyC;
import Controllers.TradingStrategies.TradingStrategyD;

/**
 * @author Colton Undseth
 * @author Bella Dong
 * @author Joey Gendy
 * @author Scott Guo
 * 
 * uses the factory design pattern to create different TradingStrategies depending on the parameters entered
 */
public class TradingStrategyCreator implements ITradingStrategyCreator {
	
	/**
	 * @param strategyString the string representation of the TradingStrategy's name
	 * @return TradingStrategy returns the type of trading strategy that the was entered as the param
	 * use the factory design pattern to create the appropriate strategy
	 */
	@Override
	public ITradingStrategy factoryMethod(String strategyString) {
		//use a switch to create the correct type of strategy 
		switch (strategyString) {
		case "Strategy A":
			return new TradingStrategyA();
		case "Strategy B":
			return new TradingStrategyB();
		case "Strategy C":
			return new TradingStrategyC();
		case "Strategy D":
			return new TradingStrategyD();
		default:
			return null;
		}
		
	}

}
