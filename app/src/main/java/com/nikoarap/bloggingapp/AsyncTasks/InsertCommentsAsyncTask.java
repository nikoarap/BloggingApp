package com.nikoarap.bloggingapp.AsyncTasks;

import android.os.AsyncTask;

import com.nikoarap.bloggingapp.db.AppDao;
import com.nikoarap.bloggingapp.models.Comment;

public class InsertCommentsAsyncTask extends AsyncTask<Comment, Void, Void> {

    private AppDao appDao;

    public InsertCommentsAsyncTask(AppDao dao) {
        appDao = dao;
    }

    @Override
    protected Void doInBackground(Comment... comments) {
        appDao.insertComments(comments);
        return null;
    }
}
