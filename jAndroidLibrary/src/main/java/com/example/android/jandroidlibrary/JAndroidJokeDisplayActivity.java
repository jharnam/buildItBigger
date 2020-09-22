package com.example.android.jandroidlibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class JAndroidJokeDisplayActivity extends AppCompatActivity {
    public static String JOKE_KEY = "Joke key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_j_android_joke_display);

        TextView jokeTextView = findViewById(R.id.joke_text_view);
        Intent intent = getIntent();
        if (intent.hasExtra(JOKE_KEY)) {
            String joke = intent.getStringExtra(JOKE_KEY);
            if (joke != null && joke.length() != 0) {
                jokeTextView.setText(joke);
            }
            else {
                jokeTextView.setText(R.string.no_jokes_text);
            }
        }
    }
}