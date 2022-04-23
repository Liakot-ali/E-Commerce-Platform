package com.team12.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.team12.R;

public class ActivityMyOrder extends AppCompatActivity {
    Toolbar toolbar;
    TextView toolbarText;
    TextView orderEmptyText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        InitializeAll();
    }
    private void InitializeAll(){

        toolbar = findViewById(R.id.myOrderToolbar);
        toolbarText = findViewById(R.id.myOrderToolbarText);
        orderEmptyText = findViewById(R.id.myOrderTextView);
    }
}