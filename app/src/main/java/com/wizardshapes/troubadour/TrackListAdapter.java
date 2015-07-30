package com.wizardshapes.troubadour;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wizardshapes.troubadour.db.Track;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zmerrill on 7/29/2015.
 */
public class TrackListAdapter extends BaseAdapter{
    List<Track> data;
    Activity activity;
    static LayoutInflater inflater = null;
    Resources res;


    public TrackListAdapter(Activity activity, List<Track> data, Resources res){
        this.activity = activity;
        this.data = data;
        this.res = res;
    }
    @Override
    public int getCount() {

        if(data.size()<=0)
            return 1;
        return data.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(convertView==null){
            inflater = (LayoutInflater)activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.track_row, null);
            TextView trackNameView = (TextView)vi.findViewById(R.id.trackNameText);
            final ImageButton recordButton = (ImageButton)vi.findViewById(R.id.buttonRecord);

            recordButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Track clicked = data.get(position);
                    clicked.setRecordEnabled(true);
                    recordButton.setImageResource(R.drawable.ic_adjust_red_24dp);
                    for(Track t : data){
                        if(!clicked.equals(t)){
                            t.setRecordEnabled(false);
                        }
                    }

                }
            });

            Track t = data.get(position);
            trackNameView.setText(t.getName());
        }
        return vi;
    }
}
