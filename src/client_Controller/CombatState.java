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
	JButton enemy1Button, enemy2Button, enemy3Button, enemy4Button;
	int selectedEnemy = 0;
	static int numEnemiesKilled = 0;
	boolean started = false;
	boolean playerTurn;
	
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
				
		enemy1Button = new JButton("Enemy1");
		enemy1Button.setBounds(GamePanel.WIDTH/8, GamePanel.HEIGHT/8, Enemy.WIDTH, Enemy.HEIGHT);
		enemy1Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectEnemy(1);
            }
        });
		enemy2Button = new JButton("Enemy2");
		enemy2Button.setBounds(GamePanel.WIDTH/8, GamePanel.HEIGHT/8 + 140, Enemy.WIDTH, Enemy.HEIGHT);
		enemy2Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	selectEnemy(2);
            }
        });
		enemy3Button = new JButton("Enemy3");
		enemy3Button.setBounds(GamePanel.WIDTH/8, GamePanel.HEIGHT/8 + 280, Enemy.WIDTH, Enemy.HEIGHT);
		enemy3Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	selectEnemy(3);
            }
        });
		enemy4Button = new JButton("Enemy4");
		enemy4Button.setBounds(GamePanel.WIDTH/8, GamePanel.HEIGHT/8 + 420, Enemy.WIDTH, Enemy.HEIGHT);
		enemy4Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	selectEnemy(4);
            }
        });
		
	}
	
	public void attackMenu()
	{
		if(playerTurn)
		{
			enemies.get(selectedEnemy-1).takeDamage(player.getMeleeDamage());
			playerTurn = false;
		}
	}
	
	public void magicMenu()
	{
		int damage = player.getMagicDamage();
		if(playerTurn && damage > 0)
		{
			enemies.get(selectedEnemy-1).takeDamage(damage);
			playerTurn = false;
		}
	}
	
	public void inventoryMenu()
	{
		System.out.println("Open Inventory");
	}
	
	public void runAway()
	{
		System.out.println("Run away, little girl.");
		String[] args = { "CountryViewState" };
		sm.changeState(args);
	}
	
	public void selectEnemy(int num)
	{
		System.out.println("Selecting enemy number: " + num);
		selectedEnemy = num;
	}
	
    public void update()
    {
    	//player.animationUpdate();
    	//player.update();
    	if(started)
    		truncateEnemies();
    	if(!playerTurn)
    	{
    		enemiesAttack();
    		playerTurn = true;
    	}
    }
    
    public void oncePerSecondUpdate()
    {
    	//player.update();
    }
  
    public void render(Graphics g)
    {
    	//g.drawImage(backgroundImage, 0, 0, null);
    	g.setColor(Color.BLUE);
    	g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
    	g.drawImage(player.getImage(), 3*GamePanel.WIDTH/4, GamePanel.HEIGHT/4, 
    				player.getImage().getWidth()*2, player.getImage().getHeight()*2, null);
    	
    	if(enemies.size() > 0)
    	{
    		g.setColor(Color.red);
			for(int i = 0; i < enemies.size(); i++)
			{
				if(enemies.get(i).isAlive())
				{
					Enemy enemy = enemies.get(i);
					Rectangle enemyRect = enemy.getRect();
					//Drawing Enemy Rectangle
					g.fillRect(enemyRect.x, enemyRect.y, enemyRect.width, enemyRect.height);
					
					//Enemy HealthBar
					float currentHealth = (float)enemy.getHealth()/(float)enemy.getMaxHealth() * 100.0f;
					g.setColor(Color.white);
					g.fillRect(enemyRect.x - 20, enemyRect.y - 25, 104, 20);
					g.setColor(Color.red);
					g.fillRect(enemyRect.x - 18, enemyRect.y - 23, (int) currentHealth, 16);
				}
			}
    	}
    	//Left Box Background
    	g.setColor(Color.lightGray);
    	g.fillRect(0, 2*GamePanel.HEIGHT/3, GamePanel.WIDTH/2, GamePanel.HEIGHT/3);
    	//Right Box Background
    	g.setColor(Color.black);
    	g.fillRect(GamePanel.WIDTH/2, 2*GamePanel.HEIGHT/3, GamePanel.WIDTH/2, GamePanel.HEIGHT/3);
    	
    	//Left Box Info
    	g.setColor(Color.black);
    	g.drawString("Name: " + player.getName(), 10, (3*GamePanel.HEIGHT/4)+10);
    	
    	float currentHealth = (float)player.getHealth()/(float)player.getMaxHealth() * 100.0f;
		g.setColor(Color.white);
		g.fillRect(10, (3*GamePanel.HEIGHT/4)+30, 104, 20);
		g.setColor(Color.red);
		g.fillRect(12, (3*GamePanel.HEIGHT/4)+32, (int) currentHealth, 16);
		g.setColor(Color.BLACK);
    	g.drawString(player.getHealth() + "/" + player.getMaxHealth(), 40, (3*GamePanel.HEIGHT/4)+45);
		
		float currentMana = (float)player.getMana()/(float)player.getMaxMana() * 100.0f;
		g.setColor(Color.white);
		g.fillRect(10, (3*GamePanel.HEIGHT/4)+60, 104, 20);
		g.setColor(Color.cyan);
		g.fillRect(12, (3*GamePanel.HEIGHT/4)+62, (int) currentMana, 16);
		g.setColor(Color.BLACK);
    	g.drawString(player.getMana() + "/" + player.getMaxMana(), 40, (3*GamePanel.HEIGHT/4)+75);
    }
    
    public void truncateEnemies()
    {
    	if(numEnemiesKilled < enemies.size())
    	{
	    	for(int i = 0; i < enemies.size(); i++)
	    	{
	    		if(!enemies.get(i).isAlive())
	    		{
	    			//enemies.remove(i);
	    			if(i == 0)
	    				sm.removeComponent(enemy1Button);
	    			else if(i == 1)
	    				sm.removeComponent(enemy2Button);
	    			else if(i == 2)
	    				sm.removeComponent(enemy3Button);
	    			else
	    				sm.removeComponent(enemy4Button);
	    		}
	    	}
    	}
    	else
    	{
    		int totalXP = 0;
    		for(int i = 0; i < enemies.size(); i++)
    			totalXP += enemies.get(i).getXP();
    		player.gainExperience(totalXP);
    		String[] args = { "CountryViewState" };
    		sm.changeState(args);
    	}
    }
    
    public static void incrementNumEnemiesKilled()
    {
    	numEnemiesKilled++;
    }
    
    public void enemiesAttack()
    {
    	if(numEnemiesKilled < enemies.size())
    	{
	    	for(int i = 0; i < enemies.size(); i++)
	    	{
	    		if(enemies.get(i).isAlive())
	    		{
	    			enemies.get(i).attack(player);
	    		}
	    	}
    	}
    }
    
    public void drawOtherPlayers(Graphics g)
    {
    	
    }
    
    public void drawLocations(Graphics g)
    {
    	
    }
  
    public void onEnter()
    {
    	started = true;
    	playerTurn = true;
    	numEnemiesKilled = 0;
		sm.addComponent(attackButton);
		sm.addComponent(magicButton);
		sm.addComponent(inventoryButton);
		sm.addComponent(runButton);
		
		sm.addComponent(enemy1Button);
		sm.addComponent(enemy2Button);
		//sm.addComponent(enemy3Button);
		//sm.addComponent(enemy4Button);
		selectedEnemy = 1;
    }
  
    public void onExit()
    {
    	sm.doRequestFocus();
        started = false;
    	sm.removeComponent(attackButton);
		sm.removeComponent(magicButton);
		sm.removeComponent(inventoryButton);
		sm.removeComponent(runButton);
		
		sm.removeComponent(enemy1Button);
		sm.removeComponent(enemy2Button);
		
        sm.client.sendMessage("UPDATECHARINFO#" + player.getAllCharInfo());
    }
    
    public void loadInfo(String[] info)
    {
		enemies = new ArrayList<Enemy>();
		for(int i = 1; i < info.length; i++)
    	{
    		String[] thisEnemy = info[i].split(" ");
    		//Takes an enemies name, health, and damage (?)
    		Enemy newEnemy = new Enemy(thisEnemy[0], Integer.parseInt(thisEnemy[1]), Integer.parseInt(thisEnemy[2]));
    		newEnemy.setRect(GamePanel.WIDTH/8, GamePanel.HEIGHT/8 + ((i-1)*140), Enemy.WIDTH, Enemy.HEIGHT);
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
