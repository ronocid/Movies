package org.aplie.android.movies.data;

import android.content.ContentValues;
import android.content.Context;

import org.aplie.android.movies.utils.Movie;

import java.util.List;

public class MovieUtilsProvider {
    public int bulkInsertMovies(Context context, List<Movie> listMovies){
        ContentValues[] cvArray = new ContentValues[listMovies.size()];
        for(int cont=0;cont<listMovies.size();cont++){
            Movie movie = listMovies.get(cont);
            ContentValues movieValues = new ContentValues();

            movieValues.put(MoviesContract.MoviesEntry.COLUMN_TITLE,movie.getTitle());
            movieValues.put(MoviesContract.MoviesEntry.COLUMN_POSTER_PATH,movie.getPosterPath());
            movieValues.put(MoviesContract.MoviesEntry.COLUMN_BACKDROP_PATH,movie.getBackdropPath());
            movieValues.put(MoviesContract.MoviesEntry.COLUMN_ORIGINAL_TITLE,movie.getOriginaTitle());
            movieValues.put(MoviesContract.MoviesEntry.COLUMN_OVERVIEW,movie.getOverview());
            movieValues.put(MoviesContract.MoviesEntry.COLUMN_VOTE_AVERAGE,movie.getVoteAverage());

            cvArray[cont] = movieValues;
        }
        return context.getContentResolver().bulkInsert(MoviesContract.MoviesEntry.CONTENT_URI,cvArray);
    }
}
