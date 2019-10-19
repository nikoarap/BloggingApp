package com.nikoarap.bloggingapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.nikoarap.bloggingapp.models.Post;
import com.nikoarap.bloggingapp.repository.DataRepository;

import java.util.List;

public class PostListViewModel extends ViewModel {

    private DataRepository dataRepository;

    public PostListViewModel() {
        dataRepository = DataRepository.getInstance();
    }

    public LiveData<List<Post>> getPosts() {
        return dataRepository.getPosts();
    }

    public void postsAPI(String query, String authorID){
        dataRepository.postsAPI(query, authorID);
    }

}
