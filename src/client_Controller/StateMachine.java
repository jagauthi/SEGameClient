package client_Controller;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import client_Model.OtherPlayer;
import client_Model.Player;
 
public class StateMachine //extends Thread
{
	//States
    Map<String, IState> states = new HashMap<String, IState>();
    IState currentState;
    EmptyState emptyState;
	CountryViewState countryViewState;
	LocalViewState localViewState;
	BlueState blueState;
	//HashMap<String, OtherPlayer> otherPlayers = new HashMap<String, OtherPlayer>();
	public static ArrayList<OtherPlayer> otherPlayers = new ArrayList<OtherPlayer>();

	Boolean started = false;
	
	Player player;
	
	static ChatClient client;
	
    public StateMachine(String[] playerInfo, ChatClient c)
    {	
        player = new Player(playerInfo);
        client = c;
        client.setStateMachine(this);
    	countryViewState = new CountryViewState(player, this);
    	localViewState = new LocalViewState(player, this);
    	blueState = new BlueState(player, this);
    	this.add("CountryViewState", countryViewState);
    	this.add("LocalViewState", localViewState);
    	this.add("BlueState", blueState);
        currentState = countryViewState;
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
    	client.sendMessage("UPDATECHARINFO:" + player.getAllCharInfo());
    	currentState.oncePerSecondUpdate();
    }
    
    public void updateCharsAroundMe(String[] charsAroundMe)
    {
    	otherPlayers.clear();
    	if(charsAroundMe.length > 1)
    	{
	    	for(int i = 1; i < charsAroundMe.length; i++)
	    	{
	    		String[] otherGuy = charsAroundMe[i].split(" ");
	    		otherPlayers.add(new OtherPlayer(otherGuy[0], otherGuy[1],
	    				Integer.parseInt(otherGuy[2]), Integer.parseInt(otherGuy[3])));
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
    	currentState.onExit();
    	if(args[0].equals("LocalViewState"))
    	{
    		currentState = states.get(args[0]);
    		client.sendMessage("GETLOCALINFO:" + args[1]);
    		player.setLocation(args[1]);
    		//currentState.loadInfo(args[1]);
    	}
    	else if(args[0].equals("CountryViewState"))
    	{
    		//Do we need to give coordinates to where the player will be returned in the country view?
    		currentState = states.get(args[0]);
    		player.setLocation("CountryView");
    	}
    	currentState.onEnter();
    }
   
}
