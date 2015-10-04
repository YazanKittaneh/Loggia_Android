package com.loggia.Create;


import android.net.Uri;
import android.provider.MediaStore;
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
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.loggia.Helpers.CalendarDialog;
import com.loggia.Helpers.ImageScalar;
import com.loggia.Helpers.ClockDialog;
import com.loggia.Helpers.StockImageRandomizer;
import com.loggia.Helpers.TagDialog;
import com.loggia.R;
import com.loggia.Utils.Constants;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * TODO: Add name input
 * TODO: Create universal Clock Dialog and make callback efficient
 *
 */

public class CreateActivity extends AppCompatActivity {

    EditText createEventName;

    TextView createEventStartTime;
    TextView createEventEndTime;
    TextView createEventStartDate;
    TextView createEventEndDate;

    TextView createEventTag;
    EditText createEventDescription;
    EditText createEventLocation;

    CollapsingToolbarLayout collapsingToolbar;
    ImageButton backdrop;
    FloatingActionButton createButton;
    Toolbar toolbar;
    private int PICK_IMAGE_REQUEST = 1;
    public Date calendarDate;

    public Calendar startDate;
    public Calendar endDate;


    Bitmap image;
    boolean imgLoaded = false;
    Context context = this;
    StockImageRandomizer randomStock;
    ImageScalar scaler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        randomStock = new StockImageRandomizer();
        scaler = new ImageScalar(this);
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
        Intent intent = getIntent();



        createEventName = (EditText) findViewById(R.id.Create_Event_Name);
        createEventDescription = (EditText) findViewById(R.id.Create_Event_Description);
        createEventLocation = (EditText) findViewById(R.id.Create_Event_Location);

        createEventStartDate = (TextView) findViewById(R.id.Create_Start_Date);
        createEventStartTime = (TextView) findViewById(R.id.Create_Start_Time);
        createEventEndDate = (TextView) findViewById(R.id.Create_End_Date);
        createEventEndTime = (TextView) findViewById(R.id.Create_End_Time);

        createEventTag = (TextView) findViewById(R.id.Create_Tag);

        backdrop = (ImageButton) findViewById(R.id.backdrop);
        createButton = (FloatingActionButton) findViewById(R.id.accept);
        collapsingToolbar =(CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));

        String Tag = intent.getStringExtra("Tag");
        if(Tag == null || !Tag.equals("All"))
        {
            createEventTag.setText(Tag);
        }



        backdrop.setImageDrawable(scaler.decodeSampledBitmapFromDrabwable(getResources(), R.drawable.upload));


        backdrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                // Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });


        //collapsingToolbar.setTitle("Test Name");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushEvent();
            }
        });

        /*
        mEventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendarDialog();

            }
        });
        */

        createEventStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendarDialog(false);

            }
        });

        createEventEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendarDialog(true);

            }
        });
        createEventStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClockDialog(false);
            }
        });

        createEventEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClockDialog(true);
            }
        });

        createEventTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickerDialog();
            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageButton backdrop = (ImageButton) findViewById(R.id.backdrop);
                image=bitmap;
                backdrop.setImageBitmap(bitmap);
                imgLoaded=true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private  void pushEvent()
    {
        boolean clear = true;

        TextView[] fields = {createEventName, createEventTag, createEventStartTime, createEventEndTime, createEventStartDate, createEventEndDate, createEventLocation, createEventDescription};

        for (int i = 0; i < fields.length; i++) {
            String mTextField = fields[i].getText().toString();
            if (TextUtils.isEmpty(mTextField)) {
                fields[i].setError("Field is empty!");
                clear = false;
            }
        }

        if (clear) {
            Log.i("Img LOAD: ", "" + imgLoaded);

            if (!imgLoaded) {
                image = BitmapFactory.decodeResource(getResources(), randomStock.getRandomStockDrawable());
                Drawable mDrawable = new BitmapDrawable(getResources(), image);
                backdrop.setImageDrawable(mDrawable);
            }

            ParseObject mParseObject = new ParseObject(Constants.currentEvents);
            mParseObject.put("Name", createEventName.getText().toString());
            mParseObject.put("StartTime", startDate.getTime());
            mParseObject.put("EndTime", endDate.getTime());
            mParseObject.put("Location", createEventLocation.getText().toString());
            mParseObject.put("Description", createEventDescription.getText().toString());
            mParseObject.put("Tag", createEventTag.getText());
            mParseObject.put("Owner", ParseUser.getCurrentUser());

            byte[] data = scaler.compressForUpload(image);
            ParseFile imageFile = new ParseFile("Image.jpg", data);

            mParseObject.put("Image", imageFile);

            mParseObject.saveInBackground();
            //startActivity(new Intent(context, DisplayActivity.class).putExtra("objectID", mParseObject.getObjectId()));
            finish();
        }
    }


    private void showClockDialog(boolean TYPE){
        FragmentManager fm = getSupportFragmentManager();
        ClockDialog clockDialog = new ClockDialog();
        clockDialog.isEndTime = TYPE;
        clockDialog.show(fm, "fragment_calender_dialog");
    }

    private void showCalendarDialog(boolean TYPE) {
        FragmentManager fm = getSupportFragmentManager();
        CalendarDialog calendarDialog = new CalendarDialog();
        calendarDialog.isEndTime = TYPE;
        calendarDialog.show(fm, "fragment_calender_dialog");
    }


    private void showPickerDialog(){

        FragmentManager fm = getSupportFragmentManager();
        TagDialog tagDialog = new TagDialog();
        tagDialog.show(fm, "tag_dialog");
    }


}
