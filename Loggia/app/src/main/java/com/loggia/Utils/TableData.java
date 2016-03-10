package com.loggia.Utils;

/**
 * Created by albertowusu-asare on 9/23/15.
 * A way of having consistant variables for tables, colomns, etc
 */
public class TableData {

    /** Names of tables in the backend **/
    public enum TableNames {
        LOGGIA_USER,
        EVENT,
        EVENT_INVITE,
        EVENT_HOST,
        USER_CONNECT,
        EVENT_ATTENDANT,
        event,
        EVENT_EVENT_REP,
        USER_ORGANISATION,
        EVENT_ATTENDEE,
        COUNTER
    }

    /** Column names for the User Table **/
    public enum UserColumnNames {
        firstName,
        lastName,
        phoneNumber,
        password,
        email,
        userType,
        username,
        objectId
    }

    /** Column names for the Event Table **/
    public enum EventColumnNames {
        event_type("Title"),
        event_name("Title"),
        event_start_date("Start"),
        event_end_date("End"),
        event_location("Where"),
        event_description("Description"),
        event_rep_id("event_rep_id"),
        event_image("Title"),
        event_views("Title"),
        event_status("Title"),
        event_num_invites("Title"),
        event_tag("Title"),
        event_approval_status("Title");


        private  String name;

        private EventColumnNames(String name){
            this.name = name;
        }

        /**  **/
        public boolean equalsName(String otherName) {
            return (otherName == null) ? false : name.equals(otherName);
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    public enum EventInviteColumnNames {
        EVENT_INVITE_ID,EVENT_INVITED_ID,EVENT_INVITEE_ID,EVENT_ID,EVENT_INVITE_FROM,
        EVENT_INVITE_TO, event;
    }

    public enum EventHostColumnNames{
        USER_ID,
        UPDATE_FREQUENCY;
    }
    public enum CounterColumnNames{
        counter_name,
        counter_frequency,

    }

    public enum UserConnectColumNames {
        FROM,TO,CONNECT_DATE;
    }

    public enum EventAttendantColumnNames{
        EVENT, USER
    }


    public enum EventEventRepColumnNames{
        event,event_rep
    }

    public enum UserOrganisationColumnNames{
        user,organisation
    }

    public enum EventAttendeeColumnNames{
        event, attendee
    }

}
