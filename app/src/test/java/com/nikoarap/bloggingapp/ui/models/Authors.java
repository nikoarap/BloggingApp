package com.nikoarap.bloggingapp.ui.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nikoarap.bloggingapp.models.Address;

@Entity(tableName = "authors")
//class that represents the model of the Authors data
public class Authors implements Parcelable{

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

    public Authors(Address address, String avatarUrl, String name, @NonNull String id, String userName, String email) {
        this.address = address;
        this.avatarUrl = avatarUrl;
        this.name = name;
        this.id = id;
        this.userName = userName;
        this.email = email;
    }

    private Authors(Parcel in) {
        address = in.readParcelable(Address.class.getClassLoader());
        avatarUrl = in.readString();
        name = in.readString();
        id = in.readString();
        userName = in.readString();
        email = in.readString();

    }


    @Ignore
    public Authors(Authors TEST_Authors_1){

    }

    public static final Creator<Authors> CREATOR = new Creator<Authors>() {
        @Override
        public Authors createFromParcel(Parcel in) {
            return new Authors(in);
        }

        @Override
        public Authors[] newArray(int size) {
            return new Authors[size];
        }
    };

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static Creator<Authors> getCREATOR() {
        return CREATOR;
    }



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

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj == null){
            return false;
        }
        if(getClass() != obj.getClass()){
            return false;
        }
        Authors author = (Authors) obj;
        return author.getAddress()==(getAddress()) && author.getAvatarUrl().equals(getAvatarUrl()) &&
                author.getName().equals(getName()) && author.getId().equals(getId()) &&
                author.getUserName().equals(getUserName()) && author.getEmail().equals(getEmail());
    }

}