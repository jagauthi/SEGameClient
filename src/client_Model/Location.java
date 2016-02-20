package client_Model;

import java.awt.Rectangle;

public class Location {
	
	String name;
	int x, y;
	public final static int WIDTH = 80;
	public final static int HEIGHT = 80;
	Rectangle locRect;
	
	public Location(String n, int x, int y)
	{
		name = n;
		this.x = x;
		this.y = y;
		locRect = new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	public String getName()
	{
		return name;
	}

	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public Rectangle getLocRect()
	{
		return locRect;
	}
}
