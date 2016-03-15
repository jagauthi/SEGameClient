package client_Controller;

import java.awt.Image;

public class Tile {
	
	int x, y, type;
	double randomEncounterChance = 0.0;
	public static final int WIDTH = 40;
	public static final int HEIGHT = 40;
	Image tileImage;
	
	public Tile(int newX, int newY, int newType)
	{
		x = newX;
		y = newY;
		type = newType;
		
		if(type == 2)
			randomEncounterChance = 1.0;
		else if(type == 3)
			randomEncounterChance = 2.0;
		else if(type == 4)
			randomEncounterChance = 3.0;
		else if(type == 5)
			randomEncounterChance = 4.0;
		else
			randomEncounterChance = 20.0;
	}

	public int getType()
	{
		return type;
	}
	
	public double getRandomEncounterChance()
	{
		return randomEncounterChance;
	}
}
