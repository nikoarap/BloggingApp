package com.nikoarap.bloggingapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nikoarap.bloggingapp.R;
import com.nikoarap.bloggingapp.viewmodels.AuthorListViewModel;

public class SplashActivity extends AppCompatActivity {

    private AuthorListViewModel authorListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity_layout);

        authorListViewModel = ViewModelProviders.of(this).get(AuthorListViewModel.class);

        //sending the request for the authors list to retrofit before the authors list activity
        // to avoid bugs while caching through the database
        RetrofitRequest();

        //splash screen for 2 seconds
        Thread logoTimer = new Thread(){
            public void run(){
                try{
                    sleep(2000);

                    Intent i = new Intent(SplashActivity.this, AuthorListActivity.class);
                    startActivity(i);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally{
                    finish();
                }
            }
        };
        logoTimer.start();
    }

    public void authorsAPI(){
        authorListViewModel.authorsAPI();
    }

    private void RetrofitRequest(){
        authorsAPI();
    }

}
