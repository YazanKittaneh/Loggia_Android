package com.loggia.Display;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import com.loggia.R;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.loggia.Utils.EventDateFormat;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

/**
 * TODO: Fix getDrawable deprication
 *
 */

public class DisplayActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbar;
    Toolbar toolbar;
    TextView mEventStartTime;
    TextView mEventEndTime;
    TextView mEventDescription;
    TextView mEventLocation;
    TextView mEventDate;
    ImageView imageView;

    Context context = this;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        /**************************
         View Declaration
         *************************/
        mEventStartTime = (TextView) findViewById(R.id.Display_Start_Time);
        mEventEndTime = (TextView) findViewById(R.id.Display_End_Time);
        mEventDescription = (TextView) findViewById(R.id.Display_Event_Description);
        mEventLocation = (TextView) findViewById(R.id.Display_Event_Location);
        mEventDate = (TextView) findViewById(R.id.Display_Event_Date);
        imageView = (ImageView) findViewById(R.id.backdrop);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);



        /**************************
        Intent handling
         *************************/
        Intent intent = getIntent();
        String objectID = intent.getStringExtra("objectID");
        String classID = intent.getStringExtra("classID");


        /**************************
         Variables
         *************************/
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));

        /**************************
         Listeners
         *************************/
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        loadData(objectID;
    }


    /**
     * Depricated method to load an image into the backdrop
     * @param image
     *  drawable, will be inputted into the imageview of the backdrop
     */
    private void loadBackdrop(Drawable image) {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        //Glide.with(this).load(image).centerCrop().into(imageView);
        imageView.setImageDrawable(image);
    }

    /**
     * TODO: make method asyncronous (research if its necessary)
     * loadData takes in objectID and classID to get the object from parse
     *
     * @param objectID
     *      ID of object requested
     * @param classID
     *      ID of class requested
     * @param width
     *      width of phone screen
     * @param height
     *      height of phone screen
     */
    private void loadData(String objectID, String classID, final int width, final int height){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("TestDate");
        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, com.parse.ParseException e) {
                if (e == null) {
                   setContent(
                           parseObject.getString("Name"),
                           EventDateFormat.formatTime(parseObject.getDate("StartTime")),
                           EventDateFormat.formatTime(parseObject.getDate("EndTime")),
                           EventDateFormat.formatDate(parseObject.getDate("Date")),
                           parseObject.getParseFile("Image").getUrl(),
                           parseObject.getString("Description"),
                           parseObject.getString("Location")
                   );
                } else {
                    e.printStackTrace();
                }

            }

        });
    }

    /**
     * Sets the ParseObject content in their respective view
     * @param name
     * @param startTime
     * @param endTime
     * @param date
     * @param imageURL
     * @param description
     * @param location
     */
    private void setContent(String name, String startTime, String endTime, String date, String imageURL, String description, String location){

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels/4;
        int height = dm.heightPixels/10;


        collapsingToolbar.setTitle(name);
        mEventDate.setText(date);
        mEventStartTime.setText(startTime);
        mEventEndTime.setText(endTime);
        mEventDescription.setText(description);
        mEventLocation.setText(location);
        Picasso
                .with(context)
                .load(imageURL)
                .resize(width, height)
                .centerCrop()
                .into(imageView);
    }




}
