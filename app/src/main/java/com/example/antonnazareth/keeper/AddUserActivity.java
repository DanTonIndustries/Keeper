package com.example.antonnazareth.keeper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.antonnazareth.keeper.data.DatabaseManager;
import com.example.antonnazareth.keeper.data.DbUtils;
import com.example.antonnazareth.keeper.data.KeeperContract;
import com.example.antonnazareth.keeper.data.dbHelper;


public class AddUserActivity extends ActionBarActivity {

    private String customFont = uiUtilities.CUSTOM_FONT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_user, menu);

        Typeface font = Typeface.createFromAsset(getAssets(), customFont);


        Button clearDbButton = (Button) findViewById(R.id.clearDb);

        clearDbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearDb();
            }
        });

        Button addUser = (Button) findViewById(R.id.button20);
        addUser.setTypeface(font);

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText enterFirstName = (EditText) findViewById(R.id.enter_first_name);
                String firstName = enterFirstName.getText().toString();

                EditText enterLastName = (EditText) findViewById(R.id.enter_last_name);
                String lastName = enterLastName.getText().toString();

                EditText enterNickname = (EditText) findViewById(R.id.enter_nickname);
                String nickname = enterNickname.getText().toString();

                if (firstName.equals("")) {
                    setResult(Activity.RESULT_CANCELED);
                    finish();
                } else {

                    DbUtils.addUser(firstName, lastName, nickname);
                    Intent newIntent = new Intent();
                    newIntent.putExtra("userName", firstName);
                    setResult(Activity.RESULT_OK, newIntent);
                    finish();
                }
                //Intent launchSettingsIntent = new Intent(view.getContext(), MainActivity.class);
                //startActivity(launchSettingsIntent);
            }
        });

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


    public void clearDb(){
        this.getApplicationContext().deleteDatabase(dbHelper.DATABASE_NAME);
    }


}
