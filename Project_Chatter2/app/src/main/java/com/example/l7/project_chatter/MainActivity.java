package com.example.l7.project_chatter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;


public class MainActivity extends ActionBarActivity {

    Button signUpButton;
    Button logInButton;
    private String TYPE = "Type";

    /**
     * onCreate method initiallizes parse with key and sets up parameters
     * Checks:
     *      If there is a current user cashed: Send to main page
     *      else: Send to logIn/signUp page
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Crash Reporting.
        ParseCrashReporting.enable(this);

        // Add your initialization code here
        Parse.initialize(this.getApplicationContext(), "pq4DTXVfCwDskh0CBEfBhwkrDLzBqmo0Q0Fqu8Om", "5mkjDImOD21MhGM6Brzh7lOriLpfrxj9w47FWCL0");
        ParseACL defaultACL = new ParseACL();

        ParseACL.setDefaultACL(defaultACL, true);



        //ParseUser currentUser = ParseUser.getCurrentUser();
        //if (currentUser != null) {
        //    startActivity(new Intent(this.getApplicationContext(), HomepageActivity.class));
        //} else {
            //show the signup or login screen
            setContentView(R.layout.activity_main);
        /*
         * On click listener for signing in
         */
        signUpButton = (Button) findViewById(R.id.signUpButton);
        logInButton = (Button) findViewById(R.id.logInButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button) v;

                    Intent validationIntent = new Intent(v.getContext(), SignUpActivity.class);
                    startActivity(validationIntent);
                }
            });

            logInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button) v;

                    Intent validationIntent = new Intent(v.getContext(), LogInActivity.class);
                    startActivity(validationIntent);
                }
            });


        //}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
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
