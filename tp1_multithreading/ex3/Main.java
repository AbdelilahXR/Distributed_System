package tp1_multithreading.ex3;

public class Main {
    public static void main(String[] args) {
        Tortoise tortoise = new Tortoise("Tortoise", 100);
        Hare hare = new Hare("Hare", 100);

        Thread tortoiseThread = new Thread(tortoise);
        Thread hareThread = new Thread(hare);

        System.out.println("The race is about to begin!");

        tortoiseThread.start();
        hareThread.start();
    }
}
