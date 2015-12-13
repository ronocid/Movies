package org.aplie.android.movies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MoviesDbHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;
    final String createMovies = "CREATE TABLE "+ MoviesContract.MoviesEntry.TABLE_NAME+" ("+
            MoviesContract.MoviesEntry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            MoviesContract.MoviesEntry.COLUMN_POSTER_PATH +" TEXT, " +
            MoviesContract.MoviesEntry.COLUMN_OVERVIEW +" TEXT, " +
            MoviesContract.MoviesEntry.COLUMN_ORIGINAL_TITLE +" TEXT, " +
            MoviesContract.MoviesEntry.COLUMN_TITLE +" TEXT, " +
            MoviesContract.MoviesEntry.COLUMN_BACKDROP_PATH +" TEXT, " +
            MoviesContract.MoviesEntry.COLUMN_VOTE_AVERAGE +" REAL NOT NULL )";

    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createMovies);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.MoviesEntry.TABLE_NAME);
        onCreate(db);
    }
}
