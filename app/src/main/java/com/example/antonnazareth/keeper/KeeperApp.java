package com.example.antonnazareth.keeper;

import android.app.Application;

import com.example.antonnazareth.keeper.backend.myApi.MyApi;
import com.example.antonnazareth.keeper.data.DatabaseManager;
import com.example.antonnazareth.keeper.data.dbHelper;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;


/**
 * Created by admin on 8/24/2015.
 */
public class KeeperApp extends Application {

    private static MyApi mApiService;

    @Override
    public void onCreate(){
        super.onCreate();

        // Initialise local db manager
        DatabaseManager.initializeInstance(new dbHelper(this));

        // Initialise Api Service handler.
        buildApiService();
    }

    private void buildApiService(){
        if(mApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://keeper-1337.appspot.com/_ah/api/");
            mApiService = builder.build();
        }
    }

    public static MyApi getApiService(){
        return mApiService;
    }
}
