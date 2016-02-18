package client_Controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import client_Model.Player;

public class KeyListener extends KeyAdapter{
	
	Player player;
	StateMachine sm;

	public KeyListener(StateMachine s) {
		sm = s;
	}
 
	public void keyPressed(KeyEvent e)
	{
		int keyCode = e.getKeyCode();		
		sm.currentState.keyPressed(keyCode);
	}
	
	public void keyReleased(KeyEvent e)
	{
		int keyCode = e.getKeyCode();
		sm.currentState.keyReleased(keyCode);
	}
}
