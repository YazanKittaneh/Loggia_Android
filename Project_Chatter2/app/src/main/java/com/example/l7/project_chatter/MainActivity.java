package com.example.l7.project_chatter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.parse.*;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;


public class MainActivity extends ActionBarActivity {

    Button SignIn_Button;
    Button LogIn_Button;
    private String TYPE = "Type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SignIn_Button = (Button) findViewById(R.id.Signin_button);
        LogIn_Button = (Button) findViewById(R.id.Login_button);

        // Initialize Crash Reporting.
        ParseCrashReporting.enable(this);

        // Add your initialization code here
        Parse.initialize(this, "pq4DTXVfCwDskh0CBEfBhwkrDLzBqmo0Q0Fqu8Om", "5mkjDImOD21MhGM6Brzh7lOriLpfrxj9w47FWCL0");
        ParseACL defaultACL = new ParseACL();

        ParseACL.setDefaultACL(defaultACL, true);
    }



    public void onClick(View v)
    {
        Button b = (Button) v;

        Intent validationIntent = new Intent(v.getContext(), ValidationActivity.class);
        validationIntent.putExtra(TYPE, b.getText().toString());
        startActivity(validationIntent);
    }

    /*

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup, menu);
        return true;
    }
    */

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
