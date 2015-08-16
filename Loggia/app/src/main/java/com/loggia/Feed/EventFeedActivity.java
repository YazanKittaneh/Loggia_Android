package com.loggia.Feed;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;


import com.loggia.Display.DisplayActivity;
import com.loggia.R;
import com.dexafree.materialList.cards.BigImageCard;
import com.dexafree.materialList.controller.RecyclerItemClickListener;
import com.dexafree.materialList.model.CardItemView;
import com.dexafree.materialList.view.MaterialListView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class EventFeedActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "cheese_name";
    Toolbar toolbar;
    Context context;
    String[] week = {"Thursday", "Friday", "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday"};

    MaterialListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_feed);


        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);


        context=this;

        mListView = (MaterialListView) findViewById(R.id.material_listview);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        */

        /*
        for (int i=0; i<week.length; i++)
        {
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(week[i]);
            //final ProgressDialog dialog = ProgressDialog.show(context, "Loading", "Please wait...", true);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> markers, com.parse.ParseException e) {
                    if (e == null) {
                        Log.e("PARSE SUCESS", "I GUESS IT WORKS");


                        for (int i = 0; i < markers.size(); i++) {
                            BigImageCard card = new BigImageCard(context);
                            Log.i(i + " Item: ", markers.get(i).getString("Name"));
                            card.setTitle(markers.get(i).getString("Name"));
                            card.setDescription(markers.get(i).getString("Location") + " at " + markers.get(i).getString("Time"));

                            ParseFile imgFile = markers.get(i).getParseFile("Image");
                            byte[] file = new byte[0];
                            if (imgFile != null) {
                                try {
                                    file = imgFile.getData();
                                } catch (ParseException e1) {
                                    e1.printStackTrace();
                                }

                                Bitmap image = BitmapFactory.decodeByteArray(file, 0, file.length);
                                Drawable mDrawable = new BitmapDrawable(getResources(), image);

                                card.setDrawable(mDrawable);
                            }
                            String test = markers.get(i).getObjectId();
                            card.setTag(test);

                            mListView.add(card);
                        }
                        Log.e("PARSE SUCESS", "WTF IT GOT OVER THE LOOP");


                    } else {
                        Log.e("DONE ERROR", "DOES NOT WORK");
                    }
                }
            });
            SmallImageCard date = new SmallImageCard(context);
            date.setTitle(week[i]);
            mListView.add(date);
        }
        */
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("event").addAscendingOrder("createdAt");
        //final ProgressDialog dialog = ProgressDialog.show(context, "Loading", "Please wait...", true);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> markers, com.parse.ParseException e) {
                if (e == null) {
                    Log.e("PARSE SUCESS", "I GUESS IT WORKS");


                    for (int i = 0; i < markers.size(); i++) {
                        BigImageCard card = new BigImageCard(context);
                        Log.i(i + " Item: ", markers.get(i).getString("Name"));
                        card.setTitle(markers.get(i).getString("Name"));
                        card.setDescription(markers.get(i).getString("Date") + " at " + markers.get(i).getString("Time"));

                        ParseFile imgFile = markers.get(i).getParseFile("Image");
                        byte[] file = new byte[0];
                        if (imgFile != null) {
                            try {
                                file = imgFile.getData();
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }

                            Bitmap image = BitmapFactory.decodeByteArray(file, 0, file.length);
                            Drawable mDrawable = new BitmapDrawable(getResources(), image);

                            card.setDrawable(mDrawable);
                        }
                        String test = markers.get(i).getObjectId();
                        card.setTag(test);

                        mListView.add(card);
                    }
                    Log.e("PARSE SUCESS", "WTF IT GOT OVER THE LOOP");


                } else {
                    Log.e("DONE ERROR", "DOES NOT WORK");
                }
            }
        });
        mListView.getLayoutManager().offsetChildrenVertical(10);


        mListView.addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(CardItemView view, int position) {
                if (view.getTag().toString() != null) {
                    String objectID = view.getTag().toString();
                    Log.d("CLICK TEST: ", objectID);

                    Intent intent = new Intent(view.getContext(), DisplayActivity.class);
                    intent.putExtra("objectID", objectID);
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(CardItemView view, int position) {

            }
        });
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

    }



}
