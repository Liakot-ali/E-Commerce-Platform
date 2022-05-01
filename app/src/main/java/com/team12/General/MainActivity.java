package com.team12.General;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.team12.R;
import com.team12.User.ActivityHome;

public class MainActivity extends AppCompatActivity {
    Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, ActivityHome.class);
                startActivity(intent);
                finish();
            }
        }, 500);


//        home = findViewById(R.id.homeButton);
//        home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, ActivityHome.class));
//            }
//        });
    }
}