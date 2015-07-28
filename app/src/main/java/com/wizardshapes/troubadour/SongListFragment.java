package com.wizardshapes.troubadour;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.util.Log;
import com.wizardshapes.troubadour.db.Song;
import com.wizardshapes.troubadour.db.TroubDAO;

import java.sql.SQLException;
import java.util.List;

/**
 * A list fragment representing a list of Songs. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link SongDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class SongListFragment extends ListFragment implements NewSongDialogFragment.NoticeDialogListener {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    private TroubDAO dao;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(String id);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }
    };

    public void onDialogPositiveClick(NewSongDialogFragment dialog){
        Song song = dao.createSong(dialog.title);
        //ArrayAdapter<Song> list = (ArrayAdapter<Song>)getListAdapter();
        //list.add(song);
        mCallbacks.onItemSelected(String.valueOf(song.getId()));
    }
    public void onDialogNegativeClick(NewSongDialogFragment dialog){
        dialog.getDialog().cancel();
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SongListFragment() {
    }

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

        List<Song> songs = dao.getAllSongs();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Song> adapter = new ArrayAdapter<Song>(this.getActivity(),
                android.R.layout.simple_list_item_1, songs);
        setListAdapter(adapter);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected
        dao.close();
        Song song = (Song)listView.getItemAtPosition(position);
        mCallbacks.onItemSelected(String.valueOf(song.getId()));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    public void showNewSongDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new NewSongDialogFragment();
        dialog.show(getFragmentManager(), "NoticeDialogFragment");
    }


    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_add:
//                Song song = dao.createSong("New Song");
//                //ArrayAdapter<Song> list = (ArrayAdapter<Song>)getListAdapter();
//                //list.add(song);
//                mCallbacks.onItemSelected(String.valueOf(song.getId()));
                showNewSongDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
