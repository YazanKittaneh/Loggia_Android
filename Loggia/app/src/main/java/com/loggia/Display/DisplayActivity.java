package com.loggia.Display;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import com.loggia.Helpers.ImageScaler;
import com.loggia.Helpers.StartClockDialog;
import com.loggia.R;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseFile;
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

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels/4;
        int height = dm.heightPixels/10;


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
        loadData(objectID, classID, width, height);
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
        final Context context = getApplicationContext();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(classID);
        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, com.parse.ParseException e) {
                if (e == null) {
                    collapsingToolbar.setTitle(parseObject.getString("Name"));
                    mEventDate.setText(parseObject.getString("Date"));
                    mEventStartTime.setText(parseObject.getString("StartTime"));
                    mEventEndTime.setText(parseObject.getString("EndTime"));

                    mEventDescription.setText(parseObject.getString("Description"));
                    mEventLocation.setText(parseObject.getString("Location"));
                    Picasso
                            .with(context)
                            .load(parseObject
                                    .getParseFile("Image")
                                    .getUrl())
                            .resize(width,height)
                            .centerCrop()
                            .into(imageView);
                    //loadBackdrop(scaler.decodeSampledBitmapFromParse(getResources(), parseObject));
                } else {
                    Log.i("Information", "This is where information starts:");
                    e.printStackTrace();
                }

            }

        });
    }


}
