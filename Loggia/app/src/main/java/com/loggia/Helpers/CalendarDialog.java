package com.loggia.Helpers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.widget.DatePicker;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;


import com.loggia.Create.CreateActivity;
import com.loggia.R;
import com.loggia.Utils.EventDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class CalendarDialog extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    public boolean isEndTime;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(new ContextThemeWrapper(getActivity(), R.style.Theme_Loggia), this, year, month, day);

    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        TextView mEventStartDate = (TextView) getActivity().findViewById(R.id.Create_Start_Date);
        TextView mEventEndDate = (TextView) getActivity().findViewById(R.id.Create_End_Date);
        CreateActivity mCreateActivity = (CreateActivity) getActivity();
        Calendar thisDate = new GregorianCalendar(year, month, day);

        TimeZone mTimeZone;
        if (thisDate.getTimeZone().inDaylightTime(new Date())) {
            mTimeZone = TimeZone.getTimeZone("GMT-5");
        }
        else {
            mTimeZone = TimeZone.getTimeZone("GMT-6");
        }
        thisDate.setTimeZone(mTimeZone);

        /*
        // String pattern = "LLLL-EEEE-yyyy";
        String pattern = "EEEE"+", " + "LLLL dd";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = new Date();

        try {
            date = format.parse((month+1)+"-"+day+"-"+year);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(format.format(new Date()));


        Log.i("DAY  INTEGER: ", String.valueOf(day));
        Log.i("MONTH INTEGER: ", String.valueOf(month));
        String sDay = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(day);
        String sMonth = new SimpleDateFormat( "LLLL", Locale.getDefault()).format(month);



        display_time.setText(sDay + ", " + sMonth + " " + day );
    */
        mCreateActivity.calendarDate = thisDate.getTime();

        if(isEndTime) {
            mEventEndDate.setText(EventDateFormat.formatDate(thisDate.getTime()));
            mCreateActivity.endDate.set(year, month, day);

        }
        else
        {
            mCreateActivity.startDate.set(year, month, day);
            mCreateActivity.endDate.set(year, month, day);
            mEventEndDate.setText(EventDateFormat.formatDate(thisDate.getTime()));
            mEventStartDate.setText(EventDateFormat.formatDate(thisDate.getTime()));


        }


}
}

