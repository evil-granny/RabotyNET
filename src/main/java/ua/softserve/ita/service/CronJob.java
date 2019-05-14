package ua.softserve.ita.service;


import java.nio.file.Path;
import java.util.Timer;

public class CronJob {

    public static void cronStart(Path path, int time){

        Timer t = new Timer();
        MyTask mTask = new MyTask(path);
        // This task is scheduled to run every 10 seconds
        t.scheduleAtFixedRate(mTask, 0, time);

    }
}
