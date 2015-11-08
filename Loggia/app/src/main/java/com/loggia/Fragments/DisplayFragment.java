package com.loggia.Fragments;


import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;

import com.loggia.Interfaces.LoggiaEvent;
import com.loggia.R;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loggia.Utils.EventDateFormat;
import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

import java.util.Date;

/**
 * TODO: Fix getDrawable deprication
 *
 */

public class DisplayFragment extends Fragment {

    CollapsingToolbarLayout collapsingToolbar;
    Toolbar toolbar;

    TextView displayEventStartTime;
    TextView displayEventStartDate;
    TextView displayEventEndTime;
    TextView displayEventEndDate;
    TextView displayEventDescription;
    TextView displayEventLocation;
    ImageView imageView;
    FloatingActionButton inviteButton;


    static LoggiaEvent currentEvent;

    DisplayFragment context = this;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DisplayFragment() {
    }


    /**
     * Constructor created by the newInstance and takes in the Item ID
     */
    public static DisplayFragment newInstance(LoggiaEvent currentObject) {
        DisplayFragment fragment = new DisplayFragment();
        currentEvent = currentObject;
        return fragment;
    }

    /**
     * OnCreate for the fragment
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /**
     * OnCreateView that inflates and sets up the view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /* Initialize setup */
        final View DisplayFragmentView = inflater.inflate(R.layout.fragment_display,
                container, false);

        /* Setup view */
        setViewItems(DisplayFragmentView);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));
        loadData(currentEvent);

        /* logic setters */
        setOnClickListeners();





        return DisplayFragmentView;
    }



    /**
     * SETUP METHOD:
     * Takes the fragment view, sets each view item
     */
    private void setViewItems(View mView)
    {
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
        inviteButton = (FloatingActionButton) mView.findViewById(R.id.invite);

    }



    /**
     * SETUP METHOD:
     * Sets the onclick listeners for each view item
     */
    public void setOnClickListeners() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        inviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FriendsListFragment friendsListFragment = FriendsListFragment.newInstance();
                fm.beginTransaction().setCustomAnimations(
                        R.anim.bottom_slide_up_fast,
                        R.anim.bottom_slide_down_fast,
                        R.anim.bottom_slide_up_fast,
                        R.anim.bottom_slide_down_fast)
                        .add(R.id.drawer_layout, friendsListFragment).addToBackStack(null).commit();
            }
        });

    }

    /**
     * TODO: Refactor into PARSE implementatoin code
     * loads the data from the parseObject loaded from the EventFeedActivity
     */
    private void loadData(LoggiaEvent event){
                    setContent(
                            event.getEventName(),
                            event.getEventStartDate(),
                            event.getEventEndDate(),
                            event.getEventImageUrl(),
                            event.getEventDescription(),
                            event.getEventLocation()
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



}
