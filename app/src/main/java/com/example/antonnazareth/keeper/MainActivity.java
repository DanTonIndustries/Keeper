package com.example.antonnazareth.keeper;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.logging.Logger;


public class MainActivity extends ActionBarActivity {
    private static final Logger logger = Logger.getLogger(MainActivity.class
            .getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        logger.warning("onCreate");
        //DatabaseManager.updateLocalDatabase(this);







        setContentView(R.layout.activity_main);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/DistProTh.ttf");

        Button playButton = (Button) findViewById(R.id.button1);
        playButton.setTypeface(font);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchSettingsIntent = new Intent(view.getContext(), PlayActivity.class);
                startActivity(launchSettingsIntent);
            }
        });

        Button addUserButton = (Button) findViewById(R.id.button2);
        addUserButton.setTypeface(font);
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //insertIntoDatabase();
                Intent launchSettingsIntent = new Intent(view.getContext(), UserTeamViewActivity.class);
                startActivity(launchSettingsIntent);
            }
        });

        Button lbButton = (Button) findViewById(R.id.button3);
        lbButton.setTypeface(font);
        lbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchSettingsIntent = new Intent(view.getContext(), LeaderboardActivity.class);
                startActivity(launchSettingsIntent);
                //testEndPoints();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;


    }

    @Override
    public void onStart(){
        super.onStart();
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

}


