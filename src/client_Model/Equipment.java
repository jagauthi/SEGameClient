package client_Model;

public class Equipment extends Item{
	
	Slot slot;
	Rarity rarity;

	public Equipment(String name, Slot slot, Rarity rarity)
	{
		super(name);
		this.slot = slot;
		this.rarity = rarity;
	}
	
	public void use()
	{
		//Will eventually be used to switch between player's inventory and equipped items
	}
}
