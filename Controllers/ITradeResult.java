package Controllers;

public interface ITradeResult {

	//getters are provided but no setters because once a TradeResult is created using the constructor, its values should not be chnaged
	ITradingBroker getBroker();

	ITradingStrategy getStrategy();

	String getCoin();

	String getBuyOrSell();

	Double getQuantity();

	Double getPrice();

	String getDate();

}