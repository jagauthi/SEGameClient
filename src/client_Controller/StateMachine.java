package client_Controller;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;

import client_Model.OtherPlayer;
import client_Model.Player;
import client_View.GamePanel;
 
public class StateMachine //extends Thread
{
	//States
    Map<String, IState> states = new HashMap<String, IState>();
    IState currentState = null;
    EmptyState emptyState;
	CountryViewState countryViewState;
	LocalViewState localViewState;
	CombatState combatState;
	BlueState blueState;
	//HashMap<String, OtherPlayer> otherPlayers = new HashMap<String, OtherPlayer>();
	public static ArrayList<OtherPlayer> otherPlayers = new ArrayList<OtherPlayer>();

	//Player Sprites
	public static BufferedImage mageSpriteSheet, rogueSpriteSheet, warriorSpriteSheet;
	public static Image mageSprite, rogueSprite, warriorSprite;
	
	Boolean started = false;
	
	Player player;
	
	GamePanel panel;
	
	static ChatClient client;
	
    public StateMachine(String[] playerInfo, ChatClient c, GamePanel gp)
    {	
    	panel = gp;
    	this.loadSprites();
    	if(playerInfo[2].equals("Mage"))
		{
			player = new Player(playerInfo, mageSpriteSheet);
		} 
    	else if(playerInfo[2].equals("Rogue"))
		{
			player = new Player(playerInfo, rogueSpriteSheet);
		} 
		else
		{
			player = new Player(playerInfo, warriorSpriteSheet);
		} 
        player.setStateMachine(this);
        client = c;
        client.setStateMachine(this);
    	countryViewState = new CountryViewState(player, this);
    	localViewState = new LocalViewState(player, this);
    	blueState = new BlueState(player, this);
    	combatState = new CombatState(player, this);
    	this.add("CountryViewState", countryViewState);
    	this.add("LocalViewState", localViewState);
    	this.add("BlueState", blueState);
    	this.add("CombatState", combatState);
    	if(!player.getLocation().equals("CountryView"))
    	{
    		String[] args = {"LocalViewState", player.getLocation()};
    		changeState(args);
    	}
    	else
    		currentState = countryViewState;
    }
    
    public void loadSprites()
    {
    	try {
			mageSpriteSheet = ImageIO.read(new File("resources/Sprites/mageSprite.png"));
			rogueSpriteSheet = ImageIO.read(new File("resources/Sprites/rogueSprite.png"));
			warriorSpriteSheet = ImageIO.read(new File("resources/Sprites/warriorSprite.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	mageSprite = mageSpriteSheet.getSubimage(80, 240, 40, 60);
    	rogueSprite = rogueSpriteSheet.getSubimage(80, 240, 40, 60);
    	warriorSprite = warriorSpriteSheet.getSubimage(80, 240, 40, 60);
    }
    
    public void finalize()
    {
    	player.setLoggedIn("0");
    	client.sendMessage("UPDATECHARINFO:" + player.getAllCharInfo());
    }
  
    public void update()
    {
        currentState.update();
        //client.sendMessage("UPDATECHARINFO:" + player.getAllCharInfo());
        //System.out.println("UPDATECHARINFO:" + player.getAllCharInfo());
    }
    
    public void oncePerSecondUpdate()
    {
    	//client.sendMessage("UPDATECHARINFO:" + player.getAllCharInfo());
    	currentState.oncePerSecondUpdate();
    }
    
    public void sendServerMyPosition()
    {
    	client.sendMessage("UPDATECHARPOS#" + player.getLocationInfo());
    }
    
    public void updateCharsAroundMe(String[] charsAroundMe)
    {
    	//charUpdated:char1Name char1x char1y char1Direction char1EquippedItems char1Sex:char2Name char2x char2y...
    	otherPlayers.clear();
    	if(charsAroundMe.length > 1)
    	{
	    	for(int i = 1; i < charsAroundMe.length; i++)
	    	{
	    		String[] otherGuy = charsAroundMe[i].split(" ");
	    		otherPlayers.add(new OtherPlayer(otherGuy[0],
	    				Integer.parseInt(otherGuy[1]), Integer.parseInt(otherGuy[2])));
	    	}
    	}
    }
  
    public void render(Graphics g)
    {
        currentState.render(g);
    }
  
    public void change(String stateName){
        currentState.onExit();
        currentState = states.get(stateName);
    }
  
    public void add(String name, IState state){
        states.put(name, state);
    }
    
    public IState getCurrentState(){
    	return currentState;
    }
    
    public void changeState(String[] args){
    	/*
    	 * args can be different depending on which state it changes to.
    	 * 
    	 * For example, when we switch to a local state...
    	 * args[0] = "Local View State"
    	 * args[1] = "StartingTown"
    	 */
    	if(currentState != null)
    		currentState.onExit();
    	if(args[0].equals("LocalViewState"))
    	{
    		currentState = states.get(args[0]);
    		client.sendMessage("GETLOCALINFO#" + args[1]);
    		player.setLocation(args[1]);
    		//currentState.loadInfo(args[1]);
    	}
    	else if(args[0].equals("CombatState"))
    	{
    		currentState = states.get(args[0]);
    		client.sendMessage("GETCOMBATINFO#" + args[1]);
    	}
    	else if(args[0].equals("CountryViewState"))
    	{
    		//Do we need to give coordinates to where the player will be returned in the country view?
    		currentState = states.get(args[0]);
    		player.setLocation("CountryView");
    		currentState.onEnter();
    	}
    	//currentState.onEnter();
    }
    
    public CountryViewState getCountryViewState()
    {
    	return countryViewState;
    }
    
    /*
     * Maybe later change these to adding panels instead of just buttons?
     */
    public void addComponent(JComponent comp)
    {
    	panel.addComponent(comp);
    }
    
    public void removeComponent(JComponent comp)
    {
    	panel.removeComponent(comp);
    }
   
    public void doRequestFocus()
    {
    	panel.doRequestFocus();
    }
    
    public void disposeWindow()
    {
    	panel.disposeWindow();
    }
}
