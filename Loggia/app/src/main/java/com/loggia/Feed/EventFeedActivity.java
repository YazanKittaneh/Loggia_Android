package com.loggia.Feed;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.loggia.Admin.AdminActivity;
import com.loggia.Create.CreateActivity;
import com.loggia.Display.DisplayActivity;
import com.loggia.R;
import com.dexafree.materialList.cards.BigImageCard;
import com.dexafree.materialList.controller.RecyclerItemClickListener;
import com.dexafree.materialList.model.CardItemView;
import com.dexafree.materialList.view.MaterialListView;
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
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerItems;
    private FloatingActionButton create;
    private String[] TAGS;

    public Context context;
    String classID ="Test";





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_feed);

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
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        setSupportActionBar(toolbar);
        mListView.getLayoutManager().offsetChildrenVertical(10);

        /**************************
         Setup
         *************************/
        context=this;
        updateEvents(classID);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        /**************************
         Listeners
         *************************/
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CreateActivity.class);
                startActivity(intent);
            }
        });

        mListView.addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(CardItemView view, int position) {
                if (view.getTag().toString() != null) {
                    String objectID = view.getTag().toString();
                    Log.d("CLICK TEST: ", objectID);

                    Intent intent = new Intent(view.getContext(), DisplayActivity.class);
                    intent.putExtra("objectID", objectID);
                    intent.putExtra("classID", classID);
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(CardItemView view, int position) {

            }
        });

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

                    @Override
                    public void onRefresh() {
                        swipeLayout.setRefreshing(true);
                        updateEvents(classID);
                        swipeLayout.setRefreshing(false);

                    }
        });

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
                        onDrawerClick(menuItem.toString());
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

        mDrawerItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // menuItem.setChecked(true);
                //String clicked = menuItem.toString();
               // onDrawerClick(menuItem.toString());
               // mDrawerLayout.closeDrawers();
            }
        });
    }

    /**
     * Handles drawer item clicks
     * @param menuID
     *      ID of the item clicked
     */
    private void onDrawerClick(String menuID) {
        if (menuID.equals("Admin")) {
            Intent intent = new Intent(context, AdminActivity.class);
            startActivity(intent);
        } else {
            updateEvents(menuID);
        }
    }

    /**
     * Handles updating of parse events shown to the user.
     * Fired at start of activity and on swipe refresh.
     * @param classID
     *       ID for the class specific events
     */
    private void updateEvents(String classID)
    {
        this.classID =classID;
        mListView.clear();

        ParseQuery < ParseObject > query = new ParseQuery<ParseObject>(classID).addAscendingOrder("createdAt");
        //final ProgressDialog dialog = ProgressDialog.show(context, "Loading", "Please wait...", true);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> markers, com.parse.ParseException e) {
                if (e == null) {
                    for (int i = 0; i < markers.size(); i++) {
                        ParseObject currentObject = markers.get(i);

                        final BigImageCard card = new BigImageCard(context);
                        Log.i(i + " Item: ", currentObject.getString("Name"));
                        card.setTitle(currentObject.getString("Name"));
                        card.setDescription(currentObject.getString("Date") + " at " + currentObject.getString("StartTime"));
                        card.setDrawable(currentObject.getParseFile("Image").getUrl());
                        card.setTag(currentObject.getObjectId());
                        mListView.add(card);
                    }
                } else {
                    Log.e("DONE ERROR", "DOES NOT WORK");
                }
            }
        });
    }




}
