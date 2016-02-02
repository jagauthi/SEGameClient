package client_Controller;
import java.util.HashMap;
import java.util.Map;
 
public class StateMachine
{
    Map<String, IState> mStates = new HashMap<String, IState>();
    IState mCurrentState;
    EmptyState emptyState = new EmptyState();
     
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
  
    public void change(String stateName)
    {
        mCurrentState.onExit();
        mCurrentState = mStates.get(stateName);
    }
  
    public void add(String name, IState state)
    {
        mStates.put(name, state);
    }
}