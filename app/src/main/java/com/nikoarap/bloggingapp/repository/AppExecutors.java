package com.nikoarap.bloggingapp.repository;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

class AppExecutors {

    private static AppExecutors instance;

    static AppExecutors getInstance(){
        if(instance == null){
            instance = new AppExecutors();
        }
        return instance;
    }

    // Executor service that schedules commands after a given delay
    private final ScheduledExecutorService execThreadsAfterDelay = Executors.newScheduledThreadPool(3);

    ScheduledExecutorService getExec(){
        return execThreadsAfterDelay;
    }

}
