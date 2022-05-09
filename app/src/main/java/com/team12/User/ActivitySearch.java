package com.team12.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;

import com.team12.R;

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