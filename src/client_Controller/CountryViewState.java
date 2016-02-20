package client_Controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import client_Model.Location;
import client_Model.Player;
import client_View.GamePanel;

public class CountryViewState extends IState
{
	ArrayList<Location> locations;
	
	public CountryViewState(Player p, StateMachine s)
	{
		super(p, s);
		locations = new ArrayList<Location>();
		getLocations();
	}
	
    public void update()
    {
        player.update();
        checkPlayerIntersectLocation();
    }
  
    public void render(Graphics g)
    {
    	g.setColor(new Color(0.5f, 0.3f, 0.2f));
    	g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
    	player.draw(g);
    	g.setColor(Color.lightGray);
    	g.fillRect(0, 600, 300, 120);
    	g.drawString("Name: " + player.getName(), 10, 610);
    	g.drawString("HP: " + player.getHealth(), 10, 630);
    	g.drawString("Mana: " + player.getMana(), 10, 650);
    	
    	drawOtherPlayers(g);
    	drawLocations(g);
    }
    
    public void drawOtherPlayers(Graphics g)
    {
    	if(StateMachine.otherPlayers.size() > 0)
    	{
    		g.setColor(Color.red);
    		for(int i = 0; i < StateMachine.otherPlayers.size(); i++)
    		{
    			if(StateMachine.otherPlayers.get(i).getPlayerClass().equals("Rogue"))
    			{
    				g.setColor(Color.green);
    			}
    			else if(StateMachine.otherPlayers.get(i).getPlayerClass().equals("Mage"))
    				g.setColor(Color.blue);
    			else
    				g.setColor(Color.red);
    			
    			if(!StateMachine.otherPlayers.get(i).getName().equals(player.getName()))
    			{
	    			g.fillRect(StateMachine.otherPlayers.get(i).getX(), 
	    					StateMachine.otherPlayers.get(i).getY(), 
	    					StateMachine.otherPlayers.get(i).getWidth(), 
	    					StateMachine.otherPlayers.get(i).getHeight());
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
    			g.fillRect(locations.get(i).getX(), locations.get(i).getY(), 
    					Location.WIDTH, Location.HEIGHT);
    		}
    	}
    }
  
    public void onEnter()
    {
        // No action to take when the state is entered
    }
  
    public void onExit()
    {
        // No action to take when the state is exited
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
    }
    
    public void keyPressed(int keyCode){
    	if(keyCode == KeyEvent.VK_W){
			player.moveUp();
			player.stopDown();
		}
    	if(keyCode == KeyEvent.VK_A){
    		player.moveLeft();
    		player.stopRight();
		}
    	if(keyCode == KeyEvent.VK_S){
    		player.moveDown();
    		player.stopUp();
		}
    	if(keyCode == KeyEvent.VK_D){
    		player.moveRight();
    		player.stopLeft();
		}
    	
    }
    
    public void keyReleased(int keyCode){
    	if(keyCode == KeyEvent.VK_W){
			player.stopUp();
		}
    	if(keyCode == KeyEvent.VK_A){
    		player.stopLeft();
		}
    	if(keyCode == KeyEvent.VK_S){
    		player.stopDown();
		}
    	if(keyCode == KeyEvent.VK_D){
    		player.stopRight();
		}
    }
    
    public void checkPlayerIntersectLocation()
    {
    	for(int i = 0; i < locations.size(); i++)
    	{
    		if(player.getPlayerRect().intersects(locations.get(i).getLocRect()))
    		{
    			//Switch to local view state, pass name of the location we're entering
    			String[] args = { "Local View State", locations.get(i).getName() };
    			sm.changeState(args);
    		}
    	}
    }
}
