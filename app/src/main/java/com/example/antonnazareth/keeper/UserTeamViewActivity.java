package com.example.antonnazareth.keeper;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class UserTeamViewActivity extends AppCompatActivity {

    private String customFont = uiUtilities.CUSTOM_FONT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_team_view);

        Typeface font = Typeface.createFromAsset(getAssets(), customFont);

        final Button viewUsersButton = (Button) findViewById(R.id.viewUsers);
        viewUsersButton.setTypeface(font);

        viewUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String genTag = viewUsersButton.getTag().toString();
                Intent launchSettingsIntent = new Intent(view.getContext(), selectUserActivity.class);
                startActivity(launchSettingsIntent);
            }
        });

        final Button addUserButton = (Button) findViewById(R.id.addNewUser);
        addUserButton.setTypeface(font);

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String genTag = addUserButton.getTag().toString();
                Intent launchSettingsIntent = new Intent(view.getContext(), AddUserActivity.class);
                startActivity(launchSettingsIntent);
            }
        });

        final Button viewTeamsButton = (Button) findViewById(R.id.viewTeams);
        viewTeamsButton.setTypeface(font);

        viewTeamsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String genTag = viewTeamsButton.getTag().toString();
                Intent launchSettingsIntent = new Intent(view.getContext(), SelectTeamActivity.class);
                startActivity(launchSettingsIntent);
            }
        });

        final Button addNewTeamButton = (Button) findViewById(R.id.addNewTeam);
        addNewTeamButton.setTypeface(font);

        addNewTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String genTag = addNewTeamButton.getTag().toString();
                Intent launchSettingsIntent = new Intent(view.getContext(), AddTeam.class);
                startActivity(launchSettingsIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_team_view, menu);
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
