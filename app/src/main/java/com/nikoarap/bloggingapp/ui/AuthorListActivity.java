package com.nikoarap.bloggingapp.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.nikoarap.bloggingapp.R;
import com.nikoarap.bloggingapp.adapters.AuthorsAdapter;
import com.nikoarap.bloggingapp.api.FetchJSONDataAPI;
import com.nikoarap.bloggingapp.api.RetrofitRequestClass;
import com.nikoarap.bloggingapp.models.Author;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorListActivity extends AppCompatActivity implements AuthorsAdapter.OnAuthorListener {

    public static final String TAG = "ERROR FETCHING DATA";


    private RecyclerView recView;
    private AuthorsAdapter recAdapter;
    public ArrayList<Author> authorList;
    private ArrayList<String> authorImages = new ArrayList<>();
    private ActionBar AB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authors_list_layout);
        recView = findViewById(R.id.recyclerView);

        getRetrofitRequest();

        //showing the action bar at the top of the screen
        AB = getSupportActionBar();
        AB.setTitle("Authors");
        AB.setDisplayShowTitleEnabled(true);

    }



    private void getRetrofitRequest(){
        FetchJSONDataAPI fetchJSONDataAPI = RetrofitRequestClass.fetchApi(); // json request of the API interface through retrofit
        Call<List<Author>> call = fetchJSONDataAPI.getAuthorsApi(); // get all authors request
        call.enqueue(new Callback<List<Author>>() {
            @Override
            public void onResponse(Call<List<Author>> call, Response<List<Author>> response) {

                //populating the recyclerView with the data fetched from the API
                populateRecyclerView(response.body());
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


    private void populateRecyclerView(List<Author> authorList) {
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recView.setLayoutManager(linearLayoutManager);
        recAdapter = new AuthorsAdapter(this, authorList, authorImages,this);
        recView.setAdapter(recAdapter);
        recAdapter.notifyDataSetChanged();
        recView.scheduleLayoutAnimation();
    }

    @Override
    public void onAuthorClick(int position) {
        Intent i = new Intent(this, AuthorDetailsActivity.class);
        i.putExtra("authorId", authorList.get(position).getId());
        i.putExtra("authorName", authorList.get(position).getName());
        i.putExtra("authorAvatar", authorList.get(position).getAvatarUrl());
        startActivity(i);
    }
}
