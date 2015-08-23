/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.antonnazareth.keeper.data;

//import com.example.antonnazareth.keeper.data.KeeperContract.Team2UserEntry;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Manages a local database for weather data.
 */
public class dbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 2;

    public static final String DATABASE_NAME = "keeper.db";

    public dbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a table to hold locations.  A location consists of the string supplied in the
        // location setting, the city name, and the latitude and longitude
        final String SQL_CREATE_USER_TABLE = "CREATE TABLE " + KeeperContract.UserEntry.TABLE_NAME + " (" +
                KeeperContract.UserEntry.COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KeeperContract.UserEntry.COLUMN_FIRST_NAME + " TEXT NOT NULL, " +
                KeeperContract.UserEntry.COLUMN_LAST_NAME + " TEXT NOT NULL, " +
                KeeperContract.UserEntry.COLUMN_FULL_NAME + " TEXT UNIQUE NOT NULL, " +
                KeeperContract.UserEntry.COLUMN_NICKNAME + " REAL NOT NULL " +
                " );";

        final String SQL_CREATE_TEAM_TABLE = "CREATE TABLE " + KeeperContract.TeamEntry.TABLE_NAME + " (" +

                KeeperContract.TeamEntry.COLUMN_TEAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KeeperContract.TeamEntry.COLUMN_TEAM_NAME + " TEXT UNIQUE NOT NULL " +
                " );";


//        final String SQL_CREATE_TEAM2USER_TABLE = "CREATE TABLE " + Team2UserEntry.TABLE_NAME + " (" +
//
//                KeeperContract.Team2UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//
//                // the ID of the location entry associated with this weather data
//                KeeperContract.TeamEntry.COLUMN_TEAM_NAME + " TEXT UNIQUE NOT NULL, " +
//
//                // Set up the location column as a foreign key to location table.
//                " FOREIGN KEY (" + KeeperContract.Team2UserEntry.COLUMN_USER_KEY + ") REFERENCES " +
//                KeeperContract.UserEntry.TABLE_NAME + " (" + KeeperContract.UserEntry.COLUMN_USER_ID + "), " +
//
//                " FOREIGN KEY (" + KeeperContract.Team2UserEntry.COLUMN_TEAM_KEY + ") REFERENCES " +
//                KeeperContract.TeamEntry.TABLE_NAME + " (" + KeeperContract.TeamEntry.COLUMN_TEAM_ID +
//                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TEAM_TABLE);
//        sqLiteDatabase.execSQL(SQL_CREATE_TEAM2USER_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + KeeperContract.UserEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + KeeperContract.TeamEntry.TABLE_NAME);
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Team2UserEntry.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
