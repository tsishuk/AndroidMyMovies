package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.utils.NetworkUtils;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //String url = NetworkUtils.buildURL(NetworkUtils.POPULARITY, 1).toString();
        JSONObject object = NetworkUtils.getJSONFROM(NetworkUtils.TOP_RATED, 3);
        if (object == null)
            Toast.makeText(this, "An ERROR Occured", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "OK OK OK", Toast.LENGTH_SHORT).show();
    }
}