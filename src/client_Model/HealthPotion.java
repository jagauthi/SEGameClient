package client_Model;

public class HealthPotion extends Consumable{
	
	int level;

	public HealthPotion(String name, Player player, int level) {
		super(name, player);
		this.level = level;
		if(level == 1)
		{
			restoreAmount = 10;
		}
		else if(level == 2)
		{
			restoreAmount = 50;
		}
		else if(level == 3)
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
