package com.nikoarap.bloggingapp.AsyncTasks;

import android.os.AsyncTask;

import com.nikoarap.bloggingapp.db.AppDao;
import com.nikoarap.bloggingapp.models.Post;

public class InsertPostsAsyncTask extends AsyncTask<Post, Void, Void> {

    private AppDao appDao;

    public InsertPostsAsyncTask(AppDao dao) {
        appDao = dao;
    }

    @Override
    protected Void doInBackground(Post... posts) {
        appDao.insertPosts(posts);
        return null;
    }
}
