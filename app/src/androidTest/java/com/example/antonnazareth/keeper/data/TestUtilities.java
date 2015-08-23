//package com.example.antonnazareth.keeper.data;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.ContentObserver;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.net.Uri;
//import android.os.Handler;
//import android.os.HandlerThread;
//import android.test.AndroidTestCase;
//import android.util.Log;
//
//import java.util.Map;
//import java.util.Set;
//
///**
// * Created by antonnazareth on 20/08/2015.
// */
//
//public class TestUtilities extends AndroidTestCase {
//    static final String TEST_FIRST_NAME = "Jamie";
//    static final String TEST_LAST_NAME = "Tomlin";
//    static final String TEST_FULL_NAME = "JamieTomlin";
//    static final String TEST_NICKNAME = "JatCat";
//
//    public static final String LOG_TAG = TestUtilities.class.getSimpleName();
//
//
//    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
//        Log.d(LOG_TAG, "valueCursor: " + valueCursor.toString());
//        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
//        validateCurrentRecord(error, valueCursor, expectedValues);
//        valueCursor.close();
//    }
//
//    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
//        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
//        for (Map.Entry<String, Object> entry : valueSet) {
//            String columnName = entry.getKey();
//            int idx = valueCursor.getColumnIndex(columnName);
//            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
//            String expectedValue = entry.getValue().toString();
//            assertEquals("Value '" + entry.getValue().toString() +
//                    "' did not match the expected value '" +
//                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
//        }
//    }
//
//    /*
//        Students: Use this to create some default weather values for your database tests.
//     */
//    static ContentValues createUserValues() {
//        ContentValues userValues = new ContentValues();
//        userValues.put(KeeperContract.UserEntry.COLUMN_FIRST_NAME, TEST_FIRST_NAME);
//        userValues.put(KeeperContract.UserEntry.COLUMN_LAST_NAME, TEST_LAST_NAME);
//        userValues.put(KeeperContract.UserEntry.COLUMN_FULL_NAME, TEST_FULL_NAME);
//        userValues.put(KeeperContract.UserEntry.COLUMN_NICKNAME, TEST_NICKNAME);
//
//        return userValues;
//
//    }
//
//    /*
//        Students: You can uncomment this helper function once you have finished creating the
//        LocationEntry part of the KeeperContract.
//     */
//    static ContentValues createTeamValues() {
//        // Create a new map of values, where column names are the keys
//        ContentValues teamValues = new ContentValues();
//        teamValues.put(KeeperContract.TeamEntry.COLUMN_TEAM_NAME, "Champions");
//
//        return teamValues;
//    }
//
//    /*
//        Students: You can uncomment this function once you have finished creating the
//        LocationEntry part of the KeeperContract as well as the WeatherDbHelper.
//     */
//    static long insertUserValues(Context context) {
//        // insert our test records into the database
//        dbHelper tDbHelper = new dbHelper(context);
//        SQLiteDatabase db = tDbHelper.getWritableDatabase();
//        ContentValues testValues = TestUtilities.createUserValues();
//
//        long userRowId;
//        userRowId = db.insert(KeeperContract.UserEntry.TABLE_NAME, null, testValues);
//
//        // Verify we got a row back.
//        assertTrue("Error: Failure to insert North Pole Location Values", userRowId != -1);
//
//        return userRowId;
//    }
//
//    /*
//        Students: The functions we provide inside of TestProvider use this utility class to test
//        the ContentObserver callbacks using the PollingCheck class that we grabbed from the Android
//        CTS tests.
//        Note that this only tests that the onChange function is called; it does not test that the
//        correct Uri is returned.
//     */
//    static class TestContentObserver extends ContentObserver {
//        final HandlerThread mHT;
//        boolean mContentChanged;
//
//        static TestContentObserver getTestContentObserver() {
//            HandlerThread ht = new HandlerThread("ContentObserverThread");
//            ht.start();
//            return new TestContentObserver(ht);
//        }
//
//        private TestContentObserver(HandlerThread ht) {
//            super(new Handler(ht.getLooper()));
//            mHT = ht;
//        }
//
//        // On earlier versions of Android, this onChange method is called
//        @Override
//        public void onChange(boolean selfChange) {
//            onChange(selfChange, null);
//        }
//
//        @Override
//        public void onChange(boolean selfChange, Uri uri) {
//            mContentChanged = true;
//        }
//
////        public void waitForNotificationOrFail() {
////            // Note: The PollingCheck class is taken from the Android CTS (Compatibility Test Suite).
////            // It's useful to look at the Android CTS source for ideas on how to test your Android
////            // applications.  The reason that PollingCheck works is that, by default, the JUnit
////            // testing framework is not running on the main Android application thread.
////            new PollingCheck(5000) {
////                @Override
////                protected boolean check() {
////                    return mContentChanged;
////                }
////            }.run();
////            mHT.quit();
////        }
//    }
//
//    static TestContentObserver getTestContentObserver() {
//        return TestContentObserver.getTestContentObserver();
//    }
//}