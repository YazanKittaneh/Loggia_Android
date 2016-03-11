
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;

import com.loggia.Activities.ContainerActivity;
import com.loggia.Model.ParseModels.ParseLoggiaEvent;
import com.loggia.Model.ParseModels.ParseLoggiaUser;
import com.loggia.Utils.BackendDomain;
import com.loggia.Utils.LoggiaUtils;
import com.loggia.Utils.TableData;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import android.content.Context;
import android.test.AndroidTestCase;
import android.test.ApplicationTestCase;
import android.test.InstrumentationTestCase;
import android.test.mock.MockContext;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;

/**
 * Created by albertowusu-asare on 3/9/16.
 */

public class ParseLoggiaModelTest  {

    @Before
    public void initialize(){
       MockContext context = new MockContext();
        LoggiaUtils.initializeBackendService(BackendDomain.PARSE, context.getApplicationContext());
    }
    @Test
    public void parseLoggiaEventTest(){
        int numEvents = (int) randLong(0,5);
        List<ParseLoggiaEvent> events = generateParseEvents(numEvents);
        for(ParseLoggiaEvent event : events)
            event.saveToDb();
        final int expectedCount = events.size();
        final String dBCounterName = "events";
        queryCounterTable(dBCounterName, expectedCount);
    }

    /**
     * Generates , numberEvents, number of Events
     * @param numberEvents, number of events to generate
     */
    private List<ParseLoggiaEvent> generateParseEvents(int numberEvents){
        List<ParseLoggiaEvent> events = new ArrayList<>();
        for(int i = 0; i< numberEvents; i++)
            events.add(generateParseEvent());
        return events;
    }

    private ParseLoggiaEvent generateParseEvent(){
        int  MILLI_SEC_1_WK= 604800000;
        int  MILLI_SEC_2_WKS= 1209600000;
        long randNumber = randLong(0,100);
        String eventName = "event" + randNumber;
        String location = "location" +randNumber;
        String description = "description" + randNumber;
        ParseLoggiaUser currentUser = new ParseLoggiaUser(ParseUser.getCurrentUser());
        Date startDate = new Date();
        long startDateTime = startDate.getTime();
        Date endDate = new Date(startDateTime + randLong(MILLI_SEC_1_WK,MILLI_SEC_2_WKS));
        ParseLoggiaEvent event = new ParseLoggiaEvent(eventName,startDate,endDate,location,null,
                description,null,currentUser);
        return event;
    }

    public static void queryCounterTable(final String counterName, final int expectedCount){
        int count;
        ParseQuery<ParseObject> counter_query = new ParseQuery(TableData.TableNames.COUNTER.toString());
        counter_query.whereEqualTo(TableData.CounterColumnNames.counter_name.toString(),counterName);
        counter_query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {
                    int actualCount = parseObject.getInt(counterName);
                    assertTrue("Expected count of " +
                            counterName + " equals actual count :",actualCount == expectedCount);
                }
            }
        });
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static long randLong(int min, int max) {
        long randomNum = ThreadLocalRandom.current().nextInt(min, max);
        return randomNum;
    }

}

