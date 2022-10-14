package Models;

import java.util.LinkedList;

import Controllers.ITradeResult;

public interface ITradeDB {

	//adds a trade to the database
	void addTrade(ITradeResult result);

	//returns a copy of the trade database
	LinkedList<ITradeResult> getTradeList();

}