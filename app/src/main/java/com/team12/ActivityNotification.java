package com.team12;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

public class ActivityNotification extends AppCompatActivity {
    Toolbar toolbar;
    TextView toolbarText,AsBuyer,AsSeller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        InitiliazeAll();
    }
    private void InitiliazeAll(){
        toolbar = findViewById(R.id.notificationToolbar);
        toolbarText = findViewById(R.id.notificationTxt);

        AsBuyer = findViewById(R.id.notificationUser);
        AsSeller = findViewById(R.id.notificationSeller);

    }
}