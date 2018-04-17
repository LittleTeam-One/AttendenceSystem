package com.example.mrc.attendencesystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent loginIntent = new Intent(this ,LoginActivity.class);
        startActivity(loginIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent loginIntent = new Intent(this ,LoginActivity.class);
        startActivity(loginIntent);
    }
}
