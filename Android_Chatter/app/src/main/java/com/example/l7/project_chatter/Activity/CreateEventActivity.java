package com.example.l7.project_chatter.Activity;

import android.graphics.drawable.Drawable;
import android.inputmethodservice.ExtractEditText;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.l7.project_chatter.Controllers.EventObject;
import com.example.l7.project_chatter.R;


public class CreateEventActivity extends ActionBarActivity {

    EventObject mEventConstructor;
    TextView mEventName;
    TextView mEventTime;
    TextView mEventHost;
    TextView mEventLocation;
    TextView mEventDescription;
    Button mEventUploadImage;
    Button mCreateButton;

    Drawable image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Create an Event");
        getSupportActionBar().setLogo(null);

        //Declarations
        mEventName = (TextView) findViewById(R.id.Create_Event_Name);
        mEventTime = (TextView) findViewById(R.id.Create_Event_Time);
        mEventHost = (TextView) findViewById(R.id.Create_Event_Host);
        mEventLocation = (TextView) findViewById(R.id.Create_Event_Location);
        mEventDescription = (TextView) findViewById(R.id.Create_Event_Description);
        mCreateButton = (Button) findViewById(R.id.Create_Event_Create_Button);
        mEventUploadImage = (Button) findViewById(R.id.Create_Event_Upload_Image_Button);


        //Handles the file uploading
        mEventUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Take you to a file manager to uplaod image, should set image = to something
            }
        });
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEventConstructor = new EventObject();

                //Checks if any fields are emtpy
                if (mEventName.getText().toString().isEmpty() ||
                        mEventTime.getText().toString().isEmpty() ||
                        mEventHost.getText().toString().isEmpty() ||
                        mEventLocation.getText().toString().isEmpty() ||
                        mEventDescription.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "One or more fields are empty!", Toast.LENGTH_SHORT);
                } else {
                    //Makes event
                    mEventConstructor.putEventInfo(mEventName.getText().toString(),
                            mEventTime.getText().toString(),
                            mEventHost.getText().toString(),
                            mEventLocation.getText().toString(),
                            mEventDescription.getText().toString(),
                            image);
                    mEventConstructor.pushEventToCloud();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
