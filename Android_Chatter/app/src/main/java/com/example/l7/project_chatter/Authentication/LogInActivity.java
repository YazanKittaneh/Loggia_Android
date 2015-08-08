package com.example.l7.project_chatter.Authentication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.l7.project_chatter.HomePage.HomepageActivity;
import com.example.l7.project_chatter.R;


/**
 * Where the user will enter login information
 */
public class LogInActivity extends ActionBarActivity {

    //Declarations
    EditText mUsername, mPassword;
    LogInController mLogIn;
    //Declarations


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        mUsername = (EditText) findViewById(R.id.Username_Text);
        mPassword = (EditText) findViewById(R.id.Password_Text);
        mLogIn = new LogInController(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_log_in, menu);
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


    /**
     * An onClick method. When the LogIn Button is clicked, it will pass in
     * username and password parameters into controller
     * @param view
     *      Button being clicked
     */
    public void login(View view) {
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();

        Intent intent = new Intent(this, HomepageActivity.class);
        this.startActivity(intent);

        mLogIn.initiateLogIn(username, password);
    }
}

