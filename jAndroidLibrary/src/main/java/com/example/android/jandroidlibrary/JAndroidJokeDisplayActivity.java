package com.example.android.jandroidlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class JAndroidJokeDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("JKM", "JKM: inside android joke display activity");
        setContentView(R.layout.activity_j_android_joke_display);
    }
}