package com.example.antonnazareth.keeper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.antonnazareth.keeper.EntityClasses.UserEntity;
import com.example.antonnazareth.keeper.data.UpdateUsers;
import com.example.antonnazareth.keeper.data.UserAdapter;
import com.example.antonnazareth.keeper.data.KeeperContract;
import com.example.antonnazareth.keeper.data.dbHelper;

import java.util.ArrayList;


public class selectUserActivity extends AppCompatActivity {

    //public ArrayAdapter<String> mUserAdapter;
    public UserAdapter mUserAdapter;

    public void getAllUsers() {
        //TODO: Check time of last appengine call, update local db if needed.
        dbHelper dbhelper = new dbHelper(this.getApplicationContext());
        UpdateUsers asyncusers = new UpdateUsers(this.getApplicationContext());
        asyncusers.execute();

        //TODO: Read from local db and build UserEntity instances.
        SQLiteDatabase db = dbhelper.getReadableDatabase();

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
            UserEntity user = new UserEntity(cursor);
            mUserAdapter.add(user);
            cursor.moveToNext();
        }

        db.close();
        mUserAdapter.notifyDataSetChanged();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

        Button addUser = (Button) findViewById(R.id.addUserFromSelect);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchAddUserIntent = new Intent(view.getContext(), AddUserActivity.class);
                startActivityForResult(launchAddUserIntent, 0);
            }
        });

        ListView listView = (ListView) findViewById(R.id.list_view_users);

        //TODO: Check this is the proper way of initialising the adapter...
        mUserAdapter = new UserAdapter(this, new ArrayList<UserEntity>());
        getAllUsers();
        listView.setAdapter(mUserAdapter);

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
                    //TODO: Change this to pass the user entity object... or
                    // the id if possible.

                    //Toast.makeText(context, "ListView clicked" + id, Toast.LENGTH_SHORT).show();
                    UserEntity user = mUserAdapter.getItem(position);
                    String username = user.nickname;

                    Intent newIntent = new Intent();
                    newIntent.putExtra("userName", username);
                    setResult(Activity.RESULT_OK, newIntent);
                    finish();
                }
            }
        });

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
            getAllUsers();
        } else if (resultCode == Activity.RESULT_CANCELED) {
            getAllUsers();
        }
    }

}
