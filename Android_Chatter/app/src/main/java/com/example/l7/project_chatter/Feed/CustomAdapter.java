package com.example.l7.project_chatter.Feed;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.l7.project_chatter.R;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by L7 on 7/24/15.
 */
public class CustomAdapter extends BaseAdapter{

    Context context;
    List<ParseObject> data;

    private static LayoutInflater inflater=null;

    public CustomAdapter(EventFeedActivity mainActivity, List<ParseObject> eventList) {
        data=eventList;
        context= mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return this.data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.program_list, null);

        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);

        holder.tv.setText(data.get(position).getString("Name"));

        //holder.img.setImageResource(imageFile);
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked " + data.get(position).get("Name"), Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }

}
