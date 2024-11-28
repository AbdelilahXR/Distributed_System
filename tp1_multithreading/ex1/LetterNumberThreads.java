package tp1_multithreading.ex1;

public class LetterNumberThreads extends Thread {
    int taskType;

    public LetterNumberThreads(int taskType) {
        this.taskType = taskType;
    }

    public void processLetters(int mode) {
        char character;
        if (mode == 0) {
            character = 'm';
            while (character <= 'z') {
                System.out.print(character++ + " ");
                try {
                    Thread.sleep(120);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else if (mode == 1) {
            character = 'H';
            while (character <= 'W') {
                System.out.print(character++ + " ");
                try {
                    Thread.sleep(120);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void processNumbers() {
        int number = 1;
        while (number <= 50) {
            System.out.print(number++ + " ");
            try {
                Thread.sleep(120);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void run() {
        if (taskType == 0) {
            processLetters(0);
        } else if (taskType == 1) {
            processLetters(1);
        } else if (taskType == 2) {
            processNumbers();
        }
    }
}
