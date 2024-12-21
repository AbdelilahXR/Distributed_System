package ex2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class TicTacToeClient {
    private JFrame frame = new JFrame("Tic-Tac-Toe");
    private JButton[][] buttons = new JButton[3][3];
    private PrintWriter out;
    private BufferedReader in;

    public TicTacToeClient(String serverAddress) throws IOException {
        Socket socket = new Socket(serverAddress, 12345);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(3, 3));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 60));
                buttons[i][j].setFocusPainted(false);
                final int row = i, col = j;
                buttons[i][j].addActionListener(e -> sendMove(row, col));
                frame.add(buttons[i][j]);
            }
        }

        new Thread(() -> {
            try {
                while (true) {
                    String line = in.readLine();
                    if (line.startsWith("UPDATE")) {
                        updateBoard(line.substring(7));
                    } else if (line.startsWith("WINNER")) {
                        JOptionPane.showMessageDialog(frame, "Winner: " + line.substring(7));
                        frame.dispose();
                        break;
                    } else if (line.startsWith("INVALID")) {
                        JOptionPane.showMessageDialog(frame, "Invalid Move! Try again.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        frame.setVisible(true);
    }

    private void sendMove(int row, int col) {
        out.println("MOVE " + row + " " + col);
    }

    private void updateBoard(String state) {
        String[] cells = state.split(",");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String cell = cells[i * 3 + j];
                buttons[i][j].setText(cell.equals("_") ? "" : cell);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String serverAddress = JOptionPane.showInputDialog("Enter Server Address:");
        new TicTacToeClient(serverAddress);
    }
}
