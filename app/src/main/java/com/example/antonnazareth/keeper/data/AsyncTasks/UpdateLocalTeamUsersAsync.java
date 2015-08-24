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
public class UpdateLocalTeamUsersAsync extends AsyncTask<Void, Void, String> {
    private static final Logger logger = Logger.getLogger(UpdateLocalUsersAsync
            .class
            .getName());

    class TeamUserEntity{
        private int teamid;
        private int userid;

        private void addToLocalDb(SQLiteDatabase db){
            ContentValues values = new ContentValues();
            values.put(KeeperContract.TeamUserEntry.COLUMN_TEAM_ID, teamid);
            values.put(KeeperContract.TeamUserEntry.COLUMN_USER_ID, userid);

            long newRowId;
            newRowId = db.insert(KeeperContract.TeamUserEntry.TABLE_NAME,
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
            json = apiService.getTeamUsers().execute().getResultsSetData();
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
        TeamUserEntity[] teamUserArray = gson.fromJson(json, TeamUserEntity[]
                .class);

        //TODO: For each table drop and replace contents with the remote.
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.execSQL("DELETE FROM " + KeeperContract.TeamUserEntry.TABLE_NAME);
        for (TeamUserEntity tu : teamUserArray) {
            tu.addToLocalDb(db);
        }
        DatabaseManager.getInstance().closeDatabase();
    }
}
