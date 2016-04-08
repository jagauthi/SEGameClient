package client_Model;

public class Consumable extends Item{
	
	String name;
	int restoreAmount;
	Player player;
	
	public Consumable(Player p, String n)
	{
		player = p;
		name = n;
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
