package com.nikoarap.bloggingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Comment implements Parcelable {

    private String date;

    private String avatarUrl;

    private String id;

    private String postId;

    private String body;

    private String userName;

    private String email;

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

    public void setAvatarUrl (String avatarUrl)
    {
        this.avatarUrl = avatarUrl;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getPostId ()
    {
        return postId;
    }

    public void setPostId (String postId)
    {
        this.postId = postId;
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

    public void setUserName (String userName)
    {
        this.userName = userName;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
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
