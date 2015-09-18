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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.antonnazareth.keeper.EntityClasses.UserEntity;
import com.example.antonnazareth.keeper.data.DbUtils;

import java.util.ArrayList;


public class AddTeam extends ActionBarActivity {

    //public ArrayAdapter<String> mTeamUsersAdapter;
    public CustomUserAdapter mTeamUsersAdapter;
    private String customFont = uiUtilities.CUSTOM_FONT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team);

        Typeface font = Typeface.createFromAsset(getAssets(), customFont);


        //inherit from selectTeam
        //Intent addUserIntent = getIntent();
        //final String teamNumber = addUserIntent.getStringExtra(Intent.EXTRA_TEXT);

        ListView listView = (ListView) findViewById(R.id.list_view_team_users);
        String[] values = new String[]{};

//        ArrayList<String> arrayList = new ArrayList<String>();
//        for (int i = 0; i < values.length; ++i) {
//            arrayList.add(values[i]);
//        }

        ArrayList<UserEntity> arrayList = new ArrayList<UserEntity>();

        mTeamUsersAdapter = new CustomUserAdapter(this, arrayList, R.drawable.user);

        //TODO: Mega query for scores.

        //TODO: Leaderboard query, filterable by activity.
        //TODO: For users and teams.

//        mTeamUsersAdapter =
//                new ArrayAdapter<String>(
//                        this, // The current context (this activity)
//                        R.layout.team_users_list_item, // The name of the layout ID.
//                        R.id.team_user_list_item_textView, // The ID of the textview to populate.
//                        arrayList);


        // Get a reference to the ListView, and attach this adapter to it.
        listView.setAdapter(mTeamUsersAdapter);

        TextView teamMembersTextView = (TextView) findViewById(R.id.teamMembersTextView);
        teamMembersTextView.setTypeface(font);

//        TextView teamNameTextView = (TextView) findViewById(R.id.teamNameTextView);
//        teamNameTextView.setTypeface(font);

        Button addUserButton = (Button) findViewById(R.id.addUserToTeam);
        addUserButton.setTypeface(font);

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addUserIntent = new Intent(view.getContext(), selectUserActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, "1");
                startActivityForResult(addUserIntent, 0);
            }
        });

        Button confirmTeamButton = (Button) findViewById(R.id.confirmTeam);
        confirmTeamButton.setTypeface(font);

        confirmTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText teamNameEditText = (EditText) findViewById(R.id.teamNameEditText);
                String teamName = teamNameEditText.getText().toString();
                if (teamName.equals("")){
                    //please enter team name
                    setResult(RESULT_CANCELED);
                    finish();
                }
                else{
                    long teamRowId = DbUtils.addTeam(teamName);
                    int teamId = (int) teamRowId;
                    for(int i=0 ; i<mTeamUsersAdapter.getCount() ; i++){
                        UserEntity userEntity = mTeamUsersAdapter.getEntity(i);
                        int userId = userEntity.id ;
                        DbUtils.addTeamUser(teamId, userId);
                    }


                    Intent returnTeamNameIntent = new Intent(view.getContext(), SelectTeamActivity.class)
                            .putExtra("teamId", teamId);
                    setResult(RESULT_OK, returnTeamNameIntent);
                    finish();
                }

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long viewId = view.getId();
                Context context = getApplicationContext();

                if (viewId == R.id.delete_btn) {
                    //Toast.makeText(context, "Button 1 clicked", Toast.LENGTH_SHORT).show();
                    mTeamUsersAdapter.remove(position);
                    //removeFromDatabase(user);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_team, menu);
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

            int userId = intent.getIntExtra("userId", 0);
            updateTeamListFromId(userId);

        }
    }

    public void updateTeamList(UserEntity entity){

        mTeamUsersAdapter.add(entity);
        mTeamUsersAdapter.notifyDataSetChanged();
    }

    public void updateTeamListFromId(int userId){

        Cursor cursor = DbUtils.getUserById(userId);
        cursor.moveToFirst();
        UserEntity entity = new UserEntity(cursor);
        mTeamUsersAdapter.add(entity);
        mTeamUsersAdapter.notifyDataSetChanged();
    }

    public void confirmTeam() {
        String bob = "1";
    }

//    public void queryDatabase(){
//
//        mTeamUsersAdapter.clearData();
//
//        // A cursor is your primary interface to the query results.
//        Cursor cursor = DbUtils.getAllTeams();
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            String teamName = cursor.getString(
//                    cursor.getColumnIndexOrThrow(KeeperContract.TeamEntry.COLUMN_TEAM_NAME)
//            );
//            updateTeamList(teamName);
//            cursor.moveToNext();
//        }
//    }
}