package com.example.antonnazareth.keeper.data.AsyncTasks;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.example.antonnazareth.keeper.KeeperApp;
import com.example.antonnazareth.keeper.backend.myApi.MyApi;
import com.example.antonnazareth.keeper.backend.myApi.model.MyBean;
import com.example.antonnazareth.keeper.data.DatabaseManager;
import com.example.antonnazareth.keeper.data.KeeperContract;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by admin on 8/24/2015.
 */
public class AddRemoteUserAsync extends AsyncTask<Void, Void, String> {
    private static final Logger logger = Logger.getLogger(AddRemoteUserAsync
            .class
            .getName());

    class UserEntity{
        private int id;
        private String forename;
        private String surname;
        private String nickname;

        private void addToLocalDb(SQLiteDatabase db){
            ContentValues values = new ContentValues();
            values.put(KeeperContract.UserEntry.COLUMN_USER_ID, id);
            values.put(KeeperContract.UserEntry.COLUMN_FIRST_NAME, forename);
            values.put(KeeperContract.UserEntry.COLUMN_LAST_NAME, surname);
            values.put(KeeperContract.UserEntry.COLUMN_NICKNAME, nickname);

            long newRowId;
            newRowId = db.insert(KeeperContract.UserEntry.TABLE_NAME,
                    null, values);
        }
    }

/*    @Override
    protected void onPreExecute() {
        logger.info("onPreExecute");

    }*/

    /*
    @Override
    protected void onProgressUpdate(Void... values) {
        *//*
         *    updating data
         *    such a Dialog or ProgressBar
        *//*

    }*/

    @Override
    protected String doInBackground(Void... params){
        logger.info("doInBackground");

        String json = "";

        //TODO: Get connection to api service
        MyApi apiService = KeeperApp.getApiService();

        //TODO: Get json from api call for each table
        try{
            json = apiService.getUsers().execute().getResultsSetData();
        } catch (IOException e) {
            logger.severe(e.getLocalizedMessage());
            this.cancel(true); // Cancelling prevents the local db from
            // updating
        }

        logger.info(json);

        return json;
    }

    @Override
    protected void onPostExecute(String json) {
        logger.info("onPostExecute");
        super.onPostExecute(json);
        // This method will be running on UI thread.

    }
}
