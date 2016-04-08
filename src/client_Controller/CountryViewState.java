package client_Controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client_Model.Location;
import client_Model.OtherPlayer;
import client_Model.Player;
import client_View.GamePanel;

public class CountryViewState extends IState
{
	ArrayList<Location> locations;
	BufferedImage mapImage;
	int xOffset, yOffset = 0;
	double randomEncounterChance = 0.0;
	boolean showMap;
	boolean charSheetOpen = false;
	boolean optionsMenuOpen = false;
	int levelUpStrCount, levelUpDexCount, levelUpConCount = 0;
	int levelUpIntCount, levelUpWilCount, levelUpLckCount = 0;
	
	boolean keepMoving = false;
	
	JPanel characterSheetPanel, optionsPanel;
    JTextField textField = new JTextField(40);
    JTextArea messageArea = new JTextArea(8, 40);
    JScrollPane scrollPane;
	
	public CountryViewState(Player p, StateMachine s)
	{
		super(p, s);
		locations = new ArrayList<Location>();
		getLocations();
		
		String mapLocation = "resources/Maps/mtStart.png";
    	mapImage = null;
    	showMap = false;

		try {
			mapImage = ImageIO.read(new File(mapLocation));
        } 
		catch (IOException ioe) {
            System.out.println("Unable to load image file.");
        }
		loadMap();
		
		//Message area displays the messages
	    messageArea.setEditable(false);
	    //Text field is where the user types his/her messages
		textField.setEditable(true);
		//Creates the message area, and adds it to a scroll pane so the user can look
		//through previous messages
		messageArea.setBounds(0, 0, 400, 140);
	    scrollPane = new JScrollPane(messageArea);
	    scrollPane.setBounds(5, 7*GamePanel.HEIGHT/10, 400, 140);
	    
	    textField.setBounds(5, 7*GamePanel.HEIGHT/10 + 145, 400, 50);

        // Adds the listener to the text field so the enter button will send the message
        textField.addActionListener(new ActionListener() {
            /**
             * Responds to pressing the enter key in the textfield by sending
             * the contents of the text field to the server. Then clear
             * the text area in preparation for the next message.
             */
            public void actionPerformed(ActionEvent e) {
            	System.out.println("Sending this message...: " + textField.getText());
            	sm.client.sendMessage("MESSAGE#" + player.getName() + "#" + textField.getText());
                textField.setText("");
                sm.doRequestFocus();
            }
        });
        
        characterSheetPanel = new JPanel();
		characterSheetPanel.setBounds(GamePanel.WIDTH/2, GamePanel.HEIGHT/8, 3*GamePanel.WIDTH/8, 3*GamePanel.HEIGHT/4);
		characterSheetPanel.setBackground(Color.LIGHT_GRAY);
		
		optionsPanel = new JPanel();
		optionsPanel.setLayout(null);
		optionsPanel.setBounds(GamePanel.WIDTH/4, GamePanel.HEIGHT/4, GamePanel.WIDTH/2, GamePanel.HEIGHT/2);
		optionsPanel.setBackground(Color.BLACK);
		
		JButton logoutButton = new JButton("Logout");
		logoutButton.setFont(new Font("TimesRoman", Font.BOLD, 20));
		logoutButton.setBounds(250, 120, 150, 100);
		logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logout();
            }
        });
		optionsPanel.add(logoutButton);
		
	}
	
	public void loadMap()
	{
		String fileName = "resources/Maps/mtStart.txt";
        String line = null;
        int width = 0, height = 0;
        int[] textMap = {};
        try {
            FileReader fileReader = 
                new FileReader(fileName);
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);
            
            //Reads the first line of the file, which contains the width and height
            if((line = bufferedReader.readLine()) != null)
            {
            	String[] specs = line.split(" ");
            	width = Integer.parseInt(specs[0]);
            	height = Integer.parseInt(specs[1]);
            }
            textMap = new int[width*height];
            //Reads the rest of the file, which contains the actual tile data
            int lineCount = 0;
            while((line = bufferedReader.readLine()) != null) {
            	String[] tiles = line.split(" ");
            	for(int x = 0; x < tiles.length; x++)
            	{
            		if(!tiles[x].equals("null"))
            		{
            			textMap[x + tiles.length*lineCount] = Integer.parseInt(tiles[x]);
            		}
            	}
            	lineCount++;
            }   
            bufferedReader.close();         
        }
        catch(Exception e) {
            e.printStackTrace();                
        }
		
		for(int y = 0; y < height; y++)
		{
			map.put(y, new HashMap<Integer, Tile>());
			for(int x = 0; x < width; x++)
			{
				map.get(y).put(x, new Tile(x, y, textMap[y*width + x]));
			}
		}
	}
	
    public void update()
    {
    	player.animationUpdate();
    	player.update();
        //checkPlayerIntersectLocation();
    }
    
    public void oncePerSecondUpdate()
    {
    	//player.update();
        checkPlayerIntersectLocation();
    }
    
    public void calculateRandomEncounterChance()
    {
    	Tile currentTile = map.get( player.getY() ).get( player.getX() );
    	System.out.println("Current tile: " + currentTile.getType());
    	if( (currentTile.getType() != 1) && !(currentTile.getType() > 31 && currentTile.getType() < 48) && 
    			!(currentTile.getType() > 64 && currentTile.getType() < 70))
    		randomEncounterChance += currentTile.getRandomEncounterChance();
    	else
    		randomEncounterChance = 0;
    	System.out.println("Random encounter chance: " + randomEncounterChance );
    	Random rand = new Random();
    	int chance = rand.nextInt(100);
    	if(chance < randomEncounterChance)
    		randomEncounter();
    }
    
    public void randomEncounter()
    {
		System.out.println("Random encounter!");
		randomEncounterChance = 0.0;
    	String[] args = { "CombatState", player.getLocation() };
		sm.changeState(args);
    }
  
    public void render(Graphics g)
    {
    	xOffset = (player.getX() * Tile.WIDTH) - GamePanel.WIDTH/2 + Tile.WIDTH/2 + player.getAnimationOffsetX();
    	yOffset = (player.getY() * Tile.HEIGHT) - GamePanel.HEIGHT/2 + Tile.HEIGHT/2 + player.getAnimationOffsetY();
		if(xOffset < 0) xOffset = 0;
		if(yOffset < 0) yOffset = 0;
    	
		if(!showMap){
	    	//This Takes only the part of the image that will be drawn
			//If you are too close to the edge it wont seem like your moving untill you get far enough away from the edge
	    	g.drawImage(mapImage.getSubimage(0+xOffset/2, 0+yOffset/2, GamePanel.WIDTH/2, GamePanel.HEIGHT/2), 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
	    	//g.drawImage(mapImage, 0-xOffset, 0-yOffset, GamePanel.WIDTH*4, GamePanel.HEIGHT*4, null);
	    	for(int y = 0; y < 100; y++)
	    	{
	    		for(int x = 0; x < 200; x++)
	    		{
	    	    	//g.drawString(String.valueOf(map.get(y).get(x).getType()), x*40-xOffset, y*40-yOffset);
	    		}
	    	}
			/*
			 * The next three are just so that there is a fuller map to play on, instead
			 * of starting in the top left corner of the map.
			 
			g.drawImage(mapImage, 0-xOffset-mapImage.getWidth(null), 0-yOffset, mapImage.getWidth(null), mapImage.getHeight(null), null);
			g.drawImage(mapImage, 0-xOffset, 0-yOffset-mapImage.getHeight(null), mapImage.getWidth(null), mapImage.getHeight(null), null);
			g.drawImage(mapImage, 0-xOffset-mapImage.getWidth(null), 0-yOffset-mapImage.getHeight(null), mapImage.getWidth(null), mapImage.getHeight(null), null);
			*/
	
	    	player.draw(g);
	    	g.setColor(Color.lightGray);
	    	g.fillRect(0, 0, 300, 120);
	    	g.setColor(Color.black);
	    	g.drawString("Name: " + player.getName() + ", level " + player.getLevel(), 10, 15);
	    	if(player.getPointsToSpend() > 0)
	    		g.drawString("(points to spend)", 160, 15);
	    	g.drawString("HP: " + player.getHealth(), 10, 35);
	    	g.drawString("Mana: " + player.getMana(), 10, 55);
	    	g.drawString("Coords: " + player.getX() + ", " + player.getY(), 10, 75);
	    	g.drawString("Exp: " + player.getExperience() + "/" + player.getExpToNextLevel(), 10, 95);
	    	
	    	drawOtherPlayers(g);
	    	drawLocations(g);
		}
		else{
			g.drawImage(mapImage, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		}
    }
    
    public void drawOtherPlayers(Graphics g)
    {
    	if(StateMachine.otherPlayers.size() > 0)
    	{
    		for(int i = 0; i < StateMachine.otherPlayers.size(); i++)
    		{
    			OtherPlayer other = StateMachine.otherPlayers.get(i);
    			
    			if(!other.getName().equals(player.getName()))
    			{
    				g.setColor(Color.BLACK);
	    			g.fillRect(other.getX()*40-xOffset, 
	    					other.getY()*40-yOffset, 
	    					other.getWidth(), 
	    					other.getHeight());
//    				
	    			g.setColor(Color.WHITE);
	    			g.drawString(other.getName(), other.getX()*40-xOffset, other.getY()*40-yOffset+10);
    			}
    		}
    	}
    }
    
    public void drawLocations(Graphics g)
    {
    	if(locations.size() > 0)
    	{
    		g.setColor(Color.magenta);
    		for(int i = 0; i < locations.size(); i++)
    		{
    			g.fillRect(locations.get(i).getX()*40-xOffset, locations.get(i).getY()*40-yOffset, 
    					Location.WIDTH*40, Location.HEIGHT*40);
//    			g.fillRect(locations.get(i).getX(), locations.get(i).getY(), 
//    					Location.WIDTH, Location.HEIGHT);
    		}
    	}
    }
    
    public void setUpCharacterSheet()
    {
    	characterSheetPanel.removeAll();
    	characterSheetPanel.setLayout(null);
    	
    	JLabel blankLine = new JLabel("\n");
    	JLabel charName = new JLabel(player.getName() + ", level " + player.getLevel() + " " + player.getPlayerClass());
    	JLabel health = new JLabel("Health: " + player.getHealth() + "/" + player.getMaxHealth());
    	JLabel mana = new JLabel("Mana: " + player.getMana() + "/" + player.getMaxMana());
    	JLabel strength = new JLabel("Strength: ");
    	JLabel dexterity = new JLabel("Dexterity: ");
    	JLabel constitution = new JLabel("Constitution: ");
    	JLabel intelligence = new JLabel("Intelligence: ");
    	JLabel willpower = new JLabel("Willpower: ");
    	JLabel luck = new JLabel("Luck: ");
    	JLabel strengthValue = new JLabel("" + player.getStrength());
    	JLabel dexterityValue = new JLabel("" + player.getDexterity());
    	JLabel constitutionValue = new JLabel("" + player.getConstitution());
    	JLabel intelligenceValue = new JLabel("" + player.getIntelligence());
    	JLabel willpowerValue = new JLabel("" + player.getWillpower());
    	JLabel luckValue = new JLabel("" + player.getLuck());
    	JLabel pointsToSpend = new JLabel("Points to spend: " + player.getPointsToSpend());
    	
    	JButton strengthPlusButton = new JButton("+");
    	strengthPlusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStat("Str");
            }
        });
    	
    	JButton strengthMinusButton = new JButton("-");
    	strengthMinusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subtractStat("Str");
            }
        });
    	
    	JButton dexterityPlusButton = new JButton("+");
    	dexterityPlusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStat("Dex");
            }
        });
    	
    	JButton dexterityMinusButton = new JButton("-");
    	dexterityMinusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	subtractStat("Dex");
            }
        });
    	
    	JButton constitutionPlusButton = new JButton("+");
    	constitutionPlusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStat("Con");
            }
        });
    	
    	JButton constitutionMinusButton = new JButton("-");  
    	constitutionMinusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	subtractStat("Con");
            }
        });
    	  	
    	JButton intelligencePlusButton = new JButton("+");
    	intelligencePlusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStat("Int");
            }
        });
    	
    	JButton intelligenceMinusButton = new JButton("-");
    	intelligenceMinusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	subtractStat("Int");
            }
        });
    	
    	JButton willpowerPlusButton = new JButton("+");
    	willpowerPlusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStat("Wil");
            }
        });
    	
    	JButton willpowerMinusButton = new JButton("-");
    	willpowerMinusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	subtractStat("Wil");
            }
        });
    	
    	JButton luckPlusButton = new JButton("+");
    	luckPlusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStat("Lck");
            }
        });
    	
    	JButton luckMinusButton = new JButton("-");
    	luckMinusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	subtractStat("Lck");
            }
        });
    	
    	
    	JButton spendPointButton = new JButton("Spend points!");
    	spendPointButton.setBounds(120, 440, 200, 90);
    	spendPointButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spendPoints();
            }
        });
    	
    	charName.setBounds(10, 5, 400, 30);
    	characterSheetPanel.add(charName);
    	health.setBounds(10, 35, 400, 30);
    	characterSheetPanel.add(health);
    	mana.setBounds(10, 65, 400, 30);
    	characterSheetPanel.add(mana);
    	pointsToSpend.setBounds(10, 120, 400, 30);
    	characterSheetPanel.add(pointsToSpend);
    	
    	strength.setBounds(10, 160, 200, 30);
    	characterSheetPanel.add(strength);
    	dexterity.setBounds(10, 200, 200, 30);
    	characterSheetPanel.add(dexterity);
    	constitution.setBounds(10, 240, 200, 30);
    	characterSheetPanel.add(constitution);
    	intelligence.setBounds(10, 280, 200, 30);
    	characterSheetPanel.add(intelligence);
    	willpower.setBounds(10, 320, 200, 30);
    	characterSheetPanel.add(willpower);
    	luck.setBounds(10, 360, 240, 30);
    	characterSheetPanel.add(luck);
    	
    	strengthValue.setBounds(220, 160, 200, 30);
    	characterSheetPanel.add(strengthValue);
    	dexterityValue.setBounds(220, 200, 200, 30);
    	characterSheetPanel.add(dexterityValue);
    	constitutionValue.setBounds(220, 240, 200, 30);
    	characterSheetPanel.add(constitutionValue);
    	intelligenceValue.setBounds(220, 280, 200, 30);
    	characterSheetPanel.add(intelligenceValue);
    	willpowerValue.setBounds(220, 320, 200, 30);
    	characterSheetPanel.add(willpowerValue);
    	luckValue.setBounds(220, 360, 200, 30);
    	characterSheetPanel.add(luckValue);
    	
    	boolean pointsBeingSpent = false;
    	if((levelUpStrCount > 0) || (levelUpDexCount > 0) || (levelUpConCount > 0) ||
    			(levelUpIntCount > 0) || (levelUpWilCount > 0) || (levelUpLckCount > 0))
    		pointsBeingSpent = true;
    	
    	if(player.getPointsToSpend() > 0 || pointsBeingSpent)
    	{
    		strengthPlusButton.setBounds(295, 160, 30, 30);
        	characterSheetPanel.add(strengthPlusButton);
        	strengthMinusButton.setBounds(260, 160, 30, 30);
        	characterSheetPanel.add(strengthMinusButton);
        	
        	dexterityPlusButton.setBounds(295, 200, 30, 30);
        	characterSheetPanel.add(dexterityPlusButton);
        	dexterityMinusButton.setBounds(260, 200, 30, 30);
        	characterSheetPanel.add(dexterityMinusButton);
        	
        	constitutionPlusButton.setBounds(295, 240, 30, 30);
        	characterSheetPanel.add(constitutionPlusButton);
        	constitutionMinusButton.setBounds(260, 240, 30, 30);
        	characterSheetPanel.add(constitutionMinusButton);
        	
        	intelligencePlusButton.setBounds(295, 280, 30, 30);
        	characterSheetPanel.add(intelligencePlusButton);
        	intelligenceMinusButton.setBounds(260, 280, 30, 30);
        	characterSheetPanel.add(intelligenceMinusButton);
        	
        	willpowerPlusButton.setBounds(295, 320, 30, 30);
        	characterSheetPanel.add(willpowerPlusButton);
        	willpowerMinusButton.setBounds(260, 320, 30, 30);
        	characterSheetPanel.add(willpowerMinusButton);
        	
        	luckPlusButton.setBounds(295, 360, 30, 30);
        	characterSheetPanel.add(luckPlusButton);
        	luckMinusButton.setBounds(260, 360, 30, 30);
        	characterSheetPanel.add(luckMinusButton);
    	}
    	
    	JLabel strCount = new JLabel("+" + levelUpStrCount);
		JLabel dexCount = new JLabel("+" + levelUpDexCount);
		JLabel conCount = new JLabel("+" + levelUpConCount);
		JLabel intCount = new JLabel("+" + levelUpIntCount);
		JLabel wilCount = new JLabel("+" + levelUpWilCount);
		JLabel lckCount = new JLabel("+" + levelUpLckCount);
		
    	if(levelUpStrCount > 0)
    	{
    		strCount.setBounds(335, 160, 30, 30);
        	characterSheetPanel.add(strCount);
    	}
    	else
    	{
    		characterSheetPanel.remove(strCount);
    	}
    	
    	if(levelUpDexCount > 0)
    	{
    		dexCount.setBounds(335, 200, 30, 30);
        	characterSheetPanel.add(dexCount);
    	}
    	else
    	{
    		characterSheetPanel.remove(dexCount);
    	}
    	
    	if(levelUpConCount > 0)
    	{
    		conCount.setBounds(335, 240, 30, 30);
        	characterSheetPanel.add(conCount);
    	}
    	else
    	{
    		characterSheetPanel.remove(conCount);
    	}
    	
    	if(levelUpIntCount > 0)
    	{
    		intCount.setBounds(335, 280, 30, 30);
        	characterSheetPanel.add(intCount);
    	}
    	else
    	{
    		characterSheetPanel.remove(intCount);
    	}
    	
    	if(levelUpWilCount > 0)
    	{
    		wilCount.setBounds(335, 320, 30, 30);
        	characterSheetPanel.add(wilCount);
    	}
    	else
    	{
    		characterSheetPanel.remove(wilCount);
    	}
    	
    	if(levelUpLckCount > 0)
    	{
    		lckCount.setBounds(335, 360, 30, 30);
        	characterSheetPanel.add(lckCount);
    	}
    	else
    	{
    		characterSheetPanel.remove(lckCount);
    	}
    	
    	if(pointsBeingSpent)
    	{
        	characterSheetPanel.add(spendPointButton);
    	}
    	else
    	{
    		characterSheetPanel.remove(spendPointButton);
    	}
    }
    
    public void addStat(String stat)
    {
    	if(player.getPointsToSpend() > 0)
    	{
	    	if(stat.equals("Str"))
	    		levelUpStrCount++;
	    	else if(stat.equals("Dex"))
	    		levelUpDexCount++;
	    	else if(stat.equals("Con"))
	    		levelUpConCount++;
	    	else if(stat.equals("Int"))
	    		levelUpIntCount++;
	    	else if(stat.equals("Wil"))
	    		levelUpWilCount++;
	    	else if(stat.equals("Lck"))
	    		levelUpLckCount++;
    	
	    	player.setPointsToSpend(player.getPointsToSpend()-1);
    	}
    	setUpCharacterSheet();
    }
    
    public void subtractStat(String stat)
    {
    	if(stat.equals("Str"))
    	{
    		if(levelUpStrCount > 0)
    		{
    			levelUpStrCount--;
    			player.setPointsToSpend(player.getPointsToSpend()+1);
    		}
    	}
    	else if(stat.equals("Dex"))
    	{
    		if(levelUpDexCount > 0)
    		{
    			levelUpDexCount--;
    			player.setPointsToSpend(player.getPointsToSpend()+1);
    		}
    	}
    	else if(stat.equals("Con"))
    	{
    		if(levelUpConCount > 0)
    		{
    			levelUpConCount--;
    			player.setPointsToSpend(player.getPointsToSpend()+1);
    		}
    	}
    	else if(stat.equals("Int"))
    	{
    		if(levelUpIntCount > 0)
    		{
    			levelUpIntCount--;
    			player.setPointsToSpend(player.getPointsToSpend()+1);
    		}
    	}
    	else if(stat.equals("Wil"))
    	{
    		if(levelUpWilCount > 0)
    		{
    			levelUpWilCount--;
    			player.setPointsToSpend(player.getPointsToSpend()+1);
    		}
    	}
    	else if(stat.equals("Lck"))
    	{
    		if(levelUpLckCount > 0)
    		{
    			levelUpLckCount--;
    			player.setPointsToSpend(player.getPointsToSpend()+1);
    		}
    	}
    	
    	setUpCharacterSheet();
    }
    
    public void spendPoints()
    {
    	player.setStrength(player.getStrength() + levelUpStrCount);
    	player.setDexterity(player.getDexterity() + levelUpDexCount);
    	player.setConstitution(player.getConstitution() + levelUpConCount);
    	player.setIntelligence(player.getIntelligence() + levelUpIntCount);
    	player.setWillpower(player.getWillpower() + levelUpWilCount);
    	player.setLuck(player.getLuck() + levelUpLckCount);
    	levelUpStrCount = 0;
		levelUpDexCount = 0;
		levelUpConCount = 0;
		levelUpIntCount = 0;
		levelUpWilCount = 0;
    	levelUpLckCount = 0;

    	setUpCharacterSheet();
        sm.client.sendMessage("UPDATECHARINFO#" + player.getAllCharInfo());
    }
    
    public void openCharacterSheet()
    {
    	charSheetOpen = true;
    	setUpCharacterSheet();
		sm.addComponent(characterSheetPanel);
    }
    
    public void closeCharacterSheet()
    {
    	charSheetOpen = false;
		sm.removeComponent(characterSheetPanel);
    }
    
    public void openOptionsMenu()
    {
		sm.addComponent(optionsPanel);
    }
    
    public void closeOptionsMenu()
    {
		sm.removeComponent(optionsPanel);
    }
    
    public void logout()
    {
        sm.client.sendMessage("UPDATECHARINFO#" + player.getAllCharInfo());
    	sm.disposeWindow();
    	sm.client.sendMessage("LOGOUT#" + player.getName());
    }
  
    public void onEnter()
    {
	    sm.addComponent(textField);
	    sm.addComponent(scrollPane);
        textField.setText("");
    }
  
    public void onExit()
    {
	    sm.removeComponent(textField);
	    sm.removeComponent(scrollPane);
    }
    
    public void getLocations()
    {
    	StateMachine.client.sendMessage("GETLOCATIONS");
    }
    
    public void loadInfo(String[] locs)
    {
    	for(int i = 1; i < locs.length; i++)
    	{
    		String[] thisLoc = locs[i].split(" ");
    		locations.add(new Location(thisLoc[0], Integer.parseInt(thisLoc[1]), 
    						Integer.parseInt(thisLoc[2])));
    	}
    	onEnter();
    }
    
    public void keyPressed(int keyCode){
    	if(keyCode == KeyEvent.VK_W){
    		if(map.get(player.getY()-1).get(player.getX()).getType() >= 0)
    			player.moveUp();
		}
    	if(keyCode == KeyEvent.VK_A){
    		if(map.get(player.getY()).get(player.getX()-1).getType() >= 0)
    			player.moveLeft();
		}
    	if(keyCode == KeyEvent.VK_S){
    		if(map.get(player.getY()+1).get(player.getX()).getType() >= 0)
    			player.moveDown();
		}
    	if(keyCode == KeyEvent.VK_D){
    		if(map.get(player.getY()).get(player.getX()+1).getType() >= 0)
    			player.moveRight();
		}
    	if(keyCode == KeyEvent.VK_SPACE){
    		
		}
    	if(keyCode == KeyEvent.VK_ENTER){
    		textField.requestFocus();
		}
    	if(keyCode == KeyEvent.VK_M){
    		showMap = !showMap;
		}
    	if(keyCode == KeyEvent.VK_C){
    		if(!charSheetOpen)
    			openCharacterSheet();
    		else
    			closeCharacterSheet();
    	}
    	if(keyCode == KeyEvent.VK_ESCAPE){
    		if(!optionsMenuOpen)
    			openOptionsMenu();
    		else
    			closeOptionsMenu();
		}
    	if(keyCode == KeyEvent.VK_F){
    		player.useInventoryItem(0);
		}
    }
    
    public void keyReleased(int keyCode){
    	if(keyCode == KeyEvent.VK_W){
		}
    	if(keyCode == KeyEvent.VK_A){

		}
    	if(keyCode == KeyEvent.VK_S){

		}
    	if(keyCode == KeyEvent.VK_D){

		}
    }
    
    public void checkPlayerIntersectLocation()
    {
    	for(int i = 0; i < locations.size(); i++)
    	{
    		if(player.getPlayerRect().intersects(locations.get(i).getLocRect()))
    		{
    			//Switch to local view state, pass name of the location we're entering
    			String[] args = { "LocalViewState", locations.get(i).getName() };
    			sm.changeState(args);
    		}
    	}
    }
}
