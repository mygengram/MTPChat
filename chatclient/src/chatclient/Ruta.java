package chatclient;

import java.util.Scanner;

public class Ruta {
    
    private Scanner read = new Scanner(System.in);

    public String pedirIP() {
        System.out.println("Ingresa la IP del servidor: ");
        return read.nextLine();
    }
}
