package com.loggia.Utils;

import android.telephony.SmsManager;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by L7 on 10/25/15.
 */
public class SMStool {


    public SMStool(){

    }


    /**
     * TODO: Create
     * @param selectedContacts
     */
    public static void sendSMS(ArrayList<Contact_Object> selectedContacts)
    {
        int contactSize = selectedContacts.size()-1;

        SmsManager sm = SmsManager.getDefault();
        for(int i=0; i<contactSize; i++){
            try {
                String number = selectedContacts.get(i).contactNumber;
                /**
                 * TODO: Create the event=>message method
                 * **/
                String msg = " ";
                Log.v("SENT MESSAGE", number);
                sm.sendTextMessage(number, null, msg, null, null);
            }
            catch(Exception e)
            {
                Log.e("SMS ERROR: ", e.toString());
            }
        }
    }


}
