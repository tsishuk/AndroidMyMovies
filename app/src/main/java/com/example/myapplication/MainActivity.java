package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.data.MainViewModel;
import com.example.myapplication.data.Movie;
import com.example.myapplication.utils.JSONUtils;
import com.example.myapplication.utils.NetworkUtils;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPosters;
    private MovieAdapter movieAdapter;
    private Switch switchSort;
    private TextView tvPopularity;
    private TextView tvTopRated;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switchSort = findViewById(R.id.switchSort);
        recyclerViewPosters = findViewById(R.id.recyclerViewPosters);
        tvPopularity = findViewById(R.id.textViewPopularity);
        tvTopRated = findViewById(R.id.textViewTopRated);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

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

        LiveData<List<Movie>> moviesFromLiveData = viewModel.getMovies();
        moviesFromLiveData.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieAdapter.setMovies(movies);
            }
        });
    }


    public void setPopularity(View view) {
        switchSort.setChecked(false);
    }

    public void setTopRated(View view) {
        switchSort.setChecked(true);
    }

    public void setMethodOfSort(boolean isChecked) {
        int methodOfSort;
        if (isChecked) {
            methodOfSort = NetworkUtils.POPULARITY;
            tvTopRated.setTextColor(getResources().getColor(R.color.colorAccent));
            tvPopularity.setTextColor(getResources().getColor(R.color.white_color));
        } else {
            methodOfSort = NetworkUtils.TOP_RATED;
            tvTopRated.setTextColor(getResources().getColor(R.color.white_color));
            tvPopularity.setTextColor(getResources().getColor(R.color.colorAccent));
        }
        downloadData(methodOfSort, 1);
    }

    private void downloadData(int methodOfSort, int page) {
        JSONObject jsonObject = NetworkUtils.getJSONFROMNetwork(methodOfSort, page);
        ArrayList<Movie> movies = JSONUtils.getMoviesFromJSON(jsonObject);
        if (movies != null && !movies.isEmpty()){
            viewModel.deleteAllMovies();
            for (Movie movie : movies){
                viewModel.insertMovie(movie);
            }
        }
    }

}