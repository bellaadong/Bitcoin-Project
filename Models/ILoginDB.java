package Models;

public interface ILoginDB {

	//gets the password associated with the entered username
	String get(String username);

}