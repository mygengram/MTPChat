package Cipher;

import java.util.Random;

public class Cifrados {
    
    Aleatorios aleatorio;
    StringBuilder builder;
    Random random;
    
    char arrayMensaje[];
    
    public String primerCifrado(String mensaje,int frecuencia,int cantidad) {
        
        String cifrado;
        aleatorio = new Aleatorios();
        builder = new StringBuilder();
        random = new Random();
        
        int contador = 0;
        
        for (int i = 0; i < mensaje.length(); i++) {
            
            builder.append(mensaje.charAt(i));
            
            if (++contador == frecuencia) {
                
                for (int j = 0; j < cantidad; j++) {
                    
                    char randomChar = aleatorio.basuraRandom(random);
                    builder.append(randomChar);
                    
                }
                contador = 0;
            }
        }
        
        cifrado = builder.toString();
        return cifrado;
    }
    
    public String segundoCifrado(String cifrado,int desplazamiento) {
        
        arrayMensaje = cifrado.toCharArray();
        
        for (int i = 0; i < arrayMensaje.length; i++) {
            
            arrayMensaje[i] = (char)(arrayMensaje[i] + (char)desplazamiento);
            
        }
        
        cifrado = String.valueOf(arrayMensaje);
        return cifrado;
    }

}
