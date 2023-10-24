package mtpreader;

public class Descifrados {
    
    char arrayCifrado[];
    StringBuilder builder;
    
    public String primerDescifrado(String cifrado, int desplazamiento) {
        
        String descifrado;
        arrayCifrado = cifrado.toCharArray();
        
        for (int i = 0; i < arrayCifrado.length; i++) {
            
            arrayCifrado[i] = (char)(arrayCifrado[i] - (char)desplazamiento);
        
        }
        
        descifrado = String.valueOf(arrayCifrado);
        return descifrado;
    }
    
    public String segundoDescifrado(String cifrado, int frecuencia, int cantidad) {
        
        String descifrado;
        builder = new StringBuilder();
        int contador1 = 0, contador2 = 0;
        char caracter;
        
        for (int i = 0; i < cifrado.length(); i++) {
            
            if(++contador1 <= frecuencia)
                caracter = cifrado.charAt(i);
            
            else {
                
                for(int j = 0; j < cantidad; j++) {
                    
                    if(++contador2 < cantidad)
                        break;
                    else {
                        contador1 = 0;
                        contador2 = 0;
                        break;
                    }
                    
                }
                
                caracter = '\0';
            }
            
            if (caracter != '\0')
                builder.append(caracter);
        }
        
        descifrado = builder.toString();
        return descifrado;
    }
    
}
