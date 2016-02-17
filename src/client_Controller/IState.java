package client_Controller;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;

import client_Model.Player;

public class IState
{
	Player player; 
	Image image; 
	Graphics graphics; 
	JFrame frame;
	
	public IState(Player p, Image i, Graphics g, JFrame f)
	{
		player = p;
		image = i;
		graphics = g;
		frame = f;
	}
	
    public void update()
    {
    	
    }
    
    public void render()
    {
    	
    }
    
    public void onEnter()
    {
    	
    }
    
    public void onExit()
    {
    	
    }
}
