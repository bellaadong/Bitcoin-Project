package Viewers;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;

/**
 * @author Colton Undseth
 * @author Bella Dong
 * @author Joey Gendy
 * @author Scott Guo
 * 
 * represents the window that is displayed when the incorrect credentials are entered
 */
public class IncorrectLoginForm implements ActionListener{
	private static IncorrectLoginForm instance;
	
	//main JFrame
	private JFrame frame = new JFrame();
	//a button to exit program
	private JButton exitButton = new JButton("Exit");
	//a text field to enter a username
	private JLabel message = new JLabel("Incorrect Username or Password");

	/**
	 * ensures only one instance of the class is made with singleton pattern
	 * @return instance returns the static instance of the class
	 */
	public static IncorrectLoginForm getInstance() {
		if (instance == null) {
			instance = new IncorrectLoginForm();
		}
		
		return instance;
	}
	
	/**
	 * creates all of the elements to go on the JFrame and sets their attributes
	 */
	private IncorrectLoginForm() {
		//set attributes for message label
		message.setForeground(Color.RED);
		message.setHorizontalAlignment(SwingConstants.CENTER);
		message.setFont(new Font("Tahoma", Font.BOLD, 20));
		message.setBounds(66, 75, 385, 51);
		
		//set attributes for exit button
		exitButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		exitButton.setBounds(150, 249, 225, 25);
		exitButton.setFocusable(false);
		exitButton.addActionListener(this);
		
		//set attributes for main JFrame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(532, 380);
		frame.getContentPane().setLayout(null);
		
		//add all of the buttons and labels to main JFrame
		frame.getContentPane().add(message);
		frame.getContentPane().add(exitButton);
		frame.setFont(new Font("Tahoma", Font.BOLD, 12));
		frame.setResizable(false);
		
		//make window visible
		frame.setVisible(true);
	}
	
	/**
	 * when a button with an Action listener is pushed, this method will be called to handle the operations
	 * @param event the action performed
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		//if clear button is pressed
		if (event.getSource() == exitButton) {
			//terminate program
			System.exit(0);
		}
		
	}
}
