package com.loggia.Model.ParseModels;


import com.loggia.Interfaces.LoggiaUser;
import com.loggia.Utils.TableData;
import com.parse.FindCallback;
import com.parse.ParseClassName;
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
@ParseClassName("ParseLoggiaUser")
public class ParseLoggiaUser extends ParseObject implements LoggiaUser {
    ParseUser parseUser;
    List<ParseLoggiaOrg> userOrganisations;
    List<ParseLoggiaEvent> userUpcomingEvents;

    public ParseLoggiaUser(){
        parseUser = ParseUser.getCurrentUser();
        this.userOrganisations = new ArrayList<ParseLoggiaOrg>();
        this.userUpcomingEvents = new ArrayList<ParseLoggiaEvent>();
    }

    public ParseLoggiaUser(ParseUser user){
        this.parseUser = user;
    }

    /** Gets first name of the ParseLoggiaUser **/
    @Override
    public String getFirstName() {
        return parseUser.getString(TableData.UserColumnNames.firstName.toString());
    }

    /** Gets last name of the ParseLoggiaUser **/
    @Override
    public String getLastName() {
        return parseUser.getString(TableData.UserColumnNames.lastName.toString());
    }

    /** Gets list of organizations that the ParseLoggiaUser is tied too **/
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

    /** Gets list of upcoming events the ParseLoggiaUser is going to attend **/
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
    @Override
    public boolean userActive(){
        return this.parseUser != null;
    }
}
