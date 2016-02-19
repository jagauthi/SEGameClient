package client_Model;

import java.awt.Rectangle;

public class OtherPlayer {
	
	Rectangle playerRect;
	String name, playerClass;
	int x, y;
	final int WIDTH = 40;
	final int HEIGHT = 60;
	
	public OtherPlayer(String n, String pc, int x, int y)
	{
		name = n;
		playerClass = pc;
		this.x = x;
		this.y = y;
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
	
	public int getWidth()
	{
		return WIDTH;
	}
	
	public int getHeight()
	{
		return HEIGHT;
	}
}
