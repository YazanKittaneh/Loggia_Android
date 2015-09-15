package com.loggia.Helpers;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;


import com.loggia.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * TODO: Change way of getting date from dialogFragment (current way is based on a mistake introduced by the code
 */

public class CalendarDialog extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        //int day = c.get(Calendar.DAY_OF_WEEK);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        TextView mEventDate = (TextView) getActivity().findViewById(R.id.Display_Event_Date);

        /**
         * TODO: fix dislay formatting
         */

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






    /*
        Log.i("DAY  INTEGER: ", String.valueOf(day));
        Log.i("MONTH INTEGER: ", String.valueOf(month));
        String sDay = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(new Date(0,0,day-2));
        String sMonth = new SimpleDateFormat( "LLLL", Locale.ENGLISH).format(new Date(0,month+1,0));
        display_time.setText(sDay + ", " + sMonth + " " + day );
    */
        mEventDate.setText(format.format(date));

}
}

