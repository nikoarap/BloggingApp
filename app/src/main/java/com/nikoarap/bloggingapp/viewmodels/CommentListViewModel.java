package com.nikoarap.bloggingapp.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nikoarap.bloggingapp.repository.DataRepository;
import com.nikoarap.bloggingapp.models.Comment;

import java.util.List;

public class CommentListViewModel extends AndroidViewModel {

    private DataRepository dataRepository;

    public CommentListViewModel(@NonNull Application application) {
        super(application);
        dataRepository = DataRepository.getInstance(application);
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
