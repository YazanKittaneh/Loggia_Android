package com.example.l7.project_chatter.Controllers;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.ByteArrayOutputStream;

/**
 * Created by L7 on 2/28/15.
 */


/*
    EventObject should handle and encapsulate all of the pareObject information
    so that it is easy to use
 */
public class EventObject {

    public String NAME = "Name";
    public String TIME = "Time";
    public String HOST = "Host";
    public String DESCRIPTION = "Description";
    public String LOCATION = "Location";
    public String EVENT = "event";
    public String IMAGE = "Image";
    ParseObject mParseObject;

    /***********************
    Constructors
    ************************/
    /*
    EventObject eventObject = new EventObject();
     */
    public EventObject(){
        this.mParseObject = new ParseObject(EVENT);
    }

    /*
    EventObject eventObject = new EventObject(praseObject);
    */
    public EventObject(ParseObject inputParseObject)
    {
        this.mParseObject = inputParseObject;
    }

    /*
     * A controlled way of putting all information necessary into the parseObject
     * Allows for the information to be handled elsewere
     */
    public void putEventInfo(String name, String time, String location, String host, String description, Bitmap image){
        this.mParseObject.put(NAME, name);
        this.mParseObject.put(TIME, time);
        this.mParseObject.put(HOST, host);
        this.mParseObject.put(DESCRIPTION, description);
        this.mParseObject.put(LOCATION, location);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] data = stream.toByteArray();
        ParseFile imageFile = new ParseFile("image.png", data);
        this.mParseObject.put(IMAGE, imageFile);
    }

    public void pushEventToCloud(){
        this.mParseObject.saveInBackground();
    }

    /*
     * Getters and Setters
     */
    public String getEventName() {
        return this.mParseObject.getString(NAME);
    }

    public void setEventName(String eventName) {
        this.mParseObject.put(NAME, eventName);
    }


    public String getEventTime() {
        return this.mParseObject.getString(TIME);
    }

    public void setEventTime(String eventTime) {
        this.mParseObject.put(TIME, eventTime);
    }

    public String getEventLocation() {
        return this.mParseObject.getString(LOCATION);
    }

    public void setEventLocation(String eventLocation) {
        this.mParseObject.put(LOCATION, eventLocation);
    }


    public void getEventID(){
        this.mParseObject.getObjectId();
    }
}
