package Controllers;

public interface ICoinDataFetcher {

	double getPriceForCoin(String id, String date);

	double getMarketCapForCoin(String id, String date);

	double getVolumeForCoin(String id, String date);
}