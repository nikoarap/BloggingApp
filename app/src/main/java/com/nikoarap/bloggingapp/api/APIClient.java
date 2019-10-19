package com.nikoarap.bloggingapp.api;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.nikoarap.bloggingapp.models.Author;
import com.nikoarap.bloggingapp.models.Comment;
import com.nikoarap.bloggingapp.models.Post;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class APIClient {

    private static final String TAG = "APIClient";

    public final static int NETWORK_TIMEOUT = 3000;

    private static APIClient instance;
    private MutableLiveData<List<Post>> postsList;
    private MutableLiveData<List<Author>> authorsList;
    private MutableLiveData<List<Comment>> commentsList;
    private RetrievePostsRunnable retrievePostsRunnable;
    private RetrieveAuthorsRunnable retrieveAuthorsRunnable;
    private RetrieveCommentsRunnable retrieveCommentsRunnable;



    public static APIClient getInstance(){
        if(instance == null){
            instance = new APIClient();
        }
        return instance;
    }


    private APIClient(){
        authorsList = new MutableLiveData<>();
        postsList = new MutableLiveData<>();
        commentsList = new MutableLiveData<>();
    }


    public LiveData<List<Author>> getAuthors(){
        return authorsList;
    }


    public LiveData<List<Post>> getPosts(){
        return postsList;
    }

    public LiveData<List<Comment>> getComments(){
        return commentsList;
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
                //let the user know its timed out
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }


    //server request for posts
    public void postsAPI(String query, String authorID){
        if(retrievePostsRunnable != null){
            retrievePostsRunnable = null;
        }
        retrievePostsRunnable = new RetrievePostsRunnable(query, authorID);
        final Future handler = AppExecutors.getInstance().getExec().submit(retrievePostsRunnable);

        //stop the request after 3 seconds
        AppExecutors.getInstance().getExec().schedule(new Runnable() {
            @Override
            public void run() {
                //let the user know its timed out
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    //server request for comments
    public void commentsAPI(String query, String postID){
        if(retrieveCommentsRunnable != null){
            retrieveCommentsRunnable = null;
        }
        retrieveCommentsRunnable = new RetrieveCommentsRunnable(query, postID);
        final Future handler = AppExecutors.getInstance().getExec().submit(retrieveCommentsRunnable);

        //stop the request after 3 seconds
        AppExecutors.getInstance().getExec().schedule(new Runnable() {
            @Override
            public void run() {
                //let the user know its timed out
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }



    // background thread that will handle the request to the server
    private class RetrieveAuthorsRunnable implements Runnable{

        boolean cancelRequest;

        public RetrieveAuthorsRunnable() {
            cancelRequest = false;
        }

        @Override
        public void run() { // actual request happen inside the background thread
            try {
                Response response = getAllAuthors().execute();
                if(cancelRequest){ //check if the request is canceled from the user
                    return;
                }
                if (response.code() == 200 && response.body() != null){
                    List<Author> list = new ArrayList<>(((List<Author>)response.body()));
                    authorsList.postValue(list); //passing data to the LiveData observable

                }
                else{
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error);
                    authorsList.postValue(null);

                }
            } catch (IOException e) {
                e.printStackTrace();
                authorsList.postValue(null);
            }

        }

        //the call to fetch the json data from the api
        private Call<List<Author>> getAllAuthors(){
            return RetrofitRequestClass.fetchApi().getAuthorsApi();
        }

        private void cancelRequest(){
            Log.d(TAG, "canceling the search request");
            cancelRequest = true;
        }
    }



    // background thread that will handle the request to the server
    private class RetrievePostsRunnable implements Runnable{

        private String query;
        private String authorID;
        boolean cancelRequest;

        public RetrievePostsRunnable(String query, String authorID) {
            this.query = query;
            this.authorID = authorID;
            cancelRequest = false;
        }

        @Override
        public void run() { // actual request happen inside the background thread
            try {
                Response response = getPostsByAuthor(query, authorID).execute();
                if(cancelRequest){ //check if the request is canceled from the user
                    return;
                }
                if (response.code() == 200 && response.body() != null){
                    List<Post> list = new ArrayList<>(((List<Post>)response.body()));
                    postsList.postValue(list); //passing data to the LiveData observable

                }
                else{
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error);
                    postsList.postValue(null);

                }
            } catch (IOException e) {
                e.printStackTrace();
                postsList.postValue(null);
            }

        }

        //the call to fetch the json data from the api
        private Call<List<Post>> getPostsByAuthor(String query, String authorID){
            return RetrofitRequestClass.fetchApi().getPostsApi(query, authorID);
        }

        private void cancelRequest(){
            Log.d(TAG, "canceling the search request");
            cancelRequest = true;
        }
    }

    // background thread that will handle the request to the server
    private class RetrieveCommentsRunnable implements Runnable{

        private String query;
        private String postID;
        boolean cancelRequest;

        public RetrieveCommentsRunnable(String query, String postID) {
            this.query = query;
            this.postID = postID;
            cancelRequest = false;
        }

        @Override
        public void run() { // actual request happen inside the background thread
            try {
                Response response = getCommentsByPost(query, postID).execute();
                if(cancelRequest){ //check if the request is canceled from the user
                    return;
                }
                if (response.code() == 200 && response.body() != null){
                    List<Comment> list = new ArrayList<>(((List<Comment>)response.body()));
                    commentsList.postValue(list); //passing data to the LiveData observable

                }
                else{
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error);
                    commentsList.postValue(null);

                }
            } catch (IOException e) {
                e.printStackTrace();
                commentsList.postValue(null);
            }

        }

        //the call to fetch the json data from the api
        private Call<List<Comment>> getCommentsByPost(String query, String postID){
            return RetrofitRequestClass.fetchApi().getCommentsApi(query, postID);
        }

        private void cancelRequest(){
            Log.d(TAG, "canceling the search request");
            cancelRequest = true;
        }
    }

    private void cancelRequest(){
        if(retrieveAuthorsRunnable != null){
            retrieveAuthorsRunnable.cancelRequest();
        }
        if(retrievePostsRunnable != null){
            retrievePostsRunnable.cancelRequest();
        }
        if(retrieveCommentsRunnable != null){
            retrieveCommentsRunnable.cancelRequest();
        }
    }

}
