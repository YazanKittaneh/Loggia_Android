package com.example.l7.project_chatter.SplashPage;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.l7.project_chatter.Activity.MainActivity;
import com.example.l7.project_chatter.AuthenticationPage.SignUpActivity;
import com.example.l7.project_chatter.HomePage.HomepageActivity;
import com.example.l7.project_chatter.R;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;

public class SplashPageActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_page);

        // Initialize Crash Reporting.
        ParseCrashReporting.enable(this);

        Parse.enableLocalDatastore(this);
        // Add your initialization code here
        Parse.initialize(this, "pq4DTXVfCwDskh0CBEfBhwkrDLzBqmo0Q0Fqu8Om", "5mkjDImOD21MhGM6Brzh7lOriLpfrxj9w47FWCL0");
        ParseACL defaultACL = new ParseACL();

        ParseACL.setDefaultACL(defaultACL, true);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            Intent validationIntent = new Intent(this, HomepageActivity.class);
            startActivity(validationIntent);
        } else {
            Intent validationIntent = new Intent(this, MainActivity.class);
            startActivity(validationIntent);
        }
    }
}

