package org.aplie.android.movies.data;

import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.test.AndroidTestCase;

public class TestProvider extends AndroidTestCase {
    public void deleteAllRecordsFromProvider(){
        mContext.getContentResolver().delete(MoviesContract.MoviesEntry.CONTENT_URI,null,null);

        Cursor cursor = mContext.getContentResolver().query(
                MoviesContract.MoviesEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals("Error: Records not deleted from Movies table during delete", 0, cursor.getCount());
        cursor.close();
    }

    public void deleteAllRecordsFromDB() {
        MoviesDbHelper dbHelper = new MoviesDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(MoviesContract.MoviesEntry.TABLE_NAME, null, null);
        db.close();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deleteAllRecordsFromProvider();
    }

    public void testProviderRegistry(){
        PackageManager pm = mContext.getPackageManager();

        ComponentName componentName = new ComponentName(mContext.getPackageName(), MoviesProvider.class.getName());

        try {
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);

            assertEquals("Error: MovieProvider registered with authority: " + providerInfo.authority +
                            " instead of authority: " + MoviesContract.CONTENT_AUTHORITY,
                    providerInfo.authority, MoviesContract.CONTENT_AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
            assertTrue("Error: WeatherProvider not registered at " + mContext.getPackageName(), false);
        }
    }

    public void testGetType(){
        //content://com.example.android.movies/movies/
        String type = mContext.getContentResolver().getType(MoviesContract.MoviesEntry.CONTENT_URI);
        assertEquals("Error: the MoviesEntry CONTENT_URI should return MoviesEntry.CONTENT_TYPE", MoviesContract.MoviesEntry.CONTENT_TYPE,type);

        //content://com.example.android.movies/movies/Star Wars: Episode VII - The Force Awakens
        String titleTest = "Star Wars: Episode VII - The Force Awakens";
        type = mContext.getContentResolver().getType(MoviesContract.MoviesEntry.buildMovieTitle(titleTest));
        assertEquals("Error: the MoviesEntry CONTENT_URI should return MoviesEntry.CONTENT_TYPE", MoviesContract.MoviesEntry.CONTENT_ITEM_TYPE,type);
    }

    public void testBasicMovieQueries(){
        MoviesDbHelper dbHelper = new MoviesDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValues = TestUtilities.createStarWarsMovieValues();
        long movieRowId = TestUtilities.insertStarWarsMovie(mContext);

        Cursor movieCursor = mContext.getContentResolver().query(MoviesContract.MoviesEntry.CONTENT_URI, null, null, null, null);

        TestUtilities.validateCursor("testBasicMovieQueries, movie query", movieCursor, testValues);
        if ( Build.VERSION.SDK_INT >= 19 ) {
            assertEquals("Error: Location Query did not properly set NotificationUri",
                    movieCursor.getNotificationUri(), MoviesContract.MoviesEntry.CONTENT_URI);
        }
    }

    public void testUpdateLocation(){
        ContentValues testValues = TestUtilities.createStarWarsMovieValues();

        Uri movieUri = mContext.getContentResolver().insert(MoviesContract.MoviesEntry.CONTENT_URI, testValues);
        long movieRowId = ContentUris.parseId(movieUri);

        assertTrue(movieRowId != -1);

        ContentValues updatedValues = new ContentValues(testValues);
        updatedValues.put(MoviesContract.MoviesEntry._ID,movieRowId);
        updatedValues.put(MoviesContract.MoviesEntry.COLUMN_TITLE, "War Star");

        Cursor movieCursor = mContext.getContentResolver().query(MoviesContract.MoviesEntry.CONTENT_URI, null, null, null, null);

        TestUtilities.TestContentObserver tco = TestUtilities.getTestContentObserver();
        movieCursor.registerContentObserver(tco);

        int count = mContext.getContentResolver().update(MoviesContract.MoviesEntry.CONTENT_URI, updatedValues,
                MoviesContract.MoviesEntry._ID + "= ?",
                new String[]{Long.toString(movieRowId)});

        tco.waitForNotificationOrFail();

        movieCursor.unregisterContentObserver(tco);
        movieCursor.close();

        Cursor cursor = mContext.getContentResolver().query(MoviesContract.MoviesEntry.CONTENT_URI, null,
                MoviesContract.MoviesEntry._ID + " = " + movieRowId, null , null);

        TestUtilities.validateCursor("testUpdateLocation.  Error validating location entry update.",
                cursor, updatedValues);

        cursor.close();
    }

    public void testInsertReadProvier(){
        ContentValues testValues = TestUtilities.createStarWarsMovieValues();

        TestUtilities.TestContentObserver tco = TestUtilities.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(MoviesContract.MoviesEntry.CONTENT_URI, true, tco);
        Uri movieUri = mContext.getContentResolver().insert(MoviesContract.MoviesEntry.CONTENT_URI, testValues);

        tco.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(tco);

        long movieRowId = ContentUris.parseId(movieUri);

        assertTrue(movieRowId != -1);

        Cursor cursor = mContext.getContentResolver().query(MoviesContract.MoviesEntry.CONTENT_URI,
                null,null,null,null);

        TestUtilities.validateCursor("testInsertReadProvider. Error: validating MovieEntry", cursor, testValues);
    }

    public void testDeleteRecords(){
        testInsertReadProvier();

        TestUtilities.TestContentObserver movieObserver = TestUtilities.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(MoviesContract.MoviesEntry.CONTENT_URI, true, movieObserver);

        deleteAllRecordsFromProvider();

        movieObserver.waitForNotificationOrFail();

        mContext.getContentResolver().unregisterContentObserver(movieObserver);
    }

    static private final int BULK_INSERT_RECORDS_TO_INSERT = 10;
    static ContentValues[] createBulkInsertWeatherValues(){
        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];
        for(int i = 0; i<BULK_INSERT_RECORDS_TO_INSERT; i++){
            ContentValues movieValues = new ContentValues();
            movieValues.put(MoviesContract.MoviesEntry.COLUMN_POSTER_PATH, "\\/upT2I74D4qHXjzXERxLQZQRRNbX"+i+".jpg");
            movieValues.put(MoviesContract.MoviesEntry.COLUMN_OVERVIEW, "overview "+i);
            movieValues.put(MoviesContract.MoviesEntry.COLUMN_ORIGINAL_TITLE, "Star Wars: Episode "+i);
            movieValues.put(MoviesContract.MoviesEntry.COLUMN_TITLE, "Star Wars: Episode "+i);
            movieValues.put(MoviesContract.MoviesEntry.COLUMN_BACKDROP_PATH, "\\/njv65RTipNSTozFLuF85jL0bcQe"+i+".jpg");
            movieValues.put(MoviesContract.MoviesEntry.COLUMN_VOTE_AVERAGE, i);

            returnContentValues[i]= movieValues;
        }
        return returnContentValues;
    }

    public void testBulInsert(){
        ContentValues [] bulkInsertContentValues = createBulkInsertWeatherValues();

        TestUtilities.TestContentObserver movieObserver = TestUtilities.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(MoviesContract.MoviesEntry.CONTENT_URI, true, movieObserver);

        int insertCount = mContext.getContentResolver().bulkInsert(MoviesContract.MoviesEntry.CONTENT_URI,bulkInsertContentValues);

        movieObserver.waitForNotificationOrFail();

        mContext.getContentResolver().unregisterContentObserver(movieObserver);

        assertEquals(insertCount, BULK_INSERT_RECORDS_TO_INSERT);

        Cursor cursor = mContext.getContentResolver().query(MoviesContract.MoviesEntry.CONTENT_URI,
                null,null,null, MoviesContract.MoviesEntry._ID+" ASC");

        assertEquals(cursor.getCount(), BULK_INSERT_RECORDS_TO_INSERT);

        cursor.moveToFirst();
        for(int i = 0;i<BULK_INSERT_RECORDS_TO_INSERT;i++, cursor.moveToNext()){
            TestUtilities.validateCurrentRecord("testBulInsert. Error validating MovieEntry "+i, cursor, bulkInsertContentValues[i]);
        }
        cursor.close();
    }
}
