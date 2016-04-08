package client_Model;

public class Armor extends Equipment{
	
	int baseArmor;

	public Armor(String name, Slot slot, Rarity rarity, int armor) 
	{
		super(name, slot, rarity);
		this.baseArmor = armor;
	}
	
	public int getBaseArmor()
	{
		return baseArmor;
	}
}
