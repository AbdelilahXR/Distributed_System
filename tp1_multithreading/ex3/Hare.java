package tp1_multithreading.ex3;

public class Hare implements Runnable {
    private int currentPosition = 0;
    private int totalDistance;
    private String name;
    private String red = "\u001B[31m";
    private String green = "\u001B[32m";
    private String blue = "\u001B[34m";
    private String RESET = "\u001B[0m";

    public Hare(String name, int totalDistance) {
        this.name = name;
        this.totalDistance = totalDistance;
    }

    private long calculateSleepTime(int position) {
        int remainingDistance = totalDistance - position;
        return remainingDistance * 1500L;
    }

    public void run() {
        while (currentPosition < totalDistance) {
            currentPosition += 4;
            System.out.println(green + name + " Distance covered: " + currentPosition + RESET);

            if (currentPosition >= totalDistance / 2 && currentPosition < totalDistance - 15) {
                System.out.println(red + name + " is taking a nap..." + RESET);
                try {
                    long napTime = calculateSleepTime(currentPosition);
                    Thread.sleep(5000); 
                } catch (InterruptedException e) {
                    return;
                }
            } else {
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    return;
                }
            }

            if (currentPosition >= totalDistance) {
                System.out.println(blue + name + " WINS THE RACE! " + RESET);
                System.exit(0);
            }
        }
    }
}
