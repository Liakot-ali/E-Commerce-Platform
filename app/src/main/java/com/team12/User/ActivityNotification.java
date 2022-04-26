package com.team12.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.team12.R;

public class ActivityNotification extends AppCompatActivity {
    Toolbar toolbar;
    TextView toolbarText,AsBuyer,AsSeller;
    FrameLayout frame;
    LinearLayout notificationLay;


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

        frame = findViewById(R.id.notificationFrame);
        notificationLay = findViewById(R.id.notificationBtnLayout);

    }
}