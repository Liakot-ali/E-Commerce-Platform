package com.team12;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

public class activityMySelling extends AppCompatActivity {
    Toolbar toolbar;
    TextView toolbarText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_selling);
        InitializeAll();

    }
    private void  InitializeAll(){

        toolbar = findViewById(R.id.mySellingToolbar);
        toolbarText = findViewById(R.id.mySellingToolbarText);
    }
}