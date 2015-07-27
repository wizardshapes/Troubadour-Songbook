package com.wizardshapes.troubadour.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zmerrill on 7/23/2015.
 */
public class TroubDAO {

    private SQLiteDatabase database;
    private TroubSQLiteHelper dbHelper;

    public  TroubDAO(Context context){
        dbHelper = new TroubSQLiteHelper(context);
    }

    public void open() throws SQLException{
        Log.w(TroubDAO.class.getName(), "Opening db.");
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public Song createSong(String title){
        ContentValues values = new ContentValues();
        values.put(Song.COLUMN_TITLE, title);
        values.put(Song.COLUMN_BPM, 120);
        values.put(Song.COLUMN_SIG_UPPER, 4);
        values.put(Song.COLUMN_SIG_LOWER, 4);

        long insertId = database.insert(Song.TABLE_SONGS, null, values);
        Cursor cursor = database.query(Song.TABLE_SONGS, Song.allColumns,
                Song.COLUMN_ID + " = " + insertId, null, null, null, null);

        cursor.moveToFirst();
        Song newSong = cursorToSong(cursor);
        cursor.close();

        for(int i = 1; i <= Song.TRACK_COUNT; i++){
            newSong.getTracks().add(createTrack(newSong, "Track " + i));
        }
        return newSong;
    }

    public void deleteSong(Song song){
        for(Track t : song.getTracks()){
            deleteDbObject(Track.TABLE_TRACKS, t.getId(), Track.COLUMN_ID);
        }

        deleteDbObject(Song.TABLE_SONGS, song.getId(), Song.COLUMN_ID);
    }

    public List<Song> getAllSongs(){
        List<Song> songs = new LinkedList<Song>();
        Cursor cursor = database.query(Song.TABLE_SONGS, Song.allColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Song song = cursorToSong(cursor);
            song.getTracks().addAll(getSongTracks(song));
            songs.add(song);
            cursor.moveToNext();
        }
        cursor.close();
        return songs;
    }

    public Song getSong(String songId){
        Cursor cursor = database.query(Song.TABLE_SONGS, Song.allColumns,
                Song.COLUMN_ID + " = " + songId
                , null, null, null, null);
        cursor.moveToFirst();
        Song song = cursorToSong(cursor);
        song.getTracks().addAll(getSongTracks(song));
        return song;
    }

    public void updateSong(Song song){
        ContentValues values = new ContentValues();
        values.put(Song.COLUMN_TITLE, song.getTitle());
        values.put(Song.COLUMN_BPM, song.getBpm());
        values.put(Song.COLUMN_SIG_UPPER, song.getSignatureUpper());
        values.put(Song.COLUMN_SIG_LOWER, song.getSignatureLower());
        values.put(Song.COLUMN_PATH, song.getPath());
        values.put(Song.COLUMN_NOTES, song.getNotes());


        database.update(Song.TABLE_SONGS, values, Song.COLUMN_ID + " = " + song.getId(), null);
    }

    public List<Track> getSongTracks(Song song){
        List<Track> tracks = new LinkedList<Track>();
        Cursor cursor = database.query(Track.TABLE_TRACKS, Track.allColumns,
                Track.COLUMN_SONG + " = " + song.getId()
                , null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Track track = cursorToTrack(cursor);
            tracks.add(track);
            cursor.moveToNext();
        }
        cursor.close();
        return tracks;
    }

    private void deleteDbObject(String tableName, long id, String keyColumnName){
        Log.w(TroubDAO.class.getName(), "Deleting from " + tableName
                + ". ID = " + id);
        database.delete(tableName, keyColumnName + " = " + id, null);
    }

    public Track createTrack(Song song, String name){
        ContentValues values = new ContentValues();
        values.put(Track.COLUMN_NAME, name);
        values.put(Track.COLUMN_SONG, song.getId());

        long insertId = database.insert(Track.TABLE_TRACKS, null, values);
        Cursor cursor = database.query(Track.TABLE_TRACKS, Track.allColumns,
                Track.COLUMN_ID + " = " + insertId, null, null, null, null);

        cursor.moveToFirst();
        Track newTrack = cursorToTrack(cursor);
        cursor.close();
        return newTrack;
    }

    private Song cursorToSong(Cursor cursor){
        Song song = new Song();
        song.setId(cursor.getLong(cursor.getColumnIndex(Song.COLUMN_ID)));
        song.setTitle(cursor.getString(cursor.getColumnIndex(Song.COLUMN_TITLE)));
        song.setBpm(cursor.getInt(cursor.getColumnIndex(Song.COLUMN_BPM)));
        song.setNotes(cursor.getString(cursor.getColumnIndex(Song.COLUMN_NOTES)));
        song.setSignatureLower(cursor.getInt(cursor.getColumnIndex(Song.COLUMN_SIG_LOWER)));
        song.setSignatureUpper(cursor.getInt(cursor.getColumnIndex(Song.COLUMN_SIG_UPPER)));
        song.setPath(cursor.getString(cursor.getColumnIndex(Song.COLUMN_PATH)));
        return song;
    }

    private Track cursorToTrack(Cursor cursor){
        Track track = new Track();
        track.setId(cursor.getInt(cursor.getColumnIndex(Track.COLUMN_ID)));
        track.setPath(cursor.getString(cursor.getColumnIndex(Track.COLUMN_PATH)));
        track.setName(cursor.getString(cursor.getColumnIndex(Track.COLUMN_NAME)));
        track.setNotes(cursor.getString(cursor.getColumnIndex(Track.COLUMN_NOTES)));
        track.setSongId(cursor.getInt(cursor.getColumnIndex(Track.COLUMN_SONG)));
        return track;
    }

}
