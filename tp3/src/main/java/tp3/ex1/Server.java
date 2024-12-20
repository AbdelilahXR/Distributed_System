package tp3.ex1;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server {

    public static void main(String[] args) {
        int port = 9999; 

        String[] proverbs = {
            "L'habit ne fait pas le moine.",
            "Qui sème le vent récolte la tempête.",
            "Pierre qui roule n'amasse pas mousse.",
        };

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Serveur démarré sur le port " + port);

            while (true) {
                
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connecté : " + clientSocket.getInetAddress());
                
                String proverb = proverbs[new Random().nextInt(proverbs.length)];
                
                OutputStream output = clientSocket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);
                writer.println(proverb);
                System.out.println("Proverbe envoyé : " + proverb);

                
                clientSocket.close();
            }
        } catch (Exception e) {
            System.out.println("Erreur dans le serveur : " + e.getMessage());
        }
    }
}
