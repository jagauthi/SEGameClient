package client_View;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
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
    private JPanel modifyAccountPanel;
    private JPanel charSelectPanel;
    private JPanel createNewCharacterPanel;

    //Info that is collected in the Login Panel
    private JTextField loginNameText;
    private JPasswordField loginPasswordText;
    
    //Info that is collected in the Create Account Panel
    private JTextField createNameText;
    private JTextField createEmailText;
    private JPasswordField createPasswordText;
    private JPasswordField createVerifyPasswordText;
    private JTextField createSecAnswer1Text;
    private JTextField createSecAnswer2Text;
    JComboBox secQuestions1;
    JComboBox secQuestions2;
    JButton createButton;
    JButton modifyButton;
    
    private JTextField modifyNameText;
    private JTextField modifyEmailText;
    private JPasswordField modifyPasswordText;
    private JPasswordField modifyVerifyPasswordText;
    private JTextField modifySecAnswer1Text;
    private JTextField modifySecAnswer2Text;
    
    String[] acctInfo;
    int charSelected = 0;
    JButton[] charSelectButtons;
    JLabel[] charLabels;
    String[] characterNames = new String[5];
    
    //Info that is collected in the Create New Character Panel
    JTextField newCharacterNameText;
    static JLabel pointsLeft;
    static int pointsRemaining;
    JRadioButton male;
    JRadioButton female;
    JRadioButton warrior;
    JRadioButton rogue;
    JRadioButton mage;
    StatField strengthField;
    StatField dexterityField;
    StatField constitutionField;
    StatField intelligenceField;
    StatField willpowerField;
    StatField luckField;
    
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
		modifyAccountPanel = new JPanel();
		charSelectPanel = new JPanel();
		createNewCharacterPanel = new JPanel();
		
	    pointsLeft = new JLabel();

		initConnectPanel();
		initLoginPanel();
		initCreateAccountPanel();
		//initCreateNewCharacterPanel();
		
		cards.add(connectPanel, "Connect Panel");
		cards.add(loginPanel, "Login Panel");
		cards.add(createAccountPanel, "Create Account Panel");
		cards.add(modifyAccountPanel, "Modify Account Panel");
		cards.add(charSelectPanel, "Char Select Panel");
		cards.add(createNewCharacterPanel, "Create New Character Panel");
        
        frame.add(cards, BorderLayout.CENTER);
        
        frame.setTitle("Launcher");
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        //switchCards("Create New Character Panel");
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
        createButton = new JButton();
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
        createButton.setActionCommand("create");
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
	
	public void initModifyAccountPanel()
	{
		JLabel modifyNameLabel = new JLabel();
		JLabel modifyEmailLabel = new JLabel();
		JLabel modifyPasswordLabel = new JLabel();
		JLabel modifyVerifyPasswordLabel = new JLabel();
        
		modifyNameText = new JTextField();
		modifyEmailText = new JTextField();
		modifyPasswordText = new JPasswordField();
		modifyVerifyPasswordText = new JPasswordField();
		modifySecAnswer1Text = new JTextField();
		modifySecAnswer2Text = new JTextField();
		modifyButton = new JButton();
        JButton modifyAccountBackButton = new JButton();
        
        String[] questions1 = { "What is your mothers maiden name", "Sec Question 2", "Sec Question 3" };
        String[] questions2 = { "Name of your first pet", "Sec Question 2", "Sec Question 3" };
        secQuestions1 = new JComboBox(questions1);
        secQuestions1.setSelectedIndex(0);
        secQuestions2 = new JComboBox(questions2);
        secQuestions2.setSelectedIndex(0);
        
        modifyNameLabel.setText("Username: ");
        modifyEmailLabel.setText("Email: ");
        modifyPasswordLabel.setText("Password: ");
        modifyVerifyPasswordLabel.setText("Re-enter password: ");
        modifyNameText.setPreferredSize(new Dimension(200, 40));
        modifyEmailText.setPreferredSize(new Dimension(200, 40));
        modifyPasswordText.setPreferredSize(new Dimension(200, 40));
        modifyVerifyPasswordText.setPreferredSize(new Dimension(200, 40));
        modifySecAnswer1Text.setPreferredSize(new Dimension(200, 40));
        modifySecAnswer2Text.setPreferredSize(new Dimension(200, 40));
        secQuestions1.setPreferredSize(new Dimension(300, 40));
        secQuestions2.setPreferredSize(new Dimension(300, 40));
        
        modifyButton.setText("Modify!");
        //createButton.setFont(normalFont);
        modifyAccountBackButton.setText("<< Back");
        
        modifyButton.setPreferredSize(new Dimension(100, 50));
        modifyButton.setActionCommand("modify");
        modifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	modifyAccount(evt);
            }
        });

        modifyAccountBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	modifyAccountGoBack(evt);
            }
        });
        
        modifyAccountPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        
        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        modifyAccountPanel.add(modifyAccountBackButton, c);
        

        c.anchor = GridBagConstraints.CENTER;
        
        c.gridx = 1;
        c.gridy = 1;
        modifyAccountPanel.add(modifyNameLabel, c);
        
        c.gridx = 1;
        c.gridy = 2;
        modifyAccountPanel.add(modifyEmailLabel, c);
        
        c.gridx = 1;
        c.gridy = 3;
        modifyAccountPanel.add(modifyPasswordLabel, c);
        
        c.gridx = 1;
        c.gridy = 4;
        modifyAccountPanel.add(modifyVerifyPasswordLabel, c);
        
        c.gridx = 1;
        c.gridy = 5;
        modifyAccountPanel.add(secQuestions1, c);
        
        c.gridx = 1;
        c.gridy = 6;
        modifyAccountPanel.add(secQuestions2, c);
        
        c.gridx = 2;
        c.gridy = 1;
        modifyAccountPanel.add(modifyNameText, c);
        
        c.gridx = 2;
        c.gridy = 2;
        modifyAccountPanel.add(modifyEmailText, c);
        
        c.gridx = 2;
        c.gridy = 3;
        modifyAccountPanel.add(modifyPasswordText, c);
        
        c.gridx = 2;
        c.gridy = 4;
        modifyAccountPanel.add(modifyVerifyPasswordText, c);
        
        c.gridx = 2;
        c.gridy = 5;
        modifyAccountPanel.add(modifySecAnswer1Text, c);
        
        c.gridx = 2;
        c.gridy = 6;
        modifyAccountPanel.add(modifySecAnswer2Text, c);

        c.gridx = 2;
        c.gridy = 7;
        modifyAccountPanel.add(modifyButton, c);
        
	}
	
	public void initCharSelectPanel()
	{
		loginNameText.setText("");
		loginPasswordText.setText("");
		charSelectPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        
		charSelectButtons = new JButton[5];
        charLabels = new JLabel[5];
        JButton[] deleteButtons = new JButton[5];
        int lastY = 1;
		for(int x = 0; x < characters.size(); x++)
		{
			final int selectVariable = x;
			String[] charInfo = characters.get(x).split(" ");
	        characterNames[x] = charInfo[0];
			charSelectButtons[x] = new JButton();
			charSelectButtons[x].setPreferredSize(new Dimension(200, 200));
			charSelectButtons[x].addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                selectChar(selectVariable);
	            }
	        });
			charLabels[x] = new JLabel();
			charLabels[x].setText(charInfo[0] + ", a level " + charInfo[2] + "\n" + charInfo[1]);
			
			deleteButtons[x] = new JButton("Delete");
			deleteButtons[x].setPreferredSize(new Dimension(200, 200));
			final int selected = x;
			deleteButtons[x].addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                deleteChar(selected);
	            }
	        });
	        
	        c.gridx = 0;
	        c.gridy = x + 1;
	        charSelectPanel.add(charSelectButtons[x], c);
	        
	        c.gridx = 1;
	        c.gridy = x + 1;
	        charSelectPanel.add(charLabels[x], c);
	        
	        c.gridx = 2;
	        c.gridy = x + 1;
	        charSelectPanel.add(deleteButtons[x], c);
	        
	        lastY++;
		}
		
		JButton accountManagementButton = new JButton("Account Management");
		accountManagementButton.setPreferredSize(new Dimension(100, 50));
		accountManagementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getSecurityInfo(evt);
            }
        });
		
		JButton logoutButton = new JButton();
		logoutButton.setText("Logout");
        logoutButton.setPreferredSize(new Dimension(100, 50));
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logout(evt);
            }
        });
        
        JButton playButton = new JButton();
        playButton.setText("Play!");
        playButton.setPreferredSize(new Dimension(100, 50));
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	playGame();
            }
        });
        
        c.gridx = 1;
        c.gridy = 0;
        charSelectPanel.add(accountManagementButton, c);
		
        c.gridx = 2;
        c.gridy = 0;
        charSelectPanel.add(logoutButton, c);
        
        c.gridx = 3;
        c.gridy = 6;
        charSelectPanel.add(playButton, c);
        
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

		pointsRemaining = 10;
	    pointsLeft = new JLabel("Points remaining: " + pointsRemaining);
		
////////Name Panel
		JLabel newCharacterNameLabel = new JLabel();
        newCharacterNameText = new JTextField();
        JLabel dontUseYourAccountNameLabel = new JLabel();
        JButton createNewCharacterButton = new JButton();
        
        newCharacterNameLabel.setText("Name: ");
        newCharacterNameText.setPreferredSize(new Dimension(300, 30));
        
        dontUseYourAccountNameLabel.setText("Do not use your account name");
        
        createNewCharacterButton.setText("Create!");
        createNewCharacterButton.setPreferredSize(new Dimension(100, 100));
        createNewCharacterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createCharacter(evt);
            }
        });
        
    	namePanel.setBorder(BorderFactory.createTitledBorder(""));//(Don't use your account name)"));
        namePanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        namePanel.add(newCharacterNameLabel, c);

        c.weightx = 1.0;
        c.gridx = 1;
        c.gridy = 0;
        namePanel.add(newCharacterNameText, c);
        
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 1;
        c.gridy = 1;
        namePanel.add(dontUseYourAccountNameLabel, c);
        
////////Sexy Panel
        male = new JRadioButton("Male", true);
    	female = new JRadioButton("Female");
    	
    	ButtonGroup sexGroup = new ButtonGroup();
    	sexGroup.add(male);
    	sexGroup.add(female);
    	
    	sexPanel.setBorder(BorderFactory.createTitledBorder("Gender"));
    	sexPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.WEST;
        
        c.gridx = 0;
        c.gridy = 0;
        sexPanel.add(male, c);
        
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 1;
        sexPanel.add(female, c);
        
/////////Class Panel
        warrior = new JRadioButton("Warrior", true);
    	rogue = new JRadioButton("Rogue");
    	mage = new JRadioButton("Mage");
    	
    	ButtonGroup classGroup = new ButtonGroup();
    	classGroup.add(warrior);
    	classGroup.add(rogue);
    	classGroup.add(mage);
    	
    	classPanel.setBorder(BorderFactory.createTitledBorder("Class"));
    	classPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.WEST;
        
        c.gridx = 0;
        c.gridy = 0;
        classPanel.add(warrior, c);
        
        c.gridx = 0;
        c.gridy = 1;
        classPanel.add(rogue, c);
        
        c.gridx = 0;
        c.gridy = 2;
        classPanel.add(mage, c);
        
        //LeftPanel
        JPanel leftPanel = new JPanel();
        leftPanel.setBorder(BorderFactory.createTitledBorder(""));
        leftPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        leftPanel.add(sexPanel, c);
        
        c.gridx = 0;
        c.gridy = 1;
        leftPanel.add(classPanel, c);
        
        
/////////Stat Panel

        strengthField = new StatField("Strength");
        dexterityField = new StatField("Dexterity");
        constitutionField = new StatField("Constitution");
        intelligenceField = new StatField("Intelligence");
        willpowerField = new StatField("Willpower");
        luckField = new StatField("Luck");
    	
    	statPanel.setBorder(BorderFactory.createTitledBorder(""));
    	statPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.WEST;
        
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
        JButton backButton = new JButton("Back");
        backButton.setMinimumSize(new Dimension(WIDTH/5-10, HEIGHT/5-20));
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backToCharSelect();
            }
        });
        
        createNewCharacterPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();

        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.NONE;
        
        c.gridx=0;
        c.gridy=0;
        createNewCharacterPanel.add(backButton, c);
        
        c.gridx = 1;
        c.gridy = 0;
        namePanel.setMinimumSize(new Dimension((4*WIDTH)/5-10, HEIGHT/5));
        createNewCharacterPanel.add(namePanel, c);
        
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 1;
        leftPanel.setMinimumSize(new Dimension((WIDTH)/5-15, (3*HEIGHT)/5));
        createNewCharacterPanel.add(leftPanel, c);

        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 1;
        c.gridy = 1;
        statPanel.setMinimumSize(new Dimension((4*WIDTH)/5-10, (3*HEIGHT)/5));
        createNewCharacterPanel.add(statPanel, c);
        
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 1;
        c.gridy = 2;
        createNewCharacterButton.setMinimumSize(new Dimension((WIDTH)/5, HEIGHT/5));
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
			client.sendMessage("LOGIN#" + username + "#" + password);
		}
	}
	
	private void createAccount(ActionEvent evt)
	{
		if(createButton.getActionCommand().equals("create"))
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
			if(!checkUsername(username))
			{
				createNameText.setText("");
				JOptionPane.showMessageDialog(null, "Please enter a username with only letters.", "ERROR", 
						JOptionPane.INFORMATION_MESSAGE);
			}
			else if(username.equals(""))
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
				client.sendMessage("CREATEACCOUNT#" + username + "#" 
						+ password + "#" + email + "#" 
						+ securityQuestion1 + "#" 
						+ securityAnswer1 + "#"
						+ securityQuestion2 + "#"
						+ securityAnswer2);
			}
		}
	}
	
	private void modifyAccount(ActionEvent evt)
	{
		if(modifyButton.getActionCommand().equals("modify"))
		{
			String username = modifyNameText.getText();
			String email = modifyEmailText.getText();
			String password = String.valueOf(modifyPasswordText.getPassword());
			String passwordVerify = String.valueOf(modifyVerifyPasswordText.getPassword());
			String securityQuestion1 = (String) secQuestions1.getSelectedItem();
			String securityQuestion2 = (String) secQuestions2.getSelectedItem();
			String securityAnswer1 = modifySecAnswer1Text.getText();
			String securityAnswer2 = modifySecAnswer2Text.getText();
			
			if(!password.equals(passwordVerify))
			{
				System.out.println("Passwords do not match");
			}
			if(email.equals("") || email.indexOf("@") < 0 || email.indexOf(".") < 0)
			{
				modifyNameText.setText("");
				modifyEmailText.setText("");
				modifyPasswordText.setText("");
				modifyVerifyPasswordText.setText("");
				modifySecAnswer1Text.setText("");
				modifySecAnswer2Text.setText("");
				
				JOptionPane.showMessageDialog(null, "Please enter an email.", "ERROR", 
						JOptionPane.INFORMATION_MESSAGE);
			}
			else if(password.equals(""))
			{
				modifyNameText.setText("");
				modifyEmailText.setText("");
				modifyPasswordText.setText("");
				modifyVerifyPasswordText.setText("");
				modifySecAnswer1Text.setText("");
				modifySecAnswer2Text.setText("");
				
				JOptionPane.showMessageDialog(null, "Please enter a password.", "ERROR", 
						JOptionPane.INFORMATION_MESSAGE);
			}
			else if(passwordVerify.equals(""))
			{
				modifyNameText.setText("");
				modifyEmailText.setText("");
				modifyPasswordText.setText("");
				modifyVerifyPasswordText.setText("");
				modifySecAnswer1Text.setText("");
				modifySecAnswer2Text.setText("");
				
				JOptionPane.showMessageDialog(null, "Please verify password.", "ERROR", 
						JOptionPane.INFORMATION_MESSAGE);
			}
			else if(!password.equals(passwordVerify))
			{
				modifyPasswordText.setText("");
				modifyVerifyPasswordText.setText("");
				
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
				client.sendMessage("UPDATEACCOUNT#" + acctInfo[0] + "#" + 
						String.valueOf(createPasswordText.getPassword()) + "#" +
						createEmailText.getText() + "#" + 
						acctInfo[3] + "#" + 
						createSecAnswer1Text.getText() + "#" + 
						acctInfo[5] + "#" + 
						createSecAnswer2Text.getText());
			}
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
	
	private void modifyAccountGoBack(ActionEvent evt)
	{
		modifyAccountPanel.removeAll();
		switchCards("Char Select Panel");
	}
	
	private void goToCreateAccount(ActionEvent evt)
	{
		loginNameText.setText("");
		loginPasswordText.setText("");
		switchCards("Create Account Panel");
	}
	
	public void getSecurityInfo(ActionEvent evt)
	{
		client.sendMessage("GETSECURITYINFO#" + accountID);
	}
	
	public void manageAccount(String[] securityInfo)
	{
		for(int i = 1; i < securityInfo.length; i++)
			System.out.println(securityInfo[i]);
		//"securityInfo", username, password, email, secQuestion1, secAnswer1, secQuestion2, secAnswer2, macAddress, lastLoginTime
		acctInfo = new String[9];
		acctInfo[0] = securityInfo[1];
		acctInfo[1] = securityInfo[2];
		acctInfo[2] = securityInfo[3];
		acctInfo[3] = securityInfo[4];
		acctInfo[4] = securityInfo[5];
		acctInfo[5] = securityInfo[6];
		acctInfo[6] = securityInfo[7];
		acctInfo[7] = securityInfo[8];
		acctInfo[8] = securityInfo[9];
		
		Boolean correctSecAnswer = false;
		int count = 0;
		while(!correctSecAnswer && count < 4)
		{
			String secAnswer = JOptionPane.showInputDialog(null, acctInfo[3], "Security Question", JOptionPane.INFORMATION_MESSAGE);
			System.out.println(secAnswer);
			if(secAnswer == null)
				break;
			
			if(secAnswer.equals(acctInfo[4]))
			{
				correctSecAnswer = true;
				updateModifyAccountPanel(acctInfo);
			}
			else
			{
				secAnswer = JOptionPane.showInputDialog(null, acctInfo[5], "Security Question", JOptionPane.INFORMATION_MESSAGE);
				if(secAnswer.equals(acctInfo[6]))
				{
					correctSecAnswer = true;
					updateModifyAccountPanel(acctInfo);
				}
				count++;
			}
		}
		
		if(count == 4)
		{
			client.sendMessage("LOCKACCOUNT#" + accountID);
			switchCards("Login Panel");
		}	
	}
	
	public void updateModifyAccountPanel(String[] accountInfo)
	{
		initModifyAccountPanel();
		switchCards("Modify Account Panel");
		modifyNameText.setEditable(false);
		modifyNameText.setText(accountInfo[0]);
		modifyPasswordText.setText(accountInfo[1]);
		modifyVerifyPasswordText.setText(accountInfo[1]);
		modifyEmailText.setText(accountInfo[2]);
		secQuestions1.setSelectedIndex(0);
		modifySecAnswer1Text.setText(accountInfo[4]);
		secQuestions2.setSelectedIndex(1);
		modifySecAnswer2Text.setText(accountInfo[6]);
		
	}
	
	private void selectChar(int selecter)
	{
		System.out.println("Selecting " + characterNames[selecter]);
		charSelected = selecter;
	}
	
	public void createNewCharacter(ActionEvent evt)
	{
		createNewCharacterPanel.removeAll();
		initCreateNewCharacterPanel();
		switchCards("Create New Character Panel");
	}
	
	public void createCharacter(ActionEvent evt)
	{
		String charName = newCharacterNameText.getText();
		String chosenSex = "";
		String chosenClass = "";
		
		if(male.isSelected())
			chosenSex = "Male";
		else
			chosenSex = "Female";
		
		if(rogue.isSelected())
			chosenClass = "Rogue";
		else if(mage.isSelected())
			chosenClass = "Mage";
		else
			chosenClass = "Warrior";
		
		String str = strengthField.points.getText();
		String dex = dexterityField.points.getText();
		String con = constitutionField.points.getText();
		String intel = intelligenceField.points.getText();
		String wil = willpowerField.points.getText();
		String lck = luckField.points.getText();
		
		//name, accountID, class, gender, str, dex, con, int, wil, lck
		if(charName.equals(""))
		{
			newCharacterNameText.setText("");
			JOptionPane.showMessageDialog(null, "Please enter a character name.", "ERROR", 
					JOptionPane.INFORMATION_MESSAGE);
		}
		else if(!checkUsername(charName))
		{
			newCharacterNameText.setText("");
			JOptionPane.showMessageDialog(null, "Please enter a character name with only letters.", "ERROR", 
					JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			client.sendMessage("CREATECHAR#" + charName + "#" + accountID + 
								"#" + chosenClass + "#" + chosenSex + "#" + str +
								"#" + dex + "#" + con + "#" + intel + "#" + wil + "#" + lck);
		}
	}
	
	public void logout(ActionEvent evt)
	{
		charSelectPanel.removeAll();
		for(int i = 0; i < characterNames.length; i++)
			characterNames[i] = "";
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
		JOptionPane.showMessageDialog(null, "Account has been locked. Please call (719)352-7025, and tell him he to unlock your account.", "ERROR", 
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void accountBanned()
	{
		loginNameText.setText("");
		loginPasswordText.setText("");
		JOptionPane.showMessageDialog(null, "Account has been banned, probably because you're lame.", "ERROR", 
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
		charSelectPanel.removeAll();
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
	
	public void updateAcctInfo(ActionEvent evt)
	{
		
		
	}
	
	public void backToCharSelect()
	{
		switchCards("Char Select Panel");
	}
	
	public void deleteChar(int x)
	{
		client.sendMessage("DELETECHAR#" + characterNames[x] + "#" + accountID);
	}
	
	public void playGame()
	{
		client.sendMessage("PLAYGAME#" + characterNames[charSelected]);
	}
	
	public void intoGame(String[] playerInfo)
	{
		JFrame window = new JFrame("SWE Game");
 		window.setContentPane(new GamePanel(playerInfo, client));
 		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		window.setResizable(false);
 		window.pack();
 		window.setVisible(true);
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
	
	public boolean checkUsername(String name) {
	    char[] charArray = name.toCharArray();
	    for (char c : charArray) {
	        if(!Character.isLetter(c)) {
	            return false;
	        }
	    }
	    return true;
	}
}
