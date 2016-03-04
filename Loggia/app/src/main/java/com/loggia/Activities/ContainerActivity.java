package com.loggia.Activities;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.loggia.Dialogs.TagDialog;
import com.loggia.Fragments.FeedFragment;
import com.loggia.Interfaces.LoggiaEvent;
import com.loggia.Interfaces.LoggiaUser;
import com.loggia.Model.ParseModels.ParseLoggiaUser;
import com.loggia.R;
import com.loggia.Utils.CategoryMap;
import com.loggia.Utils.Constants;
import com.loggia.Utils.LoggiaUtils;
import com.parse.ParseUser;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * The containing activity to make transition and pages faster
 */
public class ContainerActivity extends AppCompatActivity {

public Context context = this;


    @Bind(R.id.tool_bar) Toolbar toolbar;
    @Bind(R.id.drawer_layout) DrawerLayout drawerLayout;
    LoggiaUser currentUser;
    LoggiaEvent currentEvent;
    CategoryMap eventCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        ButterKnife.bind(this);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        context = this;

        /** initialize backend and service **/
        initializeBackend();
        startupEventFeed();
    }



    /** Initialize the backend for the app **/
    private void initializeBackend(){
        LoggiaUtils.initializeBackendService(Constants.currentBackendDomain, context);
        currentUser = new ParseLoggiaUser(ParseUser.getCurrentUser());

        if(!currentUser.userActive())
            LoggiaUtils.anonymousUserLogIn(Constants.currentBackendDomain);
    }

    /** Starts the event feed fragment **/
    private void startupEventFeed(){
        FragmentManager fm = getSupportFragmentManager();
        FeedFragment feedFragment = FeedFragment.newInstance();
        fm.beginTransaction().setCustomAnimations(
                R.anim.bottom_slide_up_fast,
                R.anim.bottom_slide_down_fast,
                R.anim.bottom_slide_up_fast,
                R.anim.bottom_slide_down_fast)
                .replace(R.id.drawer_layout, feedFragment).addToBackStack(null).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer_view, menu);
        return true;
    }




    /**
     * Handles off screen gestures when in a textview
     * Exists out of the textview and minimizes keyboard when user touches outside of view box
     */
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

            Log.d("Activity", "Touch event " + event.getRawX() + "," + event.getRawY() + " " + x + "," + y + " rect " + w.getLeft() + "," + w.getTop() + "," + w.getRight() + "," + w.getBottom() + " coords " + scrcoords[0] + "," + scrcoords[1]);
            if (event.getAction() == MotionEvent.ACTION_UP && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom()) ) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }


    /**   Navigation Setup
     * sets up the view inside the drawer
     * @param navigationView
     *      the navigation drawer that the view will be inserted into
     *
     */
    /*
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
                        queryEvents();
                       // updateEvents(currentTAG);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

        mDrawerItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentTAG = mDrawerItems.getItemAtPosition(position).toString();
                List<Constants.FilterOptions> filters = new ArrayList<Constants.FilterOptions>();
                    filters.add(Constants.FilterOptions.SGA);
                filters.add(Constants.FilterOptions.PARTY);

                selectEventFilters(filters);
                queryEvents();
                //updateEvents(currentTAG);
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
    */


}
