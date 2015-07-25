package com.wizardshapes.troubadour;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.wizardshapes.troubadour.db.Song;
import com.wizardshapes.troubadour.db.TroubDAO;

import java.sql.SQLException;

/**
 * A fragment representing a single Song detail screen.
 * This fragment is either contained in a {@link SongListActivity}
 * in two-pane mode (on tablets) or a {@link SongDetailActivity}
 * on handsets.
 */
public class SongDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    EditText titleView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SongDetailFragment() {
    }

    private Song song;
    private TroubDAO dao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = new TroubDAO(this.getActivity());
        try {
            dao.open();
        }catch(SQLException e){
            String error = "Unable to open TroubDAO" + e.getStackTrace();
            Log.e(SongListFragment.class.getName(), error);
        }
        System.err.println(this.getActivity() + " Is the act");

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            song = dao.getSong(getArguments().getString(ARG_ITEM_ID));
        }

        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_song_detail, container, false);
        titleView = (EditText)rootView.findViewById(R.id.song_detail);

        titleView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                song.setTitle(s.toString());
                dao.updateSong(song);
                getActivity().setTitle(s.toString());
            }
        });


        // Show the dummy content as text in a TextView.
        if (song != null) {
            this.getActivity().setTitle(song.getTitle());
            titleView.setText(song.getTitle());
        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail_options, menu);
        Log.v(SongListFragment.class.getName(), "Inflating menu");
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_edit:
//                AlertDialog alertDialog = new AlertDialog.Builder(this.getActivity()).create();
//                alertDialog.setTitle("Edit song...");
//                alertDialog.setMessage("Alert message to be shown");
//                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                alertDialog.show();
                FragmentManager manager = getFragmentManager();
                Bundle arguments = new Bundle();
                arguments.putSerializable(EditSongDialogFragment.ARG_SONG, song);

                EditSongDialogFragment frag = (EditSongDialogFragment)manager.findFragmentByTag("fragment_edit_name");
                if (frag != null) {
                    manager.beginTransaction().remove(frag).commit();
                }else{
                    frag = new EditSongDialogFragment();
                }
                frag.setArguments(arguments);
//                getFragmentManager().beginTransaction()
//                        .add(frag, "fragment_edit_song")
//                        .commit();

                frag.show(manager, "fragment_edit_song");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
