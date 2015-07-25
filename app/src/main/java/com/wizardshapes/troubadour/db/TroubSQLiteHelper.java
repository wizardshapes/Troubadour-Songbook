package com.wizardshapes.troubadour.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by zmerrill on 7/23/2015.
 */
public class TroubSQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "troubadour.db";
    private static final int DATABASE_VERSION = 1;

    public TroubSQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        Log.w(TroubSQLiteHelper.class.getName(),
                "getWritableDatabase");
        return super.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        Log.w(TroubSQLiteHelper.class.getName(),
                "Creating database...\n" + Song.CREATE_SONGS_TABLE + "\n" + Track.CREATE_TRACKS_TABLE);
        database.execSQL(Song.CREATE_SONGS_TABLE);
        database.execSQL(Track.CREATE_TRACKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TroubSQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + Song.TABLE_SONGS);
        db.execSQL("DROP TABLE IF EXISTS " + Track.TABLE_TRACKS);
        onCreate(db);
    }
}
