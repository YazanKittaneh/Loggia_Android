package com.loggia.Display;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.loggia.R;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class DisplayActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "cheese_name";
    CollapsingToolbarLayout collapsingToolbar;
    Toolbar toolbar;

    TextView mEventTime;
    TextView mEventDescription;
    TextView mEventLocation;
    TextView mEventDate;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        mEventTime = (TextView) findViewById(R.id.Display_Event_Time);
        mEventDescription = (TextView) findViewById(R.id.Display_Event_Description);
        mEventLocation = (TextView) findViewById(R.id.Display_Event_Location);
        mEventDate = (TextView) findViewById(R.id.Display_Event_Date);
        final Drawable image;


        Intent intent = getIntent();
        String objectID = intent.getStringExtra("objectID");


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        image=null;

        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);


        ParseQuery<ParseObject> query = ParseQuery.getQuery("event");
        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, com.parse.ParseException e) {
                if (e == null) {
                    collapsingToolbar.setTitle(parseObject.getString("Name"));
                    mEventDate.setText(parseObject.getString("Date"));
                    mEventTime.setText(parseObject.getString("Time"));
                    mEventDescription.setText(parseObject.getString("Description"));
                    mEventLocation.setText(parseObject.getString("Location"));


                    ParseFile imgFile = parseObject.getParseFile("Image");
                    byte[] file = new byte[0];
                    if (imgFile != null) {
                        try {
                            file = imgFile.getData();
                        } catch (com.parse.ParseException e1) {
                            e1.printStackTrace();
                        }

                        Bitmap image = BitmapFactory.decodeByteArray(file, 0, file.length);
                        Drawable mDrawable = new BitmapDrawable(getResources(), image);
                        loadBackdrop(mDrawable);
                    }
                } else {
                    Log.i("Information", "This is where information starts:");
                    e.printStackTrace();
                }
            }

        });
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

    }

    private void loadBackdrop(Drawable image) {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        //Glide.with(this).load(image).centerCrop().into(imageView);
        imageView.setImageDrawable(image);
    }


}
