package com.nikoarap.bloggingapp.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nikoarap.bloggingapp.R;
import com.nikoarap.bloggingapp.models.Post;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {

    private List<Post> postsList;
    public ArrayList<String> postImages;

    public Date date;


    private Context context;
    private PostsAdapter.OnPostListener onPostListener;
    private static final String TAG = "PostsAdapter";

    public PostsAdapter(Context cont, List<Post> postsList, ArrayList<String> postImages, PostsAdapter.OnPostListener onPostListener) {
        context = cont;
        this.postsList = postsList;
        this.postImages = postImages;
        this.onPostListener = onPostListener;
    }

    private static String removeFromTheEnd(String str, int x) {
        return str.substring(0, str.length() - x);
    }

    @NonNull
    @Override
    public PostsAdapter.PostsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_list_item_layout, viewGroup, false);
        return new PostsAdapter.PostsViewHolder(view,onPostListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.PostsViewHolder viewHolder, int position) {

        //formatting the date from ISO8601 to normal
        String dateTime = postsList.get(position).getDate();
        String date_time = removeFromTheEnd(dateTime,5);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            date = dateFormat.parse(date_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateTime2 = removeFromTheEnd(date.toString(),14);

        viewHolder.postDate.setText(dateTime2);
        viewHolder.postTitle.setText(postsList.get(position).getTitle());
        viewHolder.postBody.setText(postsList.get(position).getBody());
        Glide.with(context)
                .asBitmap()
                .load(postsList.get(position).getImageUrl())
                .into(viewHolder.postImage);
    }

    @Override
    public int getItemCount() {
        if (null == postsList) return 0;
        return postsList.size();
    }

    public class PostsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView postDate;
        private TextView postTitle;
        private TextView postBody;
        private ImageView postImage;
        private RelativeLayout rlayout;
        PostsAdapter.OnPostListener onPostListener;


        public PostsViewHolder(View v, PostsAdapter.OnPostListener postListener) {
            super(v);
            postDate = v.findViewById(R.id.post_date);
            postTitle = v.findViewById(R.id.post_title);
            postBody = v.findViewById(R.id.post_body);
            postImage = v.findViewById(R.id.post_image);
            rlayout = v.findViewById(R.id.parent_layout_2);
            onPostListener = postListener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "Item number: " + getAdapterPosition());
            onPostListener.onPostClick(getAdapterPosition()); //click listener gets the position of each individual listItem
        }
    }

    public interface OnPostListener{
        void onPostClick(int position);
    }
}
