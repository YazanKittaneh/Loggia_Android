package com.example.l7.project_chatter.HomePage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.l7.project_chatter.CreateEvent.CreateEventActivity;
import com.example.l7.project_chatter.DisplayEvent.DisplayEventActivity;
import com.example.l7.project_chatter.Feed.EventFeedActivity;
import com.example.l7.project_chatter.FriendsList.FriendsListActivity;
import com.example.l7.project_chatter.FriendsList.SendEventActivity;
import com.example.l7.project_chatter.R;


/**
 * Activity that will be displayed after login
 *
 * This is where the events will all reside, from here you can get to any
 * other feature
 */

public class HomepageActivity extends AppCompatActivity {

    Button mCreateEventButton;
    Button mViewEventButton;
    Button mSendEventButton;
    Button mEventListButton;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);


        mCreateEventButton = (Button) findViewById(R.id.Homepage_Create_Event_Button);
        mSendEventButton = (Button) findViewById(R.id.Homepage_Send_Event_Button);
        mViewEventButton = (Button) findViewById(R.id.Homepage_View_Event_Button);
        mEventListButton = (Button) findViewById(R.id.Homepage_List_Button);




        mCreateEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;

                Intent intent = new Intent(v.getContext(), FriendsListActivity.class);
                startActivity(intent);
            }
        });

        mViewEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;

                Intent intent = new Intent(v.getContext(), DisplayEventActivity.class);
                startActivity(intent);
            }
        });

        mSendEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;

                Intent intent = new Intent(v.getContext(), SendEventActivity.class);
                startActivity(intent);
            }
        });

        mEventListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;

                Intent intent = new Intent(v.getContext(), EventFeedActivity.class);
                startActivity(intent);
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_homepage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        Intent intent = new Intent(this.getApplicationContext(), CreateEventActivity.class);
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }


}
