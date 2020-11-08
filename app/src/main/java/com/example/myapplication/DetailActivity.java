package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.data.MainViewModel;
import com.example.myapplication.data.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageViewBigPoster;
    private TextView textViewTitle;
    private TextView textViewOriginalTitle;
    private TextView textViewReleaseDate;
    private TextView textViewRating;
    private TextView textViewOverview;

    private int id;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imageViewBigPoster = findViewById(R.id.imageViewBigPoster);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewOriginalTitle = findViewById(R.id.textViewOriginalTitle);
        textViewReleaseDate = findViewById(R.id.textViewReleaseDate);
        textViewRating = findViewById(R.id.textViewRating);
        textViewOverview = findViewById(R.id.textViewOverview);

        Intent intent = getIntent();
        if ((intent != null) && (intent.hasExtra("id"))){
            id = intent.getIntExtra("id", -1);
        }
        else
            finish();

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        Movie movie = viewModel.getMovieById(id);
        Picasso.get().load(movie.getBigPosterPath()).into(imageViewBigPoster);
        textViewTitle.setText(movie.getTitle());
        textViewOriginalTitle.setText(movie.getOriginalTitle());
        textViewReleaseDate.setText(movie.getReleaseDate());
        textViewRating.setText(Double.toString(movie.getVoteAverage()));
        textViewOverview.setText(movie.getOverview());
    }

    public void onClickAddToFavourite(View view) {
    }

}