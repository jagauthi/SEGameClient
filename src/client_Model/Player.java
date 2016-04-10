package client_Model;
 
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import client_Controller.Animation;
import client_Controller.StateMachine;
import client_Controller.Tile;
import client_View.GamePanel;

public class Player {
	
	String name, playerClass, loggedIn, gender, abilities, cooldowns;
	String location, clanName;
	int level, health, mana, experience, pointsToSpend, x, y, gold;
	int strength, dexterity, constitution, intelligence, willpower, luck;
	int speed;
	Boolean moving;
	final int WIDTH = 20;
	final int HEIGHT = 30;
	final int SCALE = 2;
	long tookDamageTime;
	
	private Animation animation;
	private BufferedImage spriteSheet;
	private ArrayList<BufferedImage[]> sprites;
	private boolean spritesLoaded = false;
	private final int numFrames[] = {4, 4, 4, 4, 4};
	private Color eyeColor, eyeColor2;
	
	private int currentAnimation;
	private int direction;
	
	private static final int MOVINGUP = 0;
	private static final int MOVINGLEFT = 1;
	private static final int MOVINGDOWN = 2;
	private static final int MOVINGRIGHT = 3;
	private static final int IDLE = 4;
	private static final int DELAY = 125;
	
	
	private int[] idleOffset = {0,0,0,0};
	private int[] movingUpOffsetX = {0, 0, 0, 0};
	private int[] movingUpOffsetY = {-Tile.HEIGHT/4, -Tile.HEIGHT/2, -(3*Tile.HEIGHT)/4, -Tile.HEIGHT};
	private int[] movingLeftOffsetX = {-Tile.WIDTH/4, -Tile.WIDTH/2, -(3*Tile.WIDTH)/4, -Tile.WIDTH};
	private int[] movingLeftOffsetY = {0, 0, 0, 0};
	private int[] movingDownOffsetX = {0, 0, 0, 0};
	private int[] movingDownOffsetY = {Tile.HEIGHT/4, Tile.HEIGHT/2, (3*Tile.HEIGHT)/4, Tile.HEIGHT};
	private int[] movingRightOffsetX = {Tile.WIDTH/4, Tile.WIDTH/2, (3*Tile.WIDTH)/4, Tile.WIDTH};
	private int[] movingRightOffsetY = {0, 0, 0, 0};
	
	StateMachine sm;
	ArrayList<String> groupMembers;
	
	ArrayList<Item> inventory;
	ArrayList<Equipment> equippedItems;
	
	Rectangle playerRect;

	public Player(String[] playerInfo, BufferedImage spriteIn) 
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
		
		//Our speed is one tile at a time
		speed = 1;
		moving = false;
		playerRect = new Rectangle(x, y, WIDTH, HEIGHT);
		groupMembers = new ArrayList<String>();
		inventory = new ArrayList<Item>();
		equippedItems = new ArrayList<Equipment>();
		
		inventory.add(new HealthPotion("HealthPotion1", this, 1));
		inventory.add(new HealthPotion("HealthPotion1", this, 1));
		inventory.add(new HealthPotion("HealthPotion1", this, 1));
		inventory.add(new HealthPotion("HealthPotion1", this, 1));
		inventory.add(new HealthPotion("HealthPotion1", this, 1));
		
		equippedItems.add(new Weapon("Iron Sword", Slot.RightHand, Rarity.Common, 10));
		equippedItems.add(new Armor("Iron Helm", Slot.Head, Rarity.Common, 10));
		equippedItems.add(new Armor("Iron Chest", Slot.Head, Rarity.Common, 20));
		equippedItems.add(new Armor("Iron Legs", Slot.Head, Rarity.Common, 15));
		
		spritesLoaded = false;
		animation = new Animation();
		spriteSheet = spriteIn;
		this.sliceSprites();
	}
	
	public void setStateMachine(StateMachine newSm)
	{
		sm = newSm;
	}
	
	private boolean sliceSprites(){
		sprites = new ArrayList<BufferedImage[]>();
		try{
//			Dynamic Armor Stuff
//			if(filePath == null){
//				//Load each of the armor pieces if not specified a sprite page
//				//For now this is hard coded but file path will depend on how we do inventory
//				BufferedImage head = ImageIO.read(new File("resources/Head/head.gif"));
//				BufferedImage arms = ImageIO.read(new File("resources/Arms/arms.gif"));
//				BufferedImage chest = ImageIO.read(new File("resources/Chest/chest.gif"));
//				BufferedImage legs = ImageIO.read(new File("resources/Legs/legs.gif"));
//				BufferedImage feet = ImageIO.read(new File("resources/Feet/feet.gif"));
//				
//				//Combine each of of the armor pieces into one image
//				spriteSheet = new BufferedImage(head.getWidth(), head.getHeight(), BufferedImage.TYPE_INT_ARGB);
//				Graphics g = spriteSheet.getGraphics();
//				g.drawImage(head, 0, 0, null);
//				g.drawImage(arms, 0, 0, null);
//				g.drawImage(chest, 0, 0, null);
//				g.drawImage(legs, 0, 0, null);
//				g.drawImage(feet, 0, 0, null);
//			} else {
//				//Load spriteSheet if given one
//				spriteSheet = ImageIO.read(new File(filePath));
//			}
			
			
			//Slice the spriteSheet into arrays of frames for animations
			for( int n = 0; n < numFrames.length; n++)
			{
				BufferedImage[] temp = new BufferedImage[numFrames[n]];
				for(int m = 0; m < numFrames[n]; m++)
				{
					temp[m] = spriteSheet.getSubimage(WIDTH * m * SCALE, HEIGHT * n * SCALE, WIDTH * SCALE, HEIGHT * SCALE);
				}
				sprites.add(temp);
			}
			
			//default to IDEL animation
			currentAnimation = IDLE;
			animation.setFrames(sprites.get(IDLE));
			animation.setCurrentFrame(MOVINGDOWN);
			animation.setDelay(-1);
			animation.setOffsetX(idleOffset);
			animation.setOffsetY(idleOffset);
			
			spritesLoaded = true;
		
		} catch(Exception e){
			e.printStackTrace();
			spritesLoaded =  false;
		}
		return spritesLoaded;
		
	}
	
	public void update()
	{
		if(moving & animation.getHasPlayedOnce()){
			updateOncePerMove();
		}
		
		playerRect.setBounds(x, y, 1, 1);
	}
	
	public void updateOncePerMove()
	{
		currentAnimation = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setCurrentFrame(direction);
		animation.setOffsetX(idleOffset);
		animation.setOffsetY(idleOffset);
		animation.setDelay(-1);
		animation.resetHasPlayedOnce();
		if(direction == MOVINGUP){
			y -= speed;
		} else if (direction == MOVINGLEFT){
			x -= speed;
		} else if (direction == MOVINGDOWN){
			y += speed;
		} else if (direction == MOVINGRIGHT){
			x += speed;
		}
		moving = false;
		sm.sendServerMyPosition();
		sm.getCurrentState().calculateRandomEncounterChance();
	}
	
	public String getLocationInfo()
	{
		String message = name + "#" + x + "#" + y + "#" + location;
		return message;
	}
	
	public void animationUpdate(){
		animation.update();
	}
	
	public void draw(Graphics g){
		if(spritesLoaded){
			g.drawImage(animation.getImage() , GamePanel.WIDTH/2 - (SCALE * this.WIDTH/2), GamePanel.HEIGHT/2 - (SCALE * this.WIDTH/2) - SCALE*(this.HEIGHT - this.WIDTH), null);
			drawEyes(g);
		}
		else {
			g.setColor(Color.black);
			//Just drawing the players box to be more similar to how the sprite will look.
			g.fillRect(GamePanel.WIDTH/2 - (SCALE * this.WIDTH/2), GamePanel.HEIGHT/2 - (SCALE * this.WIDTH/2) - SCALE*(this.HEIGHT - this.WIDTH), WIDTH*SCALE, 50);
		}
	}
	
	public String getAllCharInfo()
	{
		//charName, loggedIn, class, level, gender, health, mana, str, dex, con, int, wil, luck, exp, pointsToSpend, xCoord, yCoord, location, clanName, abilities, cooldowns
		return name + "#" + loggedIn + "#" + playerClass + "#" + level + "#" + gender + "#" + health + "#" + mana + "#" + strength + "#" + dexterity + "#" + 
				constitution + "#" + intelligence + "#" + willpower + "#" + luck + "#" + experience + "#" + pointsToSpend + "#" +
				x + "#" + y + "#" + location + "#" + clanName + "#" + abilities + "#" + cooldowns;
	}
	
	public int getAnimationOffsetX(){
		return animation.getOffsetX();
	}
	
	public int getAnimationOffsetY(){
		return animation.getOffsetY();
	}
	
	public BufferedImage getImage()
	{
		return animation.getImage();
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
	
	public String getLocation()
	{
		return location;
	}
	
	public void setLocation(String newLocation)
	{
		location = newLocation;
	}
	
	public int getPointsToSpend()
	{
		return pointsToSpend;
	}
	
	public void setPointsToSpend(int n)
	{
		pointsToSpend = n;
	}
	
	public void takeDamage(int damage)
	{
		if(isAlive())
		{
			health -= damage;
			if(health <= 0)
				System.out.println("Oh no... I'm dead...");
		}
	}
	
	public ArrayList<Item> getInventory()
	{
		return inventory;
	}
	
	public Item getInventoryItem(int n)
	{
		try{
			return inventory.get(n);
		}
		catch(IndexOutOfBoundsException e){
			System.out.println("Inventory slot " + n + " is empty");
		}
		//Shouldn't ever get to this null value...
		return null;
	}
	
	public void useInventoryItem(int n)
	{
		try{
			inventory.get(n).use();
			inventory.remove(n);
		}
		catch(IndexOutOfBoundsException e){
			System.out.println("Inventory slot " + n + " is empty");
		}
	}
	
	public boolean isAlive()
	{
		if(health > 0)
			return true;
		else
			return false;
	}
	
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
		if(moving == false & currentAnimation == IDLE){
			moving = true;
			currentAnimation = MOVINGUP;
			direction = MOVINGUP;
			animation.setFrames(sprites.get(MOVINGUP));
			animation.setDelay(DELAY);
			animation.setOffsetX(movingUpOffsetX);
			animation.setOffsetY(movingUpOffsetY);
		}
	}
	
	public void moveDown(){
		if(moving == false & currentAnimation == IDLE){
			moving = true;
			currentAnimation = MOVINGDOWN;
			direction = MOVINGDOWN;
			animation.setFrames(sprites.get(MOVINGDOWN));
			animation.setDelay(DELAY);
			animation.setOffsetX(movingDownOffsetX);
			animation.setOffsetY(movingDownOffsetY);
		}
	}
	
	public void moveLeft(){
		if(moving == false & currentAnimation == IDLE){
			moving = true;
			currentAnimation = MOVINGLEFT;
			direction = MOVINGLEFT;
			animation.setFrames(sprites.get(MOVINGLEFT));
			animation.setDelay(DELAY);
			animation.setOffsetX(movingLeftOffsetX);
			animation.setOffsetY(movingLeftOffsetY);
		}
	}
	
	public void moveRight(){
		if(moving == false & currentAnimation == IDLE){
			moving = true;
			currentAnimation = MOVINGRIGHT;
			direction = MOVINGRIGHT;
			animation.setFrames(sprites.get(MOVINGRIGHT));
			animation.setDelay(DELAY);
			animation.setOffsetX(movingRightOffsetX);
			animation.setOffsetY(movingRightOffsetY);
		}
	}
	
	public void stopUp(){
		//movingUp = false;
	}
	
	public void stopDown(){
		//movingDown = false;
	}
	
	public void stopLeft(){
		//movingLeft = false;
	}
	
	public void stopRight(){
		//movingRight = false;
	}
	
	public void setEyeColor(Color c, Color c2){
		eyeColor = c;
		eyeColor2 = c2;
	}
	
	public int getMeleeDamage()
	{
		if(playerClass.equals("Warrior"))
			return (int) (strength*1.5);
		else if(playerClass.equals("Rogue"))
			return strength + dexterity;
		else
			return strength;
	}
	
	public int getMagicDamage()
	{
		if(mana > 10)
		{
			mana -= 10;
			if(playerClass.equals("Mage"))
				return (int) (intelligence*1.5);
			else
				return strength;
		}
		else
		{
			System.out.println("Not enough mana!");
			return 0;
		}
	}

	public void gainExperience(int xp)
	{
		experience += xp;
		if(experience > getExpToNextLevel())
			levelUp();
	}
	
	public void levelUp()
	{
		level++;
		pointsToSpend += 5;
		health = getMaxHealth();
		mana = getMaxMana();
		System.out.println("Ding! Leveled up :)");
	}
	
	public int getMaxHealth()
	{
		/*
		if(playerClass.equals("Mage"))
			return (20*level) + constitution;
		else if(playerClass.equals("Rogue"))
			return (int) ((20*level) + constitution * 1.5);
		else
			return (20*level) + constitution * 2;
			*/
		return (10*level) + (10*constitution);
	}
	
	public int getMaxMana()
	{
		if(playerClass.equals("Warrior"))
			return intelligence;
		else if(playerClass.equals("Rogue"))
			return intelligence * 2;
		else
			return intelligence * 3;
	}
	
	public int getExpToNextLevel()
	{
		return (level*50) * level;
	}
}
