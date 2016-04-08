package client_Model;

public class Consumable extends Item{
	
	String name;
	int restoreAmount;
	Player player;
	
	public Consumable(String name, Player player)
	{
		super(name);
		this.player = player;
	}
	
	public void use()
	{
		
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getRestoreAmount()
	{
		return restoreAmount;
	}
}
