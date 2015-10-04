package com.loggia.Helpers;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ContextThemeWrapper;
import android.widget.TextView;
import android.widget.TimePicker;

import com.loggia.Activities.CreateActivity;
import com.loggia.R;
import com.loggia.Utils.EventDateFormat;

import java.util.Calendar;
import java.util.Date;

public class EndClockDialog extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    Calendar c;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker

        c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(new ContextThemeWrapper(getActivity(), R.style.Theme_Loggia), this, hour, minute, false);
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView display_time = (TextView) getActivity().findViewById(R.id.Display_End_Time);
        CreateActivity mCreateActivity = (CreateActivity) getActivity();

        EventDateFormat eventDateFormat = new EventDateFormat();
        Date date = eventDateFormat.standardTime(hourOfDay, minute);

        mCreateActivity.endTime =date;
        display_time.setText(eventDateFormat.formatTime(date));
    }
}

