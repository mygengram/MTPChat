package mtpwriter;

import java.util.Random;

public class Aleatorios {
   
    Random random;
    String permitidos;
    
    public int numeroRandom() {
        
        random = new Random();
        return random.nextInt(9)+1;
        
    }
    
    public char basuraRandom(Random random) {
        
        permitidos = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int indice = random.nextInt(permitidos.length());
        return permitidos.charAt(indice);
        
    }
}
