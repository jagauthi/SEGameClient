package client_Model;

import java.awt.Rectangle;

public class OtherPlayer {
	
	Rectangle playerRect;
	String name;
	int x, y;
	final int WIDTH = 40;
	final int HEIGHT = 60;
	
	public OtherPlayer(String n, int x, int y)
	{
		name = n;
		this.x = x;
		this.y = y;
		playerRect = new Rectangle(x, y, WIDTH, HEIGHT);
	}

	public Rectangle getPlayerRect()
	{
		return playerRect;
	}
}
