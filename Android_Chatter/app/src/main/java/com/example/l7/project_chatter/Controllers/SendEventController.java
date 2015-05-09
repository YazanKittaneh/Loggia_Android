package com.example.l7.project_chatter.Controllers;

import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by L7 on 3/1/15.
 */
public class SendEventController {

    public void sendMessage(EventObject event, String targetUser) throws JSONException {

        /*
        How is this working and how am I getting the user?
         */
        //Get user
        ParseQuery user = ParseUser.getQuery();
        user.whereEqualTo("username", targetUser);
        ParseQuery query = ParseInstallation.getQuery();
        query.whereMatchesQuery("user", user);
        //Get user

        //Prepare information
        JSONObject information = new JSONObject();
        information.put("alert", user.toString() + " has invited you to " + event.getEventName() + "!");
        information.put("target", user.toString());
        //information.put("uri", EventActivity());
        //information.put("user", );
        //information.put("picture", );
        information.put("ID", "Text");
        //Prepare information

        /*
        How do you send the object in particular?
         */

        //prepare push
        ParsePush push = new ParsePush();
        push.setQuery(query);
        push.setData(information);
        push.sendInBackground();
        //Log.i(recepient.getText().toString(), message.getText().toString());
    }
}
