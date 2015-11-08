package com.loggia.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;

import com.loggia.R;

/**
 * Created by yazan on 9/15/15.
 */
public class TagDialog extends DialogFragment{


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final CharSequence eventTags[] = getResources().getStringArray(R.array.tag_names);
        final TextView mEventTag = (TextView) getActivity().findViewById(R.id.Create_Tag);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("What type of event ");
        builder.setItems(R.array.tag_names, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mEventTag.setText(eventTags[which]);
            }
        });
        return builder.show();

    }



}
