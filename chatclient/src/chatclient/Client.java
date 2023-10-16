package chatclient;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        
        Ruta ruta = new Ruta();
        
        try {
            Socket socket = new Socket(ruta.pedirIP(), 8080); // Reemplaza "ip_del_servidor" por la IP del servidor

            // Configurar la entrada y la salida
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            String inputLine;
            String outputLine;

            while (true) {
                outputLine = consoleInput.readLine();
                out.println(outputLine);

                if (outputLine.equalsIgnoreCase("bye")) {
                    break;
                }

                inputLine = in.readLine();
                System.out.println("Servidor: " + inputLine);
            }

            // Cierre de recursos
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

