package com.loggia.Display;

import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.loggia.R;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.loggia.Utils.Constants;
import com.loggia.Utils.EventDateFormat;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.util.Date;

/**
 * TODO: Fix getDrawable deprication
 *
 */

public class DisplayActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbar;
    Toolbar toolbar;

    TextView displayEventStartTime;
    TextView displayEventStartDate;
    TextView displayEventEndTime;
    TextView displayEventEndDate;
    TextView displayEventDescription;
    TextView displayEventLocation;
    ImageView imageView;

    Context context = this;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        /**************************
         View Declaration
         *************************/
        displayEventStartTime = (TextView) findViewById(R.id.Display_Start_Time);
        displayEventStartDate = (TextView) findViewById(R.id.Display_Start_Date);
        displayEventEndTime = (TextView) findViewById(R.id.Display_End_Time);
        displayEventEndDate = (TextView) findViewById(R.id.Display_End_Date);
        displayEventDescription = (TextView) findViewById(R.id.Display_Event_Description);
        displayEventLocation = (TextView) findViewById(R.id.Display_Event_Location);

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

        loadData(objectID);
    }


    /**
     * TODO: make method asyncronous (research if its necessary)
     * loadData takes in objectID and classID to get the object from parse
     *
     * @param objectID
     *      ID of object requested
     */
    private void loadData(String objectID){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(Constants.currentEvents);
        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, com.parse.ParseException e) {
                if (e == null) {
                    setContent(
                            parseObject.getString("Name"),
                            parseObject.getDate("StartTime"),
                            parseObject.getDate("EndTime"),
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
     */
    private void setContent(String name, Date startDate, Date endDate, String imageURL, String description, String location){

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels/4;
        int height = dm.heightPixels/10;
        EventDateFormat format = new EventDateFormat();


        collapsingToolbar.setTitle(name);
        displayEventStartTime.setText(EventDateFormat.formatTime(startDate));
        displayEventStartDate.setText(EventDateFormat.formatDate(startDate));
        displayEventEndTime.setText(EventDateFormat.formatTime(endDate));
        displayEventEndDate.setText(EventDateFormat.formatDate(endDate));
        displayEventDescription.setText(description);
        displayEventLocation.setText(location);
        Picasso
                .with(context)
                .load(imageURL)
                .resize(width, height)
                .centerCrop()
                .into(imageView);
    }




}
