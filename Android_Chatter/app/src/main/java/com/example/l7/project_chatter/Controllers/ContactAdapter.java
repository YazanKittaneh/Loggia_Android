package com.example.l7.project_chatter.Controllers;

/**
 * Created by L7 on 6/28/15.
 */


import java.util.ArrayList;

        import android.app.Activity;
        import android.content.Context;
        import android.text.Html;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
        import android.widget.TextView;

import com.example.l7.project_chatter.R;

public class ContactAdapter extends ArrayAdapter<ContactBean> {



    private Activity activity;
    private ArrayList<ContactBean> contactValues;
    private ArrayList<ContactBean> contactOrignalValues;
    private int row;
    private ContactBean contactBean;

    public ContactAdapter(Activity act, int row, ArrayList<ContactBean> values) {
        super(act, row, values);

        this.activity = act;
        this.row = row;
        this.contactValues = values;
        this.contactOrignalValues = values;
    }

    public class ViewHolder {
        public TextView tvname, tvPhoneNo;
        public CheckBox checkBox;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(row, null);
            holder = new ViewHolder();
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if ((contactValues == null) || ((position + 1) > contactValues.size()))
            return view;

        contactBean = contactValues.get(position);

        holder.tvname = (TextView) view.findViewById(R.id.tvname);
        holder.tvPhoneNo = (TextView) view.findViewById(R.id.tvphone);
        holder.checkBox = (CheckBox) view.findViewById(R.id.checkBox);

        if (holder.tvname != null && null != contactBean.getName()
                && contactBean.getName().trim().length() > 0) {
            holder.tvname.setText(Html.fromHtml(contactBean.getName()));
        }
        if (holder.tvPhoneNo != null && null != contactBean.getPhoneNo()
                && contactBean.getPhoneNo().trim().length() > 0) {
            holder.tvPhoneNo.setText(Html.fromHtml(contactBean.getPhoneNo()));
        }
        return view;
    }

    @Override
    public ContactBean getItem(int position) {
        // TODO Auto-generated method stub
        return contactValues.get(position);
    }

    @Override
    public Filter getFilter() {
        // TODO Auto-generated method stub
        return new Filter() {

            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                // TODO Auto-generated method stub
                contactValues = (ArrayList<ContactBean>) results.values;
                notifyDataSetChanged();

            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                // TODO Auto-generated method stub
                FilterResults results = new FilterResults();
                // Holds the results of a filtering operation in values
                ArrayList<ContactBean> FilteredArrList = new ArrayList<ContactBean>();

                if (contactOrignalValues == null) {
                    contactOrignalValues = new ArrayList<ContactBean>(contactValues);
                    // saves data in OriginalValues
                }
                if (constraint == null || constraint.length() == 0) {
                    // set the Original result to return
                    results.count = contactOrignalValues.size();
                    results.values = contactOrignalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < contactOrignalValues.size(); i++) {
                        String data = contactOrignalValues.get(i).getName();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new ContactBean(contactOrignalValues.get(i)
                                    .getName(), contactOrignalValues.get(i).getPhoneNo()));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
    }

}
