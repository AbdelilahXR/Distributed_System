package tp1_multithreading.ex3;

public class Tortoise implements Runnable {
    private int currentPosition = 0;
    private int totalDistance;
    private String name;

    private String red = "\u001B[31m";
    private String green = "\u001B[32m";
    private String blue = "\u001B[34m";
    private String RESET = "\u001B[0m";

    public Tortoise(String name, int totalDistance) {
        this.name = name;
        this.totalDistance = totalDistance;
    }

    public void run() {
        while (currentPosition < totalDistance) {
            currentPosition++;
            System.out.println(name + " Distance covered: " + green + currentPosition + RESET);
            try {
                Thread.sleep(120);
            } catch (InterruptedException e) {
                return;
            }

            if (currentPosition >= totalDistance) {
                System.out.println(blue + name + " WINS THE RACE!" + RESET);
                System.exit(0);
            }
        }
    }
}

