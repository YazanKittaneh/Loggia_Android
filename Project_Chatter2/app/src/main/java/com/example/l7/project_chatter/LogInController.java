package com.example.l7.project_chatter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by L7 on 2/11/15.
 */

public class LogInController {
    LogInActivity parent;
    public void initiateLogIn(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (parseUser != null)
                    parent.startActivity(new Intent(parent, HomepageActivity.class));
                else
                    Log.e("AJSODASG", "AJSDGL");
            }
        });
    }



    public LogInController(Activity a) {
        parent = (LogInActivity) a;
    }
}