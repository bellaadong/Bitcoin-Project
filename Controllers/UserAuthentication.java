package Controllers;

import Models.LoginDB;

/**
 * @author Colton Undseth
 * @author Bella Dong
 * @author Joey Gendy
 * @author Scott Guo
 * reads from the LoginDB to validate the username and password that a user enters into the login window
 */
public class UserAuthentication{
	
	/**
	 * checks if the username and password match what is in the LoginDB
	 * @param username
	 * @param password
	 * @return boolean true if the username and password match what is in the LoginDB, false otherwise
	 */
	public static boolean validateCredentials(String username, String password) {
		//returns true if user exists in database and the password entered matches the database
		String passwordInDB = LoginDB.getInstance().get(username);
		
		//if passwordInDB == null, then the username does not exist
		//if passwordInDB != password, then the username exists, but the password is incorrect
		if ((passwordInDB == null) || !(passwordInDB.equals(password))) {
			return false;
		}
		return true;
	}
}
