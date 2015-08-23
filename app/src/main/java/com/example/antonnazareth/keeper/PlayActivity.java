package com.example.antonnazareth.keeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class PlayActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play, menu);

        final Button foozButton = (Button) findViewById(R.id.button5);
        foozButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag = foozButton.getTag().toString();
                Intent launchSettingsIntent = new Intent(view.getContext(), MatchActivity.class)
                    .putExtra(Intent.EXTRA_TEXT, tag);

                startActivity(launchSettingsIntent);
            }
        });

        final Button genericButton = (Button) findViewById(R.id.button6);
        genericButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String genTag = genericButton.getTag().toString();
                Intent launchSettingsIntent = new Intent(view.getContext(), MatchActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, genTag);
                startActivity(launchSettingsIntent);
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
}
