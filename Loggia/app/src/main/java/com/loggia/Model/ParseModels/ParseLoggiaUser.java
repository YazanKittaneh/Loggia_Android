package com.loggia.Model.ParseModels;

import com.loggia.Interfaces.LoggiaEvent;
import com.loggia.Interfaces.LoggiaOrganisation;
import com.loggia.Interfaces.LoggiaUser;
import com.loggia.Utils.TableData;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Albert Owusu-Asare
 * Date : Octoer 4 2015
 * An implementation of LoggiaUser using Parse
 */
public class ParseLoggiaUser extends ParseUser implements LoggiaUser {
    List<ParseLoggiaOrg> userOrganisations;
    List<ParseLoggiaEvent> userUpcomingEvents;

    public ParseLoggiaUser(){
        this.userOrganisations = new ArrayList<ParseLoggiaOrg>();
        this.userUpcomingEvents = new ArrayList<ParseLoggiaEvent>();
    }

    @Override
    public String getFirstName() {
        return this.getString(TableData.UserColumnNames.firstName.toString());
    }

    @Override
    public String getLastName() {
        return this.getString(TableData.UserColumnNames.lastName.toString());
    }

    @Override
    public List<ParseLoggiaOrg> getOrganisations() {
        ParseQuery<ParseLoggiaOrg> query = ParseQuery.
                getQuery(TableData.TableNames.USER_ORGANISATION.toString());
        query.whereEqualTo(TableData.UserOrganisationColumnNames.user.toString(), this);
        query.findInBackground(new FindCallback<ParseLoggiaOrg>() {
            @Override
            public void done(List<ParseLoggiaOrg> list, ParseException e) {
                userOrganisations = list;
            }
        });
        return userOrganisations;
    }

    @Override
    public List<ParseLoggiaEvent> getUpcomingEvents() {
        ParseQuery<ParseLoggiaEvent> query = ParseQuery.
                getQuery(TableData.TableNames.EVENT_ATTENDEE.toString());
        query.whereEqualTo(TableData.UserOrganisationColumnNames.user.toString(), this);
        query.findInBackground(new FindCallback<ParseLoggiaEvent>() {
            @Override
            public void done(List<ParseLoggiaEvent> list, ParseException e) {
                userUpcomingEvents = list;
            }
        });
        return userUpcomingEvents;
    }

    @Override
    public List<String> getTagsList() {
        return null;
    }

    @Override
    public List<LoggiaUser> getConnectList() {
        return null;
    }

    @Override
    public void addNewConnection(LoggiaUser user) {


    }
}
