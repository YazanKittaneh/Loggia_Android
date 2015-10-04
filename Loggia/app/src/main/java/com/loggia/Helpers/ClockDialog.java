package com.loggia.Helpers;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;
import android.widget.TimePicker;


import com.loggia.Create.CreateActivity;
import com.loggia.R;
import com.loggia.Utils.EventDateFormat;

import java.util.Calendar;
import java.util.Date;

public class ClockDialog extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    public boolean isEndTime;


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
        CreateActivity mCreateActivity = (CreateActivity) getActivity();
        EventDateFormat eventDateFormat = new EventDateFormat();
        Date date = eventDateFormat.standardTime(hourOfDay, minute);

        if(isEndTime) {
            TextView display_time = (TextView) getActivity().findViewById(R.id.Create_End_Time);
            mCreateActivity.endDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
            mCreateActivity.endDate.set(Calendar.MINUTE, minute);
            display_time.setText(eventDateFormat.formatTime(date));
        }
        else
        {
            TextView display_time = (TextView) getActivity().findViewById(R.id.Create_Start_Time);
            mCreateActivity.startDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
            mCreateActivity.startDate.set(Calendar.MINUTE, minute);
            display_time.setText(eventDateFormat.formatTime(date));
        }

    }
}

