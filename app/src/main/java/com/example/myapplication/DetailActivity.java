package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.data.FavouriteMovie;
import com.example.myapplication.data.MainViewModel;
import com.example.myapplication.data.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageViewAddToFavourite;
    private ImageView imageViewBigPoster;
    private TextView textViewTitle;
    private TextView textViewOriginalTitle;
    private TextView textViewReleaseDate;
    private TextView textViewRating;
    private TextView textViewOverview;

    private int id;
    private MainViewModel viewModel;
    private Movie movie;
    private FavouriteMovie favouriteMovie;

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
        imageViewAddToFavourite = findViewById(R.id.imageViewAddToFavourite);

        Intent intent = getIntent();
        if ((intent != null) && (intent.hasExtra("id"))){
            id = intent.getIntExtra("id", -1);
        }
        else
            finish();

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        movie = viewModel.getMovieById(id);
        Picasso.get().load(movie.getBigPosterPath()).into(imageViewBigPoster);
        textViewTitle.setText(movie.getTitle());
        textViewOriginalTitle.setText(movie.getOriginalTitle());
        textViewReleaseDate.setText(movie.getReleaseDate());
        textViewRating.setText(Double.toString(movie.getVoteAverage()));
        textViewOverview.setText(movie.getOverview());

        setFavourite();
    }


    public void onClickChangeFavourite(View view) {
        if (favouriteMovie == null){
            viewModel.insertFavouriteMovie(new FavouriteMovie(movie));
            Toast.makeText(this, "Add object to favourite", Toast.LENGTH_SHORT).show();
            //imageViewAddToFavourite.set
        }
        else {
            viewModel.deleteFavouriteMovie(favouriteMovie);
            Toast.makeText(this, "Delete object from favourite", Toast.LENGTH_SHORT).show();
        }
        setFavourite();
    }

    private void setFavourite(){
        favouriteMovie = viewModel.getFavouriteMovieById(id);
        if (favouriteMovie == null){
            imageViewAddToFavourite.setImageResource(R.drawable.favourite_add_to);
        }
        else {
            imageViewAddToFavourite.setImageResource(R.drawable.favourite_remove);
        }
    }
}