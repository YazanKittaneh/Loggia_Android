package com.loggia.Helpers;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.AnalogClock;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;
import android.widget.TimePicker;


import com.loggia.Create.CreateActivity;
import com.loggia.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class StartClockDialog extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        //int zone = c.get(Calendar.AM_PM);

        // Create a new instance of DatePickerDialog and return it
        return new TimePickerDialog(new ContextThemeWrapper(getActivity(), R.style.Theme_Loggia), this, hour, minute, false);
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView display_time = (TextView) getActivity().findViewById(R.id.Display_Start_Time);
        CreateActivity mCreateActivity = (CreateActivity) getActivity();
        Calendar thisDate = new GregorianCalendar();
        thisDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
        thisDate.set(Calendar.MINUTE, minute);


        TimeZone mTimeZone;
        if (thisDate.getTimeZone().inDaylightTime(new Date())) {
            mTimeZone = TimeZone.getTimeZone("GMT-5");
        }
        else {
            mTimeZone = TimeZone.getTimeZone("GMT-6");
        }
        thisDate.setTimeZone(mTimeZone);


        SimpleDateFormat format = new SimpleDateFormat("h:mm a");

        /*
        String aMpM = "AM";
        if(hourOfDay >11)
        {
            aMpM = "PM";
        }

        int currentHour;
        if(hourOfDay>12)
        {
                currentHour = hourOfDay - 12;
        }
        else
        {
            if(hourOfDay == 0)
                currentHour = 12;
             else
                currentHour = hourOfDay;
        }
        */

        //display_time.setText(currentHour + ":" + String.format("%02d", minute) + " " + aMpM);
        mCreateActivity.startTime = thisDate.getTime();
        display_time.setText(format.format(thisDate.getTime()));
    }
}

