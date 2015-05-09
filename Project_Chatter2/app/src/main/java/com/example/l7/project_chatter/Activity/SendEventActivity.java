package com.example.l7.project_chatter.Activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.l7.project_chatter.R;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;


public class SendEventActivity extends ActionBarActivity {

    EditText message;
    EditText recepient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_event);
        message = (EditText) findViewById(R.id.editText);
        recepient = (EditText) findViewById(R.id.editText2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_send_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View v) throws JSONException {
        JSONObject parseNotificationObject = new JSONObject();
        parseNotificationObject.put("EventObjectID", "JXaAFApgQK");

        ParseQuery user = ParseUser.getQuery();
        user.whereEqualTo("username", recepient.getText().toString());
        ParseQuery query = ParseInstallation.getQuery();
        query.whereMatchesQuery("user", user);
        ParsePush push = new ParsePush();
        push.setQuery(query);
        //push.setChannel("Chatters");
        push.setMessage(message.getText().toString());
        push.setData(parseNotificationObject);
        push.sendInBackground();
        Log.i(recepient.getText().toString(), message.getText().toString());
    }

}
