package com.example.l7.project_chatter;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by L7 on 2/11/15.
 */

public class LogInController {
    public int result = 0;
    public int initiateLogIn(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (parseUser != null)
                    result = 1;
                else
                    e.printStackTrace();
            }
        });
        return result;
    }
    public LogInController() {

    }
}