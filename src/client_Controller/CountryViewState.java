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

public class CountryViewState extends IState
{
	private BufferedImage backGround;
	public CountryViewState(Player p, JFrame f)
	{
		super(p, f);
		try {
			backGround = ImageIO.read(new File("resources/world.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
    public void update()
    {
        // Nothing to update in the empty state.
    }
  
    public void render(Graphics g)
    {
    	Color colorBrown = new Color(0.7f, 0.45f, 0.1f);
		g.setColor(colorBrown);
		g.fillRect(0, 0, 100, 100);
//    	g.drawImage(backGround, 100, 100, null);
    }
  
    public void onEnter()
    {
        // No action to take when the state is entered
    }
  
    public void onExit()
    {
        // No action to take when the state is exited
    }
}