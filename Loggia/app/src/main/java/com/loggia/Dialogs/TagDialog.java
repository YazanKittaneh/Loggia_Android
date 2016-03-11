package com.loggia.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;

import com.loggia.R;
import com.loggia.Utils.LoggiaUtils;

import java.util.Collection;
import java.util.List;

/**
 * Created by yazan on 9/15/15.
 */
public class TagDialog extends DialogFragment{


    public interface DialogListener {
        void setFilterOption(int filterNumber);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final DialogListener mDialogListener = (DialogListener) getTargetFragment();
        final CharSequence eventTags[] = getResources().getStringArray(R.array.tag_names);
        final TextView mEventTag = (TextView) getActivity().findViewById(R.id.Create_Tag);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("What type of event ");
       // final  Collection<String> tags =   LoggiaUtils.initialCategoryMap.values();
        //final CharSequence [] tagsSeq = (CharSequence[]) tags.toArray();
       builder.setItems(R.array.tag_names, new DialogInterface.OnClickListener(){
      //  builder.setItems(tagsSeq, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mEventTag.setText(eventTags[which]);
                mDialogListener.setFilterOption(which);
            }
        });
        return builder.show();
    }





}
