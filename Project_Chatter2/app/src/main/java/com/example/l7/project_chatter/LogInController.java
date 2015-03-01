package com.example.l7.project_chatter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by L7 on 2/11/15.
 */


public class LogInController {
    LogInActivity parent;

    /**
     * Logs in using Parse
     *
     * @param username
     * @param password
     */
    public void initiateLogIn(String username, String password) {
        final ProgressDialog dialog = ProgressDialog.show(parent, "Loading", "Please wait...", true);
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (parseUser != null) {
                    Intent intent = new Intent(parent, EventActivity.class);
                    intent.putExtra("name", "PARTY");
                    parent.startActivity(intent);
                    dialog.setMessage("SUCCESS");
                }
                else {
                    Log.e("AJSODASG", "AJSDGL");
                    dialog.setMessage("FAIL");
                }
                dialog.dismiss();
            }
        });
    }



    public LogInController(Activity a) {
        parent = (LogInActivity) a;
    }
}