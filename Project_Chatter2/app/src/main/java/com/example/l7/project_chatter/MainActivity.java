package com.example.l7.project_chatter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.PushService;
import com.parse.SaveCallback;


public class MainActivity extends ActionBarActivity {

    Button signUpButton;
    Button logInButton;
    private String TYPE = "Type";

    /**
     * onCreate method initiallizes parse with key and sets up parameters
     *
     * TODO:
     * Checks:
     *      If there is a current user cashed: Send to main page
     *      else: Send to logIn/signUp page
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Parse initialization.
        ParseCrashReporting.enable(this);
        Parse.initialize(this.getApplicationContext(), "pq4DTXVfCwDskh0CBEfBhwkrDLzBqmo0Q0Fqu8Om", "5mkjDImOD21MhGM6Brzh7lOriLpfrxj9w47FWCL0");
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);
        PushService.setDefaultPushCallback(this, MainActivity.class);
        // Parse initialization.

        // Listeners
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
        // Listeners

        ParsePush.subscribeInBackground("Chatters");
    }

}
