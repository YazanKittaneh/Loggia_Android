package com.example.l7.project_chatter.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.l7.project_chatter.R;
import com.parse.PushService;


/**
 * Main Activity that will be displayed to the user prompting them to logIn
 * or SignUp
 *
 * Theoretically, this should be skipped if the user had already logged in
 * or has their user cached
 */
public class AuthenticationActivity extends ActionBarActivity {

    Button signUpButton;
    Button logInButton;
    private String TYPE = "Type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signUpButton = (Button) findViewById(R.id.signUpButton);
        logInButton = (Button) findViewById(R.id.logInButton);



        PushService.setDefaultPushCallback(this, AuthenticationActivity.class);

        /*
         * On click listener for signing in
         */
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

        //ParsePush.subscribeInBackground("Chatters");
    }


/*
    public void onClick(View v)
    {
        Button b = (Button) v;

        Intent validationIntent = new Intent(v.getContext(), ValidationActivity.class);
        validationIntent.putExtra(TYPE, b.getText().toString());
        startActivity(validationIntent);
    }
    */


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
