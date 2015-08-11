package com.example.l7.project_chatter.Feed;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dexafree.materialList.cards.BigImageButtonsCard;
import com.dexafree.materialList.cards.BigImageCard;
import com.dexafree.materialList.controller.RecyclerItemClickListener;
import com.dexafree.materialList.model.CardItemView;
import com.dexafree.materialList.view.MaterialListView;
import com.example.l7.project_chatter.CreateEvent.CreateEventActivity;
import com.example.l7.project_chatter.DisplayEvent.DisplayEventActivity;
import com.example.l7.project_chatter.FriendsList.FriendsListActivity;
import com.example.l7.project_chatter.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class EventFeedActivity extends AppCompatActivity {


    Context context;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_feed);


        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);


        context=this;

        final MaterialListView mListView = (MaterialListView) findViewById(R.id.material_listview);


        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("event");
        //final ProgressDialog dialog = ProgressDialog.show(context, "Loading", "Please wait...", true);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> markers, com.parse.ParseException e) {
                if (e == null) {
                    /*
                    lv = (ListView) findViewById(R.id.listView);
                    */
                    //mListView.setAdapter(new CustomAdapter((EventViewActivity) context, markers));



                    for (int i=0; i<markers.size(); i++) {
                        BigImageCard card = new BigImageCard(context);
                        Log.i(i+" Item: ", String.valueOf(i));
                        card.setTitle(markers.get(i).getString("Name"));
                        card.setDescription(markers.get(i).getString("Description"));

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



                } else {
                    Log.e("DONE ERROR", e.toString());
                }
            }
        });

        mListView.addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(CardItemView view, int position) {
                String objectID = view.getTag().toString();
                Log.d("CLICK TEST: ", objectID);

                Intent intent = new Intent(view.getContext(), DisplayEventActivity.class);
                intent.putExtra("objectID", objectID);
                startActivity(intent);

                //startActivity(new Intent(EventFeedActivity.this, DisplayEventActivity.class).putExtra("ObjectID", objectId));
                //finish();
            }

            @Override
            public void onItemLongClick(CardItemView view, int position) {

            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        Intent intent = new Intent(this.getApplicationContext(), CreateEventActivity.class);
        startActivity(intent);


        return super.onOptionsItemSelected(item);
    }
}
