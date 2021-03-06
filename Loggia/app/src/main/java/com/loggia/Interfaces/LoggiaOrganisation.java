package com.loggia.Interfaces;

import java.util.List;

/**
 * @author  Albert Owusu-Asare
 * Date : Oct 4 2015
 *
 * Encapsulates information about organisations and the different functions for an organisaton.
 * An organistion could have multiple administrators. An organisation can be created intially
 * by one of its members
 */
public interface LoggiaOrganisation {
    /**
     * @return a list of all the admins for this organisation.
     * the size of this list >=1
     */
    abstract public List<LoggiaUser> getAdministrators();

    /**
     * @return the name of this organisation.
     */
    abstract public String getOrganisationName();

    /**
     * @return a list of all the upcoming events affiliated to this organisation.
     */
    abstract public List<LoggiaEvent> getUpcomingEvents();

    /**
     * Adds a new administrator to this organisation.
     * @param admin
     */
    abstract public void addNewAdmin(LoggiaUser admin);

}
