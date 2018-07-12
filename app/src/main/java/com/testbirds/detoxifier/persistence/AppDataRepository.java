package com.testbirds.detoxifier.persistence;

import android.app.Application;
import android.os.AsyncTask;

import com.testbirds.detoxifier.model.AppData;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Repository to execute database operations
 */
public class AppDataRepository {

    private AppDataDao appDataDao;
    private Flowable<List<AppData>> appDataList;

    public AppDataRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        appDataDao = db.appDataDao();
    }

    public Flowable<List<AppData>> getAllApps() {
        return this.appDataDao.getAll();
    }

    public void insert (AppData appData) {
        new InsertApplicationTask(appDataDao).execute(appData);
    }

    public void delete (AppData appData) {
        new DeleteApplicationTask(appDataDao).execute(appData);
    }

    /**
     * AsyncTask that will insert one row to the database
     */
    private class InsertApplicationTask extends AsyncTask<AppData, Void, Void> {

        private AppDataDao dao;

        InsertApplicationTask(AppDataDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(final AppData... params) {
            this.dao.insert(params[0]);
            return null;
        }

    }

    /**
     * AsyncTask that will delete a row from the database
     */
    private class DeleteApplicationTask extends AsyncTask<AppData, Void, Void> {

        private AppDataDao dao;

        DeleteApplicationTask(AppDataDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(final AppData... params) {
            this.dao.delete(params[0]);
            return null;
        }

    }

}
