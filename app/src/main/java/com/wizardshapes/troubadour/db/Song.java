package com.wizardshapes.troubadour.db;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zmerrill on 7/23/2015.
 */
public class Song implements Serializable {
    public static final String TABLE_SONGS = "songs";
    public static final String COLUMN_ID = "song_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_BPM = "bpm";
    public static final String COLUMN_SIG_UPPER = "signature_upper";
    public static final String COLUMN_SIG_LOWER = "signature_lower";
    public static final String COLUMN_PATH = "path";
    public static final String COLUMN_NOTES = "notes";

    protected static final String[] allColumns = {COLUMN_ID,
        COLUMN_TITLE,
        COLUMN_BPM,
        COLUMN_PATH,
        COLUMN_NOTES,
        COLUMN_SIG_LOWER,
        COLUMN_SIG_UPPER};

    protected static final String CREATE_SONGS_TABLE = "create table "
            + TABLE_SONGS + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_BPM + " integer, "
            + COLUMN_NOTES + " text, "
            + COLUMN_SIG_UPPER + " integer, "
            + COLUMN_SIG_LOWER + " integer, "
            + COLUMN_PATH + " text);";

    public static final int TRACK_COUNT = 4;

    private long id;
    private String title;
    private int bpm;
    private int signatureUpper;
    private int signatureLower;
    private String path;
    private String notes;
    private List<Track> tracks = new LinkedList<>();

    public long getId() {
        return id;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBpm() {
        return bpm;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }

    public int getSignatureUpper() {
        return signatureUpper;
    }

    public void setSignatureUpper(int signatureUpper) {
        this.signatureUpper = signatureUpper;
    }

    public int getSignatureLower() {
        return signatureLower;
    }

    public void setSignatureLower(int signatureLower) {
        this.signatureLower = signatureLower;
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

    @Override
    public String toString() {
        return getTitle();
    }
}
