package ex1;


import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 7777;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            // Authentication
            System.out.println("Enter username: ");
            out.println(userInput.readLine());
            System.out.println("Enter password: ");
            out.println(userInput.readLine());

            String response = in.readLine();
            if (response.equals("Authentication failed. Disconnecting.")) {
                System.out.println("Authentication failed.");
                return;
            }

            System.out.println("Enter salon name: ");
            out.println(userInput.readLine());

            Thread receiveMessagesThread = new Thread(new ReceiveMessagesTask(in));
            receiveMessagesThread.start();

            System.out.println("Connected to the chat salon, Start typing your messages:");

            String message;
            while ((message = userInput.readLine()) != null) {
                out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ReceiveMessagesTask implements Runnable {
        private BufferedReader in;

        public ReceiveMessagesTask(BufferedReader in) {
            this.in = in;
        }

        @Override
        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
