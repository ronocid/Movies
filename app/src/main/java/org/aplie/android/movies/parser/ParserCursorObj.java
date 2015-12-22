package org.aplie.android.movies.parser;


import android.database.Cursor;

import org.aplie.android.movies.data.MoviesContract;
import org.aplie.android.movies.utils.Movie;

public class ParserCursorObj {

    public static Movie cursorToMovie(Cursor cursor){
        Movie movie;
        int COL_MOVIE_ID = cursor.getColumnIndex(MoviesContract.MoviesEntry._ID);
        int COL_MOVIE_ORIGINAL_TITLE = cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_ORIGINAL_TITLE);
        int COL_MOVIE_TITLE = cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_TITLE);
        int COL_MOVIE_OVERVIEW = cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_OVERVIEW);
        int COL_MOVIE_POSTER_PATH = cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_POSTER_PATH);
        int COL_MOVIE_BACKDROP_PATH = cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_BACKDROP_PATH);
        int COL_MOVIE_VOTE_AVERAGE = cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_VOTE_AVERAGE);

        int id = cursor.getInt(COL_MOVIE_ID);
        String originalTitle = cursor.getString(COL_MOVIE_ORIGINAL_TITLE);
        String title = cursor.getString(COL_MOVIE_TITLE);
        String overview = cursor.getString(COL_MOVIE_OVERVIEW);
        String posterPath = cursor.getString(COL_MOVIE_POSTER_PATH);
        String backdropPath = cursor.getString(COL_MOVIE_BACKDROP_PATH);
        double voteAverage = cursor.getDouble(COL_MOVIE_VOTE_AVERAGE);

        movie = new Movie(id,title,originalTitle,posterPath,overview,backdropPath,voteAverage);
        return movie;
    }
}

