package com.loggia.Fragments;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.loggia.Helpers.StockImageRandomizer;
import com.loggia.R;
import com.dexafree.materialList.cards.BigImageCard;
import com.dexafree.materialList.controller.RecyclerItemClickListener;
import com.dexafree.materialList.model.CardItemView;
import com.loggia.Model.ParseModels.ParseLoggiaEvent;
import com.dexafree.materialList.view.MaterialListView;
import com.loggia.Utils.Constants;
import com.loggia.Utils.EventDateFormat;
import com.loggia.Utils.TableData;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class FeedFragment extends Fragment {

    /**************************
     View Declaration
     *************************/


    private String[] TAGS;
    public String currentTAG;
    public Context context;
    public Map<Constants.FilterOptions,Boolean> filterOptionsMap;
    private  MaterialListView mListView;
    private FloatingActionButton create;
    private SwipeRefreshLayout swipeLayout;




    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FeedFragment() {
    }


    /**
     * Constructor created by the newInstance and takes in the Item ID
     */
    public static FeedFragment newInstance() {
        FeedFragment fragment = new FeedFragment();
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
        final View eventFeedView = inflater.inflate(R.layout.fragment_feed,
                container, false);

        mListView = (MaterialListView) eventFeedView.findViewById(R.id.material_listview);
        create = (FloatingActionButton) eventFeedView.findViewById(R.id.create);
        swipeLayout = (SwipeRefreshLayout) eventFeedView.findViewById(R.id.swipe_container);
        swipeLayout.setColorSchemeResources(R.color.ColorPrimary);
        TAGS = getResources().getStringArray(R.array.tag_names);

        /** Navigation declaration **/
        //navigationView = (NavigationView) findViewById(R.id.nav_view);



        /**************************
         Setup
         *************************/

        /** Navigation setup **/
        //toolbar.setNavigationIcon(R.drawable.ic_menu);
        //setSupportActionBar(toolbar);

        currentTAG=null;
        setupListeners();

        initializeFilterMap();
        selectAllEventFilters();
        queryEvents();

        return eventFeedView;
    }



    /**
     * Set up listeners
     */
    private void setupListeners() {

        /** Listener for CreateFragment **/
        create.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                CreateFragment createFragment = CreateFragment.newInstance(currentTAG);
                fm.beginTransaction().setCustomAnimations(
                        R.anim.bottom_slide_up_fast,
                        R.anim.bottom_slide_down_fast,
                        R.anim.bottom_slide_up_fast,
                        R.anim.bottom_slide_down_fast)
                        .replace(R.id.full_screen, createFragment).addToBackStack(null).commit();

            }

        });

        /** Listener for DisplayFragment **/
        mListView.addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(CardItemView view, int position) {
                //if (view.getTag().toString() != null) {
                    ParseObject currentObject = (ParseObject) view.getTag();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    DisplayFragment displayFragment = DisplayFragment.newInstance(currentObject);
                    fm.beginTransaction().setCustomAnimations(
                            R.anim.bottom_slide_up_fast,
                            R.anim.bottom_slide_down_fast,
                            R.anim.bottom_slide_up_fast,
                            R.anim.bottom_slide_down_fast)
                            .replace(R.id.full_screen, displayFragment).addToBackStack(null).commit();
                //}
            }

            @Override
            public void onItemLongClick(CardItemView view, int position) {

            }
        });

        /** Listener for SwipeRefresh **/
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(true);
                queryEvents();
                swipeLayout.setRefreshing(false);
            }
        });
    }



    /**
     *  TODO: Have the ListView only clear once cards are created
     *  Updates the events according to the filters specified in filterOptionsMap
     */
    private void queryEvents(){
        mListView.clear(); //clears the cards
        ParseQuery<ParseLoggiaEvent> event_query = new ParseQuery(TableData.TableNames.EVENT.toString());
        event_query.whereGreaterThanOrEqualTo(TableData.EventColumnNames.event_end_date.toString(),
                EventDateFormat.getCurrentDate());

        //TODO: Put in filtering functianlity
        // Additional queries depending on the tag that was chosen.
        //for(Map.Entry<Constants.FilterOptions,Boolean> entry : filterOptionsMap.entrySet()){
        //    if(entry.getValue()){
        //        event_query.whereEqualTo(TableData.EventColumnNames.event_tag.toString(),
        //                entry.getKey().toString());
        //    }
        //}
        event_query.addAscendingOrder(TableData.EventColumnNames.event_start_date.toString());
        Log.e("Before ERROR", "Could possibly work");
        event_query.findInBackground(new FindCallback<ParseLoggiaEvent>() {
            @Override
            public void done(List<ParseLoggiaEvent> events, com.parse.ParseException e) {
                if (e == null) {
                    Log.e("DONE AND IT WORKS", "DOES WORK");

                    for (ParseLoggiaEvent event : events) {
                        createCard(event.getEventName(),
                                EventDateFormat.formatTime(event.getEventStartDate()),
                                EventDateFormat.formatDate((event.getEventStartDate())),
                                event.getEventImageUrl());
                    }
                } else {
                    //TODO : SEND MESSAGE TO THE UI FOR A RESPONSE
                    //Log.e("DONE ERROR", "DOES NOT WORK");
                    Log.e("DONE ERROR", "DOES NOT WORK");
                    Log.e("Error", e.toString());
                }
            }
        });
        Log.e("DONE ERROR", "DOES NOT WORK");


    }



    private void createCard(String name, String startTime, String date, String imageURL){

        BigImageCard card = new BigImageCard(context);
        card.setTitle(name);
        card.setDescription(date + " at " + startTime);
        card.setDrawable(imageURL);
        card.setTag(objectID);
        mListView.add(card);

    }

    /**
     * Initialises the map structure for event filter Options
     */
    private  void initializeFilterMap(){
        this.filterOptionsMap = new HashMap();
        this.filterOptionsMap.put(Constants.FilterOptions.CONCERTS, false);
        this.filterOptionsMap.put(Constants.FilterOptions.PARTY, false);
        this.filterOptionsMap.put(Constants.FilterOptions.STUDY_SESSION, false);
    }

    /**
     * Selects all the filters for events
     */
    private void selectAllEventFilters(){
        List<Constants.FilterOptions> filterOptions = new ArrayList();
        filterOptions.add(Constants.FilterOptions.CONCERTS);
        filterOptions.add(Constants.FilterOptions.PARTY);
        filterOptions.add(Constants.FilterOptions.SGA);
        filterOptions.add(Constants.FilterOptions.STUDY_SESSION);
        selectEventFilters(filterOptions);
    }

    /**
     * selects the filters for events as supplied by the options in filters
     * @param filters the options to query the events with
     */
    private void selectEventFilters(List<Constants.FilterOptions> filters){
        for(Constants.FilterOptions option : filters)
            this.filterOptionsMap.put(option,true);
    }

}
