package client_Model;

public class HealthPotion extends Consumable{
	
	int level;

	public HealthPotion(Player p, String n, int l) {
		super(p, n);
		level = l;
		if(l == 1)
		{
			restoreAmount = 10;
		}
		else if(l == 2)
		{
			restoreAmount = 50;
		}
		else if(l == 3)
		{
			restoreAmount = 100;
		}
	}

	public void use()
	{
		player.setHealth(player.getHealth() + restoreAmount);
		if(player.getHealth() > player.getMaxHealth())
			player.setHealth(player.getMaxHealth());
	}
}
