package com.nikoarap.bloggingapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nikoarap.bloggingapp.R;
import com.nikoarap.bloggingapp.adapters.PostsAdapter;
import com.nikoarap.bloggingapp.models.Post;
import com.nikoarap.bloggingapp.viewmodel.PostListViewModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AuthorDetailsActivity extends AppCompatActivity implements PostsAdapter.OnPostListener {

    public static final String TAG = "AuthorDetailsActivity";

    private PostListViewModel postListViewModel;


    public TextView authorTv;
    private ActionBar AB;
    public CircleImageView authorImg;
    public ImageButton backButton;
    public ImageButton infoButton;
    private String authorId;
    private String authorName;
    private String authorAvatar;
    private RecyclerView recView;
    private PostsAdapter postsAdapter;
    private ArrayList<String> postImages = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.author_details_layout);
        recView = findViewById(R.id.recyclerView);

        //hiding the action bar
        AB = getSupportActionBar();
        AB.hide();

        //getting the intent from the previous activity and the variables passed
        Intent i = getIntent();
        authorId = i.getStringExtra("authorId");
        authorName = i.getStringExtra("authorName");
        authorAvatar = i.getStringExtra("authorAvatar");

        authorTv = (TextView) findViewById(R.id.name);
        authorImg = (CircleImageView) findViewById(R.id.image);
        backButton = (ImageButton) findViewById(R.id.backbutton);

        postListViewModel = ViewModelProviders.of(this).get(PostListViewModel.class);

        authorTv.setText(authorName);
        Glide.with(this)
                .asBitmap()
                .load(authorAvatar)
                .into(authorImg);

        RetrofitRequest();
        subscribeObservers();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); //going to the previous activity
            }
        });
        
    }

    //method to create an observer
    private void subscribeObservers(){
        postListViewModel.getPosts().observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(@Nullable List<Post> posts) {
                if (posts != null){
                    for(Post post: posts){
                        Log.d(TAG, "onChanged: " + post.getAuthorId());
                        populateRecyclerView(posts);
                    }
                }
            }
        });
    }

    public void postsAPI(String query, String authorID){
        postListViewModel.postsAPI(query, authorID);
    }

    private void RetrofitRequest(){
        postsAPI("?sdas",authorId);
    }


    //method to set the recycler view and populate it
    private void populateRecyclerView(List<Post> postList) {
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recView.setLayoutManager(linearLayoutManager);
        postsAdapter = new PostsAdapter(this, postList, postImages, this);
        recView.setAdapter(postsAdapter);
        postsAdapter.notifyDataSetChanged();
        recView.scheduleLayoutAnimation();
    }

    @Override
    public void onPostClick(int position) {

    }
}
