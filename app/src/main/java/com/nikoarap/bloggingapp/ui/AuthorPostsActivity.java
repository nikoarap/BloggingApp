package com.nikoarap.bloggingapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nikoarap.bloggingapp.R;
import com.nikoarap.bloggingapp.adapters.PostsAdapter;
import com.nikoarap.bloggingapp.models.Post;
import com.nikoarap.bloggingapp.utils.VerticalSpacingDecorator;
import com.nikoarap.bloggingapp.viewmodel.AppViewModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AuthorPostsActivity extends AppCompatActivity implements PostsAdapter.OnPostListener {

    public static final String TAG = "AuthorPostsActivity";

    private static AppViewModel appViewModel;

    public TextView authorTv;
    public CircleImageView authorImg;
    public ImageButton backButton;
    public ImageButton infoButton;
    private static String authorId;
    private String authorName;
    private String authorAvatarUrl;
    private String authorUserName;
    private String authorEmail;
    private String authorAddressLat;
    private String authorAddressLng;
    private RecyclerView recView;
    private ArrayList<String> postImages = new ArrayList<>();
    public ArrayList<Post> postsList = new ArrayList<>();
    private int author_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.author_posts_layout);
        recView = findViewById(R.id.postsRecyclerView);

        setSupportActionBar((Toolbar) findViewById(R.id.widget_toolbar));

        //getting the intent from the previous activity and the variables passed
        Intent i = getIntent();
        authorId = i.getStringExtra("authorId");
        authorName = i.getStringExtra("authorName");
        authorAvatarUrl = i.getStringExtra("authorAvatarUrl");
        authorUserName = i.getStringExtra("authorUserName");
        authorEmail = i.getStringExtra("authorEmail");
        authorAddressLat = i.getStringExtra("authorAddressLat");
        authorAddressLng = i.getStringExtra("authorAddressLng");

        authorTv = findViewById(R.id.name);
        authorImg = findViewById(R.id.image);
        backButton = findViewById(R.id.backbutton);
        infoButton = findViewById(R.id.infobutton);

        String author_textView = authorName + "'" + getString(R.string.authors_posts);
        authorTv.setText(author_textView);
        Glide.with(this)
                .asBitmap()
                .load(authorAvatarUrl)
                .into(authorImg);

        author_id = Integer.parseInt(authorId);

        appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);

        RetrofitRequest();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); //going to the previous activity
            }
        });

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoMenuPopUp(v);
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        observeFromDb(); // start observing after onCreate
    }




    public static void RetrofitRequest() {
        postsByAuthorIdRequest("?", authorId);

    }

    //send request to retrofit
    public static void postsByAuthorIdRequest(String query, String authorID){
        appViewModel.postsByAuthorIdRequest(query, authorID);
    }

    //observing data from the DB
    private void observeFromDb(){
        appViewModel.getPostsByAuthorFromDb(author_id).observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(@Nullable List<Post> posts) {
                if (posts != null){
                    for(Post post: posts){
                        Log.d(TAG, "onChanged: " + post.getAuthorId());
                        populateRecyclerView(posts);
                        postsList.addAll(posts);
                    }
                }
            }
        });
    }



    //method to set the recycler view and populate it
    private void populateRecyclerView(List<Post> postList) {
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recView.setLayoutManager(linearLayoutManager);
        PostsAdapter postsAdapter = new PostsAdapter(this, postList, postImages, this);
        VerticalSpacingDecorator itemDecorator = new VerticalSpacingDecorator(1);
        recView.addItemDecoration(itemDecorator);
        recView.setAdapter(postsAdapter);
        postsAdapter.notifyDataSetChanged();
        recView.scheduleLayoutAnimation();
    }

    public void infoMenuPopUp(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.author_info_menu, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent i = new Intent(AuthorPostsActivity.this, AuthorInfoActivity.class);
                if (menuItem.getItemId() == R.id.info) {
                    i.putExtra("authorName", authorName);
                    i.putExtra("authorAvatarUrl", authorAvatarUrl);
                    i.putExtra("authorUserName", authorUserName);
                    i.putExtra("authorEmail", authorEmail);
                    i.putExtra("authorAddressLat", authorAddressLat);
                    i.putExtra("authorAddressLng", authorAddressLng);
                } else {
                    return false;
                }
                startActivity(i);

                return false;
            }
        });
    }


    @Override
    public void onPostClick(int position) {
        Intent i = new Intent(this, PostCommentsActivity.class);
        i.putExtra("postId", postsList.get(position).getId());
        i.putExtra("postTitle", postsList.get(position).getTitle());
        i.putExtra("postImageUrl", postsList.get(position).getImageUrl());
        i.putExtra("postDate", postsList.get(position).getDate());
        i.putExtra("postBody", postsList.get(position).getBody());
        i.putExtra("authorName", authorName);
        i.putExtra("authorAvatarUrl", authorAvatarUrl);

        startActivity(i);
    }



}
