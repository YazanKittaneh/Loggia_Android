package com.loggia.Model.ParseModels;

import com.loggia.Interfaces.LoggiaEvent;
import com.loggia.Interfaces.LoggiaUser;
import com.loggia.Utils.CategoryMap;
import com.loggia.Utils.Constants;
import com.loggia.Utils.TableData;
import com.parse.FindCallback;
import com.parse.ParseClassName;
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

@ParseClassName("ParseLoggiaEvent")
public class ParseLoggiaEvent extends ParseObject implements LoggiaEvent{
    private ParseObject event;
    private List<LoggiaUser> users;
    private List<LoggiaUser> eventRepresentatives;
    private String eventName;
    private Date eventStartDate;
    private Date eventEndDate;
    private String eventLocation;
    private byte [] eventImage;
    private String eventDescription;
    private List<String> eventRepIds;
    private List<Integer> eventCategoryIds;

    public ParseLoggiaEvent(){
        super();
        this.event = this;
        this.users = new ArrayList<LoggiaUser>();
        this.eventRepresentatives = new ArrayList<LoggiaUser>();
    }

    public ParseLoggiaEvent(String eventName, Date eventStartDate, Date eventEndDate,
                            String eventLocation, byte [] eventImage, String eventDescription,
                            List<Integer> eventCategoryIds, List<String> eventRepIds)
    {
        this.eventName = eventName;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.eventLocation = eventLocation;
        this.eventImage = eventImage;
        this.eventDescription = eventDescription;
        this.eventCategoryIds = eventCategoryIds;
        this.eventRepIds = eventRepIds;
        this.event =  new ParseObject(TableData.TableNames.EVENT.toString());
    }


    public void setEventName(String eventName){this.eventName = eventName;}
    public void setEventStartDate(Date eventStartDate){this.eventStartDate = eventStartDate;}
    public void setEventEndDate(Date eventEndDate){this.eventEndDate = eventEndDate;}
    public void setEventLocation(String eventLocation){this.eventLocation = eventLocation;}
    public void setEventImage(byte [] eventImage){this.eventImage = eventImage;}
    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
    public void setEventRepresentative(List<String> eventRepIds){this.eventRepIds = eventRepIds;}
    public void setEventCategory(List<Integer> eventCategoryIds){this.eventCategoryIds = eventCategoryIds;}


    @Override
    public String getEventName() {
        return event.getString(TableData.EventColumnNames.event_name.toString());
    }

    @Override
    public Date getEventStartDate(){
        return !(this.eventStartDate == null) ? this.eventStartDate :
                (this.eventStartDate = event.getDate(TableData.EventColumnNames.event_start_date.toString()));

    }

    @Override
    public Date getEventEndDate(){
        return event.getDate(TableData.EventColumnNames.event_end_date.toString());
    }

    @Override
    public String getEventLocation() {
        return event.getString(TableData.EventColumnNames.event_location.toString());
    }

    @Override
    public String getEventImageUrl() {
        return event.getParseFile(TableData.EventColumnNames.event_image.toString()).getUrl();
    }

    @Override
    public String getEventDescription() {
        return event.getParseFile(TableData.EventColumnNames.event_description.toString()).getUrl();
    }

    @Override
    public long getNumEventViews() {
        return event.getLong(TableData.EventColumnNames.event_views.toString());
    }

    @Override
    public List<String> getEventCategories() {
        return null;
    }

    @Override
    public long getNumEventInvites() {
        return event.getLong(TableData.EventColumnNames.event_num_invites.toString());
    }

    @Override
    public List<LoggiaUser> getEventUsersInvited() {
        return this.users;
    }

    @Override
    public List<LoggiaUser> getEventRepresentatives() {
        return this.eventRepresentatives;
    }

    @Override
    public void saveToDb() {
        event.put(TableData.EventColumnNames.event_name.toString(), this.eventName);
        event.put(TableData.EventColumnNames.event_start_date.toString(), this.eventStartDate);
        event.put(TableData.EventColumnNames.event_end_date.toString(),this.eventEndDate);
        event.put(TableData.EventColumnNames.event_location.toString(),this.eventLocation);
        ParseFile imageFile = new ParseFile("Image.jpg", eventImage);
        event.put(TableData.EventColumnNames.event_image.toString(), imageFile);
        event.put(TableData.EventColumnNames.event_description.toString(),this.eventDescription);
        event.put(TableData.EventColumnNames.event_rep_ids.toString(),this.eventRepIds);
        event.put(TableData.EventColumnNames.event_category_ids.toString(), this.eventCategoryIds);
        event.saveInBackground();
    }


}
