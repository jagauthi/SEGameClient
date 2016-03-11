package client_Controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import client_Model.Enemy;
import client_Model.Location;
import client_Model.Player;
import client_View.GamePanel;

public class CombatState extends IState
{
	BufferedImage backgroundImage;
	ArrayList<Enemy> enemies;
	
	public CombatState(Player p, StateMachine s)
	{
		super(p, s);
		enemies = new ArrayList<Enemy>();
		
		String backgroundPath = "resources/MKMap.jpg";
    	backgroundImage = null;

		try {
			backgroundImage = ImageIO.read(new File(backgroundPath));
        } 
		catch (IOException ioe) {
            System.out.println("Unable to load image file.");
        }
	}
	
    public void update()
    {
    	//player.animationUpdate();
    	//player.update();
    }
    
    public void oncePerSecondUpdate()
    {
    	//player.update();
    }
  
    public void render(Graphics g)
    {
    	g.drawImage(backgroundImage, 0, 0, null);

    	g.drawImage(player.getImage(), 3*GamePanel.WIDTH/4, GamePanel.HEIGHT/4, null);
    	
    	g.setColor(Color.lightGray);
    	g.fillRect(GamePanel.WIDTH/2, 3*GamePanel.HEIGHT/4, GamePanel.WIDTH/2, GamePanel.HEIGHT/4);
    	g.setColor(Color.orange);
    	g.fillRect(0, 3*GamePanel.HEIGHT/4, GamePanel.WIDTH/2, GamePanel.HEIGHT/4);
    	
    	//Left Box
    	g.setColor(Color.black);
    	g.drawString("Name: " + player.getName(), 10, (3*GamePanel.HEIGHT/4)+10);
    	g.drawString("HP: " + player.getHealth(), 10, (3*GamePanel.HEIGHT/4)+30);
    	g.drawString("Mana: " + player.getMana(), 10, (3*GamePanel.HEIGHT/4)+50);
    }
    
    public void drawOtherPlayers(Graphics g)
    {
    	
    }
    
    public void drawLocations(Graphics g)
    {
    	
    }
  
    public void onEnter()
    {
        // No action to take when the state is entered
    }
  
    public void onExit()
    {
        // No action to take when the state is exited
    }
    
    public void loadInfo(String[] info)
    {
		enemies = new ArrayList<Enemy>();
		for(int i = 1; i < info.length; i++)
    	{
    		String[] thisEnemy = info[i].split(" ");
    		//Takes an enemies name, health, and damage (?)
    		enemies.add(new Enemy(thisEnemy[0], Integer.parseInt(thisEnemy[1]), Integer.parseInt(thisEnemy[2])));
    	}
    	onEnter();
    }
    
    public void keyPressed(int keyCode){
    	if(keyCode == KeyEvent.VK_W){

		}
    	if(keyCode == KeyEvent.VK_A){

		}
    	if(keyCode == KeyEvent.VK_S){

		}
    	if(keyCode == KeyEvent.VK_D){

		}
    	if(keyCode == KeyEvent.VK_SPACE){

		}
    }
    
    public void keyReleased(int keyCode){
    	if(keyCode == KeyEvent.VK_W){

		}
    	if(keyCode == KeyEvent.VK_A){

		}
    	if(keyCode == KeyEvent.VK_S){

		}
    	if(keyCode == KeyEvent.VK_D){

		}
    }
}
