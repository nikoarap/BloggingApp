package com.nikoarap.bloggingapp.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nikoarap.bloggingapp.repository.DataRepository;
import com.nikoarap.bloggingapp.models.Author;

import java.util.List;

public class AuthorListViewModel extends AndroidViewModel {

    private DataRepository dataRepository;

    public AuthorListViewModel(@NonNull Application application) {
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

}
