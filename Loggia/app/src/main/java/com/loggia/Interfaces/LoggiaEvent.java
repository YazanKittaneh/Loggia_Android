package com.loggia.Interfaces;

import com.loggia.Model.ParseModels.ParseLoggiaUser;

import java.util.Date;
import java.util.List;

/**
 * @author Albert Owusu-Asare
 * Date : October 4 2015
 *
 * The LoggiaEvent interface captures all the functionality related to a LoggiaEvent. A Loggia
 * event can be created an individual(LoggiaUser) or an organisation (LoggiaOrganisation)
 */
public interface LoggiaEvent {
    /**
     * @return the name of this event
     */
    String getEventName();

    /**
     * @return the Start Date of commencement for this event
     */
    Date getEventStartDate();

    /**
     * @return the End Date of commencement for this event
     */
    Date getEventEndDate();

    /**
     * @return the location of this event
     */
    String getEventLocation();

    /**
     * @return the url of the image corresponding to this event
     */
    String getEventImageUrl();

    /**
     * @return the Description to this event
     */
    String getEventDescription();

    /**
     * @return the number of views for this event
     */
    long getNumEventViews();

    /**
     * @return the number of invites for this event
     */
    long getNumEventInvites();

    /**
     * TODO : check how to prevent parametized return type
     * @return the users that have been invited to this event
     */
    <T extends LoggiaUser> List<T> getEventUsersInvited();

    /**
     * @return a list of the users who are representatives for this event. If an organisation is
     * hosting this event, the representatives will be admins of this organisation.
     */
    <T extends LoggiaUser> List<T> getEventRepresentatives();

    /**
     * Saves the event to the db
     */
    void saveToDb();


}
