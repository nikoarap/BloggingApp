package com.nikoarap.bloggingapp.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "authors")
//class that represents the model of the Author data
public class Author implements Parcelable{

    @ColumnInfo(name = "address")
    private Address address;

    @ColumnInfo(name = "avatarUrl")
    private String avatarUrl;

    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @PrimaryKey
    @OnConflictStrategy
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "userName")
    private String userName;

    @ColumnInfo(name = "email")
    private String email;

    public Author(Address address, String avatarUrl, String name, @NonNull String id, String userName, String email) {
        this.address = address;
        this.avatarUrl = avatarUrl;
        this.name = name;
        this.id = id;
        this.userName = userName;
        this.email = email;
    }

    protected Author(Parcel in) {
        address = in.readParcelable(Address.class.getClassLoader());
        avatarUrl = in.readString();
        name = in.readString();
        id = in.readString();
        userName = in.readString();
        email = in.readString();

    }
    @Ignore
    public Author(){

    }

    public static final Creator<Author> CREATOR = new Creator<Author>() {
        @Override
        public Author createFromParcel(Parcel in) {
            return new Author(in);
        }

        @Override
        public Author[] newArray(int size) {
            return new Author[size];
        }
    };



    public Address getAddress ()
    {
        return address;
    }


    public String getAvatarUrl ()
    {
        return avatarUrl;
    }


    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
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
        dest.writeParcelable(address, flags);
        dest.writeString(avatarUrl);
        dest.writeString(name);
        dest.writeString(id);
        dest.writeString(userName);
        dest.writeString(email);

    }
}