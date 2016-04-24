package client_Model;

import java.awt.Rectangle;

public class OtherPlayer {
	
	Rectangle playerRect;
	String name, playerClass;
	int x, y, direction;
	final int WIDTH = 40;
	final int HEIGHT = 60;
	
	public OtherPlayer(String n, int x, int y, int dir, String charClass)
	{
		this.name = n;
		this.x = x;
		this.y = y;
		this.direction = dir;
		this.playerClass = charClass;
		playerRect = new Rectangle(x, y, WIDTH, HEIGHT);
	}

	public Rectangle getPlayerRect()
	{
		return playerRect;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getPlayerClass()
	{
		return playerClass;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getDirection()
	{
		return direction;
	}
	
	public int getWidth()
	{
		return WIDTH;
	}
	
	public int getHeight()
	{
		return HEIGHT;
	}
}
