package com.example.antonnazareth.keeper.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.example.antonnazareth.keeper.EntityClasses.UserEntity;
import com.example.antonnazareth.keeper.backend.myApi.MyApi;
import com.example.antonnazareth.keeper.backend.myApi.model.MyBean;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Logger;


class TestFuckShit {

    public int id;
}

/**
 * Created by admin on 8/23/2015.
 */
public class UpdateUsers extends AsyncTask<Void, Void, String> {
    private static final Logger logger = Logger.getLogger(UpdateUsers.class
            .getName());

    private static MyApi myApiService = null;
    public dbHelper dbhelper;

    public UpdateUsers(Context context) {
        super();
        this.dbhelper = new dbHelper(context);
    }

/*    @Override
    protected void onPreExecute() {
        *//*
         *    do things before doInBackground() code runs
         *    such as preparing and showing a Dialog or ProgressBar
        *//*
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        *//*
         *    updating data
         *    such a Dialog or ProgressBar
        *//*

    }*/

    @Override
    protected String doInBackground(Void... params){
        logger.warning("doInBackground");
        //do your work here
        MyBean bean;
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://keeper-1337.appspot.com/_ah/api/");
            myApiService = builder.build();
        }
        try{
            bean = myApiService.getUsers().execute();
        } catch (IOException e) {
            logger.warning("IOEXCEPTION OH NO");
            String oops = "";
            return oops;
        }

        logger.warning("End of doInBackground");
        return bean.getResultsSetData();
    }

    @Override
    protected void onPostExecute(String json) {

/*
        String jsononeobj = "{\"id\":\"1\"}";

        logger.warning(jsononeobj);
        Type collectionType = new TypeToken<List<UserEntity[]>>() {}.getType();
*/

        logger.warning(json);


        Gson gson = new Gson();
        TestFuckShit test = new TestFuckShit();
        test.id = 1;
        String jsonnew = gson.toJson(test);
        logger.warning(jsonnew);
        test = gson.fromJson(jsonnew, TestFuckShit.class);
        logger.warning(String.valueOf(test.id));
/*

        logger.warning(collectionType.getClass().getName());

        List<UserEntity> users = gson.fromJson(json, collectionType);

        logger.info(String.valueOf(users.size()));
*/

/*        // Clear the local db
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + KeeperContract.UserEntry
                .TABLE_NAME);*/

        logger.warning("FUCK THIS SHIT");

/*        for (UserEntity user : users) {
            ContentValues userValues = new ContentValues();
            userValues.put(KeeperContract.UserEntry.COLUMN_USER_ID, user.id);
            userValues.put(KeeperContract.UserEntry.COLUMN_FIRST_NAME, user
                    .forename);
            userValues.put(KeeperContract.UserEntry.COLUMN_LAST_NAME, user
                    .surname);
            userValues.put(KeeperContract.UserEntry.COLUMN_NICKNAME, user
                    .nickname);

            logger.warning(user.forename);
            logger.warning(user.nickname);

            // Add the row to the db
            long userRowId = db.insert(KeeperContract.UserEntry.TABLE_NAME,
                    null,
                    userValues);
        }*/
    }
}