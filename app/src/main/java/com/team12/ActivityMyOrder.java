package com.team12;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

public class ActivityMyOrder extends AppCompatActivity {
    Toolbar toolbar;
    TextView toolbarText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        InitializeAll();
    }
    private void InitializeAll(){

        toolbar = findViewById(R.id.myOrderToolbar);
        toolbarText = findViewById(R.id.myOrderToolbarText);
    }
}