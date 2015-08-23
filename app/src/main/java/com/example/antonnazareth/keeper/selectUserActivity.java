package com.example.antonnazareth.keeper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.antonnazareth.keeper.data.KeeperContract;
import com.example.antonnazareth.keeper.data.dbHelper;

import java.util.ArrayList;


public class selectUserActivity extends ActionBarActivity {

    //public ArrayAdapter<String> mUserAdapter;
    public CustomAdapter mUserAdapter;
    private String customFont = uiUtilities.CUSTOM_FONT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

        Typeface font = Typeface.createFromAsset(getAssets(), customFont);

        Button addUser = (Button) findViewById(R.id.addUserFromSelect);
        addUser.setTypeface(font);

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchAddUserIntent = new Intent(view.getContext(), AddUserActivity.class);
                startActivityForResult(launchAddUserIntent, 0);
            }
        });

        ListView listView = (ListView) findViewById(R.id.list_view_users);
        String[] values = new String[] {""};

        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            arrayList.add(values[i]);
        }

//        mUserAdapter =
//                new ArrayAdapter<String>(
//                        this, // The current context (this activity)
//                        R.layout.user_list_item, // The name of the layout ID.
//                        R.id.user_list_item_textView, // The ID of the textview to populate.
//                        arrayList);

        mUserAdapter = new CustomAdapter(this, arrayList, R.drawable.user);

        // Get a reference to the ListView, and attach this adapter to it.
        listView.setAdapter(mUserAdapter);

        queryDatabase();

        Intent intent = this.getIntent();
        final String teamNumber = intent.getStringExtra(Intent.EXTRA_TEXT);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long viewId = view.getId();
                Context context = getApplicationContext();

                if (viewId == R.id.delete_btn) {
                    //Toast.makeText(context, "Button 1 clicked", Toast.LENGTH_SHORT).show();
                    mUserAdapter.remove(position);
                    //removeFromDatabase(user);
                } else {
                    //Toast.makeText(context, "ListView clicked" + id, Toast.LENGTH_SHORT).show();
                    String userName = mUserAdapter.getText(position);

                    Intent newIntent = new Intent();
                    newIntent.putExtra("userName", userName);
                    setResult(Activity.RESULT_OK, newIntent);
                    finish();
                }
            }
        });
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Context context = getApplicationContext();
//                String userName = mUserAdapter.getText(position);
//
//                Intent newIntent = new Intent();
//                newIntent.putExtra("userName", userName);
//                setResult(Activity.RESULT_OK, newIntent);
//                finish();
                //        .putExtra(Intent.EXTRA_TEXT, dayWeather);
                //startActivity(lauchDetailIntent);
                //int duration = Toast.LENGTH_SHORT;

                //Toast toast = Toast.makeText(context, teamName, duration);
                //toast.show();
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_user, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if (resultCode == Activity.RESULT_OK) {
            String userName = intent.getExtras().getString("userName");
            queryDatabase();
        } else if (resultCode == Activity.RESULT_CANCELED) {
            queryDatabase();
        }
    }
    public void updateUserList(String userName){
        mUserAdapter.add(userName);
    }

    public void queryDatabase(){

        mUserAdapter.clearData();

        dbHelper tDbHelper = new dbHelper(this.getApplicationContext());
        SQLiteDatabase db = tDbHelper.getReadableDatabase(); //needs to BE DONE ON ASYNCTASK!!!
        // A cursor is your primary interface to the query results.
        Cursor cursor = db.query(
                KeeperContract.UserEntry.TABLE_NAME,  // Table to Query
                null, // all columns
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        cursor.moveToFirst();


        while (!cursor.isAfterLast()) {
            String firstName = cursor.getString(
                    cursor.getColumnIndexOrThrow(KeeperContract.UserEntry.COLUMN_FIRST_NAME)
            );
            updateUserList(firstName);

            cursor.moveToNext();
        }

        db.close();
        mUserAdapter.notifyDataSetChanged();

    }
}
