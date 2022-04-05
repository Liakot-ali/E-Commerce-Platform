package com.team12;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;

public class ActivitySearch extends AppCompatActivity {


    EditText Search;
    RecyclerView List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        InitializeAll();


    }

    private void InitializeAll() {
        Search = findViewById(R.id.searchText);
        List = findViewById(R.id.searchRecycleView);
    }
}