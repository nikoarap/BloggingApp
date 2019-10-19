package com.nikoarap.bloggingapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.nikoarap.bloggingapp.models.Author;
import com.nikoarap.bloggingapp.repository.DataRepository;

import java.util.List;

public class AuthorListViewModel extends ViewModel {

    private DataRepository dataRepository;

    public AuthorListViewModel() {
        dataRepository = DataRepository.getInstance();
    }

    public LiveData<List<Author>> getAuthors() {
        return dataRepository.getAuthors();
    }

    public void authorsAPI(){
        dataRepository.authorsAPI();
    }


}
