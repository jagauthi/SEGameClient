package client_Controller;
 
 import java.awt.Graphics;
import java.util.HashMap;

import client_Model.Player;
 
 public class IState
 {
 	Player player;
 	StateMachine sm;
 	HashMap<Integer, HashMap<Integer, Tile>> map;
 	
 	public IState(Player p, StateMachine s)
 	{
 		player = p;
 		sm = s;
 		map = new HashMap<Integer, HashMap<Integer, Tile>>();
 	}
 	
 	public void loadMap()
 	{
 		
 	}
 	
    public void update()
    {
    	
    }
    
    public void oncePerSecondUpdate()
    {
    	
    }
     
    public void render(Graphics g)
    {
     	
    }
     
    public void onEnter()
    {
     	
    }
     
    public void onExit()
    {
     	
    }
     
    public void keyPressed(int keyCode){
 
    }
     
    public void keyReleased(int keyCode){
     	
    }
    
    //Only used in CountryViewState, but I need it in here 
    //So that we can call it from the chatClient
    public void loadInfo(String[] args)
    {
    	
    }
}
