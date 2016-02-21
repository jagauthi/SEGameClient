package client_Controller;

import java.awt.image.BufferedImage;

public class Animation {
	private BufferedImage[] frames;
	private int currentFrame;
	private long startTime;
	private long delay;
	
	private Boolean playedOnce;
	
	public Animation(){
		playedOnce = false;
	}
	
	public void setFrames(BufferedImage[] frameIn){
		this.frames = frameIn;
		startTime = System.nanoTime();
		playedOnce = false;
	}
	
	public void setDelay(long delay){this.delay = delay;}
	public void setCurrentFrame(int n){this.currentFrame = n;}
	public int getFrame(){return currentFrame;}
	public BufferedImage getImage(){return frames[currentFrame];}
	public boolean getHasPlayedOnce(){return playedOnce;}
	
	public void update(){
		if(delay == -1) return;
		
		long elipsed = (System.nanoTime() - startTime)/1000000;
		if(elipsed > delay){
			currentFrame++;
			startTime = System.nanoTime();
		}
		if(currentFrame == frames.length){
			currentFrame = 0;
			playedOnce = true;
		}
	}
}
