package com.nikoarap.bloggingapp.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.nikoarap.bloggingapp.AsyncTasks.InsertCommentsAsyncTask;
import com.nikoarap.bloggingapp.AsyncTasks.InsertPostsAsyncTask;
import com.nikoarap.bloggingapp.api.RetrofitRequestClass;
import com.nikoarap.bloggingapp.db.AppDao;
import com.nikoarap.bloggingapp.db.AppDatabase;
import com.nikoarap.bloggingapp.models.Author;
import com.nikoarap.bloggingapp.models.Comment;
import com.nikoarap.bloggingapp.models.Post;
import com.nikoarap.bloggingapp.AsyncTasks.InsertAuthorsAsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class DataRepository {

    private static final String TAG = "DataRepository";

    private final static int NETWORK_TIMEOUT = 3000;

    private static DataRepository instance;
    private RetrievePostsRunnable retrievePostsRunnable;
    private RetrieveAuthorsRunnable retrieveAuthorsRunnable;
    private RetrieveCommentsRunnable retrieveCommentsRunnable;

    private AppDao appDao;

    public static DataRepository getInstance(Application application){
        if(instance == null){
            instance = new DataRepository(application);
        }
        return instance;
    }

    private DataRepository(Application application){
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        appDao = appDatabase.getAppDao();
    }

    //executes the insertion of the author data to the DB
    private void insertAuthorTask(Author author) {
        new InsertAuthorsAsyncTask(appDao).execute(author);
    }

    //executes the insertion of the post data to the DB
    private void InsertPostTask(Post post) {
        new InsertPostsAsyncTask(appDao).execute(post);
    }

    //executes the insertion of the comment data to the DB
    private void InsertCommentTask(Comment comment) {
        new InsertCommentsAsyncTask(appDao).execute(comment);
    }

    public LiveData<List<Author>> getAuthorsFromDb(){
         return appDao.getAuthors();
    }

    public LiveData<List<Post>> getPostsByAuthorFromDb(int authorId){
        return appDao.getPostsByAuthor(authorId);
    }

    public LiveData<List<Comment>> getCommentsByPostFromDb(int postId){
        return appDao.getCommentsByPost(postId);
    }

    //server request for authors
    public void authorsAPI(){
        if(retrieveAuthorsRunnable != null){
            retrieveAuthorsRunnable = null;
        }
        retrieveAuthorsRunnable = new RetrieveAuthorsRunnable();
        final Future handler = AppExecutors.getInstance().getExec().submit(retrieveAuthorsRunnable);

        //stop the request after 3 seconds
        AppExecutors.getInstance().getExec().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    //server request for posts
    public void postsByAuthorIdRequest(String query, String authorID){
        if(retrievePostsRunnable != null){
            retrievePostsRunnable = null;
        }
        retrievePostsRunnable = new RetrievePostsRunnable(query, authorID);
        final Future handler = AppExecutors.getInstance().getExec().submit(retrievePostsRunnable);

        //stop the request after 3 seconds
        AppExecutors.getInstance().getExec().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    //server request for comments
    public void commentsByPostIdRequest(String query, String sort, String and, String postID){
        if(retrieveCommentsRunnable != null){
            retrieveCommentsRunnable = null;
        }
        retrieveCommentsRunnable = new RetrieveCommentsRunnable(query, sort, and, postID);
        final Future handler = AppExecutors.getInstance().getExec().submit(retrieveCommentsRunnable);

        //stop the request after 3 seconds
        AppExecutors.getInstance().getExec().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    // background thread that will handle the request to the server
    private class RetrieveAuthorsRunnable implements Runnable{

        private RetrieveAuthorsRunnable() {
        }

        @Override
        public void run() { // actual request happens inside the background thread
            try {
                Response response = getAllAuthors().execute();
                if (response.code() == 200 && response.body() != null){
                    List<Author> list = new ArrayList<>(((List<Author>)response.body()));
                    for(Author author: list){
                        insertAuthorTask(author);  //inserts values to db
                    }
                }
                else{
                    assert response.errorBody() != null;
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error);

                }
            } catch (IOException e) {
                e.printStackTrace();

            }
        }

        //the call to fetch the json data from the api
        private Call<List<Author>> getAllAuthors(){
            return RetrofitRequestClass.fetchApi().getAuthorsApi();
        }
    }

    // background thread that will handle the request to the server
    private class RetrievePostsRunnable implements Runnable{

        private String query;
        private String authorID;

        private RetrievePostsRunnable(String query, String authorID) {
            this.query = query;
            this.authorID = authorID;
        }

        @Override
        public void run() { // actual request happens inside the background thread
            try {
                Response response = getPostsByAuthor(query, authorID).execute();
                if (response.code() == 200 && response.body() != null){
                    List<Post> list = new ArrayList<>(((List<Post>)response.body()));
                    for(Post post: list){
                        InsertPostTask(post);  //inserts values to db
                    }
                }
                else{
                    assert response.errorBody() != null;
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error);
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
        }

        //the call to fetch the json data from the api
        private Call<List<Post>> getPostsByAuthor(String query, String authorID){
            return RetrofitRequestClass.fetchApi().getPostsApi(query, authorID);
        }
    }

    // background thread that will handle the request to the server
    private class RetrieveCommentsRunnable implements Runnable{

        private String query;
        private String sort;
        private String and;
        private String postID;

        private RetrieveCommentsRunnable(String query, String sort, String and, String postID) {
            this.query = query;
            this.sort = sort;
            this.and = and;
            this.postID = postID;
        }

        @Override
        public void run() { // actual request happens inside the background thread
            try {
                Response response = getCommentsByPost(query, sort, and, postID).execute();
                if (response.code() == 200 && response.body() != null){
                    List<Comment> list = new ArrayList<>(((List<Comment>)response.body()));
                    for(Comment comment: list){
                        InsertCommentTask(comment); //inserts values to db
                    }
                }
                else{
                    assert response.errorBody() != null;
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //the call to fetch the json data from the api
        private Call<List<Comment>> getCommentsByPost(String query, String sort, String and, String postID){
            return RetrofitRequestClass.fetchApi().getCommentsApi(query, sort, and, postID);
        }

    }
}
