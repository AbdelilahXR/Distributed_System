package tp1_multithreading.ex1;

public class Main {
    public static void main(String args[]) {
        LetterNumberThreads thread1 = new LetterNumberThreads(0);
        LetterNumberThreads thread2 = new LetterNumberThreads(1);
        LetterNumberThreads thread3 = new LetterNumberThreads(2);

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
