package client_View;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import client_Controller.StateMachine;
import client_Model.Player;

public class HUDPanel extends JPanel{
	Image base, portrait;
	NinePatchImage healthBar, manaBar, expBar;
	StateMachine sm;
	JButton levelUpButton;
	Player player;
	Font nameFont, numFont;
	public HUDPanel(Player p, StateMachine sm)
	{
		player = p;
		this.sm = sm;
		
		healthBar = new NinePatchImage(18, 18, 4, 4, sm.healthBarNP);
		manaBar = new NinePatchImage(18, 18, 4, 4, sm.manaBarNP);
		expBar = new NinePatchImage(5, 5, 2, 2, sm.expBarNP);
		base = sm.HUDBase;
		
		nameFont = Launcher.normalFont.deriveFont(25F);
		numFont = Launcher.normalFont.deriveFont(18F);
		
		levelUpButton = new JButton();
		levelUpButton.setBackground(Launcher.TRANSPARENT);
		levelUpButton.setBorder(Launcher.emptyBorder);
		levelUpButton.setIcon(new ImageIcon(sm.HUDLvlUpImage));
		levelUpButton.setBounds(167, 100, sm.HUDLvlUpImage.getWidth(), sm.HUDLvlUpImage.getHeight());
		levelUpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.out.println("HIT ON HUD LEVELUPBUT");
                levelUpButtonClick();
            }
        });
		
		if(player.getPointsToSpend() <= 0)
		{
			levelUpButton.setEnabled(false);
			levelUpButton.setVisible(false);
		}
		
		if(player.getPlayerClass().equals("Mage"))
		{
			portrait = Launcher.magePort;
		}
		else if(player.getPlayerClass().equals("Rogue"))
		{
			portrait = Launcher.roguePort;
		}
		else if(player.getPlayerClass().equals("Warrior"))
		{
			portrait = Launcher.warriorPort;
		}
		
		this.setBounds(0, 0, 300, 133);
		this.setLayout(null);
		this.setBackground(Launcher.TRANSPARENT);
		this.add(levelUpButton);
	}
	
	public void hide()
	{
		levelUpButton.setEnabled(false);
		levelUpButton.setVisible(false);
		sm.doRequestFocus();
	}
	
	public void show()
	{
		if(player.getPointsToSpend() <= 0)
		{
			levelUpButton.setEnabled(false);
			levelUpButton.setVisible(false);
		}
		else
		{
			levelUpButton.setEnabled(true);
			levelUpButton.setVisible(true);
		}
		sm.doRequestFocus();
	}
	
	
	public void levelUpButtonClick()
	{
		if(sm.getCurrentState().equals(sm.getCountryViewState()))
		{
			sm.getCountryViewState().openCharacterSheet();
		}
		sm.doRequestFocus();
	}
	
	public void render(Graphics g)
	{
		float curtHealth = (float)player.getHealth() / (float)player.getMaxHealth();
		float curtMana = (float)player.getMana() / (float)player.getMaxMana();
		float curtExp = (float)player.getExperience() / (float)player.getExpToNextLevel();
		
		g.drawImage(base, 0, 0, null);
		g.drawImage(portrait, 0, 0, 100, 100, null);
		g.drawImage(healthBar.getScaledImage((int)(180 * curtHealth), 18), 107, 34, null);
		g.drawImage(manaBar.getScaledImage((int)(180 * curtMana), 18), 107, 61, null);
		g.drawImage(expBar.getScaledImage((int)(173 * curtExp), 5), 109, 86, null);
		
		g.setColor(Color.BLACK);
		g.setFont(nameFont);
		g.drawString(player.getName(), 105, 28);
		g.setFont(numFont);
		g.drawString(player.getHealth()+" / "+player.getMaxHealth(), 115, 49);
		g.drawString(player.getMana()+" / "+player.getMaxMana(), 115, 76);
		
		if(player.getPointsToSpend() <= 0)
		{
			levelUpButton.setEnabled(false);
			levelUpButton.setVisible(false);
		}
		else
		{
			levelUpButton.setEnabled(true);
			levelUpButton.setVisible(true);
		}
		
	}
}
