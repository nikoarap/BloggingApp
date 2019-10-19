package com.nikoarap.bloggingapp.repository;

import android.arch.lifecycle.LiveData;

import com.nikoarap.bloggingapp.models.Post;
import com.nikoarap.bloggingapp.api.APIClient;

import java.util.List;

public class DataRepository {

    private static DataRepository instance;
    private APIClient apiClient;


    public static DataRepository getInstance(){
        if(instance == null){
            instance = new DataRepository();
        }
        return instance;
    }

    private DataRepository(){
        apiClient = APIClient.getInstance();
    }


    public LiveData<List<Post>> getPosts(){
        return apiClient.getPosts();
    }

    public void postsAPI(String query, String authorID){
        apiClient.postsAPI(query, authorID);
    }


}
