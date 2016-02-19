package client_Model;
 
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import client_Controller.Main;
import client_View.GamePanel;

public class Player {
	
	int x, y;
	int health, exp, level, expToNextLevel, maxHealth;
	int speed;
	public String name;
	public String playerClass;
	Boolean moveingUp, moveingDown, moveingLeft, moveingRight;
	final int WIDTH = 40;
	final int HEIGHT = 60;
	long tookDamageTime;
	
	String spriteLocation;
	BufferedImage spriteSheet;
	Image sprite;

	public Player(String[] playerInfo) 
	{
		//game = g;
		//The first element of this array is the thing that just says "LoginSuccess", so don't look at the first element in the array when loading player info.
		//"characterInfo", name, class, level, gender, health, mana, experience, xCoord, yCoord, gold, strength, dexterituy, constitution, intelgence, willpower, luck, abilities, cooldown
		name = playerInfo[1];
		playerClass = playerInfo[2];
		level = Integer.parseInt(playerInfo[3]);
		
		System.out.println("Name: " + name + "\nClass: " + playerClass);
		
		maxHealth = 100;
		health = 100;
		x = 0;
		y = 0;
		exp = 0;
		level = 1;		
		expToNextLevel = 100;
		
		speed = 10;
		moveingUp = false;
		moveingDown = false;
		moveingLeft = false;
		moveingRight = false;
		
		spriteLocation = "resources/Sprites/bobB.gif";

		try {
            spriteSheet = ImageIO.read(new File(spriteLocation));
        } catch (IOException ioe) {
            System.out.println("Unable to load image file.");
        }
		if(spriteSheet != null){
			sprite = spriteSheet.getSubimage(0,120,WIDTH,HEIGHT);
		}
	}
	
	public void update()
	{
		if(moveingUp){
			y-=speed;
		}
		if(moveingDown){
			y+=speed;
		}
		if(moveingLeft){
			x-=speed;
		}
		if(moveingRight){
			x+=speed;
		}
		if(x < 0){x=0;}
		if(y < 0){y=0;}
		if(x > GamePanel.WIDTH - this.WIDTH){x = GamePanel.WIDTH - this.WIDTH;}
		if(y > GamePanel.HEIGHT - this.HEIGHT){y = GamePanel.HEIGHT - this.HEIGHT;}
	}
	
	public void draw(Graphics g){
		if(sprite != null){
			setSprite();
			g.drawImage(sprite, x, y, WIDTH, HEIGHT, null);
		} else {
			g.setColor(Color.black);
			g.fillRect(x, y, WIDTH, HEIGHT);
		}
	}
	
	public void setSprite(){
		if(spriteSheet != null){
			if(moveingUp){
				sprite = spriteSheet.getSubimage(0,0,WIDTH,HEIGHT);
			} else if(moveingDown){
				sprite = spriteSheet.getSubimage(0,120,WIDTH,HEIGHT);
			} else if(moveingLeft){
				sprite = spriteSheet.getSubimage(0,180,WIDTH,HEIGHT);
			} else if(moveingRight){
				sprite = spriteSheet.getSubimage(0,60,WIDTH,HEIGHT);
			} else {
				//sprite = spriteSheet.getSubimage(0,120,WIDTH,HEIGHT);
			}
		}
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getWidth()
	{
		return WIDTH;
	}
	
	public int getHeight()
	{
		return HEIGHT;
	}
	
	public void changeX(int xin){
		x+=xin;
	}
	
	public void changeY(int yin){
		y+=yin;
	}
	
	public void moveUp(){
		moveingUp = true;
	}
	
	public void moveDown(){
		moveingDown = true;
	}
	
	public void moveLeft(){
		moveingLeft = true;
	}
	
	public void moveRight(){
		moveingRight = true;
	}
	
	public void stopUp(){
		moveingUp = false;
	}
	
	public void stopDown(){
		moveingDown = false;
	}
	
	public void stopLeft(){
		moveingLeft = false;
	}
	
	public void stopRight(){
		moveingRight = false;
	}
	
//	public Boolean isAlive()
//	{
//		return isAlive;
//	}
//	
//	public int getHealth()
//	{
//		return health;
//	}
//	
//	public int getLevel()
//	{
//		return level;
//	}
//	
//	public int getExp()
//	{
//		return exp;
//	}
//	
//	public void checkIfDead()
//	{
//		if(health <= 0)
//			die();
//	}
//	
//	public void setXDirection(int newX)
//	{
//		xDir = newX;
//	}
//	
//	public void setYDirection(int newY)
//	{
//		yDir = newY;
//	}
//	
//	public void move(int xDir, int yDir)
//	{
//		x += xDir;
//		y += yDir;
//	}
//	
//	public void detectEdges()
//	{
//		if(x < 10)
//			setXDirection(1);
//		else if(x + WIDTH > 1260)
//			setXDirection(-1);
//		
//		if(y < 50)
//			setYDirection(1);
//		else if(y + HEIGHT > 700)
//			setYDirection(-1);
//	}
//	
//	public void checkForLevel()
//	{
//		if(exp >= expToNextLevel)
//		{
//			exp -= expToNextLevel;
//			expToNextLevel += 10;
//			levelUp();
//		}
//	}
//	
//	public void levelUp()
//	{
//		level++;
//		System.out.println("Ding! Level " + level);
//		maxHealth += 50;
//		health = maxHealth;
//	}
//	
//	public void die()
//	{
//		isAlive = false;
//		System.out.println("Oh no :( im ded lul");
//	}
}
