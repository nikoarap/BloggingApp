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
import com.nikoarap.bloggingapp.models.Comment;
import com.nikoarap.bloggingapp.models.Comment;
import com.nikoarap.bloggingapp.utils.JsonDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    private List<Comment> commentsList;
    public ArrayList<String> commentImages;

    private Context context;
    private CommentsAdapter.OnCommentListener onCommentListener;
    private static final String TAG = "CommentsAdapter";

    public CommentsAdapter(Context cont, List<Comment> commentsList, ArrayList<String> commentImages, CommentsAdapter.OnCommentListener onCommentListener) {
        context = cont;
        this.commentsList = commentsList;
        this.commentImages = commentImages;
        this.onCommentListener = onCommentListener;
    }

    @NonNull
    @Override
    public CommentsAdapter.CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_list_item_layout, viewGroup, false);
        return new CommentsAdapter.CommentsViewHolder(view,onCommentListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.CommentsViewHolder viewHolder, int position) {

        //formatting the date from ISO8601 to normal
        JsonDateFormat jsonDateFormat = new JsonDateFormat();
        String date = jsonDateFormat.convertJsonDateToNormal(commentsList.get(position).getDate());

        viewHolder.commentDate.setText(date);
        viewHolder.commentUserName.setText(commentsList.get(position).getUserName());
        viewHolder.commentEmail.setText(commentsList.get(position).getEmail());
        viewHolder.commentBody.setText(commentsList.get(position).getBody());
        Glide.with(context)
                .asBitmap()
                .load(commentsList.get(position).getAvatarUrl())
                .into(viewHolder.commentImage);
    }

    @Override
    public int getItemCount() {
        if (null == commentsList) return 0;
        return commentsList.size();
    }

    public class CommentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView commentDate;
        private TextView commentUserName;
        private TextView commentEmail;
        private TextView commentBody;
        private ImageView commentImage;
        private RelativeLayout rlayout;
        CommentsAdapter.OnCommentListener onCommentListener;

        public CommentsViewHolder(View v, CommentsAdapter.OnCommentListener commentListener) {
            super(v);
            commentDate = v.findViewById(R.id.comment_date);
            commentUserName = v.findViewById(R.id.comment_username);
            commentEmail = v.findViewById(R.id.comment_email);
            commentBody = v.findViewById(R.id.comment_body);
            commentImage = v.findViewById(R.id.comment_image);
            rlayout = v.findViewById(R.id.parent_layout_3);
            onCommentListener = commentListener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "Item number: " + getAdapterPosition());
            onCommentListener.onCommentClick(getAdapterPosition()); //click listener gets the position of each individual listItem
        }
    }

    public interface OnCommentListener{
        void onCommentClick(int position);
    }
    
}
