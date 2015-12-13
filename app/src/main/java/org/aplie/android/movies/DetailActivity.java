package org.aplie.android.movies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            DetailFragment df = new DetailFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movies_detail_container,df)
                    .commit();
        }
    }
}
