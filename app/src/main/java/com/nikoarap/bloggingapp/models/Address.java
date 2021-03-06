package com.nikoarap.bloggingapp.models;

import android.os.Parcel;
import android.os.Parcelable;


public class Address implements Parcelable{

    private String latitude;
    private String longitude;

    private Address(Parcel in) {
        latitude = in.readString();
        longitude = in.readString();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };


    public String getLatitude ()
    {
        return latitude;
    }


    public String getLongitude ()
    {
        return longitude;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(latitude);
        dest.writeString(longitude);
    }
}