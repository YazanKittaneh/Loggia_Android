package com.loggia.Create;


import android.app.Activity;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

public class CreateActivity extends android.support.v4.app.Fragment {

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
    CreateActivity context = this;
    StockImageRandomizer randomStock;
    ImageScalar scaler;
    static String currentTag;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CreateActivity() {
    }


    /**
     * Constructor created by the newInstance and takes in the Item ID
     */
    public static CreateActivity newInstance(String tag) {
        CreateActivity fragment = new CreateActivity();
        currentTag = tag;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mView = inflater.inflate(R.layout.activity_create,container, false);

        randomStock = new StockImageRandomizer();
        scaler = new ImageScalar(context.getActivity());
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();



        createEventName = (EditText) mView.findViewById(R.id.Create_Event_Name);
        createEventDescription = (EditText) mView.findViewById(R.id.Create_Event_Description);
        createEventLocation = (EditText) mView.findViewById(R.id.Create_Event_Location);
        createEventStartDate = (TextView) mView.findViewById(R.id.Create_Start_Date);
        createEventStartTime = (TextView) mView.findViewById(R.id.Create_Start_Time);
        createEventEndDate = (TextView) mView.findViewById(R.id.Create_End_Date);
        createEventEndTime = (TextView) mView.findViewById(R.id.Create_End_Time);
        createEventTag = (TextView) mView.findViewById(R.id.Create_Tag);
        backdrop = (ImageButton) mView.findViewById(R.id.backdrop);
        createButton = (FloatingActionButton) mView.findViewById(R.id.accept);
        collapsingToolbar =(CollapsingToolbarLayout) mView.findViewById(R.id.collapsing_toolbar);
        toolbar = (Toolbar) mView.findViewById(R.id.toolbar);

        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //setSupportActionBar(toolbar);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));

        if(currentTag == null || !currentTag.equals("All"))
        {
            createEventTag.setText(currentTag);
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
                getActivity().getSupportFragmentManager().popBackStack();
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
                newInstance(false);

            }
        });
/*
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

*/
        return mView;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == context.getActivity().RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getActivity().getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageButton backdrop = (ImageButton) context.getActivity().findViewById(R.id.backdrop);
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
            getActivity().getSupportFragmentManager().popBackStack();

        }
    }

/*
    private void showClockDialog(boolean TYPE){
        FragmentManager fm = context.getActivity().getSupportFragmentManager();
        ClockDialog clockDialog = new ClockDialog();
        clockDialog.isEndTime = TYPE;
        clockDialog.show(fm, "fragment_clock_dialog");
    }
    */

    public static ClockDialog newInstance(boolean TYPE) {
        ClockDialog clockDialog = new ClockDialog();
        clockDialog.isEndTime = TYPE;
        return clockDialog;
    }
  /*
    private void showCalendarDialog(boolean TYPE) {
        FragmentManager fm = context.getActivity().getSupportFragmentManager();
        CalendarDialog calendarDialog = new CalendarDialog();
        calendarDialog.isEndTime = TYPE;
        calendarDialog.show(fm, "fragment_calender_dialog");
    }
    */


    private void showPickerDialog(){

        FragmentManager fm = context.getActivity().getSupportFragmentManager();
        TagDialog tagDialog = new TagDialog();
        tagDialog.show(fm, "tag_dialog");
    }


}
