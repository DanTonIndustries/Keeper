package com.example.antonnazareth.keeper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.util.Pair;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Button playButton = (Button) findViewById(R.id.button1);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchSettingsIntent = new Intent(view.getContext(), PlayActivity.class);
                startActivity(launchSettingsIntent);
            }
        });

        Button addUserButton = (Button) findViewById(R.id.button2);
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchSettingsIntent = new Intent(view.getContext(), AddUserActivity.class);
                startActivity(launchSettingsIntent);
            }
        });

        Button lbButton = (Button) findViewById(R.id.button3);
        lbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchSettingsIntent = new Intent(view.getContext(), LbGameChoiceActivity.class);
                startActivity(launchSettingsIntent);
            }
        });
        return true;
    }

    @Override
    public void onStart(){
        super.onStart();
        testEndPoints();
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

    public void testEndPoints(){
        new EndpointsAsyncTask().execute(new Pair<Context, String>(this, "Danton"));
    }
}


