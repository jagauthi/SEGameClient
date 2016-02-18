package client_Controller;

import javax.swing.JFrame;

import client_View.GamePanel;
import client_View.Launcher;

public class Main{
	
 	public static void main(String[] args) throws Exception {
 		//new Launcher();
 		JFrame window = new JFrame("SWE Game");
 		window.setContentPane(new GamePanel());
 		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		window.setResizable(false);
 		window.pack();
 		window.setVisible(true);
    }
}
