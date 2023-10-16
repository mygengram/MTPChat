package chatclient;

import java.util.Scanner;

public class Ruta {
    
    Scanner read = new Scanner(System.in);
    private String ipServer;

    public String getIpServer() {
        return ipServer;
    }

    public void setIpServer(String ipServer) {
        this.ipServer = ipServer;
    }
    
    public String pedirIP() {
        System.out.println("Ingresa la IP del servidor: ");
        ipServer = read.nextLine();
        return ipServer;
    }
}
