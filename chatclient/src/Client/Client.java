package Client;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import Cipher.*;
import Message.MessagePackage;

public class Client {
    
    public static void main(String[] args) {
		
        FrameClient fc = new FrameClient();
        fc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
}

class FrameClient extends JFrame{
	
    public FrameClient() {

        setBounds(600,300,320,360);

        PanelClient pc =new PanelClient();
        add(pc);

        setVisible(true);
        
        addWindowListener(new SendOnline());
        
    }	
}

class SendOnline extends WindowAdapter {
    
    public void windowOpened(WindowEvent e) {
        
        try {
            
            Socket socketOut = new Socket("192.168.0.19",7770);
            
            MessagePackage data = new MessagePackage();
            data.setMessage("Online");
            
            ObjectOutputStream outputStream = new ObjectOutputStream(socketOut.getOutputStream());
            outputStream.writeObject(data);
            
            socketOut.close();
            
        }
        catch (IOException ex) {
            
        }
        
    }
    
}

class PanelClient extends JPanel implements Runnable {

    private JTextField msgField;
    private JLabel nickLabel;
    private JComboBox ipCombo;
    private JTextArea chatArea;
    private JButton sendButton;
    
    private String encrypted, decrypted, key;
    private int frecuencia, cantidad, desplazamiento;
    private Cifrados cifra;
    private Descifrados descifra;
    
    public PanelClient() {
        
        String varnick = JOptionPane.showInputDialog("Nombre de usuario: ");
        key = JOptionPane.showInputDialog("Clave secreta: ");
        
        //Panel
        SendMessage sendMessage = new SendMessage();
        
        JLabel userLabel = new JLabel("Usuario:");
        add(userLabel);
        
        nickLabel = new JLabel();
        nickLabel.setText(varnick);
        add(nickLabel);

        JLabel chatLabel = new JLabel("Online:");
        add(chatLabel);

        ipCombo = new JComboBox();
        add(ipCombo);

        chatArea = new JTextArea(15,22);
        add(chatArea);

        msgField = new JTextField(14);
        add(msgField);		

        sendButton = new JButton("Enviar");
        sendButton.addActionListener(sendMessage);
        add(sendButton);
        
        //Thread
        Thread thread = new Thread(this);
        thread.start();

        frecuencia = Character.getNumericValue(key.charAt(0));
        cantidad = Character.getNumericValue(key.charAt(1));
        desplazamiento = Character.getNumericValue(key.charAt(2));
        
        cifra = new Cifrados();
        descifra = new Descifrados();
        
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
                
                if (!data.getMessage().equalsIgnoreCase("Online")) {
                    
                    decrypted = descifra.primerDescifrado(data.getMessage(),desplazamiento);
                    decrypted = descifra.segundoDescifrado(decrypted,frecuencia,cantidad);
                
                    chatArea.append("\n("+data.getIp()+") "+data.getNickname()+": "+decrypted);
                    
                }   
                else {
                    
                    ArrayList<String> comboArray = new ArrayList();
                    comboArray = data.getIpArray();
                    
                    ipCombo.removeAllItems();
                    
                    for (String i:comboArray)
                        ipCombo.addItem(i);
                    
                }
                
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

                encrypted = cifra.primerCifrado(msgField.getText(),frecuencia,cantidad);
                encrypted = cifra.segundoCifrado(encrypted,desplazamiento);
                
                data.setNickname(nickLabel.getText());
                data.setIp(ipCombo.getSelectedItem().toString());
                data.setMessage(encrypted);
                
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