package com.nikoarap.bloggingapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nikoarap.bloggingapp.R;
import com.nikoarap.bloggingapp.adapters.CommentsAdapter;
import com.nikoarap.bloggingapp.models.Comment;
import com.nikoarap.bloggingapp.utils.JsonDateFormat;
import com.nikoarap.bloggingapp.viewmodel.AppViewModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostCommentsActivity extends AppCompatActivity implements CommentsAdapter.OnCommentListener{

    public static final String TAG = "AuthorPostsActivity";

    private static AppViewModel appViewModel;

    private RecyclerView recView;
    public ImageButton backButton;
    private static String postId;
    public CircleImageView authorImg;
    private int post_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_comments_layout);
        recView = findViewById(R.id.commentsRecyclerView);

        setSupportActionBar((Toolbar)findViewById(R.id.widget_toolbar2));

        Intent i = getIntent();
        postId = i.getStringExtra("postId");
        String postTitle = i.getStringExtra("postTitle");
        String postImageUrl = i.getStringExtra("postImageUrl");
        String postDate = i.getStringExtra("postDate");
        String postBody = i.getStringExtra("postBody");
        String authorName = i.getStringExtra("authorName");
        String authorAvatarUrl = i.getStringExtra("authorAvatarUrl");

        TextView post_dateTv = findViewById(R.id.post_date);
        TextView post_titleTv = findViewById(R.id.post_title);
        TextView post_bodyTv = findViewById(R.id.post_body);
        TextView author_nameTv = findViewById(R.id.authorname);
        ImageView post_image = findViewById(R.id.post_image);
        backButton = findViewById(R.id.backbutton2);
        authorImg = findViewById(R.id.image);

        //formatting the date from ISO8601 to normal
        JsonDateFormat jsonDateFormat = new JsonDateFormat();
        String date = jsonDateFormat.convertJsonDateToNormal(postDate);

        post_dateTv.setText(date);
        post_titleTv.setText(postTitle);
        post_bodyTv.setText(postBody);
        author_nameTv.setText(authorName);

        Glide.with(this)
                .asBitmap()
                .load(authorAvatarUrl)
                .into(authorImg);

        Glide.with(this)
                .asBitmap()
                .load(postImageUrl)
                .into(post_image);

        post_id = Integer.parseInt(postId);

        appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);

        RetrofitRequest();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); //going to the previous activity
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        observeFromDb(); // start observing after onCreate
    }

    public static void RetrofitRequest(){
        commentsByPostIdRequest("?","date","&",postId);
    }

    //send request to retrofit
    public static void commentsByPostIdRequest(String query, String sort, String and, String postID){
        appViewModel.commentsByPostIdRequest(query, sort, and, postID);
    }

    //observing data from the DB
    private void observeFromDb(){
        appViewModel.getCommentsByPostFromDb(post_id).observe(this, new Observer<List<Comment>>() {
            @Override
            public void onChanged(@Nullable List<Comment> comments) {
                if (comments != null){
                    for(Comment comment: comments){
                        Log.d(TAG, "onChanged: " + comment.getPostId());
                        populateRecyclerView(comments);
                    }
                }
            }
        });
    }


    //method to set the recycler view and populate it
    private void populateRecyclerView(List<Comment> commentList) {
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recView.setLayoutManager(linearLayoutManager);
        CommentsAdapter commentsAdapter = new CommentsAdapter(this, commentList, this);
        recView.setAdapter(commentsAdapter);
        commentsAdapter.notifyDataSetChanged();
        recView.scheduleLayoutAnimation();
    }

    @Override
    public void onCommentClick(int position) {

    }
}
