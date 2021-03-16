package com.example.android.roomwordssample;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class WordRepository {

    private com.android.example.roomwordssample.WordDao mWordDao;
    private LiveData<List<com.android.example.roomwordssample.Word>> mAllWords;

    WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAllWords();
    }

    LiveData<List<com.android.example.roomwordssample.Word>> getAllWords() {
        return mAllWords;
    }

    public void insert(com.android.example.roomwordssample.Word word) {
        new insertAsyncTask(mWordDao).execute(word);
    }

    public void deleteAll() {
        new deleteAllWordsAsyncTask(mWordDao).execute();
    }

    // Need to run off main thread
    public void deleteWord(com.android.example.roomwordssample.Word word) {
        new deleteWordAsyncTask(mWordDao).execute(word);
    }

    // Static inner classes below here to run database interactions
    // in the background.

    /**
     * Insert a word into the database.
     */
    private static class insertAsyncTask extends AsyncTask<com.android.example.roomwordssample.Word, Void, Void> {

        private com.android.example.roomwordssample.WordDao mAsyncTaskDao;

        insertAsyncTask(com.android.example.roomwordssample.WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final com.android.example.roomwordssample.Word... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    /**
     * Delete all words from the database (does not delete the table)
     */
    private static class deleteAllWordsAsyncTask extends AsyncTask<Void, Void, Void> {
        private com.android.example.roomwordssample.WordDao mAsyncTaskDao;

        deleteAllWordsAsyncTask(com.android.example.roomwordssample.WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    /**
     *  Delete a single word from the database.
     */
    private static class deleteWordAsyncTask extends AsyncTask<com.android.example.roomwordssample.Word, Void, Void> {
        private com.android.example.roomwordssample.WordDao mAsyncTaskDao;

        deleteWordAsyncTask(com.android.example.roomwordssample.WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final com.android.example.roomwordssample.Word... params) {
            mAsyncTaskDao.deleteWord(params[0]);
            return null;
        }
    }
}
