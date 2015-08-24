package com.example.antonnazareth.keeper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MatchActivity extends ActionBarActivity {

    private String teamOneName = "Team 1";
    private String teamTwoName = "Team 2";
    private int teamOneScore;
    private int teamTwoScore;
    private String TEAM_ONE_NAME = "teamOneName";
    private String TEAM_TWO_NAME = "teamTwoName";
    private String customFont = uiUtilities.CUSTOM_FONT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_match);

        Typeface font = Typeface.createFromAsset(getAssets(), customFont);

        if (savedInstanceState != null) {
            // Restore value of members from saved state
            teamOneName = savedInstanceState.getString(TEAM_ONE_NAME);
            teamTwoName = savedInstanceState.getString(TEAM_TWO_NAME);

        }

        Intent intent = this.getIntent();
        String Title = intent.getStringExtra(Intent.EXTRA_TEXT);

        //TextView matchTitle = (TextView) findViewById(R.id.matchTitle);
        //matchTitle.setText(Title);

        TextView teamNameView = (TextView) findViewById(R.id.team1Text);
       // teamNameView.setTypeface(font);
        teamNameView.setText(teamOneName);

        TextView team2NameView = (TextView) findViewById(R.id.team2Text);
        //team2NameView.setTypeface(font);
        team2NameView.setText(teamTwoName);

        EditText editText = (EditText) findViewById(R.id.scoreText2);
        editText.setRawInputType(2);

        EditText editText2 = (EditText) findViewById(R.id.scoreText1);
        editText2.setRawInputType(2);

        Button enterScoreButton = (Button) findViewById(R.id.button9);
        enterScoreButton.setTypeface(font);
        enterScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchSettingsIntent = new Intent(view.getContext(), MainActivity.class);
                startActivity(launchSettingsIntent);
            }
        });

        final Button sel1Button = (Button) findViewById(R.id.selectTeam1Button);
        sel1Button.setTypeface(font);
        sel1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String teamNum = sel1Button.getTag().toString();
                Intent launchSettingsIntent = new Intent(view.getContext(), SelectTeamActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, teamNum);
                startActivityForResult(launchSettingsIntent, 0);
            }
        });

        final Button sel2Button = (Button) findViewById(R.id.selectTeam2Button);
        sel2Button.setTypeface(font);
        sel2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String teamNum2 = sel2Button.getTag().toString();
                Intent launchSettingsIntent = new Intent(view.getContext(), SelectTeamActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, teamNum2);
                startActivityForResult(launchSettingsIntent, 0);
            }
        });

        Button plusButton1 = (Button) findViewById(R.id.plusButton1);
        plusButton1.setTypeface(font);
        plusButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOneToScore(R.id.plusButton1);
            }
        });

        Button minusButton1 = (Button) findViewById(R.id.minusButton1);
        minusButton1.setTypeface(font);
        minusButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subtractOneFromScore(R.id.minusButton1);
            }
        });

        Button plusButton2 = (Button) findViewById(R.id.plusButton2);
        plusButton2.setTypeface(font);
        plusButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOneToScore2(R.id.plusButton2);
            }
        });

        Button minusButton2 = (Button) findViewById(R.id.minusButton2);
        minusButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subtractOneFromScore2(R.id.minusButton2);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_match, menu);
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

    public void addOneToScore(int plusButtonId){
        //Button button = (Button) findViewById(plusButtonId);
        //String name = button.getTag().toString();
        EditText scoreEditText = (EditText) findViewById(R.id.scoreText1);
        String scoreText = scoreEditText.getText().toString();
        Integer score = Integer.valueOf(scoreText);
        Integer newScore = score + 1;
        scoreEditText.setText(Integer.toString(newScore));

    }

    public void addOneToScore2(int plusButtonId){
        //Button button = (Button) findViewById(plusButtonId);
        //String name = button.getTag().toString();
        EditText scoreEditText = (EditText) findViewById(R.id.scoreText2);
        String scoreText = scoreEditText.getText().toString();
        Integer score = Integer.valueOf(scoreText);
        Integer newScore = score + 1;
        scoreEditText.setText(Integer.toString(newScore));

    }
    public void subtractOneFromScore(int minusButton1){
        EditText scoreEditText = (EditText) findViewById(R.id.scoreText1);
        String scoreText = scoreEditText.getText().toString();
        Integer score = Integer.valueOf(scoreText);
        Integer newScore = score - 1;
        scoreEditText.setText(Integer.toString(newScore));

    }

    public void subtractOneFromScore2(int minusButton1){
        EditText scoreEditText = (EditText) findViewById(R.id.scoreText2);
        String scoreText = scoreEditText.getText().toString();
        Integer score = Integer.valueOf(scoreText);
        Integer newScore = score - 1;
        scoreEditText.setText(Integer.toString(newScore));

    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        //int duration = Toast.LENGTH_SHORT;
        //Toast toast = Toast.makeText(this, "doingOAR", duration);
        //toast.show();
        if(resultCode == Activity.RESULT_OK ){
            String teamNumber = intent.getExtras().getString("teamNumber");
            String teamName = intent.getExtras().getString("teamName");
            updateTeamName(Integer.valueOf(teamNumber), teamName);
        }

    }

    public void updateTeamName(int teamNumber, String teamName) {
        if (teamNumber == 1) {
            TextView teamNameView = (TextView) findViewById(R.id.team1Text);
            teamOneName = teamName;
            teamNameView.setText(teamOneName);
        } else if (teamNumber == 2) {
            TextView teamNameView2 = (TextView) findViewById(R.id.team2Text);
            teamTwoName = teamName;
            teamNameView2.setText(teamTwoName);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putString(TEAM_TWO_NAME, teamTwoName);
        savedInstanceState.putString(TEAM_ONE_NAME, teamOneName);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }
}
