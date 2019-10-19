package com.nikoarap.bloggingapp.api;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {

    private static AppExecutors instance;

    public static AppExecutors getInstance(){
        if(instance == null){
            instance = new AppExecutors();
        }
        return instance;
    }

    // Executor service that schedules commands after a given delay
    private final ScheduledExecutorService execThreadsAfterDelay = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService getExec(){
        return execThreadsAfterDelay;
    }

}
