package com.nikoarap.bloggingapp.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "posts")
public class Post implements Parcelable {

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "imageUrl")
    private String imageUrl;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "body")
    private String body;

    @ColumnInfo(name = "authorId")
    private String authorId;



    protected Post(Parcel in) {
        date = in.readString();
        imageUrl = in.readString();
        id = in.readString();
        title = in.readString();
        body = in.readString();
        authorId = in.readString();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public Post(String date, String imageUrl, @NonNull String id, String title, String body, String authorId) {
        this.date = date;
        this.imageUrl = imageUrl;
        this.id = id;
        this.title = title;
        this.body = body;
        this.authorId = authorId;
    }

    @Ignore
    public Post(){

    }

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    public String getImageUrl ()
    {
        return imageUrl;
    }

    public void setImageUrl (String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getBody ()
    {
        return body;
    }

    public void setBody (String body)
    {
        this.body = body;
    }

    public String getAuthorId ()
    {
        return authorId;
    }

    public void setAuthorId (String authorId)
    {
        this.authorId = authorId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(imageUrl);
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(body);
        dest.writeString(authorId);
    }
}
