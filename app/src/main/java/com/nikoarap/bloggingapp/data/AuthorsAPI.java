package com.nikoarap.bloggingapp.data;



import com.nikoarap.bloggingapp.models.Author;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AuthorsAPI {

    //GET AUTHORS API REQUEST
    @GET("/authors")
    Call<List<Author>> getAuthorsApi();

}
