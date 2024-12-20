package tp3.ex2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadedHttpServer {
    public static void main(String[] args) {
        final int port = 8000; 

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Serveur multithreads en écoute sur le port " + port);

            while (true) {
                
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nouvelle connexion acceptée : " + clientSocket.getInetAddress());

                
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            String filePath = "tp3/src/main/java/tp3/ex2/content.html"; 
            String corps = lireFichier(filePath);

            if (corps == null) {
                corps = "<HTML><TITLE>Erreur</TITLE>Le fichier HTML est introuvable.</HTML>";
            }

            
            String reponse = "HTTP/1.0 200 OK\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: " + corps.length() + "\r\n" +
                    "\r\n" +
                    corps;

            
            OutputStream outputStream = clientSocket.getOutputStream();
            outputStream.write(reponse.getBytes());
            outputStream.flush();

            
            clientSocket.close();
            System.out.println("Réponse envoyée et connexion fermée.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // fonction pour lire le contenu d'un fichier
    private String lireFichier(String cheminFichier) {
        StringBuilder contenu = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                contenu.append(ligne).append("\n");
            }
        } catch (IOException e) {
            System.err.println("erreur lors de la lecture du fichier : " + e.getMessage());
            return null;
        }
        return contenu.toString();
    }
}

