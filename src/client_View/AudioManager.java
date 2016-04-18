package client_View;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioManager {
	
	long pauseTime;
	Clip clip;
	AudioInputStream ais;
	boolean playing;
	
	public AudioManager()
	{
		pauseTime = 0;
		playing = false;
	}

	public void playSong(String Filename)
	{
		try{
	        File f = new File(Filename);
	        clip = AudioSystem.getClip();
	        ais = AudioSystem.getAudioInputStream(f);
	        clip.open(ais);
	        clip.loop(Clip.LOOP_CONTINUOUSLY);
	        playing = true;
	    }
		catch(Exception exception){
			System.out.println("Failed To Play The WAV File!");
		}
	}
	
	public void pauseSong()
	{
		pauseTime = clip.getMicrosecondPosition();
		clip.stop();
		playing = false;
	}
	
	public void resumeSong()
	{
		clip.setMicrosecondPosition(pauseTime);
		clip.start();
		playing = true;
	}
	
	public void stopSong()
	{
		clip.stop();
		playing = false;
	}
	
	public Boolean isPlaying()
	{
		return playing;
	}
}
