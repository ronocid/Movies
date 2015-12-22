package org.aplie.android.movies;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.aplie.android.movies.parser.ParserCursorObj;
import org.aplie.android.movies.utils.Movie;

public class MoviesGridAdapter extends CursorAdapter {
    private Context mContext;

    public MoviesGridAdapter(Context context, Cursor c, int flags){
        super(context,c,flags);
        this.mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grid_movies,parent,false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Movie movie = ParserCursorObj.cursorToMovie(cursor);

        ImageView imageView = (ImageView) view.findViewById(R.id.movies_grid_imageView);

        imageView.setAdjustViewBounds(true);
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185" + movie.getPosterPath()).into(imageView);
    }
}
