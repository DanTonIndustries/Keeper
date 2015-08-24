package com.example.antonnazareth.keeper;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class PlayActivity extends ActionBarActivity {

    private String customFont = uiUtilities.CUSTOM_FONT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Typeface font = Typeface.createFromAsset(getAssets(), customFont);



        final Button singleUserScoringButton = (Button) findViewById(R.id.singleUserScoring);
        singleUserScoringButton.setTypeface(font);

        singleUserScoringButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag = singleUserScoringButton.getTag().toString();
                Intent launchSettingsIntent = new Intent(view.getContext(), SingleUserMatchActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, tag);

                startActivity(launchSettingsIntent);
            }
        });


        final Button foozButton = (Button) findViewById(R.id.teamScoring);
        foozButton.setTypeface(font);

        foozButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag = foozButton.getTag().toString();
                Intent launchSettingsIntent = new Intent(view.getContext(), MatchActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, tag);

                startActivity(launchSettingsIntent);
            }
        });

//        final Button genericButton = (Button) findViewById(R.id.button6);
//        genericButton.setTypeface(font);
//
//        genericButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String genTag = genericButton.getTag().toString();
//                Intent launchSettingsIntent = new Intent(view.getContext(), MatchActivity.class)
//                        .putExtra(Intent.EXTRA_TEXT, genTag);
//                startActivity(launchSettingsIntent);
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play, menu);


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
