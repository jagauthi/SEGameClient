package client_Controller;

import java.awt.Image;

public class Tile {
	
	int x, y;
	public static final int WIDTH = 40;
	public static final int HEIGHT = 40;
	Image tileImage;
	
	public Tile(int newX, int newY)
	{
		x = newX;
		y = newY;
	}

}
