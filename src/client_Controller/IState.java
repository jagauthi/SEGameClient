package client_Controller;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import client_Model.Player;

public class IState
{
	Player player;
	JFrame screen;
	
	public IState(Player p, JFrame f)
	{
		player = p;
		screen = f;
	}
	
    public void update()
    {
    	
    }
    
    public void render(Graphics g)
    {
    	
    }
    
    public void onEnter()
    {
    	
    }
    
    public void onExit()
    {
    	
    }
    
    public void keyPressed(int keyCode){
//    	if(keyCode == KeyEvent.VK_W){
//			player.setYDirection(0);
//		}
    }
    
    public void keyReleased(int keyCode){
    	
    }
}
