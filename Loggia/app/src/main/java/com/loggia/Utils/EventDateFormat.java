package com.loggia.Utils;

import android.util.Log;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by L7 on 9/23/15
 * Standard date formatter
 */

/**
 * TODO: Make method to regularize the timezone of the Date object
 */
public class EventDateFormat {


    private static String FORMAT_TIME = "h:mm a";
    private static String FORMAT_DATE = "EE"+", " + "LLLL dd";


    /** Takes time and makes it standard to timezone
     **/
    public Date standardTime(int hourOfDay, int minute){
        Calendar thisDate = new GregorianCalendar();
        thisDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
        thisDate.set(Calendar.MINUTE, minute);


        /* //legacy way of handling timezone only in CST
        if (thisDate.getTimeZone().inDaylightTime(new Date())) {
            mTimeZone = TimeZone.getTimeZone("GMT-5");
        }
        else {
            mTimeZone = TimeZone.getTimeZone("GMT-6");
        }
        */

        TimeZone currentTimezone = TimeZone.getDefault();
        thisDate.setTimeZone(currentTimezone);

        return thisDate.getTime();
    }



    /** Gets current date based on phone **/
    public static Date getCurrentDate(){
        Calendar todayCalendar = new GregorianCalendar();
        todayCalendar.setTimeZone(TimeZone.getDefault());
        Date today = todayCalendar.getTime();
        return today;
    }


    /** Simply formats the date object given into the h-mm format
     *  return:
     *      String, the h-mm representation of the data object
     */
    public static String formatDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
        return format.format(date);
    }

    /**
     * Formats the date given to Loggia's standard time format
     */
    static public String formatTime(Date date){
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_TIME);
        return format.format(date);
    }


}
