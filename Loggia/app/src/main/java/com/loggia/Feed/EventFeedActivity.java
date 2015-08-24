package com.loggia.Feed;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;


import com.loggia.Create.CreateActivity;
import com.loggia.Display.DisplayActivity;
import com.loggia.Helpers.StockImageRandomizer;
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
import android.os.Handler;

/**
 * TODO: Create organizational system for events
 * TODO: Extract image decoder into seperate Helper class
 * TODO: Pull events in an efficient manner ( only pull events not in the system)
 * TODO: Put create event button in the bottom right
 * TODO: add '+' sign on the fab button
 * TODO: Refactor code to make it more legable
 */


public class EventFeedActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private MaterialListView mListView;
    private SwipeRefreshLayout swipeLayout;
    StockImageRandomizer randomStock;

    private Handler handler = new Handler();
    public Context context;
   // private String[] week = {"Thursday", "Friday", "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday"};






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_feed);


        /* SETUP */
        context=this;

        mListView = (MaterialListView) findViewById(R.id.material_listview);
        FloatingActionButton create = (FloatingActionButton) findViewById(R.id.create);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setColorSchemeResources(R.color.ColorPrimary);
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

        mListView.getLayoutManager().offsetChildrenVertical(10);

        updateEvents();
        /* SETUP */


        /* LISTENERS */


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CreateActivity.class);
                startActivity(intent);
            }
        });


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



        swipeLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {

                    @Override
                    public void onRefresh() {
                        swipeLayout.setRefreshing(true);
                        mListView.clear();
                        updateEvents();
                        swipeLayout.setRefreshing(false);

                    }
        });
        /* LISTENERS */


        //findViewById(R.id.loadingPanel).setVisibility(View.GONE);


    }


    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public BitmapDrawable decodeSampledBitmapFromParse(Resources res, ParseObject mParseObject,
                                                       int reqWidth, int reqHeight) {


        randomStock = new StockImageRandomizer();
        int randomImage = randomStock.getRandomStockDrawable();
        ParseFile imgFile=null;
        byte[] file = new byte[0];


        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        imgFile = mParseObject.getParseFile("Image");

        if (imgFile != null) {
            try {
                file = imgFile.getData();
            } catch (ParseException e1) {
                e1.printStackTrace();
            }

            BitmapFactory.decodeByteArray(file, 0, file.length, options);
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;
            return new BitmapDrawable(res, BitmapFactory.decodeByteArray(file, 0, file.length, options));
        }
        else {

            BitmapFactory.decodeResource(res, randomImage, options);
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;

            return new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), randomStock.getRandomStockDrawable()));
        }

    }

    private void updateEvents()
    {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        final int width=dm.widthPixels;
        final int height=dm.heightPixels;


        ParseQuery < ParseObject > query = new ParseQuery<ParseObject>("event").addAscendingOrder("createdAt");
        //final ProgressDialog dialog = ProgressDialog.show(context, "Loading", "Please wait...", true);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> markers, com.parse.ParseException e) {
                if (e == null) {
                    Log.e("PARSE SUCESS", "I GUESS IT WORKS");


                    for (int i = 0; i < markers.size(); i++) {
                        ParseObject currentObject = markers.get(i);

                        BigImageCard card = new BigImageCard(context);
                        Log.i(i + " Item: ", currentObject.getString("Name"));
                        card.setTitle(currentObject.getString("Name"));
                        card.setDescription(currentObject.getString("Date") + "at " + currentObject.getString("Time"));


                        card.setDrawable(decodeSampledBitmapFromParse(getResources(), currentObject, width/4, height/4));
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
    }


}
