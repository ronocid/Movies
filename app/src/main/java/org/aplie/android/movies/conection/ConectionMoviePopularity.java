package org.aplie.android.movies.conection;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.aplie.android.movies.R;
import org.aplie.android.movies.parser.PaserJsonMovies;
import org.aplie.android.movies.utils.Movie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ConectionMoviePopularity {
    private static final String LOG_TAG = ConectionMoviePopularity.class.getName();

    public static List<Movie> conectionServer(Context context){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        List<Movie> listMovies = null;

        try {
            URL url = new URL(buildUri(context));

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return listMovies;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return listMovies;
            }
            String movieJsonStr = buffer.toString();
            listMovies = PaserJsonMovies.parserJson(movieJsonStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return listMovies;
    }

    private static String buildUri(Context context) {
        String sort = "popularity.desc";
        String language = context.getResources().getString(R.string.language_movie);
        String languageImage = context.getResources().getString(R.string.language_image_movie);

        final String MOVIE_POPULARITY_BASE_URL ="http://api.themoviedb.org/3/discover/movie?";
        final String SORT_BY = "sort_by";
        final String LANGUAGE = "language";
        final String LANGUAGE_INCLUDE_IMAGE = "include_image_language";
        final String API_KEY = "api_key";

        Uri buildUri = Uri.parse(MOVIE_POPULARITY_BASE_URL).buildUpon()
                .appendQueryParameter(SORT_BY,sort)
                .appendQueryParameter(LANGUAGE,language)
                .appendQueryParameter(LANGUAGE_INCLUDE_IMAGE,languageImage)
                .appendQueryParameter(API_KEY,"4161f97e880e6707b97d9c44cb494cd7").build();
        return buildUri.toString();
    }
}
