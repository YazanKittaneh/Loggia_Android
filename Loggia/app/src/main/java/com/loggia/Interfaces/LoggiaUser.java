package com.loggia.Interfaces;

import java.util.List;

/**
 * @Author : Albert Owusu-Asare
 *
 * This interface describes the generic Loggia User and the functions(messages) we are interested in
 * when dealing with a typical user of the application.
 */
public interface LoggiaUser {
    /**
     * @return the first name of this user
     */
    String getFirstName();

    /**
     * @return the last name of this user
     */
    String getLastName();

    /**
     * @return a list of all the organisations this Loggia User belongs to.
     */
   <T extends LoggiaOrganisation>  List<T> getOrganisations();

    /**
     * @return the upcoming events for this Loggia User. Upcoming is defined for events >=
     * current time. These are events that
     */
    < T extends LoggiaEvent>  List<T> getUpcomingEvents();

    /**
     * @return the list of topics (trends) that this user cares about. eg. #music, #harris
     */
    List<String> getTagsList();

    /**
     * @return a list of connections (to other users) that this user has.
     */
    <T extends LoggiaEvent> List<T> getConnectList();

    /**
     * Adds a new connection to this user's connect list
     * @param user
     */
    void addNewConnection(LoggiaUser user);

}
