package com.loggia.Fragments;


import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.loggia.Dialogs.CalendarDialog;
import com.loggia.Interfaces.LoggiaEvent;
import com.loggia.Model.ParseModels.ParseLoggiaEvent;
import com.loggia.Utils.ImageCompressor;
import com.loggia.Dialogs.ClockDialog;
import com.loggia.Utils.StockImageRandomizer;
import com.loggia.Dialogs.TagDialog;
import com.loggia.Interfaces.LoggiaUser;
import com.loggia.R;
import com.loggia.Utils.CategoryMap;
import com.loggia.Utils.Constants;
import com.loggia.Utils.LoggiaUtils;
import com.loggia.Utils.TestUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * TODO: Add user contact info
 */

public class CreateFragment extends Fragment implements TagDialog.DialogListener {

    /** Declarations **/
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
    List<Integer> eventRepIds;
    Map<Integer, CharSequence> categoryMap;

    /** Global Variables **/
    private int PICK_IMAGE_REQUEST = 1;
    public static Date calendarDate;  //TODO: Create private variables and pass them as arguments
    public static Calendar startDate;
    public static Calendar endDate;
    protected static String currentTag;
    protected Bitmap image;
    protected boolean imgLoaded = false;
    private CreateFragment context = this;
    protected  StockImageRandomizer randomStock;
    protected ImageCompressor scaler;
    protected LoggiaUser currentUser;
    protected CategoryMap eventCategory;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CreateFragment() {
        eventRepIds = new ArrayList<>();
        this.categoryMap = LoggiaUtils.getCategories();
    }

    /**
     * Constructor created by the newInstance and takes in the Item ID
     */
    public static CreateFragment newInstance(String tag) {
        CreateFragment fragment = new CreateFragment();
        currentTag = tag;  //Sets the currentTag to the tag passed in


        /* Setting current times */
        calendarDate = Calendar.getInstance().getTime();
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();

        return fragment;
    }

    /**
     * OnCreate for the fragment
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * OnCreateView, handles setup and returns the fragment view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        /* Initialize setup */
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        /* Setup view */
        View mView = inflater.inflate(R.layout.fragment_create,container, false);
        setViewItems(mView);

        toolbar.setNavigationIcon(ContextCompat.getDrawable(context.getActivity(), R.drawable.abc_ic_ab_back_mtrl_am_alpha));
        backdrop.setImageDrawable(ContextCompat.getDrawable(context.getActivity(), R.drawable.upload));
        collapsingToolbar.setTitle("Upload Image");

        /* Setup objects */
        randomStock = new StockImageRandomizer();
        scaler = new ImageCompressor(context.getActivity());

        /* logic setters */
        //setTag(currentTag); TODO: Reimplement
        setOnClickListeners();

        return mView;
    }


    /**
     * SETUP METHOD:
     * Takes the fragment view, sets each view item
     */
    private void setViewItems(View mView)
    {
        createEventName = (EditText) mView.findViewById(R.id.Create_Event_Name);
        createEventDescription = (EditText) mView.findViewById(R.id.Create_Event_Description);
        createEventLocation = (EditText) mView.findViewById(R.id.Create_Event_Location);
        createEventStartDate = (TextView) mView.findViewById(R.id.Create_Start_Date);
        createEventStartTime = (TextView) mView.findViewById(R.id.Create_Start_Time);
        createEventEndDate = (TextView) mView.findViewById(R.id.Create_End_Date);
        createEventEndTime = (TextView) mView.findViewById(R.id.Create_End_Time);
        createEventTag = (TextView) mView.findViewById(R.id.Create_Tag);
        createButton = (FloatingActionButton) mView.findViewById(R.id.accept);
        collapsingToolbar =(CollapsingToolbarLayout) mView.findViewById(R.id.collapsing_toolbar);
        toolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        backdrop = (ImageButton) mView.findViewById(R.id.backdrop);
    }


    /**
     * SETUP METHOD:
     * Sets the onclick listeners for each view item
     */
    public void setOnClickListeners(){
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

        /**
         * TODO:
         * */
        createEventTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTagDialog();
            }
        });

    }


    /**
     * Handles the result from the imagepicker intent.
     * If the image is sucessfully loaded => load in backdrop
     * otherwise it will crash
     * TODO: Handle the catch exception
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getActivity().getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageButton backdrop = (ImageButton) context.getActivity().findViewById(R.id.backdrop);
                collapsingToolbar.setTitle("");
                image=bitmap;
                backdrop.setImageBitmap(bitmap);
                imgLoaded=true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * Checks view items to see if they are empty
     * Display a UI error if there are emtpy fields
     */
    private boolean filledViewItems(){
        TextView[] fields = {createEventName,
                createEventStartTime,
                createEventEndTime,
                createEventStartDate,
                createEventEndDate,
                createEventLocation,
                //TODO: reimplement createEventTag
                createEventDescription};
        boolean clear = true;

        for (int i = 0; i < fields.length; i++) {
            String mTextField = fields[i].getText().toString();
            if (TextUtils.isEmpty(mTextField)) {
                fields[i].setError("Field is empty!");
                clear = false;
            }
        }
        if (!imgLoaded) {
            image = BitmapFactory.decodeResource(getResources(), randomStock.getRandomStockDrawable());
            Drawable mDrawable = new BitmapDrawable(getResources(), image);
            backdrop.setImageDrawable(mDrawable);
        }

        return clear;
    }



    /**
     * Pushes the event to parse
     */

    private  void pushEvent()
    {
        if(filledViewItems()) {
            byte [] imageBytes = ImageCompressor.compressForUpload(image); //compress the image
            Random random = new Random();
            int randomNumber = random.nextInt(3); //pick a random # for stock image
            List<Integer> categoryIds = TestUtils.generateRandomCategoryIds(randomNumber,random); //temporary solution while these paramters arent being used
            List<String> eventRepIds = TestUtils.generateRandomEventRepIds(randomNumber,random); //^
            LoggiaEvent event = new ParseLoggiaEvent(
                    createEventName.getText().toString(),
                    startDate.getTime(),endDate.getTime(),
                    createEventLocation.getText().toString(),
                    imageBytes,
                    createEventDescription.getText().toString(),
                    categoryIds,
                    eventRepIds
            );
            event.saveToDb();
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }


    /**
     * TODO: Further abstract the dialogs into a single method
     * Brings up the clock dialog for both startTime and endTime
     * @param TYPE:
     *            True: End Time clock
     *            False: Start Time clock
     */
    private void showClockDialog(boolean TYPE){
        FragmentManager fm = context.getActivity().getSupportFragmentManager();
        ClockDialog clockDialog = new ClockDialog();
        clockDialog.isEndTime = TYPE;
        clockDialog.show(fm, "fragment_clock_dialog");
    }


    /**
     * TODO: Further abstract the dialogs into a single method
     * Brings up the calendar dialog for both startTime and endTime
     * @param TYPE:
     *            True: End Time calendar
     *            False: Start Time calendar
     *
     */
    private void showCalendarDialog(boolean TYPE) {
        FragmentManager fm = context.getActivity().getSupportFragmentManager();
        CalendarDialog calendarDialog = new CalendarDialog();
        calendarDialog.isEndTime = TYPE;
        calendarDialog.show(fm, "fragment_calender_dialog");
    }


    /**
     * TODO: Further abstract the dialogs into a single method
     * Brings the TagDialog to choose the event tag
     * */
    private void showTagDialog(){
        FragmentManager fm = context.getActivity().getSupportFragmentManager();
        TagDialog tagDialog = new TagDialog();
        tagDialog.setTargetFragment(this, 0);
        tagDialog.show(fm, "create_fragment");
    }


    @Override
    public void setFilterOption(int filterItem) {
        eventRepIds.add(filterItem);
    }

    @Override
    public void setCommunity(int filterItem) {
    }
}
