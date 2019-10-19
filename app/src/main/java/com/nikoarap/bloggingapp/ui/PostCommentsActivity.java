package com.nikoarap.bloggingapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import com.nikoarap.bloggingapp.adapters.PostsAdapter;
import com.nikoarap.bloggingapp.models.Comment;
import com.nikoarap.bloggingapp.models.Post;
import com.nikoarap.bloggingapp.utils.VerticalSpacingDecorator;
import com.nikoarap.bloggingapp.viewmodels.CommentListViewModel;
import com.nikoarap.bloggingapp.viewmodels.PostListViewModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostCommentsActivity extends AppCompatActivity implements CommentsAdapter.OnCommentListener{

    public static final String TAG = "AuthorPostsActivity";

    private CommentListViewModel commentListViewModel;

    private RecyclerView recView;
    private PostsAdapter postsAdapter;
    public ImageButton backButton;
    private String postId;
    private String postTitle;
    private String postImageUrl;
    private String postDate;
    private String postBody;
    private String authorName;
    private String authorAvatarUrl;
    private TextView post_dateTv;
    private TextView post_titleTv;
    private TextView post_bodyTv;
    private TextView author_nameTv;
    private ImageView post_image;
    public CircleImageView authorImg;
    private CommentsAdapter commentsAdapter;
    private ArrayList<String> commentImages = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_comments_layout);
        recView = findViewById(R.id.commentsRecyclerView);

        setSupportActionBar((Toolbar)findViewById(R.id.widget_toolbar2));


        Intent i = getIntent();
        postId = i.getStringExtra("postId");
        postTitle = i.getStringExtra("postTitle");
        postImageUrl = i.getStringExtra("postImageUrl");
        postDate = i.getStringExtra("postDate");
        postBody = i.getStringExtra("postBody");
        authorName = i.getStringExtra("authorName");
        authorAvatarUrl = i.getStringExtra("authorAvatarUrl");

        post_dateTv = (TextView) findViewById(R.id.post_date);
        post_titleTv = (TextView) findViewById(R.id.post_title);
        post_bodyTv = (TextView) findViewById(R.id.post_body);
        author_nameTv = (TextView) findViewById(R.id.authorname);
        post_image = (ImageView) findViewById(R.id.post_image);
        backButton = (ImageButton) findViewById(R.id.backbutton2);
        authorImg = (CircleImageView) findViewById(R.id.image);


        post_dateTv.setText(postDate);
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

        commentListViewModel = ViewModelProviders.of(this).get(CommentListViewModel.class);

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
        commentListViewModel.getComments().observe(this, new Observer<List<Comment>>() {
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

    public void commentsAPI(String query, String postID){
        commentListViewModel.commentsApi(query, postID);
    }

    private void RetrofitRequest(){
        commentsAPI("?sdas",postId);
    }

    //method to set the recycler view and populate it
    private void populateRecyclerView(List<Comment> commentList) {
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recView.setLayoutManager(linearLayoutManager);
        commentsAdapter = new CommentsAdapter(this, commentList, commentImages, this);
        VerticalSpacingDecorator itemDecorator = new VerticalSpacingDecorator(0);
        recView.addItemDecoration(itemDecorator);
        recView.setAdapter(commentsAdapter);
        commentsAdapter.notifyDataSetChanged();
        recView.scheduleLayoutAnimation();
    }

    @Override
    public void onCommentClick(int position) {

    }
}
