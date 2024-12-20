package tp3.ex1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        String serverAddress = "127.0.0.1"; 
        int port = 9999;                  

        try (Socket socket = new Socket(serverAddress, port)) {
            System.out.println("Connecté au serveur " + serverAddress + " sur le port " + port);

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String proverb = reader.readLine();

            System.out.println("Proverbe reçu : " + proverb);
        } catch (Exception e) {
            System.out.println("Erreur dans le client : " + e.getMessage());
        }
    }
}
