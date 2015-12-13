package org.aplie.android.movies.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import java.util.HashSet;

public class TestDB extends AndroidTestCase{
    void deleteTheDatabase() {
        mContext.deleteDatabase(MoviesDbHelper.DATABASE_NAME);
    }

    public void setUp() {
        deleteTheDatabase();
    }

    public void testCreateDB() throws Throwable{
        final HashSet<String> tableNameHashSet = new HashSet<String>();
        tableNameHashSet.add(MoviesContract.MoviesEntry.TABLE_NAME);

        deleteTheDatabase();
        SQLiteDatabase db = new MoviesDbHelper(mContext).getWritableDatabase();

        assertEquals(true, db.isOpen());

        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly",cursor.moveToFirst());

        do{
            tableNameHashSet.remove(cursor.getString(0));
        }while(cursor.moveToNext());

        assertTrue("Error: Your database was created without movies entry tables", tableNameHashSet.isEmpty());

        cursor = db.rawQuery("PRAGMA table_info(" + MoviesContract.MoviesEntry.TABLE_NAME + ")", null);

        assertTrue("Error: This means that we were unable to query the database for table information.", cursor.moveToFirst());

        final HashSet<String> movieColumnHashSet = new HashSet<String>();
        movieColumnHashSet.add(MoviesContract.MoviesEntry._ID);
        movieColumnHashSet.add(MoviesContract.MoviesEntry.COLUMN_TITLE);
        movieColumnHashSet.add(MoviesContract.MoviesEntry.COLUMN_VOTE_AVERAGE);
        movieColumnHashSet.add(MoviesContract.MoviesEntry.COLUMN_POSTER_PATH);
        movieColumnHashSet.add(MoviesContract.MoviesEntry.COLUMN_OVERVIEW);
        movieColumnHashSet.add(MoviesContract.MoviesEntry.COLUMN_BACKDROP_PATH);
        movieColumnHashSet.add(MoviesContract.MoviesEntry.COLUMN_ORIGINAL_TITLE);

        int columnNameIndex = cursor.getColumnIndex("name");
        do {
            String columnName = cursor.getString(columnNameIndex);
            movieColumnHashSet.remove(columnName);
        } while(cursor.moveToNext());

        assertTrue("Error: The database doesn't contain all of the required location entry columns", movieColumnHashSet.isEmpty());
        db.close();
    }

    public void testMovieTable(){
        SQLiteDatabase db = new MoviesDbHelper(mContext).getWritableDatabase();

        ContentValues testValues = TestUtilities.createStarWarMovieValues();

        long movieId = db.insert(MoviesContract.MoviesEntry.TABLE_NAME, null, testValues);

        assertTrue("Error: no Insert de values en the table movie", movieId != -1);

        Cursor cursor = db.query(MoviesContract.MoviesEntry.TABLE_NAME,null,null,null,null,null,null);
        assertTrue("Error: No records returned from movie query", cursor.moveToFirst());

        TestUtilities.validateCurrentRecord("Error: Movie query validation failed", cursor, testValues);
        assertFalse("Error: More than one recordd returned from movie query",cursor.moveToNext());
        cursor.close();
        db.close();
    }
}
