package Viewers;
import Controllers.UserAuthentication;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * @author Colton Undseth
 * @author Bella Dong
 * @author Joey Gendy
 * @author Scott Guo
 * 
 * Opens a window with 2 text boxes, one for user name, one for password.
 * Implemented using the singlton design pattern.
 */
public class LoginForm implements ActionListener{
	private static LoginForm instance;
	//main JFrame
	private JFrame frame = new JFrame();
	//a button to submit login info
	private JButton loginButton = new JButton("Login");
	//a button to clear login fields
	private JButton clearButton = new JButton("Clear");
	//a text field to enter a username
	private JTextField userIDField = new JTextField();
	//a text field to enter a password
	private JPasswordField userPasswordField = new JPasswordField();
	//a label for the username box
	private JLabel userIDLabel = new JLabel("User ID: ");
	//a label for the password box
	private JLabel userPasswordLabel = new JLabel("Password: ");
	
	/**
	 * @return instance a static instance of LoginForm
	 * implements the singleton design pattern dor the LoginForm
	 */
	public static LoginForm getInstance() {
		if (instance == null) {
			instance = new LoginForm();
		}
		return instance;
	}
	
	/**
	 * Creates JFrame elements, sets their attributes, and attaches them to the main Login window
	 */
	private LoginForm() {		
		//set attributes for username label
		userIDLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		userIDLabel.setBounds(75, 100, 75, 25);
		
		//set attributes for password label
		userPasswordLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		userPasswordLabel.setBounds(72, 151, 91, 25);
		
		//set attributes for username and password text box
		userIDField.setBounds(175, 100, 200, 25);
		userPasswordField.setBounds(175, 150, 200, 25);
		
		//set attributes for login button
		loginButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		loginButton.setBounds(175, 200, 100, 25);
		loginButton.setFocusable(false);
		loginButton.addActionListener(this);
		
		//set attributes for clear button
		clearButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		clearButton.setBounds(275, 200, 100, 25);
		clearButton.setFocusable(false);
		clearButton.addActionListener(this);
		
		//set attributes for main JFrame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(532, 380);
		frame.getContentPane().setLayout(null);
		
		//add all of the buttons, fields, and labels to main JFrame
		frame.getContentPane().add(userIDLabel);
		frame.getContentPane().add(userPasswordLabel);
		frame.getContentPane().add(userIDField);
		frame.getContentPane().add(userPasswordField);
		frame.getContentPane().add(loginButton);
		frame.getContentPane().add(clearButton);
		frame.setFont(new Font("Tahoma", Font.BOLD, 12));
		frame.setResizable(false);
		
		//make window visible
		frame.setVisible(true);
	}
	
	/**
	 * @param event When an element that has an action listener attached, the source of the event will be tracked so we know what button was pressed
	 * performs appropriate operations depending on which button was pressed
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		//if clear button is pressed
		if (event.getSource() == clearButton) {
			//set username and password fields to blank
			userIDField.setText("");
			userPasswordField.setText("");
		}
		//if login button is pressed
		else if (event.getSource() == loginButton) {
			//get the text entered into the username and password text boxes
			String username = userIDField.getText();
			String password = String.valueOf(userPasswordField.getPassword());
			
			//validate the credentials entered by the user
			if (UserAuthentication.validateCredentials(username, password)) {
				//close the login window
				frame.dispose();
				//open the MainUI
				MainUI.getInstance();
			}
			else {
				//if credentials are incorrect, display IncorrectLoginForm
				IncorrectLoginForm.getInstance();
			}
		}
		
	}
}
