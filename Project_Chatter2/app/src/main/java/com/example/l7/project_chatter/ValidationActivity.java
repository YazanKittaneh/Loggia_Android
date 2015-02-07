package com.example.l7.project_chatter;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.*;
import com.parse.ParseUser;


/**
 * Created by L7 on 1/31/15.
 */
public class ValidationActivity extends ActionBarActivity {

    private String mButtonName;
    private Button mButton;
    private EditText userNameText;
    private EditText passWordText;
    private String username;
    private  String password;
    ParseUser user = new ParseUser();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);
        userNameText = (EditText) findViewById(R.id.Username);
        passWordText = (EditText) findViewById(R.id.Password);

        Intent intent = getIntent();
        mButtonName = intent.getStringExtra("Type");

        //sets the button name passed in from the intent
        mButton = (Button) findViewById(R.id.Signin_button);
        mButton.setText(mButtonName);
    }

    public void onClick(View v)
    {

        //get user name and password
        username = userNameText.getText().toString();
        password = passWordText.getText().toString();


        if(mButtonName == "Login")
            logIn(user, username, password, v);
        else if(mButtonName == "Signin")
            signIn(user, username, password, v);
    }

        private void signIn(ParseUser user, String username, String password, View v) {
            user.setUsername(username);
            user.setPassword(password);

            final Button clicked = (Button) v;

            user.signUpInBackground(new SignUpCallback() {
                                        public void done(ParseException e) {
                                            if (e == null) {
                                                Intent completed = new Intent(clicked.getContext(), CompletionActivity.class);
                                                startActivity(completed);
                                            } else {
                                                Toast.makeText(clicked.getContext(), "Didn't work :c", Toast.LENGTH_LONG);
                                            }
                                        }

                                    }
            );
        }

    private void logIn(ParseUser user, String username, String password, View v)
    {

        final Button clicked = (Button) v;

        ParseUser.logInInBackground(username, password, new LogInCallback() {

            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    Intent completed = new Intent(clicked.getContext(), CompletionActivity.class);
                    startActivity(completed);
                } else {
                    Toast.makeText(clicked.getContext(), "Didn't work :c", Toast.LENGTH_LONG);
                }
        }
    });

    }


}
