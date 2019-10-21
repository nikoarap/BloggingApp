package com.nikoarap.bloggingapp.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nikoarap.bloggingapp.repository.DataRepository;
import com.nikoarap.bloggingapp.models.Post;

import java.util.List;

public class PostListViewModel extends AndroidViewModel {

    private DataRepository dataRepository;

    public PostListViewModel(@NonNull Application application) {
        super(application);
        dataRepository = DataRepository.getInstance(application);
    }

    //caches from the server to the db and then to the corresponding activity
    public LiveData<List<Post>> getPostsByAuthorFromDb(int authorId) {
        return dataRepository.getPostsByAuthorFromDb(authorId);
    }

    // sends request to the server and retrofit from the corresponding activity
    public void postsByAuthorIdRequest(String query, String authorID){
        dataRepository.postsByAuthorIdRequest(query, authorID);
    }

}
