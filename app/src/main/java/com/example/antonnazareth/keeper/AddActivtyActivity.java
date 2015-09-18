package com.example.antonnazareth.keeper;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.antonnazareth.keeper.data.DbUtils;


public class AddActivtyActivity extends ActionBarActivity {

    private String customFont = uiUtilities.CUSTOM_FONT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activty);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_activty, menu);
        Typeface font = Typeface.createFromAsset(getAssets(), customFont);


//        Spinner spinner = (Spinner) findViewById(R.id.userNumberSpinner);
//
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.user_num_array,
////        android.R.layout.simple_spinner_item);
//                R.layout.alt_my_spinner_textview);
////    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        adapter.setDropDownViewResource(R.layout.alt_my_spinner_textview);
//        spinner.setAdapter(adapter);

        Button addActButton = (Button) findViewById(R.id.button10);
        addActButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText gameText = (EditText) findViewById(R.id.activity_name);
                String gameName = gameText.getText().toString();
                if (gameName.equals("")){
                    Intent launchSettingsIntent = new Intent(view.getContext(), MainActivity.class);
                    startActivity(launchSettingsIntent);
                }
                else{
                    DbUtils.addGame(gameName);
                    Intent launchSettingsIntent = new Intent(view.getContext(), MainActivity.class);
                    startActivity(launchSettingsIntent);
                }

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
