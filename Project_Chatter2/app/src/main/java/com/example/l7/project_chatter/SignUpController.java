package com.example.l7.project_chatter;

import android.widget.Toast;

import com.parse.*;
import com.parse.ParseUser.*;

/**
 * Created by L7 on 2/11/15.
 */


public class SignUpController {


    public void signUp(String mUserName, String mEmail, String mPassword) {

        final SignUpActivity activity = new SignUpActivity();

        ParseUser user = new ParseUser();
        user.setUsername(mUserName);
        user.setPassword(mPassword);
        user.setEmail(mEmail);

        //user.put("phone", "<a style="cursor:pointer ">650-253-0000</a>");

        user.signUpInBackground(new

                                        SignUpCallback() {
                                            public void done(ParseException e) {
                                                if (e == null) {
                                                    Toast.makeText(activity.getApplicationContext(), "Worked!", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(activity.getApplicationContext(), "Didn't work!", Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        }

        );
    }
}
