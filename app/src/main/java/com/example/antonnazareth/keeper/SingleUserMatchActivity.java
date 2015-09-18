package com.example.antonnazareth.keeper;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.antonnazareth.keeper.EntityClasses.UserEntity;
import com.example.antonnazareth.keeper.data.DbUtils;

import java.util.ArrayList;

public class SingleUserMatchActivity extends AppCompatActivity {

    private String customFont = uiUtilities.CUSTOM_FONT;
    Spinner singleMatchTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_user_match);

        Typeface font = Typeface.createFromAsset(getAssets(), customFont);

        String[] values = new String[] {"GENERIC"};

        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            arrayList.add(values[i]);
        }

        singleMatchTitle = (Spinner) findViewById(R.id.singleUserSpinner);
        CustomArrayAdapter myAdapter = new CustomArrayAdapter(this, R.id.activityChoiceTextView, arrayList);
        singleMatchTitle.setAdapter(myAdapter);

        EditText editText = (EditText) findViewById(R.id.userScoreText1);
        editText.setRawInputType(2);

        Button plusButton1 = (Button) findViewById(R.id.plusButton3);
        plusButton1.setTypeface(font);
        plusButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOneToScore(R.id.plusButton3);
            }
        });

        Button minusButton1 = (Button) findViewById(R.id.minusButton3);
        minusButton1.setTypeface(font);
        minusButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subtractOneFromScore(R.id.minusButton3);
            }
        });

        Button selectUser1Button1 = (Button) findViewById(R.id.selectUser1Button);
        selectUser1Button1.setTypeface(font);
        selectUser1Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchSettingsIntent = new Intent(view.getContext(), selectUserActivity.class);
                startActivityForResult(launchSettingsIntent, 0);
            }
        });

        Button EnterButton1 = (Button) findViewById(R.id.enterScoreButton);
        EnterButton1.setTypeface(font);
        EnterButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchSettingsIntent = new Intent(view.getContext(), MainActivity.class);
                startActivityForResult(launchSettingsIntent, 0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_single_user_match, menu);
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

    public void addOneToScore(int plusButtonId) {
        //Button button = (Button) findViewById(plusButtonId);
        //String name = button.getTag().toString();
        EditText scoreEditText = (EditText) findViewById(R.id.userScoreText1);
        String scoreText = scoreEditText.getText().toString();
        Integer score = Integer.valueOf(scoreText);
        Integer newScore = score + 1;
        scoreEditText.setText(Integer.toString(newScore));

    }

    public void subtractOneFromScore(int minusButton1) {
        EditText scoreEditText = (EditText) findViewById(R.id.userScoreText1);
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
        if (resultCode == Activity.RESULT_OK) {
            int userName = intent.getExtras().getInt("userId");
            updateUserName(userName);
        }

    }

    public void updateUserName(int userId) {
        Cursor cursor = DbUtils.getUserById(userId);
        cursor.moveToFirst();
        UserEntity entity = new UserEntity(cursor);
        String userName= entity.forename;
        TextView userNameView = (TextView) findViewById(R.id.user1Text);
        userNameView.setText(userName);

    }
}
