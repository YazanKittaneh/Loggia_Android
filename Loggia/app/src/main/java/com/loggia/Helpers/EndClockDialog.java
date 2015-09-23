package com.loggia.Helpers;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ContextThemeWrapper;
import android.widget.TextView;
import android.widget.TimePicker;

import com.loggia.Create.CreateActivity;
import com.loggia.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class EndClockDialog extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    Calendar c;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker

        c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        //int zone = c.get(Calendar.AM_PM);

        // Create a new instance of DatePickerDialog and return it
        return new TimePickerDialog(new ContextThemeWrapper(getActivity(), R.style.Theme_Loggia), this, hour, minute, false);
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView display_time = (TextView) getActivity().findViewById(R.id.Display_End_Time);
        CreateActivity mCreateActivity = (CreateActivity) getActivity();
        Calendar thisDate = new GregorianCalendar();
        thisDate.add(Calendar.HOUR_OF_DAY, hourOfDay);
        thisDate.add(Calendar.MINUTE, minute);

        TimeZone mTimeZone;
        if (thisDate.getTimeZone().inDaylightTime(new Date())) {
            mTimeZone = TimeZone.getTimeZone("GMT-5");
        }
        else {
            mTimeZone = TimeZone.getTimeZone("GMT-6");
        }
        thisDate.setTimeZone(mTimeZone);


        SimpleDateFormat format = new SimpleDateFormat("h:mm a", Locale.US);




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
        mCreateActivity.endTime = thisDate.getTime();
        display_time.setText(format.format(thisDate.getTime()));
    }
}

