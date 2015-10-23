package com.loggia.Friends;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.loggia.Create.CreateFragment;
import com.loggia.R;
import com.parse.ParseObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FriendsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriendsListFragment extends Fragment {


    private ArrayList<ContactBean> list = new ArrayList<ContactBean>();
    ContactAdapter contactAdapter;
    String contactName;
    String phoneNumber;

    private ListView allContactList;


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
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    public FriendsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View FriendsListFragmentView = inflater.inflate(R.layout.fragment_friends_list, container, false);

        allContactList = (ListView) FriendsListFragmentView.findViewById(R.id.friends_list);


         /*
            ON CLICK LISTENER, PUT ACTION HERE
         */
        //allContactList.setOnItemClickListener();



        /*
            GET CONTACTS
         */
        Cursor phonesNo = getActivity().getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        while (phonesNo.moveToNext()) {
            contactName = phonesNo.getString(phonesNo
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            phoneNumber = phonesNo.getString(phonesNo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            ContactBean contactObj = new ContactBean(contactName, phoneNumber);

            contactObj.setName(contactName);
            contactObj.setPhoneNo(phoneNumber);
            list.add(contactObj);
        }
        phonesNo.close();
        /*
            GET CONTACTS and put them in the list
         */

        contactAdapter = new ContactAdapter(getActivity(), R.layout.contacts_row, list);

        // Load listview here..
        allContactList.setAdapter(contactAdapter);


        return FriendsListFragmentView;
    }

}



