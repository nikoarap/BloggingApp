package com.nikoarap.bloggingapp.utils;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nikoarap.bloggingapp.models.Address;

import java.lang.reflect.Type;

public class Converters {

    // converts the type String to type Address (Address model)
    @TypeConverter
    public static Address fromString(String value){
        Type listType = new TypeToken<Address>(){}.getType();
        return new Gson().fromJson(value, listType);
    }

    // converts the type Address (Address model) to type String
    @TypeConverter
    public static String fromArrayList(Address address){
        Gson gson  = new Gson();
        return gson.toJson(address);
    }
}
