package com.nikoarap.bloggingapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.nikoarap.bloggingapp.models.Comment;
import com.nikoarap.bloggingapp.models.Post;
import com.nikoarap.bloggingapp.repository.DataRepository;

import java.util.List;

public class CommentListViewModel extends ViewModel {

    private DataRepository dataRepository;

    public CommentListViewModel() {
        dataRepository = DataRepository.getInstance();
    }

    public LiveData<List<Comment>> getComments() {
        return dataRepository.getComments();
    }

    public void commentsApi(String query, String sort, String and, String postID){
        dataRepository.commentsAPI(query, sort, and, postID);
    }

}
