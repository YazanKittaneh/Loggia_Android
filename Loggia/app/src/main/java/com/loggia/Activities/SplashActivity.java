package com.loggia.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.loggia.Activities.EventFeedActivity;
import com.loggia.Interfaces.LoggiaUser;
import com.loggia.Model.ParseModels.ParseLoggiaUser;
import com.loggia.R;
import com.loggia.Utils.BackendDomain;
import com.loggia.Utils.LoggiaUtils;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 *  This activity allows functionality for user login and anonymous user log in
 */
public class SplashActivity extends AppCompatActivity {

    Context context;
    LoggiaUser currentUser;
    final BackendDomain currentBackendDomain =  BackendDomain.PARSE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = this;
        LoggiaUtils.initializeBackendService(BackendDomain.PARSE,context);
        currentUser = new ParseLoggiaUser(ParseUser.getCurrentUser());
        if(!currentUser.userActive())
            LoggiaUtils.anonymousUserLogIn(currentBackendDomain);
        loadEventFeed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
     * Starts the event feed activity
     */
    public void loadEventFeed()
    {
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        startActivity(new Intent(context, EventFeedActivity.class));
        finish();
    }
}
