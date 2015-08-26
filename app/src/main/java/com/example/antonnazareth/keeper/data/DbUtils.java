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

    public static int removeUser(int id){
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();

        int rowsDeleted = database.delete(KeeperContract.UserEntry.TABLE_NAME,
                KeeperContract.UserEntry.COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(id)});

        DatabaseManager.getInstance().closeDatabase();
        return rowsDeleted;
    }

    public static Cursor getAllUsers(){
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();

        Cursor cursor = database.query(
                KeeperContract.UserEntry.TABLE_NAME,  // Table to Query
                null, // columns
                null,// "where" clause
                null, // Values for the "where"
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        return cursor;
    }

    public static Cursor getUserById(int userid){
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();

        Cursor cursor = database.query(
                KeeperContract.UserEntry.TABLE_NAME,  // Table to Query
                null, // columns
                KeeperContract.UserEntry.COLUMN_USER_ID + " = ?",// "where"
                new String[] {String.valueOf(userid)}, // Values for the "where"
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        return cursor;
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

    public static int removeTeam(int id){
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();

        int rowsDeleted = database.delete(KeeperContract.TeamEntry.TABLE_NAME,
                KeeperContract.TeamEntry.COLUMN_TEAM_ID + " = ?",
                new String[]{String.valueOf(id)});

        DatabaseManager.getInstance().closeDatabase();
        return rowsDeleted;
    }

    public static Cursor getAllTeams(){
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();

        Cursor cursor = database.query(
                KeeperContract.TeamEntry.TABLE_NAME,  // Table to Query
                null, // columns
                null,// "where" clause
                null, // Values for the "where"
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        return cursor;
    }

    public static Cursor getTeamById(int teamid){
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();

        Cursor cursor = database.query(
                KeeperContract.TeamEntry.TABLE_NAME,  // Table to Query
                null, // columns
                KeeperContract.TeamEntry.COLUMN_TEAM_ID + " = ?",// "where"
                new String[] {String.valueOf(teamid)}, // Values for the "where"
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        return cursor;
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

    public static int removeGame(int id){
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();

        int rowsDeleted = database.delete(KeeperContract.GameEntry.TABLE_NAME,
                KeeperContract.GameEntry.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});

        DatabaseManager.getInstance().closeDatabase();
        return rowsDeleted;
    }

    public static Cursor getAllGames(){
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();

        Cursor cursor = database.query(
                KeeperContract.GameEntry.TABLE_NAME,  // Table to Query
                null, // columns
                null,// "where" clause
                null, // Values for the "where"
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        return cursor;
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

    public static int removeMatch(int id){
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();

        int rowsDeleted = database.delete(KeeperContract.MatchEntry.TABLE_NAME,
                KeeperContract.MatchEntry.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});

        DatabaseManager.getInstance().closeDatabase();
        return rowsDeleted;
    }

    public static Cursor getAllMatches(){
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();

        Cursor cursor = database.query(
                KeeperContract.MatchEntry.TABLE_NAME,  // Table to Query
                null, // columns
                null,// "where" clause
                null, // Values for the "where"
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        return cursor;
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

    public static Cursor getTeamUserByTeamId(int teamid){
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();

        Cursor cursor = database.query(
                KeeperContract.TeamUserEntry.TABLE_NAME,  // Table to Query
                null, // columns
                KeeperContract.TeamUserEntry.COLUMN_TEAM_ID + " = ?",// "where"
                new String[] {String.valueOf(teamid)}, // Values for the "where"
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        return cursor;
    }

    public static Cursor getTeamUserByUserId(int userid){
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();

        Cursor cursor = database.query(
                KeeperContract.TeamUserEntry.TABLE_NAME,  // Table to Query
                null, // columns
                KeeperContract.TeamUserEntry.COLUMN_USER_ID + " = ?",// "where"
                new String[] {String.valueOf(userid)}, // Values for the "where"
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        return cursor;
    }

    public static long addScore(int matchid, int teamid, int score, int winpts){

        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();

        ContentValues values = new ContentValues();
        values.put(KeeperContract.ScoreEntry.COLUMN_MATCH_ID, matchid);
        values.put(KeeperContract.ScoreEntry.COLUMN_TEAM_ID, teamid);
        values.put(KeeperContract.ScoreEntry.COLUMN_SCORE, score);
        values.put(KeeperContract.ScoreEntry.COLUMN_WINPTS, winpts);

        long rowId;
        rowId = database.insert(KeeperContract.ScoreEntry.TABLE_NAME, null,
                values);
        //TODO: Add async task.

        DatabaseManager.getInstance().closeDatabase();

        return rowId;
    }

    public static Cursor getScoresByMatchId(int matchid){
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();

        Cursor cursor = database.query(
                KeeperContract.ScoreEntry.TABLE_NAME,  // Table to Query
                null, // columns
                KeeperContract.ScoreEntry.COLUMN_MATCH_ID + " = ?",// "where"
                new String[] {String.valueOf(matchid)}, // Where values
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        return cursor;
    }

    public static Cursor getScoresByTeamId(int teamid){
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();

        Cursor cursor = database.query(
                KeeperContract.ScoreEntry.TABLE_NAME,  // Table to Query
                null, // columns
                KeeperContract.ScoreEntry.COLUMN_TEAM_ID + " = ?",// "where"
                new String[] {String.valueOf(teamid)}, // Where values
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        return cursor;
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

        DatabaseManager.getInstance().closeDatabase();

        int team1pts;
        int team2pts;

        if (team1score > team2score) {
            team1pts = 3;
            team2pts = 0;
        } else if (team1score < team2score){
            team1pts = 0;
            team2pts = 3;
        } else {
            team1pts = 1;
            team2pts = 1;
        }

        // Now add the scores.
        addScore(matchid, team1id, team1score, team1pts);
        addScore(matchid, team2id, team2score, team2pts);

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
        DatabaseManager.getInstance().closeDatabase();

        //TODO: Swap id's to LONG in schema...
        // Now add the match result!
        addMatchResult(gameid, team1id, team1score, team2id, team2score);

        DatabaseManager.getInstance().closeDatabase();
    }

    public static Cursor getTeamLeaderBoard(){
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();

        //TODO: Is there a better way of doing this?
        String rawSql;
        rawSql = "" +
                "SELECT " +
                "t.id, " +
                "t.name, " +
                "SUM(s.score) as points, " +
                "IFNULL(w.wins, 0) as wins, " +
                "IFNULL(ties.ties, 0) as ties, " +
                "IFNULL(l.losses, 0) as losses " +
                "FROM score s " +
                "INNER JOIN team t ON t.id = s.teamid " +
                "LEFT JOIN " +
                "(SELECT t.id as id, " +
                "COUNT(*) as wins " +
                "FROM score s " +
                "INNER JOIN team t ON t.id = s.teamid " +
                "WHERE s.winpts = 3 " +
                "GROUP BY t.id) AS w ON w.id = t.id " +
                "LEFT JOIN " +
                "(SELECT t.id as id, " +
                "COUNT(*) as ties " +
                "FROM score s " +
                "INNER JOIN team t ON t.id = s.teamid " +
                "WHERE s.winpts = 1 " +
                "GROUP BY t.id) AS ties ON ties.id = t.id " +
                "LEFT JOIN " +
                "(SELECT t.id as id, " +
                "COUNT(*) as losses " +
                "FROM score s " +
                "INNER JOIN team t ON t.id = s.teamid " +
                "WHERE s.winpts = 0 " +
                "GROUP BY t.id) AS l ON l.id = t.id " +
                "GROUP BY t.id " +
                "ORDER BY wins DESC, points DESC";

        return database.rawQuery(rawSql, null);
    }
}
