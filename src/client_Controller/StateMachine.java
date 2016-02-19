package client_Controller;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import client_Model.OtherPlayer;
import client_Model.Player;
 
public class StateMachine //extends Thread
{
	//States
    Map<String, IState> states = new HashMap<String, IState>();
    IState currentState;
    EmptyState emptyState;
	CountryViewState countryViewState;
	BlueState blueState;
	//HashMap<String, OtherPlayer> otherPlayers = new HashMap<String, OtherPlayer>();
	public static ArrayList<OtherPlayer> otherPlayers = new ArrayList<OtherPlayer>();

	Boolean started = false;
	
	Player player;
	
	ChatClient client;
	
    public StateMachine(String[] playerInfo, ChatClient c)
    {	
        player = new Player(playerInfo);
        client = c;
        client.setStateMachine(this);
    	countryViewState = new CountryViewState(player);
    	blueState = new BlueState(player);
    	this.add("Country View State", countryViewState);
        currentState = countryViewState;
    }
  
    public void update()
    {
        currentState.update();
        //client.sendMessage("UPDATECHARINFO:" + player.getAllCharInfo());
        //System.out.println("UPDATECHARINFO:" + player.getAllCharInfo());
    }
    
    public void oncePerSecondUpdate()
    {
    	client.sendMessage("UPDATECHARINFO:" + player.getAllCharInfo());
    }
    
    /*
     * NOT SURE IF THIS IS THE BEST WAY TO DO THIS, SOMONE ELSE LOOK AT THIS.
     */
    public void updateCharsAroundMe(String[] charsAroundMe)
    {
    	otherPlayers.clear();
    	if(charsAroundMe.length > 1)
    	{
	    	for(int i = 1; i < charsAroundMe.length; i++)
	    	{
	    		String[] otherGuy = charsAroundMe[i].split(" ");
	    		otherPlayers.add(new OtherPlayer(charsAroundMe[i], 
	    				Integer.parseInt(otherGuy[1]), Integer.parseInt(otherGuy[2])));
	    	}
    	}
    }
  
    public void render(Graphics g)
    {
        currentState.render(g);
    }
  
    public void change(String stateName){
        currentState.onExit();
        currentState = states.get(stateName);
    }
  
    public void add(String name, IState state){
        states.put(name, state);
    }
    
    public IState getCurrentState(){
    	return currentState;
    }
    
    public void changeState(){
    	if(currentState == countryViewState){
    		currentState = blueState;
    	}
    	else
    		currentState =countryViewState;
    }
    
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
    
//    public void run(){
////    	 long lastTime = System.nanoTime();
////	     long timer = System.currentTimeMillis();
////	     final double ns = 1000000000.0 / 60.0;
////	     double delta = 0;
////	     int frames = 0;
////	     int updates = 0;
//	    
//	     while(true)
//	     {
////	         long now = System.nanoTime();
////	         delta += (now - lastTime) / ns;
////	         lastTime = now;
//	         //while(delta >= 1)
//	         {
//	             update();
//	             render();
////	             updates++;
////	             delta --;
//	         }
////	         frames++;
//	            
////	         if(System.currentTimeMillis() - timer > 1000)
////	         {
////	             timer += 1000;
////	         //  System.out.println(updates + " updates, " + frames + "fps");
////	             fps = frames;
////	             ups = updates;
////	             updates = 0;
////	             frames = 0;
////	         }
//	     }
//    }
//}
