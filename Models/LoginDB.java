package Models;

import java.io.*;
import java.util.*;

/**
 * @author Colton Undseth
 * @author Bella Dong
 * @author Joey Gendy
 * @author Scott Guo
 * Reads all of the user's usernames and passwords out of a text file and stores them in a HashMap as a data base
 */
public class LoginDB implements ILoginDB {
	
	private static ILoginDB instance = null;
	//use a hash map to store usernames and passwords
	private HashMap<String, String> userDB;
	
	/**
	 * Reads lines out of text file and add username and password to HashMap database
	 */
	private LoginDB() {
		 userDB = new HashMap<String, String>();
		
		 try {
			 //open text file containing user data
			 File credentials = new File("UserCredentials.txt");
			 Scanner reader = new Scanner(credentials);
			 StringTokenizer tokenizer;
			 
			 //loop through all lines that are in the form: "username password"
			 while (reader.hasNextLine()) {
				 String line = reader.nextLine();
				 
				 //tokenize the line to separate the username from the password
				 tokenizer = new StringTokenizer(line);
				 String username = tokenizer.nextToken();
				 String password = tokenizer.nextToken();
				 
				 //add each user
				 addUser(username, password);
			 }
			 reader.close();
		 }
		 catch(FileNotFoundException e) {
			 System.out.println();
			 e.printStackTrace();
		 }
	}
	
	/**
	 * @return instance Returns the static instance of the LoginDB
	 */
	public static ILoginDB getInstance() {
		//use the singleton design pattern for the database
		if (instance == null) {
			instance = new LoginDB();
		}
		return instance;
	}
	
	/**
	 * @param username the username of a user
	 * @param password the password of a user
	 * Adds a username (key) and a password (value) pair to the TradeDB HashMap
	 */
	private void addUser(String username, String password) {
		//adding a user puts a new key-value pair into map
		userDB.put(username, password);
	}
	
	/**
	 * @param username a user's username
	 * @return userDB.get(username) the password associated with a user's username in the HashMap
	 */
	@Override
	public String get(String username) {
		//gets the password associated with the entered username
		return userDB.get(username);
	}
	
}
