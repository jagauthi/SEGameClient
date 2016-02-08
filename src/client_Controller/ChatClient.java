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

public class ChatClient extends Thread{
 
    BufferedReader in;
    PrintWriter out;
    public JFrame frame = new JFrame("Chatter");
    JTextField textField = new JTextField(40);
    JTextArea messageArea = new JTextArea(8, 40);

    public ChatClient() {

        // Layout GUI
        textField.setEditable(false);
        messageArea.setEditable(false);
        frame.getContentPane().add(textField, "North");
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");
        frame.pack();

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
	            if (line.startsWith("SUBMITNAME")) {
	                out.println(getScreenName());
	            } 
	            else if (line.startsWith("NAMEACCEPTED")) {
	                textField.setEditable(true);
	            } 
	            else if (line.startsWith("MESSAGE")) {
	                messageArea.append(line.substring(8) + "\n");
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
