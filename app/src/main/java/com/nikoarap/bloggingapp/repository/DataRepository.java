package com.nikoarap.bloggingapp.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import com.nikoarap.bloggingapp.db.AppDao;
import com.nikoarap.bloggingapp.db.AppDatabase;
import com.nikoarap.bloggingapp.models.Author;
import com.nikoarap.bloggingapp.models.Comment;
import com.nikoarap.bloggingapp.models.Post;
import com.nikoarap.bloggingapp.api.APIClient;

import java.util.List;

public class DataRepository {

    private static DataRepository instance;
    private APIClient apiClient;
    private LiveData<List<Author>> authors;
    private LiveData<List<Post>> posts;
    private LiveData<List<Comment>> comments;

    private AppDatabase appDatabase;
    private AppDao appDao;

    public DataRepository(Application application) {
        appDatabase = AppDatabase.getInstance(application);
        appDao = appDatabase.getAppDao();
        authors = appDao.getAuthors();
        apiClient = APIClient.getInstance();
    }

    public void insert(Author author) {
        new InsertAuthorAsyncTask(appDao).execute(author);
    }

    private static class InsertAuthorAsyncTask extends AsyncTask<Author, Void, Void> {
        private AppDao appDao;

        private InsertAuthorAsyncTask(AppDao appDao) {
            this.appDao = appDao;
        }

        @Override
        protected Void doInBackground(Author... authors) {
            appDao.insertAuthors(authors[0]);
            return null;
        }
    }

    public LiveData<List<Author>> getAuthors(){
        return apiClient.getAuthors();
    }

    public LiveData<List<Post>> getPosts(){
        return apiClient.getPosts();
    }

    public LiveData<List<Comment>> getComments(){
        return apiClient.getComments();
    }


    //server requests
    public void authorsAPI(){
        apiClient.authorsAPI();
    }

    public void postsAPI(String query, String authorID){
        apiClient.postsAPI(query, authorID);
    }

    public void commentsAPI(String query, String sort, String and, String postID){
        apiClient.commentsAPI(query, sort, and, postID);
    }

}
