package com.loggia.Utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by L7 on 9/23/15.
 */
public class EventDateFormat {


    private String FORMAT = "h:mm a";


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
    public String formatTime(Date date){
        SimpleDateFormat format = new SimpleDateFormat(FORMAT);
        return format.format(date);
    }

    public Date getCurrentDate(){
        Calendar todayCalendar = new GregorianCalendar();
        TimeZone timeZone;
        timeZone = TimeZone.getDefault();
        todayCalendar.setTimeZone(timeZone);

        Date today = todayCalendar.getTime();
        //int daysPastBase = daysBetween(today, baseDate);
        return today;
    }

}
