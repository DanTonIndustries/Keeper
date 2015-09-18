package com.example.antonnazareth.keeper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.antonnazareth.keeper.EntityClasses.TeamEntity;
import com.example.antonnazareth.keeper.data.DatabaseManager;
import com.example.antonnazareth.keeper.data.DbUtils;

import java.util.ArrayList;


public class SelectTeamActivity extends ActionBarActivity {

    //public ArrayAdapter<String> mTeamAdapter;
    public CustomTeamAdapter mTeamAdapt;

    private String customFont = uiUtilities.CUSTOM_FONT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_team);

        Typeface font = Typeface.createFromAsset(getAssets(), customFont);

        TextView existingTeamView = (TextView) findViewById(R.id.existingTeam);
        existingTeamView.setTypeface(font);

        ListView listView = (ListView) findViewById(R.id.list_view_teams);
        String[] values = new String[] {"jat"};

        ArrayList<TeamEntity> arrayList = new ArrayList<TeamEntity>();

//        mTeamAdapter =
//                new ArrayAdapter<String>(
//                        this, // The current context (this activity)
//                        R.layout.team_list_item, // The name of the layout ID.
//                        R.id.team_list_item_textView, // The ID of the textview to populate.
//                        arrayList);
        mTeamAdapt = new CustomTeamAdapter(this, arrayList, R.drawable.team_shirt);

        // Get a reference to the ListView, and attach this adapter to it.
        //listView.setAdapter(mTeamAdapter);
        listView.setAdapter(mTeamAdapt);

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
                    int teamId = mTeamAdapt.getId(position);
                    DbUtils.removeTeam(teamId);
                    mTeamAdapt.remove(position);
                } else {
                    //Toast.makeText(context, "ListView clicked" + id, Toast.LENGTH_SHORT).show();
                    String teamName = mTeamAdapt.getText(position);
                    int teamId = mTeamAdapt.getId(position);

                    Intent newIntent = new Intent();
                    newIntent.putExtra("teamName", teamName);
                    newIntent.putExtra("teamNumber", teamNumber);
                    newIntent.putExtra("teamId", teamId);
                    setResult(Activity.RESULT_OK, newIntent);
                    finish();
                }
            }
        });

        Button addTeam = (Button) findViewById(R.id.addTeam);
        addTeam.setTypeface(font);

        addTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchAddTeamIntent = new Intent(view.getContext(), AddTeam.class).
                        putExtra("teamNumber", teamNumber);
                startActivityForResult(launchAddTeamIntent,0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_team, menu);
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
        if(resultCode == Activity.RESULT_OK ) {
            int teamId = intent.getExtras().getInt("teamId");
            queryDatabase();
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            queryDatabase();
        }
    }

    public void updateTeamList(TeamEntity team){
        mTeamAdapt.add(team);
    }


    public void removeItemFromDb(View v) {
        String itemToRemove = (String)v.getTag();
        //adapter.remove(itemToRemove);
        Toast toast = Toast.makeText(getApplicationContext(),
                "itemToRemove",
                Toast.LENGTH_SHORT);

        toast.show();
    }

    public void queryDatabase(){

        //mTeamAdapter.clear();
        mTeamAdapt.clearData();

        // A cursor is your primary interface to the query results.
        Cursor cursor = DbUtils.getAllTeams();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TeamEntity team = new TeamEntity(cursor);
//            String teamName = cursor.getString(
//                    cursor.getColumnIndexOrThrow(KeeperContract.TeamEntry.COLUMN_TEAM_NAME)
//            );
            updateTeamList(team);
            cursor.moveToNext();
        }

        DatabaseManager.getInstance().closeDatabase();

        mTeamAdapt.notifyDataSetChanged();


    }
}
