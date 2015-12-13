package org.aplie.android.movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MoviesGridAdapter extends BaseAdapter{
    private Context mContext;

    private Integer[] mThumbIds = {
            R.mipmap.ic_launcher,R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,R.mipmap.ic_launcher, R.mipmap.ic_launcher
    };

    public MoviesGridAdapter(Context context){
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_grid_movies, parent, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.movies_grid_imageView);

        imageView.setAdjustViewBounds(true);
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/D6e8RJf2qUstnfkTslTXNTUAlT.jpg").into(imageView);
        return view;
    }
}
