package com.loggia.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.loggia.Friends.ContactAdapter;
import com.loggia.R;
import com.loggia.Utils.Contact_Object;
import com.loggia.Utils.FlowLayout;
import com.loggia.Utils.SMStool;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FriendsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriendsListFragment extends Fragment {


    private ArrayList<Contact_Object> list = new ArrayList<Contact_Object>();
    private ArrayList<Contact_Object> selectedContacts = new ArrayList<>();
    private ListView mListView;
    private NestedScrollView mScrollViewFilter;
    private Contacts_Adapter mContacts_Adapter;
    private Toolbar toolbar;
    ContactAdapter contactAdapter;
    String contactName;
    String phoneNumber;
    private FlowLayout mFlowLayoutFilter ;
    FloatingActionButton sendSMSButton;



    private ListView allContactList;


    public FriendsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param
     * @return A new instance of fragment FriendsListActivity.
     */
    // TODO: Rename and change types and number of parameters
    public static FriendsListFragment newInstance() {
        FriendsListFragment fragment = new FriendsListFragment();
        return fragment;
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View FriendsListFragmentView = inflater.inflate(R.layout.fragment_friends_list,
                container, false);

        mFlowLayoutFilter = (FlowLayout) FriendsListFragmentView.findViewById(R.id.flowLayout);
        mListView = (ListView) FriendsListFragmentView.findViewById(R.id.friendsListViewFilter);
        mScrollViewFilter = (NestedScrollView) FriendsListFragmentView.findViewById(R.id.scrollViewFilter);
        toolbar = (Toolbar) getActivity().findViewById(R.id.tool_bar);
        //toolbar = (Toolbar) FriendsListFragmentView.findViewById(R.id.tool_bar);
        sendSMSButton = (FloatingActionButton) FriendsListFragmentView.findViewById(R.id.Send);
        //allContactList = (ListView) FriendsListFragmentView.findViewById(R.id.friends_list);
        
         /*
            ON CLICK LISTENER, PUT ACTION HERE
         */
        //allContactList.setOnItemClickListener();



        /**
            Logic for reading content
            TODO: Refactor this into its own methods
         **/
        Cursor phonesNo = getActivity().getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        while (phonesNo.moveToNext()) {
            contactName = phonesNo.getString(phonesNo
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            phoneNumber = phonesNo.getString(phonesNo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            //ContactBean contactObj = new ContactBean(contactName, phoneNumber);
            //contactObj.setName(contactName);
            //contactObj.setPhoneNo(phoneNumber);
            //list.add(contactObj);
            Contact_Object contact_object = new Contact_Object();
            contact_object.contactName = contactName;
            contact_object.contactNumber = phoneNumber;
            list.add(contact_object);

            mContacts_Adapter = new Contacts_Adapter(list);
            mListView.setAdapter(mContacts_Adapter);
        }


        phonesNo.close();
        /*
            GET CONTACTS and put them in the list
         */

        //contactAdapter = new ContactAdapter(getActivity(), R.layout.contacts_row, list);

        // Load listview here..
        //allContactList.setAdapter(contactAdapter);
        mContacts_Adapter = new Contacts_Adapter(list);
        mListView.setAdapter(mContacts_Adapter);

        sendSMSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Get the selected tags from the mFlowLayoutFilter
                 */
                SMStool.sendSMS(selectedContacts);
            }});

        return FriendsListFragmentView;
    }


    /**************************
     *  Logic part of adding the fiter tags
     *  TODO: Refactor this method into a utili class
     *************************/
    public void addFilterTag() {
        final ArrayList<Contact_Object> arrFilterSelected = new ArrayList<>();

        mFlowLayoutFilter.removeAllViews();

        int length = list.size();
        boolean isSelected = false;
        for (int i = 0; i < length; i++) {
            Contact_Object fil = list.get(i);
            if (fil.mIsSelected) {
                isSelected = true;
                arrFilterSelected.add(fil);
            }
        }
        if (isSelected) {
            mScrollViewFilter.setVisibility(View.VISIBLE);
        } else {
            mScrollViewFilter.setVisibility(View.GONE);
        }
        int size = arrFilterSelected.size();
        LayoutInflater layoutInflater = (LayoutInflater)
                this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (int i = 0; i < size; i++) {
            View view = layoutInflater.inflate(R.layout.filter_tag_edit, null);

            TextView tv = (TextView) view.findViewById(R.id.tvTag);
            LinearLayout linClose = (LinearLayout) view.findViewById(R.id.linClose);
            final Contact_Object contact_object = arrFilterSelected.get(i);
            linClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //showToast(filter_object.name);


                    int innerSize = list.size();
                    for (int j = 0; j < innerSize; j++) {
                        Contact_Object mContact_Object = list.get(j);
                        if (mContact_Object.contactName.equalsIgnoreCase(contact_object.contactName)) {
                            mContact_Object.mIsSelected = false;

                        }
                    }
                    addFilterTag();
                    mContacts_Adapter.updateListView(list);
                }
            });


            tv.setText(contact_object.contactName);
            int color = ContextCompat.getColor(this.getActivity(), R.color.Transparent);

            View newView = view;
            newView.setBackgroundColor(color);

            FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);
            params.rightMargin = 10;
            params.topMargin = 5;
            params.leftMargin = 10;
            params.bottomMargin = 5;

            newView.setLayoutParams(params);

            mFlowLayoutFilter.addView(newView);
        }
    }


    /**************************
     *  Adapter class for the tag view
     *  TODO: Refactor this adapter into it's own class
     *************************/
    public class Contacts_Adapter extends BaseAdapter {
        ArrayList<Contact_Object> arrMenu;

        public Contacts_Adapter(ArrayList<Contact_Object> arrOptions) {
            this.arrMenu = arrOptions;
        }

        public void updateListView(ArrayList<Contact_Object> mArray) {
            this.arrMenu = mArray;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return this.arrMenu.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.filter_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.mTtvName = (TextView) convertView.findViewById(R.id.tvName);
                viewHolder.mTvSelected = (TextView) convertView.findViewById(R.id.tvSelected);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final Contact_Object mService_Object = arrMenu.get(position);
            viewHolder.mTtvName.setText(mService_Object.contactName);

            if (mService_Object.mIsSelected) {
                viewHolder.mTvSelected.setVisibility(View.VISIBLE);
            } else {
                viewHolder.mTvSelected.setVisibility(View.INVISIBLE);
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mService_Object.mIsSelected = !mService_Object.mIsSelected;
                    mScrollViewFilter.setVisibility(View.VISIBLE);
                    addFilterTag();
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }

        public class ViewHolder {
            TextView mTtvName, mTvSelected;

        }
    }


}



