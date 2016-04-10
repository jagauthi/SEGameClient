package client_View;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import client_Controller.StateMachine;
import client_Model.Player;

public class CharacterSheetPanel extends BackgroundedPanel{
	public static final int WIDTH = 3*GamePanel.WIDTH/8;
	public static final int HEIGHT = 3*GamePanel.HEIGHT/4;
	Player player;
	StateMachine sm;
	JLabel charNameClassLab, healthLab, manaLab, pointsLeftLab;
	JTextField pointsLeftField;
	StatField strengthField, dexterityField, constitutionField,
			intelligenceField, willpowerField, luckField;
	JButton spendPointsBut, comitBut;
	boolean levelingUp;
	int startingPoints;
	int[] statValueArray;
	
	
	public CharacterSheetPanel(Player p, StateMachine smIn)
	{
		super(new NinePatchImage(200,200,18,18, Launcher.npBackground));
		this.setLayout(null);
		this.setBounds(GamePanel.WIDTH/2, GamePanel.HEIGHT/8, WIDTH, HEIGHT);
		player = p;
		sm = smIn;
		
		int cornerW = this.getNinePatch().getCornerWidth();
		int cornerH = this.getNinePatch().getCornerHeight();
		Dimension size;
		
        
		charNameClassLab = new JLabel(player.getName()+"  :  Level "+player.getLevel()+" "+player.getPlayerClass());
		size = charNameClassLab.getPreferredSize();
		charNameClassLab.setBounds(cornerW, cornerH, this.getWidth() - (cornerW *2), size.height);
		charNameClassLab.setHorizontalAlignment(JLabel.CENTER);
		
		healthLab = new JLabel("Health: "+player.getHealth()+"/"+player.getMaxHealth());
		healthLab.setHorizontalAlignment(JLabel.CENTER);
		size = healthLab.getPreferredSize();
		healthLab.setBounds(cornerW, cornerH + charNameClassLab.getHeight(), (this.getWidth() - (cornerW *2))/2, size.height);
		
		manaLab = new JLabel("Mana: "+player.getMana()+"/"+player.getMaxMana());
		manaLab.setHorizontalAlignment(JLabel.CENTER);
		size = manaLab.getPreferredSize();
		manaLab.setBounds(cornerW + healthLab.getWidth(), cornerH + charNameClassLab.getHeight(), (this.getWidth() - (cornerW *2))/2, size.height);
		
		//Stat Values {pointsRemaining, Str, Dex, Con, Int, Will, Luck}
		startingPoints = player.getPointsToSpend();
		statValueArray = new int[]{startingPoints, 0, 0, 0, 0, 0, 0};
		StatField[] statFieldArray = new StatField[6];
		
		pointsLeftLab = new JLabel("Points To Spend: ");
		size = pointsLeftLab.getPreferredSize();
		pointsLeftLab.setBounds(cornerW, healthLab.getY() + healthLab.getHeight()+50, size.width, size.height);
		pointsLeftLab.setHorizontalAlignment(JLabel.CENTER);
		
		pointsLeftField  = new JTextField(2);
		pointsLeftField.setHorizontalAlignment(JTextField.CENTER);
		pointsLeftField.setBackground(Launcher.FAKETRANS);
		pointsLeftField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pointsLeftField.setText(String.valueOf(statValueArray[0]));
		pointsLeftField.setEditable(false);
        size = pointsLeftField.getPreferredSize();
        pointsLeftField.setBounds(pointsLeftLab.getX()+pointsLeftLab.getWidth(), pointsLeftLab.getY(), size.width, size.height);
        
        spendPointsBut = new JButton();
        spendPointsBut.setText("Spend Points");
        spendPointsBut.setFont(Launcher.smallFont);
        spendPointsBut.setHorizontalTextPosition(JButton.CENTER);
        spendPointsBut.setBackground(Launcher.TRANSPARENT);
        spendPointsBut.setBorder(null);
        size = spendPointsBut.getPreferredSize();
        spendPointsBut.setBounds(pointsLeftField.getX()+pointsLeftField.getWidth()+20, pointsLeftField.getY(), size.width+20, size.height+20);
        spendPointsBut.setIcon(new ImageIcon(new NinePatchImage(size.width+20, size.height+20, 7, 7, Launcher.npBasic_50_7).getScaledImage(size.width+20, size.height+20)));
        spendPointsBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setLevelingUp(!levelingUp);
            }
        });
        
        comitBut = new JButton();
        comitBut.setText("Save");
        comitBut.setHorizontalTextPosition(JButton.CENTER);
        comitBut.setBackground(Launcher.TRANSPARENT);
        comitBut.setBorder(null);
        size = comitBut.getPreferredSize();
        comitBut.setBounds(WIDTH - cornerW - size.width - 30, HEIGHT - cornerH - size.height - 30, size.width+20, size.height+20);
        comitBut.setIcon(new ImageIcon(new NinePatchImage(size.width+20, size.height+20, 7, 7, Launcher.npBasic_50_7).getScaledImage(size.width+20, size.height+20)));
        comitBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	spendPoints();
            }
        });
        
        int statSpace = this.getWidth() - cornerW - cornerW - 50;
        
        strengthField = new StatField("Strength", pointsLeftField, statValueArray, statFieldArray, 1, player.getStrength(), StatField.LEVEL_UP);
        size = strengthField.panel.getPreferredSize();
        strengthField.panel.setBounds(statSpace -size.width, pointsLeftLab.getY() + pointsLeftLab.getHeight()+30, size.width+30, size.height-10);
        statFieldArray[0] = strengthField;
        
        dexterityField = new StatField("Dexterity", pointsLeftField, statValueArray, statFieldArray, 2, player.getDexterity(), StatField.LEVEL_UP);
        size = dexterityField.panel.getPreferredSize();
        dexterityField.panel.setBounds(statSpace -size.width, strengthField.panel.getY() + strengthField.panel.getHeight(), size.width+30, size.height-10);
        statFieldArray[1] = dexterityField;
        
        constitutionField = new StatField("Constitution", pointsLeftField, statValueArray, statFieldArray, 3, player.getConstitution(), StatField.LEVEL_UP);
        size = constitutionField.panel.getPreferredSize();
        constitutionField.panel.setBounds(statSpace -size.width, dexterityField.panel.getY() + dexterityField.panel.getHeight(), size.width+30, size.height-10);
        statFieldArray[2] = constitutionField;
        
        intelligenceField = new StatField("Intelligence", pointsLeftField, statValueArray, statFieldArray, 4, player.getIntelligence(), StatField.LEVEL_UP);
        size = intelligenceField.panel.getPreferredSize();
        intelligenceField.panel.setBounds(statSpace -size.width, constitutionField.panel.getY() + constitutionField.panel.getHeight(), size.width+30, size.height-10);
        statFieldArray[3] = intelligenceField;
        
        willpowerField = new StatField("Willpower", pointsLeftField, statValueArray, statFieldArray, 5, player.getWillpower(), StatField.LEVEL_UP);
        size = willpowerField.panel.getPreferredSize();
        willpowerField.panel.setBounds(statSpace -size.width, intelligenceField.panel.getY() + intelligenceField.panel.getHeight(), size.width+30, size.height-10);
        statFieldArray[4] = willpowerField;
        
        luckField = new StatField("Luck", pointsLeftField, statValueArray, statFieldArray, 6, player.getLuck(), StatField.LEVEL_UP);
        size = luckField.panel.getPreferredSize();
        luckField.panel.setBounds(statSpace -size.width, willpowerField.panel.getY() + willpowerField.panel.getHeight(), size.width+30, size.height-10);
        statFieldArray[5] = luckField;
		
		this.add(charNameClassLab);
		this.add(healthLab);
		this.add(manaLab);
		this.add(pointsLeftLab);
		this.add(pointsLeftField);
		this.add(spendPointsBut);
		this.add(strengthField.panel);
		this.add(dexterityField.panel);
		this.add(constitutionField.panel);
		this.add(intelligenceField.panel);
		this.add(willpowerField.panel);
		this.add(luckField.panel);
		this.add(comitBut);
		
		levelingUp = false;
		if(player.getPointsToSpend() == 0)
		{
			spendPointsBut.setVisible(false);
			spendPointsBut.setEnabled(false);
		}
		comitBut.setVisible(levelingUp);
		comitBut.setEnabled(levelingUp);
		strengthField.showPlus(levelingUp);
        dexterityField.showPlus(levelingUp);
        constitutionField.showPlus(levelingUp);
        intelligenceField.showPlus(levelingUp);
        willpowerField.showPlus(levelingUp);
        luckField.showPlus(levelingUp);
	}
	
	public void setLevelingUp(boolean b)
	{
		if(b)
        {
            spendPointsBut.setText("Cancel");
            levelingUp = true;
        }
        else
        {
        	spendPointsBut.setText("Spend Points");
        	statValueArray[0] = startingPoints;
        	statValueArray[1] = 0;
        	statValueArray[2] = 0;
        	statValueArray[3] = 0;
        	statValueArray[4] = 0;
        	statValueArray[5] = 0;
        	statValueArray[6] = 0;
        	pointsLeftField.setText(String.valueOf(statValueArray[0]));
        	strengthField.reset();
            dexterityField.reset();
            constitutionField.reset();
            intelligenceField.reset();
            willpowerField.reset();
            luckField.reset();
        	levelingUp = false;
        }
        comitBut.setVisible(levelingUp);
		comitBut.setEnabled(levelingUp);
        strengthField.showPlus(levelingUp);
        dexterityField.showPlus(levelingUp);
        constitutionField.showPlus(levelingUp);
        intelligenceField.showPlus(levelingUp);
        willpowerField.showPlus(levelingUp);
        luckField.showPlus(levelingUp);
	}
		
	public void spendPoints()
	{
		System.out.println("HIT IN SPEND POINTS");
		player.setStrength(player.getStrength() + statValueArray[1]);
		strengthField.setPointValue(player.getStrength());
		player.setDexterity(player.getDexterity() + statValueArray[2]);
		dexterityField.setPointValue(player.getDexterity());
		player.setConstitution(player.getConstitution() + statValueArray[3]);
		constitutionField.setPointValue(player.getConstitution());
		player.setIntelligence(player.getIntelligence() + statValueArray[4]);
		intelligenceField.setPointValue(player.getIntelligence());
		player.setWillpower(player.getWillpower() + statValueArray[5]);
		willpowerField.setPointValue(player.getWillpower());
		player.setLuck(player.getLuck() + statValueArray[6]);
		luckField.setPointValue(player.getLuck());
		
		player.setPointsToSpend(statValueArray[0]);
		
		spendPointsBut.setText("Spend Points");
		if(player.getPointsToSpend() == 0)
		{
			spendPointsBut.setVisible(false);
			spendPointsBut.setEnabled(false);
		}
		
		startingPoints = statValueArray[0];
    	statValueArray[1] = 0;
    	statValueArray[2] = 0;
    	statValueArray[3] = 0;
    	statValueArray[4] = 0;
    	statValueArray[5] = 0;
    	statValueArray[6] = 0;
    	pointsLeftField.setText(String.valueOf(statValueArray[0]));
    	strengthField.reset();
        dexterityField.reset();
        constitutionField.reset();
        intelligenceField.reset();
        willpowerField.reset();
        luckField.reset();
    	setLevelingUp(false);
	
	    //sm.client.sendMessage("UPDATECHARINFO#" + player.getAllCharInfo());
	}
}
		
		
		
//		JLabel strengthValue = new JLabel("" + player.getStrength());
//		JLabel dexterityValue = new JLabel("" + player.getDexterity());
//		JLabel constitutionValue = new JLabel("" + player.getConstitution());
//		JLabel intelligenceValue = new JLabel("" + player.getIntelligence());
//		JLabel willpowerValue = new JLabel("" + player.getWillpower());
//		JLabel luckValue = new JLabel("" + player.getLuck());
//		JLabel pointsToSpend = new JLabel("Points to spend: " + player.getPointsToSpend());
		
		
//		charName.setBounds(10, 0, 200, 20);
//		characterSheetPanel.add(charName);
//		health.setBounds(10, 20, 200, 20);
//		characterSheetPanel.add(health);
//		mana.setBounds(10, 40, 200, 20);
//		characterSheetPanel.add(mana);
//		pointsToSpend.setBounds(10, 80, 200, 20);
//		characterSheetPanel.add(pointsToSpend);
//		
//		strength.setBounds(10, 120, 200, 20);
//		characterSheetPanel.add(strength);
//		dexterity.setBounds(10, 140, 200, 20);
//		characterSheetPanel.add(dexterity);
//		constitution.setBounds(10, 160, 200, 20);
//		characterSheetPanel.add(constitution);
//		intelligence.setBounds(10, 180, 200, 20);
//		characterSheetPanel.add(intelligence);
//		willpower.setBounds(10, 200, 200, 20);
//		characterSheetPanel.add(willpower);
//		luck.setBounds(10, 220, 200, 20);
//		characterSheetPanel.add(luck);
//		
//		strengthValue.setBounds(110, 120, 200, 20);
//		characterSheetPanel.add(strengthValue);
//		dexterityValue.setBounds(110, 140, 200, 20);
//		characterSheetPanel.add(dexterityValue);
//		constitutionValue.setBounds(110, 160, 200, 20);
//		characterSheetPanel.add(constitutionValue);
//		intelligenceValue.setBounds(110, 180, 200, 20);
//		characterSheetPanel.add(intelligenceValue);
//		willpowerValue.setBounds(110, 200, 200, 20);
//		characterSheetPanel.add(willpowerValue);
//		luckValue.setBounds(110, 220, 200, 20);
//		characterSheetPanel.add(luckValue);
//		
//		boolean pointsBeingSpent = false;
//		if((levelUpStrCount > 0) || (levelUpDexCount > 0) || (levelUpConCount > 0) ||
//				(levelUpIntCount > 0) || (levelUpWilCount > 0) || (levelUpLckCount > 0))
//			pointsBeingSpent = true;
//		
//		if(player.getPointsToSpend() > 0 || pointsBeingSpent)
//		{
//			strengthPlusButton.setBounds(175, 120, 20, 20);
//	    	characterSheetPanel.add(strengthPlusButton);
//	    	strengthMinusButton.setBounds(150, 120, 20, 20);
//	    	characterSheetPanel.add(strengthMinusButton);
//	    	
//	    	dexterityPlusButton.setBounds(175, 140, 20, 20);
//	    	characterSheetPanel.add(dexterityPlusButton);
//	    	dexterityMinusButton.setBounds(150, 140, 20, 20);
//	    	characterSheetPanel.add(dexterityMinusButton);
//	    	
//	    	constitutionPlusButton.setBounds(175, 160, 20, 20);
//	    	characterSheetPanel.add(constitutionPlusButton);
//	    	constitutionMinusButton.setBounds(150, 160, 20, 20);
//	    	characterSheetPanel.add(constitutionMinusButton);
//	    	
//	    	intelligencePlusButton.setBounds(175, 180, 20, 20);
//	    	characterSheetPanel.add(intelligencePlusButton);
//	    	intelligenceMinusButton.setBounds(150, 180, 20, 20);
//	    	characterSheetPanel.add(intelligenceMinusButton);
//	    	
//	    	willpowerPlusButton.setBounds(175, 200, 20, 20);
//	    	characterSheetPanel.add(willpowerPlusButton);
//	    	willpowerMinusButton.setBounds(150, 200, 20, 20);
//	    	characterSheetPanel.add(willpowerMinusButton);
//	    	
//	    	luckPlusButton.setBounds(175, 220, 20, 20);
//	    	characterSheetPanel.add(luckPlusButton);
//	    	luckMinusButton.setBounds(150, 220, 20, 20);
//	    	characterSheetPanel.add(luckMinusButton);
//		}
//		
//		JLabel strCount = new JLabel("+" + levelUpStrCount);
//		JLabel dexCount = new JLabel("+" + levelUpDexCount);
//		JLabel conCount = new JLabel("+" + levelUpConCount);
//		JLabel intCount = new JLabel("+" + levelUpIntCount);
//		JLabel wilCount = new JLabel("+" + levelUpWilCount);
//		JLabel lckCount = new JLabel("+" + levelUpLckCount);
//		
//		if(levelUpStrCount > 0)
//		{
//			strCount.setBounds(200, 120, 20, 20);
//	    	characterSheetPanel.add(strCount);
//		}
//		else
//		{
//			characterSheetPanel.remove(strCount);
//		}
//		
//		if(levelUpDexCount > 0)
//		{
//			dexCount.setBounds(200, 140, 20, 20);
//	    	characterSheetPanel.add(dexCount);
//		}
//		else
//		{
//			characterSheetPanel.remove(dexCount);
//		}
//		
//		if(levelUpConCount > 0)
//		{
//			conCount.setBounds(200, 160, 20, 20);
//	    	characterSheetPanel.add(conCount);
//		}
//		else
//		{
//			characterSheetPanel.remove(conCount);
//		}
//		
//		if(levelUpIntCount > 0)
//		{
//			intCount.setBounds(200, 180, 20, 20);
//	    	characterSheetPanel.add(intCount);
//		}
//		else
//		{
//			characterSheetPanel.remove(intCount);
//		}
//		
//		if(levelUpWilCount > 0)
//		{
//			wilCount.setBounds(200, 200, 20, 20);
//	    	characterSheetPanel.add(wilCount);
//		}
//		else
//		{
//			characterSheetPanel.remove(wilCount);
//		}
//		
//		if(levelUpLckCount > 0)
//		{
//			lckCount.setBounds(200, 220, 20, 20);
//	    	characterSheetPanel.add(lckCount);
//		}
//		else
//		{
//			characterSheetPanel.remove(lckCount);
//		}
//		
//		if(pointsBeingSpent)
//		{
//	    	characterSheetPanel.add(spendPointButton);
//		}
//		else
//		{
//			characterSheetPanel.remove(spendPointButton);
//		}
//	}
//	
//	public void addStat(String stat)
//	{
//		if(player.getPointsToSpend() > 0)
//		{
//	    	if(stat.equals("Str"))
//	    		levelUpStrCount++;
//	    	else if(stat.equals("Dex"))
//	    		levelUpDexCount++;
//	    	else if(stat.equals("Con"))
//	    		levelUpConCount++;
//	    	else if(stat.equals("Int"))
//	    		levelUpIntCount++;
//	    	else if(stat.equals("Wil"))
//	    		levelUpWilCount++;
//	    	else if(stat.equals("Lck"))
//	    		levelUpLckCount++;
//		
//	    	player.setPointsToSpend(player.getPointsToSpend()-1);
//		}
//		setUpCharacterSheet();
//	}
//	
//	public void subtractStat(String stat)
//	{
//		if(stat.equals("Str"))
//		{
//			if(levelUpStrCount > 0)
//			{
//				levelUpStrCount--;
//				player.setPointsToSpend(player.getPointsToSpend()+1);
//			}
//		}
//		else if(stat.equals("Dex"))
//		{
//			if(levelUpDexCount > 0)
//			{
//				levelUpDexCount--;
//				player.setPointsToSpend(player.getPointsToSpend()+1);
//			}
//		}
//		else if(stat.equals("Con"))
//		{
//			if(levelUpConCount > 0)
//			{
//				levelUpConCount--;
//				player.setPointsToSpend(player.getPointsToSpend()+1);
//			}
//		}
//		else if(stat.equals("Int"))
//		{
//			if(levelUpIntCount > 0)
//			{
//				levelUpIntCount--;
//				player.setPointsToSpend(player.getPointsToSpend()+1);
//			}
//		}
//		else if(stat.equals("Wil"))
//		{
//			if(levelUpWilCount > 0)
//			{
//				levelUpWilCount--;
//				player.setPointsToSpend(player.getPointsToSpend()+1);
//			}
//		}
//		else if(stat.equals("Lck"))
//		{
//			if(levelUpLckCount > 0)
//			{
//				levelUpLckCount--;
//				player.setPointsToSpend(player.getPointsToSpend()+1);
//			}
//		}
//		
//		setUpCharacterSheet();
//	}
