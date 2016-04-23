package client_Model;

import java.awt.Rectangle;

import client_Controller.CombatState;

public class Enemy {
	
	String name;
	int health, maxHealth, damage, experience;
	boolean alive;
	Rectangle enemyRect;
	public static int WIDTH = 150;
	public static int HEIGHT = 75;
	
	public Enemy(String newName, int newHealth, int newDamage)
	{
		name = newName;
		health = newHealth;
		maxHealth = newHealth;
		damage = newDamage;
		enemyRect = new Rectangle();
		alive = true;
		experience = newHealth/3;
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
	
	public int getXP()
	{
		return experience;
	}
	
	public void attack(Player p)
	{
		System.out.println(name + " attacking!");
		p.takeDamage(damage);
	}
}
