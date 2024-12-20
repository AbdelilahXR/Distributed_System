package tp3.ex2;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    public static void main(String[] args) {
        final int port = 8000; 

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("le serveur écoute sur le port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connexion acceptée : " + clientSocket.getInetAddress());

                String corps = "<HTML><TITLE>Mon serveur</TITLE>" +
                        "Cette page a été envoyée par mon <B>Serveur</B>" +
                        "</HTML>";
                String reponse = "HTTP/1.0 200 OK\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: " + corps.length() + "\r\n" +
                        "\r\n" + 
                        corps;

                OutputStream outputStream = clientSocket.getOutputStream();
                outputStream.write(reponse.getBytes());
                outputStream.flush();

                clientSocket.close();
                System.out.println("Réponse envoyée et connexion fermée");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
