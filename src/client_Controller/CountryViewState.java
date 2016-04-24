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
import client_View.CharacterSheetPanel;
import client_View.GamePanel;
import client_View.HUDPanel;

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
	
//	boolean keepMoving = false;
	
	JPanel characterSheetPanel, optionsPanel;
	HUDPanel HUD;
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
		
		HUD = new HUDPanel(player, sm);
		sm.addComponent(HUD);
		
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
	
	    	player.draw(g);
	    	
	    	HUD.render(g);
	    	
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
    				
	    			System.out.print(other.getDirection());
    				if(other.getPlayerClass().equals("Mage"))
    				{
    					g.drawImage(sm.mageSprite[other.getDirection()], other.getX()*40-xOffset, other.getY()-yOffset, null); 
    				}
    				else if(other.getPlayerClass().equals("Warrior"))
    				{
    					g.setColor(Color.BLACK);
    	    			g.fillRect(other.getX()*40-xOffset, 
    	    					other.getY()*40-yOffset, 
    	    					other.getWidth(), 
    	    					other.getHeight());
    					g.drawImage(sm.warriorSprite[other.getDirection()], other.getX()*40-xOffset, other.getY()*40-yOffset, null);
    				}
    				else if(other.getPlayerClass().equals("Rogue"))
    				{
    					g.drawImage(sm.rogueSprite[other.getDirection()], other.getX()*40-xOffset, other.getY()-yOffset, null);
    				}
    				
//    				
	    			g.setColor(Color.WHITE);
//	    			g.drawString(other.getName(), other.getX()*40-xOffset, other.getY()*40-yOffset+10);
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
    
    public void openCharacterSheet()
    {
    	charSheetOpen = true;
    	characterSheetPanel = new CharacterSheetPanel(player, sm);
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
		optionsMenuOpen = true;
    }
    
    public void closeOptionsMenu()
    {
		sm.removeComponent(optionsPanel);
		optionsMenuOpen = false;
    }
    
    public void logout()
    {
        sm.client.sendMessage("UPDATECHARINFO#" + player.getAllCharInfo());
    	sm.disposeWindow();
    	sm.client.sendMessage("LOGOUT#" + player.getName());
    	sm.countryViewSong.stopSong();
    }
  
    public void onEnter()
    {
	    sm.addComponent(textField);
	    sm.addComponent(scrollPane);
        textField.setText("");
        HUD.show();
    }
  
    public void onExit()
    {
	    sm.removeComponent(textField);
	    sm.removeComponent(scrollPane);
	    HUD.hide();
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
    	if(keyCode == KeyEvent.VK_J)
    	{
    		player.levelUp();
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
