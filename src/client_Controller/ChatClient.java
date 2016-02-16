package client_Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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

    private String getServerAddress() {
        return JOptionPane.showInputDialog(
            frame,
            "Enter IP Address of the Server:",
            "Welcome to the Chatter",
            JOptionPane.QUESTION_MESSAGE);
    }

    public String getScreenName() {
        return JOptionPane.showInputDialog(
            frame,
            "Choose a screen name:",
            "Screen name selection",
            JOptionPane.PLAIN_MESSAGE);
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
	            String[] message = line.split(":");
	            if (message[0].equals("SUBMITNAME")) {
	                out.println(getScreenName());
	            } 
	            else if (message[0].equals("NAMEACCEPTED")) {
	                textField.setEditable(true);
	            } 
	            else if (message[0].equals("MESSAGE")) {
	                messageArea.append(line.substring(8) + "\n");
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
