package com.loggia.Fragments;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.loggia.Interfaces.LoggiaEvent;
import com.loggia.R;
import com.dexafree.materialList.cards.BigImageCard;
import com.dexafree.materialList.controller.RecyclerItemClickListener;
import com.dexafree.materialList.model.CardItemView;
import com.loggia.Model.ParseModels.ParseLoggiaEvent;
import com.dexafree.materialList.view.MaterialListView;
import com.loggia.Utils.BackendDomain;
import com.loggia.Utils.Constants;
import com.loggia.Utils.EventDateFormat;
import com.loggia.Utils.LoggiaUtils;
import com.loggia.Utils.TableData;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;
import java.util.Map;


/**
 * The Feed, considered the "homepage". Show each object in the database for the user and
 * branch out to all different fragements
 */

public class FeedFragment extends Fragment {

    /** Declarations **/
    private String[] TAGS;
    public String currentTAG;
    public Context context;
    public Map<Constants.FilterOptions,Boolean> filterOptionsMap;
    private  MaterialListView mListView;
    private FloatingActionButton create;
    private SwipeRefreshLayout swipeLayout;
    final BackendDomain backendDomain = Constants.currentBackendDomain;

    /** Mandatory empty constructor for the fragment manager to instantiate the
     *  fragment (e.g. upon screen orientation changes). **/
    public FeedFragment() {
    }

    /** Constructor created by the newInstance and takes in the Item ID **/
    public static FeedFragment newInstance() {
        FeedFragment fragment = new FeedFragment();
        return fragment;
    }

    /** OnCreate for the fragment **/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //backendDomain = (BackendDomain) this.getArguments().getSerializable("BACKEND_DOMAIN");
    }

    /**
     * OnCreateView that inflates and sets up the view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        final View eventFeedView = inflater.inflate(R.layout.fragment_feed,container, false);
        /** Declarations **/
        mListView = (MaterialListView) eventFeedView.findViewById(R.id.material_listview);
        create = (FloatingActionButton) eventFeedView.findViewById(R.id.create);
        swipeLayout = (SwipeRefreshLayout) eventFeedView.findViewById(R.id.swipe_container);
        swipeLayout.setColorSchemeResources(R.color.ColorPrimary);
        TAGS = getResources().getStringArray(R.array.tag_names);
        currentTAG=null;
        setupListeners();
        LoggiaUtils.queryAndPopulateEvents(backendDomain, this.context, this.getView(), mListView);
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
                LoggiaEvent currentObject = (LoggiaEvent) view.getTag();
                //TODO: Make LoggiaEvent an instance variable
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DisplayFragment displayFragment = DisplayFragment.newInstance(currentObject);
                fm.beginTransaction().setCustomAnimations(
                        R.anim.bottom_slide_up_fast,
                        R.anim.bottom_slide_down_fast,
                        R.anim.bottom_slide_up_fast,
                        R.anim.bottom_slide_down_fast)
                        .replace(R.id.full_screen, displayFragment).addToBackStack(null).commit();

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
                LoggiaUtils.queryAndPopulateEvents(backendDomain, context, getView(), mListView);
                swipeLayout.setRefreshing(false);
            }
        });
    }

    private void createCard(String name, String startTime, String date, String imageURL, LoggiaEvent event){
        BigImageCard card = new BigImageCard(context);
        card.setTitle(name);
        card.setDescription(date + " at " + startTime);
        card.setDrawable(imageURL);
        card.setTag(event);
        mListView.add(card);
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
