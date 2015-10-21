package com.loggia.Utils;

import android.content.Context;
import android.util.Log;

import com.loggia.Interfaces.LoggiaEvent;
import com.loggia.Model.ParseModels.ParseLoggiaEvent;
import com.loggia.R;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by albertowusu-asare on 10/19/15.
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
                    //login not successful
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

        ParseQuery<ParseObject> event_query = new ParseQuery(TableData.TableNames.EVENT.toString());

        // query events with start time >= current time
        event_query.whereGreaterThanOrEqualTo(TableData.EventColumnNames.event_date.toString(),
                EventDateFormat.getCurrentDate());
        /* will only get events with a date greater than the current date */
        event_query.addAscendingOrder(TableData.EventColumnNames.event_date.toString());

               /* event_query.findInBackground(new FindCallback<ParseLoggiaEvent>() {


                    final List<ParseLoggiaEvent> eventes = null;

                    @Override
                    public void done(List<ParseLoggiaEvent> markers, com.parse.ParseException e) {
git
                        final List<ParseLoggiaEvent> events;

                        if (e == null) {
                            events = markers;
                            for (int i = 0; i < markers.size(); i++) {
                                Log.e("WITHIN PARSE", "WORKING");
                                ParseObject currentObject = markers.get(i);
                                /*createCard(
                                        currentObject.getString("Name"),
                                        EventDateFormat.formatTime(currentObject.getDate("StartTime")),
                                        EventDateFormat.formatDate(currentObject.getDate("StartTime")),
                                        currentObject.getParseFile("Image").getUrl(),
                                        currentObject.getObjectId()
                                );

                            }
                        } else {
                            //Log.e("DONE ERROR", "DOES NOT WORK");
                        }
            }

        }*/
        return null;
    }
}
