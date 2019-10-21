package com.nikoarap.bloggingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nikoarap.bloggingapp.R;
import com.nikoarap.bloggingapp.models.Author;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AuthorsAdapter extends RecyclerView.Adapter<AuthorsAdapter.AuthorsViewHolder> {

    private List<Author> authorsList;

    private Context context;
    private OnAuthorListener onAuthorListener;
    private static final String TAG = "AuthorsAdapter";

    public AuthorsAdapter(Context cont, List<Author> authorList, OnAuthorListener authorListener) {
        context = cont;
        this.authorsList = authorList;
        this.onAuthorListener = authorListener;
    }

    @NonNull
    @Override
    public AuthorsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.author_list_item_layout, viewGroup, false);
        return new AuthorsViewHolder(view,onAuthorListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorsViewHolder viewHolder, int position) {
        viewHolder.authorName.setText(authorsList.get(position).getName()); //setting the authorNames in the listItems of the recyclerView

        //setting the avatars in the listItems of the recyclerView
        Glide.with(context)
                .asBitmap()
                .load(authorsList.get(position).getAvatarUrl())
                .into(viewHolder.authorImage);
    }

    @Override
    public int getItemCount() {
        if (null == authorsList) return 0;
        return authorsList.size();
    }

    //defines the UI and functionality of the recyclerView and its' listItems
    public class AuthorsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView authorName;
        private CircleImageView authorImage;
        OnAuthorListener onAuthorListener;


        private AuthorsViewHolder(View v, OnAuthorListener authorListener) {
            super(v);
            authorName = v.findViewById(R.id.author_name);
            authorImage = v.findViewById(R.id.author_image);
            onAuthorListener = authorListener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "Item number: " + getAdapterPosition());
            onAuthorListener.onAuthorClick(getAdapterPosition()); //click listener gets the position of each individual listItem
        }
    }

    public interface OnAuthorListener{
        void onAuthorClick(int position);
    }

}
