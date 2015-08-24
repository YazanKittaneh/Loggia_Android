package com.loggia.Create;


import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.loggia.Display.DisplayActivity;
import com.loggia.Helpers.CalendarDialog;
import com.loggia.Helpers.StockImageRandomizer;
import com.loggia.R;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.ByteArrayOutputStream;

/**
 * TODO: Add name input
 * TODO: Add date input with dialogs
 *
 */

public class CreateActivity extends AppCompatActivity {


    EditText mEventTime;
    EditText mEventDescription;
    EditText mEventLocation;
    TextView mEventDate;
    CollapsingToolbarLayout collapsingToolbar;
    ImageButton backdrop;
    Toolbar toolbar;

    Bitmap image;
    boolean imgLoaded = false;
    private static int RESULT_LOAD_IMAGE = 1;
    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        final StockImageRandomizer randomStock = new StockImageRandomizer();


        mEventTime = (EditText) findViewById(R.id.Display_Event_Time);
        mEventDescription = (EditText) findViewById(R.id.Display_Event_Description);
        mEventLocation = (EditText) findViewById(R.id.Display_Event_Location);
        mEventDate = (TextView) findViewById(R.id.Display_Event_Date);
        backdrop = (ImageButton) findViewById(R.id.backdrop);


        //image = BitmapFactory.decodeResource(getResources(), randomStock.getRandomStockDrawable());
        //Drawable mDrawable = new BitmapDrawable(getResources(), image);
        //backdrop.setImageDrawable(mDrawable);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        backdrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });



        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        //collapsingToolbar.setTitle("Test Name");


        FloatingActionButton createButton = (FloatingActionButton) findViewById(R.id.accept);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean clear = true;

                TextView[] fields = {mEventTime, mEventLocation, mEventDescription};
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
                    Log.i("Img LOAD: ", "" + imgLoaded);

                    if (!imgLoaded) {
                        image = BitmapFactory.decodeResource(getResources(), randomStock.getRandomStockDrawable());
                        Drawable mDrawable = new BitmapDrawable(getResources(), image);
                        backdrop.setImageDrawable(mDrawable);
                    }

                    ParseObject mParseObject = new ParseObject("event");
                    //mParseObject.put("Name", );
                    mParseObject.put("Time", mEventLocation.getText());
                    mParseObject.put("Description", mEventDescription.getText());
                    mParseObject.put("Location", mEventLocation.getText());
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] data = stream.toByteArray();
                    ParseFile imageFile = new ParseFile("image.", data);
                    mParseObject.put("Image", imageFile);
                    mParseObject.saveInBackground();
                    startActivity(new Intent(context, DisplayActivity.class).putExtra("objectID", mParseObject.getObjectId()));
                    finish();
                }

            }
        });


        mEventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendarDialog();
            }
        });

    }


    private void showCalendarDialog() {
        FragmentManager fm = getSupportFragmentManager();
        CalendarDialog calendarDialog = new CalendarDialog();
        calendarDialog.show(fm, "fragment_calender_dialog");
    }

}
