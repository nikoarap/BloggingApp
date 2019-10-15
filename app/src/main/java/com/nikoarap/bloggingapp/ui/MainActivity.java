package com.nikoarap.bloggingapp.ui;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.nikoarap.bloggingapp.R;
import com.nikoarap.bloggingapp.adapters.RecyclerAdapter;
import com.nikoarap.bloggingapp.data.AuthorsAPI;
import com.nikoarap.bloggingapp.data.RetrofitRequestClass;
import com.nikoarap.bloggingapp.models.Author;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.OnAuthorListener {

    public static final String TAG = "ERROR FETCHING DATA";

    private RecyclerView recView;
    private RecyclerAdapter recAdapter;
    public ArrayList<Author> authorList;
    private ArrayList<String> authorImages = new ArrayList<>();
    ActionBar AB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recView = findViewById(R.id.recyclerView);

        getRetrofitRequest();

        //showing the action bar at the top of the screen
        AB = getSupportActionBar();
        AB.setTitle("Authors");
        AB.setDisplayShowTitleEnabled(true);
    }

    private void getRetrofitRequest(){

        AuthorsAPI apiService = RetrofitRequestClass.getRetrofitInstance().create(AuthorsAPI.class); // Get instance of Retrofit
        Call<List<Author>> call = apiService.getAuthorsApi(); // get all authors request
        call.enqueue(new Callback<List<Author>>() {
            @Override
            public void onResponse(Call<List<Author>> call, Response<List<Author>> response) {

                //populating the recyclerView with the data fetched from the API
                generateAuthorList(response.body());
                if (response.body() != null) {
                    authorList = new ArrayList<>(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Author>> call, Throwable t) {
                Log.d(TAG,"error because: "+t.getMessage());
            }
        });
    }


    private void generateAuthorList(List<Author> authorList) {
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recView.setLayoutManager(linearLayoutManager);
        recAdapter = new RecyclerAdapter(this, authorList, authorImages,this);
        recView.setAdapter(recAdapter);
        recAdapter.notifyDataSetChanged();
        recView.scheduleLayoutAnimation();
    }

    @Override
    public void onAuthorClick(int position) {
    }
}
