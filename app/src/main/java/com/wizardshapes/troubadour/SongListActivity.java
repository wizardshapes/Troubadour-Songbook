package com.wizardshapes.troubadour;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.wizardshapes.troubadour.db.Song;
import com.wizardshapes.troubadour.db.TroubDAO;

import java.sql.SQLException;


/**
 * An activity representing a list of Songs. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link SongDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link SongListFragment} and the item details
 * (if present) is a {@link SongDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link SongListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class SongListActivity extends Activity
        implements SongListFragment.Callbacks, NewSongDialogFragment.NoticeDialogListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private TroubDAO dao;

    public void onDialogPositiveClick(NewSongDialogFragment dialog){
        Song song = dao.createSong(dialog.title);
        //ArrayAdapter<Song> list = (ArrayAdapter<Song>)getListAdapter();
        //list.add(song);
        onItemSelected(String.valueOf(song.getId()));
    }
    public void onDialogNegativeClick(NewSongDialogFragment dialog){
        dialog.getDialog().cancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        dao = new TroubDAO(this);
        try {
            dao.open();
        }catch(SQLException e){
            String error = "Unable to open TroubDAO" + e.getStackTrace();
            Log.e(SongListFragment.class.getName(), error);
        }

        if (findViewById(R.id.song_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((SongListFragment) getFragmentManager()
                    .findFragmentById(R.id.song_list))
                    .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link SongListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(SongDetailFragment.ARG_ITEM_ID, id);
            SongDetailFragment fragment = new SongDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.song_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, SongDetailActivity.class);
            detailIntent.putExtra(SongDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
