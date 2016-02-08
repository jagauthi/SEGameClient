package client_Controller;

import client_Model.*;
import client_View.Launcher;

import java.awt.Color;

import javax.swing.JFrame;

public class Main extends JFrame{
	
	private static final long serialVersionUID = 1L;
  
	final int WIDTH = 1280;
	final int HEIGHT = 720;
	
	Player me;

	public Main(){
		me = new Player(this);
		addKeyListener(new KeyListener(this, me));
		setTitle("SE Game");
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.GREEN);
	} 
	
 	public static void main(String[] args) throws Exception {
 		new Launcher();
    }
 	
}
