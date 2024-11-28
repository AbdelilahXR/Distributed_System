package tp1_multithreading.ex2;

import javax.swing.*;
import java.awt.*;

public class Clock extends JLabel {
    private DisplayTime displayTask;

    public Clock() {
        this.setHorizontalAlignment(JLabel.CENTER);
        Font currentFont = this.getFont();
        this.setFont(new Font(currentFont.getName(), currentFont.getStyle(), 30));
        displayTask = new DisplayTime(this);
        Thread displayThread = new Thread(displayTask);
        displayThread.start();
    }

    private class DisplayTime implements Runnable {
        private Clock clock;
        private int seconds;
        private int minutes;
        private int hours;

        public DisplayTime(Clock clock) {
            this.clock = clock;
            this.seconds = 0;
            this.minutes = 0;
            this.hours = 0;
        }

        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000); 
                } catch (InterruptedException e) {
                    return;
                }
                seconds++;
                if (seconds == 60) {
                    minutes++;
                    seconds = 0;
                }
                if (minutes == 60) {
                    hours++;
                    minutes = 0;
                }
                String secondsStr = (seconds < 10 ? "0" : "") + seconds;
                String minutesStr = (minutes < 10 ? "0" : "") + minutes;
                String hoursStr = (hours < 10 ? "0" : "") + hours;
                clock.setText(hoursStr + " H : " + minutesStr + " M : " + secondsStr + " S");
            }
        }
    }
}
