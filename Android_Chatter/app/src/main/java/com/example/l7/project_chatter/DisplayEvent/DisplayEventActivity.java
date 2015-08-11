package com.example.l7.project_chatter.DisplayEvent;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.l7.project_chatter.CreateEvent.EventObject;
import com.example.l7.project_chatter.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class DisplayEventActivity extends AppCompatActivity {

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

    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView image;

    android.support.v7.widget.Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_display_event);
        setContentView(R.layout.example_bar);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);


        /*
        image = (ImageView) findViewById(R.id.image);
        //image.setImageResource(R.drawable.pic2);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);


        mEventName = (TextView) findViewById(R.id.Display_Event_Name);
        mEventTime = (TextView) findViewById(R.id.Display_Event_Time);
        mEventHost = (TextView) findViewById(R.id.Display_Event_Host);
        mEventDescription = (TextView) findViewById(R.id.Display_Event_Description);
        mEventLocation = (TextView) findViewById(R.id.Display_Event_Location);
        mEventImage = (ImageView) findViewById(R.id.Display_Event_Image);
        setupPage("thing");


        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Collapsing");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        setPalette();



        //String objectID = getIntent().getStringExtra("objectID");
        //Log.d("data:", objectID );
        //setupPage(objectID);

*/       final android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("TESTACTIVITY");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        loadBackdrop();
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(R.drawable.pic).centerCrop().into(imageView);
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

    private void setupPage(final String objectID) {
        /*
        ParseQuery<ParseObject> query = ParseQuery.getQuery("event");
        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {
                    EventObject mEventObject = new EventObject(parseObject);
                    mEventName.setText(mEventObject.getEventName());
                    mEventTime.setText(mEventObject.getEventTime());
                    mEventHost.setText(parseObject.getString(mEventObject.getEventHost()));
                    mEventDescription.setText(mEventObject.getEventDescription());
                    mEventLocation.setText(mEventObject.getEventLocation());
                    image.setImageDrawable(mEventObject.getEventImage());

                    //mEventImage.setImageDrawable(mEventObject.getEventImage());
                } else {
                    Log.i("Information", "This is where information starts:");
                    e.printStackTrace();
                }
            }

        });
        */
        mEventName.setText("EXAMPLE EVENT!");

        mEventTime.setText("9:00p.m");
        mEventHost.setText("ME");
        mEventDescription.setText("I TALK A LOT I TALK A LOT I TALK A LOT");
        mEventLocation.setText("GARDENER");
        image.setImageDrawable(getResources().getDrawable(R.drawable.concert));

    }

}
