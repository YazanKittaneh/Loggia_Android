package com.example.l7.project_chatter.Controllers;

import android.graphics.drawable.Drawable;

import com.parse.ParseObject;

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
    ParseObject eventObject;

    /*
    Constructors
     */
    public EventObject(){
        this.eventObject = new ParseObject(EVENT);
    }

    public EventObject(ParseObject inputParseObject)
    {
        this.eventObject = inputParseObject;
    }

    /**(
     * A controlled way of putting all information necessary into the parseObject
     * Allows for the information to be handled elsewere
     */
    public void putEventInfo(String name, String time, String location, String host, String description, Drawable image){
        this.eventObject.put(NAME, name);
        this.eventObject.put(TIME, time);
        this.eventObject.put(HOST, host);
        this.eventObject.put(DESCRIPTION, description);
        this.eventObject.put(LOCATION, location);
        this.eventObject.put(IMAGE, image);
    }

    public void pushEventToCloud(){
        this.eventObject.saveInBackground();
    }

    /*
     * Getters and Setters
     */
    public String getEventName() {
        return this.eventObject.getString(NAME);
    }

    public void setEventName(String eventName) {
        this.eventObject.put(NAME, eventName);
    }


    public String getEventTime() {
        return this.eventObject.getString(TIME);
    }

    public void setEventTime(String eventTime) {
        this.eventObject.put(TIME, eventTime);
    }

    public String getEventLocation() {
        return this.eventObject.getString(LOCATION);
    }

    public void setEventLocation(String eventLocation) {
        this.eventObject.put(LOCATION, eventLocation);
    }


    public void getEventID(){
        this.eventObject.getObjectId();
    }
}
