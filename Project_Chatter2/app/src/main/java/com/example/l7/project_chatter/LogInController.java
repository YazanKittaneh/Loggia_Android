package com.example.l7.project_chatter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by L7 on 2/11/15.
 */

public class LogInController {
    public boolean result = false;
    Activity parent;
    public void initiateLogIn(String username, String password, final View v, final Activity a) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (parseUser != null)
                    a.startActivity(new Intent(v.getContext(), HomepageActivity.class));
                else
                    Log.e("AJSODASG", "AJSDGL");
            }
        });
    }
    public LogInController() {

    }
}