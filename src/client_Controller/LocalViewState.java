package client_Controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import client_Model.OtherPlayer;
import client_Model.Player;
import client_View.GamePanel;

public class LocalViewState extends IState{
	
	int xOffset, yOffset = 0;
	BufferedImage mapImage;
	int playerEnterX, playerEnterY, playerExitX, playerExitY = 0;
	Rectangle exitBox;
	
	public LocalViewState(Player p, StateMachine s)
	{
		super(p, s);
		
		String mapLocation = "resources/FFMap.png";
    	mapImage = null;

		try {
			mapImage = ImageIO.read(new File(mapLocation));
        } 
		catch (IOException ioe) {
            System.out.println("Unable to load image file.");
        }
	}
	
    public void update()
    {
    	player.animationUpdate();
    	player.update();
    	if(exitBox != null)
    	{
	    	if(player.getPlayerRect().intersects(exitBox))
	    	{
	    		String[] args = new String[1];
	    		args[0] = "CountryViewState";
	    		sm.changeState(args);
	    	}
    	}
    }
    
    public void oncePerSecondUpdate()
    {
    	//player.update();
    }
  
    public void render(Graphics g)
    {
    	xOffset = (player.getX() * Tile.WIDTH) - GamePanel.WIDTH/2 + Tile.WIDTH/2 + player.getAnimationOffsetX();
    	yOffset = (player.getY() * Tile.HEIGHT) - GamePanel.HEIGHT/2 + Tile.HEIGHT/2 + player.getAnimationOffsetY();
    	
    	g.drawImage(mapImage, 0-xOffset, 0-yOffset, mapImage.getWidth(null), mapImage.getHeight(null), null);

    	player.draw(g);
    	g.setColor(Color.lightGray);
    	g.fillRect(0, 600, 300, 120);
    	g.setColor(Color.black);
    	g.drawString("Name: " + player.getName(), 10, 610);
    	g.drawString("HP: " + player.getHealth(), 10, 630);
    	g.drawString("Mana: " + player.getMana(), 10, 650);
    	g.drawString("Coords: " + player.getX() + ", " + player.getY(), 10, 670);
    	
    	g.setColor(Color.BLACK);
    	if(exitBox != null)
    		g.fillRect(exitBox.x*40 - xOffset, exitBox.y*40 - yOffset, exitBox.width*40, exitBox.height*40);
    	drawOtherPlayers(g);
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
	    			g.fillRect(StateMachine.otherPlayers.get(i).getX()*40 - xOffset, 
	    					StateMachine.otherPlayers.get(i).getY()*40- yOffset, 
	    					StateMachine.otherPlayers.get(i).getWidth(), 
	    					StateMachine.otherPlayers.get(i).getHeight());
	    			g.setColor(Color.WHITE);
	    			g.drawString(other.getName(), other.getX()*40-xOffset, other.getY()*40-yOffset+10);
    			}
    		}
    	}
    }
  
    public void onEnter()
    {
        player.setX(playerEnterX);
        player.setY(playerEnterY);
    }
  
    public void onExit()
    {
        // No action to take when the state is exited
    	player.setX(playerExitX);
    	player.setY(playerExitY);
    }
    
    public void loadInfo(String[] info)
    {
    	exitBox = new Rectangle(Integer.parseInt(info[10]), Integer.parseInt(info[11]), 2, 2);
    	playerEnterX = Integer.parseInt(info[14]);
    	playerEnterY = Integer.parseInt(info[15]);
    	playerExitX = Integer.parseInt(info[12]);
    	playerExitY = Integer.parseInt(info[13]);
    	//info[10] holds the exit box X
    	//info[11] holds the exit box Y
    	//info[12] holds where the player will be when he goes back to the country view X
    	//info[13] holds where the player will be when he goes back to the country view Y
    	//info[14] holds where the player will enter X
    	//info[15] holds where the player will enter Y
    	onEnter();
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
    
}
