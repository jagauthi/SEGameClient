package client_Controller;
 
 import java.awt.Graphics;
 import client_Model.Player;
 
 public class IState
 {
 	Player player;
 	
 	public IState(Player p)
 	{
 		player = p;
 	}
 	
    public void update()
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
    public void loadLocations(String[] locs)
    {
    	
    }
}
