package com.example.l7.project_chatter.Splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.l7.project_chatter.Authentication.AuthenticationActivity;
import com.example.l7.project_chatter.HomePage.HomepageActivity;
import com.example.l7.project_chatter.R;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;

public class SplashPageActivity extends Activity {
    private static boolean splashLoaded = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
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
            startActivity(new Intent(SplashPageActivity.this, HomepageActivity.class));
            finish();

        } else {
            startActivity(new Intent(SplashPageActivity.this, AuthenticationActivity.class));
            finish();
        }


    }
}

/*
        if (!splashLoaded) {
            setContentView(R.layout.activity_splash_page);
            int secondsDelayed = 1;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    if (currentUser != null) {
                        startActivity(new Intent(SplashPageActivity.this, HomepageActivity.class));
                        finish();

                    } else {
                        startActivity(new Intent(SplashPageActivity.this, MainActivity.class));
                        finish();
                    }
                }
            }, secondsDelayed * 500);

            splashLoaded = true;
        }
        else {
            Intent goToMainActivity = new Intent(SplashPageActivity.this, HomepageActivity.class);
            goToMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(goToMainActivity);
            finish();
        }

    }
    */



