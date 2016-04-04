package client_Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client_View.Launcher;

public class ChatClient extends Thread{
 
    BufferedReader in;
    PrintWriter out;
    public JFrame frame = new JFrame("Chatter");
    JTextField textField = new JTextField(40);
    JTextArea messageArea = new JTextArea(8, 40);
    Launcher launcher;
    StateMachine sm;
    
    public ChatClient(Launcher launch) {
    	launcher = launch;
        // Layout GUI
        textField.setEditable(false);
        messageArea.setEditable(false);
        frame.getContentPane().add(textField, "North");
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);

        // Add Listeners
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                out.println(textField.getText());
                textField.setText("");
            }
        });
    }
    
    public void setStateMachine(StateMachine sm)
    {
    	this.sm = sm;
    }
/*
    private String getServerAddress() {
        return JOptionPane.showInputDialog(
            frame,
            "Enter IP Address of the Server:",
            "Welcome to the Chatter",
            JOptionPane.QUESTION_MESSAGE);
    }
*/
    public String getScreenName() {
        return JOptionPane.showInputDialog(
            frame,
            "Choose a screen name:",
            "Screen name selection",
            JOptionPane.PLAIN_MESSAGE);
    }
    
    public String getServerAddress(){
    	String[] choices = { "localhost", "uaf131025.ddns.uark.edu", "uaf131018.ddns.uark.edu", "215-9020-06", "uaf132895.ddns.uark.edu" };
    	String input = (String) JOptionPane.showInputDialog(null, "Options: ",
    			"Choose Server", JOptionPane.QUESTION_MESSAGE, null,      
    			choices, // Array of choices
    			choices[0]); // Initial choice
    	return input;
    }

    public void run() {
        // Make connection and initialize streams
    	try{
	        String serverAddress = getServerAddress();
	        Socket socket = new Socket(serverAddress, 9001);
	        in = new BufferedReader(new InputStreamReader(
	            socket.getInputStream()));
	        out = new PrintWriter(socket.getOutputStream(), true);

	        // Process all messages from server, according to the protocol.
	        while (true) {
	            String line = in.readLine();
	            String[] message = line.split("#");
	            if (message[0].equals("SUBMITNAME")) {
	                //out.println(getScreenName());
	            	
	            	//Gets the mac address and sends it to the server's handler
	            	InetAddress ip = InetAddress.getLocalHost();
	        		NetworkInterface network = NetworkInterface.getByInetAddress(ip);
	        		byte[] mac = network.getHardwareAddress();
	        		StringBuilder sb = new StringBuilder();
	        		for (int i = 0; i < mac.length; i++) {
	        			sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
	        		}
	        		out.println(sb.toString());
	            } 
	            else if (message[0].equals("NAMEACCEPTED")) {
	                textField.setEditable(true);
	            } 
	            else if (message[0].equals("MESSAGE")) {
	            	String textMessage = "";
	            	if(message.length > 1)
	            	{
		            	for(int i = 2; i < message.length; i++)
		            		textMessage += message[i] + "#";
		            	if(textMessage.length() > 0)
		            		textMessage = textMessage.substring(0, textMessage.length()-1);
	            	}
	                sm.getCountryViewState().messageArea.append(message[1] + ": " + textMessage + "\n");
	            }
	            else if (message[0].equals("loginSuccess")) { 
	                //The server should send each of the characters that belong to 
	                //our account. All the information in each character will be separated
	                //with spaces (or some other delimiter) and each of the individual
	                //characters will be separated by a colon (or some other delimiter)
	            	launcher.loadCharacterInfo(message);
	                launcher.initCharSelectPanel();
	                launcher.switchCards("Char Select Panel");
	            }
	            else if (message[0].equals("usernameNotFound")) { 
	                launcher.usernameNotFound();
	            }
	            else if (message[0].equals("accountLocked")) { 
	                launcher.accountLocked();
	            }
	            else if (message[0].equals("accountBanned")) { 
	                launcher.accountBanned();
	            }
	            else if (message[0].equals("incorrectPassword")) { 
	                launcher.incorrectPassword();
	            }
	            else if (message[0].equals("moreThanOneAccountFound")) { 
	                launcher.moreThanOneAccountFound();
	            }
	            else if (message[0].equals("accountAlreadyExists")) { 
	                launcher.accountAlreadyExists();
	            }
	            else if (message[0].equals("accountCreated")) { 
	                launcher.accountCreated();
	            }
	            else if (message[0].equals("charCreatedInventorySuccess")) { 
	            	launcher.loadCharacterInfo(message);
	                launcher.initCharSelectPanel();
	                launcher.switchCards("Char Select Panel");
	            }
	            else if (message[0].equals("securityInfo")) { 
	            	launcher.manageAccount(message);
	            }
	            else if (message[0].equals("charInfoDeleted")) { 
	            	launcher.loadCharacterInfo(message);
	                launcher.initCharSelectPanel();
	                launcher.switchCards("Login Panel");
	                launcher.switchCards("Char Select Panel");
	            }
	            else if (message[0].equals("accountUpdated")) { 
	            	launcher.loadCharacterInfo(message);
	                launcher.initCharSelectPanel();
	            	launcher.backToCharSelect();
	            }
	            else if (message[0].equals("characterInfo")) { 
	            	//name, class, logggedIn, level, gender, health, mana, exp, pointsToSpend, xCoord, yCoord, location, clanName, str, dex, con, int, wil, luck, abilities, cooldown
	            	launcher.intoGame(message);
	            }
	            else if (message[0].equals("charUpdated")) { 
	            	//charUpdated:char1Name char1x char1y char1Direction char1EquippedItems char1Sex:char2Name char2x char2y...
	            	sm.updateCharsAroundMe(message);
	            }
	            else if (message[0].equals("loadLocations")) { 
	            	//charUpdated:char1Name char1x char1y:char2Name char2x char2y:...
	            	sm.getCountryViewState().loadInfo(message);
	            }
	            else if(message[0].equals("localInfo")) {
	            	if(sm.currentState instanceof LocalViewState)
	            		sm.currentState.loadInfo(message);
	            }
	            else if(message[0].equals("encounterInfo")) {
	            	if(sm.currentState instanceof CombatState)
	            	{
	            		sm.currentState.loadInfo(message);
	            	}
	            }
	            else
	            {
	            	System.out.println("Not sure how to handle this error... the error is: ");
	            	System.out.println(message[0]);
	            }
	        }
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    public void sendMessage(String message)
    {
    	out.println(message);
    }
}
