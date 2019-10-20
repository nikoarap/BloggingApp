package com.nikoarap.bloggingapp.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.nikoarap.bloggingapp.R;
import com.nikoarap.bloggingapp.adapters.AuthorsAdapter;
import com.nikoarap.bloggingapp.models.Author;
import com.nikoarap.bloggingapp.utils.VerticalSpacingDecorator;
import com.nikoarap.bloggingapp.viewmodels.AuthorListViewModel;

import java.util.ArrayList;
import java.util.List;

public class AuthorListActivity extends AppCompatActivity implements AuthorsAdapter.OnAuthorListener {

    public static final String TAG = "AuthorListActivity";

    private AuthorListViewModel authorListViewModel;



    private RecyclerView recView;
    private AuthorsAdapter recAdapter;
    public ArrayList<Author> authorList = new ArrayList<>();
    private ArrayList<String> authorImages = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authors_list_layout);
        recView = findViewById(R.id.authorsRecyclerView);


        authorListViewModel = ViewModelProviders.of(this).get(AuthorListViewModel.class);


        RetrofitRequest();
        subscribeObservers();
    }

    //method to create an observer
    private void subscribeObservers(){
        authorListViewModel.getAuthors().observe(this, new Observer<List<Author>>() {
            @Override
            public void onChanged(@Nullable List<Author> authors) {
                if (authors != null){
                    for(Author author: authors){
                        Log.d(TAG, "onChanged: " + author);
                        populateRecyclerView(authors);
                        authorList.addAll(authors);
                    }
                }
            }
        });
    }

    public void authorsAPI(){
        authorListViewModel.authorsAPI();
    }

    private void RetrofitRequest(){
        authorsAPI();
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
        Intent i = new Intent(this, AuthorPostsActivity.class);
        i.putExtra("authorId", authorList.get(position).getId());
        i.putExtra("authorName", authorList.get(position).getName());
        i.putExtra("authorAvatarUrl", authorList.get(position).getAvatarUrl());
        i.putExtra("authorUserName", authorList.get(position).getUserName());
        i.putExtra("authorEmail", authorList.get(position).getEmail());
        i.putExtra("authorAddressLat", authorList.get(position).getAddress().getLatitude());
        i.putExtra("authorAddressLng", authorList.get(position).getAddress().getLongitude());
        startActivity(i);
    }
}
