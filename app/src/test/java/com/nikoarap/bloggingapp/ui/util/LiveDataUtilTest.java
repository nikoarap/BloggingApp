package com.nikoarap.bloggingapp.ui.util;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;

import com.nikoarap.bloggingapp.ui.models.Authors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LiveDataUtilTest {

    public Authors getValue(final LiveData liveData) throws InterruptedException {

        final List<Authors> data = new ArrayList<>();

        // latch for blocking thread until data is set
        final CountDownLatch latch = new CountDownLatch(1);

        Observer observer = new Observer<Authors>() {
            @Override
            public void onChanged(Authors t) {
                data.add(t);
                latch.countDown(); // release the latch
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        try {
            latch.await(2, TimeUnit.SECONDS); // wait for onChanged to fire and set data
        } catch (InterruptedException e) {
            throw new InterruptedException("Latch failure");
        }
        if(data.size() > 0){
            return data.get(0);
        }
        return null;
    }

}
