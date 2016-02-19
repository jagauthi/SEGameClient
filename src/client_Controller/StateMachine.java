package client_Controller;
import java.awt.Graphics;
import java.awt.Rectangle;
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
	BlueState blueState;
	//HashMap<String, OtherPlayer> otherPlayers = new HashMap<String, OtherPlayer>();
	public static ArrayList<OtherPlayer> otherPlayers = new ArrayList<OtherPlayer>();

	Boolean started = false;
	
	Player player;
	
	ChatClient client;
	
    public StateMachine(String[] playerInfo, ChatClient c)
    {	
        player = new Player(playerInfo);
        client = c;
        client.setStateMachine(this);
    	countryViewState = new CountryViewState(player);
    	blueState = new BlueState(player);
    	this.add("Country View State", countryViewState);
        currentState = countryViewState;
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
    
    public void changeState(){
    	if(currentState == countryViewState){
    		currentState = blueState;
    	}
    	else
    		currentState =countryViewState;
    }
    
}
