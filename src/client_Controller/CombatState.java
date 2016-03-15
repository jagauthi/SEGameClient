package client_Controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import client_Model.Enemy;
import client_Model.Location;
import client_Model.Player;
import client_View.GamePanel;

public class CombatState extends IState
{
	BufferedImage backgroundImage;
	ArrayList<Enemy> enemies;
	JButton attackButton, magicButton, inventoryButton, runButton;
	
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
		
		//Sets up buttons for later use
		attackButton = new JButton("Attack");
		attackButton.setBounds(GamePanel.WIDTH/2 + 10, 2*GamePanel.HEIGHT/3 + 10, 300, 110);
		attackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attackMenu();
            }
        });
		magicButton = new JButton("Magic");
		magicButton.setBounds(GamePanel.WIDTH/2 + 310, 2*GamePanel.HEIGHT/3 + 10, 300, 110);
		magicButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                magicMenu();
            }
        });
		inventoryButton = new JButton("Inventory");
		inventoryButton.setBounds(GamePanel.WIDTH/2 + 10, 2*GamePanel.HEIGHT/3 + 120, 300, 110);
		inventoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inventoryMenu();
            }
        });
		runButton = new JButton("Run");
		runButton.setBounds(GamePanel.WIDTH/2 + 310, 2*GamePanel.HEIGHT/3 + 120, 300, 110);
		runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runAway();
            }
        });
		
	}
	
	public void attackMenu()
	{
		System.out.println("Attaaaaaaack");
	}
	
	public void magicMenu()
	{
		System.out.println("PewPew");
	}
	
	public void inventoryMenu()
	{
		System.out.println("Open Inventory");
	}
	
	public void runAway()
	{
		System.out.println("Run away, little girl.");
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
    	
    	
    	
    	if(enemies.size() > 0)
    	{
    		g.setColor(Color.red);
			for(int i = 0; i < enemies.size(); i++)
			{
				Rectangle enemyRect = enemies.get(i).getRect();
				g.fillRect(enemyRect.x, enemyRect.y, enemyRect.width, enemyRect.height);
			}
    	}
    	//Left Box Background
    	g.setColor(Color.orange);
    	g.fillRect(0, 2*GamePanel.HEIGHT/3, GamePanel.WIDTH/2, GamePanel.HEIGHT/3);
    	//Right Box Background
    	g.setColor(Color.lightGray);
    	g.fillRect(GamePanel.WIDTH/2, 2*GamePanel.HEIGHT/3, GamePanel.WIDTH/2, GamePanel.HEIGHT/3);
    	
    	//Left Box Info
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
		sm.addComponent(attackButton);
		sm.addComponent(magicButton);
		sm.addComponent(inventoryButton);
		sm.addComponent(runButton);
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
    		Enemy newEnemy = new Enemy(thisEnemy[0], Integer.parseInt(thisEnemy[1]), Integer.parseInt(thisEnemy[2]));
    		newEnemy.setRect(GamePanel.WIDTH/8, GamePanel.HEIGHT/8 + (i*100), Enemy.WIDTH, Enemy.HEIGHT);
    		enemies.add(newEnemy);
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
