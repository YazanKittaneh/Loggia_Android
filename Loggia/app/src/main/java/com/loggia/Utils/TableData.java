package com.loggia.Utils;

/**
 * Created by albertowusu-asare on 9/23/15.
 */
public class TableData {

    public static enum TableNames {
        LOGGIA_USER,EVENT,EVENT_INVITE,EVENT_HOST,USER_CONNECT,EVENT_ATTENDANT,event,EVENT_EVENT_REP,
        USER_ORGANISATION,EVENT_ATTENDEE
    }
    public static enum UserColumnNames {
        firstName,lastName,phoneNumber,password,email,userType,username,objectId;
    }

    public static enum EventColumnNames {
        event_type("event_type"),
        event_name("event_name"),
        event_start_date("event_start_date"),
        event_end_date("event_end_date"),
        event_location("event_location"),
        event_description("event_descr"),
        event_rep_id("event_rep_id"),
        event_image("event_image"),
        event_views("event_views"),
        event_status("event_status"),
        event_num_invites("event_num_invites"),
        event_tag("event_tag"),
        event_approval_status("event_approval_status");


        private  String name;

        private EventColumnNames(String name){
            this.name = name;
        }

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
        USER_ID,UPDATE_FREQUENCY;
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
