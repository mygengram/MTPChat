package chatclient;

import java.io.*;
import java.net.*;
import mtpreader.Descifrados;
import mtpwriter.Cifrados;

public class Client {
    public static void main(String[] args) {
        
        String secreta;
        int frecuencia, cantidad, desplazamiento;
        Clave clave = new Clave();
        Cifrados cifra = new Cifrados();
        Descifrados descifra = new Descifrados();
        Ruta ruta = new Ruta();

        try {
            
            secreta = clave.pedirClave();
            
            frecuencia = Character.getNumericValue(secreta.charAt(0));
            cantidad = Character.getNumericValue(secreta.charAt(1));
            desplazamiento = Character.getNumericValue(secreta.charAt(2));
            
            Socket socket = new Socket(ruta.pedirIP(), 8080);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            Thread readThread = new Thread(() -> {
                try {
                    String inputLine;
                    
                    while ((inputLine = descifra.segundoDescifrado(descifra.primerDescifrado(in.readLine(), desplazamiento), frecuencia, cantidad)) != null) {
                        System.out.println("Servidor: " + inputLine);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            Thread writeThread = new Thread(() -> {
                try {
                    String outputLine;

                    while ((outputLine = cifra.segundoCifrado(cifra.primerCifrado(consoleInput.readLine(), frecuencia, cantidad), desplazamiento)) != null) {
                        out.println(outputLine);
                        if (outputLine.equalsIgnoreCase("bye")) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            readThread.start();
            writeThread.start();

            readThread.join();
            writeThread.join();

            in.close();
            out.close();
            socket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}