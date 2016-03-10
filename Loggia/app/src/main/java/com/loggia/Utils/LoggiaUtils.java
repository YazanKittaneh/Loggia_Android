package com.loggia.Utils;

import android.content.Context;
import android.util.Log;

import com.loggia.Interfaces.LoggiaEvent;
import com.loggia.Interfaces.LoggiaUser;
import com.loggia.Model.ParseModels.ParseLoggiaEvent;
import com.loggia.Model.ParseModels.ParseLoggiaUser;
import com.loggia.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by albertowusu-asare on 10/19/15.
 * Util static method used in the system
 */
public class LoggiaUtils {

    /**
     * Performs initialisations of the backend Service in use
     * @param domain define the domain in use for the backend as a service
     * @param context the context this call is made from
     */
    public static void initializeBackendService(BackendDomain domain, Context context){
        if(domain.equals(BackendDomain.PARSE))
            initializeParseBackendService(context);
    }

    private static void initializeParseBackendService(Context context){
        Parse.enableLocalDatastore(context);
        Parse.initialize(context,context.getResources().getString(R.string.PARSE_APP_ID),
                context.getResources().getString(R.string.PARSE_CLIENT_KEY));
    }


    /**
     * Logs in a user anonymously into parse
     *
     * @param domain
     */
    public static void anonymousUserLogIn(BackendDomain domain) {
        if (domain.equals(BackendDomain.PARSE))
            parseAnonymousUserLogIn();
    }


    public static void parseAnonymousUserLogIn() {
        ParseAnonymousUtils.logIn(new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {

                if (e == null && user != null) {
                    //logIn successful
                    Log.d("MyApp", "Anonymous logged in.");
                } else {
                    //TODO : notification to the UI that anonymous logIn was unsuccesful
                }
            }
        });
    }


    /**
     * @param startDateTime determines the lowerbound date to query from.
     * @return a list of all the events starting from 'startDateTime'. List is returned by default
     * in ascending order.
     */
    public static List<LoggiaEvent> getEvents(Date startDateTime) {

        final List<ParseLoggiaEvent> events;

        ParseQuery<ParseObject> event_query = new ParseQuery("Email_test");//TableData.TableNames.EVENT.toString());

        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy kk-mm");
        //04/22/2016 11:30
        // query events with start time >= current time
        event_query.whereGreaterThanOrEqualTo(TableData.EventColumnNames.event_start_date.toString(),
                EventDateFormat.getCurrentDate());
        /* will only get events with a date greater than the current date */
        try {
            event_query.addAscendingOrder(formatter.parse(TableData.EventColumnNames.event_start_date.toString()).toString());
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Pushes an event to a set database according to the domain
     */
    public static <T extends LoggiaUser>  void saveEvent(BackendDomain domain, String eventName,
                                 Date eventStartDate,
                                 Date eventEndDate,
                                 String eventLocation,
                                 byte []  eventImage,
                                 String eventDescription,
                                 CategoryMap eventCategory,
                                 T eventRep)
    {
        LoggiaEvent event = null;
        if(domain.equals(BackendDomain.PARSE)){
            ParseLoggiaUser parseEventRep = (ParseLoggiaUser) eventRep;
            event = new ParseLoggiaEvent(eventName, eventStartDate, eventEndDate, eventLocation,
                    eventImage, eventDescription, eventCategory, parseEventRep);
                    //TODO: reintegrate eventCategory
        }
        event.saveToDb();
    }

    /**
     * Queries the Counter table according to the given parameter, counter name
     */

    public static void querryCounterTable(final String counterName){
        ParseQuery<ParseObject> counter_query = new ParseQuery(TableData.TableNames.COUNTER.toString());
        counter_query.whereEqualTo(TableData.CounterColumnNames.counter_name.toString(),counterName);
        counter_query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {
                   parseObject.getInt(counterName);

                }
            }
        });
    }
}
