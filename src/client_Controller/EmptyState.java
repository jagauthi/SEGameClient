package client_Controller;

import client_Model.Player;

public class EmptyState extends IState
{
	
	public EmptyState(Player p)
	{
		super(p);
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
