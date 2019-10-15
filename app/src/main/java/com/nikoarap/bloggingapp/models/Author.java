package com.nikoarap.bloggingapp.models;

import android.os.Parcel;
import android.os.Parcelable;


//class that represents the model of the Author data
public class Author implements Parcelable{

    private Address address;

    private String avatarUrl;

    private String name;

    private String id;

    private String userName;

    private String email;

    protected Author(Parcel in) {
        address = in.readParcelable(Address.class.getClassLoader());
        avatarUrl = in.readString();
        name = in.readString();
        id = in.readString();
        userName = in.readString();
        email = in.readString();
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

    public void setAddress (Address address)
    {
        this.address = address;
    }

    public String getAvatarUrl ()
    {
        return avatarUrl;
    }

    public void setAvatarUrl (String avatarUrl)
    {
        this.avatarUrl = avatarUrl;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
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
        dest.writeParcelable(address, flags);
        dest.writeString(avatarUrl);
        dest.writeString(name);
        dest.writeString(id);
        dest.writeString(userName);
        dest.writeString(email);
    }
}
