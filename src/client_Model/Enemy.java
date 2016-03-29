package client_Model;

import java.awt.Rectangle;

import client_Controller.CombatState;

public class Enemy {
	
	String name;
	int health, maxHealth, damage;
	boolean alive;
	Rectangle enemyRect;
	public static int WIDTH = 60;
	public static int HEIGHT = 100;
	
	public Enemy(String newName, int newHealth, int newDamage)
	{
		name = newName;
		health = newHealth;
		maxHealth = newHealth;
		damage = newDamage;
		enemyRect = new Rectangle();
		alive = true;
	}
	
	public Rectangle getRect()
	{
		return enemyRect;
	}
	
	public void setRect(int x, int y, int width, int height)
	{
		enemyRect.setBounds(x, y, width, height);
	}
	
	public String getName()
	{
		return name;
	}
	
	public void takeDamage(int damage)
	{
		if(isAlive())
		{
			health -= damage;
			if(health <= 0)
				die();
		}
	}
	
	public void die()
	{
		System.out.println("Ahhhhh...");
		alive = false;
		CombatState.incrementNumEnemiesKilled();
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public int getMaxHealth()
	{
		return maxHealth;
	}
	
	public boolean isAlive()
	{
		return (health > 0);
	}
}
