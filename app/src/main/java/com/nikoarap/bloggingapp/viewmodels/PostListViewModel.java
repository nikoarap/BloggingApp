package com.nikoarap.bloggingapp.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;

import com.nikoarap.bloggingapp.models.Post;
import com.nikoarap.bloggingapp.repository.DataRepository;

import java.util.List;

public class PostListViewModel extends AndroidViewModel {

    private DataRepository dataRepository;

    public PostListViewModel(@NonNull Application application) {
        super(application);
        dataRepository = new DataRepository(application);
    }

    public LiveData<List<Post>> getPosts() {
        return dataRepository.getPosts();
    }

    public void postsAPI(String query, String authorID){
        dataRepository.postsAPI(query, authorID);
    }

}
