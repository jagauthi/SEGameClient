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
	
	String name, playerClass, gender, abilities, cooldowns;
	int level, health, mana, experience, x, y, gold;
	int strength, dexterity, constitution, intelligence, willpower, luck;
	
	int speed;
	Boolean movingUp, movingDown, movingLeft, movingRight;
	final int WIDTH = 40;
	final int HEIGHT = 60;
	long tookDamageTime;
	
	String spriteLocation;
	BufferedImage spriteSheet;
	Image sprite;

	public Player(String[] playerInfo) 
	{
		//The first element of this array is the thing that just says "LoginSuccess", so don't look at the first element in the array when loading player info.
		//"characterInfo", name, class, level, gender, health, mana, experience, xCoord, yCoord, gold, 
				// strength, dexterity, constitution, intelligence, willpower, luck, abilities, cooldown
		name = playerInfo[1];
		playerClass = playerInfo[2];
		level = Integer.parseInt(playerInfo[3]);
		gender = playerInfo[4];
		health = Integer.parseInt(playerInfo[5]);
		mana = Integer.parseInt(playerInfo[6]);
		experience = Integer.parseInt(playerInfo[7]);
		x = Integer.parseInt(playerInfo[8]);
		y = Integer.parseInt(playerInfo[9]);
		gold = Integer.parseInt(playerInfo[10]);
		
		strength = Integer.parseInt(playerInfo[11]);
		dexterity = Integer.parseInt(playerInfo[12]);
		constitution = Integer.parseInt(playerInfo[13]);
		intelligence = Integer.parseInt(playerInfo[14]);
		willpower = Integer.parseInt(playerInfo[15]);
		luck = Integer.parseInt(playerInfo[16]);
		
		abilities = "000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
		cooldowns = "000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
		
		speed = 10;
		movingUp = false;
		movingDown = false;
		movingLeft = false;
		movingRight = false;
		
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
	
	public String getAllCharInfo()
	{
		//charName, class, level, gender, str, dex, con, int, wil, luck, exp, xCoord, yCoord, gold, abilities, cooldowns
		return name + ":" + playerClass + ":" + level + ":" + gender + ":" + strength + ":" + dexterity + ":" + 
				constitution + ":" + intelligence + ":" + willpower + ":" + luck + ":" + experience + ":" + 
				x + ":" + y + ":" + gold + ":" + abilities + ":" + cooldowns;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlayerClass() {
		return playerClass;
	}

	public void setPlayerClass(String playerClass) {
		this.playerClass = playerClass;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getDexterity() {
		return dexterity;
	}

	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}

	public int getConstitution() {
		return constitution;
	}

	public void setConstitution(int constitution) {
		this.constitution = constitution;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}

	public int getWillpower() {
		return willpower;
	}

	public void setWillpower(int willpower) {
		this.willpower = willpower;
	}

	public int getLuck() {
		return luck;
	}

	public void setLuck(int luck) {
		this.luck = luck;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Image getSprite() {
		return sprite;
	}

	public void setSprite(Image sprite) {
		this.sprite = sprite;
	}
	
	public int getX()
	{
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public int getY()
	{
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getWidth()
	{
		return WIDTH;
	}
	
	public int getHeight()
	{
		return HEIGHT;
	}

	public void update()
	{
		if(movingUp){
			y-=speed;
		}
		if(movingDown){
			y+=speed;
		}
		if(movingLeft){
			x-=speed;
		}
		if(movingRight){
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
			if(movingUp){
				sprite = spriteSheet.getSubimage(0,0,WIDTH,HEIGHT);
			} else if(movingDown){
				sprite = spriteSheet.getSubimage(0,120,WIDTH,HEIGHT);
			} else if(movingLeft){
				sprite = spriteSheet.getSubimage(0,180,WIDTH,HEIGHT);
			} else if(movingRight){
				sprite = spriteSheet.getSubimage(0,60,WIDTH,HEIGHT);
			} else {
				//sprite = spriteSheet.getSubimage(0,120,WIDTH,HEIGHT);
			}
		}
	}
	
	
	public void changeX(int xin){
		x+=xin;
	}
	
	public void changeY(int yin){
		y+=yin;
	}
	
	public void moveUp(){
		movingUp = true;
	}
	
	public void moveDown(){
		movingDown = true;
	}
	
	public void moveLeft(){
		movingLeft = true;
	}
	
	public void moveRight(){
		movingRight = true;
	}
	
	public void stopUp(){
		movingUp = false;
	}
	
	public void stopDown(){
		movingDown = false;
	}
	
	public void stopLeft(){
		movingLeft = false;
	}
	
	public void stopRight(){
		movingRight = false;
	}
	

}
