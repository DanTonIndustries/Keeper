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
public class UpdateLocalMatchesAsync extends AsyncTask<Void, Void, String> {
    private static final Logger logger = Logger.getLogger(UpdateLocalUsersAsync
            .class
            .getName());

    class MatchEntity{
        private int id;
        private int gameid;

        private void addToLocalDb(SQLiteDatabase db){
            ContentValues values = new ContentValues();
            values.put(KeeperContract.MatchEntry.COLUMN_ID, id);
            values.put(KeeperContract.MatchEntry.COLUMN_GAME_ID, gameid);

            long newRowId;
            newRowId = db.insert(KeeperContract.MatchEntry.TABLE_NAME,
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
            json = apiService.getMatches().execute().getResultsSetData();
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

        // TODO: Read json into list of TeamEntities
        Gson gson = new Gson();
        MatchEntity[] matchArray = gson.fromJson(json, MatchEntity[].class);

        //TODO: For each table drop and replace contents with the remote.
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.execSQL("DELETE FROM " + KeeperContract.MatchEntry.TABLE_NAME);
        for (MatchEntity match : matchArray) {
            match.addToLocalDb(db);
        }
        DatabaseManager.getInstance().closeDatabase();
    }
}
