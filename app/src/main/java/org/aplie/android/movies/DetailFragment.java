package org.aplie.android.movies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class DetailFragment extends Fragment{
    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ImageView im = (ImageView) view.findViewById(R.id.imageView);
        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185/D6e8RJf2qUstnfkTslTXNTUAlT.jpg").into(im);
        return view;
    }
}
