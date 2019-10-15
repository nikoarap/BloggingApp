package com.nikoarap.bloggingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nikoarap.bloggingapp.R;
import com.nikoarap.bloggingapp.models.Author;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Author> authorsList;
    public ArrayList<String> authorImages;


    private Context context;
    private OnAuthorListener onAuthorListener;
    private static final String TAG = "RecyclerAdapter";

    public RecyclerAdapter(Context cont, List<Author> authorList, ArrayList<String> authorImages, OnAuthorListener authorListener) {
        context = cont;
        this.authorsList = authorList;
        this.authorImages = authorImages;
        this.onAuthorListener = authorListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.author_list_item_layout, viewGroup, false);
        return new ViewHolder(view,onAuthorListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.authorName.setText(authorsList.get(position).getName());

        //setting the avatars on the listItems
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView authorName;
        private CircleImageView authorImage;
        private RelativeLayout rlayout;
        OnAuthorListener onAuthorListener;


        public ViewHolder(View v, OnAuthorListener authorListener) {
            super(v);
            authorName = v.findViewById(R.id.author_name);
            authorImage = v.findViewById(R.id.author_image);
            rlayout = v.findViewById(R.id.parent_layout);
            onAuthorListener = authorListener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "Item number: " + getAdapterPosition());
            onAuthorListener.onAuthorClick(getAdapterPosition());
            Toast.makeText(context,authorName.getText().toString(),Toast.LENGTH_SHORT).show();

        }
    }

    public interface OnAuthorListener{
        void onAuthorClick(int position);
    }

}
