package com.example.l7.project_chatter.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.l7.project_chatter.Controllers.EventObject;
import com.example.l7.project_chatter.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * NOT IMPLEMENTED YET
 *
 * TODO:
 *      Make layout for Event
 *      Take in ID
 *      Get parse object
 */


public class DisplayEventActivity extends ActionBarActivity {

    /*
        this.eventObject.put(NAME, name);
        this.eventObject.put(TIME, time);
        this.eventObject.put(HOST, host);
        this.eventObject.put(DESCRIPTION, discription);
        this.eventObject.put(LOCATION, location);
        this.eventObject.put(IMAGE, image);
     */
    TextView mEventName;
    TextView mEventTime;
    TextView mEventHost;
    TextView mEventDescription;
    TextView mEventLocation;
    ImageView mEventImage;
    EventObject mEventObject;
    ///ImageView mEventImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_event);
        mEventName = (TextView) findViewById(R.id.Display_Event_Name);
        mEventTime = (TextView) findViewById(R.id.Display_Event_Time);
        mEventHost = (TextView) findViewById(R.id.Display_Event_Host);
        mEventDescription = (TextView) findViewById(R.id.Display_Event_Description);
        mEventLocation = (TextView) findViewById(R.id.Display_Event_Location);
        mEventImage = (ImageView) findViewById(R.id.Display_Event_Image);

        setupPage("iuhYXmsJy5");
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
    /*
        this.eventObject.put(NAME, name);
        this.eventObject.put(TIME, time);
        this.eventObject.put(HOST, host);
        this.eventObject.put(DESCRIPTION, discription);
        this.eventObject.put(LOCATION, location);
        this.eventObject.put(IMAGE, image);
     */

    private void setupPage(String objectID) {
        objectID = "iuhYXmsJy5";

        ParseQuery<ParseObject> query = ParseQuery.getQuery("event");
        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {
                    mEventName.setText(parseObject.getString(mEventObject.NAME));
                    mEventTime.setText(parseObject.getString(mEventObject.TIME));
                    mEventHost.setText(parseObject.getString(mEventObject.HOST));
                    mEventDescription.setText(parseObject.getString(mEventObject.DESCRIPTION));
                    mEventLocation.setText(parseObject.getString(mEventObject.LOCATION));
                    mEventImage.setImageDrawable((Drawable)parseObject.get(mEventObject.IMAGE));
                } else {
                    Log.i("Information", "This is where information starts:");
                    e.printStackTrace();
                }
            }
        });
    }

}
