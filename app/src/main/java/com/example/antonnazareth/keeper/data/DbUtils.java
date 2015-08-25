package com.example.antonnazareth.keeper.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Class containing common database operation methods.
 */
public class DbUtils {

    public static long addUser(String forename, String surname, String
            nickname){

        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();

        ContentValues values = new ContentValues();
        values.put(KeeperContract.UserEntry.COLUMN_FIRST_NAME, forename);
        values.put(KeeperContract.UserEntry.COLUMN_LAST_NAME, surname);
        values.put(KeeperContract.UserEntry.COLUMN_NICKNAME, nickname);

        long rowId;
        rowId = database.insert(KeeperContract.UserEntry.TABLE_NAME, null,
                values);
        //TODO: Add async task.

        DatabaseManager.getInstance().closeDatabase();

        return rowId;
    }

    public static long addTeam(String teamname){

        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();

        ContentValues values = new ContentValues();
        values.put(KeeperContract.TeamEntry.COLUMN_TEAM_NAME, teamname);

        long rowId;
        rowId = database.insert(KeeperContract.TeamEntry.TABLE_NAME, null,
                values);
        //TODO: Add async task.

        DatabaseManager.getInstance().closeDatabase();

        return rowId;
    }

    public static long addGame(String gamename){

        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();

        ContentValues values = new ContentValues();
        values.put(KeeperContract.GameEntry.COLUMN_GAME_NAME, gamename);

        long rowId;
        rowId = database.insert(KeeperContract.GameEntry.TABLE_NAME, null,
                values);
        //TODO: Add async task.

        DatabaseManager.getInstance().closeDatabase();

        return rowId;
    }

    public static long addMatch(int gameid){

        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();

        ContentValues values = new ContentValues();
        values.put(KeeperContract.MatchEntry.COLUMN_GAME_ID, gameid);

        long rowId;
        rowId = database.insert(KeeperContract.MatchEntry.TABLE_NAME, null,
                values);
        //TODO: Add async task.

        DatabaseManager.getInstance().closeDatabase();

        return rowId;
    }

    public static long addTeamUser(int teamid, int userid){

        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();

        ContentValues values = new ContentValues();
        values.put(KeeperContract.TeamUserEntry.COLUMN_TEAM_ID, teamid);
        values.put(KeeperContract.TeamUserEntry.COLUMN_USER_ID, userid);

        long rowId;
        rowId = database.insert(KeeperContract.TeamUserEntry.TABLE_NAME, null,
                values);
        //TODO: Add async task.

        DatabaseManager.getInstance().closeDatabase();

        return rowId;
    }

    public static long addScore(int matchid, int teamid, int score){

        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();

        ContentValues values = new ContentValues();
        values.put(KeeperContract.ScoreEntry.COLUMN_MATCH_ID, matchid);
        values.put(KeeperContract.ScoreEntry.COLUMN_TEAM_ID, teamid);
        values.put(KeeperContract.ScoreEntry.COLUMN_SCORE, score);

        long rowId;
        rowId = database.insert(KeeperContract.ScoreEntry.TABLE_NAME, null,
                values);
        //TODO: Add async task.

        DatabaseManager.getInstance().closeDatabase();

        return rowId;
    }

    public static void addMatchResult(int gameid, int team1id, int team1score,
                                      int team2id, int team2score){

        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();

        //First add the match
        ContentValues matchValues = new ContentValues();
        matchValues.put(KeeperContract.MatchEntry.COLUMN_GAME_ID, gameid);

        long rowid;
        rowid = database.insert(KeeperContract.MatchEntry.TABLE_NAME, null,
                matchValues);

        // Now add the two scores using this matchid... rowid should give us
        // that
        Cursor cursor = database.query(
                KeeperContract.MatchEntry.TABLE_NAME,  // Table to Query
                new String[]{KeeperContract.MatchEntry.COLUMN_ID}, // columns
                "rowid = ?",// "where" clause
                new String[]{String.valueOf(rowid)}, // Values for the "where"
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );
        cursor.moveToFirst();
        int matchid = cursor.getInt(cursor.getColumnIndexOrThrow
                (KeeperContract.MatchEntry.COLUMN_ID));
        cursor.close();

        // Now add the scores.
        addScore(matchid, team1id, team1score);
        addScore(matchid, team2id, team2score);

        DatabaseManager.getInstance().closeDatabase();
    }

    public static void addMatchResult(String gameName, int team1id, int team1score,
                                      int team2id, int team2score){

        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();

        // Look for gameName in database. If it exists, use it's id.
        // Otherwise add it and use the new id!
        Cursor cursor = database.query(
                KeeperContract.GameEntry.TABLE_NAME,  // Table to Query
                null, // columns
                KeeperContract.GameEntry.COLUMN_GAME_NAME + " = ?",// where
                new String[]{gameName}, // Values for the "where"
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        // Get the gameid
        int gameid;
        if (cursor.moveToFirst()) {
            gameid = cursor.getInt(cursor.getColumnIndexOrThrow
                    (KeeperContract.GameEntry.COLUMN_ID));
            cursor.close();
        } else {
            ContentValues gameValues = new ContentValues();
            gameValues.put(KeeperContract.GameEntry.COLUMN_GAME_NAME, gameName);

            long longgameid;
            longgameid = database.insert(KeeperContract.GameEntry.TABLE_NAME,
                    null,
                    gameValues);

            gameid = (int) longgameid;
        }
        //TODO: Swap id's to LONG in schema...
        // Now add the match result!
        addMatchResult(gameid, team1id, team1score, team2id, team2score);

        DatabaseManager.getInstance().closeDatabase();
    }
}
