package com.loggia.Model.ParseModels;

import com.loggia.Interfaces.LoggiaEvent;
import com.loggia.Interfaces.LoggiaOrganisation;
import com.loggia.Interfaces.LoggiaUser;
import com.parse.Parse;
import com.parse.ParseObject;

import java.util.List;

/**
 * @author Albert Owusu-Asare
 * Date : October 4, 2015
 *
 * An implementation of Loggia Organisation using Parse
 */
public class ParseLoggiaOrg extends ParseObject implements LoggiaOrganisation {
    @Override
    public List<LoggiaUser> getAdministrators() {
        return null;
    }

    @Override
    public String getOrganisationName() {
        return null;
    }

    @Override
    public List<LoggiaEvent> getUpcomingEvents() {
        return null;
    }

    @Override
    public void addNewAdmin(LoggiaUser admin) {

    }
}
