package client_Controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import client_Model.Enemy;
import client_Model.Location;
import client_Model.Player;
import client_View.GamePanel;
import client_View.Launcher;
import client_View.NinePatchImage;

public class CombatState extends IState
{
	BufferedImage backgroundImage;
	ArrayList<Enemy> enemies;
	JButton attackButton, magicButton, inventoryButton, runButton;
	JButton enemy1Button, enemy2Button, enemy3Button, enemy4Button;
	NinePatchImage barBackNP, healthNP, manaNP;
	int selectedEnemy = 1;
	static int numEnemiesKilled = 0;
	boolean started = false;
	boolean playerTurn, doneLoading;
	Font nameFont, numFont;
	public CombatState(Player p, StateMachine s)
	{
		super(p, s);
		doneLoading = false;
		
		enemies = new ArrayList<Enemy>();
		
		nameFont = Launcher.normalFont.deriveFont(25F);
		numFont = Launcher.normalFont.deriveFont(18F);
		
		String backgroundPath = "resources/MKMap.jpg";
    	backgroundImage = null;

		try {
			backgroundImage = ImageIO.read(new File(backgroundPath));
        } 
		catch (IOException ioe) {
            System.out.println("Unable to load image file.");
        }
		
		//Get ninepatch image for button backgrounds
		NinePatchImage np = new NinePatchImage(50, 50, 7, 7, Launcher.npBasic_50_7);
		barBackNP = new NinePatchImage(26, 26, 6, 6, sm.barBoundsNP);
		healthNP = new NinePatchImage(18, 18, 4, 4, sm.healthBarNP);
		manaNP = new NinePatchImage(18, 18, 4, 4, sm.manaBarNP);
		
		//Sets up buttons for later use
		attackButton = new JButton("Attack");
		attackButton.setBounds(GamePanel.WIDTH/2 + 10, 2*GamePanel.HEIGHT/3 + 10, 300, 110);
		attackButton.setHorizontalTextPosition(JButton.CENTER);
		attackButton.setBackground(Launcher.TRANSPARENT);
		attackButton.setIcon(new ImageIcon(np.getScaledImage(attackButton.getWidth(), attackButton.getHeight())));
		attackButton.setBorder(Launcher.emptyBorder);
		attackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attackMenu();
            }
        });
		magicButton = new JButton("Magic");
		magicButton.setBounds(GamePanel.WIDTH/2 + 310, 2*GamePanel.HEIGHT/3 + 10, 300, 110);
		magicButton.setHorizontalTextPosition(JButton.CENTER);
		magicButton.setBackground(Launcher.TRANSPARENT);
		magicButton.setIcon(new ImageIcon(np.getScaledImage(magicButton.getWidth(), magicButton.getHeight())));
		magicButton.setBorder(Launcher.emptyBorder);
		magicButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                magicMenu();
            }
        });
		inventoryButton = new JButton("Inventory");
		inventoryButton.setBounds(GamePanel.WIDTH/2 + 10, 2*GamePanel.HEIGHT/3 + 120, 300, 110);
		inventoryButton.setHorizontalTextPosition(JButton.CENTER);
		inventoryButton.setBackground(Launcher.TRANSPARENT);
		inventoryButton.setIcon(new ImageIcon(np.getScaledImage(inventoryButton.getWidth(), inventoryButton.getHeight())));
		inventoryButton.setBorder(Launcher.emptyBorder);
		inventoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inventoryMenu();
            }
        });
		runButton = new JButton("Run");
		runButton.setBounds(GamePanel.WIDTH/2 + 310, 2*GamePanel.HEIGHT/3 + 120, 300, 110);
		runButton.setHorizontalTextPosition(JButton.CENTER);
		runButton.setBackground(Launcher.TRANSPARENT);
		runButton.setIcon(new ImageIcon(np.getScaledImage(runButton.getWidth(), runButton.getHeight())));
		runButton.setBorder(Launcher.emptyBorder);
		runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runAway();
            }
        });
				
		enemy1Button = new JButton("");
		enemy1Button.setBounds(GamePanel.WIDTH/8, GamePanel.HEIGHT/8, Enemy.WIDTH, Enemy.HEIGHT);
		enemy1Button.setIcon(new ImageIcon(s.enemySprite));
		enemy1Button.setBackground(Launcher.TRANSPARENT);
		enemy1Button.setBorder(Launcher.emptyBorder);
		enemy1Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectEnemy(1);
            }
        });
		enemy2Button = new JButton("");
		enemy2Button.setBounds(GamePanel.WIDTH/8, GamePanel.HEIGHT/8 + 140, Enemy.WIDTH, Enemy.HEIGHT);
		enemy2Button.setIcon(new ImageIcon(s.enemySprite));
		enemy2Button.setBackground(Launcher.TRANSPARENT);
		enemy2Button.setBorder(Launcher.emptyBorder);
		enemy2Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	selectEnemy(2);
            }
        });
		enemy3Button = new JButton("");
		enemy3Button.setBounds(GamePanel.WIDTH/8, GamePanel.HEIGHT/8 + 280, Enemy.WIDTH, Enemy.HEIGHT);
		enemy3Button.setIcon(new ImageIcon(s.enemySprite));
		enemy3Button.setBackground(Launcher.TRANSPARENT);
		enemy3Button.setBorder(Launcher.emptyBorder);
		enemy3Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	selectEnemy(3);
            }
        });
		enemy4Button = new JButton("");
		enemy4Button.setBounds(GamePanel.WIDTH/8, GamePanel.HEIGHT/8 + 420, Enemy.WIDTH, Enemy.HEIGHT);
		enemy4Button.setIcon(new ImageIcon(s.enemySprite));
		enemy4Button.setBackground(Launcher.TRANSPARENT);
		enemy4Button.setBorder(Launcher.emptyBorder);
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
    	g.drawImage(player.getCombatImage(), 3*GamePanel.WIDTH/4, GamePanel.HEIGHT/4, 
    				player.getCombatImage().getWidth()*2, player.getCombatImage().getHeight()*2, null);
    	
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
//					g.fillRect(enemyRect.x, enemyRect.y, enemyRect.width, enemyRect.height);
					
					//Enemy HealthBar
					float currentHealth = (float)enemy.getHealth()/(float)enemy.getMaxHealth() * 100.0f;
//					g.setColor(Color.white);
//					g.fillRect(enemyRect.x - 20, enemyRect.y - 25, 104, 20);
//					g.setColor(Color.red);
//					g.fillRect(enemyRect.x - 18, enemyRect.y - 23, (int) currentHealth, 16);
					g.drawImage(barBackNP.getScaledImage(108, 26), enemyRect.x + (enemyRect.width/2) - 54, enemyRect.y - 31 - 5, null);
					g.drawImage(healthNP.getScaledImage((int)(currentHealth), 18), enemyRect.x + (enemyRect.width/2) - 50, enemyRect.y - 27 - 5, null);
				}
			}
    	}
    	//Draw box around selected enimie
    	if(doneLoading)
    	{
	    	g.setColor(Color.yellow);
	    	int boxPad = 5;
	    	g.drawRoundRect(enemies.get(selectedEnemy-1).getRect().x - boxPad, enemies.get(selectedEnemy-1).getRect().y - boxPad,
	    			enemies.get(selectedEnemy-1).getRect().width + (boxPad*2), enemies.get(selectedEnemy-1).getRect().height + (boxPad*2), 10, 10);
    	}
    	
    	//Left Box Background
    	g.setColor(Color.lightGray);
    	g.fillRect(0, 2*GamePanel.HEIGHT/3, GamePanel.WIDTH/2, GamePanel.HEIGHT/3);
    	//Right Box Background
    	g.setColor(Launcher.FAKETRANS);
    	g.fillRect(GamePanel.WIDTH/2, 2*GamePanel.HEIGHT/3, GamePanel.WIDTH/2, GamePanel.HEIGHT/3);
    	
    	//Left Box Info
    	float currentHealth = (float)player.getHealth()/(float)player.getMaxHealth();
    	g.drawImage(barBackNP.getScaledImage(188, 26), 10, (3*GamePanel.HEIGHT/4)+30, null);
    	g.drawImage(healthNP.getScaledImage((int)(180 * currentHealth), 18), 14, (3*GamePanel.HEIGHT/4)+34, null);
		
		float currentMana = (float)player.getMana()/(float)player.getMaxMana() ;
		g.drawImage(barBackNP.getScaledImage(188, 26), 10, (3*GamePanel.HEIGHT/4)+60, null);
    	g.drawImage(manaNP.getScaledImage((int)(180 * currentMana), 18), 14, (3*GamePanel.HEIGHT/4)+64, null);
    	
    	g.setColor(Color.BLACK);
    	g.setFont(nameFont);
    	g.drawString(player.getName(), 10, (3*GamePanel.HEIGHT/4)+25);
    	g.setFont(numFont);
    	g.drawString(player.getHealth()+" / "+player.getMaxHealth(), 25, (3*GamePanel.HEIGHT/4)+49);
    	g.drawString(player.getMana()+" / "+player.getMaxMana(), 25, (3*GamePanel.HEIGHT/4)+79);
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
	    	
	    	if(!enemies.get(selectedEnemy-1).isAlive())
	    	{
	    		for(int i = 0; i < enemies.size(); i++)
	    	    {
	    			if(enemies.get(i).isAlive())
	    				selectedEnemy = i+1;
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
		doneLoading = true;
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
