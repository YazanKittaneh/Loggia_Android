package com.example.l7.project_chatter.CreateEvent;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.l7.project_chatter.DisplayEvent.DisplayEventActivity;
import com.example.l7.project_chatter.Feed.EventFeedActivity;
import com.example.l7.project_chatter.R;


public class CreateEventActivity extends AppCompatActivity {

    EventObject mEventConstructor;
    TextView mEventName;
    TextView mEventTime;
    TextView mEventHost;
    TextView mEventLocation;
    TextView mEventDescription;
    Button mEventUploadImage;
    Button mCreateButton;
    ImageView mEventImageView;


    Bitmap image;

    boolean imgLoaded = false;

    private static int RESULT_LOAD_IMAGE = 1;

    Context context = this;

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
        mEventImageView = (ImageView) findViewById(R.id.imgView);


        //Handles the file uploading
        mEventUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });



        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEventConstructor = new EventObject();

                boolean clear = true;

                TextView[] fields = {mEventName, mEventTime, mEventHost, mEventLocation, mEventDescription};
                String[] content = null;

                for (int i = 0; i < fields.length; i++) {
                    String mTextField = fields[i].getText().toString();
                    if (TextUtils.isEmpty(mTextField)) {
                        fields[i].setError("Field is empty!");
                        clear = false;
                        return;
                    }
                }


                if (clear) {
                    Log.i("Img LOAD: ",  ""+imgLoaded);

                    if (!imgLoaded)
                    {
                        image = BitmapFactory.decodeResource(getResources(), R.drawable.concert);
                        Log.i("PLACEMENT IMG PUT: ",  "True");

                    }

                    mEventConstructor.putEventInfo(mEventName.getText().toString(),
                            mEventTime.getText().toString(),
                            mEventHost.getText().toString(),
                            mEventLocation.getText().toString(),
                            mEventDescription.getText().toString(),
                            image);
                    mEventConstructor.pushEventToCloud();
                    startActivity(new Intent(CreateEventActivity.this, EventFeedActivity.class));
                    finish();
                }

            }
        });


    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            image = BitmapFactory.decodeFile(picturePath);
            mEventImageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            imgLoaded=true;
        }
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
