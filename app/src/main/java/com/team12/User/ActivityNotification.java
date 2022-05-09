package com.team12.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.team12.R;

public class ActivityNotification extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    TabItem AsBuyer, AsSeller;
    TextView toolbarText;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        InitializeAll();

    }

    private void InitializeAll() {

        //----------show back button in toolbar--------
        toolbar = findViewById(R.id.notificationToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbarText = findViewById(R.id.notificationTxt);

        AsBuyer = findViewById(R.id.notificationBuyer);
        AsSeller = findViewById(R.id.notificationSeller);
        tabLayout = findViewById(R.id.notificationTab);
        viewPager = findViewById(R.id.notificationViewpager);


        tabLayout.setupWithViewPager(viewPager);

    }

    //---------for back to previous activity-------------
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}