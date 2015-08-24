package com.example.antonnazareth.keeper.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.antonnazareth.keeper.data.AsyncTasks.UpdateLocalUsersAsync;
import com.example.antonnazareth.keeper.data.AsyncTasks.UpdateLocalTeamsAsync;

import java.util.logging.Logger;

/**
 * Use as follows:
 *
 * SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();
 * database.insert(...);
 * //database.close(); Don't close it directly!
 * DatabaseManager.getInstance().closeDatabase();
 */
public class DatabaseManager {
    private static final Logger logger = Logger.getLogger(DatabaseManager.class
            .getName());

    private int mOpenCounter;

    private static DatabaseManager instance;
    private static SQLiteOpenHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    public static synchronized void initializeInstance(SQLiteOpenHelper
                                                               helper) {
        if (instance == null) {
            instance = new DatabaseManager();
            mDatabaseHelper = helper;
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(DatabaseManager.class
                    .getSimpleName() + " is not initialized, call " +
                    "initializeInstance(..) method first.");
        }
        return instance;
    }

    public synchronized SQLiteDatabase openDatabase() {
        mOpenCounter++;
        if(mOpenCounter == 1) {
            //Opening new database
            mDatabase = mDatabaseHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    public synchronized void closeDatabase() {
        mOpenCounter--;
        if (mOpenCounter == 0) {
            // Closing database;
            mDatabase.close();
        }
    }

    public static synchronized void updateLocalDatabase(Context context) {
        logger.info("updateDatabase");
        SharedPreferences dbPrefs = context.getSharedPreferences("DB_PREFS",
                Context.MODE_PRIVATE);
        Long dbLastUpdate = dbPrefs.getLong("DB_LAST_UPDATE", 0);
        Long dbUpdateFreq = dbPrefs.getLong("DB_UPDATE_FREQ", 0);
        Long currentTime = System.currentTimeMillis();

        Long elapsedTime = currentTime - dbLastUpdate;

        if (elapsedTime >= dbUpdateFreq) {
            //TODO: Try to replace our local DB with the remote!
            UpdateLocalUsersAsync updateusers = new UpdateLocalUsersAsync();
            updateusers.execute();
            UpdateLocalTeamsAsync updateteams = new UpdateLocalTeamsAsync();
            updateteams.execute();
        }
    }

}
