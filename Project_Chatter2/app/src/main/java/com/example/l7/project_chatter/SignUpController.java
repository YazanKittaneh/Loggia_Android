package com.example.l7.project_chatter;

import android.content.Intent;
import android.widget.Toast;

import com.parse.*;
import com.parse.ParseUser.*;

/**
 * Created by L7 on 2/11/15.
 */


public class SignUpController {

    boolean check = false;
    SignUpActivity parent;

    public void signUp(String mUserName, String mEmail, String mPassword) {
        ParseUser user = new ParseUser();
        user.setUsername(mUserName);
        user.setPassword(mPassword);
        user.setEmail(mEmail);
        user.signUpInBackground(new SignUpCallback() {
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            parent.startActivity(new Intent(parent, EventActivity.class));
                                        } else {
                                            e.printStackTrace();
                                        }
                                    }
                                }

        );
    }

    public SignUpController(SignUpActivity a) {
        parent = a;
    }
}
