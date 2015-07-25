package com.wizardshapes.troubadour.db;

/**
 * Created by zmerrill on 7/23/2015.
 */
public class Track {
    public static final String TABLE_TRACKS = "tracks";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ID = "track_id";
    public static final String COLUMN_PATH = "path";
    public static final String COLUMN_NOTES = "notes";
    public static final String COLUMN_SONG = "song_id";

    protected static final String CREATE_TRACKS_TABLE = "create table "
            + TABLE_TRACKS + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_NOTES + " text, "
            + COLUMN_SONG + " integer not null, "
            + COLUMN_PATH + " text);";

    protected static final String[] allColumns = {COLUMN_ID,
            COLUMN_NAME,
            COLUMN_SONG,
            COLUMN_PATH,
            COLUMN_NOTES};

    private long id;
    private String name;
    private String path;
    private String notes;
    private int songId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int song_id) {
        this.songId = song_id;
    }

    @Override
    public String toString(){
        return getName();
    }


}
