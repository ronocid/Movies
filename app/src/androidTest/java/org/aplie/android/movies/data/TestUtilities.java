package org.aplie.android.movies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.test.AndroidTestCase;

import org.aplie.android.movies.utils.PollingCheck;

import java.util.Map;
import java.util.Set;

public class TestUtilities extends AndroidTestCase {
    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }

    public static ContentValues createStarWarsMovieValues() {
        ContentValues testValues = new ContentValues();
        testValues.put(MoviesContract.MoviesEntry.COLUMN_POSTER_PATH, "\\/upT2I74D4qHXjzXERxLQZQRRNbX.jpg");
        testValues.put(MoviesContract.MoviesEntry.COLUMN_OVERVIEW, "Séptima entrega de la saga Star Wars. Fue confirmada en octubre de 2012, cuando The Walt Disney Company compró LucasFilms por 4.000 millones de dólares. El guionista de 'El imperio contraataca', Lawrence Kasdan, repite en esta octava entrega de la franquicia intergaláctica más conocida en la historia del cine con Han Solo, Leia y Luke Skywalker a la cabeza. 'La guerra de las galaxias: Episodio VIII' se sitúa en la Era de la Nueva República. Veinte años después de haber terminado la historia de la trilogía original, los grupos políticos siguen intentando reconstruir un gobierno con el resurgir de la Nueva República. La fuerza también siente el cambio y los Maestros Jedi saben que es el momento de regresar.");
        testValues.put(MoviesContract.MoviesEntry.COLUMN_ORIGINAL_TITLE, "Star Wars: Episode VII - The Force Awakens");
        testValues.put(MoviesContract.MoviesEntry.COLUMN_TITLE, "Star Wars. Episode VII: El despertar de la Fuerza");
        testValues.put(MoviesContract.MoviesEntry.COLUMN_BACKDROP_PATH, "\\/njv65RTipNSTozFLuF85jL0bcQe.jpg");
        testValues.put(MoviesContract.MoviesEntry.COLUMN_VOTE_AVERAGE, 7.11);

        return testValues;
    }

    public static long insertStarWarsMovie(Context mContext) {
        MoviesDbHelper dbHelper = new MoviesDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValue = TestUtilities.createStarWarsMovieValues();

        long movieRowId;
        movieRowId = db.insert(MoviesContract.MoviesEntry.TABLE_NAME,null,testValue);

        assertTrue("Error: Failure to insert Star Wars Movie Values", movieRowId != -1);
        return movieRowId;
    }

    static class TestContentObserver extends ContentObserver {
        final HandlerThread mHT;
        boolean mContentChanged;

        static TestContentObserver getTestContentObserver() {
            HandlerThread ht = new HandlerThread("ContentObserverThread");
            ht.start();
            return new TestContentObserver(ht);
        }

        private TestContentObserver(HandlerThread ht) {
            super(new Handler(ht.getLooper()));
            mHT = ht;
        }

        // On earlier versions of Android, this onChange method is called
        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            mContentChanged = true;
        }

        public void waitForNotificationOrFail() {
            // Note: The PollingCheck class is taken from the Android CTS (Compatibility Test Suite).
            // It's useful to look at the Android CTS source for ideas on how to test your Android
            // applications.  The reason that PollingCheck works is that, by default, the JUnit
            // testing framework is not running on the main Android application thread.
            new PollingCheck(5000) {
                @Override
                protected boolean check() {
                    return mContentChanged;
                }
            }.run();
            mHT.quit();
        }
    }

    static TestContentObserver getTestContentObserver() {
        return TestContentObserver.getTestContentObserver();
    }
}
