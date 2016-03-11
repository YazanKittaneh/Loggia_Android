package com.loggia.Utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;

import com.loggia.Model.ParseModels.ParseLoggiaEvent;
import com.loggia.Model.ParseModels.ParseLoggiaUser;
import com.loggia.R;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by albertowusu-asare on 3/10/16.
 */
public class TestUtils {
    public static  ParseLoggiaEvent generateParseEvent(){
        Random random = new Random();
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
        List<Integer> categoryIds = generateRandomCategoryIds(4,random);
        List<String> eventRepIds = generateRandomEventRepIds(4,random);
        ParseLoggiaEvent event = new ParseLoggiaEvent(eventName,startDate,endDate,location,null,
                description,categoryIds,eventRepIds);
        return event;
    }

    private static List<String> generateRandomEventRepIds(int numReps,Random random){
        List<String> eventReps = new ArrayList<>();
        String base = "EventRep ";
        for (int i = 0; i< numReps ; i++){
            eventReps.add(base + random.nextInt(numReps));
        }
        return eventReps;
    }

    private static List<Integer> generateRandomCategoryIds(int numCategories, Random random){
        List<Integer> categoryIds = new ArrayList<>();
        for(int i = 0 ; i< numCategories; i++){
            categoryIds.add(random.nextInt(numCategories));
        }
        return categoryIds;
    }

    public static List<ParseLoggiaEvent> generateParseEvents(int numberEvents){
        List<ParseLoggiaEvent> events = new ArrayList<>();
        for(int i = 0; i< numberEvents; i++)
            events.add(generateParseEvent());
        return events;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static long randLong(int min, int max) {
        long randomNum = ThreadLocalRandom.current().nextInt(min, max);
        return randomNum;
    }

    public static byte[]  getRandomImage(Activity activity){
        int randomImageId = getRandomImageId();
        ImageCompressor imgCompressor = new ImageCompressor(activity);
        BitmapDrawable decodedResImage =
                imgCompressor.decodeSampledBitmapFromDrawable(randomImageId);
        return ImageCompressor.compressForUpload(decodedResImage.getBitmap());
    }

    /**
     * Return a random identifier for an image in the project resources
     * @return
     */
    private static int getRandomImageId(){
        Random RANDOM = new Random();
        switch (RANDOM.nextInt(3)) {
            default:
            case 0:
                return R.drawable.stock_1;
            case 1:
                return R.drawable.stock_2;
            case 2:
                return R.drawable.stock_3;
        }
    }
}
