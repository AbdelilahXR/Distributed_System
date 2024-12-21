package ex1;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 7777;
    private static Map<String, Set<PrintWriter>> salons = new HashMap<>();
    private static Map<String, String> users = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Chat Server Started...");
        loadUsers();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader("tp4/src/main/java/ex1/users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    users.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String salonName;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Authentification
                if (!authenticate()) {
                    out.println("Authentication failed. Disconnecting.");
                    socket.close();
                    return;
                }

                // Choisir un salon
                out.println("Enter salon name: ");
                salonName = in.readLine();
                synchronized (salons) {
                    salons.putIfAbsent(salonName, new HashSet<>());
                    salons.get(salonName).add(out);
                }

                out.println("Welcome to salon: " + salonName);
                broadcastMessage(username + " joined the salon.");

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("[" + salonName + "] " + username + ": " + message);
                    broadcastMessage(username + ": " + message);
                    saveMessageToFile(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                cleanup();
            }
        }

        private boolean authenticate() throws IOException {
            out.println("Enter username: ");
            String usernameInput = in.readLine();
            out.println("Enter password: ");
            String passwordInput = in.readLine();

            if (users.containsKey(usernameInput) && users.get(usernameInput).equals(passwordInput)) {
                username = usernameInput;
                return true;
            }
            return false;
        }

        private void broadcastMessage(String message) {
            synchronized (salons) {
                for (PrintWriter writer : salons.get(salonName)) {
                    writer.println(message);
                }
            }
        }

        private void saveMessageToFile(String message) {
            try (PrintWriter writer = new PrintWriter(new FileWriter("salon_" + salonName + ".txt", true))) {
                writer.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void cleanup() {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            synchronized (salons) {
                if (salons.containsKey(salonName)) {
                    salons.get(salonName).remove(out);
                    if (salons.get(salonName).isEmpty()) {
                        salons.remove(salonName);
                    }
                }
            }
        }
    }
}
