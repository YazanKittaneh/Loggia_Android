package com.loggia.Model.ParseModels;

import com.loggia.Interfaces.LoggiaEvent;
import com.loggia.Interfaces.LoggiaUser;
import com.loggia.Utils.TableData;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Albert Owusu-Asare
 * Date : October 4 2015
 * An implementation of LoggiaEvent using Parse
 */


public class ParseLoggiaEvent extends ParseObject implements LoggiaEvent {
   private  List<ParseLoggiaUser> users;
   private List<ParseLoggiaUser> eventRepresentatives;

    public ParseLoggiaEvent(){
        this.users = new ArrayList<ParseLoggiaUser>();
        this.eventRepresentatives = new ArrayList<ParseLoggiaUser>();
    }
    @Override
    public String getEventName() {
        return this.getString(TableData.EventColumnNames.event_name.toString());
    }

    @Override
    public Date getEventDate() {
        return this.getDate(TableData.EventColumnNames.event_date.toString());
    }

    @Override
    public String getEventLocation() {
        return this.getString(TableData.EventColumnNames.event_location.toString());
    }

    @Override
    public String getEventImageUrl() {
        return this.getParseFile(TableData.EventColumnNames.event_image.toString()).getUrl();
    }

    @Override
    public long getNumEventViews() {
        return this.getLong(TableData.EventColumnNames.event_views.toString());
    }

    @Override
    public long getNumEventInvites() {
        return this.getLong(TableData.EventColumnNames.event_num_invites.toString());
    }

    @Override

    public List<ParseLoggiaUser> getEventUsersInvited() {

        ParseQuery<ParseLoggiaUser> query = ParseQuery.
                getQuery(TableData.TableNames.EVENT_INVITE.toString());
        query.whereEqualTo(TableData.EventInviteColumnNames.event.toString(), this);
        query.findInBackground(new FindCallback<ParseLoggiaUser>() {
            @Override
            public void done(List<ParseLoggiaUser> list, ParseException e) {
                users =  list;
            }
        });
        return  users;
    }

    @Override
    public List<ParseLoggiaUser> getEventRepresentatives() {
        ParseQuery<ParseLoggiaUser> query = ParseQuery.
                getQuery(TableData.TableNames.EVENT_EVENT_REP.toString());
        query.whereEqualTo(TableData.EventEventRepColumnNames.event.toString(), this);
        query.findInBackground(new FindCallback<ParseLoggiaUser>() {
            @Override
            public void done(List<ParseLoggiaUser> list, ParseException e) {
                eventRepresentatives = list;
            }
        });
        return eventRepresentatives;
    }
}
