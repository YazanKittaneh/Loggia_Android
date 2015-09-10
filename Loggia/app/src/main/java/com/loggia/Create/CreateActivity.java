package com.loggia.Create;


import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.loggia.Display.DisplayActivity;
import com.loggia.Helpers.CalendarDialog;
import com.loggia.Helpers.EndClockDialog;
import com.loggia.Helpers.ImageScaler;
import com.loggia.Helpers.StartClockDialog;
import com.loggia.Helpers.StockImageRandomizer;
import com.loggia.R;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * TODO: Add name input
 * TODO: Create universal Clock Dialog and make callback efficient
 *
 */

public class CreateActivity extends AppCompatActivity {

    EditText mEventName;
    TextView mEventStartTime;
    TextView mEventEndTime;
    EditText mEventDescription;
    EditText mEventLocation;
    TextView mEventDate;
    CollapsingToolbarLayout collapsingToolbar;
    ImageButton backdrop;
    FloatingActionButton createButton;
    Toolbar toolbar;
    private int PICK_IMAGE_REQUEST = 1;

    Bitmap image;
    boolean imgLoaded = false;
    private static int RESULT_LOAD_IMAGE = 1;
    Context context = this;
    StockImageRandomizer randomStock;
    ImageScaler scaler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        randomStock = new StockImageRandomizer();
        scaler = new ImageScaler(this);


        mEventName = (EditText) findViewById(R.id.Display_Event_Name);
        mEventDescription = (EditText) findViewById(R.id.Display_Event_Description);
        mEventLocation = (EditText) findViewById(R.id.Display_Event_Location);
        mEventDate = (TextView) findViewById(R.id.Display_Event_Date);
        mEventStartTime = (TextView) findViewById(R.id.Display_Start_Time);
        mEventEndTime = (TextView) findViewById(R.id.Display_End_Time);
        backdrop = (ImageButton) findViewById(R.id.backdrop);
        createButton = (FloatingActionButton) findViewById(R.id.accept);
        collapsingToolbar =(CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //image = BitmapFactory.decodeResource(getResources(), randomStock.getRandomStockDrawable());
        //Drawable mDrawable = new BitmapDrawable(getResources(), image);
        //backdrop.setImageDrawable(mDrawable);


        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));



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

        mEventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendarDialog();

            }
        });

        mEventStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartClockDialog();
            }
        });

        mEventEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEndClockDialog();
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


    private  void pushEvent(){
        boolean clear = true;

        TextView[] fields = {mEventName, mEventStartTime, mEventEndTime, mEventLocation, mEventDate, mEventDescription};



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

            ParseObject mParseObject = new ParseObject("Test");
            mParseObject.put("Name", mEventName.getText().toString());
            mParseObject.put("Date", mEventDate.getText().toString());
            mParseObject.put("StartTime", mEventStartTime.getText().toString());
            mParseObject.put("EndTime", mEventEndTime.getText().toString());
            mParseObject.put("Location", mEventLocation.getText().toString());
            mParseObject.put("Description", mEventDescription.getText().toString());

            byte[] data = scaler.compressForUpload(image);
            ParseFile imageFile = new ParseFile("Image.jpg", data);

            mParseObject.put("Image", imageFile);
            mParseObject.saveInBackground();
            //startActivity(new Intent(context, DisplayActivity.class).putExtra("objectID", mParseObject.getObjectId()));
            finish();
        }
    }

    private void showCalendarDialog() {
        FragmentManager fm = getSupportFragmentManager();
        CalendarDialog calendarDialog = new CalendarDialog();
        calendarDialog.show(fm, "fragment_calender_dialog");
    }

    private void showStartClockDialog() {
        FragmentManager fm = getSupportFragmentManager();
        StartClockDialog clockDialog = new StartClockDialog();
        clockDialog.show(fm, "fragment_calender_dialog");
    }

    private void showEndClockDialog() {
        FragmentManager fm = getSupportFragmentManager();
        EndClockDialog clockDialog = new EndClockDialog();
        clockDialog.show(fm, "fragment_calender_dialog");
    }


    private void showPickerDialog(){
        CharSequence colors[] = new CharSequence[] {"Music", "Parties", "Sports", "Student Made", "Studying", "Talks", "Theater"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("What type of event ");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}
