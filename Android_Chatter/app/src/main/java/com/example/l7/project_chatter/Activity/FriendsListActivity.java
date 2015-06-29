package com.example.l7.project_chatter.Activity;



import java.util.ArrayList;
        import java.util.Collections;
        import java.util.Comparator;

        import android.app.Activity;
        import android.database.Cursor;
        import android.os.Bundle;
        import android.provider.ContactsContract;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.AdapterView.OnItemClickListener;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.Toast;

import com.example.l7.project_chatter.Controllers.ContactAdapter;
import com.example.l7.project_chatter.Controllers.ContactBean;
import com.example.l7.project_chatter.R;

public class FriendsListActivity extends Activity implements OnItemClickListener {

    private ListView allContactList;
    EditText etSearchContact;
    private ArrayList<ContactBean> list = new ArrayList<ContactBean>();
    ContactAdapter contactAdapter;
    String contactName;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        etSearchContact = (EditText) findViewById(R.id.etserch);
        allContactList = (ListView) findViewById(R.id.list);


        /*
            ON CLICK LISTENER, PUT ACTION HERE
         */
        allContactList.setOnItemClickListener(this);


        /*
            GET CONTACTS
         */
        Cursor phonesNo = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,null, null);

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


        //This makes a new contactAdapter using the activity, the individual list layout, and the list of all contacts created above
        contactAdapter = new ContactAdapter(FriendsListActivity.this,R.layout.contacts_row, list);

        // Load listview here..
        allContactList.setAdapter(contactAdapter);


        /*
        // Sorting here
        if (null != list && list.size() != 0) {
            Collections.sort(list, new Comparator<ContactBean>() {

                @Override
                public int compare(ContactBean lhs, ContactBean rhs) {
                    return lhs.getName().compareTo(rhs.getName());
                }
            });
        } else {
            Toast.makeText(this, "No Contact Found!!!", Toast.LENGTH_SHORT).show();
        }
        */

        // listView.setFilterText("");

        etSearchContact.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                contactAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> listview, View v, int position,
                            long id) {
        ContactBean contactBean = (ContactBean) listview.getItemAtPosition(position);

        final String number = contactBean.getPhoneNo();
        final String name = contactBean.getName();
        Toast.makeText(this, name+"\n"+number, Toast.LENGTH_SHORT).show();
    }
}