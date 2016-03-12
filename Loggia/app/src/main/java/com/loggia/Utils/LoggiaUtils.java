package com.loggia.Utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.dexafree.materialList.cards.BigImageCard;
import com.dexafree.materialList.view.MaterialListView;
import com.loggia.Interfaces.LoggiaCategory;
import com.loggia.Interfaces.LoggiaEvent;
import com.loggia.Interfaces.LoggiaUser;
import com.loggia.Model.ParseModels.ParseLoggiaCategory;
import com.loggia.Model.ParseModels.ParseLoggiaEvent;
import com.loggia.Model.ParseModels.ParseLoggiaOrg;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by albertowusu-asare on 10/19/15.
 * Util static method used in the system
 */
public class LoggiaUtils {

    /*
      Constants
     */

    public static Map<Integer, CharSequence> initialCategoryMap = new HashMap<>();

    public LoggiaUtils(){
        this.initialCategoryMap = new HashMap<>();
    }
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
        ParseObject.registerSubclass(ParseLoggiaEvent.class);
        ParseObject.registerSubclass(ParseLoggiaUser.class);
        ParseObject.registerSubclass(ParseLoggiaOrg.class);
        ParseObject.registerSubclass(ParseLoggiaCategory.class);
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
    public static   void saveEvent(BackendDomain domain, String eventName,
                                 Date eventStartDate,
                                 Date eventEndDate,
                                 String eventLocation,
                                 byte []  eventImage,
                                 String eventDescription,
                                 List<Integer> eventCategory,
                                 List<String> eventRep)
    {
        LoggiaEvent event = null;
        if(domain.equals(BackendDomain.PARSE)){
            ParseLoggiaUser parseEventRep = (ParseLoggiaUser) eventRep;
            event = new ParseLoggiaEvent(eventName, eventStartDate, eventEndDate, eventLocation,
                    eventImage, eventDescription, eventCategory, eventRep);
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



    public static Map<Integer,CharSequence> getCategories(){
        Log.i("get Categories :", "Inside getCategoriees: ");
        ParseQuery<ParseLoggiaCategory> categoryQuery =
                new ParseQuery(TableData.TableNames.CATEGORY.toString());
        categoryQuery.findInBackground(new FindCallback<ParseLoggiaCategory>() {
                                           @Override
                                           public void done(List<ParseLoggiaCategory> list, ParseException e) {
                                               Log.e("Done get categorries", "inside");
                                               if (e == null) {
                                                   Log.e("No exception", list.toString());
                                                   if (list == null) {
                                                       Log.e("List result", "is null");
                                                   }
                                               } else {
                                                   Log.e("Error Done", e.getMessage());
                                               }
                                           }
                                       }
        );
        return initialCategoryMap;
    }

    private static  <T extends LoggiaCategory> void populateInitialCategoryMap(T category){
        initialCategoryMap.put(category.getCategoryId(), category.getCategoryName());
    }

    public static void initializeCategoryTable(){
        ParseObject obj = new ParseObject(TableData.TableNames.CATEGORY.toString());
        obj.put(TableData.CategoryColumnNames.category_id.toString(),1);
        obj.put(TableData.CategoryColumnNames.category_name.toString(), "Art");
        obj.saveInBackground();
        obj = new ParseObject(TableData.TableNames.CATEGORY.toString());
        obj.put(TableData.CategoryColumnNames.category_id.toString(), 2);
        obj.put(TableData.CategoryColumnNames.category_name.toString(),"Food");
        obj.saveInBackground();
        obj = new ParseObject(TableData.TableNames.CATEGORY.toString());
        obj.put(TableData.CategoryColumnNames.category_id.toString(), 3);
        obj.put(TableData.CategoryColumnNames.category_name.toString(),"Parties");
        obj.saveInBackground();
        obj = new ParseObject(TableData.TableNames.CATEGORY.toString());
        obj.put(TableData.CategoryColumnNames.category_id.toString(), 4);
        obj.put(TableData.CategoryColumnNames.category_name.toString(),"Sports");
        obj.saveInBackground();
        obj = new ParseObject(TableData.TableNames.CATEGORY.toString());
        obj.put(TableData.CategoryColumnNames.category_id.toString(),5);
        obj.put(TableData.CategoryColumnNames.category_name.toString(),"Student");
        obj.saveInBackground();
    }

    public  static void queryAndPopulateEvents(BackendDomain domain,final Context context,final View view,
                             final MaterialListView listView){

        if(domain.equals(BackendDomain.PARSE)) {
            ParseQuery<ParseLoggiaEvent> event_query = new ParseQuery<>(ParseLoggiaEvent.class);
            event_query.whereGreaterThanOrEqualTo(
                    TableData.EventColumnNames.event_end_date.toString(),
                    EventDateFormat.getCurrentDate());
            event_query.addAscendingOrder(TableData.EventColumnNames.event_start_date.toString());
            event_query.findInBackground(new FindCallback<ParseLoggiaEvent>() {
                @Override
                public void done(List<ParseLoggiaEvent> events, com.parse.ParseException e) {
                    if (e == null) {
                        Log.e("DONE AND IT WORKS", "DOES WORK");

                        for (ParseLoggiaEvent event : events) {
                            createCard(event.getEventName(),
                                    EventDateFormat.formatTime(event.getEventStartDate()),
                                    EventDateFormat.formatDate((event.getEventStartDate())),
                                    event.getEventImageUrl(),event,context,listView);
                        }
                    } else {
                        Snackbar.make(view, "Feed failed to load", Snackbar.LENGTH_SHORT);
                    }
                }
            });
        }

    }

    private static void createCard(String name, String startTime, String date, String imageURL,
                            LoggiaEvent event, Context context, MaterialListView listView){
        BigImageCard card = new BigImageCard(context);
        card.setTitle(name);
        card.setDescription(date + " at " + startTime);
        card.setDrawable(imageURL);
        card.setTag(event);
        listView.add(card);
    }




}
