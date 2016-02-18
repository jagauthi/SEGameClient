package client_Controller;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import client_Model.Player;
import client_View.GameFrame;
 
public class StateMachine extends Thread
{
	//States
    Map<String, IState> states = new HashMap<String, IState>();
    IState currentState;
    EmptyState emptyState;
	CountryViewState countryViewState;
	
	final int WIDTH = 1280;
	final int HEIGHT = 720;
	
	GameFrame screen;

	int fps, ups;
	Boolean started = false;
	
	Player player;
	
    public StateMachine(String playerInfo)
    {
        player = new Player(playerInfo);
        screen = new GameFrame();
        screen.addKeyListener(new KeyListener(this));
        screen.setTitle("SE Game");
        screen.setSize(WIDTH, HEIGHT);
        screen.setResizable(false);
        screen.setVisible(true);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setBackground(Color.GREEN);

    	emptyState = new EmptyState(player, screen);
    	countryViewState = new CountryViewState(player, screen);
    	add("Empty State", emptyState);
    	add("Country View State", countryViewState);
    	
        currentState = countryViewState;
        start();
    }
  
    public void update()
    {
        currentState.update();
    }
  
    public void render()
    {
        currentState.render(screen.getGraphics());
        //screen.paintComponent(dbGraphics);
        //screen.paint(dbGraphics);
        screen.repaint();
    }
  
    public void change(String stateName){
        currentState.onExit();
        currentState = states.get(stateName);
    }
  
    public void add(String name, IState state){
        states.put(name, state);
    }
    
    public void run(){
    	 long lastTime = System.nanoTime();
	     long timer = System.currentTimeMillis();
	     final double ns = 1000000000.0 / 60.0;
	     double delta = 0;
	     int frames = 0;
	     int updates = 0;
	    
	     while(true)
	     {
	         long now = System.nanoTime();
	         delta += (now - lastTime) / ns;
	         lastTime = now;
	         //while(delta >= 1)
	         {
	             update();
	             render();
	             updates++;
	             delta --;
	         }
	         frames++;
	            
	         if(System.currentTimeMillis() - timer > 1000)
	         {
	             timer += 1000;
	         //  System.out.println(updates + " updates, " + frames + "fps");
	             fps = frames;
	             ups = updates;
	             updates = 0;
	             frames = 0;
	         }
	     }
    }
}
