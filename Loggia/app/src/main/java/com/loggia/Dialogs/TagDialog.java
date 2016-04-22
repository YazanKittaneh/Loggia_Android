package com.loggia.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;

import com.loggia.Fragments.CreateFragment;
import com.loggia.R;
import com.loggia.Utils.Constants;
import com.loggia.Utils.LoggiaUtils;

import java.util.Collection;
import java.util.List;

/**
 * Created by yazan on 9/15/15.
 */
public class TagDialog extends DialogFragment{


    public interface DialogListener {
        void setFilterOption(int filterNumber);
        void setCommunity(int filterNumber);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final DialogListener mDialogListener = (DialogListener) getTargetFragment();
        final CharSequence eventTags[] = getResources().getStringArray(R.array.tag_names);
        final TextView mEventTag = (TextView) getActivity().findViewById(R.id.Create_Tag);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Collection<CharSequence> tags;
        int length;
        final CharSequence [] tagsSeq;

        switch(getTag()){
            case "create_fragment":
                builder.setTitle("What type of event ");
                tags =   LoggiaUtils.initialCategoryMap.values();
                length  = tags.size();
                tagsSeq =  tags.toArray(new CharSequence [length]);
                builder.setItems(tagsSeq, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Constants.community=(String) tagsSeq[which];
                        mDialogListener.setFilterOption(which);
                    }
                });
                break;
            case "splash_activity":
                builder.setTitle("Select Community");
                tags =   LoggiaUtils.initialCommunityMap.values();
                length  = tags.size();
                tagsSeq =  tags.toArray(new CharSequence [length]);
                builder.setItems(tagsSeq, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mEventTag.setText(tagsSeq[which]);
                        mDialogListener.setCommunity(which);
                    }
                });
                break;
            default:
                break;
        }

        return builder.show();
    }





}
