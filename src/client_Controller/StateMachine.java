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
import client_View.AudioManager;
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
	public static BufferedImage mageSpriteSheet, rogueSpriteSheet, warriorSpriteSheet,
								healthBarNP, manaBarNP, expBarNP, HUDBase, HUDLvlUpImage, barBoundsNP;
	public static Image[] mageSprite, rogueSprite, warriorSprite;
	public static Image enemySprite;
	
	static Boolean started = false;
	
	Player player;
	
	GamePanel panel;
	
	static ChatClient client;
	
	AudioManager countryViewSong;
	AudioManager combatSong;
	AudioManager townSong;
	
    public StateMachine(String[] playerInfo, ChatClient c, GamePanel gp)
    {	
    	panel = gp;
    	this.loadImages();
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
    	
    	countryViewSong = new AudioManager();
    	combatSong = new AudioManager();
    	townSong = new AudioManager();
    	
    	if(!player.getLocation().equals("CountryView"))
    	{
    		String[] args = {"LocalViewState", player.getLocation()};
    		changeState(args);
    	}
    	else
    	{
    		currentState = countryViewState;
    		countryViewSong.playSong("resources/Sounds/SnowZone.wav");
    	}
    	
    	started = true;
    }
    
    public void loadImages()
    {
    	try {
			mageSpriteSheet = ImageIO.read(new File("resources/Sprites/mageSprite.png"));
			rogueSpriteSheet = ImageIO.read(new File("resources/Sprites/rogueSprite.png"));
			warriorSpriteSheet = ImageIO.read(new File("resources/Sprites/warriorSprite.png"));
			enemySprite = ImageIO.read(new File("resources/Sprites/wolfSprite.png"));
			
			healthBarNP = ImageIO.read(new File("resources/GUI/HUD/health_np_18x18_4x4.png"));
			manaBarNP = ImageIO.read(new File("resources/GUI/HUD/mana_np_18x18_4x4.png"));
			expBarNP = ImageIO.read(new File("resources/GUI/HUD/exp_np_5x5_2x2.png"));
			barBoundsNP = ImageIO.read(new File("resources/GUI/HUD/bar_np_26x26_6x6.png"));
			HUDBase = ImageIO.read(new File("resources/GUI/HUD/HUDbase.png"));
			HUDLvlUpImage = ImageIO.read(new File("resources/GUI/HUD/levelUpBar.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	mageSprite = new Image[4];
    	rogueSprite = new Image[4];
    	warriorSprite = new Image[4];
    	for(int n = 0; n < 4; n++)
    	{
    		mageSprite[n] = mageSpriteSheet.getSubimage(40*n, 240, 40, 60);
    		rogueSprite[n] = rogueSpriteSheet.getSubimage(40*n, 240, 40, 60);
    		warriorSprite[n] = warriorSpriteSheet.getSubimage(40*n, 240, 40, 60);
    	}
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
    	//charUpdated:char1Name char1x char1y char1Direction char1EquippedItems char1Class:char2Name char2x char2y...
    	otherPlayers.clear();
    	if(charsAroundMe.length > 1)
    	{
	    	for(int i = 1; i < charsAroundMe.length; i++)
	    	{
	    		String[] otherGuy = charsAroundMe[i].split(" ");
	    		otherPlayers.add(new OtherPlayer(otherGuy[0],
	    				Integer.parseInt(otherGuy[1]), Integer.parseInt(otherGuy[2]), Integer.parseInt(otherGuy[3]), otherGuy[5]));
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
    		if(countryViewSong.isPlaying())
    			countryViewSong.pauseSong();
    		currentState = states.get(args[0]);
    		client.sendMessage("GETLOCALINFO#" + args[1]);
    		player.setLocation(args[1]);
    		//currentState.loadInfo(args[1]);
    	}
    	else if(args[0].equals("CombatState"))
    	{
    		if(countryViewSong.isPlaying())
    			countryViewSong.pauseSong();
    		combatSong.playSong("resources/Sounds/MaxSong.wav");
    		currentState = states.get(args[0]);
    		client.sendMessage("GETCOMBATINFO#" + args[1]);
    	}
    	else if(args[0].equals("CountryViewState"))
    	{
    		if(combatSong.isPlaying())
    			combatSong.stopSong();
    		if(townSong.isPlaying())
    			townSong.stopSong();
    		countryViewSong.resumeSong();
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
