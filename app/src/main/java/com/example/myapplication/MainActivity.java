package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.data.Movie;
import com.example.myapplication.utils.JSONUtils;
import com.example.myapplication.utils.NetworkUtils;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JSONObject object = NetworkUtils.getJSONFROMNetwork(NetworkUtils.POPULARITY, 3);
        ArrayList<Movie> movies = JSONUtils.getMoviesFromJSON(object);
        for (Movie movie : movies){
            Log.d("MyResults", movie.getTitle()+"\n");
        }
    }
}