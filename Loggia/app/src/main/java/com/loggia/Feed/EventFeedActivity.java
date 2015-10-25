package com.loggia.Feed;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


import com.loggia.Create.CreateFragment;
import com.loggia.Display.DisplayFragment;
import com.loggia.Friends.FriendsListFragment;
import com.loggia.R;
import com.dexafree.materialList.cards.BigImageCard;
import com.dexafree.materialList.controller.RecyclerItemClickListener;
import com.dexafree.materialList.model.CardItemView;
import com.dexafree.materialList.view.MaterialListView;
import com.loggia.Utils.Constants;
import com.loggia.Utils.EventDateFormat;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * TODO: Create organizational system for events
 * TODO: Pull events in an efficient manner ( only pull events not in the system)
 */



public class EventFeedActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private MaterialListView mListView;
    private SwipeRefreshLayout swipeLayout;
    private NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerItems;
    private FloatingActionButton create;
    private String[] TAGS;
    public String currentTAG;
    public Context context;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_feed);
        //setupWindowAnimations();

        /**************************
         View Declaration
         *************************/
        mListView = (MaterialListView) findViewById(R.id.material_listview);
        create = (FloatingActionButton) findViewById(R.id.create);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setColorSchemeResources(R.color.ColorPrimary);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerItems = (ListView) findViewById(R.id.NavBar_List);
        TAGS = getResources().getStringArray(R.array.tag_names);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        mListView.getLayoutManager().offsetChildrenVertical(40);



        /**************************
         Setup
         *************************/
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        currentTAG=null;
        context=this;
        if (navigationView != null) {

            setupDrawerContent(navigationView);
        }
        setupListeners();
        updateEvents(currentTAG);
    }

    /*
    @TargetApi(21)
    private void setupWindowAnimations() {
        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setExitTransition(slide);
    }
*/

    /**
     * Set up listeners
     */
    private void setupListeners() {
        /* Listener for the '+' create event button */
        create.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {

                FragmentManager fm = getSupportFragmentManager();
                CreateFragment createFragment = CreateFragment.newInstance(currentTAG);
                fm.beginTransaction().setCustomAnimations(
                        R.anim.bottom_slide_up_fast,
                        R.anim.bottom_slide_down_fast,
                        R.anim.bottom_slide_up_fast,
                        R.anim.bottom_slide_down_fast)
                        .replace(R.id.drawer_layout, createFragment).addToBackStack(null).commit();

            }

        });

        /* Listener for each event card in the listview */
        mListView.addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(CardItemView view, int position) {
                if (view.getTag().toString() != null) {
                    ParseObject currentObject = (ParseObject) view.getTag();
                    FragmentManager fm = getSupportFragmentManager();
                    DisplayFragment displayFragment = DisplayFragment.newInstance(currentObject);
                    fm.beginTransaction().setCustomAnimations(
                            R.anim.bottom_slide_up_fast,
                            R.anim.bottom_slide_down_fast,
                            R.anim.bottom_slide_up_fast,
                            R.anim.bottom_slide_down_fast)
                            .replace(R.id.drawer_layout, displayFragment).addToBackStack(null).commit();
                    /*
                    Intent intent = new Intent(view.getContext(), DisplayActivity.class);
                    intent.putExtra("objectID", objectID);
                    startActivity(intent);
                    */
                }
            }

            @Override
            public void onItemLongClick(CardItemView view, int position) {

            }
        });

        /* Listener for swiping down on the view */
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(true);
                updateEvents(currentTAG);
                swipeLayout.setRefreshing(false);

            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        View v = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (v instanceof EditText) {
            View w = getCurrentFocus();
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + w.getLeft() - scrcoords[0];
            float y = event.getRawY() + w.getTop() - scrcoords[1];

            Log.d("Activity", "Touch event "+event.getRawX()+","+event.getRawY()+" "+x+","+y+" rect "+w.getLeft()+","+w.getTop()+","+w.getRight()+","+w.getBottom()+" coords "+scrcoords[0]+","+scrcoords[1]);
            if (event.getAction() == MotionEvent.ACTION_UP && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom()) ) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }
    /**
     * sets up the view inside the drawer
     * @param navigationView
     *      the navigation drawer that the view will be inserted into
     */
    private void setupDrawerContent(final NavigationView navigationView) {
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, TAGS);
        mDrawerItems.setAdapter(mAdapter);
        //registerForContextMenu(mDrawerItems);
        //navigationView.inflateMenu(mDrawerItems);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        //String clicked = menuItem.toString();
                        currentTAG = menuItem.toString();
                        updateEvents(currentTAG);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

        mDrawerItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentTAG = mDrawerItems.getItemAtPosition(position).toString();
                updateEvents(currentTAG);
                mDrawerLayout.closeDrawers();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(navigationView);
            }
        });
    }



    /**
     * TODO: Refactor into PARSE implementation
     * Update parse events shown to the user.
     * Fired at start of activity and on swipe refresh.
     **/
    private void updateEvents(String eventTag)
    {
        mListView.clear();
        ParseQuery <ParseObject> event_query = new ParseQuery<>(Constants.currentEvents);

        /* will only get events with a date greater than the current date */
        //Log.d("CURRENT DATE: ", currentDay().toString());
        event_query.whereGreaterThanOrEqualTo("EndTime", EventDateFormat.getCurrentDate());
        event_query.addAscendingOrder("StartTime");
        if(eventTag != null && !eventTag.equals("All")){
            Log.d("MENU CLICK: ", eventTag);
            event_query.whereEqualTo("Tag", eventTag);
        }

        //Date tomorrowDate = new Date(todayDate.getTime() + 86400000);
        //event_query.whereLessThan("startTime", tomorrowDate);


        //ParseQuery < ParseObject > query = new ParseQuery<ParseObject>(classID).addAscendingOrder("createdAt");
        //final ProgressDialog dialog = ProgressDialog.show(context, "Loading", "Please wait...", true);
        event_query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> markers, com.parse.ParseException e) {
                if (e == null) {
                    Log.e("DONE ", "DOES WORK");
                    for (int i = 0; i < markers.size(); i++) {
                        Log.e("WITHIN PARSE", "WORKING");
                        ParseObject currentObject = markers.get(i);
                        createCard(
                                currentObject.getString("Name"),
                                EventDateFormat.formatTime(currentObject.getDate("StartTime")),
                                EventDateFormat.formatDate(currentObject.getDate("StartTime")),
                                currentObject.getParseFile("Image").getUrl(),
                                currentObject.getObjectId(),
                                currentObject
                        );
                    }

                } else {
                    Log.e("DONE ERROR", "DOES NOT WORK");
                    Log.e("Error", e.toString());

                }
            }
        });
    }

    private void createCard(String name, String startTime, String date, String imageURL, String objectID, ParseObject currentObject){
        BigImageCard card = new BigImageCard(context);
        card.setTitle(name);
        card.setDescription(date + " at " + startTime);
        card.setDrawable(imageURL);
        card.setTag(currentObject);
        //card.setTag(objectID);
        mListView.add(card);

    }




}
