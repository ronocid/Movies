package org.aplie.android.movies;


import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.aplie.android.movies.conection.ConectionMoviePopularity;
import org.aplie.android.movies.data.MoviesContract;
import org.aplie.android.movies.utils.Movie;

import java.util.List;

public class MovieTask extends AsyncTask<Void,Void,Void>{
    private static final String LOG_TAG = MovieTask.class.getName();
    private final Context mContext;

    public MovieTask (Context context){
        mContext = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        List<Movie> listMovies = ConectionMoviePopularity.conectionServer(mContext);
        ContentValues[] cvArray = new ContentValues[listMovies.size()];
        for(int pos = 0;pos<listMovies.size();pos++){
            Movie movie = listMovies.get(pos);
            ContentValues movieValues = new ContentValues();
            movieValues.put(MoviesContract.MoviesEntry.COLUMN_TITLE,movie.getTitle());
            movieValues.put(MoviesContract.MoviesEntry.COLUMN_POSTER_PATH,movie.getPosterPath());
            movieValues.put(MoviesContract.MoviesEntry.COLUMN_BACKDROP_PATH,movie.getBackdropPath());
            movieValues.put(MoviesContract.MoviesEntry.COLUMN_ORIGINAL_TITLE,movie.getOriginaTitle());
            movieValues.put(MoviesContract.MoviesEntry.COLUMN_OVERVIEW,movie.getOverview());
            movieValues.put(MoviesContract.MoviesEntry.COLUMN_VOTE_AVERAGE,movie.getVoteAverage());
            cvArray[pos] = movieValues;
        }
        int inserted = mContext.getContentResolver().bulkInsert(MoviesContract.MoviesEntry.CONTENT_URI,cvArray);

        Log.d(LOG_TAG, "FetchWeatherTask Complete. " + inserted + " Inserted");

        return null;
    }
}
