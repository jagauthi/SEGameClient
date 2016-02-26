package client_Controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import client_Model.Location;
import client_Model.OtherPlayer;
import client_Model.Player;
import client_View.GamePanel;

public class CountryViewState extends IState
{
	ArrayList<Location> locations;
	Image mapImage;
	int xOffset, yOffset = 0;
	double randomEncounterChance = 0.0;
	
	public CountryViewState(Player p, StateMachine s)
	{
		super(p, s);
		locations = new ArrayList<Location>();
		getLocations();
		
		String mapLocation = "resources/ZeldaMap.jpg";
    	mapImage = null;

		try {
			mapImage = ImageIO.read(new File(mapLocation));
        } 
		catch (IOException ioe) {
            System.out.println("Unable to load image file.");
        }
		//loadMap();
	}
	
    public void update()
    {
        //player.update();
        //checkPlayerIntersectLocation();
    }
    
    public void oncePerSecondUpdate()
    {
    	player.update();
        checkPlayerIntersectLocation();
    }
  
    public void render(Graphics g)
    {
    	xOffset = (player.getX() * Tile.WIDTH) - GamePanel.WIDTH/2;
    	yOffset = (player.getY() * Tile.HEIGHT) - GamePanel.HEIGHT/2;
		g.drawImage(mapImage, 0-xOffset, 0-yOffset, mapImage.getWidth(null), mapImage.getHeight(null), null);
		/*
		 * The next three are just so that there is a fuller map to play on, instead
		 * of starting in the top left corner of the map.
		 
		g.drawImage(mapImage, 0-xOffset-mapImage.getWidth(null), 0-yOffset, mapImage.getWidth(null), mapImage.getHeight(null), null);
		g.drawImage(mapImage, 0-xOffset, 0-yOffset-mapImage.getHeight(null), mapImage.getWidth(null), mapImage.getHeight(null), null);
		g.drawImage(mapImage, 0-xOffset-mapImage.getWidth(null), 0-yOffset-mapImage.getHeight(null), mapImage.getWidth(null), mapImage.getHeight(null), null);
		*/

    	player.draw(g);
    	g.setColor(Color.lightGray);
    	g.fillRect(0, 600, 300, 120);
    	g.setColor(Color.black);
    	g.drawString("Name: " + player.getName(), 10, 610);
    	g.drawString("HP: " + player.getHealth(), 10, 630);
    	g.drawString("Mana: " + player.getMana(), 10, 650);
    	g.drawString("Coords: " + player.getX() + ", " + player.getY(), 10, 670);
    	
    	drawOtherPlayers(g);
    	drawLocations(g);
    }
    
    public void drawOtherPlayers(Graphics g)
    {
    	if(StateMachine.otherPlayers.size() > 0)
    	{
    		for(int i = 0; i < StateMachine.otherPlayers.size(); i++)
    		{
    			OtherPlayer other = StateMachine.otherPlayers.get(i);
    			if(other.getPlayerClass().equals("Rogue"))
    			{
    				g.setColor(Color.green);
    			}
    			else if(other.getPlayerClass().equals("Mage"))
    				g.setColor(Color.blue);
    			else
    				g.setColor(Color.red);
    			
    			if(!other.getName().equals(player.getName()))
    			{
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
    	if(keyCode == KeyEvent.VK_SPACE){

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
    			String[] args = { "LocalViewState", locations.get(i).getName() };
    			sm.changeState(args);
    		}
    	}
    }
}
