package client_Controller;
public interface IState
{
    public void update();
    public void render();
    public void onEnter();
    public void onExit();
}