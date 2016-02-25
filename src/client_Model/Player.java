package client_Model;
 
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import client_Controller.Animation;
import client_View.GamePanel;

public class Player {
	
	String name, playerClass, loggedIn, gender, abilities, cooldowns;
	String location, clanName;
	int level, health, mana, experience, pointsToSpend, x, y, gold;
	int strength, dexterity, constitution, intelligence, willpower, luck;
	int speed;
	Boolean movingUp, movingDown, movingLeft, movingRight;
	final int WIDTH = 16;
	final int HEIGHT = 26;
	final int SCALE = 2;
	long tookDamageTime;
	
	private Animation animation;
	private BufferedImage spriteSheet;
	private ArrayList<BufferedImage[]> sprites;
	private boolean spritesLoaded;
	private final int numFrames[] = {1, 4, 4, 4, 4};
	private Color eyeColor, eyeColor2;
	
	private int currentAnimation;
	private static final int IDLE = 0;
	private static final int MOVINGUP = 1;
	private static final int MOVINGLEFT = 2;
	private static final int MOVINGDOWN = 3;
	private static final int MOVINGRIGHT = 4;
	private static final int DELAY = 150;
	
	Rectangle playerRect;

	public Player(String[] playerInfo) 
	{
		//The first element of this array is the thing that just says "LoginSuccess", so don't look at the first element in the array when loading player info.
		//"characterInfo", name, class, logggedIn, level, gender, health, mana, exp, pointsToSpend, xCoord, yCoord, 
			//location, clanName, str, dex, con, int, wil, luck, abilities, cooldown
		name = playerInfo[1];
		playerClass = playerInfo[2];
		loggedIn = "1";
		level = Integer.parseInt(playerInfo[4]);
		gender = playerInfo[5];
		health = Integer.parseInt(playerInfo[6]);
		mana = Integer.parseInt(playerInfo[7]);
		experience = Integer.parseInt(playerInfo[8]);
		pointsToSpend = Integer.parseInt(playerInfo[9]);
		x = Integer.parseInt(playerInfo[10]);
		y = Integer.parseInt(playerInfo[11]);
		location = playerInfo[12];
		clanName = playerInfo[13];
		
		strength = Integer.parseInt(playerInfo[14]);
		dexterity = Integer.parseInt(playerInfo[15]);
		constitution = Integer.parseInt(playerInfo[16]);
		intelligence = Integer.parseInt(playerInfo[17]);
		willpower = Integer.parseInt(playerInfo[18]);
		luck = Integer.parseInt(playerInfo[19]);
		
		abilities = playerInfo[20];
		cooldowns = playerInfo[21];
		
		speed = 5;
		movingUp = false;
		movingDown = false;
		movingLeft = false;
		movingRight = false;
		playerRect = new Rectangle(x, y, WIDTH, HEIGHT);
		
		animation = new Animation();
		spritesLoaded = false;
		
		eyeColor = null;
		loadSprites("resources/Sprites/SpriteTemplet.gif");
	}
	
	public boolean loadSprites(String filePath){
		sprites = new ArrayList<BufferedImage[]>();
		try{
			if(filePath == null){
				//Load each of the armor pieces if not specified a sprite page
				//For now this is hard coded but file path will depend on how we do inventory
				BufferedImage head = ImageIO.read(new File("resources/Head/head.gif"));
				BufferedImage arms = ImageIO.read(new File("resources/Arms/arms.gif"));
				BufferedImage chest = ImageIO.read(new File("resources/Chest/chest.gif"));
				BufferedImage legs = ImageIO.read(new File("resources/Legs/legs.gif"));
				BufferedImage feet = ImageIO.read(new File("resources/Feet/feet.gif"));
				
				//Combine each of of the armor pieces into one image
				spriteSheet = new BufferedImage(head.getWidth(), head.getHeight(), BufferedImage.TYPE_INT_ARGB);
				Graphics g = spriteSheet.getGraphics();
				g.drawImage(head, 0, 0, null);
				g.drawImage(arms, 0, 0, null);
				g.drawImage(chest, 0, 0, null);
				g.drawImage(legs, 0, 0, null);
				g.drawImage(feet, 0, 0, null);
			} else {
				//Load spriteSheet if given one
				spriteSheet = ImageIO.read(new File(filePath));
			}
			
			//Slice the spriteSheet into arrays of frames for animations
			for( int n = 0; n < numFrames.length; n++){
				BufferedImage[] temp = new BufferedImage[numFrames[n]];
				for(int m = 0; m < numFrames[n]; m++){
					temp[m] = spriteSheet.getSubimage(WIDTH * m * SCALE, HEIGHT * n * SCALE, WIDTH * SCALE, HEIGHT * SCALE);
				}
				sprites.add(temp);
			}
			
			//default to IDEL animation
			currentAnimation = IDLE;
			animation.setFrames(sprites.get(IDLE));
			animation.setCurrentFrame(0);
			animation.setDelay(-1);
			
			spritesLoaded = true;
		
		} catch(Exception e){
			e.printStackTrace();
			spritesLoaded =  false;
		}
		return spritesLoaded;
		
	}
	
	public String getAllCharInfo()
	{
		//charName, loggedIn, class, level, gender, str, dex, con, int, wil, luck, exp, pointsToSpend, xCoord, yCoord, location, clanName, abilities, cooldowns
		return name + ":" + loggedIn + ":" + playerClass + ":" + level + ":" + gender + ":" + strength + ":" + dexterity + ":" + 
				constitution + ":" + intelligence + ":" + willpower + ":" + luck + ":" + experience + ":" + pointsToSpend + ":" +
				x + ":" + y + ":" + location + ":" + clanName + ":" + abilities + ":" + cooldowns;
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
	
	public Rectangle getPlayerRect()
	{
		return playerRect;
	}
	
	public void setLoggedIn(String newLoggedIn)
	{
		loggedIn = newLoggedIn;
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
	
		//animation Update
		if(spritesLoaded){
			if( !movingDown & movingUp & currentAnimation != MOVINGUP)
			{
				currentAnimation = MOVINGUP;
				animation.setFrames(sprites.get(MOVINGUP));
				animation.setDelay(DELAY);
			} 
			else if(!movingUp & movingDown & currentAnimation != MOVINGDOWN)
			{
				currentAnimation = MOVINGDOWN;
				animation.setFrames(sprites.get(MOVINGDOWN));
				animation.setDelay(DELAY);
			} 
			else if((!movingDown & !movingUp) &&  !movingLeft & movingRight && currentAnimation != MOVINGRIGHT)
			{
				currentAnimation = MOVINGRIGHT;
				animation.setFrames(sprites.get(MOVINGRIGHT));
				animation.setDelay(DELAY);
			} 
			else if((!movingDown & !movingUp) && !movingRight & movingLeft && currentAnimation != MOVINGLEFT)
			{
				currentAnimation = MOVINGLEFT;
				animation.setFrames(sprites.get(MOVINGLEFT));
				animation.setDelay(DELAY);
			} 
			
			else  if((movingDown & movingUp) | (movingRight & movingLeft) | 
					(!movingLeft & !movingRight & !movingUp & !movingDown & currentAnimation != IDLE))
			{
				currentAnimation = IDLE;
				animation.setCurrentFrame(0);
				animation.setDelay(-1);
			}
			animation.update();
		}
		
		playerRect.setBounds(x, y, WIDTH, HEIGHT);
	}
	
	public void draw(Graphics g){
		if(spritesLoaded){
			g.drawImage(animation.getImage() , GamePanel.WIDTH/2, GamePanel.HEIGHT/2, null);
			drawEyes(g);
		}
		else {
			g.setColor(Color.black);
			g.fillRect(GamePanel.WIDTH/2, GamePanel.HEIGHT/2, WIDTH*SCALE, HEIGHT*SCALE);
		}
	}
	
	/*
	 
	 
	 if(sprite != null){
			setSprite();
			g.drawImage(sprite, GamePanel.WIDTH/2, GamePanel.HEIGHT/2, WIDTH, HEIGHT, null);
//			g.drawImage(sprite, x, y, WIDTH, HEIGHT, null);
//			g.setColor(Color.black);
//			g.fillRect(GamePanel.WIDTH/2, GamePanel.HEIGHT/2, WIDTH, HEIGHT);
		} 
		else {
			g.setColor(Color.black);
			g.fillRect(x, y, WIDTH, HEIGHT);
		}
	 
	 
	 */
	
	public void drawEyes(Graphics g){
		if(eyeColor == null){
			return;
		}
		else if(currentAnimation == IDLE | currentAnimation == MOVINGDOWN)
		{
			g.setColor(eyeColor);
			g.fillRect(x+(6*SCALE), y+(8*SCALE), 2, 2);
			g.fillRect(x+(9*SCALE), y+(8*SCALE), 2, 2);
			g.setColor(eyeColor2);
			g.fillRect(x+(6*SCALE), y+(7*SCALE), 2, 2);
			g.fillRect(x+(9*SCALE), y+(7*SCALE), 2, 2);
		} 
		else if (currentAnimation == MOVINGLEFT)
		{
			g.setColor(eyeColor);
			g.fillRect(x+(4*SCALE), y+(8*SCALE), 2, 2);
			g.setColor(eyeColor2);
			g.fillRect(x+(4*SCALE), y+(7*SCALE), 2, 2);
		} 
		else if (currentAnimation == MOVINGRIGHT)
		{
			g.setColor(eyeColor);
			g.fillRect(x+(11*SCALE), y+(8*SCALE), 2, 2);
			g.setColor(eyeColor2);
			g.fillRect(x+(11*SCALE), y+(7*SCALE), 2, 2);
		}
	}
	
	public void changeX(int xin){
		x+=xin;
	}
	
	public void changeY(int yin){
		y+=yin;
	}
	
	public void moveUp(){
		if(movingUp == false){
			movingUp = true;
			animation.setCurrentFrame(1);
		}
	}
	
	public void moveDown(){
		if(movingDown == false){
			movingDown = true;
			animation.setCurrentFrame(1);
		}
	}
	
	public void moveLeft(){
		if(movingLeft == false){
			movingLeft = true;
			animation.setCurrentFrame(1);
		}
	}
	
	public void moveRight(){
		if(movingRight == false){
			movingRight = true;
			animation.setCurrentFrame(1);
		}
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
	
	public void setEyeColor(Color c, Color c2){
		eyeColor = c;
		eyeColor2 = c2;
	}

}
