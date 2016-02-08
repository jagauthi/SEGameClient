package client_Controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import client_Model.Player;

public class KeyListener extends KeyAdapter{
	
	Player player;
	Main game;

	public KeyListener(Main g, Player me) {
		game = g;
		player = me;
	}
 
	public void keyPressed(KeyEvent e)
	{
		int keyCode = e.getKeyCode();
			
		if(keyCode == KeyEvent.VK_W){
			player.setYDirection(-6);
		}
			
		if(keyCode == KeyEvent.VK_A){
			player.setXDirection(-6);
		}
			
		if(keyCode == KeyEvent.VK_S){
			player.setYDirection(6);
		}
			
		if(keyCode == KeyEvent.VK_D){
			player.setXDirection(6);
		}
		
		if(keyCode == KeyEvent.VK_SPACE){

		}
	}
	
	public void keyReleased(KeyEvent e)
	{
		int keyCode = e.getKeyCode();
	
		if(keyCode == KeyEvent.VK_W){
			player.setYDirection(0);
		}
		
		if(keyCode == KeyEvent.VK_A){
			player.setXDirection(0);
		}
		
		if(keyCode == KeyEvent.VK_S){
			player.setYDirection(0);
		}
		
		if(keyCode == KeyEvent.VK_D){
			player.setXDirection(0);
		}
	}
}
