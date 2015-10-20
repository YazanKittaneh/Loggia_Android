package com.loggia.Display;

import android.app.Fragment;
import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.loggia.Feed.EventFeedActivity;
import com.loggia.R;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loggia.Utils.Constants;
import com.loggia.Utils.EventDateFormat;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.util.Date;

/**
 * TODO: Fix getDrawable deprication
 *
 */

public class DisplayActivity extends android.support.v4.app.Fragment {

    CollapsingToolbarLayout collapsingToolbar;
    Toolbar toolbar;

    TextView displayEventStartTime;
    TextView displayEventStartDate;
    TextView displayEventEndTime;
    TextView displayEventEndDate;
    TextView displayEventDescription;
    TextView displayEventLocation;
    ImageView imageView;

    static ParseObject currentParseObject;

    DisplayActivity context = this;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DisplayActivity() {
    }

    /**
     * Constructor created by the newInstance and takes in the Item ID
     */
    public static DisplayActivity newInstance(ParseObject currentObject) {
        DisplayActivity fragment = new DisplayActivity();
        currentParseObject = currentObject;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.activity_display,
                container, false);

        /**************************
         View Declaration
         *************************/
        displayEventStartTime = (TextView) mView.findViewById(R.id.Display_Start_Time);
        displayEventStartDate = (TextView) mView.findViewById(R.id.Display_Start_Date);
        displayEventEndTime = (TextView) mView.findViewById(R.id.Display_End_Time);
        displayEventEndDate = (TextView) mView.findViewById(R.id.Display_End_Date);
        displayEventDescription = (TextView) mView.findViewById(R.id.Display_Event_Description);
        displayEventLocation = (TextView) mView.findViewById(R.id.Display_Event_Location);

        imageView = (ImageView) mView.findViewById(R.id.backdrop);
        toolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        collapsingToolbar =
                (CollapsingToolbarLayout) mView.findViewById(R.id.collapsing_toolbar);
        //setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        /*
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        */

        loadData(currentParseObject);
        return mView;
    }


    /**
     * TODO: make method asyncronous (research if its necessary)
     * loadData takes in objectID and classID to get the object from parse
     */

    private void loadData(ParseObject parseObject){
                    setContent(
                            parseObject.getString("Name"),
                            parseObject.getDate("StartTime"),
                            parseObject.getDate("EndTime"),
                            parseObject.getParseFile("Image").getUrl(),
                            parseObject.getString("Description"),
                            parseObject.getString("Location")
                    );
    }


    /**
     * Sets the ParseObject content in their respective view
     */
    private void setContent(String name, Date startDate, Date endDate, String imageURL, String description, String location){


        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
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
                .with(context.getContext())
                .load(imageURL)
                .resize(width, height)
                .centerCrop()
                .into(imageView);
    }


    /**
     * TODO: Create OnOptionsCreate method to handle backclicks
     */



}
