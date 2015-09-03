package com.loggia.Helpers;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;
import android.widget.TimePicker;

import com.loggia.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        //int zone = c.get(Calendar.AM_PM);

        // Create a new instance of DatePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute, false);
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView display_time = (TextView) getActivity().findViewById(R.id.Display_End_Time);





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


        display_time.setText(currentHour + ":" + String.format("%02d", minute) + " " + aMpM);

    }
}

