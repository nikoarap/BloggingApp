package com.nikoarap.bloggingapp.AsyncTasks;

import android.os.AsyncTask;

import com.nikoarap.bloggingapp.db.AppDao;
import com.nikoarap.bloggingapp.models.Author;

public class InsertAuthorsAsyncTask extends AsyncTask<Author, Void, Void> {

    private AppDao appDao;

    public InsertAuthorsAsyncTask(AppDao dao) {
        appDao = dao;
    }

    @Override
    protected Void doInBackground(Author... authors) {
        appDao.insertAuthors(authors);
        return null;
    }


}