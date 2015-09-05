package com.loggia.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.loggia.R;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.UUID;

public class AdminActivity extends AppCompatActivity {

    Button createEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        createEventButton = (Button) findViewById(R.id.create_events);

        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRandomEvents();
            }
        });


    }

    private void createRandomEvents() {
        String uuid=null;
        for (int i = 0; i < 10; i++) {
            uuid = UUID.randomUUID().toString();
            ParseObject mParseObject = new ParseObject("SGA");
            mParseObject.put("Name", uuid);
            uuid = UUID.randomUUID().toString();
            mParseObject.put("Date", uuid);
            mParseObject.put("StartTime", uuid);
            uuid = UUID.randomUUID().toString();
            mParseObject.put("EndTime",uuid);
            uuid = UUID.randomUUID().toString();
            mParseObject.put("Location", uuid);
            uuid = UUID.randomUUID().toString();
            mParseObject.put("Description",uuid);
            mParseObject.put("Image", R.drawable.stock_3);
            mParseObject.saveInBackground();
        }
        Toast.makeText(this,"Events Made", Toast.LENGTH_LONG);
    }

}

