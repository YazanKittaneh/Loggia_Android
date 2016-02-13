package com.loggia.Model.ParseModels;

import com.loggia.Interfaces.LoggiaEvent;
import com.loggia.Interfaces.LoggiaUser;
import com.loggia.Utils.CategoryMap;
import com.loggia.Utils.Constants;
import com.loggia.Utils.TableData;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Albert Owusu-Asare
 * Date : October 4 2015
 * An implementation of LoggiaEvent using Parse
 */


public class ParseLoggiaEvent extends ParseObject implements LoggiaEvent{
    private ParseObject event;
   private  List<ParseLoggiaUser> users;
   private List<ParseLoggiaUser> eventRepresentatives;


    public ParseLoggiaEvent(){
        this.users = new ArrayList<ParseLoggiaUser>();
        this.eventRepresentatives = new ArrayList<ParseLoggiaUser>();
    }


    public ParseLoggiaEvent(
            String eventName,
            Date eventStartDate,
            Date eventEndDate,
            String eventLocation,
            byte [] eventImage,
            String eventDescription,
            CategoryMap eventCategory,
            LoggiaUser eventRep
    ){
        setEventName(eventName);
        setEventStartDate(eventStartDate);
        setEventEndDate(eventEndDate);
        setEventLocation(eventLocation);
        setEventImage(eventImage);
        setEventDescription(eventDescription);
        setEventRepresentative(eventRep);
        setEventCategory(eventCategory);
    }

   /** SETTERS **/
    public void setEventName(String eventName){
        event.put(TableData.EventColumnNames.event_name.toString(), eventName);
    }
    public void setEventStartDate(Date eventStartDate){
        event.put(TableData.EventColumnNames.event_start_date.toString(), eventStartDate);
    }
    public void setEventEndDate(Date eventEndDate){
        event.put(TableData.EventColumnNames.event_end_date.toString(),eventEndDate);
    }

    public void setEventLocation(String eventLocation){
        event.put(TableData.EventColumnNames.event_location.toString(),eventLocation);
    }
    public void setEventImage(byte [] eventImage){
        ParseFile imageFile = new ParseFile("Image.jpg", eventImage);
        event.put(TableData.EventColumnNames.event_image.toString(), imageFile);
    }
    public void setEventDescription(String eventDescription){
        event.put(TableData.EventColumnNames.event_description.toString(),eventDescription);
    }
    public void setEventRepresentative(LoggiaUser eventRep){
        event.put(TableData.EventColumnNames.event_rep_id.toString(),eventRep);

    }
    public void setEventCategory(CategoryMap eventCategory){
        event.put(TableData.EventColumnNames.event_tag.toString(), eventCategory);
    }
    /** SETTERS **/

    /** GETTERS **/
    public String getEventName() {
        return event.getString(TableData.EventColumnNames.event_name.toString());
    }

    public Date getEventStartDate(){
        return event.getDate(TableData.EventColumnNames.event_start_date.toString());
    }

    public Date getEventEndDate(){
        return event.getDate(TableData.EventColumnNames.event_end_date.toString());
    }

  //  @Override
    public String getEventLocation() {
        return event.getString(TableData.EventColumnNames.event_location.toString());
    }

   // @Override
    public String getEventImageUrl() {
        return event.getParseFile(TableData.EventColumnNames.event_image.toString()).getUrl();
    }

    @Override
    public String getEventDescription() {
        return event.getParseFile(TableData.EventColumnNames.event_description.toString()).getUrl();
    }

    // @Override
    public long getNumEventViews() {
        return event.getLong(TableData.EventColumnNames.event_views.toString());
    }

  //  @Override
    public long getNumEventInvites() {
        return event.getLong(TableData.EventColumnNames.event_num_invites.toString());
    }

    @Override
    public <T extends LoggiaUser> List<T> getEventUsersInvited() {
        return null;
    }

    @Override
    public <T extends LoggiaUser> List<T> getEventRepresentatives() {
        return null;
    }
    /** GETTERS **/


    @Override
    public void saveToDb() {
        this.saveInBackground();
    }


}
