package client_Controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import client_Model.Player;
import client_View.GameFrame;

public class CountryViewState extends IState
{
	int x = 1;
	private BufferedImage backGround;
	public CountryViewState(Player p, GameFrame f)
	{
		super(p, f);
		try {
			backGround = ImageIO.read(new File("resources/map.jpg"));
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
    public void update()
    {
    	
    }
  
    public void render(Graphics g)
    {
    	Color colorBrown = new Color(0.7f, 0.45f, 0.1f);
		g.setColor(colorBrown);
		g.fillRect(0, 0, 300, 100);
		
    	g.drawImage(backGround, -x, -x, null);
    	x++;
		//screen.paintComponent(g);
    }
  
    public void onEnter()
    {
    	
    }
  
    public void onExit()
    {
    	
    }
}
