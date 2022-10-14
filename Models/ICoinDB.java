package Models;

public interface ICoinDB {

	//adds a coin to the linked list by creating a JsonObject and storing it in the linked list
	void addCoin(String name, Double price, Double volume, Double mktCap);

	//searches the linked list of JsonObjects for a coin by name and when found, returns the property of the coin entered
	//if the coin is not found by name, -1 us returned
	Double get(String name, String property);

}