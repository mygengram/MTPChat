package chatclient;

import java.util.Scanner;

public class Clave {
    
    private Scanner read = new Scanner(System.in);

    public String pedirClave() {
        System.out.println("Ingresa la clave del cifrado: ");
        return read.nextLine();
    }
    
}
