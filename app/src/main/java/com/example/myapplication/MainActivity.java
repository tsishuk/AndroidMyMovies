package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.data.Movie;
import com.example.myapplication.utils.JSONUtils;
import com.example.myapplication.utils.NetworkUtils;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPosters;
    private MovieAdapter movieAdapter;
    private Switch switchSort;
    private TextView tvPopularity;
    private TextView tvTopRated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switchSort = findViewById(R.id.switchSort);
        recyclerViewPosters = findViewById(R.id.recyclerViewPosters);
        tvPopularity = findViewById(R.id.textViewPopularity);
        tvTopRated = findViewById(R.id.textViewTopRated);

        recyclerViewPosters.setLayoutManager(new GridLayoutManager(this, 2));
        movieAdapter = new MovieAdapter();
        movieAdapter.setOnPosterClickListener(new MovieAdapter.onPosterClickListener() {
            @Override
            public void onPosterClick(int position) {
                Toast.makeText(MainActivity.this, "position = " + position, Toast.LENGTH_SHORT).show();
            }
        });
        movieAdapter.setOnReachEndListener(new MovieAdapter.onReachEndListener() {
            @Override
            public void onReachEnd() {
                Toast.makeText(MainActivity.this, "End Reached", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerViewPosters.setAdapter(movieAdapter);


        switchSort.setChecked(true);
        switchSort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setMethodOfSort(b);
            }
        });
        switchSort.setChecked(false);

    }




    public void setPopularity(View view) {
        switchSort.setChecked(false);
    }

    public void setTopRated(View view) {
        switchSort.setChecked(true);
    }

    public void setMethodOfSort(boolean isChecked){
        int methodOfSort;
        if (isChecked) {
            methodOfSort = NetworkUtils.POPULARITY;
            tvTopRated.setTextColor(getResources().getColor(R.color.colorAccent));
            tvPopularity.setTextColor(getResources().getColor(R.color.white_color));
        }
        else {
            methodOfSort = NetworkUtils.TOP_RATED;
            tvTopRated.setTextColor(getResources().getColor(R.color.white_color));
            tvPopularity.setTextColor(getResources().getColor(R.color.colorAccent));
        }

        JSONObject jsonObject = NetworkUtils.getJSONFROMNetwork(methodOfSort, 2);
        ArrayList<Movie>movies = JSONUtils.getMoviesFromJSON(jsonObject);
        movieAdapter.setMovies(movies);
    }
}