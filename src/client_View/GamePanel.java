package client_View;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import client_Controller.ChatClient;
import client_Controller.StateMachine;

@SuppressWarnings("serial")
public class GamePanel extends JPanel
	implements Runnable, KeyListener{
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;

	private Thread thread;
	private boolean running;
	
	private int FPS = 60;
	private long targetTime = 1000/FPS;
	
	private StateMachine sm;
	
	public GamePanel(String[] playerInfo, ChatClient client){
		super();
		setLayout(null);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
    	sm = new StateMachine(playerInfo, client, this);
		addKeyListener(this);
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void doRequestFocus()
	{
		requestFocus();
	}
	
	public void addComponent(JComponent comp)
	{
		this.add(comp);
	}
	
	public void removeComponent(JComponent comp)
	{
		this.remove(comp);
	}
	
	@Override
	public void run() {
		long start;

        long timer = System.currentTimeMillis();
        
		long elapsed;
		long wait;
		
		while(running){
			
			//this.requestFocus();
			
			if(System.currentTimeMillis() - timer > 1000)
            {
				//if we make timer+=1000, it will go off once per second.
				//if we make timer+=500, it will go off twice per second.
                timer += 500;
                sm.oncePerSecondUpdate();
            }
			
			start = System.nanoTime();
			
			update();
			repaint();
			SwingUtilities.windowForComponent(this).pack();
			
			elapsed = System.nanoTime() - start;
			
			wait = targetTime - elapsed / 1000000;
			if(wait < 0){
				wait = 5;
			}
			try {
				Thread.sleep(wait);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private void update(){
		sm.update();
	}
	
	public void paintComponent(Graphics g){
		sm.render(g);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		sm.getCurrentState().keyPressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		sm.getCurrentState().keyReleased(e.getKeyCode());
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
