package ex2;

import java.io.*;
import java.net.*;
import java.util.*;

public class TicTacToeServer {
    private static final int PORT = 6666;
    private static List<PrintWriter> clientWriters = new ArrayList<>();
    private static char[][] board = new char[3][3];
    private static char currentPlayer = 'X';

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Tic-Tac-Toe Server is running...");
        initializeBoard();

        while (true) {
            Socket socket = serverSocket.accept();
            new ClientHandler(socket).start();
        }
    }

    private static void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            Arrays.fill(board[i], '_');
        }
    }

    private static synchronized boolean makeMove(int row, int col) {
        if (board[row][col] == '_') {
            board[row][col] = currentPlayer;
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            return true;
        }
        return false;
    }

    private static synchronized String getBoardState() {
        StringBuilder state = new StringBuilder();
        for (char[] row : board) {
            for (char cell : row) {
                state.append(cell).append(",");
            }
        }
        return state.toString();
    }

    private static synchronized boolean checkWin() {
        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != '_' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) return true;
            if (board[0][i] != '_' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) return true;
        }
        return (board[0][0] != '_' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) ||
               (board[0][2] != '_' && board[0][2] == board[1][1] && board[1][1] == board[2][0]);
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                synchronized (clientWriters) {
                    clientWriters.add(out);
                }

                out.println("Welcome to Tic-Tac-Toe! You are player " + currentPlayer);

                while (true) {
                    String input = in.readLine();
                    if (input.startsWith("MOVE")) {
                        String[] parts = input.split(" ");
                        int row = Integer.parseInt(parts[1]);
                        int col = Integer.parseInt(parts[2]);

                        if (makeMove(row, col)) {
                            String boardState = getBoardState();
                            synchronized (clientWriters) {
                                for (PrintWriter writer : clientWriters) {
                                    writer.println("UPDATE " + boardState);
                                    if (checkWin()) {
                                        writer.println("WINNER " + (currentPlayer == 'X' ? 'O' : 'X'));
                                    }
                                }
                            }
                        } else {
                            out.println("INVALID MOVE");
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
