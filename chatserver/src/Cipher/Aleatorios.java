package Cipher;

import java.util.Random;

public class Aleatorios {
   
    String permitidos;
    
    public char basuraRandom(Random random) {
        
        permitidos = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int indice = random.nextInt(permitidos.length());
        return permitidos.charAt(indice);
        
    }
}
