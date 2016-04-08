package client_View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StatField {

	JPanel panel;
	JButton minus;
	JButton plus;
	JTextField points;
	JLabel label;
	
	public StatField(String l)
	{
		panel = new JPanel();
		panel.setBackground(Launcher.TRANSPARENT);
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;    
        c.anchor = GridBagConstraints.EAST;
		
		minus = new JButton();
		minus.setIcon(new ImageIcon(Launcher.minusIcon));
		minus.setBackground(Launcher.TRANSPARENT);
		minus.setBorder(null);
//		minus.setBounds(0,0,minus.getWidth(), minus.getHeight());
        minus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minusStat(evt);
            }
        });
        
        points = new JTextField("5");
        points.setEditable(false);
        points.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        points.setBackground(Launcher.FIELDGRAY);
		points.setMinimumSize(new Dimension(50,30));
		points.setHorizontalAlignment(JTextField.CENTER);
//		points.setBounds(minus.getWidth() + 10, 0, points.getWidth(), points.getHeight());
        
        plus = new JButton();
        plus.setIcon(new ImageIcon(Launcher.plusIcon));
        plus.setBackground(Launcher.TRANSPARENT);
        plus.setBorder(null);
//        plus.setBounds(minus.getWidth() + points.getWidth() + 20, 0, plus.getWidth(), plus.getHeight());
        plus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plusStat(evt);
            }
        });
        
        label = new JLabel();
		label.setText(l);  
//		label.setPreferredSize(new Dimension(100, label.getHeight()));
		
		c.insets = new Insets(0,0,10,10);
		
        c.gridx = 0;
        c.gridy = 0;
        panel.add(minus, c);
        
        c.gridx = 1;
        c.gridy = 0;
        panel.add(points, c);
        
        c.gridx = 2;
        c.gridy = 0;
        panel.add(plus, c);
        
        c.gridx = 3;
        c.gridy = 0;
        panel.add(label, c);
	}
	
	public void minusStat(ActionEvent evt)
	{
		int curPoints = Integer.parseInt(points.getText());
		if(curPoints > 1)
		{
    		curPoints--;
    		points.setText(String.valueOf(curPoints));
    		Launcher.pointsRemaining++;
    		Launcher.pointsLeft.setText(String.valueOf(Launcher.pointsRemaining));
		}
	}
	
	public void plusStat(ActionEvent evt)
	{
		if(Launcher.pointsRemaining > 0)
		{
    		int curPoints = Integer.parseInt(points.getText());
    		curPoints++;
    		points.setText(String.valueOf(curPoints));
    		Launcher.pointsRemaining--;
    		Launcher.pointsLeft.setText(String.valueOf(Launcher.pointsRemaining));
		}
	}
}
