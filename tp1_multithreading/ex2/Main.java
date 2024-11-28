package tp1_multithreading.ex2;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Graphical Clock");
        frame.getContentPane().add(new Clock());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200); 
        frame.setVisible(true);
    }
}
