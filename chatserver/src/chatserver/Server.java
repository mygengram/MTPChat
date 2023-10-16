package chatserver;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("Esperando una conexión...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado.");

            // Configurar la entrada y la salida
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            String inputLine;
            String outputLine;

            while (true) {
                inputLine = consoleInput.readLine();
                out.println(inputLine);

                if (inputLine.equalsIgnoreCase("bye")) {
                    break;
                }

                outputLine = in.readLine();
                System.out.println("Cliente: " + outputLine);
            }

            // Cierre de recursos
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

