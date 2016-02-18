package client_View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JFrame;

public class GameFrame extends JFrame{
	
	private Image dbImage;
	private Graphics dbGraphics;
	
	int x = 0;
	
	public GameFrame(){
		
	}
	
	public void paint(Graphics g){
		dbImage = createImage(getWidth(), getHeight());
		dbGraphics = dbImage.getGraphics();
		paintComponent(dbGraphics);
		
		g.drawImage(dbImage, 0, 0, this);
	}

	public void paintComponent(Graphics g){
		//THIS IS WHERE THINGS ARE GETTING DRAWN. THIS IS THE IMPORTANT ONE
		g.setColor(Color.black);
		g.fillRect(0, 0, 100, 100);
	}
}

