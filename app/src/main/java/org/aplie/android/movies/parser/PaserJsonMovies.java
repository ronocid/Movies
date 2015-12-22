package org.aplie.android.movies.parser;

import org.aplie.android.movies.utils.Movie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PaserJsonMovies {

    public static final String JMK_RESULTS = "results";
    public static final String JMK_TITLE = "title";
    public static final String JMK_ORIGINAL_TITLE = "original_title";
    public static final String JMK_POSTER_PATH = "poster_path";
    public static final String JMK_OVERVIEW = "overview";
    public static final String JMK_BACKDROP_PATH = "backdrop_path";
    public static final String JMK_VOTE_AVERAGE = "vote_average";

    public static List<Movie> parserJson(String movieJsonStr) {
        List<Movie> listMovies = new ArrayList<>();
        try {
            JSONObject movieJson = new JSONObject(movieJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(JMK_RESULTS);
            for(int cont = 0;cont<movieArray.length();cont++){
                JSONObject movieObject = movieArray.getJSONObject(cont);
                String title = movieObject.getString(JMK_TITLE);
                String originalTitle = movieObject.getString(JMK_ORIGINAL_TITLE);
                String posterPath = movieObject.getString(JMK_POSTER_PATH);
                String overview = movieObject.getString(JMK_OVERVIEW);
                String backdrop = movieObject.getString(JMK_BACKDROP_PATH);
                double voteAverage = movieObject.getDouble(JMK_VOTE_AVERAGE);
                Movie movie = new Movie(title,originalTitle,posterPath,overview,backdrop,voteAverage);
                listMovies.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listMovies;
    }
}
