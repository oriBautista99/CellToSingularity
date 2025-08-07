package util;

import java.util.Timer;
import java.util.TimerTask;

public class TimerManager {
    private Timer timer;

    public void iniciarTimer(Runnable tarea, long intervaloMs) {
        detener();

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                tarea.run();
            }
        }, 0, intervaloMs);

    }

    public void detener() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
