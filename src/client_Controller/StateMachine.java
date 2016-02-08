package client_Controller;
import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
 
public class StateMachine extends Thread
{
    Map<String, IState> mStates = new HashMap<String, IState>();
    IState mCurrentState;
    EmptyState emptyState = new EmptyState();
    
    /*
     * Not sure if we'll even use these
     */
	private Image dbImage;
	private Graphics dbGraphics;

	int fps, ups;
	Boolean started = false;
     
    public StateMachine()
    {
        mCurrentState = emptyState;
    }
  
    public void update()
    {
        mCurrentState.update();
    }
  
    public void render()
    {
        mCurrentState.render();
    }
  
    public void change(String stateName){
        mCurrentState.onExit();
        mCurrentState = mStates.get(stateName);
    }
  
    public void add(String name, IState state){
        mStates.put(name, state);
    }
    

/*
 * ALL THIS STUFF WILL GO IN THE INDIVIDUAL STATE CLASSES
	 
	public void paint(Graphics g){
		dbImage = createImage(getWidth(), getHeight());
		dbGraphics = dbImage.getGraphics();
		paintComponent(dbGraphics);
		g.drawImage(dbImage, 0, 0, this);
	}
	
	public void paintComponent(Graphics g)
	{
		drawGame(g);
	}
	
	public void drawGame(Graphics g)
	{
		g.setFont(normalfont);
		g.setColor(Color.BLACK);
		g.drawString("HP: " + me.getHealth(), 30, 60);
		g.drawString("Level: " + me.getLevel(), 30, 90);
		g.drawString("Exp: " + me.getExp(), 140, 60);
		
		g.setColor(Color.BLUE);
		if(me.isAlive())
			g.fillRect(me.getX(), me.getY(), me.getWidth(), me.getHeight());
			
		repaint(); //Calls the paintComponent method again to keep updating
	}
*/	
    
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
	         while(delta >= 1)
	         {
	             update();
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
