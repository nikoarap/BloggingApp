package com.nikoarap.bloggingapp.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;

import com.nikoarap.bloggingapp.models.Author;
import com.nikoarap.bloggingapp.repository.DataRepository;

import java.util.List;

public class AuthorListViewModel extends AndroidViewModel {

    private DataRepository dataRepository;
    private LiveData<List<Author>> authors;

    public AuthorListViewModel(@NonNull Application application) {
        super(application);
        dataRepository = new DataRepository(application);
        authors = dataRepository.getAuthors();
    }

    public void insert(Author author) {
        dataRepository.insert(author);
    }

    public LiveData<List<Author>> getAuthors() {
        return dataRepository.getAuthors();
    }

    public void authorsAPI(){
        dataRepository.authorsAPI();
    }

}
