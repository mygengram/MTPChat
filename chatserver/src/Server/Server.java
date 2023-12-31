package Server;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import Message.MessagePackage;
import java.util.*;

public class Server {
    
    public static void main(String[] args) {
		
        FrameServer fs = new FrameServer();
        fs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
    }
}

class FrameServer extends JFrame implements Runnable {
	
    private JTextArea textArea;
    
    public FrameServer() {
        
        //Frame
        textArea = new JTextArea();
        textArea.enable(false);
        textArea.setDisabledTextColor(Color.BLACK);
        
        JPanel panelServer = new JPanel();
        
        setBounds(1200,300,280,350);	
        
        panelServer.setLayout(new BorderLayout());
        panelServer.add(textArea,BorderLayout.CENTER);
        add(panelServer);

        setVisible(true);
        
        //Thread
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        
        textArea.append("Servidor encendido");
        
        try {
            
            ServerSocket server = new ServerSocket(7770);
            MessagePackage data = new MessagePackage();
            String nickname, ip, message;
            ArrayList<String> ipArray = new ArrayList();
            
            while(true) {
                
                //Socket Input
                Socket socketIn = server.accept();
                
                ObjectInputStream inputStream = new ObjectInputStream(socketIn.getInputStream());
                        
                data = (MessagePackage) inputStream.readObject();
                
                nickname = data.getNickname();
                ip = data.getIp();
                message = data.getMessage();
                
                if (!message.equalsIgnoreCase("Online")) {
                
                    textArea.append("\n"+nickname+" (Para:" + ip + "): "+message);

                    //Socket Output
                    Socket socketOut = new Socket(ip,7770);

                    ObjectOutputStream outputStream = new ObjectOutputStream(socketOut.getOutputStream());
                    outputStream.writeObject(data);
                    outputStream.close();

                    //Closing Sockets
                    socketIn.close();
                    socketOut.close();
                    
                } else {
                    
                    //Detect Online
                    String remote;
                    InetAddress address = socketIn.getInetAddress();
                    remote = address.getHostAddress();
                    
                    textArea.append("\nAhora online: "+remote);
                    
                    ipArray.add(remote);
                    
                    data.setIpArray(ipArray);
                    
                    for (String i:ipArray) {
                        
                        textArea.append("\nArray: "+remote);
                    
                        //Socket Output
                        Socket socketOut = new Socket(i,7770);

                        ObjectOutputStream outputStream = new ObjectOutputStream(socketOut.getOutputStream());
                        outputStream.writeObject(data);
                        outputStream.close();

                        //Closing Sockets
                        socketIn.close();
                        socketOut.close();
                        
                    }
                }
            }
            
        } 
        catch (IOException | ClassNotFoundException ex) {
            textArea.append(ex.getMessage());
        }
        
    }
}