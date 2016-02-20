package client_Controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import client_Model.Player;
import client_View.GamePanel;

public class LocalViewState extends IState{
	
	int xOffset, yOffset = 0;
	
	public LocalViewState(Player p, StateMachine s)
	{
		super(p, s);
	}
	
    public void update()
    {
        player.update();
    }
  
    public void render(Graphics g)
    {
    	xOffset = player.getX()-GamePanel.WIDTH/2;
    	yOffset = player.getY()-GamePanel.HEIGHT/2;
    	
    	g.setColor(new Color(0.8f, 0.5f, 0.8f));
    	g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
    	player.draw(g);
    	g.setColor(Color.lightGray);
    	g.fillRect(0, 600, 300, 120);
    	g.drawString("Name: " + player.getName(), 10, 610);
    	g.drawString("HP: " + player.getHealth(), 10, 630);
    	g.drawString("Mana: " + player.getMana(), 10, 650);
    	
    	drawOtherPlayers(g);
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
	    			g.fillRect(StateMachine.otherPlayers.get(i).getX() - xOffset, 
	    					StateMachine.otherPlayers.get(i).getY()- yOffset, 
	    					StateMachine.otherPlayers.get(i).getWidth(), 
	    					StateMachine.otherPlayers.get(i).getHeight());
    			}
    		}
    	}
    }
  
    public void onEnter()
    {
        /*
         * We should make it so that the player enters the location at the same
         * spot every time, like maybe it always loads the player into
         * the bottom middle side of the town or something.
         */
    }
  
    public void onExit()
    {
        // No action to take when the state is exited
    }
    
    public void loadInfo(String[] info)
    {
    	/*
    	 * Call the database and send it info[1], that is the
    	 * name of this location. Then it will send back info
    	 * like all the npcs and stuff. Maybe this needs
    	 * to go into another method, so that when the chat client
    	 * sends us all that info, we can load it in this method...
    	 * Not sure yet.
    	 */
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
