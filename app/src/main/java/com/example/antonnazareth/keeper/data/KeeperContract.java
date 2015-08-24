package com.example.antonnazareth.keeper.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by antonnazareth on 20/08/2015.
 */
public class KeeperContract {


    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.example.antonnazareth.keeper";
    public static final String LOG_TAG = KeeperContract.class.getSimpleName();
    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.

    public static final String PATH_USER = "users";
    public static final String PATH_TEAMS = "teams";
    public static final String PATH_USERTEAMS = "userteams";
    public static final String PATH_GAMES = "games";
    public static final String PATH_MATCHES = "matches";
    public static final String PATH_SCORES = "scores";

    /* Inner class that defines the table contents of the location table */
    public static final class UserEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;

        // Table name
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_USER_ID = "id";
        public static final String COLUMN_FIRST_NAME = "forename";
        public static final String COLUMN_LAST_NAME = "surname";
        public static final String COLUMN_NICKNAME = "nickname";

        public static Uri buildUserUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    /* Inner class that defines the table contents of the weather table */
    public static final class TeamEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TEAMS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TEAMS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TEAMS;

        public static final String TABLE_NAME = "team";
        public static final String COLUMN_TEAM_ID = "id";
        public static final String COLUMN_TEAM_NAME = "name";

        public static Uri buildTeamUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class TeamUserEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TEAMS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TEAMS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TEAMS;

        public static final String TABLE_NAME = "teamuser";
        public static final String COLUMN_TEAM_ID = "teamid";
        public static final String COLUMN_USER_ID = "userid";

        public static Uri buildTeamUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class GameEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TEAMS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TEAMS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TEAMS;

        public static final String TABLE_NAME = "game";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_GAME_NAME = "name";

        public static Uri buildTeamUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class MatchEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TEAMS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TEAMS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TEAMS;

        public static final String TABLE_NAME = "match";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_GAME_ID = "gameid";

        public static Uri buildTeamUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class ScoreEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TEAMS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TEAMS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TEAMS;

        public static final String TABLE_NAME = "score";
        public static final String COLUMN_MATCH_ID = "matchid";
        public static final String COLUMN_TEAM_ID = "teamid";
        public static final String COLUMN_SCORE = "score";

        public static Uri buildTeamUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
//    public static final class Team2UserEntry implements BaseColumns {
//
//        public static final Uri CONTENT_URI =
//                BASE_CONTENT_URI.buildUpon().appendPath(TEAMS_2_USER).build();
//
//        public static final String CONTENT_TYPE =
//                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TEAMS_2_USER;
//        public static final String CONTENT_ITEM_TYPE =
//                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TEAMS_2_USER;
//
//        public static final String TABLE_NAME = "team2user";
//        public static final String COLUMN_TEAM_KEY = "team_id";
//        public static final String COLUMN_USER_KEY = "user_id";
//
//        public static Uri buildTeam2UserUri(long id) {
//            return ContentUris.withAppendedId(CONTENT_URI, id);
//        }
//        /*
//            Student: This is the buildWeatherLocation function you filled in.
//         */
//        public static Uri buildUser(String fullName) {
//            return CONTENT_URI.buildUpon().appendPath(fullName).build();
//        }
//
//        public static Uri buildTeam(String teamName) {
//            return CONTENT_URI.buildUpon().appendPath(teamName).build();
//        }
//
//        public static String getTeamFromUri(Uri uri) {
//            return uri.getPathSegments().get(1);
//        }
//
//        public static String getUserFromUri(Uri uri) {
//            return uri.getPathSegments().get(1);
//        }
//
//    }
}