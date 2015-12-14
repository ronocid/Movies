package org.aplie.android.movies.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import org.aplie.android.movies.utils.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roberto on 14/12/2015.
 */
public class TestUtilsProvider extends AndroidTestCase{
    public void deleteAllRecordsFromDB() {
        MoviesDbHelper dbHelper = new MoviesDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(MoviesContract.MoviesEntry.TABLE_NAME, null, null);
        db.close();
    }

    static private final int BULK_INSERT_RECORDS_TO_INSERT = 10;
    public List<Movie> createListMoviesValue(){
        List<Movie> listMovies = new ArrayList<>();
        for(int i =0; i<BULK_INSERT_RECORDS_TO_INSERT;i++){
            String title = "title "+i;
            String originalTitle = "original Title "+i;
            String posterPath = "poster Path "+i;
            String overview = "overview "+i;
            String backdrop = "backdrop "+i;
            double voteAverage = i;
            Movie movie = new Movie(title,originalTitle,posterPath,overview,backdrop,voteAverage);
            listMovies.add(movie);
        }
        return  listMovies;
    }

    public void testBulkInsertMovies(){
        deleteAllRecordsFromDB();
        List<Movie> listMovies = createListMoviesValue();

        MovieUtilsProvider mup = new MovieUtilsProvider();
        int insertCount = mup.bulkInsertMovies(mContext,listMovies);

        assertEquals(insertCount, BULK_INSERT_RECORDS_TO_INSERT);

        Cursor cursor = mContext.getContentResolver().query(MoviesContract.MoviesEntry.CONTENT_URI,
                null,null,null, MoviesContract.MoviesEntry._ID+" ASC");

        assertEquals(cursor.getCount(), BULK_INSERT_RECORDS_TO_INSERT);

        cursor.moveToFirst();
        for(int i = 0;i<BULK_INSERT_RECORDS_TO_INSERT;i++, cursor.moveToNext()){
            validateCurrentRecord("testBulInsert. Error validating MovieEntry " + i, cursor, listMovies.get(i));
        }
        cursor.close();
    }

    private void validateCurrentRecord(String error, Cursor valueCursor, Movie expectedValue) {
        int indexTitle = valueCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_TITLE);
        int indexOriginalTitle = valueCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_ORIGINAL_TITLE);
        int indexOverview = valueCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_OVERVIEW);
        int indexPosterPath = valueCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_POSTER_PATH);
        int indexBackdrop = valueCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_BACKDROP_PATH);
        int indexVoteAverage = valueCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_VOTE_AVERAGE);

        assertFalse("Column '"+MoviesContract.MoviesEntry.COLUMN_TITLE+"' not found. " + error, indexTitle == -1);
        assertFalse("Column '"+MoviesContract.MoviesEntry.COLUMN_ORIGINAL_TITLE+"' not found. " + error, indexOriginalTitle == -1);
        assertFalse("Column '"+MoviesContract.MoviesEntry.COLUMN_OVERVIEW+"' not found. " + error,indexOverview  == -1);
        assertFalse("Column '"+MoviesContract.MoviesEntry.COLUMN_POSTER_PATH+"' not found. " + error, indexPosterPath == -1);
        assertFalse("Column '"+MoviesContract.MoviesEntry.COLUMN_BACKDROP_PATH+"' not found. " + error,indexBackdrop  == -1);
        assertFalse("Column '"+MoviesContract.MoviesEntry.COLUMN_VOTE_AVERAGE+"' not found. " + error, indexVoteAverage == -1);

        assertEquals("Value '" + valueCursor.getString(valueCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_TITLE)) +
                "' did not match the expected value '" +
                expectedValue.getTitle() + "'. " + error, expectedValue.getTitle(), valueCursor.getString(indexTitle));
        assertEquals("Value '" + valueCursor.getString(valueCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_ORIGINAL_TITLE)) +
                "' did not match the expected value '" +
                expectedValue.getOriginaTitle() + "'. " + error, expectedValue.getOriginaTitle(), valueCursor.getString(indexOriginalTitle));
        assertEquals("Value '" + valueCursor.getString(valueCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_OVERVIEW)) +
                "' did not match the expected value '" +
                expectedValue.getOverview() + "'. " + error, expectedValue.getOverview(), valueCursor.getString(indexOverview));
        assertEquals("Value '" + valueCursor.getString(valueCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_POSTER_PATH)) +
                "' did not match the expected value '" +
                expectedValue.getPosterPath() + "'. " + error, expectedValue.getPosterPath(), valueCursor.getString(indexPosterPath));
        assertEquals("Value '" + valueCursor.getString(valueCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_BACKDROP_PATH)) +
                "' did not match the expected value '" +
                expectedValue.getBackdropPath() + "'. " + error, expectedValue.getBackdropPath(), valueCursor.getString(indexBackdrop));
        assertEquals("Value '" + valueCursor.getString(valueCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_VOTE_AVERAGE)) +
                "' did not match the expected value '" +
                expectedValue.getVoteAverage() + "'. " + error, expectedValue.getVoteAverage(), valueCursor.getDouble(indexVoteAverage));
    }
}
