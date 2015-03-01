package com.example.l7.project_chatter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * NOT IMPLEMENTED YET
 *
 * TODO:
 *      Make layout for Event
 *      Take in ID
 *      Get parse object
 */


public class EventActivity extends ActionBarActivity {

    String mEventName;
    String mEventTime;
    String mEventCategory;
    String mEventLocation;
    TextView mEventCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        mEventName = getIntent().getExtras().get("name").toString();
        mEventCounter = (TextView) findViewById(R.id.counters);
        mEventCounter.setText("");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
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
