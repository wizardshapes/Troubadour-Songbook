package com.wizardshapes.troubadour;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.wizardshapes.troubadour.db.Song;

/**
 * Created by zmerrill on 7/24/2015.
 */
public class EditSongDialogFragment extends DialogFragment {
    EditText titleField;
    EditText bpmField;
    EditText upperSigField;
    EditText lowerSigField;

    Song song;

    public static String ARG_SONG = "full_song_object";

    // Empty constructor required for DialogFragment
    public EditSongDialogFragment() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_edit_song, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view).setTitle("Edit song details...")
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // save changes
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditSongDialogFragment.this.getDialog().cancel();
                    }
                });

        Song song = (Song)getArguments().getSerializable(ARG_SONG);

        titleField = (EditText)view.findViewById(R.id.title);
        bpmField = (EditText)view.findViewById(R.id.bpm);
        upperSigField = (EditText)view.findViewById(R.id.sig_upper);
        lowerSigField = (EditText)view.findViewById(R.id.sig_lower);

        titleField.setText(song.getTitle());
        bpmField.setText(String.valueOf(song.getBpm()));
        upperSigField.setText(String.valueOf(song.getSignatureUpper()));
        lowerSigField.setText(String.valueOf(song.getSignatureLower()));


        return builder.create();
    }
}
