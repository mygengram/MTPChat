package Client;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import Message.MessagePackage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    
    public static void main(String[] args) {
		
        FrameClient fc = new FrameClient();
        fc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
}

class FrameClient extends JFrame{
	
    public FrameClient() {

        setBounds(600,300,280,350);

        PanelClient pc =new PanelClient();
        add(pc);

        setVisible(true);

    }	
}

class PanelClient extends JPanel implements Runnable {

    private JTextField msgField, nickField, ipField;
    private JTextArea chatArea;
    private JButton sendButton;
	
    public PanelClient() {
        
        //Panel
        SendMessage sendMessage = new SendMessage();

        nickField = new JTextField(5);
        add(nickField);

        JLabel chatLabel = new JLabel("- ChatMTP -");
        add(chatLabel);

        ipField = new JTextField(9);
        add(ipField);

        chatArea = new JTextArea(12,20);
        add(chatArea);

        msgField = new JTextField(20);
        add(msgField);		

        sendButton =new JButton("Enviar");
        sendButton.addActionListener(sendMessage);
        add(sendButton);
        
        //Thread
        Thread thread = new Thread(this);
        thread.start();

    }

    @Override
    public void run() {
        
        try {
            
            ServerSocket server = new ServerSocket(7770);
            Socket socketIn;
            MessagePackage data = new MessagePackage();
            
            while(true) {
                
                socketIn = server.accept();
                
                ObjectInputStream inputStream = new ObjectInputStream(socketIn.getInputStream());
                
                data = (MessagePackage) inputStream.readObject();
                
                chatArea.append("\n("+data.getIp()+")"+data.getNickname()+": "+data.getMessage());
                
            }
            
        }
        catch (IOException | ClassNotFoundException ex) {
            chatArea.append(ex.getMessage());
        }
        
    }
    
    private class SendMessage implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            chatArea.append("\nYo: "+msgField.getText());
            
            try {
                
                Socket socket = new Socket("192.168.0.19",7770);
                
                MessagePackage data = new MessagePackage();
                data.setNickname(nickField.getText());
                data.setIp(ipField.getText());
                data.setMessage(msgField.getText());
                
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(data);
                
                socket.close();
                
            }
            catch (IOException ex) {
                chatArea.append(ex.getMessage());
            }
            
        }
        
    }
}