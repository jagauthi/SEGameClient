package client_View;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client_Controller.ChatClient;

public class Launcher{
  
	final int WIDTH = 1280;
	final int HEIGHT = 720;
	
	Font normalFont = new Font("Arial", Font.BOLD, 30);
	Font bigFont = new Font("Arial", Font.BOLD, 60);

	private JFrame frame;
	
	//Panels
	private JPanel cards;
    private JPanel connectPanel;
    private JPanel loginPanel;
    private JPanel charSelectPanel;
    
    //Labels
    private JLabel passwordLabel;
    private JLabel nameLabel;
    private JLabel char1Label;
    
    //Text fields
    private JTextField nameText;
    private JPasswordField passwordText;
    
    //Buttons
	private JButton connectButton;
	private JButton loginButton;
	private JButton createAccountButton;
	private JButton forgotPasswordButton;
	
	public Launcher(){
	//	connectToServer();
        frame = new JFrame();
		cards = new JPanel(new CardLayout());
		connectPanel = new JPanel();
		loginPanel = new JPanel();
		charSelectPanel = new JPanel();

		initConnectPanel();
		initLoginPanel();
		initCharSelectPanel();
		
		cards.add(connectPanel, "Connect Panel");
		cards.add(loginPanel, "Login Panel");
		cards.add(charSelectPanel, "Char Select Panel");
        
        frame.add(cards, BorderLayout.CENTER);
        
        frame.setTitle("Launcher");
		frame.setSize(600, 400);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
	}
	
	public void initConnectPanel()
	{
        connectButton = new JButton();
        connectButton.setPreferredSize(new Dimension(400, 200));
        connectButton.setText("Play!");
        connectButton.setFont(bigFont);
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goToLogin(evt);
            }
        });
        
        connectPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        
        c.gridx = 0;
        c.gridy = 0;
        connectPanel.add(connectButton, c);
	}
	
	public void initLoginPanel()
	{
		nameLabel = new JLabel();
        passwordLabel = new JLabel();
        nameText = new JTextField();
        passwordText = new JPasswordField();
        loginButton = new JButton();
        createAccountButton = new JButton();
        
        nameLabel.setText("Username: ");
        passwordLabel.setText("Password: ");
        nameText.setPreferredSize(new Dimension(200, 40));
        passwordText.setPreferredSize(new Dimension(200, 40));
        
        loginButton.setText("Login!");
        loginButton.setFont(normalFont);
        createAccountButton.setText("Create Account");
        
        loginButton.setPreferredSize(new Dimension(100, 50));
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logIn(evt);
            }
        });
        //createAccountButton.setPreferredSize(newDimension(100, 50));
        createAccountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createAccount(evt);
            }
        });
        
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        
        c.gridx = 0;
        c.gridy = 0;
        loginPanel.add(nameLabel, c);
        
        c.gridx = 1;
        c.gridy = 0;
        loginPanel.add(nameText, c);

        c.gridx = 0;
        c.gridy = 1;
        loginPanel.add(passwordLabel, c);

        c.gridx = 1;
        c.gridy = 1;
        loginPanel.add(passwordText, c);

        c.gridx = 0;
        c.gridy = 3;
        loginPanel.add(createAccountButton, c);
        
        c.gridx = 1;
        c.gridy = 2;
        loginPanel.add(loginButton , c);
	}
	
	public void initCharSelectPanel()
	{
        char1Label = new JLabel();
        char1Label.setText("Character 1");
        charSelectPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        
        c.gridx = 0;
        c.gridy = 0;
        charSelectPanel.add(char1Label, c);
	}
	
	private void switchCards(String cardName)
	{
		CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, cardName);
	}
    
	private void goToLogin(ActionEvent evt) {
		//Maybe check to make sure we're connected to server first, before
		//switching over to the login screen??
		switchCards("Login Panel");
    }
	
	private void logIn(ActionEvent evt)
	{
		if(connectToDatabase())
		{
			switchCards("Char Select Panel");
		}
	}
	
	private void createAccount(ActionEvent evt)
	{
		//Will switch the user over to the createAccountPanel
	}
	
	public void connectToServer()
	{
		ChatClient client = new ChatClient();
		client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        try {
			client.run();
		} 
        catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Boolean connectToDatabase()
	{
		try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
	      }
	      catch(ClassNotFoundException ex) {
	            System.out.println("Error: unable to load driver class!");
	            System.exit(1);
	      }
	      catch(IllegalAccessException ex) {
	            System.out.println("Error: access problem while loading!");
	            System.exit(2);
	      }
	      catch(InstantiationException ex) {
	            System.out.println("Error: unable to instantiate driver!");
	            System.exit(3);
	      }
	      
	      String URL = "jdbc:mysql://sql5.freesqldatabase.com:3306/sql5104581";
	      String USER = "sql5104581";
	      String PASS = "8CHGkEPagu";
	      Connection con = null;
	      Statement stmt = null;
	      try
	      {
	         con = DriverManager.getConnection(URL, USER, PASS);
	         stmt = con.createStatement();String sql;
	         sql = "SELECT * from AccountsTable where Username = \'" + nameText.getText() + "\';";
	         ResultSet rs = stmt.executeQuery(sql);
	          
	         //If there is no results for the entered username
	         if (!rs.next()) {
	           System.out.println("No account found with username: " + nameText.getText());
	           return false;
	           //Call a method that suggests the player to make a new account.
	         }
	
	         //Set the ResultSet counter back to the beginning of the ResultSet
	         //This is necessary since the previous method moves the counter forward to
	         //check if the ResultSet is empty.
	         rs.beforeFirst();
	         while(rs.next())
	         {
	             int accountNumber = rs.getInt("AccountNumber");
	             String accountUsername = rs.getString("Username");
	             String accountPassword = rs.getString("Password");
	
	             if(nameText.getText().equals(accountUsername))
	             {
	             	if(String.valueOf(passwordText.getPassword()).equals(accountPassword))
	             	{
	             		System.out.println("Yay! Password correct!");
	             	}
	             	else
	             	{
	             		System.out.println("Oh no :( Password incorrect");
	             		return false;
	             		//numFailedLoginAttempts++;
	             		/*
	             		 * Clear out password field
	             		 * leave username populated
	             		 * display message "Invalid login"
	             		 */
	             	}
	             }
	         }
	         // cleaning up
	         rs.close();
	         stmt.close();
	         con.close();
	      } 
	      catch (SQLException e) {
	         e.printStackTrace();
	      }
	      catch (Exception e) {
	          e.printStackTrace();
	      }
	      finally
	      {  
	          //closing the resources in case they weren't closed earlier
	          try {
	          if (stmt != null)
	                  stmt.close();
	          }
	          catch (SQLException e2) {
	             e2.printStackTrace();
	          }
	          try {
	              if (con != null) {
	                  con.close();
	               }
	          } 
	          catch (SQLException e2) {
	                 e2.printStackTrace();
	          }
	      }
	      return true;
      }
}

