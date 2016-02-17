package client_View;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatField {

	JPanel panel;
	JButton minus;
	JButton plus;
	JLabel points;
	JLabel label;
	
	public StatField(String l)
	{
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        
        label = new JLabel();
		label.setText(l);
		minus = new JButton();
		plus = new JButton();
		points = new JLabel();
		points.setText("5");        		
		
		minus.setText("-");
        minus.setPreferredSize(new Dimension(50, 50));
        minus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minusStat(evt);
            }
        });
        
        plus.setText("+");
        plus.setPreferredSize(new Dimension(50, 50));
        plus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plusStat(evt);
            }
        });
        
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
    		Launcher.pointsLeft.setText("Points remaining: " + Launcher.pointsRemaining);
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
    		Launcher.pointsLeft.setText("Points remaining: " + Launcher.pointsRemaining);
		}
	}
}
