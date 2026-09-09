/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package towerofhanoi;

/**
 *
 * @author Infinix
 */

import javax.swing.Timer;

public class GameTimer {

    private Timer timer;
    private int seconds;
    private TimerListener listener;

    public interface TimerListener {
        void onTimeUpdate(int seconds);
    }

    public GameTimer(TimerListener listener) {

        this.listener = listener;
        seconds = 0;

        timer = new Timer(1000, e -> {

            seconds++;

            if (listener != null) {
                listener.onTimeUpdate(seconds);
            }
        });
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void reset() {

        timer.stop();

        seconds = 0;

        if (listener != null) {
            listener.onTimeUpdate(seconds);
        }
    }

    public int getSeconds() {
        return seconds;
    }
}