package com.nikoarap.bloggingapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nikoarap.bloggingapp.models.Comment;
import com.nikoarap.bloggingapp.models.Post;
import com.nikoarap.bloggingapp.repository.DataRepository;
import com.nikoarap.bloggingapp.models.Author;

import java.util.List;

public class AppViewModel extends AndroidViewModel {

    private DataRepository dataRepository;

    public AppViewModel(@NonNull Application application) {
        super(application);
        dataRepository = DataRepository.getInstance(application);
    }


    //caches from the server to the db and then to the corresponding activity
    public LiveData<List<Author>> getAuthorsFromDb(){
        return dataRepository.getAuthorsFromDb();
    }

    // sends request to the server and retrofit from the corresponding activity
    public void authorsAPI(){
        dataRepository.authorsAPI();
    }

    //caches from the server to the db and then to the corresponding activity
    public LiveData<List<Post>> getPostsByAuthorFromDb(int authorId) {
        return dataRepository.getPostsByAuthorFromDb(authorId);
    }

    // sends request to the server and retrofit from the corresponding activity
    public void postsByAuthorIdRequest(String query, String authorID){
        dataRepository.postsByAuthorIdRequest(query, authorID);
    }

    //caches from the server to the db and then to the corresponding activity
    public LiveData<List<Comment>> getCommentsByPostFromDb(int postId) {
        return dataRepository.getCommentsByPostFromDb(postId);
    }

    // sends request to the server and retrofit from the corresponding activity
    public void commentsByPostIdRequest(String query, String sort, String and, String postID){
        dataRepository.commentsByPostIdRequest(query, sort, and, postID);
    }



}
