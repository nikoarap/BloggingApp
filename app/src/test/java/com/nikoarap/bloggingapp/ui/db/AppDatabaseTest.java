package com.nikoarap.bloggingapp.ui.db;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.nikoarap.bloggingapp.db.AppDao;
import com.nikoarap.bloggingapp.db.AppDatabase;

import org.junit.After;
import org.junit.Before;

public abstract class AppDatabaseTest {

    // system under test
    private AppDatabase appdb;


    public AppDaoMock getAuthorDao(){
        return (AppDaoMock) appdb.getAppDao();
    }

    @Before
    public void init(Context con){
        appdb = Room.inMemoryDatabaseBuilder(
                con.getApplicationContext(),
                AppDatabase.class
        ).build();
    }

    @After
    public void finish(){
        appdb.close();
    }
}