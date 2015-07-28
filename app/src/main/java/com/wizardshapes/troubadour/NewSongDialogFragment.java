package com.wizardshapes.troubadour;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by zmerrill on 7/27/2015.
 */
public class NewSongDialogFragment extends DialogFragment {
    /* The activity that creates an instance of this dialog fragment must
    * implement this interface in order to receive event callbacks.
    * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(NewSongDialogFragment dialog);
        public void onDialogNegativeClick(NewSongDialogFragment dialog);
    }

    String title;
    EditText titleField;

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Build the dialog and set up the button click handlers
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.fragment_new_song, null);
        titleField = (EditText)rootView.findViewById(R.id.title);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(rootView).
        setMessage("New Song")
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the positive button event back to the host activity
                        if(titleField.getText().length() != 0) {
                            title = titleField.getText().toString();
                            mListener.onDialogPositiveClick(NewSongDialogFragment.this);
                        }else{
                            Toast.makeText(getActivity(), "Please enter a title.", Toast.LENGTH_SHORT);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        mListener.onDialogNegativeClick(NewSongDialogFragment.this);
                    }
                });
        return builder.create();
    }


}
