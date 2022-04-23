package com.team12.Seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.team12.R;

public class activityMySelling extends AppCompatActivity {
    Toolbar toolbar;
    TextView toolbarText,Emptytext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_selling);
        InitializeAll();

    }
    private void  InitializeAll(){

        toolbar = findViewById(R.id.mySellingToolbar);
        toolbarText = findViewById(R.id.mySellingToolbarText);
        Emptytext = findViewById(R.id.MySellingEmptyTextView);

    }
}