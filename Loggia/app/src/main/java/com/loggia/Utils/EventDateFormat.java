package com.loggia.Utils;

import android.util.Log;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by L7 on 9/23/15.
 */

/**
 * TODO: Make method to regularize the timezone of the Date object
 */
public class EventDateFormat {


    private static String FORMAT_TIME = "h:mm a";
    private static String FORMAT_DATE = "EE"+", " + "LLLL dd";
    


    public Date standardTime(int hourOfDay, int minute){
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

        return thisDate.getTime();
    }


    /**
     * Formats the date given to Loggia's standard time format
     * @param date
     * @return
     */
    static public String formatTime(Date date){
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_TIME);
        return format.format(date);
    }

    public static Date getCurrentDate(){
        Calendar todayCalendar = new GregorianCalendar();
        //TimeZone timeZone;
        //timeZone = TimeZone.getDefault();
        todayCalendar.setTimeZone(TimeZone.getTimeZone("GMT-0"));

        Date today = todayCalendar.getTime();
        Log.d("CURRENT TIME: ", today.toString());

        return today;
    }

    public static String formatDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
        return format.format(date);
    }


}