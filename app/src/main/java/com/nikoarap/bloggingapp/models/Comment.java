package com.nikoarap.bloggingapp.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "comments")
public class Comment implements Parcelable {

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "avatarUrl")
    private String avatarUrl;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "postId")
    private String postId;

    @ColumnInfo(name = "body")
    private String body;

    @ColumnInfo(name = "userName")
    private String userName;

    @ColumnInfo(name = "email")
    private String email;

    public Comment(String date, String avatarUrl, @NonNull String id, String postId, String body, String userName, String email) {
        this.date = date;
        this.avatarUrl = avatarUrl;
        this.id = id;
        this.postId = postId;
        this.body = body;
        this.userName = userName;
        this.email = email;
    }


    protected Comment(Parcel in) {
        date = in.readString();
        avatarUrl = in.readString();
        id = in.readString();
        postId = in.readString();
        body = in.readString();
        userName = in.readString();
        email = in.readString();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };



    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    public String getAvatarUrl ()
    {
        return avatarUrl;
    }

    @NonNull
    public String getId ()
    {
        return id;
    }

    public void setId (@NonNull String id)
    {
        this.id = id;
    }

    public String getPostId ()
    {
        return postId;
    }

    public String getBody ()
    {
        return body;
    }

    public void setBody (String body)
    {
        this.body = body;
    }

    public String getUserName ()
    {
        return userName;
    }

    public String getEmail ()
    {
        return email;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(avatarUrl);
        dest.writeString(id);
        dest.writeString(postId);
        dest.writeString(body);
        dest.writeString(userName);
        dest.writeString(email);
    }
}
