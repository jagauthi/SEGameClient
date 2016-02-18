package client_Controller;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;

import client_Model.Player;
import client_View.GameFrame;

public class EmptyState extends IState
{
	
	public EmptyState(Player p, GameFrame f)
	{
		super(p, f);
	}
    public void update()
    {
        // Nothing to update in the empty state.
    }
  
    public void render()
    {
        // Nothing to render in the empty state
    }
  
    public void onEnter()
    {
        // No action to take when the state is entered
    }
  
    public void onExit()
    {
        // No action to take when the state is exited
    }
}
