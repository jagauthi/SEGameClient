package client_Model;

public class Weapon extends Equipment{
	
	int baseDamage;

	public Weapon(String name, Slot slot, Rarity rarity, int damage) 
	{
		super(name, slot, rarity);
		this.baseDamage = damage;
	}
	
	public int getBaseDamage()
	{
		return baseDamage;
	}
}
