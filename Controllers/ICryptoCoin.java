package Controllers;

public interface ICryptoCoin {

	// getters and setters for all  coin attributes
	Double getPrice();

	void setPrice(Double price);

	String getName();

	//if the name has an ID pair in the tickerToIDPairs hash map, then convert the name to the id before setting name
	void setName(String name);

	Double getVolume();

	void setVolume(Double volume);

	Double getMarketCap();

	void setMarketCap(Double mktCap);

	// 2 coins are equal if their names are the same
	boolean equals(ICryptoCoin other);
}