package client_Model;

import java.awt.Rectangle;

public class Enemy {
	
	String name;
	int health, damage;
	Rectangle enemyRect;
	public static int WIDTH = 30;
	public static int HEIGHT = 60;
	
	public Enemy(String newName, int newHealth, int newDamage)
	{
		name = newName;
		health = newHealth;
		damage = newDamage;
		enemyRect = new Rectangle();
	}
	
	public Rectangle getRect()
	{
		return enemyRect;
	}
	
	public void setRect(int x, int y, int width, int height)
	{
		enemyRect.setBounds(x, y, width, height);
	}
}
