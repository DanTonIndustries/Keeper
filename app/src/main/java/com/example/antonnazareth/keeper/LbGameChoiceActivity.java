package com.example.antonnazareth.keeper;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class LbGameChoiceActivity extends ActionBarActivity {

    private String customFont = uiUtilities.CUSTOM_FONT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lb_game_choice);
        Typeface font = Typeface.createFromAsset(getAssets(), customFont);

        Button AllLeaderboardButton = (Button) findViewById(R.id.button11);
        AllLeaderboardButton.setTypeface(font);

        AllLeaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchSettingsIntent = new Intent(view.getContext(), LbPlayerChoiceActivity.class);
                startActivity(launchSettingsIntent);
            }
        });


        Button FoozLeaderboardButton = (Button) findViewById(R.id.button12);
        FoozLeaderboardButton.setTypeface(font);

        FoozLeaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchSettingsIntent = new Intent(view.getContext(), LbPlayerChoiceActivity.class);
                startActivity(launchSettingsIntent);
            }
        });


        Button teaLeaderboardButton = (Button) findViewById(R.id.button13);
        teaLeaderboardButton.setTypeface(font);

        teaLeaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchSettingsIntent = new Intent(view.getContext(), LbPlayerChoiceActivity.class);
                startActivity(launchSettingsIntent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lb_game_choice, menu);

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
}
