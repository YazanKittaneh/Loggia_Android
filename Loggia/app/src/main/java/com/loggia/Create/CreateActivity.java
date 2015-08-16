package com.loggia.Create;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.loggia.Display.DisplayActivity;
import com.loggia.R;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.ByteArrayOutputStream;

public class CreateActivity extends AppCompatActivity {
    EditText mEventTime;
    EditText mEventDescription;
    EditText mEventLocation;
    ImageButton backdrop;



    Bitmap image;

    boolean imgLoaded = false;

    private static int RESULT_LOAD_IMAGE = 1;

    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);


        mEventTime = (EditText) findViewById(R.id.Display_Event_Time);
        mEventDescription = (EditText) findViewById(R.id.Display_Event_Description);
        mEventLocation = (EditText) findViewById(R.id.Display_Event_Location);
        backdrop = (ImageButton) findViewById(R.id.backdrop);


        backdrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });


        FloatingActionButton createButton = (FloatingActionButton) findViewById(R.id.create);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean clear = true;

                TextView[] fields = {mEventTime, mEventLocation, mEventDescription};
                String[] content = null;

                for (int i = 0; i < fields.length; i++) {
                    String mTextField = fields[i].getText().toString();
                    if (TextUtils.isEmpty(mTextField)) {
                        fields[i].setError("Field is empty!");
                        clear = false;
                        return;
                    }
                }

                if (clear) {
                    Log.i("Img LOAD: ", "" + imgLoaded);

                    if (!imgLoaded) {
                        image = BitmapFactory.decodeResource(getResources(), R.drawable.cheese_1);
                        Log.i("PLACEMENT IMG PUT: ", "True");

                    }

                    ParseObject mParseObject = new ParseObject("event");
                    //mParseObject.put("Name", );
                    mParseObject.put("Time", mEventLocation.getText());
                    mParseObject.put("Description", mEventDescription.getText());
                    mParseObject.put("Location", mEventLocation.getText());
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] data = stream.toByteArray();
                    ParseFile imageFile = new ParseFile("image.", data);
                    mParseObject.put("Image", imageFile);
                    mParseObject.saveInBackground();
                    startActivity(new Intent(context, DisplayActivity.class).putExtra("objectID", mParseObject.getObjectId()));
                    finish();
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create, menu);
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
}
