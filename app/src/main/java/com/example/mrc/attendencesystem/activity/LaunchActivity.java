package com.example.mrc.attendencesystem.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mrc.attendencesystem.R;

public class LaunchActivity extends AppCompatActivity {
    public static String myInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Intent loginIntent = new Intent(this ,MainActivity.class);
        startActivity(loginIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent loginIntent = new Intent(this ,MainActivity.class);
        startActivity(loginIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}