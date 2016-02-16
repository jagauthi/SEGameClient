package client_View;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import client_Controller.ChatClient;

public class Launcher{
  
	final int WIDTH = 600;
	final int HEIGHT = 400;
	
	Font normalFont = new Font("Arial", Font.BOLD, 30);
	Font bigFont = new Font("Arial", Font.BOLD, 60);

	private JFrame frame;
	
	//Panels
	private JPanel cards;
    private JPanel connectPanel;
    private JPanel loginPanel;
    private JPanel createAccountPanel;
    private JPanel charSelectPanel;
    private JPanel createNewCharacterPanel;

    //Text fields
    private JTextField loginNameText;
    private JPasswordField loginPasswordText;
    
    private JTextField createNameText;
    private JTextField createEmailText;
    private JPasswordField createPasswordText;
    private JPasswordField createVerifyPasswordText;
    private JTextField createSecAnswer1Text;
    private JTextField createSecAnswer2Text;
    JComboBox secQuestions1;
    JComboBox secQuestions2;
    
    private JTextField newCharacterNameText;
	
	ChatClient client;
	
	//Each of the elements holds a string with
	//info about each individual character. Each
	//of the strings hold values for the character's
	//stats and stuff, and they're each separated
	//by a space (or some other delimiter)
	ArrayList<String> characters = new ArrayList<String>();
	
	String accountID;
	
	public Launcher(){
		connectToServer();
        frame = new JFrame();
		cards = new JPanel(new CardLayout());
		connectPanel = new JPanel();
		loginPanel = new JPanel();
		createAccountPanel = new JPanel();
		charSelectPanel = new JPanel();
		createNewCharacterPanel = new JPanel();

		initConnectPanel();
		initLoginPanel();
		initCreateAccountPanel();
		initCreateNewCharacterPanel();
		
		cards.add(connectPanel, "Connect Panel");
		cards.add(loginPanel, "Login Panel");
		cards.add(createAccountPanel, "Create Account Panel");
		cards.add(charSelectPanel, "Char Select Panel");
		cards.add(createNewCharacterPanel, "Create New Character Panel");
        
        frame.add(cards, BorderLayout.CENTER);
        
        frame.setTitle("Launcher");
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
	}
	
	public void initConnectPanel()
	{
        JButton connectButton = new JButton();
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
		JLabel loginNameLabel = new JLabel();
		JLabel loginPasswordLabel = new JLabel();
        loginNameText = new JTextField();
        loginPasswordText = new JPasswordField();
        JButton loginButton = new JButton();
        JButton createAccountButton = new JButton();
        
        loginNameLabel.setText("Username: ");
        loginPasswordLabel.setText("Password: ");
        loginNameText.setPreferredSize(new Dimension(200, 40));
        loginPasswordText.setPreferredSize(new Dimension(200, 40));
        
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
                goToCreateAccount(evt);
            }
        });
        
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        
        c.gridx = 0;
        c.gridy = 0;
        loginPanel.add(loginNameLabel, c);
        
        c.gridx = 1;
        c.gridy = 0;
        loginPanel.add(loginNameText, c);

        c.gridx = 0;
        c.gridy = 1;
        loginPanel.add(loginPasswordLabel, c);

        c.gridx = 1;
        c.gridy = 1;
        loginPanel.add(loginPasswordText, c);

        c.gridx = 0;
        c.gridy = 3;
        loginPanel.add(createAccountButton, c);
        
        c.gridx = 1;
        c.gridy = 2;
        loginPanel.add(loginButton , c);
	}
	
	public void initCreateAccountPanel()
	{
		JLabel createNameLabel = new JLabel();
		JLabel createEmailLabel = new JLabel();
		JLabel createPasswordLabel = new JLabel();
		JLabel createVerifyPasswordLabel = new JLabel();
        
        createNameText = new JTextField();
        createEmailText = new JTextField();
        createPasswordText = new JPasswordField();
        createVerifyPasswordText = new JPasswordField();
        createSecAnswer1Text = new JTextField();
        createSecAnswer2Text = new JTextField();
        JButton createButton = new JButton();
        JButton createAccountBackButton = new JButton();
        
        String[] questions1 = { "What is your mothers maiden name", "Sec Question 2", "Sec Question 3" };
        String[] questions2 = { "Name of your first pet", "Sec Question 2", "Sec Question 3" };
        secQuestions1 = new JComboBox(questions1);
        secQuestions1.setSelectedIndex(0);
        secQuestions2 = new JComboBox(questions2);
        secQuestions2.setSelectedIndex(0);
        
        createNameLabel.setText("Username: ");
        createEmailLabel.setText("Email: ");
        createPasswordLabel.setText("Password: ");
        createVerifyPasswordLabel.setText("Re-enter password: ");
        createNameText.setPreferredSize(new Dimension(200, 40));
        createEmailText.setPreferredSize(new Dimension(200, 40));
        createPasswordText.setPreferredSize(new Dimension(200, 40));
        createVerifyPasswordText.setPreferredSize(new Dimension(200, 40));
        createSecAnswer1Text.setPreferredSize(new Dimension(200, 40));
        createSecAnswer2Text.setPreferredSize(new Dimension(200, 40));
        secQuestions1.setPreferredSize(new Dimension(300, 40));
        secQuestions2.setPreferredSize(new Dimension(300, 40));
        
        createButton.setText("Create!");
        //createButton.setFont(normalFont);
        createAccountBackButton.setText("<< Back");
        
        createButton.setPreferredSize(new Dimension(100, 50));
        createButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createAccount(evt);
            }
        });

        createAccountBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createAccountGoBack(evt);
            }
        });
        
        createAccountPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        
        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        createAccountPanel.add(createAccountBackButton, c);
        

        c.anchor = GridBagConstraints.CENTER;
        
        c.gridx = 1;
        c.gridy = 1;
        createAccountPanel.add(createNameLabel, c);
        
        c.gridx = 1;
        c.gridy = 2;
        createAccountPanel.add(createEmailLabel, c);
        
        c.gridx = 1;
        c.gridy = 3;
        createAccountPanel.add(createPasswordLabel, c);
        
        c.gridx = 1;
        c.gridy = 4;
        createAccountPanel.add(createVerifyPasswordLabel, c);
        
        c.gridx = 1;
        c.gridy = 5;
        createAccountPanel.add(secQuestions1, c);
        
        c.gridx = 1;
        c.gridy = 6;
        createAccountPanel.add(secQuestions2, c);
        
        c.gridx = 2;
        c.gridy = 1;
        createAccountPanel.add(createNameText, c);
        
        c.gridx = 2;
        c.gridy = 2;
        createAccountPanel.add(createEmailText, c);
        
        c.gridx = 2;
        c.gridy = 3;
        createAccountPanel.add(createPasswordText, c);
        
        c.gridx = 2;
        c.gridy = 4;
        createAccountPanel.add(createVerifyPasswordText, c);
        
        c.gridx = 2;
        c.gridy = 5;
        createAccountPanel.add(createSecAnswer1Text, c);
        
        c.gridx = 2;
        c.gridy = 6;
        createAccountPanel.add(createSecAnswer2Text, c);

        c.gridx = 2;
        c.gridy = 7;
        createAccountPanel.add(createButton, c);
        
	}
	
	public void initCharSelectPanel()
	{
		charSelectPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        
		JButton[] buttons = new JButton[5];
        JLabel[] labels = new JLabel[5];
        int lastY = 1;
		for(int x = 0; x < characters.size(); x++)
		{
			String[] charInfo = characters.get(x).split(" ");
			buttons[x] = new JButton();
	        buttons[x].setPreferredSize(new Dimension(200, 200));
	        buttons[x].addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                selectChar(evt);
	            }
	        });
	        labels[x] = new JLabel();
	        labels[x].setText(charInfo[0] + ", a level " + charInfo[2] + "\n" + charInfo[1]);
	        
	        c.gridx = 0;
	        c.gridy = x + 1;
	        charSelectPanel.add(buttons[x], c);
	        
	        c.gridx = 1;
	        c.gridy = x + 1;
	        charSelectPanel.add(labels[x], c);
	        
	        lastY++;
		}
		
		JButton logoutButton = new JButton();
		logoutButton.setText("Logout");
        logoutButton.setPreferredSize(new Dimension(100, 50));
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logout(evt);
            }
        });
		
        c.gridx = 2;
        c.gridy = 0;
        charSelectPanel.add(logoutButton, c);
        
		JButton[] createNewCharButtons = new JButton[5 - characters.size()];
        if(characters.size() < 5)
        {
        	for(int x = 0; x < (5 - characters.size()); x++)
        	{
        		createNewCharButtons[x] = new JButton();
        		createNewCharButtons[x].setText("Create New Character");
        		createNewCharButtons[x].setPreferredSize(new Dimension(100, 100));
        		createNewCharButtons[x].addActionListener(new java.awt.event.ActionListener() {
    	            public void actionPerformed(java.awt.event.ActionEvent evt) {
    	                createNewCharacter(evt);
    	            }
    	        });
    	        
    	        c.gridx = 0;
    	        c.gridy = lastY + x;
    	        charSelectPanel.add(createNewCharButtons[x], c);
    		}
        }
	}
	
	public void initCreateNewCharacterPanel()
	{
		JPanel namePanel = new JPanel();
		JPanel sexPanel = new JPanel();
		JPanel classPanel = new JPanel();
		JPanel statPanel = new JPanel();
		
////////Name Panel
		JLabel newCharacterNameLabel = new JLabel();
        newCharacterNameText = new JTextField();
        JButton createNewCharacterButton = new JButton();
        
        newCharacterNameLabel.setText("Name: ");
        newCharacterNameText.setPreferredSize(new Dimension(300, 40));
        
        createNewCharacterButton.setText("Create!");
        createNewCharacterButton.setPreferredSize(new Dimension(100, 50));
        createNewCharacterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createCharacter(evt);
            }
        });
        
    	namePanel.setBorder(BorderFactory.createTitledBorder("Don't use your account name (for security reasons)"));
        namePanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        
        c.gridx = 0;
        c.gridy = 0;
        namePanel.add(newCharacterNameLabel, c);
        
        c.gridx = 1;
        c.gridy = 0;
        namePanel.add(newCharacterNameText, c);
        
////////Sex Panel
        JRadioButton male = new JRadioButton("Male", true);
    	JRadioButton female = new JRadioButton("Female");
    	
    	sexPanel.setBorder(BorderFactory.createTitledBorder("Gender"));
    	sexPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        
        c.gridx = 0;
        c.gridy = 0;
        sexPanel.add(male, c);
        
        c.gridx = 0;
        c.gridy = 1;
        sexPanel.add(female, c);
        
/////////Class Panel
        JRadioButton warrior = new JRadioButton("Warrior", true);
    	JRadioButton rogue = new JRadioButton("Rogue");
    	JRadioButton mage = new JRadioButton("Mage");
    	
    	classPanel.setBorder(BorderFactory.createTitledBorder("Class"));
    	classPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        
        c.gridx = 0;
        c.gridy = 0;
        classPanel.add(warrior, c);
        
        c.gridx = 0;
        c.gridy = 1;
        classPanel.add(rogue, c);
        
        c.gridx = 0;
        c.gridy = 2;
        classPanel.add(mage, c);
        
/////////Stat Panel

        class buttonThing extends JComponent
        {
        	JPanel panel;
        	JButton minus;
        	JButton plus;
        	JLabel points;
        	JLabel label;
        	
        	public buttonThing(String l)
        	{
        		panel = new JPanel();
        		panel.setLayout(new GridBagLayout());
        		GridBagConstraints c = new GridBagConstraints();
                c.fill = GridBagConstraints.NONE;
                
                label = new JLabel();
        		label.setText(l);
        		minus = new JButton();
        		plus = new JButton();
        		points = new JLabel();
        		points.setText("10");        		
        		
        		minus.setText("-");
                minus.setPreferredSize(new Dimension(50, 50));
                minus.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        minusStat(evt);
                    }
                });
                
                plus.setText("+");
                plus.setPreferredSize(new Dimension(50, 50));
                plus.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        plusStat(evt);
                    }
                });
                
                c.gridx = 0;
                c.gridy = 0;
                panel.add(minus, c);
                
                c.gridx = 1;
                c.gridy = 0;
                panel.add(points, c);
                
                c.gridx = 2;
                c.gridy = 0;
                panel.add(plus, c);
                
                c.gridx = 3;
                c.gridy = 0;
                panel.add(label, c);
        	}
        	
        	public void minusStat(ActionEvent evt)
        	{
        		
        	}
        	
        	public void plusStat(ActionEvent evt)
        	{
        		
        	}
        }

        JLabel pointsLeft = new JLabel();
        buttonThing strengthField = new buttonThing("Strength");
        buttonThing dexterityField = new buttonThing("Dexterity");
        buttonThing constitutionField = new buttonThing("Constitution");
        buttonThing intelligenceField = new buttonThing("Intelligence");
        buttonThing willpowerField = new buttonThing("Willpower");
        buttonThing luckField = new buttonThing("Luck");
    	
    	statPanel.setBorder(BorderFactory.createTitledBorder(""));
    	statPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        
        c.gridx = 0;
        c.gridy = 0;
        statPanel.add(pointsLeft, c);
        
        c.gridx = 0;
        c.gridy = 1;
        statPanel.add(strengthField.panel, c);
        
        c.gridx = 0;
        c.gridy = 2;
        statPanel.add(dexterityField.panel, c);
        
        c.gridx = 0;
        c.gridy = 3;
        statPanel.add(constitutionField.panel, c);
        
        c.gridx = 0;
        c.gridy = 4;
        statPanel.add(intelligenceField.panel, c);
        
        c.gridx = 0;
        c.gridy = 5;
        statPanel.add(willpowerField.panel, c);
        
        c.gridx = 0;
        c.gridy = 6;
        statPanel.add(luckField.panel, c);
        
        
/////////Create New Character Panel
        createNewCharacterPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;

        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 1;
        c.gridy = 0;
        createNewCharacterPanel.add(namePanel, c);
        
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 1;
        createNewCharacterPanel.add(sexPanel, c);
        
        c.gridx = 0;
        c.gridy = 2;
        createNewCharacterPanel.add(classPanel, c);

        //c.anchor = GridBagConstraints.CENTER;
        c.gridx = 1;
        c.gridy = 2;
        createNewCharacterPanel.add(statPanel, c);
        
        c.gridx = 1;
        c.gridy = 3;
        createNewCharacterPanel.add(createNewCharacterButton, c);
	}
	
	public void switchCards(String cardName)
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
		String username = loginNameText.getText();
		String password = String.valueOf(loginPasswordText.getPassword());
		if(username.equals(""))
		{
			loginPasswordText.setText("");
			JOptionPane.showMessageDialog(null, "Username field is empty.", "ERROR", 
					JOptionPane.INFORMATION_MESSAGE);
		}
		else if(password.equals(""))
		{
			loginNameText.setText("");
			JOptionPane.showMessageDialog(null, "Password field is empty.", "ERROR", 
					JOptionPane.INFORMATION_MESSAGE);
		}
		else 
		{
			client.sendMessage("LOGIN:" + username + ":" + password);
		}
	}
	
	private void createAccount(ActionEvent evt)
	{
		String username = createNameText.getText();
		String email = createEmailText.getText();
		String password = String.valueOf(createPasswordText.getPassword());
		String passwordVerify = String.valueOf(createVerifyPasswordText.getPassword());
		String securityQuestion1 = (String) secQuestions1.getSelectedItem();
		String securityQuestion2 = (String) secQuestions2.getSelectedItem();
		String securityAnswer1 = createSecAnswer1Text.getText();
		String securityAnswer2 = createSecAnswer2Text.getText();
		/*
		System.out.println("Creating account...");
		System.out.println("Username: " + username);
		System.out.println("Email: " + email);
		System.out.println("Password: " + password);
		System.out.println("Verified Password: " + passwordVerify);
		System.out.println("Security question: " + securityQuestion1);
		System.out.println("Security answer: " + securityAnswer1);
		*/
		
		if(!password.equals(passwordVerify))
		{
			System.out.println("Passwords do not match");
		}
		if(username.equals(""))
		{
			createNameText.setText("");
			createEmailText.setText("");
			createPasswordText.setText("");
			createVerifyPasswordText.setText("");
			createSecAnswer1Text.setText("");
			createSecAnswer2Text.setText("");
			
			JOptionPane.showMessageDialog(null, "Please enter a username.", "ERROR", 
					JOptionPane.INFORMATION_MESSAGE);
		}
		else if(email.equals(""))
		{
			createNameText.setText("");
			createEmailText.setText("");
			createPasswordText.setText("");
			createVerifyPasswordText.setText("");
			createSecAnswer1Text.setText("");
			createSecAnswer2Text.setText("");
			
			JOptionPane.showMessageDialog(null, "Please enter an email.", "ERROR", 
					JOptionPane.INFORMATION_MESSAGE);
		}
		else if(password.equals(""))
		{
			createNameText.setText("");
			createEmailText.setText("");
			createPasswordText.setText("");
			createVerifyPasswordText.setText("");
			createSecAnswer1Text.setText("");
			createSecAnswer2Text.setText("");
			
			JOptionPane.showMessageDialog(null, "Please enter a password.", "ERROR", 
					JOptionPane.INFORMATION_MESSAGE);
		}
		else if(passwordVerify.equals(""))
		{
			createNameText.setText("");
			createEmailText.setText("");
			createPasswordText.setText("");
			createVerifyPasswordText.setText("");
			createSecAnswer1Text.setText("");
			createSecAnswer2Text.setText("");
			
			JOptionPane.showMessageDialog(null, "Please verify password.", "ERROR", 
					JOptionPane.INFORMATION_MESSAGE);
		}
		else if(!password.equals(passwordVerify))
		{
			createPasswordText.setText("");
			createVerifyPasswordText.setText("");
			
			JOptionPane.showMessageDialog(null, "Passwords do not match.", "ERROR", 
					JOptionPane.INFORMATION_MESSAGE);
		}
		else if(securityAnswer1.equals(""))
		{
			JOptionPane.showMessageDialog(null, "Please enter a security answer.", "ERROR", 
					JOptionPane.INFORMATION_MESSAGE);
		}
		else if(securityAnswer2.equals(""))
		{
			JOptionPane.showMessageDialog(null, "Please enter a security answer.", "ERROR", 
					JOptionPane.INFORMATION_MESSAGE);
		}
		else 
		{
			client.sendMessage("CREATEACCOUNT:" + username + ":" 
					+ password + ":" + email + ":" 
					+ securityQuestion1 + ":" 
					+ securityAnswer1 + ":"
					+ securityQuestion2 + ":"
					+ securityAnswer2);
		}
	}
	
	private void createAccountGoBack(ActionEvent evt)
	{
		createNameText.setText("");
		createEmailText.setText("");
		createPasswordText.setText("");
		createVerifyPasswordText.setText("");
		createSecAnswer1Text.setText("");
		createSecAnswer2Text.setText("");
		switchCards("Login Panel");
	}
	
	private void goToCreateAccount(ActionEvent evt)
	{
		loginNameText.setText("");
		loginPasswordText.setText("");
		switchCards("Create Account Panel");
	}
	
	private void selectChar(ActionEvent evt)
	{
		System.out.println("Doesn't do anything yet...");
	}
	
	public void createNewCharacter(ActionEvent evt)
	{
		switchCards("Create New Character Panel");
	}
	
	public void createCharacter(ActionEvent evt)
	{
		//name, accountID, class, gender, str, dex, con, int, wis, lck
	}
	
	public void logout(ActionEvent evt)
	{
		charSelectPanel.removeAll();
		switchCards("Login Panel");
		loginNameText.setText("");
		loginPasswordText.setText("");
	}
	
	public void usernameNotFound()
	{
		loginNameText.setText("");
		loginPasswordText.setText("");
		JOptionPane.showMessageDialog(null, "Username not found.", "ERROR", 
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void incorrectPassword()
	{
		loginPasswordText.setText("");
		JOptionPane.showMessageDialog(null, "Incorrect password.", "ERROR", 
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void accountLocked()
	{
		loginNameText.setText("");
		loginPasswordText.setText("");
		JOptionPane.showMessageDialog(null, "Account has been locked. Please call (719)352-7025, and tell him he is a fuck boi to unlock your account.", "ERROR", 
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void accountBanned()
	{
		loginNameText.setText("");
		loginPasswordText.setText("");
		JOptionPane.showMessageDialog(null, "Account has been banned, probably because you're an asshole.", "ERROR", 
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void moreThanOneAccountFound()
	{
		loginNameText.setText("");
		loginPasswordText.setText("");
		JOptionPane.showMessageDialog(null, "More than one account with that username, contact admins.", "ERROR", 
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void accountAlreadyExists()
	{
		JOptionPane.showMessageDialog(null, "Account already exists.", "ERROR", 
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void accountCreated()
	{
		switchCards("Login Panel");
		loginNameText.setText("");
		loginPasswordText.setText("");
	}
	
	public void loadCharacterInfo(String[] characterList)
	{
		if(characters.size() > 0)
		{
			characters.clear();
		}
		//Each element in the characters array holds a line of information
		//about a specific character, each of the fields separated
		//by a space (or some other delimiter)
		
		//Starts at 2, because the first element in this list contains the string "loginSuccess"
		//and the second element is the account ID
		accountID = characterList[1];
		for(int x = 2; x < characterList.length; x++)
		{
			characters.add(characterList[x]);
		}
	}
	
	public void clearCharSelectPanel()
	{
		charSelectPanel = new JPanel();
	}
	
	public void connectToServer()
	{
		client = new ChatClient(this);
        try {
			client.start();
		} 
        catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
