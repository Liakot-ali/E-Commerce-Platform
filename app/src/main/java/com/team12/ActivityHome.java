package com.team12;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class ActivityHome extends AppCompatActivity {

    RecyclerView productView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        InitializeAll();
    }
//------Out of main section-------------
    private void InitializeAll() {

        productView = findViewById(R.id.homeRecyclerView);

    }
}